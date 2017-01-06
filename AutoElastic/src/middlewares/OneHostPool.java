
package middlewares;

import static autoelastic.AutoElastic.gera_log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
 */
public class OneHostPool {
    
    //private OneHost[] hosts; //hosts que serão utilizados no ambiente    
    private static final String objname = "middlewares.OneHostPool"; //name of the class to be used in the logs
    private ArrayList<OneHost> hosts_ativos;
    private ArrayList<OneHost> hosts_inativos;
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
        
    public OneHostPool(Client oc, 
                        String[] ips, 
                        JTextArea plog, 
                        String im, 
                        String vmm, 
                        String vnm, 
                        int cluster_id,
                        boolean pmanagehost){
        hosts_inativos = new ArrayList();
        hosts_ativos = new ArrayList();
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
                hosts_inativos.add(new OneHost(ips[i], IM, VMM, VNM, CLUSTER_ID, log));
            } else {
                gera_log(objname,"OneHostPool: Nome de host vazio identificado.");
            }
            hosts = hosts + "IP: " + ips[i] + " | ";
        }
        gera_log(objname,"OneHostPool: Hosts recebidos " + hosts);
        this.hostpool = new HostPool(oc);
    }
    
    public float get_used_CPU(){
        if (managehosts){
            return this.hostUsedCPU;
        } else {
            return this.vmUsedCPU;
        }
    }
    
    public float get_allocated_CPU(){
        if (managehosts){
            return this.hostAllCPU;
        } else {
            return this.vmAllCPU;
        }
    }
    
    public float get_used_MEM(){
        if (managehosts){
            return this.hostUsedMEM;
        } else {
            return this.vmUsedMEM;
        }
    }
    
    public float get_allocated_MEM(){
        if (managehosts){
            return this.hostAllMEM;
        } else {
            return this.vmAllMEM;
        }
    }
    
    public String get_last_monitor_times(){
        if (managehosts){
            return this.hostAllMonitoringTimes;
        } else {
            return this.vmAllMonitoringTimes;
        }
    }
    
    public int get_total_ativos(){
         if (managehosts){
             return this.hosts_ativos.size();
         } else {
             return this.virtualMachines.size();
         }
    }
    
    public String getLastActiveResource(){
        if (managehosts){
            return hosts_ativos.get(hosts_ativos.size() - 1).get_name();
        } else {
            return virtualMachines.get(virtualMachines.size() - 1).get_ip();
        }
    }
    
    //return the host with specific id from the actives or inactives hosts
    public OneHost get_onehost(int id){
        OneHost onehost = null;
        for (OneHost host : hosts_ativos) {
            if (host.get_id() == id) {
                return host;
            }
        }
        for (OneHost host : hosts_inativos) {
            if (host.get_id() == id) {
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
            hosts_ativos.add(host);
            return host.get_id();
        }
        return 0;
    }
    
    //when this method is used, a new host is allocated but it is still not considered part of set of active hosts
    public int allocateHost(Client oc) throws Exception{
        if (!hosts_inativos.isEmpty()){
            OneHost host;
            host = hosts_inativos.get(0);
            host.create(oc);
            //host = hosts_inativos.remove(0);
            //hosts_ativos.add(host);
            return host.get_id();
        }
        return 0;
    }
    
    //this method insert the last host created with the method allocatesHost in the set of active hosts. this is used when the host will be considered active only when the resources in it are online.
    public void enableLastHost(){
        hosts_ativos.add(hosts_inativos.remove(0));
    }
    
    public boolean remove_host(Client oc) throws InterruptedException{        
        OneHost host;
        hosts_ativos.get(hosts_ativos.size() - 1).delete_vms();
        while (!hosts_ativos.get(hosts_ativos.size() - 1).delete()){
            Thread.sleep(1000);
        }
        //hosts_ativos.get(hosts_ativos.size() - 1).delete();
        host = hosts_ativos.remove(hosts_ativos.size() - 1);
        hosts_inativos.add(host);
        return true;
    }
    
    //synchronize the counters of the pool
    public void sync_hosts(){
        hostAllCPU = 0;
        hostUsedCPU = 0;
        hostAllMEM = 0;
        hostUsedMEM = 0;
        hostAllMonitoringTimes = "";
        for (OneHost host_ativo : hosts_ativos) {
            //percorre todos os hosts ativos
            try {
                host_ativo.sync_host(); //sincroniza cada host
                hostUsedCPU = hostUsedCPU + host_ativo.get_used_cpu(); //pega uso da cpu
                hostUsedMEM = hostUsedMEM + host_ativo.get_used_mem(); //get used memory
                //gera_log(objname, "Main|OneHostPool|sync_hosts: Uso de CPU pelo host " + host_ativo.get_id() + " : " + host_ativo.get_used_cpu());
                hostAllCPU = hostAllCPU + host_ativo.get_max_cpu(); //pega total de cpu
                hostAllMEM = hostAllMEM + host_ativo.get_max_mem(); //get total memory
                hostAllMonitoringTimes += ";" + host_ativo.get_last_mon_time(); //get the last_mon_time of the host and append in the attribute
                gera_log(objname,"sync_hosts: Host " + host_ativo.get_name() + " atualizado.");
            }catch (ParserConfigurationException | SAXException | IOException e) {
                gera_log(objname,e.getMessage());
            }
        }
        gera_log(objname,"sync_hosts:" + hosts_ativos.size() + " host(s) atualizados.");
    }
    
    //>verifica e cria os hosts no gerenciador que já estão rodando no opennebula
    public void atualiza_hosts(Client oc) throws ParserConfigurationException, SAXException, IOException, Exception {
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        InputSource is = new InputSource(new ByteArrayInputStream(hostpool.info().getMessage().getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();

        NodeList childs = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < childs.getLength(); i++){
            Element host = (Element) childs.item(i);
            cria_host(
                    host.getElementsByTagName("ID").item(0).getChildNodes().item(0).getNodeValue(),
                    host.getElementsByTagName("NAME").item(0).getChildNodes().item(0).getNodeValue(),
                    oc
                    );
        }
    }

    //cria host e adiciona no array de hosts ativos
    private void cria_host(String id, String name, Client oc) throws Exception {
        OneHost host;
        for (int i = 0; i < hosts_inativos.size(); i++){
            if (hosts_inativos.get(i).get_name().equals(name)){
                hosts_inativos.get(i).create(oc, id);
                host = hosts_inativos.remove(i);
                hosts_ativos.add(host);
            }
        }
    }
    
    //add a new host to the monitoring pool
    public boolean allocateReadOnlyHost(){
        hosts_ativos.add(hosts_inativos.remove(0));
        return true;
    }
    
    //remove the last host from the monitoring pool
    public boolean removeReadOnlyHost(){
        hosts_inativos.add(hosts_ativos.remove(hosts_ativos.size() - 1));
        return true;
    }
}
