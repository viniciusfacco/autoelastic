
package slas;

import static autoelastic.AutoElastic.gera_log;
import java.io.File;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


/**
 *
 * @author vinicius.rodrigues
 * 
 * 23/05/2014: This is a SLA manager
 *             The metrics are a maximal threshold and a minimal threshold
 *             The metrics monitored are the maximal and minimal numbers of hosts actives
 * 18/01/2017: - @viniciusfacco
 *             - added MaximalVMs and MinimalVMs parameters
 */
public class WSAgreementSLA {

    private final String objname = "slas.WSAgreementSLA"; //name of the object to use in log information
    public String nome;
    private int MaximalHosts; //max hosts allowed
    private int MinimalHosts; //min hosts allowed
    private int MaximalVMs; //max virtual machines allowed
    private int MinimalVMs; //min virtual machines allowed
    public String arquivo;
    private JTextArea log;

    // construtor da classe
    public WSAgreementSLA(String xmlPathname, JTextArea plog) throws ParserConfigurationException, SAXException, IOException{     
        this.arquivo = xmlPathname;
        leSLA();
        log = plog;
        gera_log(objname, "WSAgreementSLA: Min Hosts = " + this.MinimalHosts + " | Max Hosts = " + this.MaximalHosts + " | Min VMs = " + this.MinimalVMs + " | Max VMs = " + this.MaximalVMs);
    }
    
    public void leSLA() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(arquivo));
        doc.getDocumentElement().normalize();
        //System.out.println ("Elemento Root: " + doc.getDocumentElement().getNodeName());
        //obtendo termos
        NodeList Terms = doc.getElementsByTagName("Terms");
        Element Regras = (Element)Terms.item(0);
        //obtendo primeira regra
        NodeList regra;
        regra = Regras.getElementsByTagName("MaximalHosts");
        Element element = (Element)regra.item(0);
        NodeList nodelist = element.getChildNodes();
        this.MaximalHosts = Integer.parseInt(((Node)nodelist.item(0)).getNodeValue().trim());
        //obtendo segunda regra
        regra = Regras.getElementsByTagName("MinimalHosts");
        element = (Element)regra.item(0);
        nodelist = element.getChildNodes();
        this.MinimalHosts = Integer.parseInt(((Node)nodelist.item(0)).getNodeValue().trim());
        //obtendo terceira regra
        regra = Regras.getElementsByTagName("MinimalVirtualMachines");
        element = (Element)regra.item(0);
        nodelist = element.getChildNodes();
        this.MinimalVMs = Integer.parseInt(((Node)nodelist.item(0)).getNodeValue().trim());
        //obtendo quarta regra
        regra = Regras.getElementsByTagName("MaximalVirtualMachines");
        element = (Element)regra.item(0);
        nodelist = element.getChildNodes();
        this.MaximalVMs = Integer.parseInt(((Node)nodelist.item(0)).getNodeValue().trim());
    }

    /*
    public int[] get_metricas() {
        int[] metricas = new int[2];
        metricas[0] = this.MinimalHosts;
        metricas[1] = this.MaximalHosts;
        return metricas;
    }
    */
    
    //25/04/2014:método para verificar se posso aumentar o número de hosts, recebendo a quantidade atual de hosts ativos
    public boolean canIncrease(int active_resources, boolean managehosts){
        if (managehosts){
            return active_resources < this.MaximalHosts;
        } else {
            return active_resources < this.MaximalVMs;
        }
    }
    
    //25/04/2014:método para verificar se posso aumentar o número de hosts, recebendo a quantidade atual de hosts ativos
    public boolean canDecrease(int active_resources, boolean managehosts){
        if (managehosts){
            return active_resources > this.MinimalHosts;
        } else {
            return active_resources > this.MinimalVMs;
        }
    }
    
    /**
     *
     * @param managehosts
     * @return Returns the value of the high threshold which is the maximal number of resources allowed.
     */
    public int getHighThreshold(boolean managehosts){
        if (managehosts){
            return MaximalHosts;
        } else {
            return MaximalVMs;
        }
    }
    
    /**
     *
     * @param managehosts
     * @return Returns the value of the low threshold which is the minimum number of resources allowed.
     */
    public int getLowThreshold(boolean managehosts){
        if (managehosts){
            return MinimalHosts;
        } else {
            return MinimalVMs;
        }
    }
}
