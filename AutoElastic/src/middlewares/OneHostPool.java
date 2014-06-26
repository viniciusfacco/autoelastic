
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
 */
public class OneHostPool {
    
    //private OneHost[] hosts; //hosts que serão utilizados no ambiente    
    private static final String objname = "middlewares.OneHostPool"; //name of the class to be used in the logs
    private ArrayList<OneHost> hosts_ativos;
    private ArrayList<OneHost> hosts_inativos;
    private int usedCPU;        //soma do uso de cpu de todos os hosts ativos
    private int allocatedCPU;   //soma do total de cpu de todos os hosts ativos
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
    
    public int get_used_CPU(){
        return this.usedCPU;
    }
    
    public int get_allocated_CPU(){
        return this.allocatedCPU;
    }
    
    public int get_total_ativos(){
        return this.hosts_ativos.size();
    }
    
    public OneHost get_onehost(int id){
        OneHost onehost = null;
        for (int i = 0; i < hosts_ativos.size(); i++){
            if (hosts_ativos.get(i).get_id() == id){
                onehost = hosts_ativos.get(i);
            }
        }
        return onehost;
    }
    
    public int aloca_host(Client oc) throws Exception{
        if (!hosts_inativos.isEmpty()){
            OneHost host;
            hosts_inativos.get(0).create(oc);
            host = hosts_inativos.remove(0);
            hosts_ativos.add(host);
            return host.get_id();
        }
        return 0;
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
    
    public void sync_hosts(){
        int cputotal = 0;
        int cpuusada = 0;
        for (int i = 0; i < hosts_ativos.size(); i++){ //percorre todos os hosts ativos
            try {
                hosts_ativos.get(i).sync_host(); //sincroniza cada host
                cpuusada = cpuusada + hosts_ativos.get(i).get_used_cpu(); //pega uso da cpu
                gera_log(objname,"Main|OneHostPool|sync_hosts: Uso de CPU pelo host " + hosts_ativos.get(i).get_id() + " : " + hosts_ativos.get(i).get_used_cpu());
                cputotal = cputotal + hosts_ativos.get(i).get_max_cpu(); //pega total de cpu
            } catch (Exception e) {
                gera_log(objname,e.getMessage());
            }
        }
        this.usedCPU = cpuusada;
        this.allocatedCPU = cputotal;
    }
    
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

}
