package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDBuscaPro;
import hard.home.view.FDBuscaFuncio;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.CoparticipacaoDao;
import hard.rh.dao.FuncionarioDao;
import hard.rh.dao.ParcelaDao;
import hard.rh.dao.ProcedimentoDao;
import hard.rh.model.Coparticipacao;
import hard.rh.model.Dependente;
import hard.rh.model.Funcionario;
import hard.rh.model.Parcela;
import hard.rh.model.Procedimento;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrContParticipacao extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0706.0";

    private FDBuscaFuncio FDBuscaFuncio;
    private final FDBuscaPro FDBuscaPro;
    public String LbvalorTT;
    FDErroOcorrido fdErroOcorrido;
    int cadastro = 0;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrContParticipacao(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Controle de Coparticipações - V" + versao;

        habilitarB(1);
        EdsNaoVisiveis();
        CbxNPaga.setSelected(true);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrContParticipacao";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText(" folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

        this.FDBuscaPro = new FDBuscaPro(null, true);
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtGerarParcela.setEnabled(false);
                BtGerarParcela.setVisible(true);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtCalcular.setEnabled(false);
                BtCancelar.setEnabled(false);

                //buscar
                EdCodCop.setEnabled(true);
                EdCod.setVisible(false);
                BtBuscarCodFuncio.setEnabled(false);
                JcListaBene.setEnabled(false);
                BtBuscarCodPro.setEnabled(false);
                //funcio

                EdNomeFuncio.setEnabled(false);
                EdCpfFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdNomeFuncio.setEnabled(false);
                EdCodFuncio.setEnabled(false);

                //pro
                EdCargoFuncio.setEnabled(false);
                EdNomePro.setEnabled(false);
                EdLocalPro.setEnabled(false);
                EdMedicoPro.setEnabled(false);
                EdValorPro.setEnabled(false);
                EdCodPro.setEnabled(false);
                EdValorCoparti.setEnabled(false);
                EdValorCoparti.setEditable(false);

                //data
                JDcDataPro.setEnabled(false);
                JDcDataVencPar.setEnabled(false);
                EdParcelaCoparti.setEnabled(false);
                EdResultCopartiParcelado.setEnabled(false);
                EdResultCopartiParcelado.setEditable(false);

                LbStatusGeradaPar.setText("STATUS");
                LbStatusGeradaPar.setForeground(Color.decode("#000000"));
                cadastro = 0;
                EdNomeFuncio.requestFocus();

                break;

            case 2:
                ultimoCodCop();

                EdCod.setVisible(false);

                BtNovo.setEnabled(false);
                BtGerarParcela.setEnabled(true);
                BtGerarParcela.setVisible(true);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtRemoverPro.setEnabled(true);
                BtSalvar.setEnabled(true);
                BtCalcular.setEnabled(true);
                BtCancelar.setEnabled(true);

                //buscar
                BtBuscarCodFuncio.setEnabled(true);
                JcListaBene.setEnabled(true);
                BtBuscarCodPro.setEnabled(true);
                //funcio
                EdCodFuncio.setEnabled(true);
                EdCodCop.setEnabled(true);
                EdNomeFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);

                EdNomeFuncio.setEnabled(true);

                //pro
                EdCargoFuncio.setEnabled(true);
                EdNomePro.setEnabled(true);
                EdLocalPro.setEnabled(true);
                EdMedicoPro.setEnabled(true);
                EdValorPro.setEnabled(true);
                EdCodPro.setEnabled(true);
                EdValorCoparti.setEnabled(true);
                EdValorCoparti.setEditable(false);

                //data
                JDcDataPro.setEnabled(true);
                JDcDataVencPar.setEnabled(true);
                EdParcelaCoparti.setEnabled(true);
                EdResultCopartiParcelado.setEnabled(true);
                EdResultCopartiParcelado.setEditable(false);

                EdNomeFuncio.requestFocus();

                EdCodPro.setEnabled(true);
                BtAdcPro.setEnabled(true);
                BtRemoverPro.setEnabled(true);
                BtGerarParcela.setEnabled(true);
                BtSalvar.setEnabled(true);
                tabelaProcedi.setEnabled(true);
                break;

            case 3:

                EdCod.setVisible(false);

                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(true);
                BtCancelar.setEnabled(true);

                //buscar
                BtBuscarCodFuncio.setEnabled(false);
                BtBuscarCodPro.setEnabled(true);

                //funcio
                EdCodCop.setEnabled(false);
                EdNomeFuncio.setEnabled(true);
                EdCpfFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);

                EdNomeFuncio.setEnabled(true);
                EdCodFuncio.setEnabled(false);

                //pro
                EdCargoFuncio.setEnabled(true);
                EdNomePro.setEnabled(true);
                EdLocalPro.setEnabled(true);
                EdMedicoPro.setEnabled(true);
                EdValorPro.setEnabled(true);
                EdCodPro.setEnabled(true);
                EdValorCoparti.setEnabled(true);
                EdValorCoparti.setEditable(false);

                //data
                JDcDataPro.setEnabled(true);
                JDcDataVencPar.setEnabled(true);
                EdParcelaCoparti.setEnabled(true);
                EdResultCopartiParcelado.setEnabled(true);
                EdResultCopartiParcelado.setEditable(false);
                break;

        }
    }

    public void EdsNaoVisiveis() {
        EdStatusOff.setVisible(false);
        EdCodPar.setVisible(false);
        EdnumMesPar.setVisible(false);
        EdCodBene.setVisible(false);
    }

    public void adicionar() {

        JDcDataPro.setDate(null);
        JDcDataVencPar.setDate(null);
        EdCodPro.setText(null);
        EdNomePro.setText(null);
        EdLocalPro.setText(null);
        EdValorPro.setText(null);
        EdMedicoPro.setText(null);
        EdParcelaCoparti.setText(null);

    }

    public void limpaCampos() {
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        JcListaBene.removeAllItems();
        JcListaBene.addItem("<Selecione>");
        EdCargoFuncio.setText(null);
        EdCpfFuncio.setText(null);
        JDcDataVencPar.setDate(null);
        EdValorCoparti.setText(null);
        EdCodCop.setText(null);
        EdCodPro.setText(null);
        EdNomePro.setText(null);
        EdLocalPro.setText(null);
        EdValorPro.setText(null);
        EdMedicoPro.setText(null);
        EdParcelaCoparti.setText(null);
        EdResultCopartiParcelado.setText(null);
        EdCodBene.setText(null);

    }

    public void limpaCamposPar() {

        EdCodFuncionario.setText(null);
        EdCodParcela.setText(null);
        EdCodCoparticipacao.setText(null);
        EdValorParcela.setText(null);
        EdNomeFuncionario.setText(null);
    }

    public void parcelasGeradas() {
        EdCodPro.setEnabled(false);
        BtAdcPro.setEnabled(false);
        BtRemoverPro.setEnabled(false);
        BtGerarParcela.setEnabled(false);
        BtSalvar.setEnabled(false);
        BtExcluir.setEnabled(false);
        tabelaProcedi.setEnabled(false);
        BtBuscarCodPro.setEnabled(false);
        BtCalcular.setEnabled(false);
        EdNomeFuncio.setEnabled(false);
        EdCpfFuncio.setEnabled(false);
        EdCargoFuncio.setEnabled(false);

        EdNomePro.setEnabled(false);
        EdValorPro.setEnabled(false);
        EdLocalPro.setEnabled(false);
        EdMedicoPro.setEnabled(false);
        JDcDataPro.setEnabled(false);
        EdValorCoparti.setEnabled(false);

        EdParcelaCoparti.setEnabled(false);
        EdResultCopartiParcelado.setEnabled(false);
        JDcDataVencPar.setEnabled(false);
        BtBuscarCodFuncio.setEnabled(false);
        JcListaBene.setEnabled(true);
        BtBuscarCodPro.setEnabled(false);
    }

    public void parcelasNGeradas() {
        EdCodPro.setEnabled(true);
        BtAdcPro.setEnabled(true);
        BtRemoverPro.setEnabled(true);
        BtGerarParcela.setEnabled(true);
        BtSalvar.setEnabled(true);
        BtExcluir.setEnabled(true);
        tabelaProcedi.setEnabled(true);
        BtBuscarCodPro.setEnabled(true);
        BtCalcular.setEnabled(true);
        EdNomeFuncio.setEnabled(true);
        EdCpfFuncio.setEnabled(true);
        EdCargoFuncio.setEnabled(true);

        EdNomePro.setEnabled(true);
        EdValorPro.setEnabled(true);
        EdLocalPro.setEnabled(true);
        EdMedicoPro.setEnabled(true);
        JDcDataPro.setEnabled(true);
        EdValorCoparti.setEnabled(true);
        EdParcelaCoparti.setEnabled(true);
        EdResultCopartiParcelado.setEnabled(true);
        JDcDataVencPar.setEnabled(true);
        BtBuscarCodFuncio.setEnabled(true);
        JcListaBene.setEnabled(true);
        BtBuscarCodPro.setEnabled(true);

    }

    public void ultimoCodCop() {
        CoparticipacaoDao dao = new CoparticipacaoDao();

        try {
            for (Coparticipacao c : dao.readCodCop()) {

                int codCop;
                codCop = c.getCodCop() + 1;
                EdCodCop.setText(Integer.toString(codCop));

            }

        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

//COPARTICIPAÇÃO 
    public void checkCreateCop() throws SQLException, ParseException {

        if (EdCodCop.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "ATENÇÃO\n Não foi gerado um código de coparticipação!");

        } else {
            createCop();
        }
    }

    public void createCop() throws ParseException, SQLException {

        try {
            //coparti-01
            int codBeneficiario = 0;
            Dependente d = (Dependente) JcListaBene.getSelectedItem();
            codBeneficiario = d.getCodDependente();

            Coparticipacao c = new Coparticipacao();
            CoparticipacaoDao dao = new CoparticipacaoDao();
            c.setCodCop(Integer.parseInt(EdCodCop.getText()));
            c.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
            c.setFr_codBeneficiario(codBeneficiario);
            c.setGeradoPar(1);

            dao.createCop(c);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("criar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void checkCreateRealiza() throws SQLException, ParseException {

        if (EdCodPro.getText().equals("") || EdValorPro.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve inserir as informações do procedimento!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

        } else {
            if (JDcDataPro.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Você deve informar a data do procedimento!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                realizaProcedi();
            }

        }
    }

    public void realizaProcedi() throws ParseException, SQLException {
        int codBeneficiario = 0;

        if (EdCodBene.getText().equals("")) {
            if (!JcListaBene.getSelectedItem().equals("<Selecione>")) {
                Dependente d = (Dependente) JcListaBene.getSelectedItem();
                codBeneficiario = d.getCodDependente();
            }
        } else {
            codBeneficiario = Integer.parseInt(EdCodBene.getText());
        }

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Funcionário não informado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else if (codBeneficiario == 0) {
            JOptionPane.showMessageDialog(rootPane, "Beneficiário não informado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Coparticipacao c = new Coparticipacao();
                CoparticipacaoDao dao = new CoparticipacaoDao();

                c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                c.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
                c.setFr_codBeneficiario(codBeneficiario);
                c.setLocalPro(EdLocalPro.getText());
                c.setMedicoPro(EdMedicoPro.getText());
                c.setNfe(Integer.parseInt(EdNF.getText()));

                java.util.Date datePar = JDcDataPro.getDate();
                long dtP = datePar.getTime();
                java.sql.Date dateParcela = new java.sql.Date(dtP);
                c.setDataPro(dateParcela);

                c.setFr_codPro(Integer.parseInt(EdCodPro.getText()));

                try {

                    c.setFr_valorPro(Double.parseDouble(EdValorPro.getText().replace(",", ".")));

                    dao.realizaProcedi(c);

                    adicionar();//LIMPAS OS CAMPOS DE PROCEDIMENTO
                    listarCop();//LISTA OS PROCEDIMENTOS NA TABELA
                } catch (NumberFormatException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("relizar procedimento");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("realizar o procedimento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void validaCop() throws ParseException, SQLException {
        CoparticipacaoDao pdao = new CoparticipacaoDao();

        if (EdCodCop.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Atenção!\nPreencha o código da coparticipação para buscar!");

        } else {
            try {
                if (pdao.checkCreate(EdCodCop.getText())) {
                    listarCop();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Atenção!\nCódigo coparticipação não encontrado");
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("buscar código de coparticipação");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }

    }

    public void listarCop() throws ParseException, SQLException {

        DefaultTableModel modelo = (DefaultTableModel) tabelaProcedi.getModel();
        modelo.setNumRows(0);
        CoparticipacaoDao pdao = new CoparticipacaoDao();

        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        int codDepende = 0;

        try {
            for (Coparticipacao c : pdao.readCopForCod(EdCodCop.getText())) {
                EdCodFuncio.setText(Integer.toString(c.getFr_codFuncionario()));
                EdCodBene.setText(Integer.toString(c.getFr_codBeneficiario()));
                listarFuncioCodCop();
                codDepende = c.getFr_codBeneficiario();
                String valor = Double.toString(c.getFr_valorPro()).replace(".", ",");
                modelo.addRow(new Object[]{
                    c.getCod(),
                    c.getFr_codFuncionario(),
                    c.getFr_nomeFuncionario(),
                    c.getFr_nomeBeneficiario(),
                    c.getFr_codPro(),
                    c.getFr_nomePro(),
                    valor,
                    formatdata.format(c.getDataPro())
                });

                int GeradaParcela = c.getGeradoPar();
                switch (GeradaParcela) {
                    case 1:
                        LbStatusGeradaPar.setText("NÃO GERADA");
                        LbStatusGeradaPar.setForeground(Color.decode("#ff0000"));
                        parcelasNGeradas();
                        break;
                    case 2:
                        LbStatusGeradaPar.setText("GERADA");
                        LbStatusGeradaPar.setForeground(Color.decode("#0f943b"));
                        parcelasGeradas();
                        break;
                    default:
                        JOptionPane.showMessageDialog(rootPane, "Erro");
                        break;
                }

                cadastro = 1;

                buscarValorCop();
                habilitarB(3);
            }

            FuncionarioDao fdao = new FuncionarioDao();
            JcListaBene.removeAllItems();
            for (Dependente d : fdao.readDependenteForCod(codDepende)) {
                JcListaBene.addItem(d);
                JcListaBene.setSelectedIndex(0);
            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void limparTabelaCop() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaProcedi.getModel();
        modelo.setNumRows(0);
    }

    public void buscarValorCop() {
        CoparticipacaoDao pdao = new CoparticipacaoDao();
        try {
            for (Coparticipacao c : pdao.readSumCop(Integer.parseInt(EdCodCop.getText()))) {

                DecimalFormat valorFormata = new DecimalFormat("0.#####");
                String valorFormat = valorFormata.format(c.getValorCop()).replace('.', ',');
                EdValorCoparti.setText(valorFormat);
                buscaNumePar();

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor total da coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void buscaNumePar() {

        ParcelaDao pdao = new ParcelaDao();
        try {
            for (Parcela pa : pdao.readNumParCad(Integer.parseInt(EdCodCop.getText()))) {

                EdParcelaCoparti.setText(Integer.toString(pa.getNumeroDeParcela()));

                if (!EdParcelaCoparti.getText().equals("0")) {
                    calcularParcelas();
                } else {

                }

            }

            for (Parcela pa : pdao.readDataForNumCop(Integer.parseInt(EdCodCop.getText()))) {

                java.util.Date date = pa.getDataVencPar();
                JDcDataVencPar.setDate(date);

                if (!EdParcelaCoparti.getText().equals("0")) {
                    calcularParcelas();
                } else {

                }

            }

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar o número total de parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void calcularParcelas() {
        try {

            int qtdParcela = Integer.parseInt(EdParcelaCoparti.getText());

            if (EdValorCoparti.getText().equals("") || EdParcelaCoparti.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Atenção\nVocê deve preencher o número de parcelas");

            } else if (qtdParcela <= 0) {
                JOptionPane.showMessageDialog(rootPane, "O número de parcelas não pode ser menor que 1", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                EdParcelaCoparti.requestFocus();
            } else {
                try {
                    Double valorParcel = Double.parseDouble(EdValorCoparti.getText().replace(',', '.'));
                    int numeroParcela = Integer.parseInt(EdParcelaCoparti.getText());
                    Double resultado = (valorParcel / numeroParcela);
                    double d = resultado;
                    BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
                    EdResultCopartiParcelado.setText(Double.toString(bd.doubleValue()).replace(".", ","));
                } catch (NumberFormatException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("clcular parcelas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            }
        } catch (HeadlessException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void validaParcela() throws ClassNotFoundException {
        if (EdParcelaCoparti.getText().equals("") || EdValorCoparti.getText().equals("") || EdResultCopartiParcelado.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Para gerar a parcela você deve preencher todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else if (JDcDataVencPar.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher a data de vencimento da parcela!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            gerarParcela();
        }
    }

    //RECEBE DADOS DO FRONT E ENVIA PARA O DAO REALIZAR O INSERT NO BANCO DE DADOS
    public void gerarParcela() throws ClassNotFoundException {
        int par = 0;
        int parP = Integer.parseInt(EdParcelaCoparti.getText());
        Parcela p = new Parcela();
        ParcelaDao dao = new ParcelaDao();

        while (par < parP) {
            int codBeneficiario = 0;

            if (EdCodBene.getText().equals("")) {
                Dependente d = (Dependente) JcListaBene.getSelectedItem();
                codBeneficiario = d.getCodDependente();
            } else {
                codBeneficiario = Integer.parseInt(EdCodBene.getText());
            }

            int codFuncio = Integer.parseInt(EdCodFuncio.getText());
            p.setFr_codCop(Integer.parseInt(EdCodCop.getText()));
            p.setFr_codFuncionario(codFuncio);
            p.setFr_codBeneficiario(codBeneficiario);
            p.setFr_nomeFuncionario(EdNomeFuncio.getText());
            p.setValorParcela(Double.parseDouble(EdResultCopartiParcelado.getText().replace(",", ".")));

            java.util.Date GerardateVencPar = JDcDataVencPar.getDate();
            long dtVP = GerardateVencPar.getTime();
            java.sql.Date GerardateVencParcela = new java.sql.Date(dtVP);
            p.setDataVencPar(GerardateVencParcela);
            p.setStatusParcela(1);

            try {
                dao.createParcela(p);
                geradoParcela();

                BtNovo.setEnabled(true);
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("gerar parcelas");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

            EdnumMesPar.setText(Integer.toString(par));
            gerarUpdateDataParcela();
            par++;
        }
        limparTabelaCop();
        limpaCampos();
        habilitarB(1);
        JOptionPane.showMessageDialog(rootPane, "Parcelas geradas com sucesso" + "\nTotal geradas: " + par, "Salvo com sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public void gerarUpdateDataParcela() throws ClassNotFoundException {
        ParcelaDao pdao = new ParcelaDao();

        try {
            for (Parcela p : pdao.readMaxCodPar()) {

                int codPar;
                codPar = p.getCodParcela();
                EdCodPar.setText(Integer.toString(codPar));
            }

        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("gerar parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

        Parcela pa = new Parcela();
        ParcelaDao dao = new ParcelaDao();

        pa.setCodParcela(Integer.parseInt(EdCodPar.getText()));
        pa.setNumMesParcela(Integer.parseInt(EdnumMesPar.getText()));
        try {
            dao.updateDateParcela(pa);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar datas parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void geradoParcela() throws ClassNotFoundException {
        ParcelaDao pdao = new ParcelaDao();
        Coparticipacao cop = new Coparticipacao();

        cop.setGeradoPar(2);
        cop.setCodCop(Integer.parseInt(EdCodCop.getText()));

        try {
            pdao.updateGeradoParcela(cop);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar status parcelas geradas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void delatadoParcela() throws ClassNotFoundException {
        ParcelaDao pdao = new ParcelaDao();
        Coparticipacao cop = new Coparticipacao();

        cop.setGeradoPar(1);
        cop.setCodCop(Integer.parseInt(EdCodCoparticipacao.getText()));

        try {
            pdao.updateGeradoParcela(cop);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void listar() {

        CoparticipacaoDao pdao = new CoparticipacaoDao();

        if (EdCodCop.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código!");
        } else {
            try {
                for (Coparticipacao c : pdao.readCopForCod(EdCodCop.getText())) {

                    EdCodCop.setText(Integer.toString(c.getCodCop()));
                    EdCodFuncio.setText(Integer.toString(c.getFr_codFuncionario()));
                    EdNomeFuncio.setText(c.getFr_nomeFuncionario());
                    EdCpfFuncio.setText(c.getFr_cpfFuncionario());
                    EdCargoFuncio.setText(c.getFr_cargoFuncionario());

                    EdCodPro.setText(Integer.toString(c.getFr_codPro()));
                    EdNomePro.setText(c.getFr_nomePro());
                    EdValorPro.setText(Double.toString(c.getFr_valorPro()));
                    EdParcelaCoparti.setText(Integer.toString(c.getFr_parcelaPro()));
                    EdLocalPro.setText(c.getLocalPro());
                    EdMedicoPro.setText(c.getMedicoPro());
                    JDcDataPro.setDate(c.getDataPro());
                    JDcDataVencPar.setDate(c.getDataVencPro());

                    habilitarB(3);
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar coparticipação");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }

    }

    public void editarCop() throws ParseException {
        Coparticipacao c = new Coparticipacao();
        CoparticipacaoDao dao = new CoparticipacaoDao();

        if (EdNomeFuncio.getText().equals("")
                || EdNomeFuncio.getText().equals("")
                || EdCpfFuncio.getText().equals("")
                || EdCargoFuncio.getText().equals("")
                || EdCodPro.getText().equals("")
                || EdNomePro.getText().equals("")
                || EdNomePro.getText().equals("")
                || EdValorPro.getText().equals("")
                || EdParcelaCoparti.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "ATENÇÃO\n Você deve preencher todos os campos");
        } else {

            try {
                c.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
                c.setFr_nomeFuncionario(EdNomeFuncio.getText());
                c.setFr_cpfFuncionario(EdCpfFuncio.getText());
                c.setFr_cargoFuncionario(EdCargoFuncio.getText());
                c.setFr_codPro(Integer.parseInt(EdCodPro.getText()));
                c.setFr_nomePro(EdNomePro.getText());
                c.setFr_valorPro(Double.parseDouble(EdValorPro.getText()));
                c.setFr_parcelaPro(Integer.parseInt(EdParcelaCoparti.getText()));
                c.setLocalPro(EdLocalPro.getText());
                c.setMedicoPro(EdMedicoPro.getText());

                java.util.Date datePar = JDcDataPro.getDate();
                long dtP = datePar.getTime();
                java.sql.Date dateParcela = new java.sql.Date(dtP);
                c.setDataPro(dateParcela);

                java.util.Date dateVencPar = JDcDataVencPar.getDate();
                long dtVP = dateVencPar.getTime();
                java.sql.Date dateVencParcela = new java.sql.Date(dtVP);
                c.setDataVencPro(dateVencParcela);

                c.setCodCop(Integer.parseInt(EdCodCop.getText()));

                dao.update(c);
                limpaCampos();
                habilitarB(1);

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("editar coparticipação");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }
    }

    public void editNGeradoParcela() throws ClassNotFoundException {
        ParcelaDao pdao = new ParcelaDao();
        Coparticipacao cop = new Coparticipacao();

        cop.setGeradoPar(1);
        cop.setCodCop(Integer.parseInt(EdCodCoparticipacao.getText()));

        try {
            pdao.updateGeradoParcela(cop);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("alterar status parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void deletarCop() throws ClassNotFoundException {
        Coparticipacao c = new Coparticipacao();
        CoparticipacaoDao dao = new CoparticipacaoDao();

        if (dao.checkCreatePro(EdCodCop.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Você deve revomer os precedimentos lançados para deletar a coparticipação!");
        } else {

            if (EdCodCop.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Informe o código da coparticipação para excluír");
            } else {
                c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                try {

                    String nomepro = EdNomePro.getText();
                    String nome = EdNomeFuncio.getText();

                    int input = JOptionPane.showConfirmDialog(null,
                            " ATENÇÃO!" + "\nDeseja mesmo excluír a ficha de coparticipação do : " + nomepro + "\nCadastrado na conta do funcionário: " + nome + "?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (input == 0) {
                        dao.delete(c);
                        habilitarB(1);
                        limpaCampos();

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Exclusão cancelada");

                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("deletar coparticipação");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            }

        }

    }

    public void cancelarCop() throws ClassNotFoundException {
        try {

            Coparticipacao c = new Coparticipacao();
            CoparticipacaoDao dao = new CoparticipacaoDao();

            if (LbStatusGeradaPar.getText().equals("GERADA")) {
                habilitarB(1);
                limpaCampos();
                limparTabelaCop();
            } else {

                int input = JOptionPane.showConfirmDialog(rootPane, "Deseja cancelar o lançamento?!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    if (!dao.checkCreatePro(EdCodCop.getText())) {
                        c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                        dao.delete(c);
                        habilitarB(1);
                        limpaCampos();
                        limparTabelaCop();

                    } else {
                        int cop = Integer.parseInt(EdCodCop.getText());
                        JOptionPane.showMessageDialog(rootPane, "As parcelas para a coparticipação (" + cop + ") NÃO FORAM GERADAS.\nVocê pode acessar novamente com o código de coparticipação para gerar as parcelas.", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                        habilitarB(1);
                        limpaCampos();
                        limparTabelaCop();

                    }
                }
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cancelar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void removerProcedimento() throws ParseException, SQLException {
        Coparticipacao c = new Coparticipacao();
        CoparticipacaoDao dao = new CoparticipacaoDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione o procedimento na tabela para remover da ficha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            c.setCod(Integer.parseInt(EdCod.getText()));
            try {
                dao.deleteForCod(c);
                listarCop();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("remover procedimento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }

    }

    public void buscaCodFuncio() throws ParseException, SQLException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do funcionário!");
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Funcionário não encontrado", "Atenção", JOptionPane.WARNING_MESSAGE);

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("buscar código funcionário");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void validarCodCadastro() throws ParseException, SQLException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do funcionário", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Funcionário não encontrado");

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();
        try {
            for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
                EdNomeFuncio.setText(fu.getNomeFuncionario());
                EdCargoFuncio.setText(fu.getCargoFuncionario());
                EdCpfFuncio.setText(fu.getCpfFuncionario());
            }

            for (Dependente d : pdao.readDependenteForFuncio(Integer.parseInt(EdCodFuncio.getText()))) {
                JcListaBene.addItem(d);
            }

            BtBuscarCodFuncio.setEnabled(false);

        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar funcionário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listarFuncioCodCop() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();
        try {
            for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
                EdNomeFuncio.setText(fu.getNomeFuncionario());
                EdCargoFuncio.setText(fu.getCargoFuncionario());
                EdCpfFuncio.setText(fu.getCpfFuncionario());
            }
            BtBuscarCodFuncio.setEnabled(false);

        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar funcionário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void validarCodList() throws SQLException, ParseException {
        ProcedimentoDao pdao = new ProcedimentoDao();
        String cod = EdCodPro.getText();

        if (!EdCodBene.getText().equals("")) {
            if (cadastro == 0) {
                checkCreateCop();
                cadastro++;
            }

            if (EdCodPro.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Informe o código do procedimento", "Atenção", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    if (pdao.checkCod(EdCodPro.getText())) {

                        listaProCod();

                    } else {
                        JOptionPane.showMessageDialog(rootPane, cod + "Procedimento não encontrado", "Não encontrado", JOptionPane.WARNING_MESSAGE);

                    }
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("validar procedimento");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Beneficiário não encontrado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void listaProCod() throws ClassNotFoundException {
        try {

            ProcedimentoDao pdao = new ProcedimentoDao();

            for (Procedimento p : pdao.readProForCod(Integer.parseInt(EdCodPro.getText()))) {
                EdNomePro.setText(p.getNomePro());
                EdValorPro.setText(Double.toString(p.getValorPro()).replace(".", ","));

            }
        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar procedimento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void deletarParcela() throws ParseException {
        Parcela p = new Parcela();
        ParcelaDao dao = new ParcelaDao();

        if (EdCodParcela.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe o código da parcela para excluír", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            p.setCodParcela(Integer.parseInt(EdCodParcela.getText()));
            try {

                String valor = EdValorParcela.getText();
                String codFuncio = EdCodFuncionario.getText();

                int input = JOptionPane.showConfirmDialog(rootPane,
                        "Deseja mesmo excluír o procedimento " + valor + "\nCadastrado na conta do funcionário Nº: " + codFuncio + "?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    dao.delete(p);
                    delatadoParcela();
                    editNGeradoParcela();
                    habilitarB(1);
                    limpaCamposPar();
                    listarParcelas();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Exclusão cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar parcela");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }
    }

    public void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaParcelas.getModel();
        modelo.setNumRows(0);
    }

    public void listarParcelas() throws ParseException, ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) tabelaParcelas.getModel();
        modelo.setNumRows(0);
        ParcelaDao dao = new ParcelaDao();
        DecimalFormat df = new DecimalFormat("R$ ##.##");
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        // BUSCA TODAS AS PARCELAS PAGAS
        if (EdBuscarCod.getText().equals("")) {
            if (CbxPaga.isSelected()) {

                try {
                    for (Parcela p : dao.readParForStatusPaga()) {

                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }

                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }
                } catch (ClassNotFoundException ex) {

                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas pagas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

                //BUSCA TODAS AS PARCELAS NÃO PAGAS
            } else if (CbxNPaga.isSelected()) {
                try {
                    for (Parcela p : dao.readParForStatusNPaga()) {

                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }

                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas não pagas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } else if (CbxFiltroDatas.isSelected()) {

                if (JdcFiltraUltiParInicial.getDate() == null & JdcFiltraUltiParFinal.getDate() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Você deve informar as datas do filtro!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                } else {
                    java.util.Date dataIni = JdcFiltraUltiParInicial.getDate();
                    long dtInicial = dataIni.getTime();
                    java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                    java.util.Date dataFin = JdcFiltraUltiParFinal.getDate();
                    long dtFinal = dataFin.getTime();
                    java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);
                    try {
                        for (Parcela p : dao.readParForDataAndNPaga(dateParcelaInicial, dateParcelaFinal)) {
                            String status = "erro";
                            int diasAtraso = p.getDiasAtrasoPar() * -1;
                            int statusParcela = p.getStatusParcela();

                            if (statusParcela == 1 && diasAtraso >= 0) {
                                status = "EM ABERTO";
                            } else if (statusParcela == 1 && diasAtraso < 0) {
                                status = "VENCIDA";
                            } else {
                                status = "PAGA";
                            }

                            modelo.addRow(new Object[]{
                                p.getCodParcela(),
                                p.getFr_codCop(),
                                p.getFr_codFuncionario(),
                                p.getFr_nomeFuncionario(),
                                p.getFr_nomeBeneficiario(),
                                df.format(p.getValorParcela()),
                                formatdata.format(p.getDataVencPar()),
                                p.getDiasAtrasoPar() * -1,
                                p.getStatusParcela(),
                                status});
                        }

                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("listar parcelas por data");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }
            } else if (CbxFiltroDatas.isSelected() & CbxPaga.isSelected()) {
                if (JdcFiltraUltiParInicial.getDate() == null & JdcFiltraUltiParFinal.getDate() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Você deve informar as datas do filtro!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                } else {
                    java.util.Date dataIni = JdcFiltraUltiParInicial.getDate();
                    long dtInicial = dataIni.getTime();
                    java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                    java.util.Date dataFin = JdcFiltraUltiParFinal.getDate();
                    long dtFinal = dataFin.getTime();
                    java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);
                    try {

                        for (Parcela p : dao.readParForDataAndPaga(dateParcelaInicial, dateParcelaFinal)) {
                            String status = "erro";
                            int diasAtraso = p.getDiasAtrasoPar() * -1;
                            int statusParcela = p.getStatusParcela();

                            if (statusParcela == 1 && diasAtraso >= 0) {
                                status = "EM ABERTO";
                            } else if (statusParcela == 1 && diasAtraso < 0) {
                                status = "VENCIDA";
                            } else {
                                status = "PAGA";
                            }

                            modelo.addRow(new Object[]{
                                p.getCodParcela(),
                                p.getFr_codCop(),
                                p.getFr_codFuncionario(),
                                p.getFr_nomeFuncionario(),
                                p.getFr_nomeBeneficiario(),
                                df.format(p.getValorParcela()),
                                formatdata.format(p.getDataVencPar()),
                                p.getDiasAtrasoPar() * -1,
                                p.getStatusParcela(),
                                status});
                        }
                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("listar parcelas pagas por data");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }
            } else if (CbxFiltroDatas.isSelected() & CbxNPaga.isSelected()) {
                if (JdcFiltraUltiParInicial.getDate() == null & JdcFiltraUltiParFinal.getDate() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Você deve informar as datas do filtro!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                } else {
                    java.util.Date dataIni = JdcFiltraUltiParInicial.getDate();
                    long dtInicial = dataIni.getTime();
                    java.sql.Date dateParcelaInicial = new java.sql.Date(dtInicial);

                    java.util.Date dataFin = JdcFiltraUltiParFinal.getDate();
                    long dtFinal = dataFin.getTime();
                    java.sql.Date dateParcelaFinal = new java.sql.Date(dtFinal);
                    try {

                        for (Parcela p : dao.readParForDataAndNPaga(dateParcelaInicial, dateParcelaFinal)) {

                            String status = "erro";
                            int diasAtraso = p.getDiasAtrasoPar() * -1;
                            int statusParcela = p.getStatusParcela();

                            if (statusParcela == 1 && diasAtraso >= 0) {
                                status = "EM ABERTO";
                            } else if (statusParcela == 1 && diasAtraso < 0) {
                                status = "VENCIDA";
                            } else {
                                status = "PAGA";
                            }
                            modelo.addRow(new Object[]{
                                p.getCodParcela(),
                                p.getFr_codCop(),
                                p.getFr_codFuncionario(),
                                p.getFr_nomeFuncionario(),
                                p.getFr_nomeBeneficiario(),
                                df.format(p.getValorParcela()),
                                formatdata.format(p.getDataVencPar()),
                                p.getDiasAtrasoPar() * -1,
                                p.getStatusParcela(),
                                status});
                        }
                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("listar parcelas não pagas por data");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }
            } else {
                try {

                    for (Parcela p : dao.readParcela()) {

                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }
                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }
                } catch (ClassNotFoundException ex) {

                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        } else if (!EdBuscarCod.getText().equals("")) {
            if (CbxPaga.isSelected()) {
                try {
                    for (Parcela p : dao.readParForUserAndStatusPaga(EdBuscarCod.getText())) {
                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }
                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }
                } catch (ClassNotFoundException ex) {

                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas por funcionário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } else if (CbxNPaga.isSelected()) {
                try {
                    for (Parcela p : dao.readParForUserAndStatusNPaga(EdBuscarCod.getText())) {
                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }

                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas por funcionário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } else {
                try {
                    for (Parcela p : dao.readParForCod(EdBuscarCod.getText())) {
                        String status = "erro";
                        int diasAtraso = p.getDiasAtrasoPar() * -1;
                        int statusParcela = p.getStatusParcela();

                        if (statusParcela == 1 && diasAtraso >= 0) {
                            status = "EM ABERTO";
                        } else if (statusParcela == 1 && diasAtraso < 0) {
                            status = "VENCIDA";
                        } else {
                            status = "PAGA";
                        }

                        modelo.addRow(new Object[]{
                            p.getCodParcela(),
                            p.getFr_codCop(),
                            p.getFr_codFuncionario(),
                            p.getFr_nomeFuncionario(),
                            p.getFr_nomeBeneficiario(),
                            df.format(p.getValorParcela()),
                            formatdata.format(p.getDataVencPar()),
                            p.getDiasAtrasoPar() * -1,
                            p.getStatusParcela(),
                            status});

                    }
                } catch (ClassNotFoundException ex) {

                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar parcelas por funcionário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }

        vaParcela();
    }

    public void vaParcela() {
        tabelaParcelas.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelcted, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object texto = tabelaParcelas.getValueAt(row, 9);
                if (texto == "VENCIDA") {
                    label.setForeground(Color.RED);
                    label.setBackground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });
    }

    public void pagarParcela() throws ParseException {

        if (tabelaParcelas.getSelectedRow() != -1) {
            Parcela p = new Parcela();
            ParcelaDao dao = new ParcelaDao();

            if (EdStatusOff.getText().equals("2")) {
                JOptionPane.showMessageDialog(rootPane, "Está parcela já está paga!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

            } else {
                p.setStatusParcela(2);
                p.setCodParcela(Integer.parseInt(EdCodParcela.getText()));
                try {
                    String valor = EdValorParcela.getText();
                    String nomeFuncio = EdNomeFuncionario.getText();
                    String numeroPar = EdCodParcela.getText();
                    int input = JOptionPane.showConfirmDialog(rootPane,
                            " Deseja mesmo realizar o pagamento da parcela Nº " + numeroPar + " no valor de: R$ " + valor
                            + "\n Cadastrada na conta do funcionário: " + nomeFuncio + " ?"
                            + "\n", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (input == 0) {
                        dao.updateParcela(p);
                        listarParcelas();
                        limpaCampos();
                        habilitarB(1);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Pagamento cancelado!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("pagar parcela");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        }
    }

    public void removerPargamento() throws ParseException {

        if (tabelaParcelas.getSelectedRow() != -1) {
            Parcela p = new Parcela();
            ParcelaDao dao = new ParcelaDao();

            if (EdStatusOff.getText().equals("1")) {
                JOptionPane.showMessageDialog(rootPane, "Está parcela ainda não foi paga!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

            } else if (Integer.parseInt(EdDiasEmAtraso.getText()) < -60) {
                JOptionPane.showMessageDialog(rootPane, "Você não pode reabrir parcelas com pagamento realizado a mais de 60 dias", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {

                p.setStatusParcela(1);
                p.setCodParcela(Integer.parseInt(EdCodParcela.getText()));
                try {
                    String valor = EdValorParcela.getText();
                    String nomeFuncio = EdNomeFuncionario.getText();
                    String numeroPar = EdCodParcela.getText();
                    int input = JOptionPane.showConfirmDialog(rootPane, "Deseja mesmo remover o pagamento da parcela Nº " + numeroPar + " no valor de: R$ " + valor
                            + "\n Cadastrada na conta do funcionário: " + nomeFuncio + " ?"
                            + "\n", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (input == 0) {
                        dao.updateParcela(p);
                        listarParcelas();
                        limpaCampos();
                        habilitarB(1);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Atualização cancelada!", "Atenção", JOptionPane.WARNING_MESSAGE);

                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("remover pagamento de parcela");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        }
    }

    public void listaDataVencTabela() throws ClassNotFoundException {
        try {

            ParcelaDao dao = new ParcelaDao();
            for (Parcela p : dao.readParcelaForCod(Integer.parseInt(EdCodParcela.getText()))) {
                JdcDatavenc.setDate(p.getDataVencPar());

            }
        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar data vencimento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        EdCodFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncio = new javax.swing.JButton();
        EdNomeFuncio = new javax.swing.JTextField();
        EdCargoFuncio = new javax.swing.JTextField();
        EdCpfFuncio = new javax.swing.JFormattedTextField();
        JcListaBene = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        EdValorCoparti = new javax.swing.JTextField();
        EdParcelaCoparti = new javax.swing.JTextField();
        EdResultCopartiParcelado = new javax.swing.JTextField();
        JDcDataVencPar = new com.toedter.calendar.JDateChooser();
        BtGerarParcela = new javax.swing.JButton();
        BtCalcular = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        EdCodCop = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaProcedi = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        BtBuscar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        EdCodPro = new javax.swing.JTextField();
        BtBuscarCodPro = new javax.swing.JButton();
        EdNomePro = new javax.swing.JTextField();
        EdMedicoPro = new javax.swing.JTextField();
        EdValorPro = new javax.swing.JTextField();
        EdLocalPro = new javax.swing.JTextField();
        BtRemoverPro = new javax.swing.JButton();
        BtAdcPro = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        EdCod = new javax.swing.JTextField();
        EdCodPar = new javax.swing.JTextField();
        EdnumMesPar = new javax.swing.JTextField();
        LbStatusGeradaPar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        EdCodBene = new javax.swing.JTextField();
        JDcDataPro = new com.toedter.calendar.JDateChooser();
        EdNF = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaParcelas = new javax.swing.JTable();
        BtBuscarCodFuncio1 = new javax.swing.JButton();
        EdBuscarCod = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        EdCodParcela = new javax.swing.JTextField();
        EdCodFuncionario = new javax.swing.JTextField();
        EdCodCoparticipacao = new javax.swing.JTextField();
        EdNomeFuncionario = new javax.swing.JTextField();
        EdNomeBeneficiario = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        BtExcluir1 = new javax.swing.JButton();
        BtCancelar1 = new javax.swing.JButton();
        BtSair1 = new javax.swing.JButton();
        EdStatusOff = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        JdcDatavenc = new com.toedter.calendar.JDateChooser();
        EdValorParcela = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        LbstatusParcela = new javax.swing.JLabel();
        EdDiasEmAtraso = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        JdcFiltraUltiParInicial = new com.toedter.calendar.JDateChooser();
        JdcFiltraUltiParFinal = new com.toedter.calendar.JDateChooser();
        jPanel14 = new javax.swing.JPanel();
        CbxPaga = new javax.swing.JCheckBox();
        CbxNPaga = new javax.swing.JCheckBox();
        CbxFiltroDatas = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 33));

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
        jPanel4.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 78, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, 33));

        jLabel11.setText("Configurações das parcelas:");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
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
        jPanel6.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, -1));

        BtBuscarCodFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncio.setText("Buscar");
        BtBuscarCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(BtBuscarCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdNomeFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 320, -1));

        EdCargoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel6.add(EdCargoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 190, -1));

        EdCpfFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        try {
            EdCpfFuncio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel6.add(EdCpfFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 130, -1));

        JcListaBene.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>" }));
        JcListaBene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcListaBeneActionPerformed(evt);
            }
        });
        jPanel6.add(JcListaBene, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, 320, -1));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel1.setText("Beneficiário:");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 130, -1, -1));

        jLabel16.setText("Funcionário:");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdValorCoparti.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor Total:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jPanel7.add(EdValorCoparti, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        EdParcelaCoparti.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Número de Parcelas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        EdParcelaCoparti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdParcelaCopartiKeyPressed(evt);
            }
        });
        jPanel7.add(EdParcelaCoparti, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, -1));

        EdResultCopartiParcelado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor Parcela:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        EdResultCopartiParcelado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdResultCopartiParceladoKeyPressed(evt);
            }
        });
        jPanel7.add(EdResultCopartiParcelado, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 120, -1));

        JDcDataVencPar.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataVencPar.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel7.add(JDcDataVencPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 130, -1));

        BtGerarParcela.setText("GERAR");
        BtGerarParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtGerarParcelaActionPerformed(evt);
            }
        });
        jPanel7.add(BtGerarParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 80, 40));

        BtCalcular.setText("Calcular");
        BtCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCalcularActionPerformed(evt);
            }
        });
        jPanel7.add(BtCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 10, 80, 40));

        jLabel12.setText("Data: ");

        EdCodCop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Coparticipação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodCop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodCopKeyPressed(evt);
            }
        });

        tabelaProcedi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cód. Funcio", "Funcionário", "Beneficiário", "Cód. Procedi.", "Procedimento", "Valor", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProcedi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProcediMouseClicked(evt);
            }
        });
        tabelaProcedi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaProcediKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaProcedi);
        if (tabelaProcedi.getColumnModel().getColumnCount() > 0) {
            tabelaProcedi.getColumnModel().getColumn(1).setMinWidth(0);
            tabelaProcedi.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabelaProcedi.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jLabel15.setText("Dados do Procedimento:");

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCodPro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Procedimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodProActionPerformed(evt);
            }
        });
        EdCodPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodProKeyPressed(evt);
            }
        });
        jPanel8.add(EdCodPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, -1));

        BtBuscarCodPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodPro.setText("Buscar");
        BtBuscarCodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodProActionPerformed(evt);
            }
        });
        jPanel8.add(BtBuscarCodPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 90, -1));

        EdNomePro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdNomePro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdNomeProActionPerformed(evt);
            }
        });
        jPanel8.add(EdNomePro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, -1));

        EdMedicoPro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Médico:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.add(EdMedicoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 150, -1));

        EdValorPro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.add(EdValorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, -1));

        EdLocalPro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Local:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.add(EdLocalPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 270, -1));

        BtRemoverPro.setBackground(new java.awt.Color(255, 102, 102));
        BtRemoverPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_white.png"))); // NOI18N
        BtRemoverPro.setText("Remover");
        BtRemoverPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemoverProActionPerformed(evt);
            }
        });

        BtAdcPro.setBackground(new java.awt.Color(0, 153, 0));
        BtAdcPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_white.png"))); // NOI18N
        BtAdcPro.setText("Adcionar");
        BtAdcPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcProActionPerformed(evt);
            }
        });

        jLabel17.setText("Ficha de procedimentos: ");

        LbStatusGeradaPar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LbStatusGeradaPar.setText("STATUS");

        jLabel2.setText("Status Parcela:");

        JDcDataPro.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataPro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Procedimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        EdNF.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº NF-e:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(EdCodCop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(BtBuscar))
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JDcDataPro, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(EdNF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtAdcPro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(BtRemoverPro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
            .addComponent(jScrollPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addGap(120, 120, 120))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel17)
                        .addGap(211, 211, 211)
                        .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(EdCodPar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(EdnumMesPar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EdCodBene, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel11)
                        .addGap(371, 371, 371)
                        .addComponent(jLabel2)
                        .addGap(7, 7, 7)
                        .addComponent(LbStatusGeradaPar)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EdCodCop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtBuscar))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JDcDataPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EdNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtAdcPro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtRemoverPro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodPar, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdnumMesPar, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodBene, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(LbStatusGeradaPar))))
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Controle de Procedimentos", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tabelaParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Par.", "Cod. Cop.", "Cod. Funcionário", "Funcionário", "Beneficiário", "Valor Parcela", "Data Vencimento", "Dias Vencimento", "StatusInt", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaParcelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaParcelasMouseClicked(evt);
            }
        });
        tabelaParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaParcelasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaParcelas);
        if (tabelaParcelas.getColumnModel().getColumnCount() > 0) {
            tabelaParcelas.getColumnModel().getColumn(7).setMinWidth(0);
            tabelaParcelas.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabelaParcelas.getColumnModel().getColumn(7).setMaxWidth(0);
            tabelaParcelas.getColumnModel().getColumn(8).setMinWidth(0);
            tabelaParcelas.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabelaParcelas.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        BtBuscarCodFuncio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncio1.setText("Buscar");
        BtBuscarCodFuncio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncio1ActionPerformed(evt);
            }
        });

        EdBuscarCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdBuscarCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdBuscarCodActionPerformed(evt);
            }
        });
        EdBuscarCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscarCodKeyPressed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdCodParcela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Parcela:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel11.add(EdCodParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        EdCodFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodFuncionarioActionPerformed(evt);
            }
        });
        jPanel11.add(EdCodFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 120, -1));

        EdCodCoparticipacao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Coparticipação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel11.add(EdCodCoparticipacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 130, -1));

        EdNomeFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel11.add(EdNomeFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 370, -1));

        EdNomeBeneficiario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Beneficiário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel11.add(EdNomeBeneficiario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 370, -1));

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir1.setText("EXCLUÍR");
        BtExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluir1ActionPerformed(evt);
            }
        });
        jPanel10.add(BtExcluir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 33));

        BtCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar1.setText("CANCELAR");
        BtCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelar1ActionPerformed(evt);
            }
        });
        jPanel10.add(BtCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 120, 33));

        BtSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair1.setText("SAIR");
        BtSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSair1ActionPerformed(evt);
            }
        });
        jPanel10.add(BtSair1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 78, 33));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdcDatavenc.setBackground(new java.awt.Color(255, 255, 255));
        JdcDatavenc.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel12.add(JdcDatavenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 130, -1));

        EdValorParcela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor Parcela:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdValorParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdValorParcelaActionPerformed(evt);
            }
        });
        jPanel12.add(EdValorParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 130, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/money.png"))); // NOI18N
        jButton1.setText("Pagar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 100, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove_money.png"))); // NOI18N
        jButton2.setText("Remover");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 100, 30));

        LbstatusParcela.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 12)); // NOI18N
        LbstatusParcela.setText("STATUS");
        jPanel12.add(LbstatusParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));

        EdDiasEmAtraso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dias Vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel12.add(EdDiasEmAtraso, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 100, -1));

        jLabel26.setText("Status do Pagamento: ");
        jPanel12.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JdcFiltraUltiParInicial.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraUltiParInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel13.add(JdcFiltraUltiParInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        JdcFiltraUltiParFinal.setBackground(new java.awt.Color(255, 255, 255));
        JdcFiltraUltiParFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel13.add(JdcFiltraUltiParFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 140, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CbxPaga.setBackground(new java.awt.Color(255, 255, 255));
        CbxPaga.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        CbxPaga.setText("PAGAS");
        CbxPaga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CbxPagaMouseClicked(evt);
            }
        });
        CbxPaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxPagaActionPerformed(evt);
            }
        });
        jPanel14.add(CbxPaga, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, 30));

        CbxNPaga.setBackground(new java.awt.Color(255, 255, 255));
        CbxNPaga.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        CbxNPaga.setText("NÃO PAGAS");
        CbxNPaga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CbxNPagaMouseClicked(evt);
            }
        });
        CbxNPaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxNPagaActionPerformed(evt);
            }
        });
        jPanel14.add(CbxNPaga, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        CbxFiltroDatas.setBackground(new java.awt.Color(255, 255, 255));
        CbxFiltroDatas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        CbxFiltroDatas.setText("NÃO PAGAS POR DATA");
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
        jPanel14.add(CbxFiltroDatas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(290, 290, 290)
                                .addComponent(BtBuscarCodFuncio1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(EdStatusOff, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(200, 200, 200)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(410, 410, 410)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(410, 410, 410)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(EdBuscarCod, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(BtBuscarCodFuncio1)
                        .addGap(20, 20, 20)
                        .addComponent(EdStatusOff, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(EdBuscarCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Controle Parcelas", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtAdcProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcProActionPerformed
        if (!EdNF.getText().equals("")) {
            try {
                checkCreateRealiza();
            } catch (ParseException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("check_create_realiza");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar o número da nota fiscal de cobrança", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtAdcProActionPerformed

    private void BtRemoverProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemoverProActionPerformed
        try {
            removerProcedimento();
        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("revome_pro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtRemoverProActionPerformed

    private void EdNomeProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdNomeProActionPerformed

    }//GEN-LAST:event_EdNomeProActionPerformed

    private void BtBuscarCodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodProActionPerformed

        if (EdCodBene.getText().equals("")) {
            try {

                Dependente d = (Dependente) JcListaBene.getSelectedItem();
                EdCodBene.setText(Integer.toString(d.getCodDependente()));

                if (EdCodPro.getText().equals("")) {
                    this.FDBuscaPro.setVisible(true);

                    this.EdCodPro.setText(String.valueOf(this.FDBuscaPro.getCodPro()));
                    if (this.EdCodPro.getText().equals("0")) {
                        EdCodPro.setText("");

                    } else {
                        try {
                            validarCodList();
                        } catch (SQLException | ParseException ex) {
                            System.out.println(ex);
                        }

                    }

                } else {
                    try {
                        validarCodList();
                    } catch (SQLException | ParseException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("buscar procedimento");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o beneficiário", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            if (EdCodPro.getText().equals("")) {
                this.FDBuscaPro.setVisible(true);

                this.EdCodPro.setText(String.valueOf(this.FDBuscaPro.getCodPro()));
                if (this.EdCodPro.getText().equals("0")) {
                    EdCodPro.setText("");

                } else {
                    try {
                        validarCodList();
                    } catch (SQLException | ParseException ex) {
                        System.out.println(ex);
                    }
                }
            } else {
                try {
                    validarCodList();
                } catch (SQLException | ParseException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("validar procedimento");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_BtBuscarCodProActionPerformed

    private void EdCodProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodProKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                validarCodList();
            } catch (SQLException | ParseException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_EdCodProKeyPressed

    private void EdCodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodProActionPerformed

    }//GEN-LAST:event_EdCodProActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            validaCop();
        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("valida_cop");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void tabelaProcediKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaProcediKeyPressed

    }//GEN-LAST:event_tabelaProcediKeyPressed

    private void tabelaProcediMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProcediMouseClicked
        if (tabelaProcedi.getSelectedRow() != -1) {
            jTabbedPane1.setSelectedIndex(0);
            EdCod.setText(tabelaProcedi.getValueAt(tabelaProcedi.getSelectedRow(), 0).toString());

            habilitarB(3);

        }
    }//GEN-LAST:event_tabelaProcediMouseClicked

    private void BtGerarParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtGerarParcelaActionPerformed
        try {
            validaParcela();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("valida_par");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtGerarParcelaActionPerformed

    private void EdResultCopartiParceladoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdResultCopartiParceladoKeyPressed

    }//GEN-LAST:event_EdResultCopartiParceladoKeyPressed

    private void EdParcelaCopartiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdParcelaCopartiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (EdParcelaCoparti.getText().equals("")) {

            } else {
                calcularParcelas();
            }
        }
    }//GEN-LAST:event_EdParcelaCopartiKeyPressed

    private void EdNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdNomeFuncioActionPerformed

    }//GEN-LAST:event_EdNomeFuncioActionPerformed

    private void BtBuscarCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncioActionPerformed
        JcListaBene.removeAllItems();
        JcListaBene.addItem("<Selecione>");
        this.FDBuscaFuncio = new FDBuscaFuncio(null, true);

        if (EdCodFuncio.getText().equals("")) {
            this.FDBuscaFuncio.setVisible(true);

            this.EdCodFuncio.setText(String.valueOf(this.FDBuscaFuncio.getCodFuncio()));

            if (this.EdCodFuncio.getText().equals("0")) {
                EdCodFuncio.setText("");

            } else {
                try {
                    validarCodCadastro();
                } catch (ParseException | SQLException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("buscar código funcionário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        } else {

            try {
                validarCodCadastro();
            } catch (ParseException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }//GEN-LAST:event_BtBuscarCodFuncioActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                validarCodCadastro();
            } catch (ParseException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
            EdCodPro.requestFocus();
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void EdCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncioActionPerformed

    }//GEN-LAST:event_EdCodFuncioActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        ParcelaDao pdao = new ParcelaDao();
        CoparticipacaoDao dao = new CoparticipacaoDao();
        Coparticipacao c = new Coparticipacao();

        try {
            if (pdao.checkBtSalvarPro(EdCodCop.getText())) {

                if (pdao.checkParcelaCreate(EdCodCop.getText())) {

                    habilitarB(1);
                    limpaCampos();
                    limparTabelaCop();

                } else {
                    int codCop = Integer.parseInt(EdCodCop.getText());
                    int input = JOptionPane.showConfirmDialog(null,
                            "Não foi criada parcela para essa coparticipação!\nA coparticipação de código " + codCop + " irá permanecer aberta aguardando a geração das parcelas", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (input == 0) {
                        c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                        habilitarB(1);
                        limpaCampos();
                        limparTabelaCop();

                    }
                }

            } else {
                int input = JOptionPane.showConfirmDialog(null,
                        "Não há procedimentos lançados! O lançamento da Coparticipação será cancelada!\nDeseja excluír a coparticipação?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                    dao.delete(c);
                    habilitarB(1);
                    limpaCampos();
                    limparTabelaCop();

                } else {

                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar procedimento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        Coparticipacao c = new Coparticipacao();
        CoparticipacaoDao dao = new CoparticipacaoDao();

        if (LbStatusGeradaPar.getText().equals("GERADA")) {
            dispose();
        } else {

            if (EdCodCop.getText().equals("")) {
                dispose();
            } else {
                int input = JOptionPane.showConfirmDialog(rootPane, "Deseja cancelar o lançamento?!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);

                if (input == 0) {
                    try {
                        //! verifica se não tem procedimento cadastrado com código da coparticipação
                        if (!dao.checkCreatePro(EdCodCop.getText())) {
                            c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                            dao.delete(c);
                            dispose();

                        } else {
                            int cop = Integer.parseInt(EdCodCop.getText());
                            JOptionPane.showMessageDialog(rootPane, "As parcelas para a coparticipação (" + cop + ") NÃO FORAM GERADAS.\nVocê pode acessar novamente com o código de coparticipação para gerar as parcelas.", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                            dispose();

                        }

                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("cancelar ou deletar coparticipação");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }
                }
            }
        }

    }//GEN-LAST:event_BtSairActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        try {
            cancelarCop();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cancelar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCodFuncio.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {
            deletarCop();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCalcularActionPerformed
        if (EdValorCoparti.getText().equals("") && EdParcelaCoparti.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Valor total ou número de parcelas não informados", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            calcularParcelas();
        }
    }//GEN-LAST:event_BtCalcularActionPerformed

    private void EdCodCopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodCopKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (EdCodCop.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Você deve informar o código da coparticipação", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    validaCop();
                } catch (ParseException | SQLException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("validar coparticipação");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }
            }
        }
    }//GEN-LAST:event_EdCodCopKeyPressed

    private void tabelaParcelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaParcelasMouseClicked

        if (tabelaParcelas.getSelectedRow() != -1) {
            jTabbedPane1.setSelectedIndex(1);

            EdCodParcela.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 0).toString());
            EdCodCoparticipacao.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 1).toString());
            EdCodFuncionario.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 2).toString());
            EdNomeFuncionario.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 3).toString());
            EdNomeBeneficiario.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 4).toString());
            EdValorParcela.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 5).toString());
            EdDiasEmAtraso.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 7).toString());

            try {
                DefaultTableModel model = (DefaultTableModel) tabelaParcelas.getModel();
                int seectedRow = tabelaParcelas.getSelectedRow();

                listaDataVencTabela();

                int diasAtraso;
                diasAtraso = Integer.parseInt(EdDiasEmAtraso.getText());
                int status;
                status = Integer.parseInt(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 8).toString());

                if (status == 1 && diasAtraso >= 0) {
                    LbstatusParcela.setText("EM ABERTO");
                    LbstatusParcela.setForeground(Color.decode("#000000"));

                } else if (status == 1 && diasAtraso < 0) {
                    LbstatusParcela.setText("VENCIDA");
                    LbstatusParcela.setForeground(Color.decode("#ff0000"));
                } else {
                    LbstatusParcela.setText("PAGA");
                    LbstatusParcela.setForeground(Color.decode("#0f943b"));
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar parcelas");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        }
    }//GEN-LAST:event_tabelaParcelasMouseClicked

    private void tabelaParcelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaParcelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            try {
                deletarParcela();

            } catch (ParseException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar parcela");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }//GEN-LAST:event_tabelaParcelasKeyPressed

    private void BtBuscarCodFuncio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncio1ActionPerformed
        try {
            listarParcelas();

        } catch (ParseException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarCodFuncio1ActionPerformed

    private void EdBuscarCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdBuscarCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdBuscarCodActionPerformed

    private void EdBuscarCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscarCodKeyPressed
        try {
            listarParcelas();
        } catch (ParseException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdBuscarCodKeyPressed

    private void EdCodFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncionarioActionPerformed

    }//GEN-LAST:event_EdCodFuncionarioActionPerformed

    private void BtExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluir1ActionPerformed
        try {
            deletarParcela();

        } catch (ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtExcluir1ActionPerformed

    private void BtCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelar1ActionPerformed
        limpaCamposPar();
        limparTabela();
    }//GEN-LAST:event_BtCancelar1ActionPerformed

    private void BtSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSair1ActionPerformed
        dispose();
    }//GEN-LAST:event_BtSair1ActionPerformed

    private void CbxPagaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxPagaMouseClicked

    }//GEN-LAST:event_CbxPagaMouseClicked

    private void CbxPagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxPagaActionPerformed
        CbxNPaga.setSelected(false);
    }//GEN-LAST:event_CbxPagaActionPerformed

    private void CbxNPagaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxNPagaMouseClicked

    }//GEN-LAST:event_CbxNPagaMouseClicked

    private void CbxNPagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxNPagaActionPerformed
        CbxPaga.setSelected(false);
        CbxFiltroDatas.setSelected(false);
    }//GEN-LAST:event_CbxNPagaActionPerformed

    private void EdValorParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdValorParcelaActionPerformed

    }//GEN-LAST:event_EdValorParcelaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            pagarParcela();

        } catch (ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("pagar parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            removerPargamento();

        } catch (ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("romover pagamento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void CbxFiltroDatasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CbxFiltroDatasMouseClicked

    }//GEN-LAST:event_CbxFiltroDatasMouseClicked

    private void CbxFiltroDatasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxFiltroDatasActionPerformed
        CbxNPaga.setSelected(false);
    }//GEN-LAST:event_CbxFiltroDatasActionPerformed

    private void JcListaBeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcListaBeneActionPerformed

    }//GEN-LAST:event_JcListaBeneActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if (!EdCodCop.getText().equals("")) {
            CoparticipacaoDao dao = new CoparticipacaoDao();
            Coparticipacao c = new Coparticipacao();
            try {
                if (!dao.checkCreatePro(EdCodCop.getText())) {
                    c.setCodCop(Integer.parseInt(EdCodCop.getText()));
                    dao.delete(c);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar ao fechar");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAdcPro;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtBuscarCodFuncio;
    private javax.swing.JButton BtBuscarCodFuncio1;
    private javax.swing.JButton BtBuscarCodPro;
    private javax.swing.JButton BtCalcular;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtCancelar1;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtExcluir1;
    private javax.swing.JButton BtGerarParcela;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtRemoverPro;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSair1;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JCheckBox CbxFiltroDatas;
    private javax.swing.JCheckBox CbxNPaga;
    private javax.swing.JCheckBox CbxPaga;
    private javax.swing.JTextField EdBuscarCod;
    private javax.swing.JTextField EdCargoFuncio;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdCodBene;
    private javax.swing.JTextField EdCodCop;
    private javax.swing.JTextField EdCodCoparticipacao;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodFuncionario;
    private javax.swing.JTextField EdCodPar;
    private javax.swing.JTextField EdCodParcela;
    private javax.swing.JTextField EdCodPro;
    private javax.swing.JFormattedTextField EdCpfFuncio;
    private javax.swing.JTextField EdDiasEmAtraso;
    private javax.swing.JTextField EdLocalPro;
    private javax.swing.JTextField EdMedicoPro;
    private javax.swing.JTextField EdNF;
    private javax.swing.JTextField EdNomeBeneficiario;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdNomeFuncionario;
    private javax.swing.JTextField EdNomePro;
    private javax.swing.JTextField EdParcelaCoparti;
    private javax.swing.JTextField EdResultCopartiParcelado;
    private javax.swing.JTextField EdStatusOff;
    private javax.swing.JTextField EdValorCoparti;
    private javax.swing.JTextField EdValorParcela;
    private javax.swing.JTextField EdValorPro;
    private javax.swing.JTextField EdnumMesPar;
    private com.toedter.calendar.JDateChooser JDcDataPro;
    private com.toedter.calendar.JDateChooser JDcDataVencPar;
    private javax.swing.JComboBox<Object> JcListaBene;
    private com.toedter.calendar.JDateChooser JdcDatavenc;
    private com.toedter.calendar.JDateChooser JdcFiltraUltiParFinal;
    private com.toedter.calendar.JDateChooser JdcFiltraUltiParInicial;
    private javax.swing.JLabel LbStatusGeradaPar;
    private javax.swing.JLabel LbstatusParcela;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabelaParcelas;
    private javax.swing.JTable tabelaProcedi;
    // End of variables declaration//GEN-END:variables
}
