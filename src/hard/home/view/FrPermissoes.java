package hard.home.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Permissao;
import hard.home.model.Usuario;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrPermissoes extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSÃO
    String versao = "1.0-21.0625.0";
    FDErroOcorrido fdErroOcorrido;

    int codUser = 0;

    public FrPermissoes(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle de Permissões - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrPermissoes";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

        listarUsuarios();

        btAdcTelas.setToolTipText("CADASTRAR NOVO PERFIL DE ACESSOS");
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    //LISTA USUÁRIO
    public void listarUsuarios() {

        DefaultTableModel modelo = (DefaultTableModel) tbListaUser.getModel();
        modelo.setNumRows(0);
        UsuarioDao pdao = new UsuarioDao();

        if (EdBuscarNome.getText().equals("")) {
            try {
                for (Usuario u : pdao.readUser()) {
                    modelo.addRow(new Object[]{
                        u.getCodUsuario(),
                        u.getLoginUsuario(),
                        u.getNomeUsuario()
                    });
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            try {
                for (Usuario u : pdao.readUserForDesc(EdBuscarNome.getText())) {
                    modelo.addRow(new Object[]{
                        u.getCodUsuario(),
                        u.getLoginUsuario(),
                        u.getNomeUsuario()
                    });
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void listTelas() throws ClassNotFoundException, SQLException {
        if (codUser != 0) {
            DefaultTableModel modelo = (DefaultTableModel) tbListaTelas.getModel();
            modelo.setNumRows(0);
            PermissaoDao dao = new PermissaoDao();
            try {
                if (EdBuscaTela.getText().equals("")) {
                    for (Permissao p : dao.listTela(codUser, "")) {
                        modelo.addRow(new Object[]{
                            p.getCod(),
                            p.getFr_codTela(),
                            p.isPermite()
                        });
                    }
                } else {
                    for (Permissao p : dao.listTela(codUser, EdBuscaTela.getText())) {
                        modelo.addRow(new Object[]{
                            p.getCod(),
                            p.getFr_codTela(),
                            p.isPermite()
                        });

                    }
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {

        }

    }

    public void listaTodasTelas() {
        DefaultTableModel modelo = (DefaultTableModel) tbListaTelas.getModel();
        modelo.setNumRows(0);
        ContVersaoDao dao = new ContVersaoDao();
        try {
            for (ContVersao c : dao.listTelas()) {
                modelo.addRow(new Object[]{
                    "",
                    c.getCodTela()

                });
            }
            salvarPermissoes();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void salvarPermissoes() {
        try {

            int totalRows = tbListaTelas.getRowCount();
            Permissao p = new Permissao();
            PermissaoDao dao = new PermissaoDao();
            for (int i = 0; i < totalRows; i++) {

                String codTela = (String) tbListaTelas.getModel().getValueAt(i, 1);
                p.setFr_codTela(codTela);
                p.setFr_codUser(codUser);
                p.setPermite(false);

                if (dao.checkCreatePermissao(codTela, codUser)) {
                    dao.updatePermissao(p);
                } else {
                    dao.createPermissao(p);
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar permissões");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnListaUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaUser = new javax.swing.JTable();
        EdBuscarNome = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListaTelas = new javax.swing.JTable();
        EdBuscaTela = new javax.swing.JTextField();
        btAdcTelas = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        pnListaUser.setBackground(new java.awt.Color(255, 255, 255));

        tbListaUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codUser", "Usuário", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListaUser.setMinimumSize(new java.awt.Dimension(0, 0));
        tbListaUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListaUser);

        EdBuscarNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscarNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarNomeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnListaUserLayout = new javax.swing.GroupLayout(pnListaUser);
        pnListaUser.setLayout(pnListaUserLayout);
        pnListaUserLayout.setHorizontalGroup(
            pnListaUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnListaUserLayout.createSequentialGroup()
                .addGroup(pnListaUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(EdBuscarNome, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnListaUserLayout.setVerticalGroup(
            pnListaUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnListaUserLayout.createSequentialGroup()
                .addComponent(EdBuscarNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Listagem de Usuários", pnListaUser);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tbListaTelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Tela", "Permissão"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListaTelas.setMaximumSize(new java.awt.Dimension(0, 0));
        tbListaTelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaTelasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbListaTelas);
        if (tbListaTelas.getColumnModel().getColumnCount() > 0) {
            tbListaTelas.getColumnModel().getColumn(0).setMinWidth(0);
            tbListaTelas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbListaTelas.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        EdBuscaTela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaTela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdBuscaTelaMouseClicked(evt);
            }
        });
        EdBuscaTela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaTelaKeyPressed(evt);
            }
        });

        btAdcTelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/inserir.png"))); // NOI18N
        btAdcTelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcTelasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(EdBuscaTela, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdcTelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EdBuscaTela)
                    .addComponent(btAdcTelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Manutenção de Liberações", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbListaUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaUserMouseClicked
        codUser = Integer.parseInt(tbListaUser.getValueAt(tbListaUser.getSelectedRow(), 0).toString());
        EdBuscaTela.setText("");
        if (codUser != 0) {
            try {
                listTelas();
            } catch (ClassNotFoundException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("click fora da área");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            System.out.println("usuário não encontrado");
        }
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_tbListaUserMouseClicked

    private void EdBuscarNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarNomeKeyPressed
        listarUsuarios();
    }//GEN-LAST:event_EdBuscarNomeKeyPressed

    private void btAdcTelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcTelasActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "O processo de importação de tela irá apagar as permissões atuais, deseja prosseguir?", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            listaTodasTelas();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Impostação cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btAdcTelasActionPerformed

    private void tbListaTelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaTelasMouseClicked

        Permissao p = new Permissao();
        PermissaoDao dao = new PermissaoDao();
        try {
            String codTela = (String) tbListaTelas.getValueAt(tbListaTelas.getSelectedRow(), 1).toString();
            p.setFr_codTela(codTela);
            p.setFr_codUser(codUser);
            p.setPermite((boolean) tbListaTelas.getValueAt(tbListaTelas.getSelectedRow(), 2));
            if (dao.checkCreatePermissao(codTela, codUser)) {
                dao.updatePermissao(p);
            } else {
                dao.createPermissao(p);
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar telas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_tbListaTelasMouseClicked

    private void EdBuscaTelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdBuscaTelaMouseClicked

    }//GEN-LAST:event_EdBuscaTelaMouseClicked

    private void EdBuscaTelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaTelaKeyPressed
        try {
            listTelas();
        } catch (ClassNotFoundException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdBuscaTelaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EdBuscaTela;
    private javax.swing.JTextField EdBuscarNome;
    private javax.swing.JButton btAdcTelas;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnListaUser;
    private javax.swing.JTable tbListaTelas;
    private javax.swing.JTable tbListaUser;
    // End of variables declaration//GEN-END:variables
}
