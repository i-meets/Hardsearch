package hard.engenharia.view;

import hard.engenharia.dao.FerramentaDao;
import hard.engenharia.model.Ferramenta;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDBuscaFerramenta extends javax.swing.JDialog {

    private int cod;
    private String descricao;
    private String codItem;

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodItem() {
        return codItem;
    }

    /**
     * Creates new form
     *
     * @param parent
     * @param modal
     * @throws java.lang.ClassNotFoundException
     */
    public FDBuscaFerramenta(java.awt.Frame parent, boolean modal) throws ClassNotFoundException {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setIcon();

        EdCod.setVisible(false);
        EdCodItem.setVisible(false);
        EdDescricao.setVisible(false);
        listarItem();

    }

    public void listarItem() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TabelaBusca.getModel();
        modelo.setNumRows(0);
        FerramentaDao dao = new FerramentaDao();
        for (Ferramenta p : dao.readFerramentaForDesc(EdBuscaItem.getText())) {
            modelo.addRow(new Object[]{
                p.getCod_fer(),
                p.getCodFerramenta(),
                p.getGrupo()
            });

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaBusca = new javax.swing.JTable();
        EdCod = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtOk = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        EdBuscaItem = new javax.swing.JTextField();
        EdDescricao = new javax.swing.JTextField();
        EdCodItem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TabelaBusca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "C??digo", "Descri????o"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaBuscaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaBusca);
        if (TabelaBusca.getColumnModel().getColumnCount() > 0) {
            TabelaBusca.getColumnModel().getColumn(0).setMinWidth(0);
            TabelaBusca.getColumnModel().getColumn(0).setPreferredWidth(0);
            TabelaBusca.getColumnModel().getColumn(0).setMaxWidth(0);
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

        EdBuscaItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Ferramenta C??digo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscaItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaItemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdBuscaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtOk))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtBuscar)
                    .addComponent(EdBuscaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtSair)
                    .addComponent(BtOk)
                    .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            listarItem();
            EdCod.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nC??DIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOkActionPerformed

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Voc?? deve selecionar o procedimento na tabela!");

        } else {
            try {
                this.cod = Integer.parseInt(this.EdCod.getText());
                this.codItem = this.EdCodItem.getText();
                this.descricao = this.EdDescricao.getText();
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nC??DIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(rootPane, ex);
            }

        }

    }//GEN-LAST:event_BtOkActionPerformed

    private void TabelaBuscaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaBuscaMouseClicked
        if (TabelaBusca.getSelectedRow() != -1) {

            EdCod.setText(TabelaBusca.getValueAt(TabelaBusca.getSelectedRow(), 0).toString());
            EdCodItem.setText(TabelaBusca.getValueAt(TabelaBusca.getSelectedRow(), 1).toString());
            EdDescricao.setText(TabelaBusca.getValueAt(TabelaBusca.getSelectedRow(), 2).toString());
        }

    }//GEN-LAST:event_TabelaBuscaMouseClicked

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void EdBuscaItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaItemKeyPressed
        try {
            listarItem();
            EdCod.setText("");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nC??DIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EdBuscaItemKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FDBuscaFerramenta dialog = null;
            try {
                dialog = new FDBuscaFerramenta(new javax.swing.JFrame(), true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FDBuscaFerramenta.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    private javax.swing.JTextField EdBuscaItem;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdCodItem;
    private javax.swing.JTextField EdDescricao;
    private javax.swing.JTable TabelaBusca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/hs-icon.png")));
    }
}
