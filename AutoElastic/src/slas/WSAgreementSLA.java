
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
 */
public class WSAgreementSLA {

    private static final String objname = "slas.WSAgreementSLA"; //name of the object to use in log information
    public String nome;
    private int MaximalHosts; //máximo de hosts permitidos
    private int MinimalHosts; //mínimo de hosts permitidos
    public String arquivo;
    private JTextArea log;

    // construtor da classe
    public WSAgreementSLA(String xmlPathname, JTextArea plog) throws ParserConfigurationException, SAXException, IOException{     
        this.arquivo = xmlPathname;
        leSLA();
        log = plog;
        gera_log(objname, "SLA Min: " + this.MinimalHosts + " / SLA Max: " + this.MaximalHosts);
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
        Element MaxHosts = (Element)regra.item(0);
        NodeList textMaxHosts = MaxHosts.getChildNodes();
        this.MaximalHosts = Integer.parseInt(((Node)textMaxHosts.item(0)).getNodeValue().trim());
        //obtendo segunda regra
        regra = Regras.getElementsByTagName("MinimalHosts");
        Element MinHosts = (Element)regra.item(0);
        NodeList textMinHosts = MinHosts.getChildNodes();
        this.MinimalHosts = Integer.parseInt(((Node)textMinHosts.item(0)).getNodeValue().trim());
        //obtendo terceira regra
    }

    public int[] get_metricas() {
        int[] metricas = new int[2];
        metricas[0] = this.MinimalHosts;
        metricas[1] = this.MaximalHosts;
        return metricas;
    }
    
    //25/04/2014:método para verificar se posso aumentar o número de hosts, recebendo a quantidade atual de hosts ativos
    public boolean canIncrease(int active_hosts){
        return active_hosts < this.MaximalHosts;
    }
    
    //25/04/2014:método para verificar se posso aumentar o número de hosts, recebendo a quantidade atual de hosts ativos
    public boolean canDecrease(int active_hosts){
        return active_hosts > this.MinimalHosts;
    }
    
    //return the value of the high threshold
    public int getHighThreshold(){
        return MaximalHosts;
    }
    
    //return the value of the low threshold
    public int getLowThreshold(){
        return MaximalHosts;
    }
}
