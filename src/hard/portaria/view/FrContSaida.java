package hard.portaria.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.portaria.dao.ContSaidaDao;
import hard.rh.dao.FuncionarioDao;
import hard.portaria.model.ContSaida;
import hard.rh.model.Funcionario;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrContSaida extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrContSaida(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle de Solicitações de Saída - V" + versao;

        JcRetornoNao.setEnabled(false);
        JcRetornoSim.setEnabled(false);
        EdNomeFuncio.setEditable(false);
        EdSetorFuncio.setEditable(false);
        EdHoraSaida.setEditable(false);
        EdCodFuncio.setEditable(false);

        readContSaida();

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrContSaida";
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
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 4);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                EdCodSaida.setEditable(true);
                EdCodFuncio.setEditable(false);
                EdNomeFuncio.setEditable(false);
                EdSetorFuncio.setEditable(false);

                EdHoraSaida.setEditable(false);

                break;
        }
    }

    public void limpaCampos() {
        EdCodFuncio.setText(null);
        EdCodSaida.setText(null);
        EdHoraSaida.setText(null);
        EdNomeFuncio.setText(null);
        EdSetorFuncio.setText(null);
        EdBuscaFuncio.setText(null);

        JDcDataSaida.setDate(null);
        JcRetornoNao.setSelected(false);
        JcRetornoSim.setSelected(false);

    }

    public void readContSaida() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        ContSaidaDao cdao = new ContSaidaDao();

        if (EdBuscaFuncio.getText().equals("")) {
            try {

                for (ContSaida c : cdao.readContSaida()) {
                    modelo.addRow(new Object[]{
                        c.getCodSaida(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getDataSaidaFuncio(),
                        c.getHoraSaidaFuncio(),
                        c.getResponsavel(),
                        c.getRetornaTraba()
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

                for (ContSaida c : cdao.readContSaidaForDesc(EdBuscaFuncio.getText())) {
                    modelo.addRow(new Object[]{
                        c.getCodSaida(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getDataSaidaFuncio(),
                        c.getHoraSaidaFuncio(),
                        c.getResponsavel(),
                        c.getRetornaTraba()
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

    public void validarCodCadastro() throws ParseException, SQLException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário!");
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();

                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado");

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();

        for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNomeFuncio.setText(fu.getNomeFuncionario());
            EdSetorFuncio.setText(fu.getSetorFuncionario());

        }
    }

    public void checkExit() {
        ContSaidaDao cdao = new ContSaidaDao();
        if (EdCodSaida.getText().equals("") || EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma solicitação na tabela", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (cdao.checkExit(Integer.parseInt(EdCodSaida.getText()), 2)) {

                    JOptionPane.showMessageDialog(null, "Solicitação já possui confirmação da saída!", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);

                } else {
                    ConfirmaSaida();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar saída");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void checkRemoveExit() {
        ContSaidaDao cdao = new ContSaidaDao();
        if (EdCodSaida.getText().equals("") || EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma solicitação na tabela!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (cdao.checkExit(Integer.parseInt(EdCodSaida.getText()), 1)) {

                    JOptionPane.showMessageDialog(null, "Solicitação saída não foi confirmação!", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);

                } else {
                    RemoverConfirmaSaida();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("remover cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }

    }

    public void ConfirmaSaida() throws ClassNotFoundException {

        ContSaida c = new ContSaida();
        ContSaidaDao dao = new ContSaidaDao();

        String funcio = EdNomeFuncio.getText();
        int input = JOptionPane.showConfirmDialog(null,
                " ATENÇÃO!" + "\nDeseja confirmar a saída do funcionario: " + funcio + "", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (input == 0) {
            c.setCodSaida(Integer.parseInt(EdCodSaida.getText()));

            dao.updateConfirmaSaida(c);
            limpaCampos();
            readContSaida();
        }

    }

    public void RemoverConfirmaSaida() throws ClassNotFoundException {

        ContSaida c = new ContSaida();
        ContSaidaDao dao = new ContSaidaDao();

        String funcio = EdNomeFuncio.getText();
        int input = JOptionPane.showConfirmDialog(null,
                " ATENÇÃO!" + "\nDeseja remover a confirmação de saída do funcionario: " + funcio + "", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (input == 0) {
            c.setCodSaida(Integer.parseInt(EdCodSaida.getText()));

            dao.deleteConfirmaSaida(c);
            limpaCampos();
            readContSaida();
        }

    }

    public void listSaidaForCod() throws ParseException, SQLException {

        ContSaidaDao pdao = new ContSaidaDao();

        if (EdCodSaida.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código da solicitação de saída!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                for (ContSaida cs : pdao.readSaidaForCod(EdCodSaida.getText())) {

                    EdCodFuncio.setText(Integer.toString(cs.getFr_codFuncionario()));
                    validarCodCadastro();
                    EdHoraSaida.setText(cs.getHoraSaidaFuncio());

                    java.util.Date date = cs.getDataSaidaFuncio();
                    JDcDataSaida.setDate(date);
                    int retorno = cs.getRetornaTraba();
                    LbResponsavel.setText(cs.getResponsavel());

                    switch (retorno) {
                        case 1:
                            JcRetornoSim.setSelected(true);
                            JcRetornoNao.setSelected(false);
                            break;
                        case 2:
                            JcRetornoNao.setSelected(true);
                            JcRetornoSim.setSelected(false);
                            break;
                        default:
                            System.out.println("erro so buscar retorno: " + retorno);
                            break;
                    }

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
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
        jPanel6 = new javax.swing.JPanel();
        EdSetorFuncio = new javax.swing.JTextField();
        EdCodFuncio = new javax.swing.JTextField();
        EdNomeFuncio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        JDcDataSaida = new com.toedter.calendar.JDateChooser();
        EdHoraSaida = new javax.swing.JFormattedTextField();
        JcRetornoNao = new javax.swing.JCheckBox();
        JcRetornoSim = new javax.swing.JCheckBox();
        LbResponsavel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        EdCodSaida = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        EdBuscaFuncio = new javax.swing.JTextField();
        BtBuscarNomeFuncio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdSetorFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdSetorFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdSetorFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(EdSetorFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, -1));

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodFuncioActionPerformed(evt);
            }
        });
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel6.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdNomeFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 330, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 350, 140));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Retorna ao trabalho:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 108, -1, 20));

        JDcDataSaida.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data saída:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel2.add(JDcDataSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, -1));

        EdHoraSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hora saída:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        try {
            EdHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        EdHoraSaida.setToolTipText("");
        jPanel2.add(EdHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 90, -1));

        JcRetornoNao.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        JcRetornoNao.setText("Não");
        jPanel2.add(JcRetornoNao, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, -1, -1));

        JcRetornoSim.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        JcRetornoSim.setText("Sim");
        jPanel2.add(JcRetornoSim, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, -1, -1));
        jPanel2.add(LbResponsavel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 150, 15));

        jLabel2.setText("Solicitado:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 240, 140));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 15, -1, -1));

        EdCodSaida.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jPanel1.add(EdCodSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 68, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Saída", "Cód. Funcionario", "Nome Funcionário", "Data Saída", "Hora Saída", "Responsavel", "Retorna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(0);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(0).setMaxWidth(0);
            tabela.getColumnModel().getColumn(1).setMinWidth(0);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(1).setMaxWidth(0);
            tabela.getColumnModel().getColumn(6).setMinWidth(0);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 620, 160));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdBuscaFuncio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        EdBuscaFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdBuscaFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaFuncioKeyPressed(evt);
            }
        });
        jPanel7.add(EdBuscaFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 230, -1));

        BtBuscarNomeFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNomeFuncio.setText("Buscar");
        BtBuscarNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeFuncioActionPerformed(evt);
            }
        });
        jPanel7.add(BtBuscarNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 350, 90));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/saida.png"))); // NOI18N
        jButton2.setText("CONFIRMAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 15, 120, 31));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Confirmar Saída:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Remover Saída:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_red.png"))); // NOI18N
        jButton3.setText("REMOVER");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 240, 90));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 78, 33));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 620, 55));

        jTabbedPane1.addTab("Solicitações de Saída", jPanel1);

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

    private void EdSetorFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdSetorFuncioActionPerformed

    }//GEN-LAST:event_EdSetorFuncioActionPerformed

    private void EdCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncioActionPerformed

    }//GEN-LAST:event_EdCodFuncioActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed

    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void EdNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdNomeFuncioActionPerformed

    }//GEN-LAST:event_EdNomeFuncioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            listSaidaForCod();
        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        try {

            if (tabela.getSelectedRow() != -1) {
                DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                int seectedRow = tabela.getSelectedRow();
                jTabbedPane1.setSelectedIndex(0);

                EdCodSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                EdCodFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
                validarCodCadastro();
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(seectedRow, 3).toString());
                JDcDataSaida.setDate(date);
                EdHoraSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());
                LbResponsavel.setText(tabela.getValueAt(tabela.getSelectedRow(), 5).toString());
                int retorno = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 6).toString());

                switch (retorno) {
                    case 1:
                        JcRetornoSim.setSelected(true);
                        JcRetornoNao.setSelected(false);
                        break;
                    case 2:
                        JcRetornoNao.setSelected(true);
                        JcRetornoSim.setSelected(false);
                        break;
                    default:
                        System.out.println("erro so buscar retorno: " + retorno);
                        break;
                }

            }
        } catch (NumberFormatException | SQLException | ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseEntered

    }//GEN-LAST:event_tabelaMouseEntered

    private void EdBuscaFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaFuncioKeyPressed
        readContSaida();
    }//GEN-LAST:event_EdBuscaFuncioKeyPressed

    private void BtBuscarNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeFuncioActionPerformed
        readContSaida();
    }//GEN-LAST:event_BtBuscarNomeFuncioActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        checkExit();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        checkRemoveExit();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed

        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscarNomeFuncio;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtSair;
    private javax.swing.JTextField EdBuscaFuncio;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodSaida;
    private javax.swing.JFormattedTextField EdHoraSaida;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdSetorFuncio;
    private com.toedter.calendar.JDateChooser JDcDataSaida;
    private javax.swing.JCheckBox JcRetornoNao;
    private javax.swing.JCheckBox JcRetornoSim;
    private javax.swing.JLabel LbResponsavel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
