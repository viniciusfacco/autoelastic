package autoelastic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import middlewares.OneManager;
import slas.WSAgreementSLA;
import evaluators.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import thresholds.*;

/**
 * Main class that runs the monitoring system.
 * @author viniciusfacco
 * 23/05/2014 - viniciusfacco
 *            - Criação do novo AutoElastic
 *            - Inicialização está OK
 * 26/05/2014 - viniciusfacco
 *            - Finalizada a implementação do novo gerenciador
 * Última atualização: 26/05/2014
 * 26/06/2014 - viniciusfacco
 *            - Inicialização do projeto no GitHub
 *            - Versão 2.1
 * 05/08/2014 - viniciusfacco
 *            - Realizadas correções de erros
 * 27/10/2014 - viniciusfacco
 *            - Update to export the MAX_MEM, USED_MEM and LAST_MON_TIME
 * 02/07/2015 - viniciusfacco
 *            - added parameter "thresholdtype"
 *            - renamed parametes "threshold_max" and "threshold_min" to "upper_threshold" and "lower_threshold"
 *            - created object to manage thresholds
 * 06/07/2015 - viniciusfacco
 *            - created selection of constructors using the new parameter "thresholdtype"
 *            - inserted calls to new obect "thresholds" to recalculate thresholds
 * 08/07/2015 - viniciusfacco
 *            - created two methods to group the code for inicialization and monitoring
 */
public class AutoElastic implements Runnable {

    private OneManager cloud_manager;   //manager para a nuvem que vamos usar
    private WSAgreementSLA sla;         //sla que será utilizado pelo gerenciador para monitoramento
    private Evaluator evaluator;        //avaliador para saber se operações devem ser tomadas ou não
    private Thresholds thresholds;      //object to manage the thresholds
    
    private static final String objname = "autoelastic.AutoElastic"; //name of the object to use in log information
    private static String frontend;
    private static String usuario;
    private static String senha;
    private static String slapath;
    private static String logspath;
    private static int vmtemplateid;
    private static int intervalo;
    private static float uppert;
    private static float lowert;
    private static String evaluatortype;
    private static String thresholdtype;
    private static int viewsize;
    private static int num_vms;
    private static JTextArea log;
    private static Graphic graphic1;
    private static Graphic graphic2;
    private static String logtime;
    private volatile boolean monitoring;
    private String[] iphosts;
    private static String image_manager;
    private static String virtual_machine_manager;
    private static String virtual_network_manager;
    private int cluster_id;
    
    public AutoElastic(JPanel pgraphic1, JPanel pgraphic2){
        graphic1 = new Graphic(pgraphic1, "CPU Usage (Total)");
        graphic2 = new Graphic(pgraphic2, "CPU Usage (%)");
    }

    /**
     * Set all systems' parameters.
     * @param pfrontend - address of the cloud frontend
     * @param pusuario - user to connect the frontend
     * @param psenha - password to connect the frontend
     * @param psla - sla file
     * @param plogs - path to save the logs
     * @param ptemplateid - template id of the virtual machine to be launched
     * @param pintervalo - time between the monitoring observations
     * @param pthreshouldmax - the value of the upper threshold
     * @param pthreshouldmin - the value of the lower threshold
     * @param pvmsporhost - amount of virtual machines to be launched in each physical machine
     * @param pevaluator - type of evaluator algorithm
     * @param pthresholdtype - type of threshold algorithm
     * @param pmonitoringwindow - size of the monitoring window
     * @param hosts - addresses of the host to be used
     * @param pim - cloud image manager type
     * @param pvmm - cloud virtual machine manager type
     * @param pvnm - cloud virtual network manager type
     * @param pcid - cloud cluster id
     * @param plog - component to receive the log messages
     */
    public void set_parameters(String pfrontend, 
                       String pusuario, 
                       String psenha, 
                       String psla, 
                       String plogs,
                       int ptemplateid, 
                       int pintervalo, 
                       double pthreshouldmax, 
                       double pthreshouldmin, 
                       int pvmsporhost,
                       String pevaluator,
                       String pthresholdtype,
                       int pmonitoringwindow, 
                       String[] hosts,
                       String pim,
                       String pvmm,
                       String pvnm,
                       int pcid,
                       JTextArea plog) {
        
        frontend = pfrontend;
        usuario = pusuario;
        senha = psenha;
        slapath = psla;
        logspath = plogs;
        vmtemplateid = ptemplateid;
        intervalo = pintervalo * 1000;
        uppert = (float) pthreshouldmax;
        lowert = (float) pthreshouldmin;
        evaluatortype = pevaluator;
        thresholdtype = pthresholdtype; 
        viewsize = pmonitoringwindow;
        num_vms = pvmsporhost;
        log = plog;
        iphosts = hosts;
        image_manager = pim;
        virtual_machine_manager = pvmm;
        virtual_network_manager = pvnm;
        cluster_id = pcid;
        graphic1.initialize();
        graphic2.initialize();
        logtime = "(" + new Date().toString().replaceAll(":", "") + ")";
        gera_log(objname,"Main: Construindo...");
    }

    @Override
    public void run() {
        
        //Este é o gerenciador principal
        System.out.println("Oi Vini");        

        try {
/*LOG*    */gera_log(objname,"Inicialização...");
            inicialize();//inicialize the system
/*LOG*    */gera_log(objname,"Main: Iniciando monitoramento...");
            elasticity();//start monitoring
/*LOG*    */gera_log(objname,"Monitoramento finalizado.");
        } catch (ParserConfigurationException | SAXException | IOException e) {
/*LOG*    */gera_log(objname,e.getMessage());
        } catch (Exception ex) {
/*LOG*    */gera_log(objname,ex.getMessage());
        }
    }

    /**
     * Stop the monitoring of the cloud
     * @return true if stop the monitoring
     */
    public boolean stop() {
        monitoring = false;
        return !monitoring;
    }
    
    //método que redimensiona o gráfico. é chamada sempre que janela redimensionar
    void resize_grafico() {
        graphic1.resize();
        graphic2.resize();
    }
    
    /**
     * Generate log in screen.
     * @param name - object that are logging
     * @param texto - the message
     */
    public static void gera_log(String name, String texto){
        System.out.println(texto);
        log.append(name + ": " + texto + "\n");
        log.setCaretPosition(log.getText().length());
    }
    
    //método para geração do arquivo de log
    private static void export_log(int tempo, int time, int num_hosts, float tot_cpu_dis, float tot_cpu_usa, float tot_mem_dis, float tot_mem_usa, double th_max, double th_min, float load, float calcutated_load, float lowert, float uppert, String extra_info){
        File arquivo = new File(logspath + "autoelastic" + logtime + ".csv");
        try (
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            escritor.append(tempo + "," + time + "," + num_hosts + "," + tot_cpu_dis + "," + tot_cpu_usa + "," + tot_mem_dis + "," + tot_mem_usa + "," + th_max + "," + th_min + "," + load + "," + calcutated_load + "," + lowert + "," + uppert + "," + extra_info + "\n");
            escritor.close();
        } catch (IOException ex) { 
            Logger.getLogger(AutoElastic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void elasticity() throws ParserConfigurationException, SAXException, IOException, InterruptedException, Exception {
        int tempo; //tempo decorrido
        int cont = 1; //contador de verificações
        long time0 = System.currentTimeMillis(); //tempo inicial
        long timen;
        while (monitoring){
        timen = System.currentTimeMillis();
        tempo = (int) ((timen - time0)/1000);
/*LOG*  */gera_log(objname,"Main: " + cont + " Time: " + tempo + "s");
/*LOG*  */gera_log(objname,"Main: Sincronizando hosts...");
        cloud_manager.syncData(); //synchronize data of the cloud
        thresholds.calculateThresholds(cloud_manager.getCPULoad()); //recalculate the thresholds
/*GRA* */graphic1.update(cont, cloud_manager.getUsedCPU(), cloud_manager.getAllocatedCPU(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold());
/*GRA* */graphic2.update(cont, cloud_manager.getCPULoad(), 1, thresholds.getUpperThreshold(), thresholds.getLowerThreshold());
/*LOG*  */gera_log(objname,"Main|monitora: Soma da carga de cpu de todos os hosts: " + cloud_manager.getUsedCPU() + " / Threshold maximo estabelecido: " + cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold() + " / Threshold minimo estabelecido: " + cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold());
/*LOG*  */export_log(cont, tempo, cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold(), cloud_manager.getCPULoad(), evaluator.getDecisionLoad(), thresholds.getLowerThreshold(), thresholds.getUpperThreshold(), cloud_manager.getLastMonitorTimes());
/*LOG*  */gera_log(objname,"Main: Realiza verificação de alguma violação dos thresholds...");
            if (!cloud_manager.isWaiting()){// if we are not waiting for new resource allocation we can evaluate the cloud
                if (evaluator.evaluate(cloud_manager.getCPULoad(), thresholds.getUpperThreshold(), thresholds.getLowerThreshold())){//analyze the cloud situation and if we have some violation we need deal with this
                    //here we need deal with the violation
                    if (evaluator.isHighAction()){//if we have a violation on the high threshold
/*LOG*                  */gera_log(objname,"Main: Avaliador detectou alta carga...Verificando se SLA está no limite...");
                        evaluator.reset(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                        thresholds.recalculateUpperThreshold(cloud_manager.getCPULoad()); //recalculate the upper threshold
                        if(sla.canIncrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can increase resources
/*LOG*                      */gera_log(objname,"Main: SLA não atingido...novo recurso pode ser alocado...");
/*LOG*                      */gera_log(objname,"Main: Alocando recursos...");
                            cloud_manager.increaseResources(); //increase one host and the number of vms informed in the parameters
                        } else {
/*LOG*                      */gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                        }
                    } else if (evaluator.isLowAction()){ //if we have a violation on the low threshold
/*LOG*                  */gera_log(objname,"Main: Avaliador detectou baixa carga...Verificando se SLA está no limite...");
                        evaluator.reset(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                        thresholds.recalculateLowerThreshold(cloud_manager.getCPULoad()); //recalculate the lower threshold
                        if(sla.canDecrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can decrease resources
/*LOG*                      */gera_log(objname,"Main: SLA não atingido...novo recurso pode ser liberado...");
/*LOG*                      */gera_log(objname,"Main: Liberando recursos...");
                            cloud_manager.decreaseResources(); //decrease the last host added and the number its vms
                        } else {
/*LOG*                      */gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                        }
                    } else {
/*LOG*                  */gera_log(objname,"Main: Evaluator problem. We have violation but we do not know which.");
                    }
                } else {
/*LOG*              */gera_log(objname,"Main: Nenhum problema detectado pelo avaliador.");
                }                    
            } else {
/*LOG*          */gera_log(objname,"Main: Aguardando inicialização de recursos.");
            }
/*LOG*      */gera_log(objname,"Main: Aguarda intervalo de tempo...");
            Thread.sleep(intervalo);
            cont++;
        }
    }

    private void inicialize() throws ParserConfigurationException, SAXException, IOException {
        //create a new cloud manager with OpenNebula
        cloud_manager = new OneManager(usuario, senha, frontend, iphosts, image_manager, virtual_machine_manager, virtual_network_manager, cluster_id, log, num_vms, vmtemplateid);            
        //connect with the cloud server
        if (cloud_manager.serverConnect()){
            gera_log(objname,"Conexão realizada com o servidor: " + frontend);
            monitoring = true; //if connection ok, then we can monitor
        } else {
            gera_log(objname,"Problema ao realizar conexão com o servidor: " + frontend);
            monitoring = false; //if trouble, then we can't monitor
        }
        //--
        gera_log(objname,"Main: Inicializando SLA: " + slapath);
        //create a new SLA
        sla = new WSAgreementSLA(slapath, log);
        //--
        gera_log(objname,"Main: Inicializando avaliador.");
        //create a new cloud evaluator
        switch (evaluatortype){
            case "generic": 
                //evaluator = new GenericEvaluator(viewsize, upper_threshold, lower_threshold);
                evaluator = new GenericEvaluator(viewsize);
                break;
            case "window_aging": 
                //evaluator = new AgingWindowEvaluator(viewsize, upper_threshold, lower_threshold);
                evaluator = new AgingWindowEvaluator(viewsize);
                break;
            case "full_aging":
                evaluator = new AgingFullEvaluator(viewsize);
        }
        //create a new threshold manager
        switch (thresholdtype){
            case "static":
                thresholds = new StaticThresholds(uppert, lowert);
                break;
            case "live":
                thresholds = new LiveThresholds(uppert, lowert);
        }
        export_log(0,0,0,0,0,0,0,0,0,0,0,0,0,"Contador,Tempo,Total Hosts Ativos,Total CPU Alocada,Total CPU Usada,Total RAM Alocada,Total RAM Usada,CPU Limite Superior,CPU Limite Inferior,% Carga de CPU,Load Calculado,Threshold Inferior,Threshold Superior,Tempos de Monitoramento");
    }
}
