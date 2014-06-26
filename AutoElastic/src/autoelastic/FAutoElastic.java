
package autoelastic;

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
        gerenciador = new AutoElastic(this.jpGraficoLine);
        this.setExtendedState(MAXIMIZED_BOTH);//maximoza janela        
        variaveis_padroes();//inicializo todos os parâmetros para o padrão
        this.setIconImage(new ImageIcon("./images/icone64x64.png").getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgEvaluators = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaLog = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtfVmsPorHost = new javax.swing.JTextField();
        jtfIntervalo = new javax.swing.JTextField();
        jtfTemplateid = new javax.swing.JTextField();
        jtfThresholdMin = new javax.swing.JTextField();
        jtfThresholdMax = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfSla = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jbBuscarSLA = new javax.swing.JButton();
        jtfEvaluatorWindow = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtfLogs = new javax.swing.JTextField();
        jrbGeneric = new javax.swing.JRadioButton();
        jrbAging = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtHosts = new javax.swing.JTable();
        jbAddHost = new javax.swing.JButton();
        jBDelHost = new javax.swing.JButton();
        jpGraficos = new javax.swing.JPanel();
        jpGraficoLine = new javax.swing.JPanel();
        jbLimpar = new javax.swing.JButton();
        jbParar = new javax.swing.JButton();
        jbExecutar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiSaveLog = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AutoElastic");
        setIconImages(null);

        jtaLog.setEditable(false);
        jtaLog.setBackground(new java.awt.Color(0, 0, 0));
        jtaLog.setColumns(20);
        jtaLog.setForeground(new java.awt.Color(255, 255, 0));
        jtaLog.setRows(5);
        jScrollPane1.setViewportView(jtaLog);

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

        jLabel9.setText("User");

        jLabel10.setText("Password");

        jLabel11.setText("IM");

        jtfIM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfIMFocusGained(evt);
            }
        });

        jLabel12.setText("VMM");

        jtfVMM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVMMFocusGained(evt);
            }
        });

        jLabel13.setText("VNM");

        jtfVNM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVNMFocusGained(evt);
            }
        });

        jLabel14.setText("Cluster ID");

        jtfClusterId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfClusterIdFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jtfFrontend, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jtfIM)
                            .addComponent(jtfVMM))))
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jtfVNM)
                    .addComponent(jtfClusterId))
                .addGap(296, 296, 296))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFrontend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jtfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jtfVNM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtfVMM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jtfClusterId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Server", jPanel1);

        jLabel8.setText("Evaluator Window");

        jtfVmsPorHost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVmsPorHostFocusGained(evt);
            }
        });

        jtfIntervalo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfIntervaloFocusGained(evt);
            }
        });

        jtfTemplateid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfTemplateidFocusGained(evt);
            }
        });

        jtfThresholdMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfThresholdMinFocusGained(evt);
            }
        });

        jtfThresholdMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfThresholdMaxFocusGained(evt);
            }
        });

        jLabel4.setText("Interval");

        jLabel2.setText("SLA");

        jLabel3.setText("Template ID");

        jtfSla.setEditable(false);

        jLabel7.setText("Virtual Machines");

        jLabel6.setText("Threshold Low");

        jLabel5.setText("Threshold High");

        jbBuscarSLA.setText("...");
        jbBuscarSLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarSLAActionPerformed(evt);
            }
        });

        jtfEvaluatorWindow.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfEvaluatorWindowFocusGained(evt);
            }
        });

        jLabel15.setText("Logs");

        jtfLogs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfLogsFocusGained(evt);
            }
        });

        bgEvaluators.add(jrbGeneric);
        jrbGeneric.setSelected(true);
        jrbGeneric.setText("Generic");
        jrbGeneric.setActionCommand("generic");

        bgEvaluators.add(jrbAging);
        jrbAging.setText("Aging");
        jrbAging.setActionCommand("aging");

        jLabel16.setText("Evaluation Algorithm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel15))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfTemplateid, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(140, 140, 140))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jtfVmsPorHost, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))
                                        .addGap(16, 16, 16))
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jtfThresholdMax, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(213, 213, 213)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jrbAging, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                                .addGap(9, 9, 9))
                                            .addComponent(jrbGeneric, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(75, 75, 75))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfEvaluatorWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfThresholdMin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addComponent(jLabel16)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jtfSla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbBuscarSLA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfLogs)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfSla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarSLA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtfLogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jtfTemplateid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtfIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtfThresholdMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtfThresholdMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel16))))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jtfVmsPorHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jtfEvaluatorWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jrbAging)
                        .addGap(22, 22, 22)
                        .addComponent(jrbGeneric)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parameters", jPanel2);

        jtHosts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"192.168.10.2"},
                {"192.168.10.3"},
                {"192.168.10.4"},
                {"192.168.10.5"},
                {"192.168.10.6"},
                {"192.168.10.7"},
                {"192.168.10.8"},
                {"192.168.10.10"},
                {"192.168.10.11"},
                {"192.168.10.14"}
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
        jScrollPane3.setViewportView(jtHosts);

        jbAddHost.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbAddHost.setText("+");
        jbAddHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddHostActionPerformed(evt);
            }
        });

        jBDelHost.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jBDelHost.setText("-");
        jBDelHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelHostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAddHost, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBDelHost, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jbAddHost, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jBDelHost, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Hosts", jPanel3);

        jpGraficos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jpGraficosLineComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jpGraficoLineLayout = new javax.swing.GroupLayout(jpGraficoLine);
        jpGraficoLine.setLayout(jpGraficoLineLayout);
        jpGraficoLineLayout.setHorizontalGroup(
            jpGraficoLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
        );
        jpGraficoLineLayout.setVerticalGroup(
            jpGraficoLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpGraficosLayout = new javax.swing.GroupLayout(jpGraficos);
        jpGraficos.setLayout(jpGraficosLayout);
        jpGraficosLayout.setHorizontalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpGraficoLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpGraficosLayout.setVerticalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpGraficoLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Graph", jpGraficos);

        jbLimpar.setText("Reset");
        jbLimpar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparActionPerformed(evt);
            }
        });

        jbParar.setText("Stop");
        jbParar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPararActionPerformed(evt);
            }
        });

        jbExecutar.setText("Execute");
        jbExecutar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExecutarActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jmiSaveLog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmiSaveLog.setText("Save Log");
        jmiSaveLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveLogActionPerformed(evt);
            }
        });
        jMenu1.add(jmiSaveLog);
        jMenu1.add(jSeparator1);

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jbParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //esta é a oppção sair do menu. Aqui eu finalizo o programa
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            jbExecutar.setEnabled(false);//desabilito o botão para o usuário não clicar novamente
            
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
                Integer.parseInt(this.jtfIntervalo.getText()),
                Double.parseDouble(this.jtfThresholdMax.getText()) / 100,
                Double.parseDouble(this.jtfThresholdMin.getText()) / 100,
                Integer.parseInt(this.jtfVmsPorHost.getText()),
                this.bgEvaluators.getSelection().getActionCommand(),
                Integer.parseInt(this.jtfEvaluatorWindow.getText()),
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
        //ao clicar em Limpar defino todos os campos para o padrão
        variaveis_padroes();
    }//GEN-LAST:event_jbLimparActionPerformed

    private void jbPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPararActionPerformed
        //ao clicar em Parar chamo o método "stop()" dentro do gerenciador que finaliza sua execução
        //jtaLog.setText("");
        gerenciador.stop();
        jbExecutar.setEnabled(true);//habilito novamente o botão para executar
    }//GEN-LAST:event_jbPararActionPerformed

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

    private void jBDelHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelHostActionPerformed
        //excluo a linha selecionada do grid dos hosts
        if (jtHosts.getSelectedRow() >= 0){
            DefaultTableModel model= (DefaultTableModel) jtHosts.getModel();
            model.removeRow(jtHosts.getSelectedRow());
        }
    }//GEN-LAST:event_jBDelHostActionPerformed

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

    private void jtfEvaluatorWindowFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEvaluatorWindowFocusGained
        jtfEvaluatorWindow.selectAll();
    }//GEN-LAST:event_jtfEvaluatorWindowFocusGained

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

    private void jtfIntervaloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfIntervaloFocusGained
        jtfIntervalo.selectAll();
    }//GEN-LAST:event_jtfIntervaloFocusGained

    private void jtfVmsPorHostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVmsPorHostFocusGained
        jtfVmsPorHost.selectAll();
    }//GEN-LAST:event_jtfVmsPorHostFocusGained

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
    private javax.swing.JButton jBDelHost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAddHost;
    private javax.swing.JButton jbBuscarSLA;
    private javax.swing.JButton jbExecutar;
    private javax.swing.JButton jbLimpar;
    private javax.swing.JButton jbParar;
    private javax.swing.JMenuItem jmiSaveLog;
    private javax.swing.JPanel jpGraficoLine;
    private javax.swing.JPanel jpGraficos;
    private javax.swing.JRadioButton jrbAging;
    private javax.swing.JRadioButton jrbGeneric;
    private javax.swing.JTable jtHosts;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextField jtfClusterId;
    private javax.swing.JTextField jtfEvaluatorWindow;
    private javax.swing.JTextField jtfFrontend;
    private javax.swing.JTextField jtfIM;
    private javax.swing.JTextField jtfIntervalo;
    private javax.swing.JTextField jtfLogs;
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
        this.jtfFrontend.setText("192.168.10.1");
        this.jtfUsuario.setText("oneadmin");
        this.jtfSenha.setText("oneadmin");
        this.jtfSla.setText("D:\\Users\\Vinicius\\Dropbox\\UNISINOS\\PIPCA\\Projetos\\AutoElastic\\autoelasticsla.xml");
        this.jtfLogs.setText("C:\\Temp\\");
        this.jtfIntervalo.setText("15");
        this.jtfEvaluatorWindow.setText("3");
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
