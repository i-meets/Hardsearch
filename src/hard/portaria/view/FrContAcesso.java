package hard.portaria.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.portaria.dao.ContAcessoDao;
import hard.portaria.model.ContAcesso;
import hard.home.view.FDBuscaEmp;
import hard.home.view.FDErroOcorrido;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrContAcesso extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";

    private FDBuscaEmp FDBuscaEmp;
    FDErroOcorrido fdErroOcorrido;

    /**
     * Creates new form FrContAcesso
     *
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrContAcesso(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle de Acessos - V" + versao;
        habilitarB(1);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrContAcesso";
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
        this.setLocation((d.width - this.getSize().width) / 3, (d.height - this.getSize().height) / 2);
    }

    public void maiorCodAcesso() {

        ContAcessoDao cdao = new ContAcessoDao();

        try {
            for (ContAcesso p : cdao.readMaiorCodAcesso()) {

                int codAcesso;
                codAcesso = p.getCodAcesso();
                EdCodAcesso.setText(Integer.toString(codAcesso));

            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                EdCodAcesso.setEnabled(true);
                EdHoraEntra.setEnabled(false);
                EdHoraSai.setEnabled(false);
                EdNomeEmp.setEnabled(false);
                EdNomePessoa.setEnabled(false);
                EdObserva.setEnabled(false);
                EdPlaca.setEnabled(false);
                EdRgPessoa.setEnabled(false);
                EdVeiculo.setEnabled(false);

                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtEditar.setVisible(false);
                BtEditar.setEnabled(false);
                BtApontaSaida.setEnabled(false);
                BtExcluir.setEnabled(false);

                break;

            case 2:
                maiorCodAcesso();
                EdCodAcesso.setEnabled(false);
                EdHoraEntra.setEnabled(true);
                EdHoraSai.setEnabled(true);
                EdNomeEmp.setEnabled(true);
                EdNomePessoa.setEnabled(true);
                EdObserva.setEnabled(true);
                EdPlaca.setEnabled(true);
                EdRgPessoa.setEnabled(true);
                EdVeiculo.setEnabled(true);

                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtEditar.setVisible(false);
                BtEditar.setEnabled(false);
                BtApontaSaida.setEnabled(false);
                BtExcluir.setEnabled(false);

                break;

            case 3:

                EdCodAcesso.setEnabled(false);
                EdHoraEntra.setEnabled(false);
                EdHoraSai.setEnabled(false);
                EdNomeEmp.setEnabled(false);
                EdNomePessoa.setEnabled(false);
                EdObserva.setEnabled(false);
                EdPlaca.setEnabled(false);
                EdRgPessoa.setEnabled(false);
                EdVeiculo.setEnabled(false);

                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtEditar.setVisible(true);
                BtEditar.setEnabled(false);
                BtApontaSaida.setEnabled(false);
                BtExcluir.setEnabled(false);

                break;

            case 4:

                EdCodAcesso.setEnabled(false);
                EdHoraEntra.setEnabled(false);
                EdHoraSai.setEnabled(true);
                EdNomeEmp.setEnabled(false);
                EdNomePessoa.setEnabled(false);
                EdObserva.setEnabled(false);
                EdPlaca.setEnabled(false);
                EdRgPessoa.setEnabled(false);
                EdVeiculo.setEnabled(false);

                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtEditar.setVisible(true);
                BtEditar.setEnabled(true);
                BtApontaSaida.setEnabled(true);
                BtExcluir.setEnabled(true);

                break;
        }
    }

    public void limpaCampos() {
        EdCodAcesso.setText(null);
        EdNomeEmp.setText(null);
        EdNomePessoa.setText(null);
        EdHoraEntra.setText(null);
        EdObserva.setText(null);
        EdPlaca.setText(null);
        EdRgPessoa.setText(null);
        EdVeiculo.setText(null);
        EdHoraSai.setText(null);
        LbStatus.setText("Status");
        LbStatus.setForeground(Color.decode("#000000"));
    }

    public void checkCreate() throws SQLException {
        if (EdCodAcesso.getText().equals("") || EdNomePessoa.getText().equals("")
                || EdHoraEntra.getText().equals("  :  ") || EdNomeEmp.getText().equals("")
                || EdObserva.getText().equals("") || EdPlaca.getText().equals("")
                || EdRgPessoa.getText().equals("") || EdVeiculo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            createAcesso();
        }

    }

    public void createAcesso() {

        ContAcesso e = new ContAcesso();
        ContAcessoDao dao = new ContAcessoDao();

        e.setCodAcesso(Integer.parseInt(EdCodAcesso.getText()));
        e.setNomeEmp(EdNomeEmp.getText());
        e.setVeiculo(EdVeiculo.getText());
        e.setPlaca(EdPlaca.getText());
        e.setNomePessoa(EdNomePessoa.getText());
        e.setRg(EdRgPessoa.getText());
        e.setObserva(EdObserva.getText());
        e.setHoraEntra(EdHoraEntra.getText());
        e.setHoraSai(EdHoraSai.getText());
        e.setStatusAcesso(1);

        try {
            if (!EdHoraSai.getText().equals("  :  ")) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Deseja mesmo criar acesso com o horário de saída preenchido?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {

                    dao.createAcesso(e);
                    limpaCampos();
                    listarAcesso();

                } else {
                    EdHoraSai.setText("");
                }

            } else {
                dao.createAcesso(e);
                limpaCampos();
                listarAcesso();
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar acesso");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void listarAcesso() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        ContAcessoDao pdao = new ContAcessoDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        if (EdBuscaEmpNome.getText().equals("")) {

            try {
                for (ContAcesso e : pdao.readAcesso()) { //buscar todas saidas
                    modelo.addRow(new Object[]{
                        e.getCodAcesso(),
                        formatdata.format(e.getDataAcesso()),
                        e.getNomePessoa(),
                        e.getRg(),
                        e.getNomeEmp(),
                        e.getVeiculo(),
                        e.getPlaca(),
                        e.getHoraEntra(),
                        e.getHoraSai(),
                        e.getObserva(),
                        e.getStatusAcesso()
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
                for (ContAcesso e : pdao.readEmpForDesc(EdBuscaEmpNome.getText())) {  //buscar saidas por descrição
                    modelo.addRow(new Object[]{
                        e.getCodAcesso(),
                        formatdata.format(e.getDataAcesso()),
                        e.getNomePessoa(),
                        e.getRg(),
                        e.getNomeEmp(),
                        e.getVeiculo(),
                        e.getPlaca(),
                        e.getHoraEntra(),
                        e.getHoraSai(),
                        e.getObserva(),
                        e.getStatusAcesso()
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

    public void listarAcessoTelaListar() {

        DefaultTableModel modelo = (DefaultTableModel) TbListagem.getModel();
        modelo.setNumRows(0);
        ContAcessoDao pdao = new ContAcessoDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        if (JcSaidasConfirmaListagem.isSelected()) {

            int mes = 0;

            if (JbSelectMes.getSelectedItem().equals("Janeiro")) {
                mes = 1;
            } else if (JbSelectMes.getSelectedItem().equals("Fevereiro")) {
                mes = 2;
            } else if (JbSelectMes.getSelectedItem().equals("Março")) {
                mes = 3;
            } else if (JbSelectMes.getSelectedItem().equals("Abril")) {
                mes = 4;
            } else if (JbSelectMes.getSelectedItem().equals("Maio")) {
                mes = 5;
            } else if (JbSelectMes.getSelectedItem().equals("Junho")) {
                mes = 6;
            } else if (JbSelectMes.getSelectedItem().equals("Julho")) {
                mes = 7;
            } else if (JbSelectMes.getSelectedItem().equals("Agosto")) {
                mes = 8;
            } else if (JbSelectMes.getSelectedItem().equals("Setembro")) {
                mes = 9;
            } else if (JbSelectMes.getSelectedItem().equals("Outubro")) {
                mes = 10;
            } else if (JbSelectMes.getSelectedItem().equals("Novembro")) {
                mes = 11;
            } else if (JbSelectMes.getSelectedItem().equals("Dezembro")) {
                mes = 12;
            } else {
                System.out.println("erro");
            }

            if (mes == 0) {
            } else {

                if (EdBuscaEmpNome.getText().equals("")) {

                    try {
                        for (ContAcesso e : pdao.readSaidaConfirmForMes(mes)) { //buscar todas saidas confirmadas
                            modelo.addRow(new Object[]{
                                e.getCodAcesso(),
                                formatdata.format(e.getDataAcesso()),
                                e.getNomePessoa(),
                                e.getRg(),
                                e.getNomeEmp(),
                                e.getVeiculo(),
                                e.getPlaca(),
                                e.getHoraEntra(),
                                e.getHoraSai(),
                                e.getObserva(),
                                e.getStatusAcesso()
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
                        for (ContAcesso e : pdao.readSaidaConfirmForDescForMes(mes, EdBuscaEmpNome.getText())) {  //buscar saidas confirmadas por descrição
                            modelo.addRow(new Object[]{
                                e.getCodAcesso(),
                                formatdata.format(e.getDataAcesso()),
                                e.getNomePessoa(),
                                e.getRg(),
                                e.getNomeEmp(),
                                e.getVeiculo(),
                                e.getPlaca(),
                                e.getHoraEntra(),
                                e.getHoraSai(),
                                e.getObserva(),
                                e.getStatusAcesso()
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
        } else {

            if (EdBuscaEmp.getText().equals("")) {

                try {
                    for (ContAcesso e : pdao.readAcesso()) { //buscar todas saidas
                        modelo.addRow(new Object[]{
                            e.getCodAcesso(),
                            formatdata.format(e.getDataAcesso()),
                            e.getNomePessoa(),
                            e.getRg(),
                            e.getNomeEmp(),
                            e.getVeiculo(),
                            e.getPlaca(),
                            e.getHoraEntra(),
                            e.getHoraSai(),
                            e.getObserva(),
                            e.getStatusAcesso()
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
                    for (ContAcesso e : pdao.readEmpForDesc(EdBuscaEmp.getText())) {  //buscar saidas por descrição
                        modelo.addRow(new Object[]{
                            e.getCodAcesso(),
                            formatdata.format(e.getDataAcesso()),
                            e.getNomePessoa(),
                            e.getRg(),
                            e.getNomeEmp(),
                            e.getVeiculo(),
                            e.getPlaca(),
                            e.getHoraEntra(),
                            e.getHoraSai(),
                            e.getObserva(),
                            e.getStatusAcesso()
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

    }

    public void aportarSaidaAcesso() throws ClassNotFoundException {

        ContAcesso e = new ContAcesso();
        ContAcessoDao dao = new ContAcessoDao();

        if (EdCodAcesso.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você selecionar um acesso na tabela", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else if (EdHoraSai.getText().equals("  :  ")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar a hora de saída", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            String nome = EdNomePessoa.getText();
            String nomeEmp = EdNomeEmp.getText();
            int input = JOptionPane.showConfirmDialog(null,
                    " ATENÇÃO!" + "\nDeseja mesmo apontar a SAÍDA do  " + nome + " da empresa " + nomeEmp + "?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == 0) {

                e.setCodAcesso(Integer.parseInt(EdCodAcesso.getText()));
                e.setHoraSai(EdHoraSai.getText());
                e.setStatusAcesso(2);

                dao.apontaSaida(e);
                limpaCampos();
                listarAcesso();
            }
        }

    }

    public void editarAcesso() throws ClassNotFoundException {

        ContAcesso e = new ContAcesso();
        ContAcessoDao dao = new ContAcessoDao();

        if (EdCodAcesso.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você selecionar um acesso na tabela", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

        } else if (EdNomePessoa.getText().equals("")
                || EdHoraEntra.getText().equals("") || EdNomeEmp.getText().equals("")
                || EdObserva.getText().equals("") || EdPlaca.getText().equals("")
                || EdRgPessoa.getText().equals("") || EdVeiculo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);

        } else {
            String nome = EdNomePessoa.getText();
            String nomeEmp = EdNomeEmp.getText();
            int input = JOptionPane.showConfirmDialog(null,
                    " ATENÇÃO!" + "\nDeseja mesmo alterar o cadatro do  " + nome + " da empresa " + nomeEmp + "?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == 0) {

                e.setCodAcesso(Integer.parseInt(EdCodAcesso.getText()));
                e.setNomeEmp(EdNomeEmp.getText());
                e.setVeiculo(EdVeiculo.getText());
                e.setPlaca(EdPlaca.getText());
                e.setNomePessoa(EdNomePessoa.getText());
                e.setRg(EdRgPessoa.getText());
                e.setObserva(EdObserva.getText());
                e.setHoraEntra(EdHoraEntra.getText());
                e.setHoraSai(EdHoraSai.getText());
                e.setStatusAcesso(1);

                dao.apontaSaida(e);
                limpaCampos();
                listarAcesso();
            }
        }

    }

    public void deletarAcesso() {
        ContAcesso e = new ContAcesso();
        ContAcessoDao dao = new ContAcessoDao();
        habilitarB(1);
        if (EdCodAcesso.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código para excluír");
        } else {
            e.setCodAcesso(Integer.parseInt(EdCodAcesso.getText()));
            try {

                String nome = EdNomePessoa.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o acesso do  " + nome, "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
                if (input == 0) {
                    dao.delete(e);
                    limpaCampos();
                    listarAcesso();
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
        jPanel3 = new javax.swing.JPanel();
        BtBuscarNomeEmp = new javax.swing.JButton();
        EdNomeEmp = new javax.swing.JTextField();
        EdVeiculo = new javax.swing.JTextField();
        EdPlaca = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        EdNomePessoa = new javax.swing.JTextField();
        EdRgPessoa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtExcluir = new javax.swing.JButton();
        BtNovo1 = new javax.swing.JButton();
        BtEditar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        EdHoraEntra = new javax.swing.JFormattedTextField();
        EdHoraSai = new javax.swing.JFormattedTextField();
        BtApontaSaida = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        EdObserva = new javax.swing.JTextArea();
        EdCodAcesso = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        LbStatus = new javax.swing.JLabel();
        BtBuscaSaidas = new javax.swing.JButton();
        EdBuscaEmpNome = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TbListagem = new javax.swing.JTable();
        EdBuscaEmp = new javax.swing.JTextField();
        BtBusca = new javax.swing.JButton();
        JcSaidasConfirmaListagem = new javax.swing.JCheckBox();
        JbSelectMes = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 11))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBuscarNomeEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNomeEmp.setText("Buscar");
        BtBuscarNomeEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeEmpActionPerformed(evt);
            }
        });
        jPanel3.add(BtBuscarNomeEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 90, 30));

        EdNomeEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Empresa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel3.add(EdNomeEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, -1));

        EdVeiculo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Veículo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel3.add(EdVeiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 71, 200, -1));

        EdPlaca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Placa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel3.add(EdPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 90, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 310, 130));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 11))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdNomePessoa.setToolTipText(" ");
        EdNomePessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel4.add(EdNomePessoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, -1));

        EdRgPessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "RG/CPF", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel4.add(EdRgPessoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 230, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 250, 130));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Data", "Nome", "RG", "Empresa", "Veículo", "Placa", "Entrada", "Saída", "Observação", "Status"
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setMinWidth(0);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(3).setMaxWidth(0);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(9).setMinWidth(0);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(9).setMaxWidth(0);
            tabela.getColumnModel().getColumn(10).setMinWidth(0);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 570, 150));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel7.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 120, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel7.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 78, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel7.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 33));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel7.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 100, 33));

        BtNovo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo1.setText("NOVO");
        BtNovo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovo1ActionPerformed(evt);
            }
        });
        jPanel7.add(BtNovo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 33));

        BtEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtEditar.setText("SALVAR");
        BtEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEditarActionPerformed(evt);
            }
        });
        jPanel7.add(BtEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 33));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 600, 60));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações de Entrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 11))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdHoraEntra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hora Entrada:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        try {
            EdHoraEntra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel8.add(EdHoraEntra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        EdHoraSai.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hora Saída", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        try {
            EdHoraSai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel8.add(EdHoraSai, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 110, -1));

        BtApontaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/saida.png"))); // NOI18N
        BtApontaSaida.setText("SAÍDA");
        BtApontaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtApontaSaidaActionPerformed(evt);
            }
        });
        jPanel8.add(BtApontaSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 100, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel3.setText("do visitante");
        jPanel8.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 60, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel4.setText("Informar hora saída");
        jPanel8.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, 100, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel5.setText("somente após a saída ");
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 250, 110));

        EdObserva.setColumns(20);
        EdObserva.setRows(5);
        EdObserva.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observações Destino:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 11))); // NOI18N
        jScrollPane2.setViewportView(EdObserva);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 310, 100));

        EdCodAcesso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel1.add(EdCodAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        jLabel1.setText("Status:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        LbStatus.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 11)); // NOI18N
        LbStatus.setText("Status");
        jPanel1.add(LbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, -1));

        BtBuscaSaidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaSaidas.setText("Buscar");
        BtBuscaSaidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaSaidasActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscaSaidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, -1, -1));

        EdBuscaEmpNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Nome Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        EdBuscaEmpNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaEmpNomeKeyPressed(evt);
            }
        });
        jPanel1.add(EdBuscaEmpNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 220, -1));

        jTabbedPane1.addTab("Controle de Acessos", jPanel1);

        TbListagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Data", "Nome", "RG", "Empresa", "Veículo", "Placa", "Entrada", "Saída", "Observação", "Status"
            }
        ));
        jScrollPane3.setViewportView(TbListagem);
        if (TbListagem.getColumnModel().getColumnCount() > 0) {
            TbListagem.getColumnModel().getColumn(3).setMinWidth(0);
            TbListagem.getColumnModel().getColumn(3).setPreferredWidth(0);
            TbListagem.getColumnModel().getColumn(3).setMaxWidth(0);
            TbListagem.getColumnModel().getColumn(9).setMinWidth(0);
            TbListagem.getColumnModel().getColumn(9).setPreferredWidth(0);
            TbListagem.getColumnModel().getColumn(9).setMaxWidth(0);
            TbListagem.getColumnModel().getColumn(10).setMinWidth(0);
            TbListagem.getColumnModel().getColumn(10).setPreferredWidth(0);
            TbListagem.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        EdBuscaEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Nome Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        EdBuscaEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaEmpKeyPressed(evt);
            }
        });

        BtBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBusca.setText("Buscar");
        BtBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaActionPerformed(evt);
            }
        });

        JcSaidasConfirmaListagem.setText("Saídas Confirmadas");

        JbSelectMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(EdBuscaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(BtBusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JbSelectMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(JcSaidasConfirmaListagem)))
                .addGap(45, 45, 45))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdBuscaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtBusca)
                            .addComponent(JbSelectMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcSaidasConfirmaListagem))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listar Acessos", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        limpaCampos();
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

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

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarAcesso();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtBuscarNomeEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeEmpActionPerformed
        try {
            this.FDBuscaEmp = new FDBuscaEmp("", null, true);

            if (EdNomeEmp.getText().equals("")) {
                this.FDBuscaEmp.setVisible(true);

                this.EdNomeEmp.setText(String.valueOf(this.FDBuscaEmp.getNomeEmp()));
                habilitarB(2);

                if (this.EdNomeEmp.getText().equals("null")) {
                    EdNomeEmp.setText("");
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarNomeEmpActionPerformed

    private void BtApontaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtApontaSaidaActionPerformed

        try {
            aportarSaidaAcesso();

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("apontar saída- 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtApontaSaidaActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        if (tabela.getSelectedRow() != -1) {

            EdCodAcesso.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            //data não
            EdNomePessoa.setText(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
            EdRgPessoa.setText(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
            EdNomeEmp.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());
            EdVeiculo.setText(tabela.getValueAt(tabela.getSelectedRow(), 5).toString());
            EdPlaca.setText(tabela.getValueAt(tabela.getSelectedRow(), 6).toString());
            EdHoraEntra.setText(tabela.getValueAt(tabela.getSelectedRow(), 7).toString());
            EdHoraSai.setText(tabela.getValueAt(tabela.getSelectedRow(), 8).toString());
            EdObserva.setText(tabela.getValueAt(tabela.getSelectedRow(), 9).toString());

            int status = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 10).toString());

            switch (status) {
                case 1:
                    LbStatus.setText("Saída não confirmada");
                    LbStatus.setForeground(Color.decode("#ff0000"));
                    habilitarB(4);
                    break;
                case 2:
                    LbStatus.setText("Saída confirmada");
                    LbStatus.setForeground(Color.decode("#0f943b"));
                    habilitarB(3);
                    break;
                default:
                    System.out.println("erro ao buscar status: " + status);
                    break;
            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void BtNovo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovo1ActionPerformed
        limpaCampos();
        habilitarB(2);

    }//GEN-LAST:event_BtNovo1ActionPerformed

    private void BtBuscaSaidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaSaidasActionPerformed
        listarAcesso();
    }//GEN-LAST:event_BtBuscaSaidasActionPerformed

    private void EdBuscaEmpNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaEmpNomeKeyPressed
        listarAcesso();
    }//GEN-LAST:event_EdBuscaEmpNomeKeyPressed

    private void EdBuscaEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaEmpKeyPressed
        listarAcessoTelaListar();
    }//GEN-LAST:event_EdBuscaEmpKeyPressed

    private void BtBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaActionPerformed
        listarAcessoTelaListar();
    }//GEN-LAST:event_BtBuscaActionPerformed

    private void BtEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEditarActionPerformed

    }//GEN-LAST:event_BtEditarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtApontaSaida;
    private javax.swing.JButton BtBusca;
    private javax.swing.JButton BtBuscaSaidas;
    private javax.swing.JButton BtBuscarNomeEmp;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtEditar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo1;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JTextField EdBuscaEmp;
    private javax.swing.JTextField EdBuscaEmpNome;
    private javax.swing.JTextField EdCodAcesso;
    private javax.swing.JFormattedTextField EdHoraEntra;
    private javax.swing.JFormattedTextField EdHoraSai;
    private javax.swing.JTextField EdNomeEmp;
    private javax.swing.JTextField EdNomePessoa;
    private javax.swing.JTextArea EdObserva;
    private javax.swing.JTextField EdPlaca;
    private javax.swing.JTextField EdRgPessoa;
    private javax.swing.JTextField EdVeiculo;
    private javax.swing.JComboBox<String> JbSelectMes;
    private javax.swing.JCheckBox JcSaidasConfirmaListagem;
    private javax.swing.JLabel LbStatus;
    private javax.swing.JTable TbListagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
