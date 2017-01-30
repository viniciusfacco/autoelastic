/*
 * 25/04/2014
 * OneManager
 * - Classe destinada a fazer a a realizar operações e leituras no ambiente
 */

package middlewares;

import static autoelastic.AutoElastic.gera_log;
import communication.SSHClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.xml.sax.SAXException;

/**
 * Cloud Manager
 * @author viniciusfacco
 * 27/10/2014 - viniciusfacco
 *            - Upated to get allocatedMEM, usedMEM and allMonitoringTimes from the pool
 * 11/08/2015 - viniciusfacco
 *            - removed sleep between VM allocations
 *            - added method to set the SSHClient in the OneCommunicator
 * 16/11/2016 - viniciusfacco
 *            - added new parameters: psshuser, psshpassword, psshserver, pmsgwarningremove, pmsgcanremove, pmsgnewresources, plocaldirtemp, premotedirsource and premotedirtarget
 * 03/01/2017 - viniciusfacco
 *            - added organizeReadOnlyMode, increaseReadOnlyResources and decreaseReadOnlyResources methods for Read Only mode
 * 18/01/2017 - @viniciusfacco
 *            - added funcionality to monitor only virtual machines if necessary
 *            - added fkag managehosts
 *            - fixed bug in newResourcesPending to enable resources in the monitoring after they get online
 * 24/01/2017 - viniciusfacco
 *            - added port parameters to connect servers
 * 30/01/2017 - viniciusfacco
 *            - now ping vms use ssh method and run the command in the front end
 */
public class OneManager {
    
    private static final String objname = "middlewares.OneManager"; //name of the class to be used in the logs
    private Client oneClient; //cliente de conexão com o servidor opennebula
    private OneResourcePool orpool; //set of resources in the cloud
    private OneCommunicator messenger; //comunicador utilizado para realizar a comunicação de operações de elasticidade
    private final String user; //usuario para conexão com o OpenNebula
    private final String password; //senha para conexão com o OpenNebula
    private final String server_address; //IP do servidor OpenNebula
    private int server_port; //porta de conexão com o servidor OpenNebula
    private final String[] iphosts;
    private static String image_manager;
    private static String virtual_machine_manager;
    private static String virtual_network_manager;
    private final int cluster_id;
    private final JTextArea log;
    private final int vms_per_operation;
    private final int hosts_per_operation = 1;
    private final int vmtemplateid;
    private boolean waiting_vms;
    private ArrayList<OneVM> new_vms;
    private final boolean managehosts;
    
    public OneManager(  String puser, 
                        String ppassword, 
                        String pserver_address, 
                        String psshuser,
                        String psshpassword,
                        String psshserver,
                        String[] hosts, 
                        String pim, 
                        String pvmm, 
                        String pvnm, 
                        int pcid, 
                        JTextArea plog, 
                        int pvms_for_host, 
                        int pvmtemplateid,
                        String pmsgwarningremove, 
                        String pmsgcanremove, 
                        String pmsgnewresources, 
                        String plocaldirtemp, 
                        String premotedirsource, 
                        String premotedirtarget,
                        boolean pmanagehosts,
                        int pserverport
    ){
        user = puser;
        password = ppassword;
        server_address = pserver_address;
        iphosts = hosts;
        managehosts = pmanagehosts;
        image_manager = pim;
        virtual_machine_manager = pvmm;
        virtual_network_manager = pvnm;
        cluster_id = pcid;
        log = plog;
        vms_per_operation = pvms_for_host;
        vmtemplateid = pvmtemplateid;
        waiting_vms = false;
        messenger = new OneCommunicator(psshserver, psshuser, psshpassword, plog);
        messenger.setParameters(pmsgwarningremove, pmsgcanremove, pmsgnewresources, plocaldirtemp, premotedirsource, premotedirtarget);
        server_port = pserverport;
    }
    
    public boolean serverConnect(){
        try {
            //> realiza conexão com o front-end
            oneClient = new Client(user + ":" + password, "http://" + server_address + ":" + server_port + "/RPC2");
            if (oneClient.get_version().getMessage() != null){ //try to get the version. if null is because we do not have connection with the server
                gera_log(objname,"serverConnect: OpenNebula version: " + oneClient.get_version().getMessage());
                //>criação dos hosts que podem ser utilizados
                orpool = new OneResourcePool(
                        oneClient, 
                        iphosts, 
                        log, 
                        image_manager, 
                        virtual_machine_manager, 
                        virtual_network_manager, 
                        cluster_id, 
                        managehosts);
                //>verifica e cria os hosts no gerenciador que já estão rodando no opennebula
                //ohpool.atualiza_hosts(oneClient);
                return true;            
            } else {
                return false;
            }
        } catch (ClientConfigurationException ex) {
            Logger.getLogger(OneManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(OneManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void syncData(){
        orpool.syncResources(); //sincroniza dados dos hosts
    }
    
    /**
     * Return the load of the cloud based on CPU
     * @return [0 &lt load &lt 1]
     */
    public float getCPULoad(){
        float used = orpool.getUsedCPU();
        float allocated = orpool.getAllocatedCPU();        
        float load = used / allocated;
        return load;
    }
    
    //return the total of CPU available in the cloud
    public float getAllocatedCPU(){
        return orpool.getAllocatedCPU();
    }
    
    /**
     * Return the amount of busy CPU
     * @return 0 &lt CPU 
     */
    public float getUsedCPU(){
        return orpool.getUsedCPU();
    }
    
    //return the total of MEM available in the cloud
    public float getAllocatedMEM(){
        return orpool.getAllocatedMEM();
    }
    
    //return the current use of MEM
    public float getUsedMEM(){
        return orpool.getUsedMEM();
    }
    
    /**
     * Return the list of the last monitor time of each host.
     * @return the times in string separeted by ","
     */
    public String getLastMonitorTimes(){
        return orpool.getLastMonitorTimes();
    }
    
    //return the current number of hosts in use
    public int getTotalActiveResources(){
        return orpool.getTotalAtivos();
    }

    /**
     * Create a new host with virtual machines.
     * @return
     * @throws InterruptedException
     * @throws Exception
     */
    public boolean increaseResources() throws InterruptedException, Exception{
        waiting_vms = false;
        new_vms = new ArrayList();
        if (managehosts){
            int hostid;
            for (int i = 0; i < hosts_per_operation; i++){
                //hostid = ohpool.allocatesHostNow(oneClient); //allocates the host and it will be active immediatly
                hostid = orpool.allocateResource(oneClient);      //allocates the host and it will be active after resorces be online
                if (hostid > 0){
                    for (int j = 0; j < vms_per_operation; j++) {
                        new_vms.add(0,new OneVM(vmtemplateid));
                        new_vms.get(0).deploy(oneClient, hostid, log);//aloca vm nesse host
                        //gera_log(objname,"Main: Nova VM alocada: " + last_vms[i].getID());
                        orpool.getOneHost(hostid).addVM(new_vms.get(0));
                        waiting_vms = true;
                        //System.out.println("Allocating vm " + j);
                        //Thread.sleep(10000); //why?
                    }
                } else {
                    System.out.println("Problem to allocate host " + i);
                }
            }
        } else {
            for (int i = 0; i < vms_per_operation; i++){
                new_vms.add(0,new OneVM(vmtemplateid));
                new_vms.get(0).instantiate(oneClient, log);
                waiting_vms = true;
                //System.out.println("Instantiating vm " + i);
            }
        }
        return waiting_vms;
    }
        
    //método que remove um host e suas máquinas virtuais no ambiente
    public boolean decreaseResources() throws InterruptedException, IOException{
        if (messenger.notifyDecrease(orpool.getLastActiveResources(vms_per_operation, hosts_per_operation))){
            while(!messenger.canDecrease()){}
            return orpool.removeResource(oneClient, vms_per_operation, hosts_per_operation);//remove último host criado e suas vms também
        }
        return false;
    }
    
    /**
     * Remove the host with the highest id and its virtual machines without ask permission
     * @return
     * @throws InterruptedException
     */
    public boolean decreaseResourcesHard() throws InterruptedException{
        return orpool.removeResource(oneClient, vms_per_operation, hosts_per_operation);//remove último host criado e suas vms também
    }
    
    public boolean newResourcesPending() throws ParserConfigurationException, SAXException, IOException, InterruptedException{        
        String message = "";
        if (waiting_vms){
            for (int i = 0; i < new_vms.size(); i++){
                new_vms.get(i).syncInfo();
                if (!messenger.ping(new_vms.get(i).getIP())){
                    return waiting_vms;
                }
                message = message + new_vms.get(i).getIP() + "\n";
            }
            waiting_vms = false;
            //gera_log(objname,"Notifica criação de novos recursos...");
            messenger.notifyNewResources(message);
            
            //now lets activate this new resources in the monitoring
            if(managehosts){ //if we are monitoring hosts then lets enable the added hosts
                for (int i = 0; i < hosts_per_operation; i++){
                    orpool.enableLastHost();
                }
            } else { //if we are monitoring only virtual machines lets add them to monitoring
                for (int i = 0; i < new_vms.size(); i++){
                    orpool.addVirtualMachine(new_vms.get(i));
                }
            }
            new_vms = null;
        }
        return waiting_vms;
    }
         
    public void setSSHClient(SSHClient sshClient) {
        this.messenger.setSSHClient(sshClient);
    }
    
    public void reset(){
        this.waiting_vms = false;
    }
    
    //remove host from the monitoring pool leaving only "lowThreshold" hosts
    public void organizeReadOnlyMode(int lowThreshold) {
        int hosts = orpool.getTotalAtivos();
        for (int i = lowThreshold; i < hosts; i++){
            gera_log(objname,"organizeReadOnlyMode: Removing host " + i + " from " + hosts + ".");
            orpool.removeReadOnlyHost();
        }
    }
    
    public boolean increaseReadOnlyResources(){
        orpool.allocateReadOnlyHost();
        try {
            messenger.notifyNewResources(orpool.getLastActiveResources(vms_per_operation, hosts_per_operation));
        } catch (ParserConfigurationException | SAXException | IOException | InterruptedException ex) {Logger.getLogger(OneManager.class.getName()).log(Level.SEVERE, null, ex);}
        return true;
    }
    
    public boolean decreaseReadOnlyResources() throws InterruptedException, IOException{
        if (messenger.notifyDecrease(orpool.getLastActiveResources(vms_per_operation, hosts_per_operation))){
            while(!messenger.canDecrease()){}
            return orpool.removeReadOnlyHost();
        }
        return false;
    }
    
    //================================ Métodos não utilizados ========================================
    //================================ Implementação para futura nova versão =========================
    //instantiate "amounthosts" hosts with "vmsperhost" virtual machines each one / return an array with the IPs of the new virtual machines

    /**
     * Unused
     * @param amounthosts
     * @param vmsperhost
     * @param idtemplatevm
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
        public String[] instantiate_resources(int amounthosts, int vmsperhost, int idtemplatevm) throws ParserConfigurationException, SAXException, IOException{
        int hostid;
        int vmid;
        String[] ipvms = new String[vmsperhost * amounthosts]; //create a array to store the IPs of all new virtual machines
        int countvm = 0; //in the end the total of virtual machines
        for(int i = 0; i < amounthosts; i++){ //for each new host
            hostid = instantiate_host(); //create a new host
            if (hostid > 0){ //if success and a new host was create
                //gera_log(objname,"Main: Novo host alocado: " + hostid); //log
                //gera_log(objname,"Main: Aloca nova VM no host ID " + hostid); //log
                for (int j = 0; j < vmsperhost; j++){ //create vmsperhost new virtual machines
                    vmid = instantiate_vm(hostid, idtemplatevm); //create a new virtual machine in "hostid"
                    //gera_log(objname,"Main: Nova VM alocada: " + vmid); //log
                    ipvms[countvm] = orpool.getOneHost(hostid).getVM(0).getIP(); //store the IP of this virtual machine
                    countvm++;
                }
            } else {//problem in host allocation
                gera_log(objname,"Main: Host não alocado..."); //log
            }
        }
        return ipvms;
    }
    
    //instantiate a new virtual machine (vmtemplateid) in the "hid" host
    private int instantiate_vm(int hid, int vmtemplateid) throws ParserConfigurationException, SAXException, IOException {
        int vmid;
        OneVM vm = new OneVM(vmtemplateid);
        vmid = vm.deploy(oneClient, hid, log);
        orpool.getOneHost(hid).addVM(vm); //add this virtual machine in the "ohpool"
        return vmid;
    }
    
    //instantiate a new host in the cloud
    private int instantiate_host(){
        int hostid = 0;
        try {
            hostid = orpool.allocateHostNow(oneClient); //create a new host in the "ohpool"
        } catch (Exception ex) {
            Logger.getLogger(OneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostid;
    }
    
    /*
    public boolean newResourcesPending() throws ParserConfigurationException, SAXException, IOException, InterruptedException{        
        if (waiting_vms){
            last_vms[0].syncInfo();
            last_vms[1].syncInfo();
            if (last_vms[0].getIP().equalsIgnoreCase("") || last_vms[1].getIP().equalsIgnoreCase("")) {
                return false;
            } else {
                if (ping(last_vms[0].getIP()) && ping(last_vms[1].getIP())){
                    ohpool.enableLastHost();
                    waiting_vms = false;
                    //gera_log(objname,"Notifica criação de novos recursos...");
                    messenger.notifyNewResources(last_vms[0].getIP() + "\n" + last_vms[1].getIP());
                }
            }
        }
        return waiting_vms;
    }*/

    //================================ Methods discontinued =========================
    
    //ping :)
    //this ping works in windows 
    private boolean ping(String ip) {
        if (ip.equalsIgnoreCase("")){return false;}
        String resposta;
        int fim;
        boolean online = false;
        String comando = "ping -n 1 -w 600 " + ip;
        try {
            Scanner s = new Scanner(Runtime.getRuntime().exec("cmd /c " + comando).getInputStream());
            while (s.hasNextLine()) {
                resposta = s.nextLine() + "\n";
                fim = resposta.length() - 5;
                for (int j = ip.length() + 13; j <= fim; j++) {
                    if (resposta.substring(j, j + 5).equals("tempo")) {
                        online = true;
                        break;
                    }
                }
                if (online) {
                    break;
                }
            }
        } catch (Exception e) {
        }
        return online;
    }

}
