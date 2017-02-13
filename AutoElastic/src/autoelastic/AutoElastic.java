package autoelastic;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Auto Elastic
 * @author viniciusfacco
 * 13/02/2017 - viniciusfacco
 *            - class creation to run command line mode without UI
 */
public class AutoElastic {
    
    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                //    break;
                //}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AutoElasticUserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoElasticUserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoElasticUserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoElasticUserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        if (args.length > 0){
            System.out.println("Starting command line mode.");
            startCommandLineOnly(args[0]);
        } else {
            System.out.println("Starting graphical mode.");
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new AutoElasticUserInterface().setVisible(true);
                }
            });
        }
    }
    
    public static void startCommandLineOnly(String config_file){
                
        AutoElasticManager autoelastic_manager = new AutoElasticManager(true);
        
        File file = new File(config_file);
        if (!config_file.equalsIgnoreCase("") && file.exists()){
            try {
                File arquivo = new File(config_file);
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(arquivo);
                doc.getDocumentElement().normalize();

                Element el;
                NodeList nl;
                
                //============================================================================
                //firstly we populate the fields
                //server
                String Frontend             = doc.getElementsByTagName("FRONTEND_ADDRESS").item(0).getChildNodes().item(0).getNodeValue().trim();
                String FrontEndPort         = doc.getElementsByTagName("FRONTEND_PORT").item(0).getChildNodes().item(0).getNodeValue().trim();
                String FrontEndUser         = doc.getElementsByTagName("FRONTEND_USER").item(0).getChildNodes().item(0).getNodeValue().trim();
                String FrontEndPassword     = doc.getElementsByTagName("FRONTEND_PWD").item(0).getChildNodes().item(0).getNodeValue().trim();
                String ClusterId            = doc.getElementsByTagName("CLUSTER_ID").item(0).getChildNodes().item(0).getNodeValue().trim();
                String IM                   = doc.getElementsByTagName("IM").item(0).getChildNodes().item(0).getNodeValue().trim();
                String VNM                  = doc.getElementsByTagName("VNM").item(0).getChildNodes().item(0).getNodeValue().trim();
                String VMM                  = doc.getElementsByTagName("VMM").item(0).getChildNodes().item(0).getNodeValue().trim();
                //parameters
                String Sla                  = doc.getElementsByTagName("SLA").item(0).getChildNodes().item(0).getNodeValue().trim();
                String LogPath              = doc.getElementsByTagName("LOG_PATH").item(0).getChildNodes().item(0).getNodeValue().trim();
                String ExecutionLogName     = doc.getElementsByTagName("EXEC_LOG").item(0).getChildNodes().item(0).getNodeValue().trim();
                String Templateid           = doc.getElementsByTagName("TEMPLATE_ID").item(0).getChildNodes().item(0).getNodeValue().trim();
                String MonitoringInterval   = doc.getElementsByTagName("MON_INTERVAL").item(0).getChildNodes().item(0).getNodeValue().trim();
                String VmsPorHost           = doc.getElementsByTagName("NUM_VMS").item(0).getChildNodes().item(0).getNodeValue().trim();
                String ThresholdMax         = doc.getElementsByTagName("UPPER_THRESHOLD").item(0).getChildNodes().item(0).getNodeValue().trim();
                String ThresholdMin         = doc.getElementsByTagName("LOWER_THRESHOLD").item(0).getChildNodes().item(0).getNodeValue().trim();
                String MonitoringWindow     = doc.getElementsByTagName("MON_WINDOW").item(0).getChildNodes().item(0).getNodeValue().trim();
                String CoolDown             = doc.getElementsByTagName("COOL_DOWN").item(0).getChildNodes().item(0).getNodeValue().trim();
                String ThresholdType        = doc.getElementsByTagName("THRESHOLD_TYPE").item(0).getChildNodes().item(0).getNodeValue().trim();
                String Evaluators           = doc.getElementsByTagName("EVALUATION_ALGORITHM").item(0).getChildNodes().item(0).getNodeValue().trim();
                boolean LabMode             = doc.getElementsByTagName("LAB_MODE").item(0).getChildNodes().item(0).getNodeValue().trim().equalsIgnoreCase("true");
                boolean ReadOnly            = doc.getElementsByTagName("READ_ONLY").item(0).getChildNodes().item(0).getNodeValue().trim().equalsIgnoreCase("true");
                boolean ManageHosts         = doc.getElementsByTagName("MANAGE_HOSTS").item(0).getChildNodes().item(0).getNodeValue().trim().equalsIgnoreCase("true");
                //communication
                String SSHServer            = doc.getElementsByTagName("DATA_SERVER_ADDRESS").item(0).getChildNodes().item(0).getNodeValue().trim();
                String SSHPort              = doc.getElementsByTagName("DATA_SERVER_PORT").item(0).getChildNodes().item(0).getNodeValue().trim();
                String SSHUser              = doc.getElementsByTagName("DATA_SERVER_USER").item(0).getChildNodes().item(0).getNodeValue().trim();
                String SSHPassword          = doc.getElementsByTagName("DATA_SERVER_PWD").item(0).getChildNodes().item(0).getNodeValue().trim();
                String MsgWarningRemove     = doc.getElementsByTagName("MSG_WARNING_REMOVE").item(0).getChildNodes().item(0).getNodeValue().trim();
                String MsgPermissionRemove  = doc.getElementsByTagName("MSG_PERMISSION_REMOVE").item(0).getChildNodes().item(0).getNodeValue().trim();
                String MsgNewResources      = doc.getElementsByTagName("MSG_NOTIFICATION_NEW_RESOURCES").item(0).getChildNodes().item(0).getNodeValue().trim();
                String RemoteDirSource      = doc.getElementsByTagName("DIR_MSG_SOURCE").item(0).getChildNodes().item(0).getNodeValue().trim();
                String RemoteDirTarget      = doc.getElementsByTagName("DIR_MSG_TARGET").item(0).getChildNodes().item(0).getNodeValue().trim();
                String LocalDirTemp         = doc.getElementsByTagName("DIR_MSG_LOCAL").item(0).getChildNodes().item(0).getNodeValue().trim();
                //hosts
                String[] hosts = new String[doc.getElementsByTagName("HOST").getLength()];
                for (int i = 0; i < doc.getElementsByTagName("HOST").getLength(); i++){
                    hosts[i] = doc.getElementsByTagName("HOST").item(i).getChildNodes().item(0).getNodeValue().trim();
                }
                //============================================================================
                //now we execute
                    
                //seta parametros do gerenciador
                autoelastic_manager.set_parameters(Frontend,
                        FrontEndUser,
                        FrontEndPassword,
                        Sla,
                        LogPath,
                        ExecutionLogName,
                        Integer.parseInt(Templateid),
                        Integer.parseInt(MonitoringInterval),
                        Double.parseDouble(ThresholdMax) / 100,
                        Double.parseDouble(ThresholdMin) / 100,
                        Integer.parseInt(VmsPorHost),
                        Evaluators,
                        ThresholdType,
                        Integer.parseInt(MonitoringWindow),
                        hosts,
                        IM,
                        VMM,
                        VNM,
                        Integer.parseInt(ClusterId),
                        SSHServer,
                        SSHUser,
                        SSHPassword,
                        MsgWarningRemove,
                        MsgPermissionRemove,
                        MsgNewResources,
                        LocalDirTemp,
                        RemoteDirSource,
                        RemoteDirTarget,
                        null,
                        ReadOnly,
                        ManageHosts,
                        Integer.parseInt(CoolDown),
                        Integer.parseInt(FrontEndPort),
                        Integer.parseInt(SSHPort)
                );
                //coloco o gerenciador dentro de uma Thread e inicio ele
                //th_gerenciador = new Thread(autoelastic_manager);
                //th_gerenciador.start();
                autoelastic_manager.run();
                //============================================================================
            } catch (IOException | ParserConfigurationException | SAXException ex){
                Logger.getLogger(AutoElasticManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error loading XML file:" + ex.getMessage());
                System.exit(1);
            }
        } else {
            System.out.println("Input configuration file does not exist: " + file.getAbsolutePath());
            System.exit(1);
        }
    }
    
}
