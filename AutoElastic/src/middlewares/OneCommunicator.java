/*
 * 25/04/2014
 * OneCommunicator
 * - Classe destinada a realizar a comunicação de eventos de elasticidade
 */

package middlewares;

import autoelastic.AutoElastic;
import static autoelastic.AutoElastic.gera_log;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import communication.SSHClient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author viniciusfacco
 * 06/08/2015 - viniciusfacco
 *            - added a new ssh object to manage the shared file system.
 *            - updated methos to use the new ssh object and removed old methods
 * 16/11/2016 - viniciusfacco
 *            - added method setParameters to set local and remote directories and name of the communication files
 * 03/01/2017 - viniciusfacco
 *            - added parameter "message" in the notifyDecrease method
 */

public class OneCommunicator {
    
    private final String objname = "middlewares.OneComunicator"; //name of the class to be used in the logs
    private final JTextArea log;
    private final String server;
    private final String user;
    private final String password;
    private String remotedir_file_source = "/var/lib/one/app/msg/";
    private String remotedir_file_target = "/var/lib/one/app/msg/";
    private String permission_decrease_file_name = "liberarecurso.txt";
    private String warning_deacrease_file_name = "poucacarga.txt";
    private String notify_increase_file_name = "novorecurso.txt";
    private String localdir_temp_files = "C:\\temp\\autoelastic\\";
    private SSHClient ssh;
    
    public OneCommunicator(String pserver, String puser, String ppassword, JTextArea plog){
        server = pserver;
        user = puser;
        password = ppassword;
        log = plog;
        ssh = null;
    }
    
    //set parameters related with communication
    public void setParameters(
                        String pmsgwarningremove, 
                        String pmsgcanremove, 
                        String pmsgnewresources, 
                        String plocaldirtemp, 
                        String premotedirsource, 
                        String premotedirtarget
    ){
        remotedir_file_source = premotedirsource;
        remotedir_file_target = premotedirtarget;
        warning_deacrease_file_name = pmsgwarningremove;
        permission_decrease_file_name = pmsgcanremove;
        notify_increase_file_name = pmsgnewresources;
        localdir_temp_files = plocaldirtemp;
        //gera_log(objname,
        //          "remotedir_file_source\n" + remotedir_file_source 
        //        + "remotedir_file_target\n" + remotedir_file_target
        //        + "warning_deacrease_file_name\n" + warning_deacrease_file_name
        //        + "permission_decrease_file_name\n" + permission_decrease_file_name
        //        + "notify_increase_file_name\n" + notify_increase_file_name
        //        + "localdir_temp_files\n" + localdir_temp_files
        //);
    }
    
    public void setSSHClient(SSHClient sshclient){
        ssh = sshclient;
    }
    
    
    //método que verifica se é possível liberar recursos
    public boolean notifyDecrease(String message) throws InterruptedException, IOException {
        File arquivo = new File(localdir_temp_files + warning_deacrease_file_name);
        try (
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            escritor.write(message);
            escritor.flush();
        }
        //if (envia_arquivo(arquivo.getAbsolutePath())) {
        if (ssh.sendFile(arquivo.getAbsolutePath(), remotedir_file_target)){
            //gera_log(objname,"Main|posso_liberar: Arquivo enviado com sucesso...");
            return true;
        } else {
            //gera_log(objname,"Main|posso_liberar: Arquivo não foi enviado...");
        }
        //aqui eu deveria ler o diretorio compartilhado para verificar se posso ou nao liberar
        //Thread.sleep(60000); //mas como nao implementei espero um tempo
        return false;
    }
    
    //método para verificar se arquivo de liberação foi criado no diretório no frontend
    public boolean canDecrease() {
        
        boolean libera;
        if (ssh.fileExists(permission_decrease_file_name, remotedir_file_source)){
            ssh.deleteFile(permission_decrease_file_name, remotedir_file_source);
            libera = true; //liberação pode ser realizada
            //System.out.println("Main|VerificaLiberacao: Liberação de recursos pode ser realizada...");
            //gera_log(objname,"Main|VerificaLiberacao: Liberação de recursos pode ser realizada.");
        } else {
            System.out.println("Recursos ainda não podem ser liberados.");
            //gera_log(objname,"Main|VerificaLiberacao: Recursos ainda não podem ser liberados.");
            libera = false;
        }
        return libera;
    }

    //método para enviar que cria e envia arquivo para o frontend, notificando a aplicação da criação de novo host e vm
    public boolean notifyNewResources(String file_content) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        
        gera_log(objname,"notifyNewResources: New VMs online.");
        try {
            //File arquivo = new File("C:\\temp\\one");
            //arquivo.mkdirs();
            File arquivo = new File(localdir_temp_files + notify_increase_file_name);
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo));
            escritor.write(file_content + "\n");
            escritor.flush();
            //if (envia_arquivo(arquivo.getAbsolutePath())) {
            if (ssh.sendFile(arquivo.getAbsolutePath(), remotedir_file_target)){
                //gera_log(objname,"Main|notifica: Arquivo enviado com sucesso...");
                return true;
            } else {
                //gera_log(objname,"Main|notifica: Arquivo não foi enviado...");
            }
        } catch (IOException ex) {
            Logger.getLogger(AutoElastic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /*==================================================*
     *This methods are discontinued and will be deleted *
     *==================================================*/
    //método para verificar se arquivo de liberação foi criado no diretório no frontend
    public boolean canDecreaseAntigo() {
        
        String retorno;
        retorno = "";
        boolean libera = false;
        
        try{      
            JSch jsch = new JSch();  

            Session session = jsch.getSession(user, server, 22);

            UserInfo ui = new OneFrontEndConnect(password);
            session.setUserInfo(ui);
            session.connect();

            String listar = "ls -l " + remotedir_file_source;
            String remover = "rm " + remotedir_file_source + permission_decrease_file_name;

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(listar);

            channel.setInputStream(null);

            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    retorno = new String(tmp, 0, i);
                    System.out.println(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }

            if (retorno.contains(permission_decrease_file_name)){ //se listagem conter arquivo de liberação
                channel=session.openChannel("exec");
                ((ChannelExec)channel).setCommand(remover);//removemos o arquivo
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                in = channel.getInputStream();
                channel.connect();
                libera = true; //liberação pode ser realizada
                System.out.println("Main|VerificaLiberacao: Liberação de recursos pode ser realizada...");
                //gera_log(objname,"Main|VerificaLiberacao: Liberação de recursos pode ser realizada.");
            } else {
                System.out.println("Recursos ainda não podem ser liberados.");
                //gera_log(objname,"Main|VerificaLiberacao: Recursos ainda não podem ser liberados.");
                libera = false;
            }

            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return libera;
    }
    
    //método para enviar arquivo para o frontend
    private boolean sendFile(String filepath) {

        FileInputStream fis = null;

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, server, 22);
            // username and password will be given via UserInfo interface.
            UserInfo ui = new OneFrontEndConnect(password);
            session.setUserInfo(ui);
            session.connect();
            boolean ptimestamp = true;
            // exec 'scp -t destino' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + remotedir_file_target;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            try (
                OutputStream out = channel.getOutputStream()) {
                InputStream in = channel.getInputStream();
                channel.connect();
                if (checkAck(in) != 0) {
                    gera_log(objname,"envia_arquivo: System.exit(0);");
                    return false;
                }
                File _lfile = new File(filepath);
                if (ptimestamp) {
                    //command = "T " + (_lfile.lastModified() / 1000) + " 0";
                    command = "T" + (_lfile.lastModified() / 1000) + " 0";
                    // The access time should be sent here,
                    // but it is not accessible with JavaAPI ;-<
                    command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                    out.write(command.getBytes());
                    out.flush();
                    if (checkAck(in) != 0) {
                        gera_log(objname,"envia_arquivo: System.exit(0);");
                        return false;
                    }
                }
                // send "C0644 filesize filename", where filename should not include '/'
                long filesize = _lfile.length();
                command = "C0644 " + filesize + " ";
                //realiza corte para pegar apenas o nome do arquivo
                if (filepath.lastIndexOf('\\') > 0) {
                    command += filepath.substring(filepath.lastIndexOf('\\') + 1);
                } else {
                    command += filepath;
                }
                command += "\n";
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    gera_log(objname,"envia_arquivo: System.exit(0);");
                    return false;
                }
                // send a content of arquivo
                fis = new FileInputStream(filepath);
                byte[] buf = new byte[1024];
                while (true) {
                    int len = fis.read(buf, 0, buf.length);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len); //out.flush();
                }
                fis.close();
                fis = null;
                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
                if (checkAck(in) != 0) {
                    gera_log(objname,"envia_arquivo: System.exit(0);");
                    return false;
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | IOException e) {
            gera_log(objname,e.toString());
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ee) {
            }
        }
        return true;
    }

    //método auxiliar do método envia_arquivo(String filepath)
    private int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }
        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                gera_log(objname,sb.toString());
            }
            if (b == 2) { // fatal error
                gera_log(objname,sb.toString());
            }
        }
        return b;
    }
}
