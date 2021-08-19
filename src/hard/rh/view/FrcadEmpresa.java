package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.EmpresaDao;
import hard.rh.model.Empresa;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrcadEmpresa extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    FDErroOcorrido fdErroOcorrido;

    public FrcadEmpresa(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        habilitarB(1);
        title = "Manutenção de Empresas - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrcadEmpresa";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
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
        this.setLocation((d.width - this.getSize().width) / 3, (d.height - this.getSize().height) / 2);
    }

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
                EdCod.setEditable(true);
                EdNome.setEnabled(false);
                EdMail.setEnabled(false);
                EdBuscaNome.setEnabled(true);
                break;

            case 2:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtBuscarNome.setEnabled(false);
                BtCancelar.setEnabled(true);
                EdCod.setEnabled(true);
                EdCod.setEditable(true);
                EdNome.setEnabled(true);
                EdMail.setEnabled(true);
                EdCod.requestFocus();
                EdBuscaNome.setEnabled(false);
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtBuscarNome.setEnabled(false);
                BtCancelar.setEnabled(true);
                EdCod.setEnabled(true);
                EdCod.setEditable(false);
                EdNome.setEnabled(true);
                EdMail.setEnabled(true);
                EdBuscaNome.setEnabled(false);
                EdNome.requestFocus();
                break;
        }
    }

    public void limpaCampos() {
        EdCod.setText(null);
        EdNome.setText(null);
        EdMail.setText(null);
        EdBuscaNome.setText(null);
        JcCliente.setSelected(false);
        JcFornecedor.setSelected(false);

    }

    public void checkCod() throws SQLException {
        EmpresaDao pdao = new EmpresaDao();
        if (EdCod.getText().equals("")
                || EdNome.getText().equals("") || EdMail.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else {
            try {
                if (pdao.checkCod(EdCod.getText())) {

                    JOptionPane.showMessageDialog(null, "Empresa já possui cadastro");

                } else {
                    salvarEmp();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void salvarEmp() {

        Empresa e = new Empresa();
        EmpresaDao dao = new EmpresaDao();

        habilitarB(2);
        e.setCodEmp(Integer.parseInt(EdCod.getText()));
        e.setNomeEmp(EdNome.getText());
        e.setMailEmp(EdMail.getText());
        if (JcCliente.isSelected()) {
            e.setTipoEmp("Cliente");
        } else if (JcFornecedor.isSelected()) {
            e.setTipoEmp("Fornecedor");
        } else {
            e.setTipoEmp("Não Informado");
        }

        try {
            dao.createEmp(e);
            limpaCampos();
            habilitarB(1);
            listarEmp();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listarEmp() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        EmpresaDao pdao = new EmpresaDao();
        String tipo;

        if (JcCliente.isSelected()) {
            tipo = "cliente";

        } else if (JcFornecedor.isSelected()) {
            tipo = "fornecedor";
        } else {
            tipo = "";
        }

        if (EdCod.getText().equals("")) {
            if (EdBuscaNome.getText().equals("")) {
                try {
                    for (Empresa p : pdao.readEmp(tipo)) {
                        modelo.addRow(new Object[]{
                            p.getCodEmp(),
                            p.getNomeEmp(),
                            p.getTipoEmp(),
                            p.getMailEmp()

                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar empresas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            } else {
                try {
                    for (Empresa p : pdao.readEmpForDesc(EdBuscaNome.getText(), tipo)) {
                        modelo.addRow(new Object[]{
                            p.getCodEmp(),
                            p.getNomeEmp(),
                            p.getTipoEmp(),
                            p.getMailEmp()
                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar empresas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            }

        } else {
            listaEmpCod();
        }

    }

    public void validarCodList() {
        EmpresaDao pdao = new EmpresaDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código da Empresa!");
        } else {

            try {
                if (pdao.checkCod(EdCod.getText())) {
                    listaEmpCod();
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa não encontrada");
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    public void listaEmpCod() {

        EmpresaDao pdao = new EmpresaDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do empário!");
        } else {

            try {
                for (Empresa emp : pdao.readEmpForCod(EdCod.getText())) {
                    EdCod.setText(Integer.toString(emp.getCodEmp()));
                    EdNome.setText(emp.getNomeEmp());
                    EdMail.setText(emp.getMailEmp());
                    String tipoEmp = emp.getTipoEmp();
                    if (tipoEmp.equals("Cliente")) {
                        JcCliente.setSelected(true);
                        JcFornecedor.setSelected(false);
                    } else if (tipoEmp.equals("Fornecedor")) {
                        JcCliente.setSelected(false);
                        JcFornecedor.setSelected(true);
                    }

                    habilitarB(3);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar empresa");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    public void editarEmp() {

        Empresa e = new Empresa();
        EmpresaDao dao = new EmpresaDao();

        if (EdNome.getText().equals("") || EdCod.getText().equals("") || EdMail.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO\n Você deve preencher todos os campos");
        } else {

            e.setNomeEmp(EdNome.getText());
            e.setMailEmp(EdMail.getText());
            e.setCodEmp(Integer.parseInt(EdCod.getText()));
            if (JcCliente.isSelected()) {
                e.setTipoEmp("Cliente");
            } else if (JcFornecedor.isSelected()) {
                e.setTipoEmp("Fornecedor");
            } else {
                e.setTipoEmp("Não Informado");
            }
            try {
                dao.update(e);
                limpaCampos();
                listarEmp();
                habilitarB(1);

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }

    }

    public void deletarEmp() {
        Empresa e = new Empresa();
        EmpresaDao dao = new EmpresaDao();
        habilitarB(1);
        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do procedimento para excluír");
        } else {
            e.setCodEmp(Integer.parseInt(EdCod.getText()));
            try {

                String nome = EdNome.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o cadastro do procedimento  " + nome, "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    dao.delete(e);
                    habilitarB(1);
                    limpaCampos();
                    listarEmp();
                } else {
                    JOptionPane.showMessageDialog(null, "Exclusão cancelada");
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        BtNovo = new javax.swing.JButton();
        EdBuscaNome = new javax.swing.JTextField();
        JcCliente = new javax.swing.JCheckBox();
        JcFornecedor = new javax.swing.JCheckBox();
        EdMail = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 145, 90, 30));

        EdNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 290, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Tipo", "E-mail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 610, 240));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel1.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 15, 90, 30));

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdBuscaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 330, -1));

        JcCliente.setText("Cliente");
        JcCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcClienteActionPerformed(evt);
            }
        });
        jPanel1.add(JcCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        JcFornecedor.setText("Fornecedor");
        JcFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcFornecedorActionPerformed(evt);
            }
        });
        jPanel1.add(JcFornecedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        EdMail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "E-mail:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 310, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 610, -1));

        jTabbedPane1.addTab("Cadastrar Empresa", jPanel1);

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
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 80, 33));

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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCod.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarEmp();
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {
            checkCod();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarEmp();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        listarEmp();
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (tabela.getSelectedRow() != -1) {
            EdCod.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            EdNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            boolean tipoEmp = tabela.getValueAt(tabela.getSelectedRow(), 2).toString().equals("Fornecedor");
            if (!tipoEmp) {
                JcCliente.setSelected(true);
                JcFornecedor.setSelected(false);
            } else if (tipoEmp) {
                JcCliente.setSelected(false);
                JcFornecedor.setSelected(true);
            } else {
                JcCliente.setSelected(true);
                JcFornecedor.setSelected(true);
            }

            EdMail.setText(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
            habilitarB(3);
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed
        listarEmp();
    }//GEN-LAST:event_EdBuscaNomeKeyPressed

    private void JcClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcClienteActionPerformed
        JcFornecedor.setSelected(false);
    }//GEN-LAST:event_JcClienteActionPerformed

    private void JcFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcFornecedorActionPerformed
        JcCliente.setSelected(false);
    }//GEN-LAST:event_JcFornecedorActionPerformed

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaKeyPressed

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
    private javax.swing.JTextField EdMail;
    private javax.swing.JTextField EdNome;
    private javax.swing.JCheckBox JcCliente;
    private javax.swing.JCheckBox JcFornecedor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
