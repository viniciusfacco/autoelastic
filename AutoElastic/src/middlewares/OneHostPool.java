
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
 * 27/10/2014 - viniciusfacco
 *            - Upated to save allocatedMEM, usedMEM and allMonitoringTimes
 * 04/08/2015 - viniciusfacco
 *            - Renamed the method aloca_host to allocatesHostNow
 *            - Created two new methods to allocate hosts: allocatesHost and enableLastHost
 * 03/01/2017 - viniciusfacco
 *            - added allocateReadOnlyHost and removeReadOnlyHost methods for Read Only mode
 *            - added getLastActiveHost method to return the last added host name 
 */
public class OneHostPool {
    
    //private OneHost[] hosts; //hosts que serão utilizados no ambiente    
    private static final String objname = "middlewares.OneHostPool"; //name of the class to be used in the logs
    private ArrayList<OneHost> hosts_ativos;
    private ArrayList<OneHost> hosts_inativos;
    private float usedCPU;        //soma do uso de cpu de todos os hosts ativos
    private float allocatedCPU;   //soma do total de cpu de todos os hosts ativos
    private float usedMEM;        //soma do uso de memória de todos os hosts ativos
    private float allocatedMEM;   //soma do total de memória de todos os hosts ativos
    private String allMonitoringTimes; //string with all LAST_MON_TIME's of the used hosts in the pool
    private JTextArea log;
    private final String IM;
    private final String VMM;
    private final String VNM;
    private final int CLUSTER_ID;
    private final HostPool hostpool;
    
    public OneHostPool(Client oc, String[] ips, JTextArea plog, String im, String vmm, String vnm, int cluster_id){
        hosts_inativos = new ArrayList();
        hosts_ativos = new ArrayList();
        log = plog;
        IM = im;
        VMM = vmm;
        VNM = vnm;
        CLUSTER_ID = cluster_id;
        for (int i = 0; i < ips.length; i++){
            //hosts[i] = new OneHost(ips[i], "im_kvm", "vmm_kvm", "dummy", "tm_ssh");
            //hosts_inativos.add(new OneHost(ips[i], "kvm", "kvm", "dummy", 100, log));
            //hosts_inativos.add(new OneHost(ips[i], "im_kvm", "vmm_kvm", "dummy", "tm_ssh")); comentado por atualização de versão
            hosts_inativos.add(new OneHost(ips[i], IM, VMM, VNM, CLUSTER_ID, log));
        }
        this.hostpool = new HostPool(oc);
    }
    
    public float get_used_CPU(){
        return this.usedCPU;
    }
    
    public float get_allocated_CPU(){
        return this.allocatedCPU;
    }
    
    public float get_used_MEM(){
        return this.usedMEM;
    }
    
    public float get_allocated_MEM(){
        return this.allocatedMEM;
    }
    
    public String get_last_monitor_times(){
        return this.allMonitoringTimes;
    }
    
    public int get_total_ativos(){
        return this.hosts_ativos.size();
    }
    
    public String getLastActiveHost(){
        return hosts_ativos.get(hosts_ativos.size() - 1).get_name();
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
        allocatedCPU = 0;
        usedCPU = 0;
        allocatedMEM = 0;
        usedMEM = 0;
        allMonitoringTimes = "";
        for (OneHost host_ativo : hosts_ativos) {
            //percorre todos os hosts ativos
            try {
                host_ativo.sync_host(); //sincroniza cada host
                usedCPU = usedCPU + host_ativo.get_used_cpu(); //pega uso da cpu
                usedMEM = usedMEM + host_ativo.get_used_mem(); //get used memory
                //gera_log(objname, "Main|OneHostPool|sync_hosts: Uso de CPU pelo host " + host_ativo.get_id() + " : " + host_ativo.get_used_cpu());
                allocatedCPU = allocatedCPU + host_ativo.get_max_cpu(); //pega total de cpu
                allocatedMEM = allocatedMEM + host_ativo.get_max_mem(); //get total memory
                allMonitoringTimes += ";" + host_ativo.get_last_mon_time(); //get the last_mon_time of the host and append in the attribute
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
