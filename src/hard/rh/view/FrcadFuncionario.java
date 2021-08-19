package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FuncionarioDao;
import hard.rh.model.Dependente;
import hard.rh.model.Funcionario;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrcadFuncionario extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0810.0";

    FDErroOcorrido fdErroOcorrido;
    String codDependente = null;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrcadFuncionario(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        habilitarB(1);

        title = "Manutenção de Funcionário - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrcadFuncionario";
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

    public void habilitarB(int op) {
        switch (op) {
            case 1:
                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtUpdateCloud.setEnabled(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(true);
                EdNome.setEnabled(false);
                EdCodFuncionario.setEnabled(true);
                EdCodFuncionario.setEditable(true);
                EdNr.setEnabled(false);
                EdNr.setEditable(false);
                EdSetor.setEnabled(false);
                EdCpf.setEnabled(false);
                EdCargo.setEnabled(false);
                EdNome.requestFocus();
                EdTurno.setEnabled(false);
                EdMail.setEnabled(false);
                EdCelular.setEnabled(false);
                EdNumeResi.setEnabled(false);

                EdNomeDepende.setEnabled(false);
                EdCpf.setEnabled(false);
                JcTipoDepende.setEnabled(false);
                BtAdcDepende.setEnabled(false);
                BtRemovDepend.setEnabled(false);

                JcStatus.setEnabled(false);
                EdCbo.setEnabled(false);
                JDcDataAdmi.setEnabled(false);
                EdSalario.setEnabled(false);
                JcTipoSalario.setEnabled(false);
                JbAdNoturno.setEnabled(false);
                JbInsalubri.setEnabled(false);
                JbUtiTrans.setEnabled(false);
                JbUtiliRef.setEnabled(false);

                EdCodFuncionario.requestFocus();
                break;

            case 2:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtUpdateCloud.setEnabled(true);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdNome.setEnabled(true);
                EdCodFuncionario.setEnabled(true);
                EdNr.setEnabled(true);
                EdNr.setEditable(true);
                EdSetor.setEnabled(true);
                EdCpf.setEnabled(true);
                EdCargo.setEnabled(true);
                EdTurno.setEnabled(true);
                EdNome.requestFocus();
                EdMail.setEnabled(true);
                EdCelular.setEnabled(true);
                EdNumeResi.setEnabled(true);

                EdNomeDepende.setEnabled(true);
                EdCpf.setEnabled(true);
                JcTipoDepende.setEnabled(true);
                BtAdcDepende.setEnabled(true);
                BtRemovDepend.setEnabled(true);

                JcStatus.setEnabled(true);
                EdCbo.setEnabled(true);
                JDcDataAdmi.setEnabled(true);
                EdSalario.setEnabled(true);
                JcTipoSalario.setEnabled(true);
                JbAdNoturno.setEnabled(true);
                JbInsalubri.setEnabled(true);
                JbUtiTrans.setEnabled(true);
                JbUtiliRef.setEnabled(true);

                EdCodFuncionario.requestFocus();
                break;

            case 3:

                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtUpdateCloud.setEnabled(true);
                BtExcluir.setEnabled(true);
                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdNome.setEnabled(true);
                EdCodFuncionario.setEnabled(true);
                EdCodFuncionario.setEditable(false);
                EdNr.setEnabled(true);
                EdNr.setEditable(false);
                EdSetor.setEnabled(true);
                EdCpf.setEnabled(true);
                EdCargo.setEnabled(true);
                EdTurno.setEnabled(true);
                EdNome.requestFocus();
                EdMail.setEnabled(true);
                EdCelular.setEnabled(true);
                EdNumeResi.setEnabled(true);

                EdNomeDepende.setEnabled(true);
                EdCpf.setEnabled(true);
                JcTipoDepende.setEnabled(true);
                BtAdcDepende.setEnabled(true);
                BtRemovDepend.setEnabled(true);

                JcStatus.setEnabled(true);
                EdCbo.setEnabled(true);
                JDcDataAdmi.setEnabled(true);
                EdSalario.setEnabled(true);
                JcTipoSalario.setEnabled(true);
                JbAdNoturno.setEnabled(true);
                JbInsalubri.setEnabled(true);
                JbUtiTrans.setEnabled(true);
                JbUtiliRef.setEnabled(true);
                break;

            case 4:
                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtUpdateCloud.setEnabled(true);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(true);
                EdNome.setEnabled(false);
                EdCodFuncionario.setEnabled(true);
                EdCodFuncionario.setEditable(true);
                EdNr.setEnabled(true);
                EdNr.setEditable(false);
                EdSetor.setEnabled(false);
                EdCpf.setEnabled(false);
                EdCargo.setEnabled(false);
                EdNome.requestFocus();
                EdTurno.setEnabled(false);
                EdMail.setEnabled(false);
                EdCelular.setEnabled(false);
                EdNumeResi.setEnabled(false);

                EdNomeDepende.setEnabled(false);
                EdCpf.setEnabled(false);
                JcTipoDepende.setEnabled(false);
                BtAdcDepende.setEnabled(false);
                BtRemovDepend.setEnabled(false);

                JcStatus.setEnabled(true);
                EdCbo.setEnabled(false);
                JDcDataAdmi.setEnabled(false);
                EdSalario.setEnabled(false);
                JcTipoSalario.setEnabled(false);
                JbAdNoturno.setEnabled(false);
                JbInsalubri.setEnabled(false);
                JbUtiTrans.setEnabled(false);
                JbUtiliRef.setEnabled(false);
                break;
        }
    }

    public void limpaCampos() {
        codDependente = null;
        EdCodFuncionario.setText("");
        EdNr.setText("");
        EdNome.setText("");
        EdSetor.setText("");
        EdCargo.setText("");
        EdCpf.setText("");
        EdCargo.setText("");
        EdTurno.setText("");
        JcStatus.setSelectedItem("<Selecione>");
        JcTipoSalario.setSelectedItem("<Selecione>");
        JbAdNoturno.setSelected(false);
        JbInsalubri.setSelected(false);
        JbUtiTrans.setSelected(false);
        JbUtiliRef.setSelected(false);
        EdCbo.setText("");
        EdSalario.setText("");
        EdCpfDepende.setText("");
        EdMail.setText("");
        EdCelular.setText("");
        EdNumeResi.setText("");

        EdNomeDepende.setText("");
        JcTipoDepende.setSelectedItem("<Selecione>");

        DefaultTableModel modelo = (DefaultTableModel) JtListaDepende.getModel();
        modelo.setNumRows(0);

    }

    public void validarCpfCadastro() {
        FuncionarioDao pdao = new FuncionarioDao();

        try {
            if (!pdao.checkCodFuncio(Integer.parseInt(EdCodFuncionario.getText()))) {
                if (pdao.checkCpf(EdCpf.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Este CPF já foi cadastrado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                } else {

                    if (JcStatus.getSelectedItem() == "<Selecione>") {
                        JOptionPane.showMessageDialog(rootPane, "Voce deve selecionar a situação do cadastro", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                    } else {
                        salvarFuncionario();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Código do funcionário já cadastrado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar CPF");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void validarCpfEdit() throws ClassNotFoundException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncionario.getText().equals("") || EdNr.getText().equals("") || EdNome.getText().equals("") || EdSetor.getText().equals("") || EdCargo.getText().equals("") || EdCpf.getText().equals("   .   .   -  ") || EdTurno.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCpf(EdCpf.getText())) {
                    if (!pdao.checkCodAndCpf(Integer.parseInt(EdCodFuncionario.getText()), EdCpf.getText())) {
                        JOptionPane.showMessageDialog(rootPane, "Este CPF está cadastrado em outro colaborador", "Atenção", JOptionPane.WARNING_MESSAGE);
                    } else {
                        editarFuncionario();
                    }
                } else {
                    editarFuncionario();
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar parcelas pagas");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void salvarFuncionario() {

        if (JDcDataAdmi.getDate() == null || EdCodFuncionario.getText().equals("") || EdNome.getText().equals("") || EdSetor.getText().equals("") || EdCargo.getText().equals("") || EdCpf.getText().equals("") || EdTurno.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else if (JcTipoSalario.getSelectedItem() == "<Selecione>") {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o tipo de salário!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

        } else {
            try {

                Funcionario f = new Funcionario();
                FuncionarioDao dao = new FuncionarioDao();
                habilitarB(2);
                f.setCodFuncionario(Integer.parseInt(EdCodFuncionario.getText()));
                f.setNrFuncionario(Integer.parseInt(EdNr.getText()));
                f.setNomeFuncionario(EdNome.getText());
                f.setSetorFuncionario(EdSetor.getText());
                f.setCpfFuncionario(EdCpf.getText());
                f.setCargoFuncionario(EdCargo.getText());
                f.setTurnoFuncionario(EdTurno.getText());

                f.setCboCargoFuncionario(EdCbo.getText());

                java.util.Date dateAd = JDcDataAdmi.getDate();
                long dtA = dateAd.getTime();
                java.sql.Date dateAdmi = new java.sql.Date(dtA);
                f.setDataAdmiFuncionario(dateAdmi);

                f.setTipoSalarioFuncionario(JcTipoSalario.getSelectedItem().toString());

                if (JbAdNoturno.isSelected()) {
                    f.setAdcNoturnoFuncionario(1);
                } else {
                    f.setAdcNoturnoFuncionario(0);
                }

                if (JbInsalubri.isSelected()) {
                    f.setInsalubriFuncionario(1);
                } else {
                    f.setInsalubriFuncionario(0);
                }

                if (JbUtiTrans.isSelected()) {
                    f.setUtiliTransFuncionario(1);
                } else {
                    f.setUtiliTransFuncionario(0);
                }

                if (JbUtiliRef.isSelected()) {
                    f.setUtiliRefFuncionario(1);
                } else {
                    f.setUtiliRefFuncionario(0);
                }

                if (JcStatus.getSelectedItem() == "Ativo") {
                    f.setStatusFuncionario(0);
                } else if (JcStatus.getSelectedItem() == "Inativo") {
                    f.setStatusFuncionario(1);
                } else {
                    f.setStatusFuncionario(0);
                }
                String salario = EdSalario.getText().replace(",", ".");
                f.setSalarioFuncionario(Double.parseDouble(salario));
                f.setEmail(EdMail.getText());
                f.setCelular(EdCelular.getText());
                f.setNumResidencial(EdNumeResi.getText());

                dao.createFuncionario(f);
                limpaCampos();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("realizar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void validarCodCadastro() {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncionario.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do usuário", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCod(EdCodFuncionario.getText())) {
                    listaFuncionarioCod();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Usuário não encontrado", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarFuncionario() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        FuncionarioDao pdao = new FuncionarioDao();

        int status = 0;
        if (JbListInat.isSelected()) {
            status = 1;
        }

        for (Funcionario f : pdao.readFuncionarioForStatus(status, EdBuscarNome.getText())) {
            modelo.addRow(new Object[]{
                f.getCodFuncionario(),
                f.getNomeFuncionario(),
                f.getCpfFuncionario(),
                f.getSetorFuncionario(),
                f.getCargoFuncionario(),
                f.getTurnoFuncionario()
            });

        }

    }

    public void listaFuncionarioCod() {

        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncionario.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do usuário", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncionario.getText())) {
                    EdNr.setText(Integer.toString(fu.getNrFuncionario()));
                    EdNome.setText(fu.getNomeFuncionario());
                    EdSetor.setText(fu.getSetorFuncionario());
                    EdCargo.setText(fu.getCargoFuncionario());
                    EdCpf.setText(fu.getCpfFuncionario());
                    EdTurno.setText(fu.getTurnoFuncionario());
                    EdCbo.setText(fu.getCboCargoFuncionario());
                    java.util.Date date = fu.getDataAdmiFuncionario();
                    JDcDataAdmi.setDate(date);
                    EdSalario.setText(Double.toString(fu.getSalarioFuncionario()).replace(".", ","));
                    JcTipoSalario.setSelectedItem(fu.getTipoSalarioFuncionario());

                    int noturno = fu.getAdcNoturnoFuncionario();
                    switch (noturno) {
                        case 0:
                            JbAdNoturno.setSelected(false);
                            break;
                        case 1:
                            JbAdNoturno.setSelected(true);
                            break;
                        default:
                            System.out.println("erro");
                            break;
                    }

                    int insalubri = fu.getInsalubriFuncionario();
                    switch (insalubri) {
                        case 0:
                            JbInsalubri.setSelected(false);
                            break;
                        case 1:
                            JbInsalubri.setSelected(true);
                            break;
                        default:
                            System.out.println("erro");
                            break;
                    }

                    int transp = fu.getUtiliTransFuncionario();
                    switch (transp) {
                        case 0:
                            JbUtiTrans.setSelected(false);
                            break;
                        case 1:
                            JbUtiTrans.setSelected(true);
                            break;
                        default:
                            System.out.println("erro");
                            break;
                    }

                    int ref = fu.getUtiliRefFuncionario();

                    switch (ref) {
                        case 0:
                            JbUtiliRef.setSelected(false);
                            break;
                        case 1:
                            JbUtiliRef.setSelected(true);
                            break;
                        default:
                            System.out.println("erro");
                            break;
                    }

                    int status = fu.getStatusFuncionario();
                    switch (status) {
                        case 0:
                            JcStatus.setSelectedItem("Ativo");
                            break;
                        case 1:
                            JcStatus.setSelectedItem("Inativo");
                            break;
                        default:
                            System.out.println("erro");
                            break;
                    }

                    int statusCad = fu.getStatusFuncionario();
                    if (statusCad == 0) {
                        habilitarB(3);
                    } else if (statusCad == 1) {
                        habilitarB(4);
                    }

                    EdMail.setText(fu.getEmail());
                    EdCelular.setText(fu.getCelular());
                    EdNumeResi.setText(fu.getNumResidencial());

                }

                listaDependentes();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar funcionário");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void listaDependentes() throws ClassNotFoundException {
        try {
            FuncionarioDao dao = new FuncionarioDao();
            DefaultTableModel modelo = (DefaultTableModel) JtListaDepende.getModel();
            modelo.setNumRows(0);
            for (Dependente d : dao.readDependenteForFuncio(Integer.parseInt(EdCodFuncionario.getText()))) {
                modelo.addRow(new Object[]{
                    d.getCodDependente(),
                    d.getNomeDependente(),
                    d.getTipoDependente(),
                    d.getCpfDependente()
                });
            }
        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar dependentes");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void editarFuncionario() throws ClassNotFoundException {

        Funcionario f = new Funcionario();
        FuncionarioDao dao = new FuncionarioDao();

        if (EdNome.getText().equals("") || EdSetor.getText().equals("") || EdCpf.getText().equals("")
                || EdCargo.getText().equals("") || EdNome.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            f.setNrFuncionario(Integer.parseInt(EdNr.getText()));
            f.setNomeFuncionario(EdNome.getText());
            f.setCargoFuncionario(EdCargo.getText());
            f.setSetorFuncionario(EdSetor.getText());
            f.setCpfFuncionario(EdCpf.getText());
            f.setTurnoFuncionario(EdTurno.getText());

            f.setCboCargoFuncionario(EdCbo.getText());

            java.util.Date dateAd = JDcDataAdmi.getDate();
            long dtA = dateAd.getTime();
            java.sql.Date dateAdmi = new java.sql.Date(dtA);
            f.setDataAdmiFuncionario(dateAdmi);
            String salario = EdSalario.getText().replace(",", ".");
            f.setSalarioFuncionario(Double.parseDouble(salario));
            f.setTipoSalarioFuncionario(JcTipoSalario.getSelectedItem().toString());

            if (JbAdNoturno.isSelected()) {
                f.setAdcNoturnoFuncionario(1);
            } else {
                f.setAdcNoturnoFuncionario(0);
            }

            if (JbInsalubri.isSelected()) {
                f.setInsalubriFuncionario(1);
            } else {
                f.setInsalubriFuncionario(0);
            }

            if (JbUtiTrans.isSelected()) {
                f.setUtiliTransFuncionario(1);
            } else {
                f.setUtiliTransFuncionario(0);
            }

            if (JbUtiliRef.isSelected()) {
                f.setUtiliRefFuncionario(1);
            } else {
                f.setUtiliRefFuncionario(0);
            }

            if (JcStatus.getSelectedItem() == "Ativo") {
                f.setStatusFuncionario(0);
            } else if (JcStatus.getSelectedItem() == "Inativo") {
                f.setStatusFuncionario(1);
            } else {
                f.setStatusFuncionario(0);
            }

            f.setCodFuncionario(Integer.parseInt(EdCodFuncionario.getText()));
            if (EdMail.getText().equals("")) {
                f.setEmail(null);
            } else {
                f.setEmail(EdMail.getText());
            }
            f.setCelular(EdCelular.getText());
            f.setNumResidencial(EdNumeResi.getText());

            try {
                dao.updateFuncionario(f);
                limpaCampos();
                listarFuncionario();
                habilitarB(1);
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar cadastro ");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }

    }

    public void deletarFuncionario() throws ClassNotFoundException {
        Funcionario f = new Funcionario();
        FuncionarioDao dao = new FuncionarioDao();
        habilitarB(1);
        if (EdCodFuncionario.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do usuário para excluír", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            f.setCodFuncionario(Integer.parseInt(EdCodFuncionario.getText()));
            try {
                String nome = EdNome.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        " ATENÇÃO!" + "\nDeseja mesmo excluír o cadastro do funcionário  " + nome, "Atenção", JOptionPane.YES_NO_OPTION);

                if (input == 0) {
                    dao.deleteFuncionario(f);
                    habilitarB(1);
                    limpaCampos();
                } else {

                    JOptionPane.showMessageDialog(rootPane, "Exclusão cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro ");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

            listarFuncionario();

        }

    }

    public void updateCloud() {
        try {

            FuncionarioDao dao = new FuncionarioDao();
            Funcionario f = new Funcionario();
            f.setCodFuncionario(Integer.parseInt(EdCodFuncionario.getText()));
            f.setNomeFuncionario(EdNome.getText());
            f.setCpfFuncionario(EdCpf.getText());
            f.setSetorFuncionario(EdSetor.getText());

            if (dao.checkCodCloud(EdCodFuncionario.getText())) {
                dao.updateFuncionarioCloud(f);
            } else {
                dao.createFuncionarioCloud(f);
            }

        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("enviar dados para o cloud");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        BtBuscar = new javax.swing.JButton();
        EdNome = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtExcluir = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        EdSetor = new javax.swing.JTextField();
        EdTurno = new javax.swing.JTextField();
        EdCargo = new javax.swing.JTextField();
        JDcDataAdmi = new com.toedter.calendar.JDateChooser();
        EdCbo = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        JcTipoSalario = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        JbInsalubri = new javax.swing.JCheckBox();
        JbAdNoturno = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        JbUtiliRef = new javax.swing.JCheckBox();
        JbUtiTrans = new javax.swing.JCheckBox();
        EdSalario = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtListaDepende = new javax.swing.JTable();
        EdNomeDepende = new javax.swing.JTextField();
        BtRemovDepend = new javax.swing.JButton();
        BtAdcDepende = new javax.swing.JButton();
        JcTipoDepende = new javax.swing.JComboBox<>();
        EdCpfDepende = new javax.swing.JTextField();
        BtAtualizar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        EdMail = new javax.swing.JTextField();
        EdCelular = new javax.swing.JFormattedTextField();
        EdNumeResi = new javax.swing.JFormattedTextField();
        BtUpdateCloud = new javax.swing.JButton();
        JcStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        EdCodFuncionario = new javax.swing.JFormattedTextField();
        EdNr = new javax.swing.JFormattedTextField();
        EdCpf = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        EdBuscarNome = new javax.swing.JTextField();
        BtBuscarNome = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        JbListInat = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(550, 410));
        jPanel1.setPreferredSize(new java.awt.Dimension(550, 410));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        EdNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(EdNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 360, -1));

        jPanel4.setBackground(new java.awt.Color(216, 216, 216));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 10, -1, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 90, 33));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 100, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 33));

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvarEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 33));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel4.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 33));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 540, 60));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdSetor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdSetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 200, -1));

        EdTurno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turno:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 160, -1));

        EdCargo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        JDcDataAdmi.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataAdmi.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Admissão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(JDcDataAdmi, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 140, -1));

        EdCbo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CBO:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        try {
            EdCbo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        EdCbo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdCboFocusLost(evt);
            }
        });
        jPanel6.add(EdCbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 160, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 520, 100));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JcTipoSalario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "Horista", "Mensalista" }));
        jPanel7.add(JcTipoSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Adcionais:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 10))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JbInsalubri.setText("Insalubri.");
        JbInsalubri.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(JbInsalubri, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        JbAdNoturno.setText("Ad. Noturno");
        jPanel10.add(JbAdNoturno, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 45, -1, 20));

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 120, 80));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descontos:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 10))); // NOI18N
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JbUtiliRef.setText("Utiliza V.R.");
        jPanel11.add(JbUtiliRef, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        JbUtiTrans.setText("Utiliza V.T.");
        jPanel11.add(JbUtiTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, -1, -1));

        jPanel7.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 110, 80));

        EdSalario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salário Base:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdSalario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.###"))));
        EdSalario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdSalarioFocusLost(evt);
            }
        });
        jPanel7.add(EdSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 520, 100));

        jTabbedPane2.addTab("Profissional", jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JtListaDepende.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Nome", "Tipo", "CPF"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JtListaDepende.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtListaDependeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JtListaDepende);
        if (JtListaDepende.getColumnModel().getColumnCount() > 0) {
            JtListaDepende.getColumnModel().getColumn(0).setMinWidth(50);
            JtListaDepende.getColumnModel().getColumn(0).setPreferredWidth(50);
            JtListaDepende.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 540, 150));

        EdNomeDepende.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome dependente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel3.add(EdNomeDepende, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, -1));

        BtRemovDepend.setText("Desativar");
        BtRemovDepend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemovDependActionPerformed(evt);
            }
        });
        jPanel3.add(BtRemovDepend, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        BtAdcDepende.setText("Adicionar");
        BtAdcDepende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcDependeActionPerformed(evt);
            }
        });
        jPanel3.add(BtAdcDepende, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, -1, -1));

        JcTipoDepende.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "Filho", "Cônjuge", "Funcionário" }));
        jPanel3.add(JcTipoDepende, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        EdCpfDepende.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel3.add(EdCpfDepende, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 140, 40));

        BtAtualizar.setText("Atualizar");
        BtAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAtualizarActionPerformed(evt);
            }
        });
        jPanel3.add(BtAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 80, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 10)); // NOI18N
        jLabel2.setText("Tipo:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 8, -1, -1));

        jTabbedPane2.addTab("Dependentes", jPanel3);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdMail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço de E-mail:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel8.add(EdMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, -1));

        EdCelular.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Telefone Celular:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        try {
            EdCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("# ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        EdCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdCelularFocusLost(evt);
            }
        });
        jPanel8.add(EdCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, -1));

        EdNumeResi.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Telefone Residencial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        try {
            EdNumeResi.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        EdNumeResi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdNumeResiFocusLost(evt);
            }
        });
        jPanel8.add(EdNumeResi, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 180, -1));

        BtUpdateCloud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cloud.png"))); // NOI18N
        BtUpdateCloud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtUpdateCloudActionPerformed(evt);
            }
        });
        jPanel8.add(BtUpdateCloud, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));

        jTabbedPane2.addTab("Contatos", jPanel8);

        jPanel1.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 540, 270));

        JcStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "Ativo", "Inativo" }));
        JcStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JcStatusMouseClicked(evt);
            }
        });
        jPanel1.add(JcStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 110, -1));

        jLabel1.setText("Situação:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 13, -1, 20));

        EdCodFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdCodFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("######"))));
        EdCodFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdCodFuncionarioFocusLost(evt);
            }
        });
        EdCodFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncionarioKeyPressed(evt);
            }
        });
        jPanel1.add(EdCodFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        EdNr.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº Registro:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 9))); // NOI18N
        EdNr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("######"))));
        EdNr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EdNrFocusLost(evt);
            }
        });
        jPanel1.add(EdNr, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 110, -1));

        EdCpf.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel1.add(EdCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 160, 40));

        jTabbedPane1.addTab("Cadastrar Funcionário", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        EdBuscarNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscarNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarNomeKeyPressed(evt);
            }
        });

        BtBuscarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNome.setText("Buscar");
        BtBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód.", "Nome", "CPF", "Setor", "Cargo", "Turno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        jLabel9.setText("Dois cliques para editar*");

        JbListInat.setText("Listar Inativos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(EdBuscarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtBuscarNome)
                        .addGap(18, 18, 18)
                        .addComponent(JbListInat)
                        .addGap(58, 58, 58)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdBuscarNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscarNome)
                    .addComponent(JbListInat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listar Funcionário", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        limpaCampos();
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        validarCpfCadastro();
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            deletarFuncionario();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        validarCodCadastro();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeActionPerformed
        try {
            listarFuncionario();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro ");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarNomeActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        limpaCampos();
        if (tabela.getSelectedRow() != -1) {
            jTabbedPane1.setSelectedIndex(0);
            EdCodFuncionario.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            validarCodCadastro();

        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void EdBuscarNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarNomeKeyPressed
        try {
            listarFuncionario();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdBuscarNomeKeyPressed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        try {
            validarCpfEdit();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar CPF");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void JcStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JcStatusMouseClicked

    }//GEN-LAST:event_JcStatusMouseClicked

    private void BtAdcDependeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcDependeActionPerformed
        if (!EdNomeDepende.getText().equals("") || !EdCpfDepende.getText().equals("")) {
            try {
                FuncionarioDao dao = new FuncionarioDao();
                Dependente d = new Dependente();
                if (!dao.checkCpfDependente(EdCpfDepende.getText())) {

                    String dependente = JcTipoDepende.getSelectedItem().toString();

                    if (!dependente.equals("<Selecione>")) {

                        for (Dependente de : dao.readMaiorCodDepende()) {
                            d.setCodDependente(de.getCodDependente() + 1);
                        }

                        d.setNomeDependente(EdNomeDepende.getText());
                        d.setTipoDependente(dependente);
                        d.setFk_codFuncionario(Integer.parseInt(EdCodFuncionario.getText()));
                        d.setCpfDependente(EdCpfDepende.getText());
                        d.setStatusDependente(1);

                        dao.addDependente(d);
                        listaDependentes();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Você deve selecionar um tipo de dependente", "Atenção", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Já existe um dependente com este CPF ativo", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("adicionar dependente ");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_BtAdcDependeActionPerformed

    private void BtRemovDependActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemovDependActionPerformed
        if (JtListaDepende.getSelectedRow() != -1) {
            int input = JOptionPane.showConfirmDialog(null,
                    " ATENÇÃO!" + "\nDeseja mesmo desativar o cadastro do dependente " + EdNomeDepende.getText(), "Atenção", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                try {
                    FuncionarioDao dao = new FuncionarioDao();
                    Dependente d = new Dependente();

                    d.setStatusDependente(0);
                    d.setCodDependente((int) JtListaDepende.getValueAt(JtListaDepende.getSelectedRow(), 0));
                    dao.desativaDependente(d);
                    listaDependentes();
                    EdNomeDepende.setText(null);
                    JcTipoDepende.setSelectedItem("<Selecione>");

                } catch (HeadlessException | ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("remover dependente ");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            } else {

                JOptionPane.showMessageDialog(rootPane, "Selecione na tabela, o dependente que deseja remover", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione na tabela, o dependente que deseja remover", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtRemovDependActionPerformed

    private void EdCodFuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncionarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (EdCodFuncionario.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Você deve informar o código para listar", "Atenção", JOptionPane.WARNING_MESSAGE);
            } else {
                validarCodCadastro();
            }
        }
    }//GEN-LAST:event_EdCodFuncionarioKeyPressed

    private void JtListaDependeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtListaDependeMouseClicked

        EdNomeDepende.setText(JtListaDepende.getValueAt(JtListaDepende.getSelectedRow(), 1).toString());
        String tipo = JtListaDepende.getValueAt(JtListaDepende.getSelectedRow(), 2).toString();
        JcTipoDepende.setSelectedItem(tipo);
        EdCpfDepende.setText(JtListaDepende.getValueAt(JtListaDepende.getSelectedRow(), 3).toString());
        codDependente = JtListaDepende.getValueAt(JtListaDepende.getSelectedRow(), 0).toString();

    }//GEN-LAST:event_JtListaDependeMouseClicked

    private void BtAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAtualizarActionPerformed
        if (!EdNomeDepende.getText().equals("") || !EdCpfDepende.getText().equals("")) {
            try {
                String dependente = JcTipoDepende.getSelectedItem().toString();
                if (!dependente.equals("<Selecione>")) {
                    FuncionarioDao dao = new FuncionarioDao();
                    Dependente d = new Dependente();
                    d.setNomeDependente(EdNomeDepende.getText());
                    d.setCpfDependente(EdCpfDepende.getText());
                    d.setTipoDependente(dependente);
                    d.setCodDependente(Integer.parseInt(codDependente));

                    dao.updateDependente(d);
                    EdNomeDepende.setText("");
                    EdCpfDepende.setText("");
                    JcTipoDepende.setSelectedItem("<Selecione>");
                    listaDependentes();

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("atualizar dependente ");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_BtAtualizarActionPerformed

    private void EdSalarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdSalarioFocusLost
        EdSalario.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdSalarioFocusLost

    private void EdCodFuncionarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdCodFuncionarioFocusLost
        EdCodFuncionario.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdCodFuncionarioFocusLost

    private void EdNrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdNrFocusLost
        EdNr.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdNrFocusLost

    private void EdCboFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdCboFocusLost
        EdCbo.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdCboFocusLost

    private void EdCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdCelularFocusLost
        EdCelular.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdCelularFocusLost

    private void EdNumeResiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EdNumeResiFocusLost
        EdNumeResi.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }//GEN-LAST:event_EdNumeResiFocusLost

    private void BtUpdateCloudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtUpdateCloudActionPerformed
        updateCloud();
    }//GEN-LAST:event_BtUpdateCloudActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAdcDepende;
    private javax.swing.JButton BtAtualizar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtBuscarNome;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtRemovDepend;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JButton BtUpdateCloud;
    private javax.swing.JTextField EdBuscarNome;
    private javax.swing.JTextField EdCargo;
    private javax.swing.JFormattedTextField EdCbo;
    private javax.swing.JFormattedTextField EdCelular;
    private javax.swing.JFormattedTextField EdCodFuncionario;
    private javax.swing.JTextField EdCpf;
    private javax.swing.JTextField EdCpfDepende;
    private javax.swing.JTextField EdMail;
    private javax.swing.JTextField EdNome;
    private javax.swing.JTextField EdNomeDepende;
    private javax.swing.JFormattedTextField EdNr;
    private javax.swing.JFormattedTextField EdNumeResi;
    private javax.swing.JFormattedTextField EdSalario;
    private javax.swing.JTextField EdSetor;
    private javax.swing.JTextField EdTurno;
    private com.toedter.calendar.JDateChooser JDcDataAdmi;
    private javax.swing.JCheckBox JbAdNoturno;
    private javax.swing.JCheckBox JbInsalubri;
    private javax.swing.JCheckBox JbListInat;
    private javax.swing.JCheckBox JbUtiTrans;
    private javax.swing.JCheckBox JbUtiliRef;
    private javax.swing.JComboBox<String> JcStatus;
    private javax.swing.JComboBox<String> JcTipoDepende;
    private javax.swing.JComboBox<String> JcTipoSalario;
    private javax.swing.JTable JtListaDepende;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
