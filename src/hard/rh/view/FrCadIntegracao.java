package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FrcadUsuario;
import hard.home.view.FDBuscaFuncio;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.EmpresaDao;
import hard.rh.dao.FuncionarioDao;
import hard.rh.dao.IntegracaoDao;
import hard.rh.model.Empresa;
import hard.rh.model.Funcionario;
import hard.rh.model.Integracao;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrCadIntegracao extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";
    FDErroOcorrido fdErroOcorrido;
    private FDBuscaFuncio FDBuscaFuncio;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    public FrCadIntegracao(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        title = "Controle de Integrações - V" + versao;

        habilitarB(1);
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrCadIntegracao";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar log de acesso");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 3, (d.height - this.getSize().height) / 6);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtRenovar.setEnabled(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscarCodFuncionario.setEnabled(true);
                BtBuscarCodEmpresa.setEnabled(true);
                EdCodIntegra.setEditable(true);
                temp.setVisible(false);

                EdCodFuncio.setEnabled(true);
                EdCodFuncio.setEditable(true);
                EdNomeFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdSetorFuncio.setEnabled(false);
                EdTurnoFuncio.setEnabled(false);
                EdCpfFuncio.setEnabled(false);

                EdCodEmpresa.setEditable(true);
                EdNomeEmpresa.setEnabled(false);

                JdcUltiIntegra.setEnabled(false);
                JdcVencIntegra.setEnabled(false);
                JdcUltiAso.setEnabled(false);
                JdcVencAso.setEnabled(false);

                EdCodFuncio.requestFocus();
                break;

            case 2:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtRenovar.setEnabled(false);
                BtExcluir.setEnabled(false);
                BtBuscarCodEmpresa.setEnabled(true);
                BtBuscarCodFuncionario.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdCodFuncio.setEditable(true);
                EdCodEmpresa.setEditable(true);

                EdCodIntegra.setEditable(false);

                EdNomeFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);
                EdSetorFuncio.setEnabled(true);
                EdTurnoFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);

                EdNomeEmpresa.setEnabled(true);

                JdcUltiIntegra.setEnabled(true);
                JdcVencIntegra.setEnabled(true);
                JdcUltiAso.setEnabled(true);
                JdcVencAso.setEnabled(true);

                EdNomeFuncio.requestFocus();
                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtRenovar.setEnabled(true);
                BtExcluir.setEnabled(true);
                BtCancelar.setEnabled(true);
                BtBuscarCodFuncionario.setEnabled(false);
                BtBuscarCodEmpresa.setEnabled(false);
                EdCodIntegra.setEditable(false);

                EdCodFuncio.setEditable(false);
                EdCodEmpresa.setEditable(false);
                EdNomeFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);
                EdSetorFuncio.setEnabled(true);
                EdTurnoFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);

                EdNomeFuncio.setEditable(false);
                EdCargoFuncio.setEditable(false);
                EdSetorFuncio.setEditable(false);
                EdTurnoFuncio.setEditable(false);
                EdCpfFuncio.setEditable(false);

                EdNomeEmpresa.setEnabled(true);
                EdNomeEmpresa.setEditable(false);

                JdcUltiIntegra.setEnabled(true);
                JdcVencIntegra.setEnabled(true);
                JdcUltiAso.setEnabled(true);
                JdcVencAso.setEnabled(true);

                EdNomeFuncio.requestFocus();
                break;
        }
    }

    public void limpaCampos() {
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        EdNomeEmpresa.setText(null);
        EdSetorFuncio.setText(null);
        EdCargoFuncio.setText(null);
        EdCpfFuncio.setText(null);
        EdCargoFuncio.setText(null);
        EdTurnoFuncio.setText(null);
        EdCodEmpresa.setText(null);
        EdNomeEmpresa.setText(null);

        JdcUltiIntegra.setDate(null);
        JdcVencIntegra.setDate(null);
        JdcUltiAso.setDate(null);
        JdcVencAso.setDate(null);
    }

    public void validaCadastroIntegra() {
        IntegracaoDao pdao = new IntegracaoDao();

        try {
            String codFuncio = EdCodFuncio.getText();
            String codEmpresa = EdCodEmpresa.getText();

            if (pdao.checkCadIntegra(codFuncio, codEmpresa)) {

                JOptionPane.showMessageDialog(JPanelFront, "Atenção\nFuncionário já possui cadastrado de integração nesta empresa!");

            } else if (JdcUltiIntegra.getDate() == null || JdcVencIntegra.getDate() == null || JdcUltiAso.getDate() == null || JdcVencAso.getDate() == null) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO\n Você deve preencher as datas!");
            } else {
                salvarIntegracao();
            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void salvarIntegracao() {
        if (EdCodEmpresa.getText().equals("") || EdNomeEmpresa.getText().equals("") || EdSetorFuncio.getText().equals("") || EdCargoFuncio.getText().equals("") || EdCpfFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else {

            Integracao i = new Integracao();
            IntegracaoDao dao = new IntegracaoDao();

            i.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
            i.setFr_nomeFuncionario(EdNomeFuncio.getText());
            i.setFr_setorFuncionario(EdSetorFuncio.getText());
            i.setFr_cpfFuncionario(EdCpfFuncio.getText());
            i.setFr_cargoFuncionario(EdCargoFuncio.getText());
            i.setFr_turnoFuncionario(EdTurnoFuncio.getText());
            i.setFr_codEmpresa(Integer.parseInt(EdCodEmpresa.getText()));
            i.setFr_nomeEmpresa(EdNomeEmpresa.getText());

            java.util.Date UltiIntegra = JdcUltiIntegra.getDate();
            long dtIntegra = UltiIntegra.getTime();
            java.sql.Date dateUltiIntegra = new java.sql.Date(dtIntegra);
            i.setDataUltiInt(dateUltiIntegra);

            java.util.Date VencIntegra = JdcVencIntegra.getDate();
            long dtVenciIntegra = VencIntegra.getTime();
            java.sql.Date dateVencIntegra = new java.sql.Date(dtVenciIntegra);
            i.setDataVencInt(dateVencIntegra);

            java.util.Date UltiAso = JdcUltiAso.getDate();
            long dtUltiAso = UltiAso.getTime();
            java.sql.Date dateUltiAso = new java.sql.Date(dtUltiAso);
            i.setDataUltiAso(dateUltiAso);

            java.util.Date dataVencAso = JdcVencAso.getDate();
            long dtVencAso = dataVencAso.getTime();
            java.sql.Date vencimentoAso = new java.sql.Date(dtVencAso);
            i.setDataVencAso(vencimentoAso);

            try {
                dao.createIntegracao(i);
                habilitarB(1);
                limpaCampos();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("salvar nova integração");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }
    }

    public void listarIntegracao() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        IntegracaoDao pdao = new IntegracaoDao();
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        if (CbxFiltroDatas.isSelected()) {
            if (JdcFiltraUltiIntegraInicial.getDate() == null || JdcFiltraUltiIntegraFinal.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Você deve informar as datas do filtro!");
            } else {
                java.util.Date dataIni = JdcFiltraUltiIntegraInicial.getDate();
                long dtInicial = dataIni.getTime();
                java.sql.Date dateIntegraInicial = new java.sql.Date(dtInicial);

                java.util.Date dataFin = JdcFiltraUltiIntegraFinal.getDate();
                long dtFinal = dataFin.getTime();
                java.sql.Date dateIntegraFinal = new java.sql.Date(dtFinal);

                for (Integracao i : pdao.readParForData(dateIntegraInicial, dateIntegraFinal)) {

                    modelo.addRow(new Object[]{
                        i.getCodInt(),
                        i.getFr_codFuncionario(),
                        i.getFr_nomeFuncionario(),
                        i.getFr_cpfFuncionario(),
                        i.getFr_setorFuncionario(),
                        i.getFr_cargoFuncionario(),
                        i.getFr_turnoFuncionario(),
                        i.getFr_codEmpresa(),
                        i.getFr_nomeEmpresa(),
                        formatdata.format(i.getDataUltiInt()),
                        formatdata.format(i.getDataVencInt()),
                        formatdata.format(i.getDataUltiAso()),
                        formatdata.format(i.getDataVencAso()),
                        (i.getDiasVencidos() * -1)});
                }
            }

        } else if (CbxVencidas.isSelected()) {

            for (Integracao i : pdao.readIntegracaoVencidas()) {
                modelo.addRow(new Object[]{
                    i.getCodInt(),
                    i.getFr_codFuncionario(),
                    i.getFr_nomeFuncionario(),
                    i.getFr_cpfFuncionario(),
                    i.getFr_setorFuncionario(),
                    i.getFr_cargoFuncionario(),
                    i.getFr_turnoFuncionario(),
                    i.getFr_codEmpresa(),
                    i.getFr_nomeEmpresa(),
                    formatdata.format(i.getDataUltiInt()),
                    formatdata.format(i.getDataVencInt()),
                    formatdata.format(i.getDataUltiAso()),
                    formatdata.format(i.getDataVencAso()),
                    (i.getDiasVencidos() * -1)});
            }
        } else if (!EdBuscarFuncio.getText().equals("")) {
            for (Integracao i : pdao.readIntegracaoForFuncionario(EdBuscarFuncio.getText())) {
                modelo.addRow(new Object[]{
                    i.getCodInt(),
                    i.getFr_codFuncionario(),
                    i.getFr_nomeFuncionario(),
                    i.getFr_cpfFuncionario(),
                    i.getFr_setorFuncionario(),
                    i.getFr_cargoFuncionario(),
                    i.getFr_turnoFuncionario(),
                    i.getFr_codEmpresa(),
                    i.getFr_nomeEmpresa(),
                    formatdata.format(i.getDataUltiInt()),
                    formatdata.format(i.getDataVencInt()),
                    formatdata.format(i.getDataUltiAso()),
                    formatdata.format(i.getDataVencAso()),
                    (i.getDiasVencidos() * -1)});
            }

        } else {

            for (Integracao i : pdao.readIntegracao()) {
                modelo.addRow(new Object[]{
                    i.getCodInt(),
                    i.getFr_codFuncionario(),
                    i.getFr_nomeFuncionario(),
                    i.getFr_cpfFuncionario(),
                    i.getFr_setorFuncionario(),
                    i.getFr_cargoFuncionario(),
                    i.getFr_turnoFuncionario(),
                    i.getFr_codEmpresa(),
                    i.getFr_nomeEmpresa(),
                    formatdata.format(i.getDataUltiInt()),
                    formatdata.format(i.getDataVencInt()),
                    formatdata.format(i.getDataUltiAso()),
                    formatdata.format(i.getDataVencAso()),
                    (i.getDiasVencidos() * -1)});

            }

        }

    }

    //READ INTEGRAÇÃO PELO CÓDIGO DA INTEGRAÇÃO
    public void readIntegracaoForCod() throws ClassNotFoundException {

        if (!EdCodIntegra.getText().equals("")) {
            IntegracaoDao dao = new IntegracaoDao();
            for (Integracao i : dao.readIntegracaoForCod(Integer.parseInt(EdCodIntegra.getText()))) {
                EdCodFuncio.setText(String.valueOf(i.getFr_codFuncionario()));
                EdNomeFuncio.setText(i.getFr_nomeFuncionario());
                EdSetorFuncio.setText(i.getFr_setorFuncionario());
                EdCargoFuncio.setText(i.getFr_cargoFuncionario());
                EdCpfFuncio.setText(i.getFr_cpfFuncionario());
                EdTurnoFuncio.setText(i.getFr_turnoFuncionario());

                EdCodEmpresa.setText(String.valueOf(i.getFr_codEmpresa()));
                EdNomeEmpresa.setText(i.getFr_nomeEmpresa());

                JdcUltiIntegra.setDate(i.getDataUltiInt());
                JdcUltiAso.setDate(i.getDataUltiAso());
                JdcVencIntegra.setDate(i.getDataVencInt());
                JdcVencAso.setDate(i.getDataVencAso());
                habilitarB(3);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar o código da integração", "Listar Integração", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void listaIntegracaoCodEmpresa() {

        IntegracaoDao pdao = new IntegracaoDao();

        if (CbxVencidas.isSelected()) {

            JOptionPane.showMessageDialog(null, "Informe o código do usuário!");

            try {
                for (Integracao fu : pdao.readIntegracaoVencidas()) {

                    EdNomeEmpresa.setText(fu.getFr_nomeFuncionario());
                    EdSetorFuncio.setText(fu.getFr_setorFuncionario());
                    EdCargoFuncio.setText(fu.getFr_cargoFuncionario());
                    EdCpfFuncio.setText(fu.getFr_cpfFuncionario());
                    habilitarB(3);

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar integração");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
        if (EdCodEmpresa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário!");
        } else {

            try {
                for (Integracao fu : pdao.readIntegracaoForCodEmpresa(EdCodEmpresa.getText())) {

                    EdNomeEmpresa.setText(fu.getFr_nomeFuncionario());
                    EdSetorFuncio.setText(fu.getFr_setorFuncionario());
                    EdCargoFuncio.setText(fu.getFr_cargoFuncionario());
                    EdCpfFuncio.setText(fu.getFr_cpfFuncionario());
                    habilitarB(3);

                }
            } catch (ClassNotFoundException ex) {
               fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar integração");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void editarIntegracao() throws ClassNotFoundException {

        if (tabela.getSelectedRow() != -1) {
            Integracao i = new Integracao();
            IntegracaoDao dao = new IntegracaoDao();

            if (EdNomeEmpresa.getText().equals("") || EdSetorFuncio.getText().equals("") || EdCpfFuncio.getText().equals("")
                    || EdCargoFuncio.getText().equals("") || EdNomeEmpresa.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO\n Você deve preencher todos os campos");
            } else {

                java.util.Date UltiIntegra = JdcUltiIntegra.getDate();
                long dtIntegra = UltiIntegra.getTime();
                java.sql.Date dateUltiIntegra = new java.sql.Date(dtIntegra);
                i.setDataUltiInt(dateUltiIntegra);

                java.util.Date VencIntegra = JdcVencIntegra.getDate();
                long dtVenciIntegra = VencIntegra.getTime();
                java.sql.Date dateVencIntegra = new java.sql.Date(dtVenciIntegra);
                i.setDataVencInt(dateVencIntegra);

                java.util.Date UltiAso = JdcUltiAso.getDate();
                long dtUltiAso = UltiAso.getTime();
                java.sql.Date dateUltiAso = new java.sql.Date(dtUltiAso);
                i.setDataUltiAso(dateUltiAso);

                java.util.Date dataVencAso = JdcVencAso.getDate();
                long dtVencAso = dataVencAso.getTime();
                java.sql.Date vencimentoAso = new java.sql.Date(dtVencAso);
                i.setDataVencAso(vencimentoAso);

                i.setCodInt((int) tabela.getValueAt(tabela.getSelectedRow(), 0));
                try {
                    dao.updateIntegracao(i);
                    limpaCampos();
                    listarIntegracao();
                    habilitarB(1);

                } catch (ClassNotFoundException ex) {
                   fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar integração");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
                }

            }

        }
    }

    public void deletarIntegracao() throws ClassNotFoundException {
        Integracao i = new Integracao();
        IntegracaoDao dao = new IntegracaoDao();

        if (EdCodIntegra.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do usuário para excluír");
        } else {
            i.setCodInt(Integer.parseInt(EdCodIntegra.getText()));
            try {
                String nome = EdNomeFuncio.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o cadastro da integração do funcionário  " + nome, "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);

                if (input == 0) {
                    dao.deleteIntegracao(i);
                    habilitarB(1);
                    limpaCampos();
                    listarIntegracao();

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

    public void validarCodFuncio() {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário!");
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();
                    habilitarB(2);
                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado");

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar cadastro");
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
            EdCargoFuncio.setText(fu.getCargoFuncionario());
            EdCpfFuncio.setText(fu.getCpfFuncionario());
            EdTurnoFuncio.setText(fu.getTurnoFuncionario());

        }
    }

    public void validarCodEmpresa() {
        EmpresaDao pdao = new EmpresaDao();

        if (EdCodEmpresa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código da empresa!");
        } else {

            try {
                if (pdao.checkCod(EdCodEmpresa.getText())) {
                    listarEmpresaCod();
                    habilitarB(2);
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa não encontrada!");

                }
            } catch (ClassNotFoundException ex) {
               fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código empresa");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarEmpresaCod() throws ClassNotFoundException {

        EmpresaDao pdao = new EmpresaDao();

        for (Empresa em : pdao.readEmpForCod(EdCodEmpresa.getText())) {
            EdNomeEmpresa.setText(em.getNomeEmp());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        JPanelFront = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        BtNovo = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtExcluir = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtRenovar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        EdNomeEmpresa = new javax.swing.JTextField();
        BtBuscarCodEmpresa = new javax.swing.JButton();
        EdCodEmpresa = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        EdTurnoFuncio = new javax.swing.JTextField();
        EdCargoFuncio = new javax.swing.JTextField();
        EdSetorFuncio = new javax.swing.JTextField();
        EdNomeFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncionario = new javax.swing.JButton();
        EdCodFuncio = new javax.swing.JTextField();
        EdCpfFuncio = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        JdcUltiIntegra = new com.toedter.calendar.JDateChooser();
        JdcVencIntegra = new com.toedter.calendar.JDateChooser();
        JdcVencAso = new com.toedter.calendar.JDateChooser();
        JdcUltiAso = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        EdCodIntegra = new javax.swing.JTextField();
        temp = new javax.swing.JTextField();
        BtBuscaIntegra = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        EdBuscarFuncio = new javax.swing.JTextField();
        BtBuscarNome = new javax.swing.JButton();
        CbxFiltroDatas = new javax.swing.JCheckBox();
        CbxVencidas = new javax.swing.JCheckBox();
        JdcFiltraUltiIntegraInicial = new com.toedter.calendar.JDateChooser();
        JdcFiltraUltiIntegraFinal = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        JPanelFront.setBackground(new java.awt.Color(255, 255, 255));

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
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 78, 33));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 110, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 33));

        BtRenovar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-black.png"))); // NOI18N
        BtRenovar.setText("RENOVAR");
        BtRenovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRenovarActionPerformed(evt);
            }
        });
        jPanel4.add(BtRenovar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 110, 33));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdNomeEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel5.add(EdNomeEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 180, -1));

        BtBuscarCodEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodEmpresa.setText("Buscar");
        BtBuscarCodEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodEmpresaActionPerformed(evt);
            }
        });
        jPanel5.add(BtBuscarCodEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        EdCodEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Empresa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodEmpresaKeyPressed(evt);
            }
        });
        jPanel5.add(EdCodEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdTurnoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turno:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdTurnoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 160, -1));

        EdCargoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdCargoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, -1));

        EdSetorFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdSetorFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 160, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 330, -1));

        BtBuscarCodFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncionario.setText("Buscar");
        BtBuscarCodFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncionarioActionPerformed(evt);
            }
        });
        jPanel6.add(BtBuscarCodFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel6.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        EdCpfFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        try {
            EdCpfFuncio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel6.add(EdCpfFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 170, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdcUltiIntegra.setBackground(new java.awt.Color(255, 255, 255));
        JdcUltiIntegra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data integração:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcUltiIntegra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        JdcVencIntegra.setBackground(new java.awt.Color(255, 255, 255));
        JdcVencIntegra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcVencIntegra, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 150, -1));

        JdcVencAso.setBackground(new java.awt.Color(255, 255, 255));
        JdcVencAso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data vencimento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcVencAso, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 65, 150, -1));

        JdcUltiAso.setBackground(new java.awt.Color(255, 255, 255));
        JdcUltiAso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data ASO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JdcUltiAso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, 150, -1));

        jLabel17.setText("Dados do Cliente");

        jLabel18.setText("Dados do funcionário");

        jLabel21.setText("Datas");

        EdCodIntegra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cod. Integração", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        BtBuscaIntegra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaIntegra.setText("Buscar");
        BtBuscaIntegra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaIntegraActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cód. Funcionário", "Nome Funcionário", "CPF", "Setor", "Cargo", "Turno", "Cód. Empresa", "Nome Empresa", "Ultima Integração", "Vencimento Integração", "Ultimo Aso", "Vencimento Aso", "Dias Vencimento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
            tabela.getColumnModel().getColumn(0).setMinWidth(0);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(0).setMaxWidth(0);
            tabela.getColumnModel().getColumn(1).setMinWidth(0);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(1).setMaxWidth(0);
            tabela.getColumnModel().getColumn(2).setMinWidth(110);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(255);
            tabela.getColumnModel().getColumn(2).setMaxWidth(300);
            tabela.getColumnModel().getColumn(3).setMinWidth(0);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(3).setMaxWidth(0);
            tabela.getColumnModel().getColumn(4).setMinWidth(100);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(4).setMaxWidth(200);
            tabela.getColumnModel().getColumn(5).setMinWidth(0);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(5).setMaxWidth(0);
            tabela.getColumnModel().getColumn(6).setMinWidth(50);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(6).setMaxWidth(120);
            tabela.getColumnModel().getColumn(7).setMinWidth(0);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(7).setMaxWidth(0);
            tabela.getColumnModel().getColumn(9).setMinWidth(0);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(9).setMaxWidth(0);
            tabela.getColumnModel().getColumn(11).setMinWidth(0);
            tabela.getColumnModel().getColumn(11).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        EdBuscarFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscarFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarFuncioKeyPressed(evt);
            }
        });

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });

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

        JdcFiltraUltiIntegraInicial.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraUltiIntegraInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        JdcFiltraUltiIntegraFinal.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraUltiIntegraFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabel9.setText("Clique para editar*");

        javax.swing.GroupLayout JPanelFrontLayout = new javax.swing.GroupLayout(JPanelFront);
        JPanelFront.setLayout(JPanelFrontLayout);
        JPanelFrontLayout.setHorizontalGroup(
            JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
            .addGroup(JPanelFrontLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(350, 350, 350)
                                .addComponent(CbxVencidas))
                            .addComponent(EdBuscarFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(250, 250, 250)
                                .addComponent(BtBuscarNome))
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(480, 480, 480)
                                .addComponent(JdcFiltraUltiIntegraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(620, 620, 620)
                                .addComponent(JdcFiltraUltiIntegraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(350, 350, 350)
                                .addComponent(CbxFiltroDatas)))
                        .addGap(90, 90, 90)
                        .addComponent(jLabel9))
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel18)
                                .addGap(182, 182, 182)
                                .addComponent(temp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(BtBuscaIntegra))
                                    .addComponent(jLabel17))
                                .addGap(1, 1, 1)
                                .addComponent(EdCodIntegra, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))))
        );
        JPanelFrontLayout.setVerticalGroup(
            JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelFrontLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(temp, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addComponent(BtBuscaIntegra)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(EdCodIntegra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel9))
                    .addGroup(JPanelFrontLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(JPanelFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(CbxVencidas))
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(EdBuscarFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(BtBuscarNome))
                            .addComponent(JdcFiltraUltiIntegraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JdcFiltraUltiIntegraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JPanelFrontLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(CbxFiltroDatas)))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Cadastrar Nova Integração", JPanelFront);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCodEmpresa.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            deletarIntegracao();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed

        validaCadastroIntegra();
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtRenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRenovarActionPerformed
        try {
            editarIntegracao();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("editar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtRenovarActionPerformed

    private void BtBuscarCodEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodEmpresaActionPerformed

        validarCodEmpresa();
    }//GEN-LAST:event_BtBuscarCodEmpresaActionPerformed

    private void EdCodEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodEmpresaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodEmpresa();
        }
    }//GEN-LAST:event_EdCodEmpresaKeyPressed

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
            EdCodEmpresa.requestFocus();
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        try {
            listarIntegracao();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (tabela.getSelectedRow() != -1) {
            jTabbedPane1.setSelectedIndex(0);
            EdCodIntegra.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            try {

                readIntegracaoForCod();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar integração");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void CbxVencidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxVencidasMouseClicked

    }//GEN-LAST:event_CbxVencidasMouseClicked

    private void CbxVencidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxVencidasActionPerformed

    }//GEN-LAST:event_CbxVencidasActionPerformed

    private void CbxFiltroDatasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxFiltroDatasMouseClicked

    }//GEN-LAST:event_CbxFiltroDatasMouseClicked

    private void CbxFiltroDatasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxFiltroDatasActionPerformed

    }//GEN-LAST:event_CbxFiltroDatasActionPerformed

    private void BtBuscaIntegraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaIntegraActionPerformed
        try {
            readIntegracaoForCod();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscaIntegraActionPerformed

    private void EdBuscarFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarFuncioKeyPressed
        try {
            listarIntegracao();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdBuscarFuncioKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscaIntegra;
    private javax.swing.JButton BtBuscarCodEmpresa;
    private javax.swing.JButton BtBuscarCodFuncionario;
    private javax.swing.JButton BtBuscarNome;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtRenovar;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JCheckBox CbxFiltroDatas;
    private javax.swing.JCheckBox CbxVencidas;
    private javax.swing.JTextField EdBuscarFuncio;
    private javax.swing.JTextField EdCargoFuncio;
    private javax.swing.JTextField EdCodEmpresa;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodIntegra;
    private javax.swing.JFormattedTextField EdCpfFuncio;
    private javax.swing.JTextField EdNomeEmpresa;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdSetorFuncio;
    private javax.swing.JTextField EdTurnoFuncio;
    private javax.swing.JPanel JPanelFront;
    private com.toedter.calendar.JDateChooser JdcFiltraUltiIntegraFinal;
    private com.toedter.calendar.JDateChooser JdcFiltraUltiIntegraInicial;
    private com.toedter.calendar.JDateChooser JdcUltiAso;
    private com.toedter.calendar.JDateChooser JdcUltiIntegra;
    private com.toedter.calendar.JDateChooser JdcVencAso;
    private com.toedter.calendar.JDateChooser JdcVencIntegra;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField temp;
    // End of variables declaration//GEN-END:variables
}
