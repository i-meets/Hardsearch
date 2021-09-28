package hard.compra.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.EmailException;
import hard.compra.dao.RatingDao;
import hard.compra.model.Rating;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import hard.compra.model.MailJava;
import hard.compra.model.MailJavaSender;
import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import java.awt.Dimension;
import java.awt.HeadlessException;

public class FrRatingView extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSÃO
    String versao = "0.1-21.0210.0";

    /**
     * Creates new form FrRatingView
     *
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrRatingView(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle e Envio de Avaliações de Fornecedores - V" + versao;
        JProgressBar.setVisible(false);
        LbCarregando.setVisible(false);
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrRatingView";
            u.setFr_codTela(codTela);
            ContVersaoDao vdao = new ContVersaoDao();
            for (ContVersao c : vdao.listTela(codTela)) {
                u.setFr_versaoTela(c.getVersaoAtualTela());
            }
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 3);
    }

    public void importarXlsx() throws FileNotFoundException, IOException {

        if (EdLink.getText().equals("")) {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(".csv", "csv");
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.addChoosableFileFilter(filtro);

            int respostDoFileChooser = jFileChooser.showOpenDialog(this);
            if (respostDoFileChooser == JFileChooser.APPROVE_OPTION) {
                File link = jFileChooser.getSelectedFile();
                EdLink.setText(link.toString());

                try {
                    BufferedReader br = new BufferedReader(new FileReader(EdLink.getText()));
                    DefaultTableModel model = (DefaultTableModel) TbImportAvalia.getModel();

                    Object[] tableLines = br.lines().toArray();

                    for (Object tableLine : tableLines) {
                        String line = tableLine.toString().trim();
                        String[] dataRow = line.split(";");
                        model.addRow(dataRow);
                    }
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(PnImport, "Erro: " + e);
                }

            } else {

            }
        } else {

            try {
                BufferedReader br = new BufferedReader(new FileReader(EdLink.getText()));
                DefaultTableModel model = (DefaultTableModel) TbImportAvalia.getModel();

                Object[] tableLines = br.lines().toArray();

                for (Object tableLine : tableLines) {
                    String line = tableLine.toString().trim();
                    String[] dataRow = line.split(";");
                    model.addRow(dataRow);
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(PnImport, "Erro: " + e);
            }
        }

    }

    public void save() throws ClassNotFoundException {

        try {

            RatingDao dao = new RatingDao();

            int totalRows = TbImportAvalia.getRowCount();

            if (JdDataAvalia.getDate() == null) {
                JOptionPane.showMessageDialog(PnImport, "Você deve informar a data da avaliação", "Data não informada", JOptionPane.WARNING_MESSAGE);

            } else {

                try {

                    for (int i = 0; i < totalRows; i++) {

                        int codForn = 0;
                        Rating p = new Rating();
                        //RECEBE DA TABELA O CÓDIGO DO FORNECEDOR COM LETRAS*
                        String nameForne = (String) TbImportAvalia.getModel().getValueAt(i, 0);
                        //LIMPA O CÓDIGO*
                        String rc1 = nameForne.replaceAll("[^0-9]", "");
                        //CONVERTE STRING EM INT*
                        codForn = Integer.parseInt(rc1);

                        String condPgmt = (String) TbImportAvalia.getModel().getValueAt(i, 1);
                        String condEntrega = (String) TbImportAvalia.getModel().getValueAt(i, 2);
                        String qualPreco = (String) TbImportAvalia.getModel().getValueAt(i, 3);
                        String qualidade = (String) TbImportAvalia.getModel().getValueAt(i, 4);
                        String avaliaTotal = (String) TbImportAvalia.getModel().getValueAt(i, 5);

                        if (codForn != 0 || !condPgmt.equals("") || !condEntrega.equals("") || !qualPreco.equals("") || !qualidade.equals("") || !avaliaTotal.equals("")) {
                            p.setFk_codEmp(codForn);
                            p.setCondPgmt(condPgmt);
                            p.setCondEntrega(condEntrega);
                            p.setQualPreco(qualPreco);
                            p.setQualidade(qualidade);
                            p.setAvaliaTotal(avaliaTotal);

                            java.util.Date dateAv = JdDataAvalia.getDate();
                            long dtA = dateAv.getTime();
                            java.sql.Date dateAvalia = new java.sql.Date(dtA);
                            p.setDataRating(dateAvalia);
                            try {

                                dao.SalvarRating(p);
                            } catch (Error e) {
                                EdLog.setText("Erro no processo, contate administrador\nErro: " + e);
                            }
                        } else {
                            JOptionPane.showMessageDialog(PnImport, "Atenção, fornecedor: " + codForn + " não importado por falta de dados!", "Dados não informados", JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    JOptionPane.showMessageDialog(PnImport, "Salvo com sucesso");
                    DefaultTableModel modelo = (DefaultTableModel) TbImportAvalia.getModel();
                    modelo.setNumRows(0);

                } catch (HeadlessException | ClassNotFoundException | NumberFormatException e) {
                    System.out.println(e);
                }

            }

        } catch (HeadlessException | NumberFormatException ex) {
            EdLog.setText("Erro no processo, contate administrador\nErro: " + ex);
        }

    }

    public void listaAvalia() {
        DefaultTableModel modelo = (DefaultTableModel) TbConteudoMail.getModel();
        modelo.setNumRows(0);
        RatingDao rdao = new RatingDao();
        int listarEnviadas = 0;

        if (JdFiltroDataAvalia.getDate() == null) {
            JOptionPane.showMessageDialog(PnImport, "Você deve informar a data da avaliação", "Data não informada", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                java.util.Date dateAv = JdFiltroDataAvalia.getDate();
                long dtA = dateAv.getTime();
                java.sql.Date dateAvalia = new java.sql.Date(dtA);

                if (JbListarEnviadas.isSelected()) {
                    listarEnviadas = 1;
                }

                for (Rating r : rdao.readAvalia(dateAvalia, listarEnviadas)) {
                    String status = "Não enviado";
                    if (r.getStatusAvalia() == 1) {
                        status = "Enviado";
                    }
                    modelo.addRow(new Object[]{
                        r.getCod(),
                        r.getFk_codEmp(),
                        r.getFk_nomeEmp(),
                        r.getFk_mailEmp(),
                        r.getCondPgmt(),
                        r.getCondEntrega(),
                        r.getQualPreco(),
                        r.getQualidade(),
                        r.getAvaliaTotal(),
                        status});

                }

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);

                Logger.getLogger(FrRatingView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void enviaAvalia() throws EmailException, MessagingException, InterruptedException, ClassNotFoundException {
        JProgressBar.setVisible(true);
        LbCarregando.setVisible(true);
        new Thread() {
            @Override
            public void run() {

                int totalRows = TbConteudoMail.getRowCount();
                if (totalRows != 0) {

                    int mailEnviado = 1;
                    int limiteEnvioMail = 1;
                    int erroMail = 0;
                    String error = "";
                    //Início do loop de coleta da tabela
                    for (int i = 0; i < totalRows; i++) {
                        MailJava mj = new MailJava();

                        //Verifica o número limite de envios
                        int EdmailEnviado = Integer.parseInt(EdLimiteEnvios.getText());
                        if (limiteEnvioMail <= EdmailEnviado) {

                            //configuracoes de envio
                            mj.setSmtpHostMail(EdServSaida.getText());
                            mj.setSmtpPortMail(EdPortaSmtp.getText());
                            String star = "false";
                            if (CbSSL.isSelected()) {
                                star = "true";
                            }
                            mj.setSmtpAuth("true");
                            mj.setSmtpStarttls(star);
                            mj.setUserMail(EdUser.getText());
                            mj.setFromNameMail("SISTEMA DE AVALIAÇÃO IMAC");
                            mj.setPassMail(new String(EdSenhaMail.getPassword()));
                            mj.setCharsetMail("");

//           
//                           /RECEBE DA TABELA O EMAIL DO FORNECEDOR
                            String nomeForne = (String) TbConteudoMail.getModel().getValueAt(i, 2);

                            //Valida se o e-mail já não foi enviado
                            String validaEnvio = (String) TbConteudoMail.getModel().getValueAt(i, 9);
                            if (!validaEnvio.equals("Enviado")) {

                                int codAvalia = (int) TbConteudoMail.getModel().getValueAt(i, 0);
                                String mailForne = (String) TbConteudoMail.getModel().getValueAt(i, 3);
                                String condPgmt = (String) TbConteudoMail.getModel().getValueAt(i, 4);
                                String qualEntrega = (String) TbConteudoMail.getModel().getValueAt(i, 5);
                                String qualPreco = (String) TbConteudoMail.getModel().getValueAt(i, 6);
                                String qualidade = (String) TbConteudoMail.getModel().getValueAt(i, 7);
                                String total = (String) TbConteudoMail.getModel().getValueAt(i, 8);

                                if (codAvalia == 0 || mailForne.equals("") || condPgmt.equals("") || qualEntrega.equals("") || qualPreco.equals("") || qualidade.equals("") || total.equals("")) {
                                    EdLog.setText(EdLog.getText() + "\n" + "NÃO ENVIADO - existem celulas em branco na linha da tabela para o fornecedor: " + nomeForne + " \n");
                                    erroMail++;
                                } else {

                                    mj.setSubjectMail("RQ2057 - Avaliação mensal de Performance " + nomeForne);
                                    String colorPgmt = "green";
                                    double pgmtTest = Double.parseDouble(condPgmt.replace(",", "."));
                                    if (pgmtTest < 4) {
                                        colorPgmt = "red";
                                    }
                                    String colorEntre = "red";
                                    double entreTest = Double.parseDouble(qualEntrega.replace(",", "."));
                                    if (entreTest < 4) {
                                        colorEntre = "red";
                                    }
                                    String colorPreco = "green";
                                    double precoTest = Double.parseDouble(qualPreco.replace(",", "."));
                                    if (precoTest < 4) {
                                        colorPreco = "red";
                                    }
                                    String colorQuali = "green";
                                    double qualiTest = Double.parseDouble(qualidade.replace(",", "."));
                                    if (qualiTest < 4) {
                                        colorQuali = "red";
                                    }
                                    String colorTotal = "green";
                                    double ttTest = Double.parseDouble(total.replace(",", "."));
                                    if (ttTest < 4) {
                                        colorTotal = "red";
                                    }

                                    //BodyMail dados do e-mail em HTML
                                    mj.setBodyMail("<h3>INDICE DE PERFORMANCE DE FORNECEDORES</h3><p>Prezado fornecedor " + nomeForne + ",<br>Segue a avaliacao mensal de fornecimento.</p><table border='1' style='width: 340px; height: 240px;  font-family: Calibri;'>\n"
                                            + "<tr><td style='text-align: center;'><h2></h2>CONDICAO DE PAGAMENTO</h2><p style='color:" + colorPgmt + "; font-size:28px; margin-top: 5px; margin-bottom: 6px; font-weight: bold'>" + condPgmt + "</p></td></tr>\n"
                                            + "<tr><td style='text-align: center;'><h2></h2>QUALIDADE DE ENTREGA</h2><p style='color:" + colorEntre + "; font-size:28px; margin-top: 5px; margin-bottom: 6px; font-weight: bold'>" + qualEntrega + "</p></td></tr>\n"
                                            + "<tr><td style='text-align: center;'><h2 ></h2>PRECO</h2><p style='color:" + colorPreco + "; font-size:28px; margin-top: 5px; margin-bottom: 6px; font-weight: bold'>" + qualPreco + "</p></td></tr>\n"
                                            + "<tr><td style='text-align: center;'><h2></h2>QUALIDADE</h2><p style='color:" + colorQuali + "; font-size:28px; margin-top: 5px; margin-bottom: 6px; font-weight: bold'>" + qualidade + "</p></td></tr>\n"
                                            + "<tr><td style='text-align: center;'><h2></h2>AVALIACAO TOTAL</h2><p style='color:" + colorTotal + "; font-size:28px; margin-top: 5px; margin-bottom: 6px; font-weight: bold'>" + total + "</p></td></tr>"
                                            + "<tr><td><p style='font-size: 14px;'>[5,0 - Otimo] - [4,0 a 4,99 - Bom] - [3,0 a 3,99 - Regular] - [2,0 a 2,99 - Fraco] - [1,0 a 1,99 - Pessimo]</p></td></tr> </table>"
                                            + "<p>O Indice de Performance do Fornecedor, e onde se verifica quais os fornecedores estao atendendo o nivel de qualificacao estipulado. Fornecedores com pontuacao abaixo de 4,0 devem encaminhar um plano de acao de melhorias.<br><br>"
                                            + "E-mail foi enviado automaticamente. Em caso de duvidas, entrar em contato com compras1@metalurgicaimac.com.br</p>");

                                    mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

                                    //INFORMA O DESTINATÁRIO
                                    Map<String, String> map = new HashMap<>();
                                    map.put(mailForne, nomeForne);
                                    EdLog.setText(EdLog.getText() + "Enviando -> " + mailForne + " - " + nomeForne + "");

                                    mj.setToMailsUsers(map);

                                    try {

                                        //Realiza o envio
                                        new MailJavaSender().senderMail(mj);
                                        mailEnviado++;
                                        limiteEnvioMail++;

                                        //Atualiza status da avaliação
                                        RatingDao upDao = new RatingDao();
                                        Rating up = new Rating();
                                        up.setCod(codAvalia);
                                        up.setStatusAvalia(1);
                                        upDao.updateStatus(up);
                                        EdLog.setText(EdLog.getText() + "\nEnviado\n\n");

                                    } catch (UnsupportedEncodingException | MessagingException e) {
                                        error = e.toString();
                                        if (error.contains("UGFzc3dvcmQ6")) {
                                            EdLog.setText(EdLog.getText() + "\n" + "NÃO ENVIADO: Usuário ou senha do e-mail incorretos\n\n");
                                        } else if (error.contains("Recipient address rejected")) {
                                            EdLog.setText(EdLog.getText() + "\n" + "NÃO ENVIADO: Endereço do destinatário incorreto!\n\n");
                                        } else {
                                            EdLog.setText(EdLog.getText() + "\n" + "NÃO ENVIADO\nERRO DESCONHECIDO: " + e + "\n\n");
                                        }
                                        erroMail++;
                                        limiteEnvioMail++;
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(FrRatingView.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } else {
                                EdLog.setText(EdLog.getText() + "\n" + "NÃO ENVIADO - Status do envio para o fornecedor: " + nomeForne + " é de já enviado, novo envio não permitido\n");
                                erroMail++;
                            }

                        }
                        listaAvalia();
                    }
                    EdLog.setText(EdLog.getText() + "\n\n" + "Finalizado, total envios: " + (mailEnviado - 1) + "\nTotal erros: " + erroMail + "\n\n");
                    JProgressBar.setValue(100);
                    JOptionPane.showMessageDialog(PnImport, "Envio terminou!\nConsulte a tela de log para a verificação de envio.", "Enviado", JOptionPane.INFORMATION_MESSAGE);
                    JProgressBar.setVisible(false);
                    LbCarregando.setVisible(false);
                    listaAvalia();
                } else {
                    JOptionPane.showMessageDialog(PnImport, "Você buscar pelo menos um dado para envio!", "Tabela Vazia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 101; i++) {
                    try {
                        sleep(200);
                        JProgressBar.setValue(i);

                        if (JProgressBar.getValue() <= 30) {
                            LbCarregando.setText("Lendo endereços...");
                        } else if (JProgressBar.getValue() <= 50) {
                            LbCarregando.setText("Criando e-mails...");

                        } else if (JProgressBar.getValue() <= 70) {
                            LbCarregando.setText("Enviando e-mails...");
                        } else if (JProgressBar.getValue() <= 80) {
                            LbCarregando.setText("Limpando a bagunça...");
                        } else if (JProgressBar.getValue() <= 90) {
                            LbCarregando.setText("Gerando logs...");
                        }

                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        PnImport = new javax.swing.JPanel();
        EdLink = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        JdDataAvalia = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        BtProteger = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbImportAvalia = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JbRevisado = new javax.swing.JCheckBox();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        JdFiltroDataAvalia = new com.toedter.calendar.JDateChooser();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbConteudoMail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        EdUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        EdServSaida = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        EdPortaSmtp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        CbSSL = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        EdLog = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        EdSenhaMail = new javax.swing.JPasswordField();
        EdLimiteEnvios = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        JbListarEnviadas = new javax.swing.JCheckBox();
        jButton7 = new javax.swing.JButton();
        JProgressBar = new javax.swing.JProgressBar();
        LbCarregando = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setToolTipText("");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnImport.setBackground(new java.awt.Color(255, 255, 255));
        PnImport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PnImport.add(EdLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 550, -1));

        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PnImport.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 28, -1, -1));

        JdDataAvalia.setBackground(new java.awt.Color(255, 255, 255));
        PnImport.add(JdDataAvalia, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 130, -1));

        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_green.png"))); // NOI18N
        jButton2.setText("LINHA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        PnImport.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        BtProteger.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtProteger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/protect.png"))); // NOI18N
        BtProteger.setText("PROTEGER");
        BtProteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProtegerActionPerformed(evt);
            }
        });
        PnImport.add(BtProteger, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, -1, -1));

        TbImportAvalia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FORNECEDOR", "QUAL.COND.PGTO", "QUAL.ENTREGA", "QUAL.PRECO", "QUALIDADE", "TOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TbImportAvalia);

        PnImport.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1015, 390));

        jButton3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/import-db.png"))); // NOI18N
        jButton3.setText("IMPORTAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        PnImport.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, -1, -1));

        jButton4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton4.setText("SAÍR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        PnImport.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 460, -1, -1));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel1.setText("Data da avalição:");
        PnImport.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 15, -1, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel2.setText("Selecionar arquivo:");
        PnImport.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, -1, -1));

        JbRevisado.setText("Campos revisados");
        PnImport.add(JbRevisado, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 35, -1, -1));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_red.png"))); // NOI18N
        jButton8.setText("LINHA");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        PnImport.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, -1, -1));

        jTabbedPane1.addTab("Importação de Avaliações", PnImport);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdFiltroDataAvalia.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(JdFiltroDataAvalia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 130, -1));

        TbConteudoMail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD. AVA.", "COD. FORNEC.", "FORNECEDOR", "E-MAIL", "QUAL.COND.PGTO", "QUAL.ENTREGA", "QUAL.PRECO", "QUALIDADE", "TOTAL", "STATUS ENVIO"
            }
        ));
        jScrollPane2.setViewportView(TbConteudoMail);

        jTabbedPane2.addTab("Dados de envio", jScrollPane2);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdUser.setText("seuemail@seuservidor.com.br");
        jPanel2.add(EdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 290, -1));

        jLabel4.setText("E-mail de envio:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 15, -1, -1));

        jLabel5.setText("Servidor de envio SMTP:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 65, -1, -1));

        EdServSaida.setText("smtp.seuservidor.com.br");
        jPanel2.add(EdServSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 290, -1));

        jLabel6.setText("Porta:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 165, -1, -1));

        EdPortaSmtp.setText("587");
        jPanel2.add(EdPortaSmtp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 130, -1));

        jLabel7.setText("Senha do e-mail:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 115, -1, -1));

        CbSSL.setText("Utilizar segurança SSL");
        jPanel2.add(CbSSL, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, -1, 30));

        EdLog.setColumns(20);
        EdLog.setRows(5);
        jScrollPane3.setViewportView(EdLog);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 690, 220));

        jLabel8.setText("Log envio:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        EdSenhaMail.setText("suasenha");
        jPanel2.add(EdSenhaMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 290, -1));

        EdLimiteEnvios.setText("1");
        jPanel2.add(EdLimiteEnvios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 50, -1));

        jLabel9.setText("Número maximo de envios:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 216, -1, -1));

        jTabbedPane2.addTab("Configurações de envio", jPanel2);

        jPanel1.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1030, 350));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel3.setText("Data de importação:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/submit.png"))); // NOI18N
        jButton6.setText("Gerar ENVIOS");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, -1, -1));

        JbListarEnviadas.setText("Listar avaliações já enviadas");
        jPanel1.add(JbListarEnviadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jButton7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton7.setText("SAÍR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 460, -1, -1));
        jPanel1.add(JProgressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 220, -1));
        jPanel1.add(LbCarregando, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, 150, 20));

        jTabbedPane1.addTab("Envio de Avaliações", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            importarXlsx();
        } catch (IOException ex) {
            Logger.getLogger(FrRatingView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel model = (DefaultTableModel) TbImportAvalia.getModel();
        String[] dataRow = null;

        model.addRow(dataRow);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BtProtegerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProtegerActionPerformed

        if (BtProteger.getText().equals("PROTEGER")) {
            TbImportAvalia.setEnabled(false);
            BtProteger.setText("LIBERAR");
        } else if (BtProteger.getText().equals("LIBERAR")) {
            TbImportAvalia.setEnabled(true);
            BtProteger.setText("PROTEGER");
        }

    }//GEN-LAST:event_BtProtegerActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (!JbRevisado.isSelected()) {
                JOptionPane.showMessageDialog(PnImport, "Você deve revisar as celulas, e selecionar o check-box 'Campos revisados'", "Atenção, Revisar as informações", JOptionPane.WARNING_MESSAGE);
            } else {
                save();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrRatingView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            enviaAvalia();
        } catch (EmailException | MessagingException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(FrRatingView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        listaAvalia();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) TbImportAvalia.getModel();
        if (TbImportAvalia.getSelectedRow() >= 0) {
            dtm.removeRow(TbImportAvalia.getSelectedRow());
            TbImportAvalia.setModel(dtm);
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtProteger;
    private javax.swing.JCheckBox CbSSL;
    private javax.swing.JTextField EdLimiteEnvios;
    private javax.swing.JTextField EdLink;
    private javax.swing.JTextArea EdLog;
    private javax.swing.JTextField EdPortaSmtp;
    private javax.swing.JPasswordField EdSenhaMail;
    private javax.swing.JTextField EdServSaida;
    private javax.swing.JTextField EdUser;
    private javax.swing.JProgressBar JProgressBar;
    private javax.swing.JCheckBox JbListarEnviadas;
    private javax.swing.JCheckBox JbRevisado;
    private com.toedter.calendar.JDateChooser JdDataAvalia;
    private com.toedter.calendar.JDateChooser JdFiltroDataAvalia;
    private javax.swing.JLabel LbCarregando;
    private javax.swing.JPanel PnImport;
    private javax.swing.JTable TbConteudoMail;
    private javax.swing.JTable TbImportAvalia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
