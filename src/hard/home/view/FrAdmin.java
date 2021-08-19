package hard.home.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrAdmin extends javax.swing.JInternalFrame {

    private FDBuscaTelaVersao FDBuscaTelaVersao;
    String versao = "1.0-21.0810.0";

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    public FrAdmin(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        title = "Ferramentas Administrativas - V" + versao;
        listVersao();
        habilitarB(1);
        BtDow.setEnabled(false);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrAdmin";
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
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNova.setEnabled(true);
                BtUp.setEnabled(false);
                BtDow.setEnabled(false);
                BtBuscaCodTela.setEnabled(true);

                EdId.setEnabled(true);
                EdId.setEditable(true);
                EdCodTela.setEnabled(true);
                EdNovaVersao.setEnabled(false);
                JDObserva.setEnabled(false);
                JDcDataLibeVersão.setEnabled(false);
                EdVersaoAtual.setEnabled(false);

                EdId.setEditable(false);
                EdVersaoAtual.setEditable(false);

                break;

            case 2:

                BtNova.setEnabled(true);
                BtUp.setEnabled(true);
                BtDow.setEnabled(false);
                BtBuscaCodTela.setEnabled(true);

                EdId.setEnabled(true);
                EdCodTela.setEnabled(true);
                EdNovaVersao.setEnabled(true);
                JDObserva.setEnabled(true);
                JDcDataLibeVersão.setEnabled(true);
                EdVersaoAtual.setEnabled(true);

                EdId.setEditable(false);
                EdVersaoAtual.setEditable(false);

                ultimoId();

                break;

            case 3:
                BtNova.setEnabled(false);
                BtUp.setEnabled(false);
                BtDow.setEnabled(false);
                BtBuscaCodTela.setEnabled(false);

                EdId.setEnabled(true);
                EdCodTela.setEnabled(true);
                EdNovaVersao.setEnabled(true);
                JDObserva.setEnabled(true);
                JDcDataLibeVersão.setEnabled(true);
                EdVersaoAtual.setEnabled(true);

                EdId.setEditable(false);
                EdCodTela.setEditable(false);
                EdNovaVersao.setEditable(true);
                JDcDataLibeVersão.setEnabled(true);
                JDObserva.setEditable(true);
                EdVersaoAtual.setEditable(false);

                break;
        }
    }

    public void limpaCampos() {
        EdId.setText(null);
        EdCodTela.setText(null);
        EdNovaVersao.setText(null);
        JDObserva.setText(null);
        JDcDataLibeVersão.setDate(null);
        EdVersaoAtual.setText(null);
    }

    public void listarTela() throws ClassNotFoundException {
        ContVersaoDao dao = new ContVersaoDao();

        for (ContVersao cv : dao.listTela(EdCodTela.getText())) {
            EdVersaoAtual.setText(cv.getVersaoAtualTela());
            habilitarB(2);
        }
    }

    public void salvarContVersao() throws SQLException {

        if (EdId.getText().equals("") || EdCodTela.getText().equals("") || EdNovaVersao.getText().equals("1.0-20.    . ") || JDObserva.getText().equals("") || JDcDataLibeVersão.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Por favor, preencha todos os dados");

        } else {
            ContVersao ct = new ContVersao();
            ContVersaoDao dao = new ContVersaoDao();

            ct.setIdVersao(Integer.parseInt(EdId.getText()));
            ct.setFk_codTela(EdCodTela.getText());
            ct.setNovaVersao(EdNovaVersao.getText());

            java.util.Date dateVer = JDcDataLibeVersão.getDate();
            long dtP = dateVer.getTime();
            java.sql.Date dateVersao = new java.sql.Date(dtP);
            ct.setDataLibVersao(dateVersao);
            ct.setObservaVersao(JDObserva.getText());

            try {
                dao.newVersao(ct);
                alteraVersaoAtual();

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void listVersao() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TbListagem.getModel();
        modelo.setNumRows(0);
        ContVersaoDao pdao = new ContVersaoDao();

        for (ContVersao u : pdao.listVersao()) {
            modelo.addRow(new Object[]{
                u.getIdVersao(),
                u.getFk_codTela(),
                u.getNovaVersao(),
                u.getDataLibVersao(),
                u.getObservaVersao()

            });
        }

    }

    public void ultimoId() {
        ContVersaoDao dao = new ContVersaoDao();

        try {
            for (ContVersao c : dao.readUltIdVersao()) {

                int idVersao;
                idVersao = c.getIdVersao() + 1;
                EdId.setText(Integer.toString(idVersao));

            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.YES_OPTION);
        }

    }

    public void alteraVersaoAtual() {

        ContVersao ct = new ContVersao();
        ContVersaoDao dao = new ContVersaoDao();

        ct.setVersaoAtualTela(EdNovaVersao.getText());

        java.util.Date UltiIntegra = JDcDataLibeVersão.getDate();
        long dtIntegra = UltiIntegra.getTime();
        java.sql.Date dateUltiIntegra = new java.sql.Date(dtIntegra);
        ct.setDataVersao(dateUltiIntegra);

        ct.setCodTela(EdCodTela.getText());

        try {
            dao.updateVersaoTela(ct);

            listVersao();
            limpaCampos();
            habilitarB(1);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrcadUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void vaCop() {
        try {

            JtListaSessao.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelcted, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String versaoAtual = JtListaSessao.getValueAt(row, 2).toString();
                    String versaoMaquina = JtListaSessao.getValueAt(row, 3).toString();

                    if (versaoAtual.equals(versaoMaquina)) {
                        label.setBackground(Color.WHITE);
                        label.setForeground(Color.BLACK);
                    } else {
                        label.setForeground(Color.RED);
                        label.setBackground(Color.YELLOW);
                        JtListaSessao.setSelectionForeground(Color.GREEN);

                    }

                    return label;
                }
            });
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JtListaSessao = new javax.swing.JTable();
        BtListaSessao = new javax.swing.JButton();
        CbListaAdmin = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListagem = new javax.swing.JTable();
        JDcDataLibeVersão = new com.toedter.calendar.JDateChooser();
        BtUp = new javax.swing.JButton();
        BtDow = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        JDObserva = new javax.swing.JTextPane();
        EdId = new javax.swing.JTextField();
        EdCodTela = new javax.swing.JTextField();
        BtNova = new javax.swing.JButton();
        EdVersaoAtual = new javax.swing.JTextField();
        BtBuscaCodTela = new javax.swing.JButton();
        EdNovaVersao = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        JtListaSessao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuário", "Tela", "Versão", "Versão na Máquina", "Desktop", "IP", "Data", "Hora"
            }
        ));
        jScrollPane3.setViewportView(JtListaSessao);

        BtListaSessao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtListaSessao.setText("Buscar");
        BtListaSessao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtListaSessaoActionPerformed(evt);
            }
        });

        CbListaAdmin.setText("Listar admin");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BtListaSessao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 518, Short.MAX_VALUE)
                .addComponent(CbListaAdmin)
                .addGap(15, 15, 15))
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtListaSessao)
                    .addComponent(CbListaAdmin))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("Controle de Sessões", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        TbListagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tela", "Versão", "Data e Hora", "Obs"
            }
        ));
        TbListagem.setMaximumSize(new java.awt.Dimension(0, 0));
        TbListagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListagemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListagem);
        if (TbListagem.getColumnModel().getColumnCount() > 0) {
            TbListagem.getColumnModel().getColumn(0).setMinWidth(0);
            TbListagem.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListagem.getColumnModel().getColumn(0).setMaxWidth(0);
            TbListagem.getColumnModel().getColumn(4).setMinWidth(0);
            TbListagem.getColumnModel().getColumn(4).setPreferredWidth(0);
            TbListagem.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        JDcDataLibeVersão.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataLibeVersão.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Liberação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-seta-para-recolher-64 (1).png"))); // NOI18N
        BtUp.setText("UPDATE");
        BtUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtUpActionPerformed(evt);
            }
        });

        BtDow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-fechar-janela-48 (2).png"))); // NOI18N
        BtDow.setText("DOWNGRADE");
        BtDow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtDowActionPerformed(evt);
            }
        });
        BtDow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtDowKeyPressed(evt);
            }
        });

        JDObserva.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição da versão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane2.setViewportView(JDObserva);

        EdId.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdCodTela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Tela:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtNova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNova.setText("Nova");
        BtNova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovaActionPerformed(evt);
            }
        });

        EdVersaoAtual.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Versão Atual:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtBuscaCodTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaCodTela.setText("Buscar");
        BtBuscaCodTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaCodTelaActionPerformed(evt);
            }
        });

        EdNovaVersao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nova Versão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        try {
            EdNovaVersao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("1.0-21.####.#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit.png"))); // NOI18N
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-black.png"))); // NOI18N
        jButton3.setText("Editar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(BtUp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtDow))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(EdVersaoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(71, 71, 71)
                                        .addComponent(EdNovaVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(EdCodTela, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(BtBuscaCodTela)
                                        .addGap(13, 13, 13)
                                        .addComponent(JDcDataLibeVersão, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                                .addComponent(BtNova)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EdId, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtNova)
                    .addComponent(EdId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodTela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscaCodTela)
                    .addComponent(JDcDataLibeVersão, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdVersaoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdNovaVersao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtUp)
                    .addComponent(BtDow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(10, 10, 10))
        );

        jTabbedPane1.addTab("Controle de Versão", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtUpActionPerformed
        try {
            salvarContVersao();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtUpActionPerformed

    private void TbListagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListagemMouseClicked
        if (TbListagem.getSelectedRow() != -1) {
            EdId.setText(TbListagem.getValueAt(TbListagem.getSelectedRow(), 0).toString());
            EdCodTela.setText(TbListagem.getValueAt(TbListagem.getSelectedRow(), 1).toString());
            EdVersaoAtual.setText(TbListagem.getValueAt(TbListagem.getSelectedRow(), 2).toString());

            try {
                DefaultTableModel model = (DefaultTableModel) TbListagem.getModel();
                int seectedRow = TbListagem.getSelectedRow();
                java.util.Date date = null;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(seectedRow, 3).toString());
                JDcDataLibeVersão.setDate(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
            JDObserva.setText(TbListagem.getValueAt(TbListagem.getSelectedRow(), 4).toString());
            habilitarB(3);
            try {
                listarTela();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_TbListagemMouseClicked

    private void BtNovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovaActionPerformed
        limpaCampos();
        habilitarB(2);

    }//GEN-LAST:event_BtNovaActionPerformed

    private void BtDowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtDowActionPerformed
        try {
            listVersao();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtDowActionPerformed

    private void BtBuscaCodTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaCodTelaActionPerformed
        this.FDBuscaTelaVersao = new FDBuscaTelaVersao(null, true);

        if (EdCodTela.getText().equals("")) {

            this.FDBuscaTelaVersao.setVisible(true);

            this.EdCodTela.setText(String.valueOf(this.FDBuscaTelaVersao.getCodVersao()));

            if (this.EdCodTela.getText().equals("null")) {
                EdCodTela.setText("");

            } else {
                try {
                    listarTela();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {

            try {
                listarTela();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_BtBuscaCodTelaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpaCampos();
        habilitarB(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtDowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtDowKeyPressed

    }//GEN-LAST:event_BtDowKeyPressed

    private void BtListaSessaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtListaSessaoActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) JtListaSessao.getModel();
        modelo.setNumRows(0);
        UsuarioDao dao = new UsuarioDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        try {
            for (Usuario u : dao.readSessao()) {
                if (CbListaAdmin.isSelected()) {
                    modelo.addRow(new Object[]{
                        u.getLoginUsuario(),
                        u.getFr_codTela(),
                        u.getFr_versaoTela(),
                        u.getFr_versaoNoUsuario(),
                        u.getNameDesktop(),
                        u.getIpDesktop(),
                        formatdata.format(u.getDataSessao()),
                        u.getHoraSessao()
                    });
                } else {
                    if (!u.getLoginUsuario().equals("admin")) {
                        modelo.addRow(new Object[]{
                            u.getLoginUsuario(),
                            u.getFr_codTela(),
                            u.getFr_versaoTela(),
                            u.getFr_versaoNoUsuario(),
                            u.getNameDesktop(),
                            u.getIpDesktop(),
                            formatdata.format(u.getDataSessao()),
                            u.getHoraSessao()
                        });
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        vaCop();
    }//GEN-LAST:event_BtListaSessaoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Função não implantada", "Erro", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscaCodTela;
    private javax.swing.JButton BtDow;
    private javax.swing.JButton BtListaSessao;
    private javax.swing.JButton BtNova;
    private javax.swing.JButton BtUp;
    private javax.swing.JCheckBox CbListaAdmin;
    private javax.swing.JTextField EdCodTela;
    private javax.swing.JTextField EdId;
    private javax.swing.JFormattedTextField EdNovaVersao;
    private javax.swing.JTextField EdVersaoAtual;
    private javax.swing.JTextPane JDObserva;
    private com.toedter.calendar.JDateChooser JDcDataLibeVersão;
    private javax.swing.JTable JtListaSessao;
    private javax.swing.JTable TbListagem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
