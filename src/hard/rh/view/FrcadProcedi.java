package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.ProcedimentoDao;
import hard.rh.model.Procedimento;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrcadProcedi extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrcadProcedi(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Manutenção de Procedimentos - V" + versao;

        habilitarB(1);
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrcadProcedi";
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
        this.setLocation((d.width - this.getSize().width) / 8, (d.height - this.getSize().height) / 6);
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
                EdCod.setEditable(true);
                EdParcela.setEnabled(false);
                EdValor.setEnabled(false);
                EdNome.setEnabled(false);
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
                EdParcela.setEnabled(true);
                EdValor.setEnabled(true);
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
                EdParcela.setEnabled(true);
                EdValor.setEnabled(true);
                EdCod.setEnabled(true);
                EdCod.setEditable(false);
                EdNome.setEnabled(true);
                EdNome.requestFocus();
                break;
        }
    }

    //LIMPA CAMPOS DO FRONT
    public void limpaCampos() {
        EdCod.setText(null);
        EdNome.setText(null);
        EdParcela.setText(null);
        EdValor.setText(null);
        EdBuscaNome.setText(null);

    }

    public void maiorCod() {

        ProcedimentoDao cdao = new ProcedimentoDao();

        try {
            for (Procedimento p : cdao.readMaiorCod()) {

                int cod;
                cod = p.getCodPro();
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
        ProcedimentoDao pdao = new ProcedimentoDao();
        if (EdCod.getText().equals("")
                || EdNome.getText().equals("")
                || EdValor.getText().equals("")
                || EdParcela.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (pdao.checkCod(EdCod.getText())) {

                    JOptionPane.showMessageDialog(rootPane, "Procedimento já possui cadastro", "Atenção", JOptionPane.WARNING_MESSAGE);

                } else {
                    salvarPro();
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
    public void salvarPro() {
        try {

            Procedimento e = new Procedimento();
            ProcedimentoDao dao = new ProcedimentoDao();
            habilitarB(2);
            e.setCodPro(Integer.parseInt(EdCod.getText()));
            e.setNomePro(EdNome.getText());
            e.setCodPro(Integer.parseInt(EdCod.getText()));
            String valor = EdValor.getText();
            e.setValorPro(Double.parseDouble(valor.replace(',', '.')));
            e.setParcelaPro(Integer.parseInt(EdParcela.getText()));
            int status = 1;
            if (JbStatus.getSelectedItem().equals("Inativo")) {
                status = 0;
            }
            e.setStatusPro(status);

            dao.createPro(e);
            limpaCampos();
            habilitarB(1);
            listarPro();

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar procedimento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    //LISTA PROCEDIMETNOS
    public void listarPro() {

        habilitarB(1);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        ProcedimentoDao pdao = new ProcedimentoDao();
        int status = 1;
        if (JbInativo.isSelected()) {
            status = 0;
        }

        if (EdCod.getText().equals("")) {
            if (EdBuscaNome.getText().equals("")) {
                try {
                    modelo.setNumRows(0);

                    for (Procedimento p : pdao.readPro(status)) {
                        modelo.addRow(new Object[]{
                            p.getCodPro(),
                            p.getNomePro(),
                            p.getValorPro(),
                            p.getParcelaPro(),
                            p.getStatusPro()

                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar procedimentos");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            } else {
                try {
                    modelo.setNumRows(0);
                    for (Procedimento p : pdao.readProForDesc(EdBuscaNome.getText(), status)) {
                        modelo.addRow(new Object[]{
                            p.getCodPro(),
                            p.getNomePro(),
                            p.getValorPro(),
                            p.getParcelaPro(),
                            p.getStatusPro()
                        });
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar procedimentos");
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
        ProcedimentoDao pdao = new ProcedimentoDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código da Procedimento", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCod(EdCod.getText())) {
                    listaProCod();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Procedimento não encontrada", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    //LISTA PROCEDIMENTO POR CÓDIGO
    public void listaProCod() {

        ProcedimentoDao pdao = new ProcedimentoDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do usuário", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                for (Procedimento usu : pdao.readProForCod(Integer.parseInt(EdCod.getText()))) {
                    EdCod.setText(Integer.toString(usu.getCodPro()));
                    EdNome.setText(usu.getNomePro());
                    EdValor.setText(Double.toString(usu.getValorPro()));
                    EdParcela.setText(Integer.toString(usu.getParcelaPro()));
                    int status = usu.getStatusPro();
                    switch (status) {
                        case 1:
                            JbStatus.setSelectedItem("Ativo");
                            break;
                        case 0:
                            JbStatus.setSelectedItem("Inativo");
                            break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "Erro_list_status_pro", "Erro", JOptionPane.ERROR_MESSAGE);
                            break;
                    }

                    habilitarB(3);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar procedimento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    //EDITA PROCEDIMENTO
    public void editarPro() {

        Procedimento e = new Procedimento();
        ProcedimentoDao dao = new ProcedimentoDao();

        if (EdNome.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            e.setNomePro(EdNome.getText());
            String valor = EdValor.getText();
            e.setValorPro(Double.parseDouble(valor.replace(",", ".")));
            e.setParcelaPro(Integer.parseInt(EdParcela.getText()));
            e.setCodPro(Integer.parseInt(EdCod.getText()));
            int status = 1;
            if (JbStatus.getSelectedItem().equals("Inativo")) {
                status = 0;
            }
            e.setStatusPro(status);

            try {
                dao.update(e);
                limpaCampos();
                listarPro();

                habilitarB(1);
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar procedimento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }
    }

    //DELETA PROCEDIMENTO
    public void deletarPro() {
        Procedimento e = new Procedimento();
        ProcedimentoDao dao = new ProcedimentoDao();
        habilitarB(1);
        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do procedimento para excluír", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            e.setCodPro(Integer.parseInt(EdCod.getText()));
            try {

                String nome = EdNome.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        "Deseja mesmo excluír o cadastro do procedimento '" + nome + "' ", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    dao.delete(e);
                    habilitarB(1);
                    limpaCampos();
                    listarPro();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Exclusão cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        EdBuscaNome = new javax.swing.JTextField();
        JbStatus = new javax.swing.JComboBox<>();
        JbInativo = new javax.swing.JCheckBox();
        EdValor = new javax.swing.JFormattedTextField();
        EdCod = new javax.swing.JFormattedTextField();
        EdParcela = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 90, 30));

        EdNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 500, -1));

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
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 80, 33));

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

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 650, 60));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Procedimento", "Descrição do Procedimento", "Valor", "Nº Parcelas", "status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false
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
            tabela.getColumnModel().getColumn(0).setMinWidth(30);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(0).setMaxWidth(150);
            tabela.getColumnModel().getColumn(1).setMinWidth(300);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(1).setMaxWidth(600);
            tabela.getColumnModel().getColumn(4).setMinWidth(0);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 650, 230));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel1.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 90, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel6.setText("Nº Vezes");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel7.setText("R$");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, 20));

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdBuscaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 250, -1));

        JbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));
        jPanel1.add(JbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 80, -1));

        JbInativo.setText("Inativos");
        jPanel1.add(JbInativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, -1, -1));

        EdValor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel1.add(EdValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, -1));

        EdCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("######"))));
        jPanel1.add(EdCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, -1));

        EdParcela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº Parcelas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdParcela.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        jPanel1.add(EdParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 70, -1));

        jTabbedPane1.addTab("Cadastrar Procedimento", jPanel1);

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

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarPro();
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {
            checkCod();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código");
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
        listarPro();
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (tabela.getSelectedRow() != -1) {
            EdCod.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            EdNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            String valor = tabela.getValueAt(tabela.getSelectedRow(), 2).toString();
            EdValor.setText(valor.replace(".", ","));
            EdParcela.setText(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
            int status = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());
            switch (status) {
                case 1:
                    JbStatus.setSelectedItem("Ativo");
                    break;
                case 0:
                    JbStatus.setSelectedItem("Inativo");
                    break;
                default:
                    JOptionPane.showMessageDialog(rootPane, "Erro_list_status_pro", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }

            habilitarB(3);
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void EdNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNomeKeyPressed

    }//GEN-LAST:event_EdNomeKeyPressed

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed
        listarPro();
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
    private javax.swing.JFormattedTextField EdCod;
    private javax.swing.JTextField EdNome;
    private javax.swing.JFormattedTextField EdParcela;
    private javax.swing.JFormattedTextField EdValor;
    private javax.swing.JCheckBox JbInativo;
    private javax.swing.JComboBox<String> JbStatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
