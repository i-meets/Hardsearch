package hard.engenharia.view;

import hard.engenharia.dao.MaquinaDao;
import hard.engenharia.model.Maquina;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrCadMaquina extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "0.0";
    int cod_maq = 0;

    public FrCadMaquina(String usuario, String ipDesktop, String nameDesktop) {
        title = "Cadastro de Maquinas - V" + versao;
        initComponents();
        habilitarB(1);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrCadMaquina";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 3, (d.height - this.getSize().height) / 6);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:
                limpaCampos();
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtBuscar.setEnabled(true);
                BtExcluir.setEnabled(false);

                EdCodMaquina.setEnabled(false);
                EdCodMaquina.setEditable(false);
                EdDescricao.setEnabled(false);
                break;
            case 2:
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtBuscar.setEnabled(false);
                BtExcluir.setEnabled(false);

                EdCodMaquina.setEnabled(true);
                EdCodMaquina.setEditable(true);
                EdDescricao.setEnabled(true);
                break;
            case 3:
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtBuscar.setEnabled(false);
                BtExcluir.setEnabled(true);

                EdCodMaquina.setEditable(false);
                EdCodMaquina.setEnabled(true);
                EdDescricao.setEnabled(true);
                break;
        }
    }

    public void limpaCampos() {
        EdCodMaquina.setText(null);
        EdDescricao.setText(null);
        cod_maq = 0;
    }

    public void validaMaquina() {
        try {

            MaquinaDao dao = new MaquinaDao();
            if (dao.checkCadMaq(EdCodMaquina.getText())) {
                JOptionPane.showMessageDialog(rootPane, "Já existe uma ferramenta cadastrada com este código", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                salvarMaquina();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void salvarMaquina() {

        try {

            Maquina m = new Maquina();
            MaquinaDao dao = new MaquinaDao();
            m.setCodMaquina(EdCodMaquina.getText());
            m.setDescricao_maq(EdDescricao.getText());
            dao.createMaquina(m);
            listaMaquina();

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro: " + ex);
        }

    }

    public void listaMaquina() {
        try {

            MaquinaDao dao = new MaquinaDao();
            DefaultTableModel modelo = (DefaultTableModel) TbListaMaquina.getModel();
            modelo.setNumRows(0);
            for (Maquina m : dao.readMaquina(EdBuscaDescricao.getText())) {
                modelo.addRow(new Object[]{
                    m.getCod_maq(),
                    m.getCodMaquina(),
                    m.getDescricao_maq()
                });
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void editarMaquina() {
        try {
            MaquinaDao dao = new MaquinaDao();
            Maquina f = new Maquina();
            f.setDescricao_maq(EdDescricao.getText());
            f.setCod_maq(cod_maq);
            dao.updateMaquina(f);
            listaMaquina();
            habilitarB(1);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void deletarMaquina() throws ClassNotFoundException {
        MaquinaDao dao = new MaquinaDao();
        Maquina m = new Maquina();
        m.setCod_maq(cod_maq);
        dao.deleteMaquina(m);
        listaMaquina();
        habilitarB(1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaMaquina = new javax.swing.JTable();
        EdCodMaquina = new javax.swing.JTextField();
        EdDescricao = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        EdBuscaDescricao = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TbListaMaquina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Codigo", "Descrição"
            }
        ));
        TbListaMaquina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaMaquinaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaMaquina);
        if (TbListaMaquina.getColumnModel().getColumnCount() > 0) {
            TbListaMaquina.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaMaquina.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaMaquina.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        EdCodMaquina.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdBuscaDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaDescricaoKeyPressed(evt);
            }
        });

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("BUSCAR");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdDescricao)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(EdBuscaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtBuscar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(EdCodMaquina, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdCodMaquina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        jTabbedPane1.addTab("Cadastro de Máquinas", jPanel1);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 100, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 80, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 100, 33));

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvarEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 100, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 33));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        if (cod_maq == 0) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar a máquina que deseja deletar", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String nome = EdCodMaquina.getText();
                int input = JOptionPane.showConfirmDialog(rootPane,
                        "Deseja mesmo excluír o cadastro da máquina " + nome, "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (input == 0) {

                    deletarMaquina();
                }

            } catch (ClassNotFoundException ex) {
                System.out.println("Erro: " + ex);
            }
        }

    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        if (EdCodMaquina.getText().equals("") || EdDescricao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve inserir as informações da máquina", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            validaMaquina();
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        if (EdCodMaquina.getText().equals("") || EdDescricao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve inserir as informações da máquina", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            editarMaquina();
        }
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        listaMaquina();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCodMaquina.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void EdBuscaDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaDescricaoKeyPressed
        listaMaquina();
    }//GEN-LAST:event_EdBuscaDescricaoKeyPressed

    private void TbListaMaquinaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaMaquinaMouseClicked
        cod_maq = Integer.parseInt(TbListaMaquina.getValueAt(TbListaMaquina.getSelectedRow(), 0).toString());
        EdCodMaquina.setText(TbListaMaquina.getValueAt(TbListaMaquina.getSelectedRow(), 1).toString());
        EdDescricao.setText(TbListaMaquina.getValueAt(TbListaMaquina.getSelectedRow(), 2).toString());
        habilitarB(3);
    }//GEN-LAST:event_TbListaMaquinaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JTextField EdBuscaDescricao;
    private javax.swing.JTextField EdCodMaquina;
    private javax.swing.JTextField EdDescricao;
    private javax.swing.JTable TbListaMaquina;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
