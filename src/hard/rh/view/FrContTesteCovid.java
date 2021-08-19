package hard.rh.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import hard.home.view.FDBuscaFuncio;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FuncionarioDao;
import hard.rh.dao.TesteCovidDao;
import hard.rh.model.Funcionario;
import hard.rh.model.TesteCovid;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrContTesteCovid extends javax.swing.JInternalFrame {

    private FDBuscaFuncio FDBuscaFuncio;
    FDErroOcorrido fdErroOcorrido;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    public FrContTesteCovid(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        habilitarB(1);
        listarTestes();
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrContTesteCovid";
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

    public void habilitarB(int op) throws ClassNotFoundException {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);

                JcResultado.setEnabled(false);
                EdCodFuncio.setEnabled(false);
                BtBuscarCodFuncionario.setEnabled(false);
                EdNomeFuncio.setEnabled(false);
                EdSetorFuncio.setEnabled(false);
                EdTurnoFuncio.setEnabled(false);

                EdCodTeste.requestFocus();
                break;

            case 2:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtCancelar.setEnabled(true);
                EdCodFuncio.setEnabled(true);
                BtBuscarCodFuncionario.setEnabled(true);
                JcResultado.setEnabled(true);
                EdNomeFuncio.setEnabled(true);
                EdSetorFuncio.setEnabled(true);
                EdTurnoFuncio.setEnabled(true);

                EdNomeFuncio.requestFocus();
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtCancelar.setEnabled(true);
                JcResultado.setEnabled(true);
                EdCodFuncio.setEnabled(false);
                BtBuscarCodFuncionario.setEnabled(false);
                EdNomeFuncio.setEnabled(true);
                EdSetorFuncio.setEnabled(true);
                EdTurnoFuncio.setEnabled(true);
                EdNomeFuncio.setEditable(false);
                EdSetorFuncio.setEditable(false);
                EdTurnoFuncio.setEditable(false);

                EdNomeFuncio.requestFocus();
                break;
        }
    }

    public void limpaCampos() {
        EdCodTeste.setText(null);
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        EdSetorFuncio.setText(null);
        EdTurnoFuncio.setText(null);
        JdcDataTeste.setDate(null);
        JcResultado.setSelectedItem("AGUARDANDO");
    }

    public void buscaMaiorCodTeste() throws ClassNotFoundException {
        TesteCovidDao dao = new TesteCovidDao();
        for (TesteCovid t : dao.readMaiorCodTeste()) {

            int codPar;
            codPar = t.getCodTeste();
            EdCodTeste.setText(Integer.toString(codPar));
        }
    }

    public void validarCodFuncio() {
        FuncionarioDao tdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário!");
        } else {

            try {
                if (tdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();
                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado");

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao tdao = new FuncionarioDao();

        for (Funcionario fu : tdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNomeFuncio.setText(fu.getNomeFuncionario());
            EdSetorFuncio.setText(fu.getSetorFuncionario());
            EdTurnoFuncio.setText(fu.getTurnoFuncionario());

        }
    }

    public void validaCadastroTesteCovid() {
        TesteCovidDao tdao = new TesteCovidDao();

        try {
            String codFuncio = EdCodFuncio.getText();

            if (tdao.checkCadContra(codFuncio)) {

                JOptionPane.showMessageDialog(null, "Funcionário já possui teste cadastrado e sem resultado apontado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

            } else if (JdcDataTeste.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Você deve preencher as datas!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                salvarTesteCovid();
            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void salvarTesteCovid() {
        if (EdCodTeste.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do Funcionário!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {

            TesteCovid te = new TesteCovid();
            TesteCovidDao dao = new TesteCovidDao();

            te.setCodTeste(Integer.parseInt(EdCodTeste.getText()));
            te.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
            te.setFr_nomeFuncionario(EdNomeFuncio.getText());
            java.util.Date dataRTesteCovid = JdcDataTeste.getDate();
            long dtTesteCovid = dataRTesteCovid.getTime();
            java.sql.Date dateTesteCovid = new java.sql.Date(dtTesteCovid);
            te.setDataTeste(dateTesteCovid);
            int resultado = 0;
            if (JcResultado.getSelectedItem().equals("NEGATIVO")) {
                resultado = 1;
            } else if (JcResultado.getSelectedItem().equals("POSITIVO")) {
                resultado = 2;
            }

            te.setResultadoTeste(resultado);

            try {
                dao.createTesteCovid(te);
                habilitarB(1);
                limpaCampos();
                listarTestes();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("salvar registro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarTestes() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) JtTesteSemResultado.getModel();
        modelo.setNumRows(0);
        TesteCovidDao pdao = new TesteCovidDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        for (TesteCovid t : pdao.readTesteCovid("0", "")) {
            modelo.addRow(new Object[]{
                t.getCodTeste(),
                t.getFr_codFuncionario(),
                t.getFr_nomeFuncionario(),
                formatdata.format(t.getDataTeste())
            });
        }
    }

    public void listarTestesCovid() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) JtTestesCovid.getModel();
        modelo.setNumRows(0);
        TesteCovidDao pdao = new TesteCovidDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        String filtoR = "";
        if (JcPositivo.isSelected()) {
            filtoR = "2";
        } else if (JcNegativo.isSelected()) {
            filtoR = "1";
        }

        for (TesteCovid t : pdao.readTesteCovid(filtoR, EdNomeFuncionario.getText())) {
            int result = t.getResultadoTeste();
            String resultado = "AGUARDANDO";
            if (result == 1) {
                resultado = "NEGATIVO";
            } else if (result == 2) {
                resultado = "POSITIVO";
            }

            modelo.addRow(new Object[]{
                t.getCodTeste(),
                t.getFr_nomeFuncionario(),
                formatdata.format(t.getDataTeste()),
                resultado
            });
        }
    }

    public void listarCodTeste() {

        if (EdCodTeste.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve informar o código do contrato para editar", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            TesteCovidDao cdao = new TesteCovidDao();

            try {
                for (TesteCovid c : cdao.readTesteForCod(Integer.parseInt(EdCodTeste.getText()))) {
                    EdCodFuncio.setText(Integer.toString(c.getFr_codFuncionario()));
                    validarCodFuncio();
                    JdcDataTeste.setDate(c.getDataTeste());

                    if (c.getResultadoTeste() != 0) {
                        habilitarB(1);
                        if (c.getResultadoTeste() == 1) {
                            JcResultado.setForeground(Color.decode("#0f943b"));
                            JcResultado.setSelectedItem("NEGATIVO");
                        } else if (c.getResultadoTeste() == 2) {
                            JcResultado.setForeground(Color.decode("#db0000"));
                            JcResultado.setSelectedItem("POSITIVO");
                        }

                    } else {
                        JcResultado.setSelectedItem("AGUARDANDO");
                        habilitarB(3);
                    }

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar testes");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void editarTeste() {
        if (EdCodTeste.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do Funcionário!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {

            TesteCovid te = new TesteCovid();
            TesteCovidDao dao = new TesteCovidDao();

            te.setCodTeste(Integer.parseInt(EdCodTeste.getText()));

            java.util.Date dataRTesteCovid = JdcDataTeste.getDate();
            long dtTesteCovid = dataRTesteCovid.getTime();
            java.sql.Date dateTesteCovid = new java.sql.Date(dtTesteCovid);
            te.setDataTeste(dateTesteCovid);

            int resultado = 0;

            if (JcResultado.getSelectedItem().equals("NEGATIVO")) {
                resultado = 1;
            } else if (JcResultado.getSelectedItem().equals("POSITIVO")) {
                resultado = 2;
            }

            te.setResultadoTeste(resultado);

            try {
                dao.updateStatusTesteCovid(te);
                habilitarB(1);
                limpaCampos();
                listarTestes();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar teste");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void deletarTeste() throws ClassNotFoundException {
        TesteCovid i = new TesteCovid();
        TesteCovidDao dao = new TesteCovidDao();

        if (EdCodTeste.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário para excluír", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            i.setCodTeste(Integer.parseInt(EdCodTeste.getText()));
            try {
                String nome = EdNomeFuncio.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        "Deseja mesmo excluír o teste do contrato do funcionário  " + nome, "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);

                if (input == 0) {
                    dao.deleteTesteCovid(i);
                    habilitarB(1);
                    limpaCampos();
                    listarTestes();

                } else {
                    JOptionPane.showMessageDialog(null, "Exclusão cancelada", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar teste");
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
        jPanel7 = new javax.swing.JPanel();
        EdTurnoFuncio = new javax.swing.JTextField();
        EdSetorFuncio = new javax.swing.JTextField();
        EdNomeFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncionario = new javax.swing.JButton();
        EdCodFuncio = new javax.swing.JTextField();
        JdcDataTeste = new com.toedter.calendar.JDateChooser();
        EdCodTeste = new javax.swing.JTextField();
        BtBuscaCodTeste = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtTesteSemResultado = new javax.swing.JTable();
        JcResultado = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        BtNovo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtTestesCovid = new javax.swing.JTable();
        JcPositivo = new javax.swing.JCheckBox();
        BtListarTestesCovid = new javax.swing.JButton();
        EdNomeFuncionario = new javax.swing.JTextField();
        JcNegativo = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setForeground(new java.awt.Color(255, 255, 255));
        setIconifiable(true);
        setTitle("Controle de Testes COVID-19 - V0.1BETA-20.0304.0");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdTurnoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turno:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(EdTurnoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 160, -1));

        EdSetorFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(EdSetorFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 170, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 330, -1));

        BtBuscarCodFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncionario.setText("Buscar");
        BtBuscarCodFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncionarioActionPerformed(evt);
            }
        });
        jPanel7.add(BtBuscarCodFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel7.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 350, 140));

        JdcDataTeste.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataTeste.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Teste:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(JdcDataTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 150, -1));

        EdCodTeste.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Teste:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodTeste.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodTesteKeyPressed(evt);
            }
        });
        jPanel1.add(EdCodTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        BtBuscaCodTeste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaCodTeste.setText("Buscar");
        BtBuscaCodTeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaCodTesteActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscaCodTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

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
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 80, 33));

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

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 530, 55));

        JtTesteSemResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Funcionário", "Data", "Resultado"
            }
        ));
        JtTesteSemResultado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtTesteSemResultadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JtTesteSemResultadoMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(JtTesteSemResultado);
        if (JtTesteSemResultado.getColumnModel().getColumnCount() > 0) {
            JtTesteSemResultado.getColumnModel().getColumn(0).setMinWidth(0);
            JtTesteSemResultado.getColumnModel().getColumn(0).setPreferredWidth(0);
            JtTesteSemResultado.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 530, 150));

        JcResultado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AGUARDANDO", "NEGATIVO", "POSITIVO" }));
        jPanel1.add(JcResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 150, -1));

        jLabel1.setText("Resultado:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 155, -1, -1));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel1.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 90, 30));

        jTabbedPane1.addTab("Controle de Teste Covid", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JtTestesCovid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Funcionario", "Data", "Resultado"
            }
        ));
        JtTestesCovid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtTestesCovidMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JtTestesCovid);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 65, 530, 340));

        JcPositivo.setText("Testes Positivos");
        JcPositivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JcPositivoMouseClicked(evt);
            }
        });
        JcPositivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcPositivoActionPerformed(evt);
            }
        });
        jPanel2.add(JcPositivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        BtListarTestesCovid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtListarTestesCovid.setText("Buscar");
        BtListarTestesCovid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtListarTestesCovidActionPerformed(evt);
            }
        });
        jPanel2.add(BtListarTestesCovid, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, -1, -1));

        EdNomeFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Funcionario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdNomeFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdNomeFuncionarioKeyPressed(evt);
            }
        });
        jPanel2.add(EdNomeFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, 250, -1));

        JcNegativo.setText("Testes Negativos");
        JcNegativo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JcNegativoMouseClicked(evt);
            }
        });
        jPanel2.add(JcNegativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        jTabbedPane1.addTab("Listagem de Testes", jPanel2);

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

    private void BtBuscarCodFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncionarioActionPerformed
        this.FDBuscaFuncio = new FDBuscaFuncio(null, true);

        if (EdCodFuncio.getText().equals("")) {
            this.FDBuscaFuncio.setVisible(true);

            this.EdCodFuncio.setText(String.valueOf(this.FDBuscaFuncio.getCodFuncio()));

            if (this.EdCodFuncio.getText().equals("0")) {
                EdCodFuncio.setText("");

            } else {
                validarCodFuncio();
            }

        } else {

            validarCodFuncio();
        }
    }//GEN-LAST:event_BtBuscarCodFuncionarioActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodFuncio();
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            deletarTeste();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar teste");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        validaCadastroTesteCovid();

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarTeste();
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        try {
            habilitarB(1);
            limpaCampos();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cancelar");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        try {
            habilitarB(2);
            buscaMaiorCodTeste();
            EdCodFuncio.requestFocus();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtBuscaCodTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaCodTesteActionPerformed
        listarCodTeste();
    }//GEN-LAST:event_BtBuscaCodTesteActionPerformed

    private void JtTesteSemResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtTesteSemResultadoMouseClicked
        DefaultTableModel model = (DefaultTableModel) JtTesteSemResultado.getModel();
        int seectedRow = JtTesteSemResultado.getSelectedRow();
        if (JtTesteSemResultado.getSelectedRow() != -1) {
            EdCodTeste.setText(JtTesteSemResultado.getValueAt(JtTesteSemResultado.getSelectedRow(), 0).toString());
            EdCodFuncio.setText(JtTesteSemResultado.getValueAt(JtTesteSemResultado.getSelectedRow(), 1).toString());
            listarCodTeste();
        }
    }//GEN-LAST:event_JtTesteSemResultadoMouseClicked

    private void JtTesteSemResultadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtTesteSemResultadoMouseEntered

    }//GEN-LAST:event_JtTesteSemResultadoMouseEntered

    private void BtListarTestesCovidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtListarTestesCovidActionPerformed
        try {
            listarTestesCovid();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar teste");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtListarTestesCovidActionPerformed

    private void JcNegativoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JcNegativoMouseClicked
        JcPositivo.setSelected(false);
    }//GEN-LAST:event_JcNegativoMouseClicked

    private void JcPositivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcPositivoActionPerformed

    }//GEN-LAST:event_JcPositivoActionPerformed

    private void JcPositivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JcPositivoMouseClicked
        JcNegativo.setSelected(false);
    }//GEN-LAST:event_JcPositivoMouseClicked

    private void EdNomeFuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdNomeFuncionarioKeyPressed
        try {
            listarTestesCovid();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar teste");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdNomeFuncionarioKeyPressed

    private void EdCodTesteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodTesteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            listarCodTeste();
        }
    }//GEN-LAST:event_EdCodTesteKeyPressed

    private void JtTestesCovidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtTestesCovidMouseClicked

    }//GEN-LAST:event_JtTestesCovidMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscaCodTeste;
    private javax.swing.JButton BtBuscarCodFuncionario;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtListarTestesCovid;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodTeste;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdNomeFuncionario;
    private javax.swing.JTextField EdSetorFuncio;
    private javax.swing.JTextField EdTurnoFuncio;
    private javax.swing.JCheckBox JcNegativo;
    private javax.swing.JCheckBox JcPositivo;
    private javax.swing.JComboBox<String> JcResultado;
    private com.toedter.calendar.JDateChooser JdcDataTeste;
    private javax.swing.JTable JtTesteSemResultado;
    private javax.swing.JTable JtTestesCovid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
