package hard.home.view;

import hard.rh.dao.FuncionarioDao;
import hard.rh.model.Funcionario;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDBuscaFuncio extends javax.swing.JDialog {

    private int codFuncio;

    public int getCodFuncio() {
        return codFuncio;

    }

    /**
     * Creates new form FDBuscaFuncio
     *
     * @param parent
     * @param modal
     */
    public FDBuscaFuncio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setIcon();
        EdCodFuncio.setVisible(false);
    }

    public void listaFuncio() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TabelaBuscaFuncio.getModel();
        modelo.setNumRows(0);
        FuncionarioDao fdao = new FuncionarioDao();

        for (Funcionario f : fdao.readFuncionarioForDesc(EdBuscaFuncio.getText())) {
            modelo.addRow(new Object[]{
                f.getCodFuncionario(),
                f.getNomeFuncionario()
            });

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaBuscaFuncio = new javax.swing.JTable();
        EdCodFuncio = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtOk = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        EdBuscaFuncio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TabelaBuscaFuncio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome"
            }
        ));
        TabelaBuscaFuncio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaBuscaFuncioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaBuscaFuncio);
        if (TabelaBuscaFuncio.getColumnModel().getColumnCount() > 0) {
            TabelaBuscaFuncio.getColumnModel().getColumn(0).setMinWidth(40);
            TabelaBuscaFuncio.getColumnModel().getColumn(0).setPreferredWidth(80);
            TabelaBuscaFuncio.getColumnModel().getColumn(0).setMaxWidth(100);
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

        EdBuscaFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscaFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaFuncioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EdCodFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtOk))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdBuscaFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(BtBuscar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscaFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdCodFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSair)
                    .addComponent(BtOk))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 240));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            listaFuncio();
            EdCodFuncio.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FDBuscaFuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOkActionPerformed

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o Funcionário na tabela!");

        } else {
            try {

                this.codFuncio = Integer.parseInt(this.EdCodFuncio.getText());
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(rootPane, ex);
            }

        }

    }//GEN-LAST:event_BtOkActionPerformed

    private void TabelaBuscaFuncioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaBuscaFuncioMouseClicked
        if (TabelaBuscaFuncio.getSelectedRow() != -1) {

            EdCodFuncio.setText(TabelaBuscaFuncio.getValueAt(TabelaBuscaFuncio.getSelectedRow(), 0).toString());
        }

    }//GEN-LAST:event_TabelaBuscaFuncioMouseClicked

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void EdBuscaFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaFuncioKeyPressed
        try {
            listaFuncio();
            EdCodFuncio.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FDBuscaFuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EdBuscaFuncioKeyPressed

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
            java.util.logging.Logger.getLogger(FDBuscaFuncio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FDBuscaFuncio dialog = new FDBuscaFuncio(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/hs-icon.png")));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtOk;
    private javax.swing.JButton BtSair;
    private javax.swing.JTextField EdBuscaFuncio;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTable TabelaBuscaFuncio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
