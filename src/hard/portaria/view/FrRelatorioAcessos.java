package hard.portaria.view;

import hard.config.ConnectionFactory;
import hard.home.view.FDBuscaEmp;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.EmpresaDao;
import hard.rh.model.Empresa;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrRelatorioAcessos extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSÃO
    String versao = "1.0-21.0527.0";

    FDErroOcorrido fdErroOcorrido;

    private FDBuscaEmp FDBuscaEmp;

    public FrRelatorioAcessos() {
        initComponents();
        title = "Relatório de Acessos - V" + versao;
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void cancelar() {
        EdNomeEmp.setText(null);
        EdNomeEmp.setText(null);
        JDcDataInicial.setDate(null);
        JDcDataFinal.setDate(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        BtBusca = new javax.swing.JButton();
        EdNomeEmp = new javax.swing.JTextField();
        JDcDataInicial = new com.toedter.calendar.JDateChooser();
        JDcDataFinal = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBusca.setText("Buscar");
        BtBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaActionPerformed(evt);
            }
        });
        jPanel1.add(BtBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 90, -1));

        EdNomeEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdNomeEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdNomeEmpMouseClicked(evt);
            }
        });
        jPanel1.add(EdNomeEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        JDcDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(JDcDataInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 140, -1));

        JDcDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(JDcDataFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 140, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jButton2.setText("Relatório");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton4.setText("Sair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 200, 70, -1));

        jTabbedPane1.addTab("Relatório Integrações", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (EdNomeEmp.getText().equals("") || JDcDataInicial.getDate() == null || JDcDataFinal.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os filtros", "Atenção", JOptionPane.WARNING_MESSAGE);
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
                String nomeEmp = null;

                nomeEmp = EdNomeEmp.getText();

                map.put("dataInicial", dateParcelaInicial);
                map.put("dataFinal", dateParcelaFinal);
                map.put("nomeEmp", nomeEmp);

                try {

                    JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relAcessosForDataAndNome.jasper", map, con);
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
    }//GEN-LAST:event_jButton2ActionPerformed

    public void listarFuncioCod() throws ClassNotFoundException {

        EmpresaDao pdao = new EmpresaDao();

        for (Empresa fu : pdao.readEmpForCod(EdNomeEmp.getText())) {
            EdNomeEmp.setText(fu.getNomeEmp());
        }
    }

    private void BtBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaActionPerformed
        try {

            String temp = EdNomeEmp.getText();
            EdNomeEmp.setText("");
            this.FDBuscaEmp = new FDBuscaEmp("", null, true);

            if (EdNomeEmp.getText().equals("")) {
                this.FDBuscaEmp.setVisible(true);

                this.EdNomeEmp.setText(String.valueOf(this.FDBuscaEmp.getNomeEmp()));

                if (this.EdNomeEmp.getText().equals("null")) {
                    EdNomeEmp.setText(temp);
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cancelar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void EdNomeEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdNomeEmpMouseClicked
        
    }//GEN-LAST:event_EdNomeEmpMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBusca;
    private javax.swing.JTextField EdNomeEmp;
    private com.toedter.calendar.JDateChooser JDcDataFinal;
    private com.toedter.calendar.JDateChooser JDcDataInicial;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
