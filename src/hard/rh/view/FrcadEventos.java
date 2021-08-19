package hard.rh.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.EventoDao;
import hard.rh.model.Evento;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrcadEventos extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    FDErroOcorrido fdErroOcorrido;

    public FrcadEventos(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        habilitarB(1);
        title = "Manutenção de Eventos - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrcadEventos";
            u.setFr_codTela(codTela);
            ContVersaoDao vdao = new ContVersaoDao();
            for (ContVersao c : vdao.listTela(codTela)) {
                u.setFr_versaoTela(c.getVersaoAtualTela());
            }
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 6, (d.height - this.getSize().height) / 3);
    }

    //HABILITA OU DESABILITAS ITENS DO FRONT
    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscarNome.setEnabled(true);
                EdCod.setEnabled(true);
                EdNome.setEnabled(false);
                break;

            case 2:

                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtBuscarNome.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdCod.setEnabled(true);
                EdNome.setEnabled(true);
                EdCod.requestFocus();
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtBuscarNome.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdCod.setEnabled(false);
                EdNome.setEnabled(true);
                EdNome.requestFocus();
                break;
        }
    }

    //LIMPA CAMPOS DO FRONT
    public void limpaCampos() {
        EdCod.setText(null);
        EdNome.setText(null);
        JbTipoEvento.setSelectedItem("<SELECIONE>");
        JbTipoProcessaEvento.setSelectedItem("<SELECIONE>");

    }

    public void maiorCod() {

        EventoDao cdao = new EventoDao();

        try {
            for (Evento p : cdao.readMaiorCod()) {

                int cod;
                cod = p.getCodEvento();
                EdCod.setText(Integer.toString(cod));

            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    //VERIFICA SE O PROCEDIMENTO POSSUI CADASTRO
    public void checkCod() throws SQLException {
        EventoDao pdao = new EventoDao();
        if (EdCod.getText().equals("") || EdNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else if (JbTipoEvento.getSelectedItem().equals("")) {

            JOptionPane.showMessageDialog(null, "Um tipo de evento deve ser selecionado!");

        } else {
            try {
                if (pdao.checkCod(EdCod.getText())) {

                    JOptionPane.showMessageDialog(null, "Evento já possui cadastro");

                } else if (JbTipoProcessaEvento.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Um tipo de Processamento deve ser selecionado!");
                } else {

                    salvarEvento();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    //CADASTRA NOVO PROCEDIMENTO
    public void salvarEvento() {

        Evento e = new Evento();
        EventoDao dao = new EventoDao();
        habilitarB(2);
        e.setCodEvento(Integer.parseInt(EdCod.getText()));
        e.setDescriEvento(EdNome.getText());

        if (JbTipoEvento.getSelectedItem().equals("Desc")) {
            e.setTipoEvento("Desc");
        } else {
            e.setTipoEvento("Venc");
        }

        if (JbTipoProcessaEvento.getSelectedItem().equals("Valor")) {
            e.setTipoProcessaEvento("Valor");
        } else {
            e.setTipoProcessaEvento("Horas");
        }
        try {
            dao.createEve(e);
            limpaCampos();
            habilitarB(1);
            listarEvento();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar evento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    //LISTA PROCEDIMETNOS
    public void listarEvento() {

        habilitarB(1);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        EventoDao pdao = new EventoDao();

        if (EdCod.getText().equals("")) {
            if (EdBuscaNome.getText().equals("")) {
                try {
                    modelo.setNumRows(0);
                    for (Evento p : pdao.readEve()) {
                        modelo.addRow(new Object[]{
                            p.getCodEvento(),
                            p.getDescriEvento(),
                            p.getTipoEvento(),
                            p.getTipoProcessaEvento()

                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar evento");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            } else {
                try {
                    modelo.setNumRows(0);
                    for (Evento p : pdao.readEveForDesc(EdBuscaNome.getText())) {
                        modelo.addRow(new Object[]{
                            p.getCodEvento(),
                            p.getDescriEvento(),
                            p.getTipoEvento(),
                            p.getTipoProcessaEvento()
                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar evento");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            }

        } else {
            listaProCod();
        }

    }

    //VERIFICA O CÓDIGO CA PROCEDIMENTO CADASTRADO
    public void validarCodList() {
        EventoDao pdao = new EventoDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código da Evento!");
        } else {

            try {
                if (pdao.checkCod(EdCod.getText())) {
                    listaProCod();
                } else {
                    JOptionPane.showMessageDialog(null, "Evento não encontrada");
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    //LISTA EVENTO POR CÓDIGO
    public void listaProCod() {

        EventoDao pdao = new EventoDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código");
        } else {

            try {
                for (Evento usu : pdao.readEveForCod(EdCod.getText())) {
                    EdCod.setText(Integer.toString(usu.getCodEvento()));
                    EdNome.setText(usu.getDescriEvento());

                    habilitarB(3);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    //EDITA PROCEDIMENTO
    public void editarEve() {

        Evento e = new Evento();
        EventoDao dao = new EventoDao();

        if (EdNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO\n Você deve preencher todos os campos");
        } else {

            e.setDescriEvento(EdNome.getText());
            e.setCodEvento(Integer.parseInt(EdCod.getText()));

            if (JbTipoEvento.getSelectedItem().equals("Desc")) {
                e.setTipoEvento("Desc");
            } else {
                e.setTipoEvento("Venc");
            }

            if (JbTipoProcessaEvento.getSelectedItem().equals("Valor")) {
                e.setTipoProcessaEvento("Valor");
            } else {
                e.setTipoProcessaEvento("Horas");
            }

            try {
                dao.update(e);
                limpaCampos();
                listarEvento();

                habilitarB(1);
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }
    }

    //DELETA PROCEDIMENTO
    public void deletarPro() {
        Evento e = new Evento();
        EventoDao dao = new EventoDao();
        habilitarB(1);
        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do procedimento para excluír");
        } else {
            e.setCodEvento(Integer.parseInt(EdCod.getText()));
            try {

                String nome = EdNome.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o cadastro do procedimento  " + nome, null, JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    dao.delete(e);
                    habilitarB(1);
                    limpaCampos();
                    listarEvento();
                } else {
                    JOptionPane.showMessageDialog(null, "Exclusão cancelada");
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar procedimento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        EdCod = new javax.swing.JTextField();
        BtBuscarNome = new javax.swing.JButton();
        EdNome = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        BtNovo = new javax.swing.JButton();
        EdBuscaNome = new javax.swing.JTextField();
        JbTipoEvento = new javax.swing.JComboBox<>();
        JbTipoProcessaEvento = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, -1));

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 90, 30));

        EdNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 440, -1));

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

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 460, 60));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Evento", "Descrição", "Tipo", "Tipo Processamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(80);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(0).setMaxWidth(80);
            tabela.getColumnModel().getColumn(1).setMinWidth(150);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setMinWidth(50);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(2).setMaxWidth(50);
            tabela.getColumnModel().getColumn(3).setMinWidth(70);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 460, 210));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel1.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, 30));

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdBuscaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 250, -1));

        JbTipoEvento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<SELECIONE>", "Desc", "Venc" }));
        jPanel1.add(JbTipoEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        JbTipoProcessaEvento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<SELECIONE>", "Valor", "Horas" }));
        jPanel1.add(JbTipoProcessaEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jTabbedPane1.addTab("Cadastrar Evento", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarEve();
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {
            checkCod();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("valida código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarPro();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        listarEvento();
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (tabela.getSelectedRow() != -1) {
            EdCod.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            EdNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());

            String tipoEve = tabela.getValueAt(tabela.getSelectedRow(), 2).toString();
            String tipoProcessaEve = tabela.getValueAt(tabela.getSelectedRow(), 3).toString();

            JbTipoEvento.setSelectedItem(tipoEve);
            JbTipoProcessaEvento.setSelectedItem(tipoProcessaEve);

            habilitarB(3);
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void EdNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNomeKeyPressed

    }//GEN-LAST:event_EdNomeKeyPressed

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed
        listarEvento();
    }//GEN-LAST:event_EdBuscaNomeKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscarNome;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JTextField EdBuscaNome;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdNome;
    private javax.swing.JComboBox<String> JbTipoEvento;
    private javax.swing.JComboBox<String> JbTipoProcessaEvento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
