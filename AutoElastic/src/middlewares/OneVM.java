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
 * @author @viniciusfacco
 * 18/01/2017 - @viniciusfacco
 *            - Added hostid, USED_MEM, MAX_MEM, USED_CPU, MAX_CPU and LAST_POLL to the object and its respective methods
 *            - Now attributes from virtual machines are monitored too
 * 24/01/2017 - viniciusfacco
 *            - bug correction in the xml parser
 * 26/01/2017 - viniciusfacco
 *            - bug correction in syncInfo
 */
public class OneVM {
    
    private static final String objname = "middlewares.OneVM"; //name of the class to be used in the logs
    private int id;
    private int hostid; //id from the host in which this virtual machine is
    private VirtualMachine vm;
    private String ip;
    private int state;
    private int templateid;
    private JTextArea log;
    private float USED_MEM;
    private float MAX_MEM;
    private float USED_CPU;
    private float MAX_CPU;
    private long LAST_POLL;
    
    public OneVM(int tid){
        templateid = tid;
        id = 0;
        hostid = 0;
        ip = "";
        state = 0;
        USED_MEM = 0;
        MAX_MEM = 0;
        USED_CPU = 0;
        MAX_CPU = 0;
        LAST_POLL = 0;
    }
    
    public OneVM(Client oc, int vid, int hid){
        id = vid;
        hostid = hid;
        vm = new VirtualMachine(this.id, oc);
        templateid = 0;
        ip = "";
        state = 0;
        USED_MEM = 0;
        MAX_MEM = 0;
        USED_CPU = 0;
        MAX_CPU = 0;
        LAST_POLL = 0;
    }
    
    public int deploy(Client oc, int hostid, JTextArea plog){
        Template vmtemplate = new Template(this.templateid, oc);
        OneResponse rc = vmtemplate.instantiate();
        log = plog;
        if (!rc.isError()){
            this.id = Integer.parseInt(rc.getMessage());
            this.hostid = hostid;
            this.vm = new VirtualMachine(this.id, oc);
            vm.deploy(hostid);
        }
        return this.id;
    }
    
    /**
     * This method instantiate a new virtual machine.
     * @param oc Cloud handler
     * @param plog Log area
     * @return The VM ID
     */
    public int instantiate(Client oc, JTextArea plog){
        Template vmtemplate = new Template(this.templateid, oc);
        OneResponse rc = vmtemplate.instantiate();
        log = plog;
        if (!rc.isError()){
            this.id = Integer.parseInt(rc.getMessage());
            this.vm = new VirtualMachine(this.id, oc);
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
    
    public String getIP(){
        return this.ip;
    }
    
    public int getID(){
        return this.id;
    }
    
    public int getHostID(){
        return this.hostid;
    }
    
    public int getState(){
        return this.state;
    }
    
    public float getAllocatedMEM(){
        return this.MAX_MEM;
    }
    
    public float getUsedMEM(){
        return this.USED_MEM;
    }
    
    public float getAllocatedCPU(){
        return this.MAX_CPU;
    }
    
    public float getUsedCPU(){
        return this.USED_CPU;
    }
    
    public long getLastPoll(){
        return this.LAST_POLL;
    }
  
    public void syncInfo() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        String info = this.getInfo();
        InputSource is = new InputSource(new ByteArrayInputStream(info.getBytes()));
        Document doc = docBuilder.parse(is);
        doc.getDocumentElement().normalize();
        
        Element el;
        NodeList nl;
        //gera_log(objname,"syncInfo: NIC.");
        //NodeList nl = doc.getElementsByTagName("NIC");
        //Element el = (Element) nl.item(0);
        //this.ip = el.getElementsByTagName("IP").item(0).getChildNodes().item(0).getNodeValue().trim();
        nl = doc.getElementsByTagName("IP");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getChildNodes().getLength() > 0){
                this.ip = el.getChildNodes().item(0).getNodeValue().trim();
            }
        }
        
        //gera_log(objname,"syncInfo: HISTORY.");
        nl = doc.getElementsByTagName("HID");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getChildNodes().getLength() > 0){
                this.hostid = Integer.parseInt(el.getChildNodes().item(0).getNodeValue().trim());
            }
        }
        
        //gera_log(objname,"syncInfo: STATE.");
        nl = doc.getElementsByTagName("STATE");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
           if (el.getChildNodes().getLength() > 0){
                this.state = Integer.parseInt(el.getChildNodes().item(0).getNodeValue().trim());
            }
        }
        //System.out.println("Status VM ID " + this.id + ": " + this.state);log.append("Status VM ID " + this.id + ": " + this.state + "\n");
        
        //gera_log(objname,"syncInfo: LAST_POLL.");
        nl = doc.getElementsByTagName("LAST_POLL");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getChildNodes().getLength() > 0){
                this.LAST_POLL = Long.parseLong(el.getChildNodes().item(0).getNodeValue().trim());
            }
        }
        
        //gera_log(objname,"syncInfo: MEMORY.");
        nl = doc.getElementsByTagName("MEMORY");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getChildNodes().getLength() > 0){
                this.USED_MEM = (float) (Double.parseDouble(el.getChildNodes().item(0).getNodeValue().trim()) / 1024);
            }
        }
        
        //gera_log(objname,"syncInfo: CPU.");
        nl = doc.getElementsByTagName("CPU");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getChildNodes().getLength() > 0){
                this.USED_CPU = (float) Double.parseDouble(el.getChildNodes().item(0).getNodeValue().trim());
            }
        }
        
        //gera_log(objname,"syncInfo: TEMPLATE.");
        nl = doc.getElementsByTagName("TEMPLATE");
        if (nl.getLength() > 0){
            el = (Element) nl.item(0);
            if (el.getElementsByTagName("MEMORY").getLength() > 0){
                this.MAX_MEM = (float) Double.parseDouble(el.getElementsByTagName("MEMORY").item(0).getChildNodes().item(0).getNodeValue().trim());
            }
            if (el.getElementsByTagName("CPU").getLength() > 0){
                this.MAX_CPU = (float) (Double.parseDouble(el.getElementsByTagName("CPU").item(0).getChildNodes().item(0).getNodeValue().trim()) * 100);
            }
        }
    }
    
    private String getInfo() {
        OneResponse rc = this.vm.info();
        if (rc.isError()) {
            System.out.println("Falha ao requisitar status da vm ID " + this.id + "\n" + rc.getErrorMessage());log.append("Falha ao requisitar status da vm ID " + this.id + "\n" + rc.getErrorMessage() + "\n");
        }
        //System.out.println(rc.getMessage());        
        return rc.getMessage();
    }
    
}
