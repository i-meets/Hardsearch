package hard.home.view;

import hard.rh.dao.EmpresaDao;
import hard.rh.model.Empresa;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDBuscaEmp extends javax.swing.JDialog {

    private String nomeEmp;
    private int codEmp;

    private FDBuscaEmp(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNomeEmp() {
        return nomeEmp;
    }

    public int getCodEmp() {
        return codEmp;
    }

    String tipoEmpresa = "";

    /**
     * Creates new form FDBuscaFuncio
     *
     * @param tipoEmp
     * @param parent
     * @param modal
     * @throws java.lang.ClassNotFoundException
     */
    public FDBuscaEmp(String tipoEmp, java.awt.Frame parent, boolean modal) throws ClassNotFoundException {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setIcon();
        EdNomeEmp.setVisible(false);
        EdCodEmp.setVisible(false);
        tipoEmpresa = tipoEmp;
        listaEmp();
    }

    public void listaEmp() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TabelaBuscaEmp.getModel();
        modelo.setNumRows(0);
        EmpresaDao edao = new EmpresaDao();

        for (Empresa p : edao.readEmpForDesc(EdBuscaEmp.getText(), tipoEmpresa)) {
            modelo.addRow(new Object[]{
                p.getCodEmp(),
                p.getNomeEmp(),
                p.getTipoEmp()
            });

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaBuscaEmp = new javax.swing.JTable();
        EdNomeEmp = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtOk = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        EdBuscaEmp = new javax.swing.JTextField();
        EdCodEmp = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TabelaBuscaEmp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Tipo"
            }
        ));
        TabelaBuscaEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaBuscaEmpMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaBuscaEmp);
        if (TabelaBuscaEmp.getColumnModel().getColumnCount() > 0) {
            TabelaBuscaEmp.getColumnModel().getColumn(0).setMinWidth(40);
            TabelaBuscaEmp.getColumnModel().getColumn(0).setPreferredWidth(60);
            TabelaBuscaEmp.getColumnModel().getColumn(0).setMaxWidth(100);
            TabelaBuscaEmp.getColumnModel().getColumn(1).setPreferredWidth(220);
        }

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        BtOk.setText("Ok");
        BtOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtOkActionPerformed(evt);
            }
        });

        BtSair.setText("Sair");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });

        EdBuscaEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Nome Empresa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscaEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaEmpKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdBuscaEmp)
                        .addGap(18, 18, 18)
                        .addComponent(BtBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EdNomeEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EdCodEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtOk)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtSair)
                    .addComponent(BtOk)
                    .addComponent(EdNomeEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            listaEmp();
            EdNomeEmp.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FDBuscaEmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOkActionPerformed

        if (EdNomeEmp.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar a empresa na tabela!");

        } else {
            try {
                this.nomeEmp = this.EdNomeEmp.getText();
                this.codEmp = Integer.parseInt(this.EdCodEmp.getText());
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(rootPane, ex);
            }

        }

    }//GEN-LAST:event_BtOkActionPerformed

    private void TabelaBuscaEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaBuscaEmpMouseClicked
        if (TabelaBuscaEmp.getSelectedRow() != -1) {
            EdCodEmp.setText(TabelaBuscaEmp.getValueAt(TabelaBuscaEmp.getSelectedRow(), 0).toString());
            EdNomeEmp.setText(TabelaBuscaEmp.getValueAt(TabelaBuscaEmp.getSelectedRow(), 1).toString());
        }

    }//GEN-LAST:event_TabelaBuscaEmpMouseClicked

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void EdBuscaEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaEmpKeyPressed
        try {
            listaEmp();
            EdNomeEmp.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FDBuscaEmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EdBuscaEmpKeyPressed

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
            java.util.logging.Logger.getLogger(FDBuscaEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            FDBuscaEmp dialog = new FDBuscaEmp(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton BtOk;
    private javax.swing.JButton BtSair;
    private javax.swing.JTextField EdBuscaEmp;
    private javax.swing.JTextField EdCodEmp;
    private javax.swing.JTextField EdNomeEmp;
    private javax.swing.JTable TabelaBuscaEmp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/hs-icon.png")));
    }
}
