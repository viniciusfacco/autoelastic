
package autoelastic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author vinicius.rodrigues
 */
public class FAutoElastic extends javax.swing.JFrame {
    private Thread th_gerenciador;
    private AutoElastic gerenciador;
    
    public FAutoElastic() {
        initComponents();
        gerenciador = new AutoElastic(this.jpGraficoLineTotal, this.jbGraphicLinePercent);
        this.setExtendedState(MAXIMIZED_BOTH);//maximoza janela        
        variaveis_padroes();//inicializo todos os parâmetros para o padrão
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/icone64x64.png")).getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgEvaluators = new javax.swing.ButtonGroup();
        bgThresholdType = new javax.swing.ButtonGroup();
        jpMain = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpServer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfFrontend = new javax.swing.JTextField();
        jtfUsuario = new javax.swing.JTextField();
        jtfSenha = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtfIM = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfVMM = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtfVNM = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtfClusterId = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jpParameters = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfSla = new javax.swing.JTextField();
        jbBuscarSLA = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jtfLogs = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jrbAging = new javax.swing.JRadioButton();
        jrbGeneric = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jrbFixed = new javax.swing.JRadioButton();
        jrbLive = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtfThresholdMax = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtfThresholdMin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtfMonitoringWindow = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtfTemplateid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtfMonitoringInterval = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtfVmsPorHost = new javax.swing.JTextField();
        jpHosts = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtHosts = new javax.swing.JTable();
        jbAddHost = new javax.swing.JButton();
        jbDelHost = new javax.swing.JButton();
        jpGraficos = new javax.swing.JPanel();
        jpGraficoLineTotal = new javax.swing.JPanel();
        jbGraphicLinePercent = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jbLimpar = new javax.swing.JButton();
        jbExecutar = new javax.swing.JButton();
        jbParar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaLog = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiSaveLog = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AutoElastic");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImages(null);

        jpMain.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jpServer.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("FrontEnd");

        jtfFrontend.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfFrontendFocusGained(evt);
            }
        });

        jtfUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfUsuarioFocusGained(evt);
            }
        });

        jtfSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfSenhaFocusGained(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setText("User");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setText("Password");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setText("IM");

        jtfIM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfIMFocusGained(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setText("VMM");

        jtfVMM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVMMFocusGained(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setText("VNM");

        jtfVNM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVNMFocusGained(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setText("Cluster ID");

        jtfClusterId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfClusterIdFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jpServerLayout = new javax.swing.GroupLayout(jpServer);
        jpServer.setLayout(jpServerLayout);
        jpServerLayout.setHorizontalGroup(
            jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfFrontend, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfIM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfVMM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 89, Short.MAX_VALUE)
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfClusterId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfVNM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(321, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jpServerLayout.setVerticalGroup(
            jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFrontend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jtfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpServerLayout.createSequentialGroup()
                        .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jtfVNM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpServerLayout.createSequentialGroup()
                        .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jtfIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)))
                .addGroup(jpServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfClusterId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jtfVMM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Server", jpServer);

        jpParameters.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("SLA");

        jtfSla.setEditable(false);

        jbBuscarSLA.setText("...");
        jbBuscarSLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarSLAActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setText("Logs");

        jtfLogs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfLogsFocusGained(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel16.setText("Evaluation Algorithm");

        jrbAging.setBackground(new java.awt.Color(255, 255, 255));
        bgEvaluators.add(jrbAging);
        jrbAging.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jrbAging.setSelected(true);
        jrbAging.setText("Window Aging");
        jrbAging.setToolTipText("");
        jrbAging.setActionCommand("window_aging");

        jrbGeneric.setBackground(new java.awt.Color(255, 255, 255));
        bgEvaluators.add(jrbGeneric);
        jrbGeneric.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jrbGeneric.setText("Generic");
        jrbGeneric.setActionCommand("generic");

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        bgEvaluators.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioButton1.setText("Full Aging");
        jRadioButton1.setActionCommand("full_aging");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jrbAging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jrbGeneric, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbAging)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbGeneric))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel17.setText("Threshold Type");

        jrbFixed.setBackground(new java.awt.Color(255, 255, 255));
        bgThresholdType.add(jrbFixed);
        jrbFixed.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jrbFixed.setSelected(true);
        jrbFixed.setText("Static");
        jrbFixed.setActionCommand("static");

        jrbLive.setBackground(new java.awt.Color(255, 255, 255));
        bgThresholdType.add(jrbLive);
        jrbLive.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jrbLive.setText("Live");
        jrbLive.setActionCommand("live");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbLive)
                    .addComponent(jLabel17)
                    .addComponent(jrbFixed))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jrbFixed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jrbLive)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Upper Threshold");

        jtfThresholdMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfThresholdMaxFocusGained(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setText("Lower Threshold");

        jtfThresholdMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfThresholdMinFocusGained(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setText("Monitoring Window");

        jtfMonitoringWindow.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfMonitoringWindowFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfMonitoringWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jtfThresholdMin)
                    .addComponent(jtfThresholdMax))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtfThresholdMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtfThresholdMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtfMonitoringWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Slave Template ID");

        jtfTemplateid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfTemplateidFocusGained(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Monitoring Interval");

        jtfMonitoringInterval.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfMonitoringIntervalFocusGained(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setText("Virtual Machines");

        jtfVmsPorHost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVmsPorHostFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtfMonitoringInterval, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jtfTemplateid, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfVmsPorHost, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jtfTemplateid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jtfMonitoringInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(jtfVmsPorHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpParametersLayout = new javax.swing.GroupLayout(jpParameters);
        jpParameters.setLayout(jpParametersLayout);
        jpParametersLayout.setHorizontalGroup(
            jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpParametersLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel15))
                .addGap(64, 64, 64)
                .addGroup(jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpParametersLayout.createSequentialGroup()
                        .addComponent(jtfSla, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscarSLA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfLogs))
                .addContainerGap())
        );
        jpParametersLayout.setVerticalGroup(
            jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfSla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarSLA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtfLogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jTabbedPane1.addTab("Parameters", jpParameters);

        jpHosts.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setOpaque(false);

        jtHosts.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtHosts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"10.210.7.230"},
                {"10.210.7.231"},
                {"10.210.6.232"},
                {"10.210.1.88"}
            },
            new String [] {
                "Hosts"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtHosts.setOpaque(false);
        jScrollPane3.setViewportView(jtHosts);

        jbAddHost.setBackground(new java.awt.Color(51, 204, 255));
        jbAddHost.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbAddHost.setText("+");
        jbAddHost.setContentAreaFilled(false);
        jbAddHost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbAddHost.setFocusable(false);
        jbAddHost.setOpaque(true);
        jbAddHost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbAddHostMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbAddHostMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbAddHostMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbAddHostMouseReleased(evt);
            }
        });
        jbAddHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddHostActionPerformed(evt);
            }
        });

        jbDelHost.setBackground(new java.awt.Color(51, 204, 255));
        jbDelHost.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbDelHost.setText("-");
        jbDelHost.setContentAreaFilled(false);
        jbDelHost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbDelHost.setFocusable(false);
        jbDelHost.setOpaque(true);
        jbDelHost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbDelHostMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbDelHostMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbDelHostMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbDelHostMouseReleased(evt);
            }
        });
        jbDelHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDelHostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpHostsLayout = new javax.swing.GroupLayout(jpHosts);
        jpHosts.setLayout(jpHostsLayout);
        jpHostsLayout.setHorizontalGroup(
            jpHostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHostsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpHostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbDelHost, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAddHost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        jpHostsLayout.setVerticalGroup(
            jpHostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jpHostsLayout.createSequentialGroup()
                .addComponent(jbAddHost, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbDelHost, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hosts", jpHosts);

        jpGraficos.setBackground(new java.awt.Color(255, 255, 255));
        jpGraficos.setFocusable(false);
        jpGraficos.setOpaque(false);
        jpGraficos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jpGraficosLineComponentResized(evt);
            }
        });

        jpGraficoLineTotal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpGraficoLineTotalLayout = new javax.swing.GroupLayout(jpGraficoLineTotal);
        jpGraficoLineTotal.setLayout(jpGraficoLineTotalLayout);
        jpGraficoLineTotalLayout.setHorizontalGroup(
            jpGraficoLineTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        jpGraficoLineTotalLayout.setVerticalGroup(
            jpGraficoLineTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jbGraphicLinePercent.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jbGraphicLinePercentLayout = new javax.swing.GroupLayout(jbGraphicLinePercent);
        jbGraphicLinePercent.setLayout(jbGraphicLinePercentLayout);
        jbGraphicLinePercentLayout.setHorizontalGroup(
            jbGraphicLinePercentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
        );
        jbGraphicLinePercentLayout.setVerticalGroup(
            jbGraphicLinePercentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpGraficosLayout = new javax.swing.GroupLayout(jpGraficos);
        jpGraficos.setLayout(jpGraficosLayout);
        jpGraficosLayout.setHorizontalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGraficosLayout.createSequentialGroup()
                .addComponent(jbGraphicLinePercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpGraficoLineTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpGraficosLayout.setVerticalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpGraficoLineTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jbGraphicLinePercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Graph", jpGraficos);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jbLimpar.setBackground(new java.awt.Color(51, 204, 255));
        jbLimpar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jbLimpar.setForeground(new java.awt.Color(255, 255, 255));
        jbLimpar.setText("Reset");
        jbLimpar.setContentAreaFilled(false);
        jbLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbLimpar.setFocusable(false);
        jbLimpar.setOpaque(true);
        jbLimpar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbLimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbLimparMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbLimparMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbLimparMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbLimparMouseReleased(evt);
            }
        });
        jbLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparActionPerformed(evt);
            }
        });

        jbExecutar.setBackground(new java.awt.Color(51, 204, 255));
        jbExecutar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jbExecutar.setForeground(new java.awt.Color(255, 255, 255));
        jbExecutar.setText("Execute");
        jbExecutar.setBorder(null);
        jbExecutar.setContentAreaFilled(false);
        jbExecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbExecutar.setFocusable(false);
        jbExecutar.setOpaque(true);
        jbExecutar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbExecutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbExecutarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbExecutarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbExecutarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbExecutarMouseReleased(evt);
            }
        });
        jbExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExecutarActionPerformed(evt);
            }
        });

        jbParar.setBackground(new java.awt.Color(204, 204, 204));
        jbParar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jbParar.setForeground(new java.awt.Color(214, 214, 214));
        jbParar.setText("Stop");
        jbParar.setBorder(null);
        jbParar.setContentAreaFilled(false);
        jbParar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbParar.setEnabled(false);
        jbParar.setFocusable(false);
        jbParar.setOpaque(true);
        jbParar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbParar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbPararMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbPararMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbPararMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbPararMouseReleased(evt);
            }
        });
        jbParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPararActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jbLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbExecutar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbParar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jbExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jbParar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtaLog.setEditable(false);
        jtaLog.setBackground(new java.awt.Color(0, 0, 0));
        jtaLog.setColumns(20);
        jtaLog.setForeground(new java.awt.Color(255, 255, 0));
        jtaLog.setRows(5);
        jScrollPane1.setViewportView(jtaLog);

        javax.swing.GroupLayout jpMainLayout = new javax.swing.GroupLayout(jpMain);
        jpMain.setLayout(jpMainLayout);
        jpMainLayout.setHorizontalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jpMainLayout.setVerticalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMainLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorder(null);
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setAutoscrolls(true);
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jmiSaveLog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmiSaveLog.setBackground(new java.awt.Color(255, 255, 255));
        jmiSaveLog.setText("Save Log");
        jmiSaveLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveLogActionPerformed(evt);
            }
        });
        jMenu1.add(jmiSaveLog);
        jMenu1.add(jSeparator1);

        jMenuItem1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //esta é a oppção sair do menu. Aqui eu finalizo o programa
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jmiSaveLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveLogActionPerformed
        // TODO add your handling code here:
        File arquivo = new File(seleciona_arquivo());
        try (
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            escritor.append(jtaLog.getText());
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(AutoElastic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jmiSaveLogActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new About().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jpGraficosLineComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jpGraficosLineComponentResized
        //toda vez que a janela é redimensionada tenho que redimensionar o gráfico também
        if (!(gerenciador == null)){
            gerenciador.resize_grafico();
        }
    }//GEN-LAST:event_jpGraficosLineComponentResized

    private void jbDelHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDelHostActionPerformed
        //excluo a linha selecionada do grid dos hosts
        if (jtHosts.getSelectedRow() >= 0){
            DefaultTableModel model= (DefaultTableModel) jtHosts.getModel();
            model.removeRow(jtHosts.getSelectedRow());
        }
    }//GEN-LAST:event_jbDelHostActionPerformed

    private void jbAddHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddHostActionPerformed
        //crio uma nova linha no grid dos hosts
        DefaultTableModel model= (DefaultTableModel) jtHosts.getModel();
        model.addRow(new Object[]{""});
    }//GEN-LAST:event_jbAddHostActionPerformed

    private void jtfClusterIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfClusterIdFocusGained
        jtfClusterId.selectAll();
    }//GEN-LAST:event_jtfClusterIdFocusGained

    private void jtfVNMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVNMFocusGained
        jtfVNM.selectAll();
    }//GEN-LAST:event_jtfVNMFocusGained

    private void jtfVMMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVMMFocusGained
        jtfVMM.selectAll();
    }//GEN-LAST:event_jtfVMMFocusGained

    private void jtfIMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfIMFocusGained
        jtfIM.selectAll();
    }//GEN-LAST:event_jtfIMFocusGained

    private void jtfSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSenhaFocusGained
        jtfSenha.selectAll();
    }//GEN-LAST:event_jtfSenhaFocusGained

    private void jtfUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfUsuarioFocusGained
        jtfUsuario.selectAll();
    }//GEN-LAST:event_jtfUsuarioFocusGained

    private void jtfFrontendFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrontendFocusGained
        jtfFrontend.selectAll();
    }//GEN-LAST:event_jtfFrontendFocusGained

    private void jtfLogsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfLogsFocusGained
        jtfLogs.selectAll();
    }//GEN-LAST:event_jtfLogsFocusGained

    private void jtfMonitoringWindowFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfMonitoringWindowFocusGained
        jtfMonitoringWindow.selectAll();
    }//GEN-LAST:event_jtfMonitoringWindowFocusGained

    private void jbBuscarSLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarSLAActionPerformed
        //seleciono o arquivo SLA
        jtfSla.setText(seleciona_arquivo());
    }//GEN-LAST:event_jbBuscarSLAActionPerformed

    private void jtfThresholdMaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfThresholdMaxFocusGained
        jtfThresholdMax.selectAll();
    }//GEN-LAST:event_jtfThresholdMaxFocusGained

    private void jtfThresholdMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfThresholdMinFocusGained
        jtfThresholdMin.selectAll();
    }//GEN-LAST:event_jtfThresholdMinFocusGained

    private void jtfTemplateidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTemplateidFocusGained
        jtfTemplateid.selectAll();
    }//GEN-LAST:event_jtfTemplateidFocusGained

    private void jtfMonitoringIntervalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfMonitoringIntervalFocusGained
        jtfMonitoringInterval.selectAll();
    }//GEN-LAST:event_jtfMonitoringIntervalFocusGained

    private void jtfVmsPorHostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVmsPorHostFocusGained
        jtfVmsPorHost.selectAll();
    }//GEN-LAST:event_jtfVmsPorHostFocusGained

    private void jbPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPararActionPerformed
        //ao clicar em Parar chamo o método "stop()" dentro do gerenciador que finaliza sua execução
        //jtaLog.setText("");
        gerenciador.stop();
        //configuro os botões
        jbParar.setEnabled(false);
        jbParar.setBackground(new Color(204,204,204));
        jbParar.setForeground(new Color(214,214,214));
        jbLimpar.setEnabled(true);
        jbLimpar.setBackground(new Color(51,204,255));
        jbLimpar.setForeground(Color.white);
        jbExecutar.setEnabled(true);
        jbExecutar.setBackground(new Color(51,204,255));
        jbExecutar.setForeground(Color.white);
    }//GEN-LAST:event_jbPararActionPerformed

    private void jbExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExecutarActionPerformed

        //limpo o log
        jtaLog.setText("");
        
        //se eu clicar em EXECUTAR testo se o SLA foi informado ou se ele existe
        //if ((this.jtfSla.getText().equalsIgnoreCase("")) || (!new File(this.jtfSla.getText()).isFile())){
            if (!new File(this.jtfSla.getText()).isFile()){
                //se o SLA não foi informado, então apresento um aviso
                JOptionPane.showMessageDialog(null, "SLA não informado ou inexistente!", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else {
                //se o SLA foi informado enão inicio a execução
                //configuro os botões
                jbParar.setEnabled(true);
                jbParar.setBackground(new Color(51,204,255));
                jbParar.setForeground(Color.white);
                jbLimpar.setEnabled(false);
                jbLimpar.setBackground(new Color(204,204,204));
                jbLimpar.setForeground(new Color(214,214,214));
                jbExecutar.setEnabled(false);
                jbExecutar.setBackground(new Color(204,204,204));
                jbExecutar.setForeground(new Color(214,214,214));

                //este bloco eu pego o grid com os hosts e transformo em um array contendo cada host como String
                DefaultTableModel model = (DefaultTableModel) jtHosts.getModel();
                String hosts[] = new String[model.getRowCount()];
                String host;
                for (int i = 0; i < model.getRowCount(); i++){
                    host = model.getDataVector().get(i).toString();
                    hosts[i] = host.replace("[", "").replace("]", "");
                }

                //seta parametros do gerenciador
                gerenciador.set_parameters(
                    this.jtfFrontend.getText(),
                    this.jtfUsuario.getText(),
                    this.jtfSenha.getText(),
                    this.jtfSla.getText(),
                    this.jtfLogs.getText(),
                    Integer.parseInt(this.jtfTemplateid.getText()),
                    Integer.parseInt(this.jtfMonitoringInterval.getText()),
                    Double.parseDouble(this.jtfThresholdMax.getText()) / 100,
                    Double.parseDouble(this.jtfThresholdMin.getText()) / 100,
                    Integer.parseInt(this.jtfVmsPorHost.getText()),
                    this.bgEvaluators.getSelection().getActionCommand(),
                    this.bgThresholdType.getSelection().getActionCommand(),
                    Integer.parseInt(this.jtfMonitoringWindow.getText()),
                    hosts,
                    this.jtfIM.getText(),
                    this.jtfVMM.getText(),
                    this.jtfVNM.getText(),
                    Integer.parseInt(this.jtfClusterId.getText()),
                    this.jtaLog
                );

                //coloco o gerenciador dentro de uma Thread e inicio ele
                th_gerenciador = new Thread(gerenciador);
                th_gerenciador.start();
            }
    }//GEN-LAST:event_jbExecutarActionPerformed

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
        variaveis_padroes();
    }//GEN-LAST:event_jbLimparActionPerformed

    private void jbLimparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLimparMouseEntered
        if (jbLimpar.isEnabled()){
            jbLimpar.setBackground(new Color(41,194,255));
        }
    }//GEN-LAST:event_jbLimparMouseEntered

    private void jbLimparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLimparMouseExited
        if (jbLimpar.isEnabled()){
            jbLimpar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbLimparMouseExited

    private void jbLimparMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLimparMousePressed
        if (jbLimpar.isEnabled()){
            jbLimpar.setBackground(new Color(51,234,205));
        }
    }//GEN-LAST:event_jbLimparMousePressed

    private void jbLimparMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLimparMouseReleased
        if (jbLimpar.isEnabled()){
            jbLimpar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbLimparMouseReleased

    private void jbExecutarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExecutarMouseEntered
        if (jbExecutar.isEnabled()){
            jbExecutar.setBackground(new Color(41,194,255));
        }
    }//GEN-LAST:event_jbExecutarMouseEntered

    private void jbExecutarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExecutarMouseExited
        if (jbExecutar.isEnabled()){
            jbExecutar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbExecutarMouseExited

    private void jbExecutarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExecutarMousePressed
        if (jbExecutar.isEnabled()){
            jbExecutar.setBackground(new Color(51,234,205));
        }
    }//GEN-LAST:event_jbExecutarMousePressed

    private void jbExecutarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExecutarMouseReleased
        if (jbExecutar.isEnabled()){
            jbExecutar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbExecutarMouseReleased

    private void jbPararMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPararMouseEntered
        if (jbParar.isEnabled()){
            jbParar.setBackground(new Color(41,194,255));
        }
    }//GEN-LAST:event_jbPararMouseEntered

    private void jbPararMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPararMouseExited
        if (jbParar.isEnabled()){
            jbParar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbPararMouseExited

    private void jbPararMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPararMousePressed
        if (jbParar.isEnabled()){
            jbParar.setBackground(new Color(51,234,205));
        }
    }//GEN-LAST:event_jbPararMousePressed

    private void jbPararMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPararMouseReleased
        if (jbParar.isEnabled()){
            jbParar.setBackground(new Color(51,204,255));
        }
    }//GEN-LAST:event_jbPararMouseReleased

    private void jbAddHostMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAddHostMouseEntered
        // TODO add your handling code here:
        jbAddHost.setBackground(new Color(41,194,255));
    }//GEN-LAST:event_jbAddHostMouseEntered

    private void jbAddHostMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAddHostMouseExited
        jbAddHost.setBackground(new Color(51,204,255));
    }//GEN-LAST:event_jbAddHostMouseExited

    private void jbAddHostMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAddHostMousePressed
        jbAddHost.setBackground(new Color(51,234,205));
    }//GEN-LAST:event_jbAddHostMousePressed

    private void jbAddHostMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAddHostMouseReleased
        jbAddHost.setBackground(new Color(51,204,255));
    }//GEN-LAST:event_jbAddHostMouseReleased

    private void jbDelHostMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDelHostMouseEntered
        jbDelHost.setBackground(new Color(41,194,255));
    }//GEN-LAST:event_jbDelHostMouseEntered

    private void jbDelHostMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDelHostMouseExited
        jbDelHost.setBackground(new Color(51,204,255));
    }//GEN-LAST:event_jbDelHostMouseExited

    private void jbDelHostMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDelHostMousePressed
        jbDelHost.setBackground(new Color(51,234,205));
    }//GEN-LAST:event_jbDelHostMousePressed

    private void jbDelHostMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDelHostMouseReleased
        jbDelHost.setBackground(new Color(51,204,255));
    }//GEN-LAST:event_jbDelHostMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(FAutoElastic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FAutoElastic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FAutoElastic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FAutoElastic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FAutoElastic().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgEvaluators;
    private javax.swing.ButtonGroup bgThresholdType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAddHost;
    private javax.swing.JButton jbBuscarSLA;
    private javax.swing.JButton jbDelHost;
    private javax.swing.JButton jbExecutar;
    private javax.swing.JPanel jbGraphicLinePercent;
    private javax.swing.JButton jbLimpar;
    private javax.swing.JButton jbParar;
    private javax.swing.JMenuItem jmiSaveLog;
    private javax.swing.JPanel jpGraficoLineTotal;
    private javax.swing.JPanel jpGraficos;
    private javax.swing.JPanel jpHosts;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpParameters;
    private javax.swing.JPanel jpServer;
    private javax.swing.JRadioButton jrbAging;
    private javax.swing.JRadioButton jrbFixed;
    private javax.swing.JRadioButton jrbGeneric;
    private javax.swing.JRadioButton jrbLive;
    private javax.swing.JTable jtHosts;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextField jtfClusterId;
    private javax.swing.JTextField jtfFrontend;
    private javax.swing.JTextField jtfIM;
    private javax.swing.JTextField jtfLogs;
    private javax.swing.JTextField jtfMonitoringInterval;
    private javax.swing.JTextField jtfMonitoringWindow;
    private javax.swing.JTextField jtfSenha;
    private javax.swing.JTextField jtfSla;
    private javax.swing.JTextField jtfTemplateid;
    private javax.swing.JTextField jtfThresholdMax;
    private javax.swing.JTextField jtfThresholdMin;
    private javax.swing.JTextField jtfUsuario;
    private javax.swing.JTextField jtfVMM;
    private javax.swing.JTextField jtfVNM;
    private javax.swing.JTextField jtfVmsPorHost;
    // End of variables declaration//GEN-END:variables

    
    private void centraliza_tela() {
        // Centraliza a janela de abertura no centro do desktop.  
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();  
        Rectangle r      = this.getBounds();  
        // Dimensões da janela  
        int widthSplash = r.width ;  
        int heightSplash = r.height;  
        // calculo para encontrar as cooredenadas X e Y para a centralização da janela.  
        int posX = (screen.width / 2) - ( widthSplash / 2 );  
        int posY = (screen.height / 2) - ( heightSplash / 2 );  
        this.setBounds(posX,posY,widthSplash,heightSplash);
    }

    private void variaveis_padroes() {
        //definir os parametros para o padrao
        this.jtfFrontend.setText("10.210.7.116");
        this.jtfUsuario.setText("oneadmin");
        this.jtfSenha.setText("nebula");
        //this.jtfSla.setText("D:\\Users\\Vinicius\\Dropbox\\UNISINOS\\PIPCA\\Projetos\\AutoElastic\\autoelasticsla.xml");
        this.jtfSla.setText("C:\\Users\\Vinicius Facco\\Dropbox\\UNISINOS\\PIPCA\\Projetos\\AutoElastic\\autoelasticsla.xml");
        this.jtfLogs.setText("C:\\Temp\\");
        this.jtfMonitoringInterval.setText("15");
        this.jtfMonitoringWindow.setText("0");
        this.jtfTemplateid.setText("1");
        this.jtfThresholdMax.setText("80");
        this.jtfThresholdMin.setText("40");
        this.jtfVmsPorHost.setText("2");
        this.jtfIM.setText("kvm");
        this.jtfVMM.setText("kvm");
        this.jtfVNM.setText("dummy");
        this.jtfClusterId.setText("0");
        this.jtaLog.setText("");
    }

    private String seleciona_arquivo() {
        //selecionar um arquivo
        JFileChooser file = new JFileChooser(); 
        file.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        int i = file.showSaveDialog(null); 
        if (i == 1){ 
        	return ""; 
	} else { 
		File arquivo = file.getSelectedFile(); 
		return arquivo.getPath(); 
        }
    }
}
