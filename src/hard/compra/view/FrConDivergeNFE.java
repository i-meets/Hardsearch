package hard.compra.view;

import hard.compra.dao.DivergeNfeDao;
import hard.compra.model.DivergeNFE;
import hard.compra.model.MailJava;
import hard.compra.model.MailJavaSender;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrConDivergeNFE extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSÃO
    String versao = "1.0-21.0720.0";

    int cod_nfe = 0;
    int num_nfe = 0;
    String nomeForne = "";
    int cod_vd = 0;
    FDErroOcorrido fdErroOcorrido;
    int codUser = 0;

    public FrConDivergeNFE(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Lançamento de Ajustes de Divergencias de NF-e - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
                codUser = Integer.parseInt(codUsuario);
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrConDivergeNFE";
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

    public void limpaCampos() {
        EdNumNF.setText("");
        EdNomeEmp.setText("");
        lbLink_nfe.setText("Link_nfe");
        txDescriDiv.setText("");
        txObsCompra.setText("");
        lbLink_nfe.setForeground(Color.decode("#3366ff"));

    }

    public void readNFe() {
        try {
            String num_nfe_list = EdNumNF.getText();
            DivergeNfeDao dao = new DivergeNfeDao();
            DefaultTableModel model = (DefaultTableModel) JtListaNfe.getModel();
            model.setRowCount(0);

            for (DivergeNFE nf : dao.readNFe(num_nfe_list, "", 0)) {
                String status = "";
                if (nf.getStatus_nfe() == 0) {
                    status = "AGUARDANDO";
                } else {
                    status = "AJUSTADO";
                }
                model.addRow(new Object[]{
                    nf.getCod_nfe(),
                    nf.getNum_nfe(),
                    nf.getNomeForne(),
                    status,
                    nf.getObs_compra()
                });

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NFs");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    //TRÁS NFE NA TELA APÓS MOUSE_CLICK
    public void listNfe() {
        if (cod_nfe != 0) {
            try {

                DivergeNfeDao dao = new DivergeNfeDao();

                for (DivergeNFE nf : dao.readNFeForDesc(cod_nfe)) {

                    lbLink_nfe.setText("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + nf.getLink_nfe());

                    if (new File("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + nf.getLink_nfe()).isFile()) {
                        lbLink_nfe.setForeground(Color.decode("#3366ff"));
                    } else {
                        lbLink_nfe.setForeground(Color.decode("#ff0000"));
                    }

                    //LISTA OS DIVERGE
                    readDivergeForSaveNfe();
                }

            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar NFs");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            System.out.println("erro no cod_nfe");
        }
    }

    public void notificaMail() {
        new Thread() {

            @Override
            public void run() {
                MailJava mj = new MailJava();
                mj.setSmtpHostMail("");
                mj.setSmtpPortMail("");
                mj.setSmtpAuth("");
                mj.setSmtpStarttls("");
                mj.setUserMail("");
                mj.setFromNameMail("NOTIFICAÇÃO IMAC");
                mj.setPassMail("");
                mj.setCharsetMail("");

                SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
                Date data = new Date(System.currentTimeMillis());

                mj.setSubjectMail("RE: Nova divergencia NF " + num_nfe + " - " + nomeForne);
                mj.setBodyMail("<h3>O setor de compras acaba de realizar os ajustes na NF-e abaixo</h3><table border='1' style='width: 580px; height: 140px;  font-family: Calibri;'><tr style='text-align:center;'>"
                        + "<td><h3>FORNECEDOR</h3></td><td><h3>NOTA FISCAL</h3></td>"
                        + "<td><h3>DATA</h3></td></tr>"
                        + "<tr><td style='text-align:center;'>" + nomeForne + "</td>"
                        + "<td style='text-align:center;'>" + num_nfe + "</td><td style='text-align:center;'>" + formatdata.format(data) + "</td></tr></table>"
                        + "<br><h4>Acesse o sistema Hardsearch para visualizar as divergencias</h4>"
                        + "<br>Sistema de envio automatico, nao responda este e-mail.  V-0.1Beta");
                mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

                UsuarioDao udao = new UsuarioDao();
                try {

                    for (Usuario u : udao.readConfigNtfy()) {

                        if (u.getFr_rEmailNfeCompras() == 1) {
                            Map<String, String> map = new HashMap<>();

                            map.put(u.getEmailUsuario(), u.getNomeUsuario());
                            mj.setToMailsUsers(map);

                            //Realiza o envio
                            new MailJavaSender().senderMail(mj);
                        }
                    }

                } catch (UnsupportedEncodingException | ClassNotFoundException | MessagingException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("mandar notificaçãod e e-mail");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }.start();
    }

    public void corNaLinha() {

    }

    public void listaNfs() {
        try {
            String num_nfe_list = EdListaNfeCod.getText();
            DivergeNfeDao dao = new DivergeNfeDao();
            DefaultTableModel model = (DefaultTableModel) jtListaNF.getModel();
            model.setRowCount(0);
            int st = 0;

            if (cbListStatusNfe.getSelectedItem().equals("Ajustada")) {
                st = 1;
            } else if (cbListStatusNfe.getSelectedItem().equals("NF Cancelada")) {
                st = 2;
            } else if (cbListStatusNfe.getSelectedItem().equals("NF Estornada")) {
                st = 3;
            } else if (cbListStatusNfe.getSelectedItem().equals("Cart. Correção")) {
                st = 4;

            } else {
                st = 0;
            }

            if (cbListaTodasNF.isSelected()) {

                for (DivergeNFE nf : dao.readTNFe(num_nfe_list, EdListaForForne.getText())) {
                    String status = "";

                    switch (nf.getStatus_nfe()) {
                        case 0:
                            status = "AGUARDANDO";
                            break;
                        case 1:
                            status = "AJUSTADO";
                            break;
                        case 2:
                            status = "NF CANCELADA";
                            break;
                        case 3:
                            status = "NF ESTORNADA";
                            break;
                        case 4:
                            status = "CART. CORREÇÃO";
                            break;
                    }

                    model.addRow(new Object[]{
                        nf.getCod_nfe(),
                        nf.getNum_nfe(),
                        nf.getNomeForne(),
                        status
                    });
                }

            } else {

                for (DivergeNFE nf : dao.readNFe(num_nfe_list, EdListaForForne.getText(), st)) {
                    String status = "";

                    switch (nf.getStatus_nfe()) {
                        case 0:
                            status = "AGUARDANDO";
                            break;
                        case 1:
                            status = "AJUSTADO";
                            break;
                        case 2:
                            status = "NF CANCELADA";
                            break;
                        case 3:
                            status = "NF ESTORNADA";
                            break;
                        case 4:
                            status = "CART. CORREÇÃO";
                            break;
                    }

                    model.addRow(new Object[]{
                        nf.getCod_nfe(),
                        nf.getNum_nfe(),
                        nf.getNomeForne(),
                        status
                    });

                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NFs");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtListaNfe = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        EdNomeEmp = new javax.swing.JTextField();
        EdNumNF = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        lbLink_nfe = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        BtSair1 = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtAjustar = new javax.swing.JButton();
        cbStatusNfe = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txDescriDiv = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        txObsCompra = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtListaNF = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cbListStatusNfe = new javax.swing.JComboBox<>();
        BtBuscar1 = new javax.swing.JButton();
        cbListaTodasNF = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbListaDiverge = new javax.swing.JTable();
        EdListaNfeCod = new javax.swing.JTextField();
        EdListaForForne = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_dv", "Divergência", "Descrição", "Data e Hora", "Status"
            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(0);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(0).setMaxWidth(0);
            tabela.getColumnModel().getColumn(2).setMinWidth(0);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 150, 730, 156));

        JtListaNfe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_nfe", "Nº NF-e", "Fornecedor", "Status", "obs_compra"
            }
        ));
        JtListaNfe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtListaNfeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JtListaNfe);
        if (JtListaNfe.getColumnModel().getColumnCount() > 0) {
            JtListaNfe.getColumnModel().getColumn(0).setMinWidth(0);
            JtListaNfe.getColumnModel().getColumn(0).setPreferredWidth(0);
            JtListaNfe.getColumnModel().getColumn(0).setMaxWidth(0);
            JtListaNfe.getColumnModel().getColumn(1).setMinWidth(50);
            JtListaNfe.getColumnModel().getColumn(1).setPreferredWidth(80);
            JtListaNfe.getColumnModel().getColumn(1).setMaxWidth(110);
            JtListaNfe.getColumnModel().getColumn(4).setMinWidth(0);
            JtListaNfe.getColumnModel().getColumn(4).setPreferredWidth(0);
            JtListaNfe.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 6, 337, 138));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdNomeEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdNumNF.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdNumNF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNumNFKeyPressed(evt);
            }
        });

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        lbLink_nfe.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbLink_nfe.setForeground(new java.awt.Color(51, 102, 255));
        lbLink_nfe.setText("Link_nfe");
        lbLink_nfe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLink_nfeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbLink_nfe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(EdNumNF, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtBuscar))
                            .addComponent(EdNomeEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 48, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdNumNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdNomeEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLink_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair1.setText("SAIR");
        BtSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSair1ActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 80, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 120, 33));

        BtAjustar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/validation.png"))); // NOI18N
        BtAjustar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAjustarActionPerformed(evt);
            }
        });
        jPanel4.add(BtAjustar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 40, 33));

        cbStatusNfe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linha Ajustada", "NF Cancelada", "NF Estornada", "Cart. Correção" }));
        jPanel4.add(cbStatusNfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 130, 33));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("Ocorrência:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 16, -1, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 742, 55));

        txDescriDiv.setColumns(20);
        txDescriDiv.setRows(5);
        txDescriDiv.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição da Divergência:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane5.setViewportView(txDescriDiv);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 310, 360, 110));

        txObsCompra.setColumns(20);
        txObsCompra.setRows(5);
        txObsCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observações do Comprador:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane6.setViewportView(txObsCompra);

        jPanel1.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 366, -1));

        jTabbedPane2.addTab("Listagem de Divergencias NF-e", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jtListaNF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_nfe", "Nº NF-e", "Fornecedor", "Status"
            }
        ));
        jtListaNF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaNFMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtListaNF);
        if (jtListaNF.getColumnModel().getColumnCount() > 0) {
            jtListaNF.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaNF.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaNF.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jLabel3.setText("Listar por:");

        cbListStatusNfe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguardando", "Ajustada", "NF Cancelada", "NF Estornada", "Cart. Correção" }));

        BtBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar1.setText("Buscar");
        BtBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscar1ActionPerformed(evt);
            }
        });

        cbListaTodasNF.setText("Lista todas");

        tbListaDiverge.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_dv", "Divergência", "Descrição", "Data e Hora", "Status"
            }
        ));
        tbListaDiverge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaDivergeMouseClicked(evt);
            }
        });
        tbListaDiverge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListaDivergeKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbListaDiverge);
        if (tbListaDiverge.getColumnModel().getColumnCount() > 0) {
            tbListaDiverge.getColumnModel().getColumn(0).setMinWidth(0);
            tbListaDiverge.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbListaDiverge.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        EdListaNfeCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdListaNfeCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdListaNfeCodKeyPressed(evt);
            }
        });

        EdListaForForne.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Fornecedor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdListaForForne.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdListaForForneKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(EdListaNfeCod, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EdListaForForne)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(cbListStatusNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(BtBuscar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbListaTodasNF)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbListStatusNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbListaTodasNF)))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EdListaNfeCod, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EdListaForForne, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTabbedPane2.addTab("Consulta NF-e", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbLink_nfeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLink_nfeMouseClicked

        try {

            String linkLb = lbLink_nfe.getText();
            if (!linkLb.equals("Link_nfe")) {
                Desktop d = Desktop.getDesktop();
                d.open(new File(linkLb));
            } else {
                JOptionPane.showMessageDialog(rootPane, "Você deve importar o arquivo primeiro", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Arquivo não localizado no caminho informado\n" + ex, "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_lbLink_nfeMouseClicked

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        readNFe();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void JtListaNfeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtListaNfeMouseClicked
        txObsCompra.setText("");
        txDescriDiv.setText("");
        String nf = "";
        cod_nfe = Integer.parseInt(JtListaNfe.getValueAt(JtListaNfe.getSelectedRow(), 0).toString());
        EdNumNF.setText(JtListaNfe.getValueAt(JtListaNfe.getSelectedRow(), 1).toString());
        EdNomeEmp.setText(JtListaNfe.getValueAt(JtListaNfe.getSelectedRow(), 2).toString());
        try {
            nf = JtListaNfe.getValueAt(JtListaNfe.getSelectedRow(), 4).toString();
            txObsCompra.setText(nf);
        } catch (Exception e) {
        }

        //limpa antes do clicked
        lbLink_nfe.setText("");

        listNfe();
    }//GEN-LAST:event_JtListaNfeMouseClicked

    private void BtAjustarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAjustarActionPerformed

        if (tabela.getSelectedRow() >= 0) {
            num_nfe = Integer.parseInt(EdNumNF.getText());
            nomeForne = EdNomeEmp.getText();
            try {

                DivergeNfeDao dao = new DivergeNfeDao();
                DivergeNFE div = new DivergeNFE();

                div.setCod_nfe(cod_nfe);
                int statusDv = 0;
                if (cbStatusNfe.getSelectedItem().equals("Linha Ajustada")) {
                    statusDv = 1;
                } else if (cbStatusNfe.getSelectedItem().equals("NF Cancelada")) {
                    statusDv = 2;
                } else if (cbStatusNfe.getSelectedItem().equals("NF Estornada")) {
                    statusDv = 3;
                } else if (cbStatusNfe.getSelectedItem().equals("Cart. Correção")) {
                    statusDv = 4;
                }

                div.setStatus_dv(statusDv);
                div.setFr_codDv(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
                div.setCod_nfe(cod_nfe);
                dao.updateStatusDvForNf(div);

                DivergeNFE obs = new DivergeNFE();
                obs.setObs_compra(txObsCompra.getText());
                obs.setCod_nfe(cod_nfe);
                dao.updateObservaCompra(obs);

                if (!dao.checkDvForStatusAndNfe(cod_nfe, 0)) {
                    DivergeNFE nf = new DivergeNFE();
                    nf.setCod_nfe(cod_nfe);
                    int status = 0;
                    if (cbStatusNfe.getSelectedItem().equals("Linha Ajustada")) {
                        status = 1;
                    } else if (cbStatusNfe.getSelectedItem().equals("NF Cancelada")) {
                        status = 2;
                    } else if (cbStatusNfe.getSelectedItem().equals("NF Estornada")) {
                        status = 3;
                    } else if (cbStatusNfe.getSelectedItem().equals("Cart. Correção")) {
                        status = 4;
                    }

                    nf.setStatus_nfe(status);
                    dao.updateStatusNf(nf);
                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                    model.setRowCount(0);
                    limpaCampos();
                    notificaMail();
                } else {
                    listNfe();
                }

                readNFe();

            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("ajustar linha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar a linha que deseja atualizar", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtAjustarActionPerformed

    private void BtSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSair1ActionPerformed
        dispose();
    }//GEN-LAST:event_BtSair1ActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        limpaCampos();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        txDescriDiv.setText(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tabelaMouseClicked

    private void jtListaNFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaNFMouseClicked

        try {
            DivergeNfeDao dao = new DivergeNfeDao();
            DefaultTableModel model = (DefaultTableModel) tbListaDiverge.getModel();
            model.setRowCount(0);
            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
            int cod = Integer.parseInt(jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 0).toString());
            for (DivergeNFE nf : dao.readDivergeForSaveNFe(cod)) {
                String status = "";

                switch (nf.getStatus_dv()) {
                    case 0:
                        status = "AGUARDANDO";
                        break;
                    case 1:
                        status = "AJUSTADO";
                        break;
                    case 2:
                        status = "NF CANCELADA";
                        break;
                    case 3:
                        status = "NF ESTORNADA";
                        break;
                    case 4:
                        status = "CART. CORREÇÃO";
                        break;
                }

                model.addRow(new Object[]{
                    nf.getFr_codDv(),
                    nf.getFr_tipoDiverge(),
                    nf.getFr_descricao(),
                    formatdata.format(nf.getFr_dataDv()) + "-" + nf.getFr_hora(),
                    status

                });

            }

        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NFs");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_jtListaNFMouseClicked

    private void BtBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscar1ActionPerformed
        listaNfs();
    }//GEN-LAST:event_BtBuscar1ActionPerformed

    private void tbListaDivergeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListaDivergeKeyPressed

    }//GEN-LAST:event_tbListaDivergeKeyPressed

    private void tbListaDivergeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaDivergeMouseClicked

    }//GEN-LAST:event_tbListaDivergeMouseClicked

    private void EdListaNfeCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdListaNfeCodKeyPressed
        listaNfs();
    }//GEN-LAST:event_EdListaNfeCodKeyPressed

    private void EdNumNFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNumNFKeyPressed
        readNFe();
    }//GEN-LAST:event_EdNumNFKeyPressed

    private void EdListaForForneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdListaForForneKeyPressed
        listaNfs();
    }//GEN-LAST:event_EdListaForForneKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAjustar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtBuscar1;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtSair1;
    private javax.swing.JTextField EdListaForForne;
    private javax.swing.JTextField EdListaNfeCod;
    private javax.swing.JTextField EdNomeEmp;
    private javax.swing.JTextField EdNumNF;
    private javax.swing.JTable JtListaNfe;
    private javax.swing.JComboBox<String> cbListStatusNfe;
    private javax.swing.JCheckBox cbListaTodasNF;
    private javax.swing.JComboBox<String> cbStatusNfe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jtListaNF;
    private java.awt.Label lbLink_nfe;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tbListaDiverge;
    private javax.swing.JTextArea txDescriDiv;
    private javax.swing.JTextArea txObsCompra;
    // End of variables declaration//GEN-END:variables

    public void readDivergeForSaveNfe() {
        if (cod_nfe != 0) {
            try {
                DivergeNfeDao dao = new DivergeNfeDao();
                DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                model.setRowCount(0);
                SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
                for (DivergeNFE nf : dao.readDvFrCon(cod_nfe)) {
                    String status = "";

                    switch (nf.getStatus_dv()) {
                        case 0:
                            status = "AGUARDANDO";
                            break;
                        case 1:
                            status = "AJUSTADO";
                            break;
                        case 2:
                            status = "NF CANCELADA";
                            break;
                        case 3:
                            status = "NF ESTORNADA";
                            break;
                        case 4:
                            status = "CART. CORREÇÃO";
                            break;
                    }

                    model.addRow(new Object[]{
                        nf.getFr_codDv(),
                        nf.getFr_tipoDiverge(),
                        nf.getFr_descricao(),
                        formatdata.format(nf.getFr_dataDv()) + "-" + nf.getFr_hora(),
                        status

                    });

                }
            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar NFs");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {

        }
    }

}
