package autoelastic;

import evaluators.Aging;
import evaluators.Generic;
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

/**
 *
 * @author vinicius.rodrigues
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
 */
public class AutoElastic implements Runnable {

    private OneManager cloud_manager;   //manager para a nuvem que vamos usar
    private WSAgreementSLA sla;         //sla que será utilizado pelo gerenciador para monitoramento
    private Generic evaluator; //avaliador para saber se operações devem ser tomadas ou não
    
    private static final String objname = "autoelastic.AutoElastic"; //name of the object to use in log information
    private static String frontend;
    private static String usuario;
    private static String senha;
    private static String slapath;
    private static String logspath;
    private static int vmtemplateid;
    private static int intervalo;
    private static float threshold_max;
    private static float threshold_min;
    private static String evaluatortype;
    private static int viewsize;
    private static int num_vms;
    private static JTextArea log;
    private static Graphic grafico;
    private static String logtime;
    private volatile boolean monitoring;
    private String[] iphosts;
    private static String image_manager;
    private static String virtual_machine_manager;
    private static String virtual_network_manager;
    private int cluster_id;
    
    public AutoElastic(JPanel pgraficoline){
        grafico = new Graphic(pgraficoline);
    }

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
                       int plimiteestouros, 
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
        threshold_max = (float) pthreshouldmax;
        threshold_min = (float) pthreshouldmin;
        evaluatortype = pevaluator;
        viewsize = plimiteestouros;
        num_vms = pvmsporhost;
        log = plog;
        iphosts = hosts;
        image_manager = pim;
        virtual_machine_manager = pvmm;
        virtual_network_manager = pvnm;
        cluster_id = pcid;
        grafico.initialize();
        logtime = "(" + new Date().toString().replaceAll(":", "") + ")";
        gera_log(objname,"Main: Construindo...");
    }

    @Override
    public void run() {
        
        //Este é o gerenciador principal
        System.out.println("Oi Vini");        

        try {
            //---------------------------INICIALIZAÇÂO---------------------------------//
            gera_log(objname,"Inicialização...");
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
            //create a new cloud evaluator and set the thresholds
            switch (evaluatortype){
                case "generic": 
                    evaluator = new Generic(viewsize, threshold_max, threshold_min);
                    break;
                case "aging": 
                    evaluator = new Aging(viewsize, threshold_max, threshold_min);
            }
            //----------------------------------------------------------------------------------//

            //---------------------------------MONITORAMENTO------------------------------------//
            gera_log(objname,"Main: Iniciando monitoramento...");
            int tempo; //tempo decorrido
            int cont = 1; //contador de verificações
            long time0 = System.currentTimeMillis(); //tempo inicial
            long timen;
            
            while (monitoring){
                timen = System.currentTimeMillis();
                tempo = (int) ((timen - time0)/1000);
                gera_log(objname,"Main: " + cont + " Time: " + tempo + "s");
                gera_log(objname,"Main: Sincronizando hosts...");
                cloud_manager.syncData();                               //synchronize data of the cloud
                grafico.update(cont, cloud_manager.getUsedCPU(), cloud_manager.getAllocatedCPU(), cloud_manager.getAllocatedCPU() * threshold_max, cloud_manager.getAllocatedCPU() * threshold_min);
                gera_log(objname,"Main|monitora: Soma da carga de cpu de todos os hosts: " + cloud_manager.getUsedCPU() + " / Threshold maximo estabelecido: " + cloud_manager.getAllocatedCPU() * threshold_max + " / Threshold minimo estabelecido: " + cloud_manager.getAllocatedCPU() * threshold_min);
                export_log(cont, tempo, cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * threshold_max, cloud_manager.getAllocatedCPU() * threshold_min, cloud_manager.getLastMonitorTimes());
                gera_log(objname,"Main: Realiza verificação de alguma violação dos thresholds...");
                if (!cloud_manager.isWaiting()){// if we are not waiting for new resource allocation we can evaluate the cloud
                    if (evaluator.evaluate(cloud_manager.getCPULoad())){//analyze the cloud situation and if we have some violation we need deal with this
                        //here we need deal with the violation
                        if (evaluator.isHighAction()){//if we have a violation on the high threshold
                            gera_log(objname,"Main: Avaliador detectou alta carga...Verificando se SLA está no limite...");
                            evaluator.reset(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                            if(sla.canIncrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can increase resources
                                gera_log(objname,"Main: SLA não atingido...novo recurso pode ser alocado...");
                                gera_log(objname,"Main: Alocando recursos...");
                                cloud_manager.increaseResources(); //increase one host and the number of vms informed in the parameters
                            } else {gera_log(objname,"Main: SLA no limite...nada pode ser feito...");}
                        } else if (evaluator.isLowAction()){ //if we have a violation on the low threshold
                            gera_log(objname,"Main: Avaliador detectou baixa carga...Verificando se SLA está no limite...");
                            evaluator.reset(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                            if(sla.canDecrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can decrease resources
                                gera_log(objname,"Main: SLA não atingido...novo recurso pode ser liberado...");
                                gera_log(objname,"Main: Liberando recursos...");
                                cloud_manager.decreaseResources(); //decrease the last host added and the number its vms
                            } else {gera_log(objname,"Main: SLA no limite...nada pode ser feito...");}
                        } else {gera_log(objname,"Main: Evaluator problem. We have violation but we do not know which.");}
                    } else {
                        gera_log(objname,"Main: Nenhum problema detectado pelo avaliador.");
                    }                    
                } else {
                        gera_log(objname,"Main: Aguardando inicialização de recursos.");
                }
                
                gera_log(objname,"Main: Aguarda intervalo de tempo...");
                Thread.sleep(intervalo);
                cont++;
            }
            
            gera_log(objname,"Monitoramento finalizado.");

        } catch (Exception e) {
            gera_log(objname,e.getMessage());
        }
    }

    //stop the monitoring of the cloud
    public boolean stop() {
        monitoring = false;
        return !monitoring;
    }
    
    //método que redimensiona o gráfico. é chamada sempre que janela redimensionar
    void resize_grafico() {
        grafico.resize();
    }
    
    //método para geração do log
    public static void gera_log(String name, String texto){
        System.out.println(texto);
        log.append(name + ": " + texto + "\n");
        log.setCaretPosition(log.getText().length());
    }
    
    //método para geração do arquivo de log
    private static void export_log(int tempo, int time, int num_hosts, float tot_cpu_dis, float tot_cpu_usa, float tot_mem_dis, float tot_mem_usa, double th_max, double th_min, String extra_info){
        File arquivo = new File(logspath + "autoelastic" + logtime + ".csv");
        try (
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            escritor.append(tempo + "," + time + "," + num_hosts + "," + tot_cpu_dis + "," + tot_cpu_usa + "," + tot_mem_dis + "," + tot_mem_usa + "," + th_max + "," + th_min + extra_info + "\n");
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(AutoElastic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
