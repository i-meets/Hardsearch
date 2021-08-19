package hard.rh.view;

import hard.home.view.FDBuscaFuncio;
import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FuncionarioDao;
import hard.rh.model.Funcionario;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrRelatorioIntegra extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    private FDBuscaFuncio FDBuscaFuncio;

    public FrRelatorioIntegra() {
        initComponents();
        title = "Relatório de Integração - V" + versao;

        EdCodFuncio.setEnabled(false);
        EdNomeFuncio.setEnabled(false);
        JDcDataInicial.setEnabled(false);
        JDcDataFinal.setEnabled(false);
        BtBusca.setEnabled(false);
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void cancelar() {
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        JDcDataInicial.setDate(null);
        JDcDataFinal.setDate(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        EdCodFuncio = new javax.swing.JTextField();
        BtBusca = new javax.swing.JButton();
        EdNomeFuncio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        JDcDataInicial = new com.toedter.calendar.JDateChooser();
        JDcDataFinal = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        CbxVencido = new javax.swing.JCheckBox();
        CbxNVencido = new javax.swing.JCheckBox();

        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodFuncioActionPerformed(evt);
            }
        });
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel1.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, -1));

        BtBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBusca.setText("Buscar");
        BtBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaActionPerformed(evt);
            }
        });
        jPanel1.add(BtBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 90, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, -1));

        jLabel2.setText("Filtrar Por:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, 20));

        JDcDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(JDcDataInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 130, -1));

        JDcDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(JDcDataFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 130, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jButton2.setText("Relatório");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton4.setText("Sair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 70, -1));

        CbxVencido.setText("Vencidas");
        CbxVencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxVencidoActionPerformed(evt);
            }
        });
        jPanel1.add(CbxVencido, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        CbxNVencido.setText("Não Vencidas");
        CbxNVencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxNVencidoActionPerformed(evt);
            }
        });
        jPanel1.add(CbxNVencido, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        jTabbedPane1.addTab("Relatório Integrações", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (CbxVencido.isSelected()) {//RELATÓRIO DE PARCELAS NÃO PAGAS

            if (EdCodFuncio.getText().equals("")) {//SE FOR EMITIR O RELATÓRIO SEM O CÓDIGO DE FUNCIONÁRIO
                //LISTA TODAS AS PARCELAS NÃO PAGAS

                if (JDcDataFinal.getDate() == null || JDcDataInicial.getDate() == null) {

                    try {

                        Connection con = ConnectionFactory.getConnection();

                        try {

                            JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relIntegraVencida.jasper", null, con);
                            JasperViewer.viewReport(print, false);
                        } catch (JRException ex) {
                            fdErroOcorrido = new FDErroOcorrido(null, true);
                            fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                            fdErroOcorrido.setVisible(true);

                        }
                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);

                    }

                } else {
                    try {

                        Connection con = ConnectionFactory.getConnection();
                        Map<String, Object> map = new HashMap<>();

                        java.util.Date dataIni = JDcDataInicial.getDate();
                        long dtInicial = dataIni.getTime();
                        java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                        java.util.Date dataFin = JDcDataFinal.getDate();
                        long dtFinal = dataFin.getTime();
                        java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);

                        map.put("FiltroDataInicial", dateParcelaInicial);
                        map.put("FiltroDataFinal", dateParcelaFinal);

                        try {

                            JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relParcelaNPagaForDate.jasper", map, con);
                            JasperViewer.viewReport(print, false);
                        } catch (JRException ex) {
                            fdErroOcorrido = new FDErroOcorrido(null, true);
                            fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                            fdErroOcorrido.setVisible(true);

                        }
                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);

                    }
                }

            } else if (JDcDataFinal.getDate() == null || JDcDataFinal.getDate() == null) {//SE EMITIR O RELATÓRIO SEM O CAMPO DE DATAS PREENCHIDAS
                //EMITE RELATÓRIO POR CÓDIGO DE FUNCIONÁRIO SEM DATA INICIAL E FINAL
                try {
                    Connection con = ConnectionFactory.getConnection();
                    //JRDataSource data = new JREmptyDataSource();
                    Map<String, Object> map = new HashMap<>();
                    int codFuncio = Integer.parseInt(EdCodFuncio.getText());
                    map.put("FiltroCodFuncio", codFuncio);

                    try {
                        JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relParcelaNPagaForUser.jasper", map, con);
                        JasperViewer.viewReport(print, false);
                    } catch (JRException ex) {

                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                    Logger.getLogger(FrRelatorioIntegra.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {//SE OS CAMPOS DE DATA ESTIVEREM PREENCHIDOS
                //EMITE RELATÓRIO PELO CÓDIGO DO FUNCIONÁRIO E POR PERÍODO
                try {
                    Connection con = ConnectionFactory.getConnection();
                    Map<String, Object> map = new HashMap<>();
                    int codFuncio = Integer.parseInt(EdCodFuncio.getText());

                    java.util.Date dataIni = JDcDataInicial.getDate();
                    long dtInicial = dataIni.getTime();
                    java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                    java.util.Date dataFin = JDcDataFinal.getDate();
                    long dtFinal = dataFin.getTime();
                    java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);

                    map.put("FiltroCodFuncio", codFuncio);
                    map.put("FiltroDataInicial", dateParcelaInicial);
                    map.put("FiltroDataFinal", dateParcelaFinal);

                    try {
                        JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relParcelaNPagaForUserForDate.jasper", map, con);
                        JasperViewer.viewReport(print, false);
                    } catch (JRException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        } else if (CbxNVencido.isSelected()) {//RELATÓRIO DE PARCELAS PAGAS

            JOptionPane.showMessageDialog(rootPane, "Relatório não encontrado.\n\nContate Suporte.", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);

        } else {//LISTA TODAS AS PARCELAS PAGAS OU NÃO PAGAS

            if (EdCodFuncio.getText().equals("")) {//SE FOR EMITIR O RELATÓRIO SEM O CÓDIGO DE FUNCIONÁRIO
                //LISTA TODAS AS PARCELAS
                try {

                    Connection con = ConnectionFactory.getConnection();

                    try {

                        JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relIntegraCadastradas.jasper", null, con);

                        String local = "\\\\C:\\Hardsearch\\MyReports\\relIntegraCadastradas.jrxml";

                        JasperExportManager.exportReportToPdfFile(local);

                        JasperViewer.viewReport(print, false);

                    } catch (JRException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);

                    }
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } else if (JDcDataFinal.getDate() == null || JDcDataFinal.getDate() == null) {//SE EMITIR O RELATÓRIO SEM O CAMPO DE DATAS PREENCHIDAS
                //EMITE RELATÓRIO POR CÓDIGO DE FUNCIONÁRIO SEM DATA INICIAL E FINAL
                try {
                    Connection con = ConnectionFactory.getConnection();
                    //JRDataSource data = new JREmptyDataSource();
                    Map<String, Object> map = new HashMap<>();
                    int codFuncio = Integer.parseInt(EdCodFuncio.getText());
                    map.put("FiltroCodFuncio", codFuncio);

                    try {
                        JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relParcelaForUser.jasper", map, con);
                        JasperViewer.viewReport(print, false);
                    } catch (JRException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                    Logger.getLogger(FrRelatorioIntegra.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {//SE OS CAMPOS DE DATA ESTIVEREM PREENCHIDOS
                //EMITE RELATÓRIO PELO CÓDIGO DO FUNCIONÁRIO E POR PERÍODO
                try {
                    Connection con = ConnectionFactory.getConnection();
                    Map<String, Object> map = new HashMap<>();
                    int codFuncio = Integer.parseInt(EdCodFuncio.getText());

                    java.util.Date dataIni = JDcDataInicial.getDate();
                    long dtInicial = dataIni.getTime();
                    java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                    java.util.Date dataFin = JDcDataFinal.getDate();
                    long dtFinal = dataFin.getTime();
                    java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);

                    map.put("FiltroCodFuncio", codFuncio);
                    map.put("FiltroDataInicial", dateParcelaInicial);
                    map.put("FiltroDataFinal", dateParcelaFinal);

                    try {
                        JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relParcelaForUserForDate.jasper", map, con);
                        JasperViewer.viewReport(print, false);
                    } catch (JRException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    public void validaCodigo() {
        FuncionarioDao pdao = new FuncionarioDao();

        try {
            if (pdao.checkCod(EdCodFuncio.getText())) {
                listarFuncioCod();
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar funcionário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();

        for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNomeFuncio.setText(fu.getNomeFuncionario());
        }
    }

    private void BtBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaActionPerformed
        this.FDBuscaFuncio = new FDBuscaFuncio(null, true);

        if (EdCodFuncio.getText().equals("")) {
            this.FDBuscaFuncio.setVisible(true);

            this.EdCodFuncio.setText(String.valueOf(this.FDBuscaFuncio.getCodFuncio()));

            if (this.EdCodFuncio.getText().equals("0")) {
                EdCodFuncio.setText("");

            } else {
                validaCodigo();
            }

        } else {

            validaCodigo();
        }

    }//GEN-LAST:event_BtBuscaActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validaCodigo();
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        cancelar();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void CbxVencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxVencidoActionPerformed
        CbxNVencido.setSelected(false);
    }//GEN-LAST:event_CbxVencidoActionPerformed

    private void CbxNVencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxNVencidoActionPerformed
        CbxVencido.setSelected(false);
    }//GEN-LAST:event_CbxNVencidoActionPerformed

    private void EdCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncioActionPerformed
        
    }//GEN-LAST:event_EdCodFuncioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBusca;
    private javax.swing.JCheckBox CbxNVencido;
    private javax.swing.JCheckBox CbxVencido;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdNomeFuncio;
    private com.toedter.calendar.JDateChooser JDcDataFinal;
    private com.toedter.calendar.JDateChooser JDcDataInicial;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
