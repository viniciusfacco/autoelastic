/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middlewares;

import static autoelastic.AutoElastic.gera_log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.template.Template;
import org.opennebula.client.vm.VirtualMachine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author vinicius.rodrigues
 */
public class OneVM {
    
    private static final String objname = "middlewares.OneVM"; //name of the class to be used in the logs
    private int id;
    private VirtualMachine vm;
    private String ip = "";
    private int state;
    private int templateid;
    private JTextArea log;
    
    public OneVM(int tid){
        this.templateid = tid;
    }
    
    public OneVM(Client oc, String vid){
        this.id = Integer.parseInt(vid);
        this.vm = new VirtualMachine(this.id, oc);
    }
    
    public int deploy(Client oc, int hostid, JTextArea plog){
        Template vmtemplate = new Template(this.templateid, oc);
        OneResponse rc = vmtemplate.instantiate();
        log = plog;
        if (!rc.isError()){
            this.id = Integer.parseInt(rc.getMessage());
            this.vm = new VirtualMachine(this.id, oc);
            vm.deploy(hostid);
        }
        return this.id;
    }
    
    public boolean delete(){
        //this.vm.finalizeVM();
        //this.vm.cancel();
        OneResponse rc = this.vm.delete();
         if (rc.isError()){
            System.out.println("Falha ao deletar VM ID " + this.id + "\n" + rc.getErrorMessage());
            return false;
        }
        return true;
    }
    
    public String get_ip(){
        return this.ip;
    }
    
    public int get_id(){
        return this.id;
    }
    
    public int get_state(){
        return this.state;
    }
  
    public void sync_vm() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        String info = this.get_info();
        InputSource is = new InputSource(new ByteArrayInputStream(info.getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();
        
        NodeList nl = doc.getElementsByTagName("NIC");
        Element el = (Element) nl.item(0);
        this.ip = el.getElementsByTagName("IP").item(0).getChildNodes().item(0).getNodeValue().trim();
        
        nl = doc.getElementsByTagName("STATE");
        el = (Element) nl.item(0);
        this.state = Integer.parseInt(el.getChildNodes().item(0).getNodeValue().trim());
        System.out.println("Status VM ID " + this.id + ": " + this.state);log.append("Status VM ID " + this.id + ": " + this.state + "\n");
    }
    
    private String get_info() {
        OneResponse rc = this.vm.info();
        if (rc.isError()) {
            System.out.println("Falha ao requisitar status da vm ID " + this.id + "\n" + rc.getErrorMessage());log.append("Falha ao requisitar status da vm ID " + this.id + "\n" + rc.getErrorMessage() + "\n");
        }
        //System.out.println(rc.getMessage());        
        return rc.getMessage();
    }
    
}
