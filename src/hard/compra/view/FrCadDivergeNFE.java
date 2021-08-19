package hard.compra.view;

import hard.compra.dao.DivergeNfeDao;
import hard.compra.model.DivergeNFE;
import hard.compra.model.MailJava;
import hard.compra.model.MailJavaSender;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDBuscaEmp;
import hard.home.view.FDErroOcorrido;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FrCadDivergeNFE extends javax.swing.JInternalFrame {

    //CONTOLE DE VERSAO
    String versao = "1.0-21.0625.0";

    File link = null;
    private FDBuscaEmp FDBuscaEmp;
    FDErroOcorrido fdErroOcorrido;
    int cod_nfe = 0;
    int cod_dv = 0;
    int codUser = 0;

    public FrCadDivergeNFE(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle de Divergencias - V" + versao;
        habilitarB(1);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
                codUser = Integer.parseInt(codUsuario);
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrCadDivergeNFE";
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
                limpaCampos();
                lbLink_nfe.setForeground(Color.decode("#3366ff"));

                EdNumNF.setEditable(true);
                EdNomeEmp.setEnabled(false);
                txDescricao.setEnabled(false);
                lbInsereLinhas.setEnabled(false);
                cbDiverge.setEnabled(false);

                BtAnexar.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtCancelar.setEnabled(true);
                BtNovo.setEnabled(true);
                BtAdc.setEnabled(false);
                BtRemove.setEnabled(false);
                break;

            case 2:
                readMaiorCod();
                EdNumNF.setEditable(false);
                EdNomeEmp.setEnabled(true);
                txDescricao.setEnabled(false);
                cbDiverge.setEnabled(false);
                lbInsereLinhas.setEnabled(true);

                BtAnexar.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtNovo.setEnabled(false);
                BtCancelar.setEnabled(true);
                BtAdc.setEnabled(false);
                BtRemove.setEnabled(false);

                break;

            case 3:
                EdNumNF.setEditable(false);
                EdNomeEmp.setEnabled(false);
                txDescricao.setEnabled(true);
                cbDiverge.setEnabled(true);
                lbInsereLinhas.setEnabled(true);

                BtAnexar.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(true);
                BtNovo.setEnabled(false);
                BtCancelar.setEnabled(true);
                BtAdc.setEnabled(true);
                BtRemove.setEnabled(true);

                break;

            case 4:
                EdNumNF.setEditable(false);
                EdNomeEmp.setEnabled(false);
                txDescricao.setEnabled(false);
                cbDiverge.setEnabled(false);
                lbInsereLinhas.setEnabled(false);

                BtAnexar.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtNovo.setEnabled(false);
                BtCancelar.setEnabled(true);
                BtAdc.setEnabled(false);
                BtRemove.setEnabled(false);
                break;

        }

    }

    public void limpaCampos() {

        EdNumNF.setText("");
        lbLink_nfe.setText("Link_nfe");
        EdNomeEmp.setText("");
        txDescricao.setText("");
        cod_nfe = 0;
        cod_dv = 0;

        DefaultTableModel modelo = (DefaultTableModel) tbListaDiverge.getModel();
        modelo.setNumRows(0);

    }

    public void readMaiorCod() {
        try {

            DivergeNfeDao dao = new DivergeNfeDao();
            for (DivergeNFE f : dao.readMaiorCod()) {
                cod_nfe = f.getCod_nfe();
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void salvarNFe() {
        if (cod_nfe != 0) {
            try {
                DivergeNFE nf = new DivergeNFE();
                nf.setCod_nfe(cod_nfe);
                nf.setNum_nfe(Integer.parseInt(EdNumNF.getText()));
                nf.setNomeForne(EdNomeEmp.getText());
                nf.setLink_nfe(EdNumNF.getText() + "_" + EdNomeEmp.getText() + ".pdf");
                java.util.Date dateE = JdcDataEmissao.getDate();
                long dtE = dateE.getTime();
                java.sql.Date dateEmi = new java.sql.Date(dtE);
                nf.setDataEmiNfe(dateEmi);
                nf.setStatus_nfe(0);
                DivergeNfeDao dao = new DivergeNfeDao();
                dao.saveNFE(nf);
                notificaMail();

                habilitarB(3);

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("cadastrar NF-e");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "erro no cod_nfe", "Atenção", JOptionPane.ERROR_MESSAGE);
        }
    }

    //LISTA DIVERGE APÓS CADASTRAR DIVERGE
    public void readDivergeForSaveNfe() {
        if (cod_nfe != 0) {
            try {
                DivergeNfeDao dao = new DivergeNfeDao();
                DefaultTableModel model = (DefaultTableModel) tbListaDiverge.getModel();
                model.setRowCount(0);
                SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
                for (DivergeNFE nf : dao.readDivergeForSaveNFe(cod_nfe)) {
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
            JOptionPane.showMessageDialog(rootPane, "erro no cod_nfe");
        }
    }

    //LISTA NFE NA TABELA DE LISTAGEM DE NFE
    public void readNFe() {
        try {
            String num_nfe = EdListaNfeCod.getText();
            DivergeNfeDao dao = new DivergeNfeDao();
            DefaultTableModel model = (DefaultTableModel) jtListaNF.getModel();
            model.setRowCount(0);
            int st = 0;

            if (cbStatusNfe.getSelectedItem().equals("Ajustada")) {
                st = 1;
            } else if (cbStatusNfe.getSelectedItem().equals("NF Cancelada")) {
                st = 2;
            } else if (cbStatusNfe.getSelectedItem().equals("NF Estornada")) {
                st = 3;
            } else if (cbStatusNfe.getSelectedItem().equals("Cart. Correção")) {
                st = 4;

            } else {
                st = 0;
            }

            if (cbListaTodasNF.isSelected()) {

                for (DivergeNFE nf : dao.readTNFe(num_nfe, "")) {
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
                        status,
                        nf.getObs_compra(),
                        nf.getDataEmiNfe()
                    });
                }

            } else {

                for (DivergeNFE nf : dao.readNFe(num_nfe, "", st)) {
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
                        status,
                        nf.getObs_compra(),
                        nf.getDataEmiNfe()
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

    //TRÁS NFE NA TELA APÓS MOUSE_CLICK
    public void listNfe() {
        if (cod_nfe != 0) {
            try {

                DivergeNfeDao dao = new DivergeNfeDao();

                for (DivergeNFE nf : dao.readNFeForDesc(cod_nfe)) {

                    lbLink_nfe.setText("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + nf.getLink_nfe());

                    //LISTA OS DIVERGE
                    readDivergeForSaveNfe();

                    int status = nf.getStatus_nfe();
                    switch (nf.getStatus_nfe()) {
                        case 0:
                            lbStatus.setText("AGUARDANDO");
                            break;
                        case 1:
                            lbStatus.setText("AJUSTADA");
                            break;
                        case 2:
                            lbStatus.setText("NF CANCELADA");
                            break;
                        case 3:
                            lbStatus.setText("NF ESTORNADA");
                            break;
                        case 4:
                            lbStatus.setText("CART. CORREÇÃO");
                            break;
                    }

                    habilitarB(3);

                }

            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar NFs");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "erro no cod_nfe", "Atenção", JOptionPane.ERROR_MESSAGE);
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

                    String numNf = EdNumNF.getText();
                    String nomeForne = EdNomeEmp.getText();
                    SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
                    Date data = new Date(System.currentTimeMillis());

                    mj.setSubjectMail("Nova divergencia NF " + numNf + " - " + nomeForne);
                    mj.setBodyMail("<h3>Nova divergencia de OC com nota fiscal</h3><h4>Atencao, a nota fiscal abaixo, nao esta conforme com sua ordem de compra.</h4><table border='1' style='width: 580px; height: 140px;  font-family: Calibri;'><tr style='text-align:center;'>"
                            + "<td><h3>FORNECEDOR</h3></td><td><h3>NOTA FISCAL</h3></td>"
                            + "<td><h3>DATA</h3></td></tr>"
                            + "<tr><td style='text-align:center;'>" + nomeForne + "</td>"
                            + "<td style='text-align:center;'>" + numNf + "</td><td style='text-align:center;'>" + formatdata.format(data) + "</td></tr></table>"
                            + "<br><h4>Acesse o sistema Hardsearch para visualizar as divergencias</h4>"
                            + "<br>Sistema de envio automatico, nao responda este e-mail.  V-0.1Beta");
                    mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

                    UsuarioDao udao = new UsuarioDao();
                    for (Usuario u : udao.readConfigNtfy()) {

                        if (u.getFr_rEmailNfeFiscal() == 1) {
                            Map<String, String> map = new HashMap<>();

                            map.put(u.getEmailUsuario(), u.getNomeUsuario());
                            mj.setToMailsUsers(map);

                            //Realiza o envio
                            new MailJavaSender().senderMail(mj);
                        }
                    }
                } catch (UnsupportedEncodingException | ClassNotFoundException | MessagingException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("mandar notificação de e-mail fiscal");
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txDescricao = new javax.swing.JTextArea();
        lbInsereLinhas = new javax.swing.JLabel();
        cbDiverge = new javax.swing.JComboBox<>();
        BtAdc = new javax.swing.JButton();
        BtRemove = new javax.swing.JButton();
        pnNfe = new javax.swing.JPanel();
        BtAnexar = new javax.swing.JButton();
        lbLink_nfe = new java.awt.Label();
        EdNumNF = new javax.swing.JTextField();
        BtNovo = new javax.swing.JButton();
        EdNomeEmp = new javax.swing.JTextField();
        lbStatus = new javax.swing.JLabel();
        JdcDataEmissao = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbListaDiverge = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtListaNF = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        BtBuscar = new javax.swing.JButton();
        cbStatusNfe = new javax.swing.JComboBox<>();
        cbListaTodasNF = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txObsCompra = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        EdListaNfeCod = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Divergencias na NF-e e OC: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txDescricao.setColumns(20);
        txDescricao.setRows(5);
        txDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição problema:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane1.setViewportView(txDescricao);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 450, 90));

        lbInsereLinhas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 11)); // NOI18N
        lbInsereLinhas.setText("\"Clique\" -> Divergencias nas linhas: 1, 2, 3, 4");
        lbInsereLinhas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbInsereLinhasMouseClicked(evt);
            }
        });
        jPanel2.add(lbInsereLinhas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, 14));

        cbDiverge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "OC não gerada", "Preço divergênte da OC", "CNPJ divergênte da OC", "Quantidade divergênte da OC", "Outro problema" }));
        jPanel2.add(cbDiverge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 200, -1));

        BtAdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_green.png"))); // NOI18N
        BtAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcActionPerformed(evt);
            }
        });
        jPanel2.add(BtAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 60, -1));

        BtRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_red.png"))); // NOI18N
        BtRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemoveActionPerformed(evt);
            }
        });
        jPanel2.add(BtRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 60, -1));

        pnNfe.setBackground(new java.awt.Color(255, 255, 255));
        pnNfe.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnNfe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtAnexar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/attach.png"))); // NOI18N
        BtAnexar.setText("ANEXAR");
        BtAnexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAnexarActionPerformed(evt);
            }
        });
        pnNfe.add(BtAnexar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        lbLink_nfe.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbLink_nfe.setForeground(new java.awt.Color(51, 102, 255));
        lbLink_nfe.setText("Link_nfe");
        lbLink_nfe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLink_nfeMouseClicked(evt);
            }
        });
        pnNfe.add(lbLink_nfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 346, -1));

        EdNumNF.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdNumNF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNumNFKeyPressed(evt);
            }
        });
        pnNfe.add(EdNumNF, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 28, 153, -1));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        pnNfe.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

        EdNomeEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdNomeEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdNomeEmpMouseClicked(evt);
            }
        });
        EdNomeEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNomeEmpKeyPressed(evt);
            }
        });
        pnNfe.add(EdNomeEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 28, 200, -1));

        lbStatus.setFont(new java.awt.Font("sansserif", 1, 10)); // NOI18N
        lbStatus.setText("STATUS");
        pnNfe.add(lbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        JdcDataEmissao.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataEmissao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Emissão NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        pnNfe.add(JdcDataEmissao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, -1));

        tbListaDiverge.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_dv", "Divergência", "Descrição", "Data e Hora", "Status"
            }
        ));
        tbListaDiverge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListaDivergeKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbListaDiverge);
        if (tbListaDiverge.getColumnModel().getColumnCount() > 0) {
            tbListaDiverge.getColumnModel().getColumn(0).setMinWidth(0);
            tbListaDiverge.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbListaDiverge.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lançamento de divergencias NF-e x OC", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtListaNF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_nfe", "Nº NF-e", "Fornecedor", "Status", "obs_compra", "dataEmi"
            }
        ));
        jtListaNF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaNFMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtListaNF);
        if (jtListaNF.getColumnModel().getColumnCount() > 0) {
            jtListaNF.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaNF.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaNF.getColumnModel().getColumn(0).setMaxWidth(0);
            jtListaNF.getColumnModel().getColumn(4).setMinWidth(0);
            jtListaNF.getColumnModel().getColumn(4).setPreferredWidth(0);
            jtListaNF.getColumnModel().getColumn(4).setMaxWidth(0);
            jtListaNF.getColumnModel().getColumn(5).setMinWidth(0);
            jtListaNF.getColumnModel().getColumn(5).setPreferredWidth(0);
            jtListaNF.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 480, 290));

        jLabel3.setText("Listar por:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 20));

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(BtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 28));

        cbStatusNfe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguardando", "Ajustada", "NF Cancelada", "NF Estornada", "Cart. Correção" }));
        jPanel3.add(cbStatusNfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 170, 28));

        cbListaTodasNF.setText("Lista todas");
        jPanel3.add(cbListaTodasNF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, 20));

        txObsCompra.setColumns(20);
        txObsCompra.setRows(5);
        txObsCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observações do Comprador:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane4.setViewportView(txObsCompra);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 485, 90));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-black.png"))); // NOI18N
        jButton1.setText("EDITAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        EdListaNfeCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdListaNfeCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdListaNfeCodKeyPressed(evt);
            }
        });
        jPanel3.add(EdListaNfeCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        jTabbedPane1.addTab("Listagem de NF-e", jPanel3);

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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            int input = JOptionPane.showConfirmDialog(null,
                    "Deseja mesmo cancelar o lançamento da NF-e?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {
                DivergeNfeDao dao = new DivergeNfeDao();
                DivergeNFE d = new DivergeNFE();
                d.setCod_nfe(cod_nfe);
                d.setNum_nfe(Integer.parseInt(EdNumNF.getText()));
                d.setNomeForne(EdNomeEmp.getText());
                dao.deleteNfe(d);

                try {
                    File file = new File("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + EdNumNF.getText() + "_" + EdNomeEmp.getText() + ".pdf");
                    file.delete();
                    habilitarB(1);
                } catch (Exception ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("deletar nota fiscal importada");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

                habilitarB(1);
            }
        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar NF-e");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {

            DivergeNfeDao dao = new DivergeNfeDao();
            if (dao.checkDivergeForNFe(cod_nfe)) {

                habilitarB(1);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Nenhuma divergência foi lançada para esta NF-e", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar NF-e");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed

    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtAnexarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAnexarActionPerformed
        try {
            DivergeNfeDao dao = new DivergeNfeDao();
            if (!dao.checkNFe(Integer.parseInt(EdNumNF.getText()), EdNomeEmp.getText())) {

                if (!EdNomeEmp.getText().equals("")) {
                    if (JdcDataEmissao.getDate() != null) {

                        JFileChooser jFileChooser = new JFileChooser();
                        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".pdf", "pdf");
                        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        jFileChooser.setAcceptAllFileFilterUsed(false);
                        jFileChooser.addChoosableFileFilter(filtro);

                        int respostDoFileChooser = jFileChooser.showOpenDialog(this);
                        File origem = null;
                        if (respostDoFileChooser == JFileChooser.APPROVE_OPTION) {
                            origem = jFileChooser.getSelectedFile();

                            File destino = null;
                            destino = new File("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + EdNumNF.getText() + "_" + EdNomeEmp.getText() + ".pdf");
                            lbLink_nfe.setText(destino.toString());
                            copyFile(origem, destino);

                            salvarNFe();

                            if (new File("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + EdNumNF.getText() + "_" + EdNomeEmp.getText() + ".pdf").isFile()) {
                                lbLink_nfe.setForeground(Color.decode("#3366ff"));
                            } else {
                                copyFile(origem, destino);

                                if (new File("\\\\192.168.24.252\\Hardsearch\\nfe_file\\" + EdNumNF.getText() + "_" + EdNomeEmp.getText() + ".pdf").isFile()) {
                                    lbLink_nfe.setForeground(Color.decode("#3366ff"));

                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Erro ao salvar arquivo no local, você deve excluir a NF-e e tentar realizar o cadastro novamente", "Atenção", JOptionPane.ERROR_MESSAGE);
                                    lbLink_nfe.setForeground(Color.decode("#ff0000"));
                                }

                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Você deve informar a data de emissão da nota", "Atenção", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Você deve informar o fornecedor", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "NF-e Já importada", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("importar NF-e");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_BtAnexarActionPerformed

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

    public void buscaFornecedor() {
        if (EdNumNF.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar no número da NF-e", "NF-e não informada", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                this.FDBuscaEmp = new FDBuscaEmp("", null, true);

                if (EdNomeEmp.getText().equals("")) {
                    this.FDBuscaEmp.setVisible(true);

                    this.EdNomeEmp.setText(String.valueOf(this.FDBuscaEmp.getNomeEmp()));

                    if (this.EdNomeEmp.getText().equals("null")) {
                        EdNomeEmp.setText("");
                    }
                    habilitarB(2);
                }
            } catch (ClassNotFoundException ex) {

            }
        }
    }

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
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_lbLink_nfeMouseClicked

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        buscaFornecedor();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void lbInsereLinhasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbInsereLinhasMouseClicked
        txDescricao.setText(txDescricao.getText() + "Divergências nas linhas: 1, 2, 3 e 4 da NF-e");
    }//GEN-LAST:event_lbInsereLinhasMouseClicked

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        readNFe();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void jtListaNFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaNFMouseClicked
        txObsCompra.setText("");
        try {
            String obsCompra = "";
            obsCompra = jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 4).toString();
            txObsCompra.setText(obsCompra);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jtListaNFMouseClicked

    private void BtAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcActionPerformed
        if (cbDiverge.getSelectedItem().equals("<Selecione>")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o tipo da divergência", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                DivergeNFE nf = new DivergeNFE();
                DivergeNFE nfe = new DivergeNFE();
                DivergeNfeDao dao = new DivergeNfeDao();

                nf.setCod_nfe(cod_nfe);
                nf.setFr_tipoDiverge(cbDiverge.getSelectedItem().toString());
                nf.setFr_descricao(txDescricao.getText());
                nf.setStatus_dv(0);
                dao.saveDivergeNFE(nf);
                nfe.setStatus_nfe(0);
                nfe.setCod_nfe(cod_nfe);
                dao.updateStatusNf(nfe);
                readDivergeForSaveNfe();
            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("lançar divergência na NF-e");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtAdcActionPerformed

    private void EdNomeEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNomeEmpKeyPressed

    }//GEN-LAST:event_EdNomeEmpKeyPressed

    private void EdNomeEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdNomeEmpMouseClicked
        if (EdNomeEmp.getText().equals("")) {
            buscaFornecedor();
        }

    }//GEN-LAST:event_EdNomeEmpMouseClicked

    private void tbListaDivergeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListaDivergeKeyPressed

    }//GEN-LAST:event_tbListaDivergeKeyPressed

    private void BtRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemoveActionPerformed
        try {

            if (tbListaDiverge.getSelectedRow() >= 0) {
                cod_dv = Integer.parseInt(tbListaDiverge.getValueAt(tbListaDiverge.getSelectedRow(), 0).toString());
                String status = tbListaDiverge.getValueAt(tbListaDiverge.getSelectedRow(), 4).toString();
                if (status.equals("AGUARDANDO")) {
                    DivergeNFE nf = new DivergeNFE();
                    DivergeNfeDao dao = new DivergeNfeDao();
                    nf.setFr_codDv(cod_dv);
                    nf.setCod_nfe(cod_nfe);
                    dao.deleteDivergeNfe(nf);
                    readDivergeForSaveNfe();
                } else {

                    if (codUser == 1) {

                        DivergeNFE nf = new DivergeNFE();
                        DivergeNfeDao dao = new DivergeNfeDao();
                        nf.setFr_codDv(cod_dv);
                        nf.setCod_nfe(cod_nfe);
                        dao.deleteDivergeNfe(nf);
                        readDivergeForSaveNfe();

                    } else {

                        JOptionPane.showMessageDialog(null, "Esta linha da NF-e já foi ajustada, ela não pode ser editada ou excluída", "Atenção", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Você deve selecionar a linha que deseja deletar", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("remover divergência da NF-e");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtRemoveActionPerformed

    private void EdNumNFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNumNFKeyPressed

    }//GEN-LAST:event_EdNumNFKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        cod_nfe = Integer.parseInt(jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 0).toString());
        EdNumNF.setText(jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 1).toString());
        EdNomeEmp.setText(jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 2).toString());
        JdcDataEmissao.setDate((Date) jtListaNF.getValueAt(jtListaNF.getSelectedRow(), 5));

        //limpa antes do clicked
        lbLink_nfe.setText("");
        txDescricao.setText("");

        listNfe();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EdListaNfeCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdListaNfeCodKeyPressed
        readNFe();
    }//GEN-LAST:event_EdListaNfeCodKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAdc;
    private javax.swing.JButton BtAnexar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtRemove;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JTextField EdListaNfeCod;
    private javax.swing.JTextField EdNomeEmp;
    private javax.swing.JTextField EdNumNF;
    private com.toedter.calendar.JDateChooser JdcDataEmissao;
    private javax.swing.JComboBox<String> cbDiverge;
    private javax.swing.JCheckBox cbListaTodasNF;
    private javax.swing.JComboBox<String> cbStatusNfe;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jtListaNF;
    private javax.swing.JLabel lbInsereLinhas;
    private java.awt.Label lbLink_nfe;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JPanel pnNfe;
    private javax.swing.JTable tbListaDiverge;
    private javax.swing.JTextArea txDescricao;
    private javax.swing.JTextArea txObsCompra;
    // End of variables declaration//GEN-END:variables

}
