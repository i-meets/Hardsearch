package hard.rh.view;

import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FuncionarioDao;
import hard.rh.model.Funcionario;
import java.awt.Toolkit;
import java.sql.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDListaFuncioFolha extends javax.swing.JDialog {

    FDErroOcorrido fdErroOcorrido;
    private int codFuncio;

    private FDListaFuncioFolha(JFrame jFrame, boolean b) {

    }

    public int getCodFuncio() {
        return codFuncio;

    }

    /**
     * Creates new form FDBuscaFuncio
     *
     * @param parent
     * @param modal
     */
    java.sql.Date dataFiltro;

    public FDListaFuncioFolha(Date data, java.awt.Frame parent, boolean modal) throws ClassNotFoundException {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setIcon();
        EdCodFuncio.setVisible(false);
        dataFiltro = data;
        listaFuncio();
    }

    public void listaFuncio() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TabelaBuscaFuncio.getModel();
        modelo.setNumRows(0);
        FuncionarioDao fdao = new FuncionarioDao();

        for (Funcionario f : fdao.readFuncionarioForDescFolha(dataFiltro)) {
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
        BtOk = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecionar Funcionário");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EdCodFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtOk)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void BtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOkActionPerformed

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o Funcionário na tabela!");

        } else {
            try {

                this.codFuncio = Integer.parseInt(this.EdCodFuncio.getText());
                this.dispose();
            } catch (NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("no listagem");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FDErroOcorrido fdErroOcorrido;
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
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir a tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FDListaFuncioFolha dialog = new FDListaFuncioFolha(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton BtOk;
    private javax.swing.JButton BtSair;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTable TabelaBuscaFuncio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
