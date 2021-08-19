package hard.home.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.rh.view.FrContParticipacao;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrcadUsuario extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSÃO
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    /**
     * Creates new form FrcadUsuario
     */
    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrcadUsuario(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Manutenção de Usuário - V" + versao;

        habilitarB(1);
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();
            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrcadUsuario";
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
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    //HABILITA OU DESABILITA ITENS DO FRONT
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
                BtBuscar.setEnabled(true);
                EdNome.setEnabled(false);
                EdCod.setEnabled(true);
                EdSetor.setEnabled(false);
                EdLogin.setEnabled(false);
                EdSenha.setEnabled(false);
                EdCargo.setEnabled(false);
                EdMail.setEnabled(false);
                EdNome.requestFocus();
                break;

            case 2:
                maiorCod();
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdNome.setEnabled(true);
                EdCod.setEnabled(true);
                EdSetor.setEnabled(true);
                EdLogin.setEnabled(true);
                EdSenha.setEnabled(true);
                EdCargo.setEnabled(true);
                EdMail.setEnabled(true);
                EdNome.requestFocus();
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdNome.setEnabled(true);
                EdCod.setEnabled(false);
                EdSetor.setEnabled(true);
                EdLogin.setEnabled(false);
                EdSenha.setEnabled(true);
                EdCargo.setEnabled(true);
                EdMail.setEnabled(true);
                EdNome.requestFocus();
                break;
        }
    }

    //LIMPA CAMPOS DO FRONT
    public void limpaCampos() {
        EdCod.setText(null);
        EdNome.setText(null);
        EdSetor.setText(null);
        EdCargo.setText(null);
        EdLogin.setText(null);
        EdSenha.setText(null);
        EdCargo.setText(null);
        EdMail.setText(null);
        CbVDash.setSelected(false);
        CbEmailIntegra.setSelected(false);
        CbEmailPar.setSelected(false);
        CbEmailContra.setSelected(false);
        CbEmailNfeFiscal.setSelected(false);
        CbEmailOcorreProd.setSelected(false);
        CbEmailNfeCompras.setSelected(false);
    }

    public void maiorCod() {

        UsuarioDao cdao = new UsuarioDao();

        try {
            for (Usuario p : cdao.readMaiorCod()) {

                int cod;
                cod = p.getCodUsuario();
                EdCod.setText(Integer.toString(cod));

            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("valida código");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    //VERIFICAR SE O USUÁRIO JÁ POSSUI CADASTRO
    public void checkCreate() throws SQLException {
        UsuarioDao pdao = new UsuarioDao();
        if (EdNome.getText().equals("") || EdSetor.getText().equals("") || EdLogin.getText().equals("") || EdSenha.getText().equals("")
                || EdCargo.getText().equals("") || EdNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else {
            try {
                if (pdao.checkCreate(EdLogin.getText())) {

                    JOptionPane.showMessageDialog(null, "Usuário já possui cadastro");
                } else {
                    salvarUsuario();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("valida código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    //CACADASTRA USUÁRIO
    public void salvarUsuario() {

        Usuario u = new Usuario();
        UsuarioDao dao = new UsuarioDao();
        u.setCodUsuario(Integer.parseInt(EdCod.getText()));
        u.setNomeUsuario(EdNome.getText());
        u.setSetorUsuario(EdSetor.getText());
        u.setCargoUsuario(EdCargo.getText());
        u.setLoginUsuario(EdLogin.getText());
        u.setSenhaUsuario(EdSenha.getText());
        u.setEmailUsuario(EdMail.getText());

        try {
            dao.createUser(u);
            dao.importeTelas(u);

            try {

                int veDashboardDP = 0;
                int rEmailIntegra = 0;
                int rEmailParcela = 0;
                int rEmailContrato = 0;
                int rEmailNfeCompras = 0;
                int rEmailNfeFiscal = 0;
                int rEmailOcorreProd = 0;
                if (CbVDash.isSelected()) {
                    veDashboardDP = 1;
                }
                if (CbEmailIntegra.isSelected()) {
                    rEmailIntegra = 1;
                }
                if (CbEmailPar.isSelected()) {
                    rEmailParcela = 1;
                }
                if (CbEmailContra.isSelected()) {
                    rEmailContrato = 1;
                }

                if (CbEmailNfeCompras.isSelected()) {
                    rEmailNfeCompras = 1;
                }

                if (CbEmailNfeFiscal.isSelected()) {
                    rEmailNfeFiscal = 1;
                }
                if (CbEmailOcorreProd.isSelected()) {
                    rEmailOcorreProd = 1;
                }
                u.setFr_codConfig(Integer.parseInt(EdCod.getText()));
                u.setCodUsuario(Integer.parseInt(EdCod.getText()));
                u.setFr_vDash(veDashboardDP);
                u.setFr_rEmailIntegra(rEmailIntegra);
                u.setFr_rEmailPar(rEmailParcela);
                u.setFr_rEmailContr(rEmailContrato);
                u.setFr_rEmailNfeCompras(rEmailNfeCompras);
                u.setFr_rEmailNfeFiscal(rEmailNfeFiscal);
                u.setFr_rEmailOcoProd(rEmailOcorreProd);
                dao.saveConfig(u);

            } catch (ClassNotFoundException | NumberFormatException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("salvar configurações do usuário");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

            habilitarB(1);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar usuário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

        limpaCampos();

    }

    //LISTA USUÁRIO
    public void listarUsuarios() {

        habilitarB(1);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        UsuarioDao pdao = new UsuarioDao();

        if (EdBuscarNome.getText().equals("")) {
            try {
                for (Usuario u : pdao.readUser()) {
                    modelo.addRow(new Object[]{
                        u.getCodUsuario(),
                        u.getNomeUsuario(),
                        u.getSetorUsuario(),
                        u.getCargoUsuario(),
                        u.getLoginUsuario(),
                        u.getEmailUsuario()

                    });
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("lisar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            try {
                for (Usuario u : pdao.readUserForDesc(EdBuscarNome.getText())) {
                    modelo.addRow(new Object[]{
                        u.getCodUsuario(),
                        u.getNomeUsuario(),
                        u.getSetorUsuario(),
                        u.getCargoUsuario(),
                        u.getLoginUsuario(),
                        u.getEmailUsuario()
                    });
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("lisar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    //VALIDA O CÓDIGO DO USUÁRIO
    public void validarCodList() {
        UsuarioDao pdao = new UsuarioDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário!");
        } else {

            try {
                if (pdao.checkCod(EdCod.getText())) {
                    listaUsuarioCod();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado");
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    //LISTA USUÁRIO POR CÓDIGO
    public void listaUsuarioCod() {

        UsuarioDao pdao = new UsuarioDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário!");
        } else {

            try {
                for (Usuario usu : pdao.readUserForCod(Integer.parseInt(EdCod.getText()))) {
                    EdCod.setText(Integer.toString(usu.getCodUsuario()));
                    EdNome.setText(usu.getNomeUsuario());
                    EdSetor.setText(usu.getSetorUsuario());
                    EdCargo.setText(usu.getCargoUsuario());
                    EdLogin.setText(usu.getLoginUsuario());
                    EdSenha.setText(usu.getSenhaUsuario());
                    EdMail.setText(usu.getEmailUsuario());

                    UsuarioDao dao = new UsuarioDao();

                    int veDashboardDP = 0;
                    int rEmailIntegra = 0;
                    int rEmailParcela = 0;
                    int rEmailContrato = 0;
                    int rEmailNfeCompras = 0;
                    int rEmailNfeFiscal = 0;
                    int rEmailOcoProd = 0;

                    for (Usuario u : dao.readConfig(Integer.parseInt(EdCod.getText()))) {
                        veDashboardDP = u.getFr_vDash();
                        rEmailIntegra = u.getFr_rEmailIntegra();
                        rEmailParcela = u.getFr_rEmailPar();
                        rEmailContrato = u.getFr_rEmailContr();
                        rEmailNfeCompras = u.getFr_rEmailNfeCompras();
                        rEmailNfeFiscal = u.getFr_rEmailNfeFiscal();
                        rEmailOcoProd = u.getFr_rEmailOcoProd();
                    }

                    if (veDashboardDP == 1) {
                        CbVDash.setSelected(true);
                    } else {
                        CbVDash.setSelected(false);
                    }
                    if (rEmailIntegra == 1) {
                        CbEmailIntegra.setSelected(true);
                    } else {
                        CbEmailIntegra.setSelected(false);
                    }
                    if (rEmailParcela == 1) {
                        CbEmailPar.setSelected(true);
                    } else {
                        CbEmailPar.setSelected(false);
                    }
                    if (rEmailContrato == 1) {
                        CbEmailContra.setSelected(true);
                    } else {
                        CbEmailContra.setSelected(false);
                    }

                    if (rEmailNfeCompras == 1) {
                        CbEmailNfeCompras.setSelected(true);
                    } else {
                        CbEmailNfeCompras.setSelected(false);
                    }
                    if (rEmailNfeFiscal == 1) {
                        CbEmailNfeFiscal.setSelected(true);
                    } else {
                        CbEmailNfeFiscal.setSelected(false);
                    }
                    if (rEmailOcoProd == 1) {
                        CbEmailOcorreProd.setSelected(true);
                    } else {
                        CbEmailOcorreProd.setSelected(false);
                    }

                    habilitarB(3);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("lisar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    //EDITA USUÁRIO
    public void editarUsuario() {

        Usuario u = new Usuario();
        UsuarioDao dao = new UsuarioDao();

        if (EdNome.getText().equals("") || EdSetor.getText().equals("")
                || EdLogin.getText().equals("") || EdCargo.getText().equals("") || EdNome.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "ATENÇÃO\n Você deve preencher todos os campos");

        } else {

            u.setNomeUsuario(EdNome.getText());
            u.setCargoUsuario(EdCargo.getText());
            u.setSetorUsuario(EdSetor.getText());
            u.setEmailUsuario(EdMail.getText());
            u.setCodUsuario(Integer.parseInt(EdCod.getText()));

            try {
                dao.update(u);
                try {

                    int veDashboardDP = 0;
                    int rEmailIntegra = 0;
                    int rEmailParcela = 0;
                    int rEmailContrato = 0;
                    int rEmailNfeCompras = 0;
                    int rEmailNfeFiscal = 0;
                    int rEmailOcoProd = 0;
                    if (CbVDash.isSelected()) {
                        veDashboardDP = 1;
                    }
                    if (CbEmailIntegra.isSelected()) {
                        rEmailIntegra = 1;
                    }
                    if (CbEmailPar.isSelected()) {
                        rEmailParcela = 1;
                    }
                    if (CbEmailContra.isSelected()) {
                        rEmailContrato = 1;
                    }
                    if (CbEmailNfeCompras.isSelected()) {
                        rEmailNfeCompras = 1;
                    }
                    if (CbEmailNfeFiscal.isSelected()) {
                        rEmailNfeFiscal = 1;
                    }
                    if (CbEmailOcorreProd.isSelected()) {
                        rEmailOcoProd = 1;
                    }
                    u.setFr_codConfig(Integer.parseInt(EdCod.getText()));
                    u.setCodUsuario(Integer.parseInt(EdCod.getText()));
                    u.setFr_vDash(veDashboardDP);
                    u.setFr_rEmailIntegra(rEmailIntegra);
                    u.setFr_rEmailPar(rEmailParcela);
                    u.setFr_rEmailContr(rEmailContrato);
                    u.setFr_rEmailNfeCompras(rEmailNfeCompras);
                    u.setFr_rEmailNfeFiscal(rEmailNfeFiscal);
                    u.setFr_rEmailOcoProd(rEmailOcoProd);
                    dao.updateConfig(u);

                } catch (ClassNotFoundException | NumberFormatException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("salvar configurações do usuário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

                limpaCampos();
                listarUsuarios();
                habilitarB(1);
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("lisar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }

    }

    public void deletaConfig() {

    }

//DELETA USUÁRIO                }
    public void deletarUsuario() {
        Usuario u = new Usuario();
        UsuarioDao dao = new UsuarioDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário para excluír");
        } else {

            u.setCodUsuario(Integer.parseInt(EdCod.getText()));
            try {

                String nome = EdNome.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o cadastro do usuário  " + nome, null, JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    dao.deleteConfig(u);
                    dao.deletePermissao(u);
                    dao.delete(u);

                    habilitarB(1);
                    limpaCampos();
                    listarUsuarios();
                } else {

                    JOptionPane.showMessageDialog(null, "Exclusão cancelada");
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
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
        EdCod = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        EdNome = new javax.swing.JTextField();
        EdSetor = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        EdSenha = new javax.swing.JPasswordField();
        EdLogin = new javax.swing.JTextField();
        EdMail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        CbEmailPar = new javax.swing.JCheckBox();
        CbEmailIntegra = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        CbEmailContra = new javax.swing.JCheckBox();
        CbVDash = new javax.swing.JCheckBox();
        CbEmailNfeCompras = new javax.swing.JCheckBox();
        CbEmailNfeFiscal = new javax.swing.JCheckBox();
        CbEmailOcorreProd = new javax.swing.JCheckBox();
        EdCargo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        EdBuscarNome = new javax.swing.JTextField();
        BtBuscarNome = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 78, -1));

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        EdNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 260, -1));

        EdSetor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdSetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 260, -1));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Senha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(EdSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, -1));

        EdLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Login:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(EdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 270, -1));

        EdMail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "E-mail:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(EdMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 270, -1));

        jTabbedPane2.addTab("Usuário", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CbEmailPar.setText("E-mail vencimento Parcela Copart.");
        jPanel5.add(CbEmailPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        CbEmailIntegra.setText("E-mail vencimento Integração");
        jPanel5.add(CbEmailIntegra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel1.setText("Configurações Recebimento de Alertas E-mail:");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        CbEmailContra.setText("E-mail vencimento Contrato.");
        CbEmailContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbEmailContraActionPerformed(evt);
            }
        });
        jPanel5.add(CbEmailContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        CbVDash.setText("Visualizar Dashboard tela DPessoal");
        jPanel5.add(CbVDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        CbEmailNfeCompras.setText("Recebe e-mail alerta nf-e compras");
        jPanel5.add(CbEmailNfeCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        CbEmailNfeFiscal.setText("Recebe e-mail alerta nf-e fiscal");
        jPanel5.add(CbEmailNfeFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        CbEmailOcorreProd.setText("Recebe e-mail Ocorrências Produção");
        jPanel5.add(CbEmailOcorreProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jTabbedPane2.addTab("Outras Configurações", jPanel5);

        jPanel1.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 280, 230));

        EdCargo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 260, -1));

        jTabbedPane1.addTab("Cadastrar Usuário", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdBuscarNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscarNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarNomeKeyPressed(evt);
            }
        });
        jPanel2.add(EdBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 241, -1));

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });
        jPanel2.add(BtBuscarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Setor", "Cargo", "Login", "mail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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
            tabela.getColumnModel().getColumn(0).setMinWidth(50);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
            tabela.getColumnModel().getColumn(5).setMinWidth(0);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 550, 164));

        jLabel9.setText("Dois cliques para editar*");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 225, -1, -1));

        jTabbedPane1.addTab("Listar Usuários", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, -1));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, 33));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel4.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 120, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 78, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 33));

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvarEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 33));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 550, 52));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarUsuario();
        deletaConfig();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarUsuario();

    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        listarUsuarios();
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        try {
            //INSERE OS DADOS DO USUÁRIO QUE ESTÁ NA TABELA PARA OS CAMPOS
            if (tabela.getSelectedRow() != -1) {
                jTabbedPane1.setSelectedIndex(0);
                EdCod.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                EdNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
                EdSetor.setText(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
                EdCargo.setText(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
                EdLogin.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());
                EdMail.setText(tabela.getValueAt(tabela.getSelectedRow(), 5).toString());

                UsuarioDao dao = new UsuarioDao();

                int veDashboardDP = 0;
                int rEmailIntegra = 0;
                int rEmailParcela = 0;
                int rEmailContrato = 0;
                int rEmailNfeCompras = 0;
                int rEmailNfeFiscal = 0;
                int rEmailOcoProd = 0;

                for (Usuario u : dao.readConfig(Integer.parseInt(EdCod.getText()))) {
                    veDashboardDP = u.getFr_vDash();
                    rEmailIntegra = u.getFr_rEmailIntegra();
                    rEmailParcela = u.getFr_rEmailPar();
                    rEmailContrato = u.getFr_rEmailContr();
                    rEmailNfeCompras = u.getFr_rEmailNfeCompras();
                    rEmailNfeFiscal = u.getFr_rEmailNfeFiscal();
                    rEmailOcoProd = u.getFr_rEmailOcoProd();
                }

                if (veDashboardDP == 1) {
                    CbVDash.setSelected(true);
                } else {
                    CbVDash.setSelected(false);
                }
                if (rEmailIntegra == 1) {
                    CbEmailIntegra.setSelected(true);
                } else {
                    CbEmailIntegra.setSelected(false);
                }
                if (rEmailParcela == 1) {
                    CbEmailPar.setSelected(true);
                } else {
                    CbEmailPar.setSelected(false);
                }
                if (rEmailContrato == 1) {
                    CbEmailContra.setSelected(true);
                } else {
                    CbEmailContra.setSelected(false);
                }
                if (rEmailNfeCompras == 1) {
                    CbEmailNfeCompras.setSelected(true);
                } else {
                    CbEmailNfeCompras.setSelected(false);
                }
                if (rEmailNfeFiscal == 1) {
                    CbEmailNfeFiscal.setSelected(true);
                } else {
                    CbEmailNfeFiscal.setSelected(false);
                }
                if (rEmailOcoProd == 1) {
                    CbEmailOcorreProd.setSelected(true);
                } else {
                    CbEmailOcorreProd.setSelected(false);
                }
            }

            habilitarB(3);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar usuário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {
            checkCreate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCod.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        validarCodList();

    }//GEN-LAST:event_BtBuscarActionPerformed

    private void EdBuscarNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarNomeKeyPressed
        listarUsuarios();
    }//GEN-LAST:event_EdBuscarNomeKeyPressed

    private void CbEmailContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbEmailContraActionPerformed

    }//GEN-LAST:event_CbEmailContraActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtBuscarNome;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JCheckBox CbEmailContra;
    private javax.swing.JCheckBox CbEmailIntegra;
    private javax.swing.JCheckBox CbEmailNfeCompras;
    private javax.swing.JCheckBox CbEmailNfeFiscal;
    private javax.swing.JCheckBox CbEmailOcorreProd;
    private javax.swing.JCheckBox CbEmailPar;
    private javax.swing.JCheckBox CbVDash;
    private javax.swing.JTextField EdBuscarNome;
    private javax.swing.JTextField EdCargo;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdLogin;
    private javax.swing.JTextField EdMail;
    private javax.swing.JTextField EdNome;
    private javax.swing.JPasswordField EdSenha;
    private javax.swing.JTextField EdSetor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
