package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FrcadUsuario;
import hard.home.view.FDBuscaFuncio;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FuncionarioDao;
import hard.rh.dao.CotistaDao;
import hard.rh.model.Funcionario;
import hard.rh.model.Cotista;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrContCotista extends javax.swing.JInternalFrame {
    // CONTROLE DE VERSÃO DO SISTEMA

    String versao = "1.0-21.0625.0";

    private FDBuscaFuncio FDBuscaFuncio;
    FDErroOcorrido fdErroOcorrido;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    public FrContCotista(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        title = "Controle de Cota e Estágio - V" + versao;

        habilitarB(1);
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrContCotista";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("gerar log de acesso");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 4, (d.height - this.getSize().height) / 10);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtEditar.setVisible(false);
                BtEditar.setEnabled(false);

                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscarCodFuncionario.setEnabled(false);

                EdCodFuncio.setEnabled(false);
                EdCodFuncio.setEditable(true);
                EdNomeFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdCpfFuncio.setEnabled(false);
                JtObserva.setEnabled(false);

                EdCodFuncio.requestFocus();
                break;

            case 2:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtEditar.setVisible(false);
                BtEditar.setEnabled(false);

                BtExcluir.setEnabled(false);

                BtBuscarCodFuncionario.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdCodFuncio.setEditable(true);
                EdCodFuncio.setEnabled(true);
                JtObserva.setEnabled(true);
                EdNomeFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);

                EdCodFuncio.requestFocus();
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtEditar.setVisible(true);
                BtEditar.setEnabled(true);

                BtExcluir.setEnabled(true);
                BtCancelar.setEnabled(true);
                BtBuscarCodFuncionario.setEnabled(false);

                EdCodFuncio.setEditable(false);
                EdNomeFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);
                JtObserva.setEnabled(true);
                EdNomeFuncio.setEditable(false);
                EdCargoFuncio.setEditable(false);
                EdCpfFuncio.setEditable(false);

                EdNomeFuncio.requestFocus();
                break;
        }
    }

    public void limpaCampos() {
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        EdCargoFuncio.setText(null);
        EdCpfFuncio.setText(null);
        EdCargoFuncio.setText(null);
        JtObserva.setText(null);
        EdCodCota.setText(null);
        JdcAdmCota.setDate(null);
        JdcVencCota.setDate(null);

    }

    public void buscarCodCota() {

        CotistaDao pdao = new CotistaDao();

        try {
            for (Cotista p : pdao.readMaiorCodCota()) {

                int codCota;
                codCota = p.getCodCota();
                EdCodCota.setText(Integer.toString(codCota));
                habilitarB(2);

            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void validaCadastroCotista() {
        if (EdCargoFuncio.getText().equals("") || EdCpfFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            if (JdcAdmCota.getDate() == null || JdcVencCota.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Você deve preencher as datas!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                salvarCotista();
            }
        }

    }

    public void salvarCotista() {

        Cotista c = new Cotista();
        CotistaDao dao = new CotistaDao();
        c.setCodCota(Integer.parseInt(EdCodCota.getText()));

        c.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));

        c.setObserva(JtObserva.getText());

        java.util.Date UltiCotista = JdcAdmCota.getDate();
        long dtCotista = UltiCotista.getTime();
        java.sql.Date dateUltiCotista = new java.sql.Date(dtCotista);
        c.setDataAdmi(dateUltiCotista);

        java.util.Date VencCotista = JdcVencCota.getDate();
        long dtVenciCotista = VencCotista.getTime();
        java.sql.Date dateVencCotista = new java.sql.Date(dtVenciCotista);
        c.setDataVenci(dateVencCotista);

        try {
            dao.createCota(c);
            habilitarB(1);
            limpaCampos();
            listarCotista();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar registro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listarCotista() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        CotistaDao pdao = new CotistaDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        if (CbxVencidas.isSelected()) {

            for (Cotista c : pdao.readCotistaVencidas()) {
                modelo.addRow(new Object[]{
                    c.getCodCota(),
                    c.getFr_codFuncionario(),
                    c.getFr_nomeFuncionario(),
                    c.getFr_cpfFuncionario(),
                    c.getFr_cargoFuncionario(),
                    formatdata.format(c.getDataAdmi()),
                    formatdata.format(c.getDataVenci()),
                    (c.getDiasVencidos() * -1)});
            }

        } else if (CbxFiltroDatas.isSelected()) {
            if (JdcFiltraCotaInicial.getDate() == null || JdcFiltraCotaFinal.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Você deve informar a data inicial e final!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                java.util.Date dataIni = JdcFiltraCotaInicial.getDate();
                long dtInicial = dataIni.getTime();
                java.sql.Date dateCotistaInicial = new java.sql.Date(dtInicial);

                java.util.Date dataFin = JdcFiltraCotaFinal.getDate();
                long dtFinal = dataFin.getTime();
                java.sql.Date dateCotistaFinal = new java.sql.Date(dtFinal);

                for (Cotista c : pdao.readCotaForData(dateCotistaInicial, dateCotistaFinal)) {
                    modelo.addRow(new Object[]{
                        c.getCodCota(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getFr_cpfFuncionario(),
                        c.getFr_cargoFuncionario(),
                        formatdata.format(c.getDataAdmi()),
                        formatdata.format(c.getDataVenci()),
                        (c.getDiasVencidos() * -1)});
                }
            }

        } else {

            if (!EdBuscarNome.getText().equals("")) {
                for (Cotista c : pdao.readCotistaForFuncionario(EdBuscarNome.getText())) {
                    modelo.addRow(new Object[]{
                        c.getCodCota(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getFr_cpfFuncionario(),
                        c.getFr_cargoFuncionario(),
                        formatdata.format(c.getDataAdmi()),
                        formatdata.format(c.getDataVenci()),
                        (c.getDiasVencidos() * -1),
                        c.getObserva()});

                }

            } else {
                for (Cotista c : pdao.readCotista()) {
                    modelo.addRow(new Object[]{
                        c.getCodCota(),
                        c.getFr_codFuncionario(),
                        c.getFr_nomeFuncionario(),
                        c.getFr_cpfFuncionario(),
                        c.getFr_cargoFuncionario(),
                        formatdata.format(c.getDataAdmi()),
                        formatdata.format(c.getDataVenci()),
                        (c.getDiasVencidos() * -1)});
                }
            }
        }
    }

    public void editarCotista() throws ClassNotFoundException {

        if (tabela.getSelectedRow() != -1) {
            Cotista c = new Cotista();
            CotistaDao dao = new CotistaDao();

            if (EdNomeFuncio.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Você deve preencher todos os campos", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                if (JdcAdmCota.getDate() == null || JdcVencCota.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Você deve preencher a datas", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                } else {

                    c.setObserva(JtObserva.getText());

                    java.util.Date UltiCotista = JdcAdmCota.getDate();
                    long dtCotista = UltiCotista.getTime();
                    java.sql.Date dateUltiCotista = new java.sql.Date(dtCotista);
                    c.setDataAdmi(dateUltiCotista);

                    java.util.Date VencCotista = JdcVencCota.getDate();
                    long dtVenciCotista = VencCotista.getTime();
                    java.sql.Date dateVencCotista = new java.sql.Date(dtVenciCotista);
                    c.setDataVenci(dateVencCotista);
                    c.setCodCota((int) tabela.getValueAt(tabela.getSelectedRow(), 0));
                    try {
                        dao.updateCotista(c);
                        limpaCampos();
                        listarCotista();
                        habilitarB(1);

                    } catch (ClassNotFoundException ex) {

                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("editar cadastro");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }

            }

        }
    }

    public void deletarCotista() throws ClassNotFoundException {
        Cotista c = new Cotista();
        CotistaDao dao = new CotistaDao();

        if (EdCodCota.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do cota para excluír", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            c.setCodCota(Integer.parseInt(EdCodCota.getText()));
            try {
                String nome = EdNomeFuncio.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        "Deseja mesmo excluír o cadastro da cota do funcionário  " + nome, "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);

                if (input == 0) {
                    dao.deleteCotista(c);
                    habilitarB(1);
                    limpaCampos();
                    listarCotista();
                    listarCotista();

                } else {
                    JOptionPane.showMessageDialog(null, "Exclusão cancelada", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                }

            } catch (ClassNotFoundException ex) {

                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void validarCodFuncio() {
        FuncionarioDao pdao = new FuncionarioDao();
        CotistaDao dao = new CotistaDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    if (dao.checkCadCotista(EdCodFuncio.getText())) {
                        JOptionPane.showMessageDialog(null, "Funcionário já possui cadastro", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                    } else {
                        listarFuncioCod();
                        habilitarB(2);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado", "ERRO", JOptionPane.ERROR_MESSAGE);

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

        FuncionarioDao pdao = new FuncionarioDao();

        for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNomeFuncio.setText(fu.getNomeFuncionario());
            EdCargoFuncio.setText(fu.getCargoFuncionario());
            EdCpfFuncio.setText(fu.getCpfFuncionario());

        }
    }

    public void listarObsAndDate() throws ClassNotFoundException, SQLException {
        CotistaDao cdao = new CotistaDao();

        for (Cotista c : cdao.readObservaAndDateForCod(EdCodCota.getText())) {
            JtObserva.setText(c.getObserva());
            JdcAdmCota.setDate(c.getDataAdmi());
            JdcVencCota.setDate(c.getDataVenci());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        BtNovo = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtExcluir = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtEditar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        EdCargoFuncio = new javax.swing.JTextField();
        EdNomeFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncionario = new javax.swing.JButton();
        EdCodFuncio = new javax.swing.JTextField();
        EdCpfFuncio = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        JdcAdmCota = new com.toedter.calendar.JDateChooser();
        JdcVencCota = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtObserva = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        EdCodCota = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        BtBuscarNome = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        EdBuscarNome = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        JdcFiltraCotaInicial = new com.toedter.calendar.JDateChooser();
        JdcFiltraCotaFinal = new com.toedter.calendar.JDateChooser();
        CbxVencidas = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        CbxFiltroDatas = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Controle de Cota e Estágio - V1.0-20.1006");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel4.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 120, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 78, 33));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 100, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 100, 33));

        BtEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtEditar.setText("SALVAR");
        BtEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEditarActionPerformed(evt);
            }
        });
        jPanel4.add(BtEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 100, 33));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 640, 55));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCargoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdCargoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 270, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, -1));

        BtBuscarCodFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncionario.setText("Buscar");
        BtBuscarCodFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncionarioActionPerformed(evt);
            }
        });
        jPanel6.add(BtBuscarCodFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel6.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        EdCpfFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        try {
            EdCpfFuncio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel6.add(EdCpfFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 140, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 290, 180));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdcAdmCota.setBackground(new java.awt.Color(255, 255, 255));
        JdcAdmCota.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Admissão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcAdmCota, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        JdcVencCota.setBackground(new java.awt.Color(255, 255, 255));
        JdcVencCota.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcVencCota, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 140, -1));

        JtObserva.setColumns(20);
        JtObserva.setRows(5);
        JtObserva.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observações:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jScrollPane2.setViewportView(JtObserva);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 300, 100));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 320, 180));

        jLabel18.setText("Dados do funcionário");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        EdCodCota.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel1.add(EdCodCota, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 80, -1));

        jLabel24.setText("Datas");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, 20));

        jTabbedPane1.addTab("Constrole do Cadastro", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });
        jPanel2.add(BtBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód.", "Cód. Funcionário", "Nome Funcionário", "CPF", "Cargo", "Data Admissão", "Dias Vencimento", "Vencimento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 640, 180));

        jLabel9.setText("Duplo clique para editar*");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, -1, -1));

        EdBuscarNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscarNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarNomeKeyPressed(evt);
            }
        });
        jPanel2.add(EdBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdcFiltraCotaInicial.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraCotaInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.add(JdcFiltraCotaInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        JdcFiltraCotaFinal.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraCotaFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.add(JdcFiltraCotaFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 140, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 310, 80));

        CbxVencidas.setBackground(new java.awt.Color(255, 255, 255));
        CbxVencidas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        CbxVencidas.setText("VENCIDAS");
        CbxVencidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CbxVencidasMouseClicked(evt);
            }
        });
        CbxVencidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxVencidasActionPerformed(evt);
            }
        });
        jPanel2.add(CbxVencidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        jLabel35.setText("Filtrar por: ");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        CbxFiltroDatas.setBackground(new java.awt.Color(255, 255, 255));
        CbxFiltroDatas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        CbxFiltroDatas.setText("DATA VENCIMENTO");
        CbxFiltroDatas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CbxFiltroDatasMouseClicked(evt);
            }
        });
        CbxFiltroDatas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxFiltroDatasActionPerformed(evt);
            }
        });
        jPanel2.add(CbxFiltroDatas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, -1));

        jLabel2.setText("*Duplo clique para editar");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, -1, -1));

        jTabbedPane1.addTab("Listar Cotas e Estágios", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        buscarCodCota();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        validaCadastroCotista();
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            deletarCotista();
        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        try {
            listarCotista();
        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (tabela.getSelectedRow() != -1) {
            jTabbedPane1.setSelectedIndex(0);
            EdCodCota.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            EdCodFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            EdNomeFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
            EdCpfFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
            EdCargoFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());

            try {
                listarObsAndDate();
                habilitarB(3);
            } catch (ClassNotFoundException | SQLException ex) {

                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }//GEN-LAST:event_tabelaMouseClicked

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

    private void CbxVencidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxVencidasMouseClicked
        CbxFiltroDatas.setSelected(false);
    }//GEN-LAST:event_CbxVencidasMouseClicked

    private void CbxVencidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxVencidasActionPerformed

    }//GEN-LAST:event_CbxVencidasActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodFuncio();
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void CbxFiltroDatasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxFiltroDatasMouseClicked
        CbxVencidas.setSelected(false);
    }//GEN-LAST:event_CbxFiltroDatasMouseClicked

    private void CbxFiltroDatasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxFiltroDatasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbxFiltroDatasActionPerformed

    private void EdBuscarNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarNomeKeyPressed
        try {
            listarCotista();
        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdBuscarNomeKeyPressed

    private void BtEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEditarActionPerformed
        try {
            editarCotista();
        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtEditarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscarCodFuncionario;
    private javax.swing.JButton BtBuscarNome;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtEditar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JCheckBox CbxFiltroDatas;
    private javax.swing.JCheckBox CbxVencidas;
    private javax.swing.JTextField EdBuscarNome;
    private javax.swing.JTextField EdCargoFuncio;
    private javax.swing.JTextField EdCodCota;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JFormattedTextField EdCpfFuncio;
    private javax.swing.JTextField EdNomeFuncio;
    private com.toedter.calendar.JDateChooser JdcAdmCota;
    private com.toedter.calendar.JDateChooser JdcFiltraCotaFinal;
    private com.toedter.calendar.JDateChooser JdcFiltraCotaInicial;
    private com.toedter.calendar.JDateChooser JdcVencCota;
    private javax.swing.JTextArea JtObserva;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
