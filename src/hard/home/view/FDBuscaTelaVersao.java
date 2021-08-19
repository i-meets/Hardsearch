package hard.home.view;

import hard.home.dao.ContVersaoDao;
import hard.home.model.ContVersao;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDBuscaTelaVersao extends javax.swing.JDialog {

    private String codVersao;

    public String getCodVersao() {
        return codVersao;
    }

    public void setCodVersao(String codVersao) {
        this.codVersao = codVersao;
    }

    public FDBuscaTelaVersao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Controle e Seleção de Telas");
        setLocationRelativeTo(null);

        setIcon();

        EdBuscaCodTela.setVisible(false);
    }

    public void novaTela() throws SQLException {

        if (EdCodTela.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Por favor, preencha todos os dados");

        } else {
            ContVersao ct = new ContVersao();
            ContVersaoDao dao = new ContVersaoDao();

            ct.setCodTela(EdCodTela.getText());
            ct.setVersaoAtualTela("0.0");
            try {
                dao.createTela(ct);
                listTelas();
                EdCodTela.setText(null);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void listTelas() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TabelaListaTela.getModel();
        modelo.setNumRows(0);
        ContVersaoDao dao = new ContVersaoDao();

        for (ContVersao cv : dao.listTelasForDesc(EdBuscaTela.getText())) {
            modelo.addRow(new Object[]{
                cv.getCodTela(),
                cv.getVersaoAtualTela(),
                cv.getDataVersao()
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaListaTela = new javax.swing.JTable();
        EdBuscaCodTela = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtOk = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        EdBuscaTela = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        BtNovaTela = new javax.swing.JButton();
        BtSalvarTela = new javax.swing.JButton();
        EdCodTela = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaListaTela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Tela", "Versão Atual", "Data da Versão"
            }
        ));
        TabelaListaTela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaListaTelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaListaTela);
        if (TabelaListaTela.getColumnModel().getColumnCount() > 0) {
            TabelaListaTela.getColumnModel().getColumn(1).setPreferredWidth(90);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 370, 129));
        jPanel1.add(EdBuscaCodTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 22, -1));

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        BtOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/import.png"))); // NOI18N
        BtOk.setText("OK");
        BtOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtOkActionPerformed(evt);
            }
        });
        jPanel1.add(BtOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit.png"))); // NOI18N
        BtSair.setText("CANCELAR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel1.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        EdBuscaTela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaTelaKeyPressed(evt);
            }
        });
        jPanel1.add(EdBuscaTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 270, -1));

        jLabel3.setText("Buscar Tela");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtNovaTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovaTela.setText("Nova");
        jPanel2.add(BtNovaTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        BtSalvarTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_green.png"))); // NOI18N
        BtSalvarTela.setText("Adicionar");
        BtSalvarTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarTelaActionPerformed(evt);
            }
        });
        jPanel2.add(BtSalvarTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));
        jPanel2.add(EdCodTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, -1));

        jLabel4.setText("Código nova tela:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 370, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            listTelas();
            EdBuscaCodTela.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger
                    .getLogger(FDBuscaTelaVersao.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOkActionPerformed

        if (EdBuscaCodTela.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o procedimento na tabela!");

        } else {
            try {
                this.codVersao = this.EdBuscaCodTela.getText();
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(rootPane, ex);
            }

        }

    }//GEN-LAST:event_BtOkActionPerformed

    private void TabelaListaTelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaListaTelaMouseClicked
        if (TabelaListaTela.getSelectedRow() != -1) {

            EdBuscaCodTela.setText(TabelaListaTela.getValueAt(TabelaListaTela.getSelectedRow(), 0).toString());
        }

    }//GEN-LAST:event_TabelaListaTelaMouseClicked

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void EdBuscaTelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaTelaKeyPressed
        try {
            listTelas();
            EdBuscaCodTela.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger
                    .getLogger(FDBuscaTelaVersao.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EdBuscaTelaKeyPressed

    private void BtSalvarTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarTelaActionPerformed
        if (EdCodTela.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código da tela!");
        } else {
            try {
                novaTela();

            } catch (SQLException ex) {
                Logger.getLogger(FDBuscaTelaVersao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_BtSalvarTelaActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDBuscaTelaVersao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FDBuscaTelaVersao dialog = new FDBuscaTelaVersao(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtNovaTela;
    private javax.swing.JButton BtOk;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvarTela;
    private javax.swing.JTextField EdBuscaCodTela;
    private javax.swing.JTextField EdBuscaTela;
    private javax.swing.JTextField EdCodTela;
    private javax.swing.JTable TabelaListaTela;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/hs-icon.png")));
    }
}
