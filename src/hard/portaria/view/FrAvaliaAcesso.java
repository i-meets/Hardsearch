package hard.portaria.view;

import hard.compra.model.MailJava;
import hard.compra.model.MailJavaSender;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.portaria.dao.FormularioDao;
import hard.portaria.model.Formulario;
import java.awt.Color;
import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrAvaliaAcesso extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0804.0";

    int codFormu = 0;
    int status = 0;
    String nomeFuncio = "";
    String setorFuncio = "";

    FDErroOcorrido fdErroOcorrido;

    public FrAvaliaAcesso(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();

        title = "Controle de Entrada - Formulário COVID-19 - V" + versao;

        LbAlerta.setVisible(false);
        LbEncaDc.setVisible(false);
        BtEncaDc.setVisible(false);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrAvaliaAcesso";
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
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 6);
    }

    public void limpaCampos() {
        BtEncaDc.setVisible(false);
        LbEncaDc.setVisible(false);
        BtEntrada.setVisible(true);
        LbEntrada.setVisible(true);
        codFormu = 0;
        status = 0;
        LbAlerta.setVisible(false);
        LbAcesso.setText("VERIFICAÇÃO");
        LbAcesso.setForeground(Color.BLACK);
        EdTemp.setText(null);
        nomeFuncio = "";
        setorFuncio = "";
    }

    public void listaFormulario() {
        FormularioDao dao = new FormularioDao();
        DefaultTableModel model = (DefaultTableModel) TbListaFormulario.getModel();
        model.setRowCount(0);
        try {
            String codFun = EdBuscaCracha.getText();
            String nomeFun = EdBuscaNome.getText();

            for (Formulario fo : dao.readFormulario(codFun, nomeFun)) {
                model.addRow(new Object[]{
                    fo.getCodFormulario(),
                    fo.getFr_codFuncionario(),
                    fo.getFr_nomeFuncionario(),
                    fo.getFr_setorFuncionario(),
                    fo.getSintomas(),
                    fo.getContato(),
                    fo.getFebre(),
                    fo.getTosse(),
                    fo.getD_respirar(),
                    fo.getCalafrios(),
                    fo.getDor_muscular(),
                    fo.getDor_garganta(),
                    fo.getP_olfato(),
                    fo.getNausea(),
                    fo.getDor_cabeca(),
                    fo.getData_sintoma(),
                    fo.getMetros_2(),
                    fo.getData_p(),
                    fo.getStatus()

                });

            }

        } catch (ClassNotFoundException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar respostas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void atualizaStatus() {
        try {

            Formulario fo = new Formulario();
            FormularioDao dao = new FormularioDao();
            fo.setStatus(status);
            fo.setCodFormulario(codFormu);
            if (status == 2) {
                fo.setTemp(0.0);
            } else {
                fo.setTemp(Double.parseDouble(EdTemp.getText().replace(",", ".")));
            }

            dao.updateFormulario(fo);
            notificaMail();
            listaFormulario();
            limpaCampos();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar status");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void notificaMail() {
        new Thread() {

            @Override
            public void run() {
                try {
                    MailJava mj = new MailJava();
                    mj.setSmtpHostMail("");
                    mj.setSmtpPortMail("");
                    mj.setSmtpAuth("");
                    mj.setSmtpStarttls("");
                    mj.setUserMail("");
                    mj.setFromNameMail("");
                    mj.setPassMail("");
                    mj.setCharsetMail("");

                    mj.setSubjectMail("ALERTAS - ENTRADA FUNCIONÁRIO NÃO PERMITIDA");
                    mj.setBodyMail("<h3>Entrada do funcionario: " + nomeFuncio + " nao foi permitida</h3>"
                            + "<br><h4>Em caso de duvida, entrar em contato com a seguranca do trabalho<h4>E</h4>"
                            + "<br>Sistema de envio automatico, nao responda este e-mail.  V-0.1Beta");
                    mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

                    Map<String, String> map = new HashMap<>();

                    if (setorFuncio.equals("PROCESSOS") || setorFuncio.equals("QUALIDADE")) {
                        map.put("", "");
                    } else if (setorFuncio.equals("PCP") || setorFuncio.equals("EXPEDICAO")) {
                        map.put("", "");
                    }

                    mj.setToMailsUsers(map);

                    //Realiza o envio
                    new MailJavaSender().senderMail(mj);

                } catch (UnsupportedEncodingException | MessagingException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("mandar notificação de e-mail");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            }
        }.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaFormulario = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        EdBuscaNome = new javax.swing.JTextField();
        EdBuscaCracha = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        BtEntrada = new javax.swing.JButton();
        LbEntrada = new javax.swing.JLabel();
        EdTemp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        BtCancelar = new javax.swing.JButton();
        LbEncaDc = new javax.swing.JLabel();
        BtEncaDc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        LbAcesso = new javax.swing.JLabel();
        LbAlerta = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TbListaFormulario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_for", "Crachá", "Funcionário", "Setor", "sintomas", "contato", "febre", "tosse", "d_respirar", "calafrios", "dor_muscular", "dor_garganta", "p_olfato", "nausea", "dor_cabeca", "data_sintoma", "2_metros", "data_p", "status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbListaFormulario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaFormularioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaFormulario);
        if (TbListaFormulario.getColumnModel().getColumnCount() > 0) {
            TbListaFormulario.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(0).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(1).setMinWidth(30);
            TbListaFormulario.getColumnModel().getColumn(1).setPreferredWidth(90);
            TbListaFormulario.getColumnModel().getColumn(1).setMaxWidth(150);
            TbListaFormulario.getColumnModel().getColumn(4).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(4).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(4).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(5).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(5).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(5).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(6).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(6).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(6).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(7).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(7).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(7).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(8).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(8).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(8).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(9).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(9).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(9).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(10).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(10).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(10).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(11).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(11).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(11).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(12).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(12).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(12).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(13).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(13).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(13).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(14).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(14).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(14).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(15).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(15).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(15).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(16).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(16).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(16).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(17).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(17).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(17).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(18).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(18).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(18).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 71, 711, 142));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });

        EdBuscaCracha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Crachá:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaCracha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaCrachaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdBuscaCracha, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EdBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EdBuscaCracha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 699, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/entrada.png"))); // NOI18N
        BtEntrada.setText("OK");
        BtEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEntradaActionPerformed(evt);
            }
        });
        jPanel3.add(BtEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 70, 35));

        LbEntrada.setText("Confirmar entrada:");
        jPanel3.add(LbEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 8, -1, -1));

        EdTemp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Temperatura:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel3.add(EdTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 27, 136, -1));

        jLabel3.setText("Medição de temperatura:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 8, -1, -1));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, -1, 33));

        LbEncaDc.setText("Encaminhado para a Doctor Clin:");
        jPanel3.add(LbEncaDc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 8, -1, -1));

        BtEncaDc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/going.png"))); // NOI18N
        BtEncaDc.setText("OK");
        BtEncaDc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEncaDcActionPerformed(evt);
            }
        });
        jPanel3.add(BtEncaDc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 70, 35));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 280, 700, 75));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel1.setText("SITUAÇÃO DE ACESSO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 237, -1, -1));

        LbAcesso.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        LbAcesso.setText("VERIFICAÇÃO");
        LbAcesso.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(LbAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 235, 131, -1));

        LbAlerta.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        LbAlerta.setForeground(new java.awt.Color(255, 0, 0));
        LbAlerta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atencao.png"))); // NOI18N
        LbAlerta.setText("ATENÇÃO, O FUNCIONÁRIO DEVE SE DIRIGIR A DOCTOR CLIN");
        jPanel1.add(LbAlerta, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 225, 392, 45));

        jTabbedPane1.addTab("Permissão de Entrada", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listaFormulario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EdBuscaCrachaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaCrachaKeyPressed

    }//GEN-LAST:event_EdBuscaCrachaKeyPressed

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed

    }//GEN-LAST:event_EdBuscaNomeKeyPressed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        limpaCampos();
        listaFormulario();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void TbListaFormularioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaFormularioMouseClicked
        if (TbListaFormulario.getSelectedRow() != -1) {
            codFormu = Integer.parseInt(TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 0).toString());
            nomeFuncio = TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 2).toString();
            setorFuncio = TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 3).toString();
            int peso = 0;

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 6).toString().equals("1")
                    || TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 8).toString().equals("1")
                    || TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 9).toString().equals("1")
                    || TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 12).toString().equals("1")) {
                peso = 3;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 4).toString().equals("1")) {
                peso = peso + 1;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 7).toString().equals("1")) {
                peso = peso + 1;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 10).toString().equals("1")) {
                peso = peso + 1;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 11).toString().equals("1")) {
                peso = peso + 1;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 13).toString().equals("1")) {
                peso = peso + 1;
            }

            if (TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 14).toString().equals("1")) {
                peso = peso + 1;
            }

            if (peso >= 3) {
                BtEntrada.setVisible(false);
                LbEntrada.setVisible(false);
                BtEncaDc.setVisible(true);
                LbEncaDc.setVisible(true);
                LbAlerta.setVisible(true);
                LbAcesso.setText("NÃO PERMITIDA");
                LbAcesso.setForeground(Color.RED);
            } else {
                BtEntrada.setVisible(true);
                LbEntrada.setVisible(true);
                BtEncaDc.setVisible(false);
                LbEncaDc.setVisible(false);
                LbAlerta.setVisible(false);
                LbAcesso.setText("AUTORIZADA");
                LbAcesso.setForeground(Color.GREEN);
            }

        }

    }//GEN-LAST:event_TbListaFormularioMouseClicked

    private void BtEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEntradaActionPerformed
        if (!EdTemp.getText().equals("")) {

            status = 1;
            atualizaStatus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você precisa informar o valor do teste de temperatura", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtEntradaActionPerformed

    private void BtEncaDcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEncaDcActionPerformed
        status = 2;
        atualizaStatus();
    }//GEN-LAST:event_BtEncaDcActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtEncaDc;
    private javax.swing.JButton BtEntrada;
    private javax.swing.JTextField EdBuscaCracha;
    private javax.swing.JTextField EdBuscaNome;
    private javax.swing.JTextField EdTemp;
    private javax.swing.JLabel LbAcesso;
    private javax.swing.JLabel LbAlerta;
    private javax.swing.JLabel LbEncaDc;
    private javax.swing.JLabel LbEntrada;
    private javax.swing.JTable TbListaFormulario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
