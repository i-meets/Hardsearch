package hard.engenharia.view;

import hard.engenharia.dao.FerramentaDao;
import hard.engenharia.model.Ferramenta;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrCadFerramenta extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "0.0";

    int cod_fer = 0;

    public FrCadFerramenta(String usuario, String ipDesktop, String nameDesktop) {
        title = "Cadastro de Ferramentas - V" + versao;
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
            String codTela = "FrCadFerramenta";
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

                EdCodFerramenta.setEnabled(false);
                EdCodFerramenta.setEditable(false);
                CbGrupo.setEnabled(false);
                break;
            case 2:
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtBuscar.setEnabled(false);
                BtExcluir.setEnabled(false);

                EdCodFerramenta.setEnabled(true);
                EdCodFerramenta.setEditable(true);
                CbGrupo.setEnabled(true);
                break;
            case 3:
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtBuscar.setEnabled(false);
                BtExcluir.setEnabled(true);

                EdCodFerramenta.setEditable(false);
                EdCodFerramenta.setEnabled(true);
                CbGrupo.setEnabled(true);
                break;
        }
    }

    public void limpaCampos() {
        EdCodFerramenta.setText(null);
        CbGrupo.setSelectedItem("<Selecione>");
        cod_fer = 0;
    }

    public void validaFerramenta() {
        try {

            FerramentaDao dao = new FerramentaDao();
            if (dao.checkCadFer(EdCodFerramenta.getText())) {
                JOptionPane.showMessageDialog(rootPane, "Já existe uma ferramenta cadastrada com este código", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                salvarFerramenta();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void salvarFerramenta() {

        try {

            Ferramenta f = new Ferramenta();
            FerramentaDao dao = new FerramentaDao();
            f.setCodFerramenta(EdCodFerramenta.getText());
            f.setGrupo(CbGrupo.getSelectedItem().toString());
            dao.createFerramenta(f);
            listaFerramenta();
            habilitarB(1);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro: " + ex);
        }

    }

    public void listaFerramenta() {
        try {

            FerramentaDao dao = new FerramentaDao();
            DefaultTableModel modelo = (DefaultTableModel) TbListaFerramenta.getModel();
            modelo.setNumRows(0);
            for (Ferramenta m : dao.readFerramenta(EdBuscaCodigo.getText())) {
                modelo.addRow(new Object[]{
                    m.getCod_fer(),
                    m.getCodFerramenta(),
                    m.getGrupo()
                });
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void editarFerramenta() {
        try {
            FerramentaDao dao = new FerramentaDao();
            Ferramenta f = new Ferramenta();
            f.setGrupo(CbGrupo.getSelectedItem().toString());
            f.setCod_fer(cod_fer);
            dao.updateFerramenta(f);
            listaFerramenta();
            habilitarB(1);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void deletarFerramenta() throws ClassNotFoundException {
        FerramentaDao dao = new FerramentaDao();
        Ferramenta f = new Ferramenta();
        f.setCod_fer(cod_fer);
        dao.deleteFerramenta(f);
        listaFerramenta();
        habilitarB(1);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaFerramenta = new javax.swing.JTable();
        EdCodFerramenta = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        EdBuscaCodigo = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        CbGrupo = new javax.swing.JComboBox<>();
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

        TbListaFerramenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Codigo", "Grupo"
            }
        ));
        TbListaFerramenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaFerramentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaFerramenta);
        if (TbListaFerramenta.getColumnModel().getColumnCount() > 0) {
            TbListaFerramenta.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaFerramenta.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaFerramenta.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        EdCodFerramenta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdCodFerramenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdCodFerramentaMouseClicked(evt);
            }
        });

        EdBuscaCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaCodigoKeyPressed(evt);
            }
        });

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("BUSCAR");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        CbGrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "AÇO RAPIDO", "BITS ESPECIAIS", "BROCAS ESPECIAIS", "BROCHAS", "CANAL HELICOIDAL", "CANAL RETO", "CENTRAR", "CINTA", "COMPONENTES", "CORTE/CANAL", "DISCO", "DIVERSOS", "FERRAMENTA PERFILADA", "FIXAÇÃO DE FERRAMENTAS", "FIXAÇÃO DE MATERIAL", "FOLHA", "FRESAMENTO", "FURAÇÃO", "HASTE CÔNICA", "HASTE PARALELA", "LIMAS", "LIMAS ROTATIVAS", "MACHOS ESPECIAIS", "METAL DURO", "ÓLEOS/GRAXAS", "PONTA HELICOIDAL", "PORTA-FERRAMENTA", "ROSCA MÉTRICA", "ROSCA POLEGADA", "ROSQUEAMENTO", "SERRA/REBARBAGEM", "SOLDA", "TORNEAMENTO", "TORNEAMENTO EXTERNO", "TORNEAMENTO INTERNO", "OUTROS" }));

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtBuscar))
                    .addComponent(CbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdCodFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdCodFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Cadastro de Ferramenta", jPanel1);

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
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 80, 33));

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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        if (cod_fer == 0) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar a ferramenta que deseja deletar", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String nome = EdCodFerramenta.getText();
                int input = JOptionPane.showConfirmDialog(rootPane,
                        "Deseja mesmo excluír o cadastro da ferramenta" + nome, "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (input == 0) {

                    deletarFerramenta();
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
        if (EdCodFerramenta.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o código da ferramenta", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            if (CbGrupo.getSelectedItem().equals("<Selecione>")) {
                JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o grupo da ferramenta", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                validaFerramenta();
            }
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        if (EdCodFerramenta.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o código da ferramenta", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            if (CbGrupo.getSelectedItem().equals("<Selecione>")) {
                JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o grupo da ferramenta", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                editarFerramenta();
            }
        }
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        listaFerramenta();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void EdCodFerramentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdCodFerramentaMouseClicked

    }//GEN-LAST:event_EdCodFerramentaMouseClicked

    private void TbListaFerramentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaFerramentaMouseClicked
        cod_fer = Integer.parseInt(TbListaFerramenta.getValueAt(TbListaFerramenta.getSelectedRow(), 0).toString());
        EdCodFerramenta.setText(TbListaFerramenta.getValueAt(TbListaFerramenta.getSelectedRow(), 1).toString());
        CbGrupo.setSelectedItem(TbListaFerramenta.getValueAt(TbListaFerramenta.getSelectedRow(), 2).toString());
        habilitarB(3);
    }//GEN-LAST:event_TbListaFerramentaMouseClicked

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCodFerramenta.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void EdBuscaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaCodigoKeyPressed
        listaFerramenta();
    }//GEN-LAST:event_EdBuscaCodigoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JComboBox<String> CbGrupo;
    private javax.swing.JTextField EdBuscaCodigo;
    private javax.swing.JTextField EdCodFerramenta;
    private javax.swing.JTable TbListaFerramenta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
