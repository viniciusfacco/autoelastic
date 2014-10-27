
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
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Vinicius
 */
public class OneHost {
    
    private static final String objname = "middlewares.OneHost"; //name of the class to be used in the logs
    private int id; //id do host: preenchido quando host é criado
    private Host host; //representação do host: instanciado quando onehost é criado
    private String name; //ip do host: preenchido quando onehost é criado
    private String image_manager; //preenchido quando onehost é criado
    private String virtual_machine_manager; //preenchido quando onehost é criado
    private String virtual_network_manager; //preenchido quando onehost é criado
    private int cluster_id; //preenchido quando onehost é criado
    //private String transfer_manager; //preenchido quando onehost é criado - comentado por atualização de versão
    private boolean status; //flag que definirá se host já foi criado ou não no ambiente opennebula
    private ArrayList<OneVM> vms; //VMs alocadas no host
    private float MAX_CPU;
    private float USED_CPU;
    private float MAX_MEM;
    private float USED_MEM;
    private long LAST_MON_TIME;
     private JTextArea log;
    
    public OneHost(String nome, String im, String vmm, String vnm, int cid, JTextArea plog){
    //public OneHost(String nome, String im, String vmm, String vnm, String tm){ comentado por atualização de versão
        this.vms = new ArrayList();
        this.name = nome;
        this.image_manager = im;
        this.virtual_machine_manager = vmm;
        this.virtual_network_manager = vnm;
        this.cluster_id = cid;
        //this.transfer_manager = tm; comentado por atualização de versão
        this.status = false;
        log = plog;
    }
    
    public boolean create(Client oc) throws Exception {
        //OneResponse rc = Host.allocate(oc, name, image_manager, virtual_machine_manager, virtual_network_manager, transfer_manager); comentado por atualização de versão
        OneResponse rc = Host.allocate(oc, name, image_manager, virtual_machine_manager, virtual_network_manager);
        if( rc.isError() ){
            gera_log(objname,"Falha ao criar Host!" + "\n" + rc.getErrorMessage());
            return false;
        }
        else {
            this.id = Integer.parseInt(rc.getMessage());
            this.host = new Host(this.id, oc);
        }
        this.status = true;
        return true;
    }
    
    public boolean create(Client oc, String id) throws Exception {
        this.id = Integer.parseInt(id);
        this.host = new Host(this.id, oc);
        //aqui tambem tenho que ler e adicionar as vms que estao neste host
        String id_vms[] = this.get_values_from_xml("VMS", "ID");
        for(int i = 0; i < id_vms.length; i++){
            this.add_vm(new OneVM(oc, id_vms[i]));
        }
        this.status = true;
        return true;
    }
    
    public boolean delete() {
        OneResponse rc = this.host.delete();
        if (rc.isError()){
            gera_log(objname,"Falha ao deletar host ID " + this.id + "\n" + rc.getErrorMessage());
            return false;
        }
        this.status = false;
        return true;
    }
    
    public void add_vm(OneVM onevm){
        this.vms.add(0, onevm);
    }
    
    public boolean delete_vms(){
        while (!vms.isEmpty()){
            vms.get(0).delete();
            vms.remove(0);
        }
        return true;
    }
    
    public OneVM get_vm(int index){
        return this.vms.get(index);
    }
    
    public int get_id(){
        return this.id;
    }
    
    public String get_name(){
        return this.name;
    }
    
    public float get_used_cpu(){
        return this.USED_CPU;
    }
    
    public float get_max_cpu(){
        return this.MAX_CPU;
    }
    
    public float get_used_mem(){
        return this.USED_MEM;
    }
    
    public float get_max_mem(){
        return this.MAX_MEM;
    }
    
    public long get_last_mon_time(){
        return this.LAST_MON_TIME;
    }
    
    public boolean enable(){
        return this.status;
    }    
    
    public void sync_host() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        String info = this.get_info();
        InputSource is = new InputSource(new ByteArrayInputStream(info.getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();
        //System.out.println ("Elemento Root: " + doc.getDocumentElement().getNodeName());
        
        //obtendo o último tempoo de monitoramento
        NodeList LASTMONTIME = doc.getElementsByTagName("LAST_MON_TIME");
        this.LAST_MON_TIME = Long.parseLong(LASTMONTIME.item(0).getChildNodes().item(0).getNodeValue());
        
        //obtendo lista de métricas para pesquisa
        NodeList parametros = doc.getElementsByTagName("HOST_SHARE");
        Element conjunto_parametros = (Element)parametros.item(0);
        
        //buscando elemento MAX_CPU no xml para tradução
        NodeList MAXCPU = conjunto_parametros.getElementsByTagName("MAX_CPU");
        Element MCPU = (Element)MAXCPU.item(0);
        NodeList textMCPU = MCPU.getChildNodes();
        this.MAX_CPU = Integer.parseInt(((Node)textMCPU.item(0)).getNodeValue().trim());
        //System.out.println("MAX_CPU: " + this.MAX_CPU);
        
        //buscando elemento USED_CPU no xml para tradução
        NodeList USEDCPU = conjunto_parametros.getElementsByTagName("USED_CPU");
        Element UCPU = (Element)USEDCPU.item(0);
        NodeList textUCPU = UCPU.getChildNodes();
        this.USED_CPU = Integer.parseInt(((Node)textUCPU.item(0)).getNodeValue().trim());
        //System.out.println("USED_CPU: " + this.USED_CPU);
        
        //buscando elemento MAX_MEM no xml para tradução
        NodeList MAXMEM = conjunto_parametros.getElementsByTagName("MAX_MEM");
        Element MMEM = (Element)MAXMEM.item(0);
        NodeList textMMEM = MMEM.getChildNodes();
        this.MAX_MEM = Integer.parseInt(((Node)textMMEM.item(0)).getNodeValue().trim());
        //System.out.println("MAX_MEM: " + this.MAX_MEM);
        
        //buscando elemento USED_MEM no xml para tradução
        NodeList USEDMEM = conjunto_parametros.getElementsByTagName("USED_MEM");
        Element UMEM = (Element)USEDMEM.item(0);
        NodeList textUMEM = UMEM.getChildNodes();
        this.USED_MEM = Integer.parseInt(((Node)textUMEM.item(0)).getNodeValue().trim());
        //System.out.println("USED_MEM: " + this.USED_MEM);        
    }
    
    private String[] get_values_from_xml(String TAG, String SUBTAG) throws ParserConfigurationException, SAXException, IOException{

        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        String[] retorno;
        String info = this.get_info();
        InputSource is = new InputSource(new ByteArrayInputStream(info.getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();
        
        //obtendo lista de métricas para pesquisa
        Element conjuntoElementos = (Element)doc.getElementsByTagName(TAG).item(0);
        
        //buscando elemento SUBTAG dentro da lista TAG principal
        NodeList ElementosSUBTAG = conjuntoElementos.getElementsByTagName(SUBTAG);
        retorno = new String[ElementosSUBTAG.getLength()];
        
        for(int i = 0; i < retorno.length; i++){
            retorno[i] = ElementosSUBTAG.item(i).getChildNodes().item(0).getNodeValue().trim();
        }
        
        return retorno;
    }
    
    private String get_info() {
        OneResponse rc = this.host.info();
        if (rc.isError()) {
            gera_log(objname,"Falha ao requisitar status da vm ID " + this.id + "\n" + rc.getErrorMessage());
        }
        //System.out.println(rc.getMessage());        
        return rc.getMessage();
    }    
}
