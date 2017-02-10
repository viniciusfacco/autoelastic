
package middlewares;

import static autoelastic.AutoElastic.gera_log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.opennebula.client.Client;
import org.opennebula.client.host.HostPool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author vinicius.rodrigues
 *27/10/2014 - viniciusfacco
 *           - Upated to save allocatedMEM, usedMEM and allMonitoringTimes
 *04/08/2015 - viniciusfacco
 *           - Renamed the method aloca_host to allocatesHostNow
 *           - Created two new methods to allocate hosts: allocatesHost and enableLastHost
 *03/01/2017 - viniciusfacco
 *           - added allocateReadOnlyHost and removeReadOnlyHost methods for Read Only mode
 *           - added getLastActiveResource method to return the last added host name 
 *06/01/2017 - viniciusfacco
 *           - added support for managing only hosts (not ready)          
 *18/01/2017 - @viniciusfacco
 *           - added list of virtual machines when the monitoring will consider only virtual machines
 *           - changed the logic of all class to enable monitoring of only virtual machines if necessary
 */
public class OneResourcePool {
    
    //private OneHost[] hosts; //hosts que serão utilizados no ambiente    
    private final String objname = "middlewares.OneResourcePool"; //name of the class to be used in the logs
    private ArrayList<OneHost> hosts_ativos;
    private ArrayList<OneHost> hosts_inativos;
    private ArrayList<OneHost> pending_hosts;
    private ArrayList<OneVM> virtualMachines;
    private float hostUsedCPU;        //soma do uso de cpu de todos os hosts ativos
    private float hostAllCPU;         //soma do total de cpu de todos os hosts ativos
    private float hostUsedMEM;        //soma do uso de memória de todos os hosts ativos
    private float hostAllMEM;         //soma do total de memória de todos os hosts ativos
    private String hostAllMonitoringTimes; //string with all LAST_MON_TIME's of the used hosts in the pool
    private float vmUsedCPU;          //sum of all virtual machines used CPU 
    private float vmAllCPU;           //sum of all virtual machines available CPU
    private float vmUsedMEM;          //sum of all virtual machines used MEM 
    private float vmAllMEM;           //sum of all virtual machines available MEM
    private String vmAllMonitoringTimes; //string with all LAST_POOL's of the used hosts in the pool
    private JTextArea log;
    private final String IM;
    private final String VMM;
    private final String VNM;
    private final int CLUSTER_ID;
    private final HostPool hostpool;
    private final boolean managehosts;
        
    public OneResourcePool(Client oc, 
                        String[] ips, 
                        JTextArea plog, 
                        String im, 
                        String vmm, 
                        String vnm, 
                        int cluster_id,
                        boolean pmanagehost) throws SAXException, IOException, Exception{
        hosts_inativos = new ArrayList();
        hosts_ativos = new ArrayList();
        pending_hosts = new ArrayList();
        virtualMachines = new ArrayList();
        log = plog;
        managehosts = pmanagehost;
        IM = im;
        VMM = vmm;
        VNM = vnm;
        CLUSTER_ID = cluster_id;
        String hosts = "";
        for (int i = 0; i < ips.length; i++){
            //hosts[i] = new OneHost(ips[i], "im_kvm", "vmm_kvm", "dummy", "tm_ssh");
            //hosts_inativos.add(new OneHost(ips[i], "kvm", "kvm", "dummy", 100, log));
            //hosts_inativos.add(new OneHost(ips[i], "im_kvm", "vmm_kvm", "dummy", "tm_ssh")); comentado por atualização de versão
            if (!ips[i].equalsIgnoreCase("")){
                hosts_inativos.add(0, new OneHost(ips[i], IM, VMM, VNM, CLUSTER_ID, log));
            } else {
                gera_log(objname,"OneHostPool: Host name empty.");
            }
            hosts = hosts + "IP: " + ips[i] + " | ";
        }
        //gera_log(objname,"OneResourcePool: Hosts recebidos " + hosts);
        this.hostpool = new HostPool(oc);
        updateResources(oc);
    }
    
    public float getUsedCPU(){
        if (managehosts){
            return this.hostUsedCPU;
        } else {
            return this.vmUsedCPU;
        }
    }
    
    public float getAllocatedCPU(){
        if (managehosts){
            return this.hostAllCPU;
        } else {
            return this.vmAllCPU;
        }
    }
    
    public float getUsedMEM(){
        if (managehosts){
            return this.hostUsedMEM;
        } else {
            return this.vmUsedMEM;
        }
    }
    
    public float getAllocatedMEM(){
        if (managehosts){
            return this.hostAllMEM;
        } else {
            return this.vmAllMEM;
        }
    }
    
    public String getLastMonitorTimes(){
        if (managehosts){
            return this.hostAllMonitoringTimes;
        } else {
            return this.vmAllMonitoringTimes;
        }
    }
    
    public int getTotalAtivos(){
         if (managehosts){
             return this.hosts_ativos.size();
         } else {
             return this.virtualMachines.size();
         }
    }
    
    public String getLastActiveResources(int amount_of_hosts, int amount_of_vms){
        String resources = "";
        if (managehosts){
            for (int i = 0; i < amount_of_hosts; i++){
                resources += hosts_ativos.get(i).getName() + ";";
                resources += hosts_ativos.get(i).getVMsIPs() + "\n";
            }
        } else {
            for (int i = 0; i < amount_of_vms; i++){
                resources += virtualMachines.get(i).getIP() + ";";
            }
        }
        return resources;
    }
    
    //return the host with specific id from the actives or inactives hosts
    public OneHost getOneHost(int id){
        OneHost onehost = null;
        for (OneHost host : pending_hosts) {
            if (host.getID() == id) {
                return host;
            }
        }
        for (OneHost host : hosts_ativos) {
            if (host.getID() == id) {
                return host;
            }
        }
        for (OneHost host : hosts_inativos) {
            if (host.getID() == id) {
                return host;
            }
        }
        return onehost;
    }
    
    //when this method is used, automatically allocated host becomes part of the set of active hosts
    public int allocateHostNow(Client oc) throws Exception{
        if (!hosts_inativos.isEmpty()){
            OneHost host;
            hosts_inativos.get(0).create(oc);
            host = hosts_inativos.remove(0);
            hosts_ativos.add(0, host);
            return host.getID();
        }
        return 0;
    }
    
    //when this method is used, a new host is allocated but it is still not considered part of set of active hosts
    public int allocateResource(Client oc) throws Exception{
        if (managehosts){
            if (!hosts_inativos.isEmpty()){
                pending_hosts.add(0, hosts_inativos.remove(0));
                pending_hosts.get(0).create(oc);
                return pending_hosts.get(0).getID();
            }
        } 
        return 0;
    }
    
    //this method insert the last host created with the method allocatesHost in the set of active hosts. this is used when the host will be considered active only when the resources in it are online.
    public void enableLastHost(){
        hosts_ativos.add(0,pending_hosts.remove(0));
    }
    
    public void addVirtualMachine(OneVM vm){
        virtualMachines.add(0, vm);
    }
    
    public boolean removeResource(Client oc, int amount_of_vms, int amount_of_hosts) {        
        if (managehosts){
            //if we must manage hosts then remove the last amount of hosts
            OneHost host;
            for (int i = 0; i < amount_of_hosts; i++){
                hosts_ativos.get(0).deleteVMs();
                while (!hosts_ativos.get(0).delete()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {Logger.getLogger(OneResourcePool.class.getName()).log(Level.SEVERE, null, ex);}
                }
                //hosts_ativos.get(i).delete();
                host = hosts_ativos.remove(0);
                hosts_inativos.add(0,host);
            }
            return true;
        } else {
            //if we do not want to manage hosts then remove the last amount of vms
            OneVM vm;
            for (int i = 0; i < amount_of_vms; i++){
                vm = virtualMachines.remove(0);
                while (!vm.delete()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {Logger.getLogger(OneResourcePool.class.getName()).log(Level.SEVERE, null, ex);}
                }
            }
            return true;
        }
    }
    
    //synchronize the counters of the pool
    public void syncResources(){
        if (managehosts){
            //we update the hosts information if we want to manage them
            hostAllCPU = 0;
            hostUsedCPU = 0;
            hostAllMEM = 0;
            hostUsedMEM = 0;
            hostAllMonitoringTimes = "";
            for (OneHost host_ativo : hosts_ativos) {
                //percorre todos os hosts ativos
                try {
                    //gera_log(objname,"syncResources: Updating HOST ID " + host_ativo.getID() + " data from cloud.");
                    host_ativo.syncInfo(); //sincroniza cada host
                    hostUsedCPU = hostUsedCPU + host_ativo.getUsedCPU(); //pega uso da cpu
                    hostUsedMEM = hostUsedMEM + host_ativo.getUsedMEM(); //get used memory
                    //gera_log(objname, "Main|OneResourcePool|syncResources: Uso de CPU pelo host " + host_ativo.getID() + " : " + host_ativo.getUsedCPU());
                    hostAllCPU = hostAllCPU + host_ativo.getMaxCPU(); //pega total de cpu
                    hostAllMEM = hostAllMEM + host_ativo.getMaxMEM(); //get total memory
                    hostAllMonitoringTimes += ";" + host_ativo.getLastMonTime(); //get the last_mon_time of the host and append in the attribute
                    gera_log(objname,"syncResources: HOST " + host_ativo.getID() + " synchronized.");
                }catch (ParserConfigurationException | SAXException | IOException e) {
                    gera_log(objname,e.getMessage());
                }
            }
            gera_log(objname,"syncResources: " + hosts_ativos.size() + " host(s) synchronized.");
        } else {
            //here we update only virtual machine information if we do not want to manage the hosts
            vmAllCPU = 0;
            vmUsedCPU = 0;
            vmAllMEM = 0;
            vmUsedMEM = 0;
            vmAllMonitoringTimes = "";
            float time = 0;
            for (OneVM vm : virtualMachines){
                //gera_log(objname,"syncResources: Updating VM ID " + vm.getID() + " data from cloud.");
                time = vm.syncInfo();
                //gera_log(objname,"syncResources: VM ID " + vm.getID() + " data from cloud updated.");
                vmUsedCPU = vmUsedCPU + vm.getUsedCPU();
                vmUsedMEM = vmUsedMEM + vm.getUsedMEM();
                vmAllCPU = vmAllCPU + vm.getAllocatedCPU();
                vmAllMEM = vmAllMEM + vm.getAllocatedMEM();
                vmAllMonitoringTimes += ";" + vm.getLastPoll();
                gera_log(objname,"syncResources: VM " + vm.getID() + " synchronized. (Data from cloud in " + time + "s)");
            }
            gera_log(objname,"syncResources: " + virtualMachines.size() + " vm(s) synchronized.");
        }
    }
    
    //>update hosts running in the cloud and its virtual machines
    private void updateResources(Client oc) throws ParserConfigurationException, SAXException, IOException, Exception {
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        InputSource is = new InputSource(new ByteArrayInputStream(hostpool.info().getMessage().getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();

        NodeList childs = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < childs.getLength(); i++){
            Element host = (Element) childs.item(i);
            createHost(
                    host.getElementsByTagName("ID").item(0).getChildNodes().item(0).getNodeValue(),
                    host.getElementsByTagName("NAME").item(0).getChildNodes().item(0).getNodeValue(),
                    oc
                    );
        }
        //now if we do not want to manage hosts we need to get all active virtual machines in these hosts
        if (!managehosts){
            for (OneHost host : hosts_ativos) { //for each active host
                for (OneVM vm : host.getVMs()){ //we get each vm
                    virtualMachines.add(0, vm); //and add in the arraylist
                }
            }
        }
    }

    //cria host e adiciona no array de hosts ativos
    private void createHost(String id, String name, Client oc) throws Exception {
        OneHost host;
        for (int i = 0; i < hosts_inativos.size(); i++){
            if (hosts_inativos.get(i).getName().equals(name)){
                hosts_inativos.get(i).create(oc, id);
                host = hosts_inativos.remove(i);
                hosts_ativos.add(0, host);
            }
        }
    }
    
    //add a new host to the monitoring pool
    public boolean allocateReadOnlyHost(){
        hosts_ativos.add(0, hosts_inativos.remove(0));
        return true;
    }
    
    //remove the last host from the monitoring pool
    public boolean removeReadOnlyHost(){
        hosts_inativos.add(0,hosts_ativos.remove(0));
        return true;
    }
}
