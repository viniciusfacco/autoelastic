package autoelastic;

import communication.SSHClient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
 * 11/08/2015 - viniciusfacco
 *            - method set_parameters ajusted to receive the name of the log
 *            - created a new boolean in the monitoring method and ajusted the logic when new resources are not online
 *            - added SSHClient object to the cloud manager in the inicialize method
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
    private static String logtitle;
    private volatile boolean monitoring;
    private static String[] iphosts;
    private static String image_manager;
    private static String virtual_machine_manager;
    private static String virtual_network_manager;
    private static int cluster_id;
    
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
     * @param plogpath - path to save the logs
     * @param plogname - name of the file to save the log
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
                       String plogpath,
                       String plogname,
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
        logspath = plogpath;
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
        logtitle = plogname;
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
            monitoring();//start monitoring
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
    private static void export_log(int contador, int time, long timemilis, int num_hosts, float tot_cpu_dis, float tot_cpu_usa, float tot_mem_dis, float tot_mem_usa, double th_max, double th_min, float load, float calcutated_load, float lowert, float uppert, String extra_info){
        File arquivo = new File(logspath + "autoelastic" + logtitle + ".csv");
        try (
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            escritor.append(contador + ";" + time + ";" + timemilis + ";" + num_hosts + ";" + tot_cpu_dis + ";" + tot_cpu_usa + ";" + tot_mem_dis + ";" + tot_mem_usa + ";" + th_max + ";" + th_min + ";" + load + ";" + calcutated_load + ";" + lowert + ";" + uppert + ";" + extra_info + "\n");
            escritor.close();
        } catch (IOException ex) { 
            Logger.getLogger(AutoElastic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void monitoring() throws ParserConfigurationException, SAXException, IOException, InterruptedException, Exception {
        boolean resourcesPending;                   //flag to inform if the system are waiting for new resources
        int time;                                   //elapsed time
        int cont = 0;                               //counter of verifications
        long timeLoop;                              //time to execute the entire loop
        long timen;                                 //current time         
        long time0 = System.currentTimeMillis();    //initial time
        while (monitoring){     
            timen = System.currentTimeMillis();
            cont++;
            time = (int) ((timen - time0)/1000);
            /*LOG*/gera_log(objname,"Main: " + cont + " Time: " + time + "s" + " | " + timen);
            /*LOG*/gera_log(objname,"Main: Sincronizando hosts...");
            resourcesPending = cloud_manager.newResourcesPending();
            cloud_manager.syncData(); //synchronize data of the cloud
            thresholds.calculateThresholds(cloud_manager.getCPULoad()); //recalculate the thresholds
            /*GRA*/graphic1.update(cont, cloud_manager.getUsedCPU(), cloud_manager.getAllocatedCPU(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold());
            /*GRA*/graphic2.update(cont, cloud_manager.getCPULoad(), 1, thresholds.getUpperThreshold(), thresholds.getLowerThreshold());
            /*LOG*/gera_log(objname,"Main|monitora: Soma da carga de cpu de todos os hosts: " + cloud_manager.getUsedCPU() + " / Threshold maximo estabelecido: " + cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold() + " / Threshold minimo estabelecido: " + cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold());
            /*LOG*/gera_log(objname,"Main: Realiza verificação de alguma violação dos thresholds...");
            evaluator.computeLoad(cloud_manager.getCPULoad());
            if ((evaluator.evaluate(thresholds.getUpperThreshold(), thresholds.getLowerThreshold())) && (!resourcesPending)){
                //analyze the cloud situation and if we have some violation we need deal with this and if we are not waiting for new resource allocation we can evaluate the cloud
                /*LOG*/export_log(cont, time, System.currentTimeMillis(), cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold(), cloud_manager.getCPULoad(), evaluator.getDecisionLoad(), thresholds.getLowerThreshold(), thresholds.getUpperThreshold(), cloud_manager.getLastMonitorTimes());
                if (evaluator.isHighAction()){//if we have a violation on the high threshold
                    /*LOG*/gera_log(objname,"Main: Avaliador detectou alta carga...Verificando se SLA está no limite...");
                    evaluator.resetFlags(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                    thresholds.resetThresholds(); //reset the thresholds
                    if(sla.canIncrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can increase resources
                        /*LOG*/gera_log(objname,"Main: SLA não atingido...novo recurso pode ser alocado...");
                        /*LOG*/gera_log(objname,"Main: Alocando recursos...");
                        cloud_manager.increaseResources(); //increase one host and the number of vms informed in the parameters
                    } else {
                        /*LOG*/gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                    }
                } else if (evaluator.isLowAction()){ //if we have a violation on the low threshold
                    /*LOG*/gera_log(objname,"Main: Avaliador detectou baixa carga...Verificando se SLA está no limite...");
                    evaluator.resetFlags(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                    thresholds.resetThresholds(); //reset the thresholds
                    if(sla.canDecrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can decrease resources
                        /*LOG*/gera_log(objname,"Main: SLA não atingido...novo recurso pode ser liberado...");
                        /*LOG*/gera_log(objname,"Main: Liberando recursos...");
                        cloud_manager.decreaseResources(); //decrease the last host added and the number its vms
                    } else {
                        /*LOG*/gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                    }
                } else {
                        /*LOG*/gera_log(objname,"Main: Evaluator problem. We have violation but we do not know which.");
                }
            } else {
                /*LOG*/gera_log(objname,"Main: Aguardando inicialização de recursos ou aguardando inicialização de recursos.");
                /*LOG*/export_log(cont, time, System.currentTimeMillis(), cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold(), cloud_manager.getCPULoad(), evaluator.getDecisionLoad(), thresholds.getLowerThreshold(), thresholds.getUpperThreshold(), cloud_manager.getLastMonitorTimes());
            }                    
            /*LOG*/gera_log(objname,"Main: Aguarda intervalo de tempo...");
            timeLoop = System.currentTimeMillis() - timen;
            if (timeLoop < intervalo){
                Thread.sleep(intervalo - timeLoop);
            }
        }
    }

    //inicialize all objects and parameters
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
        cloud_manager.setSSHClient(new SSHClient(frontend, usuario, senha));
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
        export_log(0,0,0,0,0,0,0,0,0,0,0,0,0,0,"Contador,Tempo,Tempo Milisegundos,Total Hosts Ativos,Total CPU Alocada,Total CPU Usada,Total RAM Alocada,Total RAM Usada,CPU Limite Superior,CPU Limite Inferior,% Carga de CPU,Load Calculado,Threshold Inferior,Threshold Superior,Tempos de Monitoramento");
    }
    
    /**
     * Method to run more than one executions with the application in cloud with different configurations.
     * @param srv
     * @param usr
     * @param pwd
     * @param sla
     * @param hosts
     * @param lg
     * @throws InterruptedException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws Exception
     */
    public void startLabMode(String srv, String usr, String pwd, String sla, String[] hosts, JTextArea lg) throws InterruptedException, IOException, ParserConfigurationException, SAXException, Exception{
        
        SSHClient ssh = new SSHClient(srv, usr, pwd);
        String ip_vm_master = "10.210.14.65";//VM que vai rodar mestre e slave inicial. Processos devem ser iniciados manualmente aqui.
        String server_message_start = "appstarted";
        String server_message_stop = "appstoped";
        String autoelastic_message_start = "startapp";
        String master_command;
        String localdir_temp_files = "C:\\temp\\autoelastic\\";
        String remotedir_message = "/var/lib/one/app/msg/";
        String remotedir_logs = "/one/app/logs/"; //diretorio que o mestre irá utilizar para salvar os logs
        
        String times; //string que receberá os tempos do monitoramento para salvar em um log
        
        File arquivo; //usaremos para manipular arquivos de logs
        BufferedWriter escritor; //usaremos para manipular arquivos de logs
        
        AutoElastic.frontend = srv;
        AutoElastic.usuario = usr;
        AutoElastic.senha = pwd;
        AutoElastic.slapath = sla;
        AutoElastic.log = lg;
        AutoElastic.iphosts = hosts;        
        AutoElastic.logspath = "C:\\Temp\\autoelastic\\";
        AutoElastic.vmtemplateid = 3;
        AutoElastic.intervalo = 15 * 1000;
        AutoElastic.num_vms = 2;
        AutoElastic.viewsize = 6;
        AutoElastic.evaluatortype = "full_aging";
        AutoElastic.thresholdtype = "static";
        AutoElastic.image_manager = "kvm";
        AutoElastic.virtual_machine_manager = "kvm";
        AutoElastic.virtual_network_manager = "dummy";
        AutoElastic.cluster_id = 0;

        inicialize();
        cloud_manager.setSSHClient(ssh);
        //int initial_hosts = 2;
        int initial_hosts = 1;
        int minimum_hosts = 1;
        boolean letsgo = false;
        String[] apps = {"asc","des","con","wav"};//cargas que serao testadas
        int[] upperthresholds = {70,75,80,85,90};//thresholds que serao testados
        int[] lowerthresholds = {30,35,40,45,50};//thresholds que serao testados
        for (String app : apps) {
            for (int uthreshold : upperthresholds){
                for (int lthreshold : lowerthresholds){
                    if (lthreshold <= uthreshold){//não posso fazer execuções em que threshold inferior é maior que o superior
                        //seto os parametros dessa execução
                        AutoElastic.logtitle = thresholdtype + "-" + app + "UT" + uthreshold + "LT" + lthreshold;
                        System.out.println("Log: " + AutoElastic.logtitle);
                        AutoElastic.uppert = (float) uthreshold/100;
                        AutoElastic.lowert = (float) lthreshold/100;

                        export_log(0,0,0,0,0,0,0,0,0,0,0,0,0,0,"Contador;Tempo;Tempo Milisegundos;Total Hosts Ativos;Total CPU Alocada;Total CPU Usada;Total RAM Alocada;Total RAM Usada;CPU Limite Superior;CPU Limite Inferior;% Carga de CPU;Load Calculado;Threshold Inferior;Threshold Superior;Tempos de Monitoramento");
                        thresholds.reset(uppert, lowert);
                        evaluator.reset();

                        //tenho que verificar se não tenho recursos sendo iniciados da execução anterior
                        if(cloud_manager.newResourcesPending()){
                            System.out.println("Ainda tem recursos sendo iniciados da execução anterior.");
                            while(cloud_manager.newResourcesPending()){}//fico aqui enquanto não estiverem online
                            ssh.deleteFile("novorecurso.txt", remotedir_message);//agora deleto o arquivo que o manager enviou para avisar dos novos recursos
                        }
                            
                        //volto para um host que é o mínimo
                        while(cloud_manager.getTotalActiveHosts() > minimum_hosts){
                            cloud_manager.decreaseResourcesHard();//removo um
                        }

                        //usado somente se quantidade inicial de hosts é maior que a quantidade mínima definida pelo SLA
                        //while (!letsgo){//para eu prosseguir primeiro tenho que estar com a quantidade de vms 
                        //    if (cloud_manager.getTotalActiveHosts() > initial_hosts){//se eu tiver mais hosts que o inicial
                        //        cloud_manager.decreaseResourcesHard();//removo um
                        //    } else if (cloud_manager.getTotalActiveHosts() < initial_hosts){//agora se eu tiver menos hosts que o inicial
                        //        cloud_manager.increaseResources();//eu adiciono mais um
                        //    }
                        //    if (!(cloud_manager.getTotalActiveHosts() == initial_hosts)){ //se a quantidade é igual já posso continuar
                        //        while (cloud_manager.newResourcesPending()){//se não, vejo se tem recursos sendo alocados
                        //            Thread.sleep(1000);
                        //            //coloquei esse while pq se eu tiver 
                        //            //que alocar mais de um recurso para chegar a quantidade ideal, 
                        //            //vou alocar um de cada vez, sendo que só proseguirei quando as 
                        //            //vms do anterior já estiverem online. isso pois nesse momento 
                        //            //o mestre já estará esperando conexões e quero garantir que 
                        //            //elas aconteçam na ordem
                        //        } 
                        //    } else letsgo = true; //se a quantidade já for igual posso prosseguir mesmo se eu tiver vms sendo alocadas
                        //}
                        //letsgo = false;

                        //depois disso estarão rodando apenas os slaves e terei que rodar o master
                        System.out.println("Quantidade de hosts inicial atingida.");

                        //crio o comando para rodar a aplicação com esses parâmetros
                        master_command = "/one/app/scripts/master.sh " 
                                + AutoElastic.logtitle  
                                //#1 titulo do log
                                + " " 
                                + ip_vm_master          
                                //#2 ip do master
                                + " " 
                                + app                   
                                //#3 aplicação
                                + " "
                                + 2               
                                //#4 quantidade inicial de vms
                                + " "
                                + remotedir_logs + AutoElastic.thresholdtype + "/" + app + "/u" + uthreshold + "/l" + lthreshold + "/";
                                //#5 diretorio que o mestre vai salvar o log
                        //crio o arquivo para o bash ler e rodar o comando de dentro dele
                        System.out.println("Comando de inicialização da aplicação: " + master_command);

                        arquivo = new File(localdir_temp_files + autoelastic_message_start);
                        escritor = new BufferedWriter(new FileWriter(arquivo));
                        escritor.write(master_command);
                        escritor.close();

                        ssh.sendFile(arquivo.getAbsolutePath(), remotedir_message);
                        System.out.println("Arquivo de inicialização enviado.");

                        //agora tenho que ver se posso iniciar o monitoramento
                        //só irei monitorar quando o mestre iniciar o processamento                    
                        while (!ssh.fileExists(server_message_start, remotedir_message)){
                            System.out.println("Aguardando inicialização da aplicação...");
                            Thread.sleep(500);
                        }

                        //aqui vamos colocar dentro do arquivo de alocações um marcador de que uma nova execução está iniciando. Esse marcador vai ser o nome da execução que é o nome do outro log que é gerado
                        arquivo = new File("C:\\temp\\autoelastic\\autoelastic_resource_operation.csv");
                        escritor = new BufferedWriter(new FileWriter(arquivo, true));
                        escritor.append(System.currentTimeMillis() + ";INI " + AutoElastic.logtitle + "\n");
                        escritor.close();

                        System.out.println("###############Aplicação iniciada###############");
                        times = this.monitoring(ssh, server_message_stop, remotedir_message);
                        System.out.println("###############Aplicação finalizada###############");

                        //aqui vamos escrever dentro do arquivo de alocações que a execução terminou
                        arquivo = new File("C:\\temp\\autoelastic\\autoelastic_resource_operation.csv");
                        escritor = new BufferedWriter(new FileWriter(arquivo, true));
                        escritor.append(System.currentTimeMillis() + ";FIM " + AutoElastic.logtitle + "\n");
                        escritor.close();

                        //deletamos o arquivo que informa que a aplicação iniciou
                        ssh.deleteFile(server_message_start, remotedir_message);

                        //vamos salvar o log com os tempos do AutoElastic
                        arquivo = new File("C:\\temp\\autoelastic\\Tempos-" + AutoElastic.logtitle + ".csv");
                        escritor = new BufferedWriter(new FileWriter(arquivo, true));
                        escritor.append(times);
                        escritor.close();
                    }
                }
            }
        }
        System.out.println("FIM!");
    }

    //monitoring almost equal the other  monitoring(), but here the ssh is used to know when stoip the monitoring
    private String monitoring(SSHClient ssh, String message, String remotedir) throws InterruptedException, IOException, Exception {
        int tempo; //tempo decorrido
        int cont = 0; //contador de verificações
        long timeLoop; //usarei essa variavel para calcular o tempo total para realizar o loop
        boolean resourcesPending = false;
        byte recalculate_thresholds = 0;
        float load_before = 0, load_after; //loads before and after delivery new resources
        String times = "Contador;T1-InicioLoop;T2-AntesDeSincronizar;T3-AposSincronizar&AntesDeCalcularThresholds;T4-AposCalcularThresholds;T5-AntesDeAvaliarCarga;T6-AposAvaliarCarga;T7-AntesDeAlocar;T8-AposAlocar;T9-AntesDeDesalocar;T10-AposDesalocar;T11-AntesDeVerificarRecursosPendentes;T12-AposVerificarRecursosPendentes&FimLoop;Sincronização;CalculoThresholds;AvaliaçãoCarga;Alocação;Desalocação;VerificaRecursosPendentes;Loop";
        long time0 = System.currentTimeMillis(); //tempo inicial
        long timen = System.currentTimeMillis(); //primeiro tempo antes de iniciar o loop, apos isso esse tempo vai ser coletado no final após o sleep
        while (!ssh.fileExists(message, remotedir)){//while do not exists a message to stop the monitoring we keep going            
            cont++;
            times = times + "\n" + cont + ";" + timen; //T1-InicioLoop            
            tempo = (int) ((timen - time0)/1000);
            System.out.println("Main: " + cont + " Time: " + tempo + "s");
            ///*LOG*/gera_log(objname,"Main: " + cont + " Time: " + tempo + "s");
            ///*LOG*/gera_log(objname,"Main: Sincronizando hosts...");
            times = times + ";" + System.currentTimeMillis(); //T2-AntesDeSincronizar
            cloud_manager.syncData(); //synchronize data of the cloud
            times = times + ";" + System.currentTimeMillis(); //T3-AposSincronizar&AntesDeCalcularThresholds
            thresholds.calculateThresholds(cloud_manager.getCPULoad()); //recalculate the thresholds            
            times = times + ";" + System.currentTimeMillis(); //T4-AposCalcularThresholds
            //*GRA*/graphic1.update(cont, cloud_manager.getUsedCPU(), cloud_manager.getAllocatedCPU(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold());
            //*GRA*/graphic2.update(cont, cloud_manager.getCPULoad(), 1, thresholds.getUpperThreshold(), thresholds.getLowerThreshold());
            ///*LOG*/gera_log(objname,"Main|monitora: Carga do ambiente: " + cloud_manager.getCPULoad() + " / Threshold superior: " + thresholds.getUpperThreshold() + " / Threshold inferior: " + thresholds.getLowerThreshold());
            ///*LOG*/gera_log(objname,"Main: Realiza verificação de alguma violação dos thresholds...");
            times = times + ";" + System.currentTimeMillis(); //T5-AntesDeAvaliarCarga
            evaluator.computeLoad(cloud_manager.getCPULoad());
            if (recalculate_thresholds > 0){//se essa flag foi maior que 0 então devo recalcular os thresholds (faço isso após a sincronização)
                thresholds.resetThresholds(); //trago os thresholds para seus valores originais
                load_after = evaluator.getDecisionLoad();//pego o novo load já com os novos recursos
                switch (recalculate_thresholds){
                    case 1://significa que a violação foi no threshold superior
                        //thresholds.recalculateUpperThreshold(1, load_after, load_after); //implementação 1
                        if (load_before > load_after){ //implementação 2
                            thresholds.recalculateUpperThreshold(load_before, load_after, load_before); 
                        } else {
                            thresholds.recalculateUpperThreshold(load_after, load_before, load_before); 
                        }
                        break;
                    case 2://significa que a violação foi no threshold inferior
                        thresholds.recalculateLowerThreshold(load_after);
                }
                recalculate_thresholds = 0;
            }
            if ((evaluator.evaluate(thresholds.getUpperThreshold(), thresholds.getLowerThreshold())) && (!resourcesPending)){
                //analyze the cloud situation and if we have some violation we need deal with this and if we are not waiting for new resource allocation we can evaluate the cloud
                times = times + ";" + System.currentTimeMillis(); //T6-AposAvaliarCarga
                /*LOG*/export_log(cont, tempo, System.currentTimeMillis(), cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold(), cloud_manager.getCPULoad(), evaluator.getDecisionLoad(), thresholds.getLowerThreshold(), thresholds.getUpperThreshold(), cloud_manager.getLastMonitorTimes());
                //here we need deal with the violation
                if (evaluator.isHighAction()){//if we have a violation on the high threshold
                    ///*LOG*/gera_log(objname,"Main: Avaliador detectou alta carga...Verificando se SLA está no limite...");
                    evaluator.resetFlags(); //after deal with the problem/violation, re-initialize the parameters of evaluation
                    if(sla.canIncrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can increase resources
                        ///*LOG*/gera_log(objname,"Main: SLA não atingido...novo recurso pode ser alocado...");
                        ///*LOG*/gera_log(objname,"Main: Alocando recursos...");
                        times = times + ";" + System.currentTimeMillis(); //T7-AntesDeAlocar
                        cloud_manager.increaseResources(); //increase one host and the number of vms informed in the parameters
                        resourcesPending = true;
                        times = times + ";" + System.currentTimeMillis() + ";;"; //T8-AposAlocar + T9 e T10 vazios
                    } else {
                        times = times + ";;;;"; //T7 T8 T9 e T10 vazios
                        ///*LOG*/gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                    }
                } else if (evaluator.isLowAction()){ //if we have a violation on the low threshold
                    ///*LOG*/gera_log(objname,"Main: Avaliador detectou baixa carga...Verificando se SLA está no limite...");
                    evaluator.resetFlags(); //after deal with the problem/violation, re-initialize the parameters of evaluation                    
                    if(sla.canDecrease(cloud_manager.getTotalActiveHosts())){ //verify the SLA to know if we can decrease resources
                        ///*LOG*/gera_log(objname,"Main: SLA não atingido...novo recurso pode ser liberado...");
                        ///*LOG*/gera_log(objname,"Main: Liberando recursos...");
                        times = times + ";;;" + System.currentTimeMillis(); //T7 e T8 vazios + T9-AntesDeDesalocar
                        cloud_manager.decreaseResources(); //decrease the last host added and the number its vms
                        recalculate_thresholds = 2;
                        times = times + ";" + System.currentTimeMillis(); //T10-AposDesalocar
                    } else {
                        times = times + ";;;;"; //T7 T8 T9 e T10 vazios
                        ///*LOG*/gera_log(objname,"Main: SLA no limite...nada pode ser feito...");
                    }
                } else {
                        ///*LOG*/gera_log(objname,"Main: Evaluator problem. We have violation but we do not know which.");
                }
            } else {
                times = times + ";" + System.currentTimeMillis() + ";;;;"; //T6-AposAvaliarCarga + T7 T8 T9 e T10 vazios
                /*LOG*/export_log(cont, tempo, System.currentTimeMillis(), cloud_manager.getTotalActiveHosts(), cloud_manager.getAllocatedCPU(), cloud_manager.getUsedCPU(), cloud_manager.getAllocatedMEM(), cloud_manager.getUsedMEM(), cloud_manager.getAllocatedCPU() * thresholds.getUpperThreshold(), cloud_manager.getAllocatedCPU() * thresholds.getLowerThreshold(), cloud_manager.getCPULoad(), evaluator.getDecisionLoad(), thresholds.getLowerThreshold(), thresholds.getUpperThreshold(), cloud_manager.getLastMonitorTimes());
                ///*LOG*/gera_log(objname,"Main: Nenhum problema detectado pelo avaliador ou aguardando vms.");
            }
            times = times + ";" + System.currentTimeMillis(); //T11-AntesVerificarRecursosPendentes
            if (resourcesPending){//se tenho recursos pendentes, entao devo verificar se eles ja estao online para eu adicionalos e recalcular os thresholds
                load_before = evaluator.getDecisionLoad();
                resourcesPending = cloud_manager.newResourcesPending(); //verifico no final antes do sleep se tenho que entregar mais recursos, dessa maneira esses recursos só serão analisados na próxima observação
                if (!resourcesPending){//se agora eles não estão mais pendentes é porque ficaram online, tenho que recalcular os thresholds no início da próxima observação
                    recalculate_thresholds = 1;
                }
            }            
            times = times + ";" + System.currentTimeMillis(); //T12-AposVerificarRecursosPendentes&FimLoop
            timeLoop = System.currentTimeMillis() - timen; //pego o tempo que cheguei até aqui e calculo o tempo após o sleep (inicio do processamento)
            if (timeLoop < intervalo){
                Thread.sleep(intervalo - timeLoop);
            }
            timen = System.currentTimeMillis();//acordei do sleep e vou começar a processar, então pego esse tempo
        }
        ssh.deleteFile(message, remotedir);
        return times;
    }
}
