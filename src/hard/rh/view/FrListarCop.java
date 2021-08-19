
package hard.rh.view;

import hard.home.view.FDErroOcorrido;
import hard.rh.dao.CoparticipacaoDao;
import hard.rh.model.Coparticipacao;
import javax.swing.table.DefaultTableModel;

public class FrListarCop extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    public FrListarCop() {
        initComponents();
        title = "Listagem de Coparticipações - V" + versao;
    }

    public void listarCoparti() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        CoparticipacaoDao pdao = new CoparticipacaoDao();

        if (EdBuscarCodFuncio.getText().equals("")) {
            try {
                for (Coparticipacao c : pdao.readCop()) {
                    modelo.addRow(new Object[]{
                        c.getCodCop(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getFr_cpfFuncionario(),
                        c.getFr_cargoFuncionario(),
                        c.getFr_setorFuncionario(),
                        c.getFr_codPro(),
                        c.getFr_nomePro(),
                        c.getFr_valorPro(),
                        c.getFr_parcelaPro(),
                        c.getLocalPro(),
                        c.getMedicoPro(),
                        c.getDataPro(),
                        c.getDataVencPro()});

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar coparticipações");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            try {
                for (Coparticipacao c : pdao.readCopForCodUser(EdBuscarCodFuncio.getText())) {
                    modelo.addRow(new Object[]{
                        c.getCodCop(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getFr_cpfFuncionario(),
                        c.getFr_cargoFuncionario(),
                        c.getFr_setorFuncionario(),
                        c.getFr_codPro(),
                        c.getFr_nomePro(),
                        c.getFr_valorPro(),
                        c.getFr_parcelaPro(),
                        c.getLocalPro(),
                        c.getMedicoPro(),
                        c.getDataPro(),
                        c.getDataVencPro()});
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar coparticipações");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        EdBuscarCodFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncio1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód Cop", "Cód Funcionário", "Nome Funcionário", "CPF", "Cargo", "Setor", "Cod. Procedi", "Procedimento", "Valor", "Nº Parcelas", "Local", "Médico", "Data Procedimento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
            tabela.getColumnModel().getColumn(1).setMinWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        EdBuscarCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarCodFuncioKeyPressed(evt);
            }
        });

        BtBuscarCodFuncio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncio1.setText("Buscar");
        BtBuscarCodFuncio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncio1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 12)); // NOI18N
        jLabel4.setText("Cód. Funcionário: ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdBuscarCodFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtBuscarCodFuncio1)
                .addContainerGap(930, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscarCodFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscarCodFuncio1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Consulta Coparticipação", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarCodFuncio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncio1ActionPerformed
        listarCoparti();
    }//GEN-LAST:event_BtBuscarCodFuncio1ActionPerformed

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
       
    }//GEN-LAST:event_tabelaKeyPressed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

    }//GEN-LAST:event_tabelaMouseClicked

    private void EdBuscarCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarCodFuncioKeyPressed
        listarCoparti();
    }//GEN-LAST:event_EdBuscarCodFuncioKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscarCodFuncio1;
    private javax.swing.JTextField EdBuscarCodFuncio;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
