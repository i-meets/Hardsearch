package hard.rh.view;

import hard.home.view.FDBuscaEvento;
import hard.config.ConnectionFactory;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.EventoDao;
import hard.rh.dao.FolhaDao;
import hard.rh.dao.FuncionarioDao;
import hard.rh.dao.ParcelaDao;
import hard.rh.model.Evento;
import hard.rh.model.Folha;
import hard.rh.model.Funcionario;
import hard.rh.model.Parcela;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrGeraFolha extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;
    private FDListaFuncioFolha fDListaFuncioFolha;
    private FDBuscaEvento FDBuscaEvento;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    public FrGeraFolha(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        title = "Geração de Folha - V" + versao;

        btsNVisiveis();
        habilitarB(1);
        listaFolhas();

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrGeraFolha";
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

    public void internalFrameClosing(InternalFrameEvent e) {

        int op = JOptionPane.showInternalConfirmDialog(rootPane, "Quer mesmo fechar essa janela?", "Fechar Janela",
                JOptionPane.YES_NO_OPTION);

        if (op == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 4, (d.height - this.getSize().height) / 4);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:

                //folha
                EdCodFicha.setEnabled(true);
                EdCodFicha.setEditable(true);
                JDcDataFolha.setEnabled(true);

                //funcionario
                JpFuncio.setEnabled(false);
                EdCodFuncio.setEnabled(false);
                EdNrFuncio.setEnabled(false);

                EdNomeFuncio.setEnabled(false);
                EdSalarioBase.setEnabled(false);
                EdTipoSalario.setEnabled(false);

                //Evento
                EdCodEve.setEnabled(false);
                EdDescriEve.setEnabled(false);
                EdTipoEve.setEnabled(false);
                EdTipoProcessaEve.setEnabled(false);
                EdConteEve.setEnabled(false);

                LbCalcSalario.setVisible(false);

                //botões
                BtBuscarFolha.setEnabled(true);
                BtBuscarCodFuncio.setEnabled(false);
                BtBuscarEve.setEnabled(false);
                BtBuscarParcelas.setEnabled(false);

                BtAdcEvento.setEnabled(false);
                BtRemoverEvento.setEnabled(false);
                BtAdcEventoPar.setEnabled(false);
                BtRemEventoPar.setEnabled(false);

                BtCancelar.setEnabled(false);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(false);

                break;

            case 2:
                ultimoCodFolha();

                //folha
                EdCodFicha.setEnabled(true);
                JDcDataFolha.setEnabled(true);

                //funcionario
                EdCodFuncio.setEditable(true);
                JpFuncio.setEnabled(true);
                EdCodFuncio.setEnabled(true);
                EdNrFuncio.setEnabled(true);
                EdNomeFuncio.setEnabled(true);
                EdSalarioBase.setEnabled(true);
                EdTipoSalario.setEnabled(true);

                //Evento
                EdCodEve.setEnabled(false);
                EdDescriEve.setEnabled(false);
                EdTipoEve.setEnabled(false);
                EdTipoProcessaEve.setEnabled(false);
                EdConteEve.setEnabled(false);

                //botões
                BtBuscarFolha.setEnabled(false);
                BtBuscarCodFuncio.setEnabled(true);
                BtBuscarEve.setEnabled(false);
                BtBuscarParcelas.setEnabled(false);

                BtAdcEvento.setEnabled(false);
                BtRemoverEvento.setEnabled(false);
                BtAdcEventoPar.setEnabled(false);
                BtRemEventoPar.setEnabled(false);

                BtCancelar.setEnabled(true);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(false);

                break;

            case 3:

                //Evento
                EdCodEve.setEnabled(true);
                EdDescriEve.setEnabled(true);
                EdTipoEve.setEnabled(true);
                EdTipoProcessaEve.setEnabled(true);
                EdConteEve.setEnabled(true);

                //funcionario
                EdCodFuncio.setEditable(false);
                EdNrFuncio.setEnabled(false);

                //botões
                BtBuscarCodFuncio.setEnabled(false);

                BtBuscarEve.setEnabled(true);
                BtBuscarParcelas.setEnabled(true);

                BtAdcEvento.setEnabled(true);
                BtRemoverEvento.setEnabled(true);
                BtAdcEventoPar.setEnabled(true);
                BtRemEventoPar.setEnabled(true);

                BtCancelar.setEnabled(true);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(false);

                break;

            case 4:

                //folha
                EdCodFicha.setEditable(false);
                JDcDataFolha.setEnabled(false);

                //funcionario
                EdCodFuncio.setEnabled(false);
                EdNrFuncio.setEnabled(false);
                EdNomeFuncio.setEnabled(false);
                EdSalarioBase.setEnabled(false);
                EdTipoSalario.setEnabled(false);

                //botões
                BtBuscarCodFuncio.setEnabled(false);

                BtCancelar.setEnabled(true);
                BtExcluir.setEnabled(true);
                BtSalvar.setEnabled(true);

                break;

        }
    }

    public void adicionar() {
        EdCodEve.setText(null);
        EdDescriEve.setText(null);
        EdTipoEve.setText(null);
        EdTipoProcessaEve.setText(null);
        EdConteEve.setText(null);

    }

    public void limpaCampos() {
        EdCodFuncio.setText(null);
        EdNrFuncio.setText(null);
        EdNomeFuncio.setText(null);
        EdTipoSalario.setText(null);
        EdSalarioBase.setText(null);

        EdCodFicha.setText(null);
        EdDescriEve.setText(null);
        EdTipoEve.setText(null);
        EdTipoProcessaEve.setText(null);
        EdConteEve.setText(null);

    }

    private void btsNVisiveis() {
        EdCodRelFolha.setVisible(false);
        EdCodRelFolha.setEditable(false);
        EdCod.setVisible(false);
        EdCod.setEditable(false);
        EdValor.setVisible(false);
        EdValor.setEditable(false);

    }

    public void listaFolhas() throws ClassNotFoundException {

        FolhaDao dao = new FolhaDao();
        DefaultTableModel modelo = (DefaultTableModel) tbListaFolhas.getModel();
        modelo.setNumRows(0);

        for (Folha f : dao.listFolhas()) {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            modelo.addRow(new Object[]{
                f.getDataFolha(),
                date.format(f.getDataFolha()),
                f.getNumFichas(),
                f.getStatusFolha()
            });

        }

    }

    public void ultimoCodFolha() {
        FolhaDao dao = new FolhaDao();

        try {
            for (Folha c : dao.readCodFolha()) {

                int codFolha;
                codFolha = c.getCodFolha() + 1;
                EdCodFicha.setText(Integer.toString(codFolha));

            }

        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void validarCodCadastro() throws ParseException, SQLException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário!");
        } else if (JDcDataFolha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Informe a data da folha!");
        } else {

            try {
                if (pdao.checkCodAndStatusAtivo(EdCodFuncio.getText())) {
                    listarFuncioCod();
                    checkCreateFolha();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Cadastro do funcionário inativo ou inexistente", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText(" folha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void checkCreateFolha() throws SQLException, ParseException, ClassNotFoundException {

        if (EdCodFicha.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO\n Não foi gerado um código de coparticipação!");

        } else {
            FolhaDao fdao = new FolhaDao();

            if (fdao.checkCreateFolhaForFuncioStatusOpen(Integer.parseInt(EdCodFuncio.getText()))) {

                JOptionPane.showMessageDialog(rootPane, "______________________________________________________\n\n"
                        + "Existe folha em aberto para o funcionário.\nNão é permitido lançar nova folha sem a anterior estar baixada.\n"
                        + "______________________________________________________", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

            } else {
                createFolha();

            }

        }

    }

    public void createFolha() throws ParseException, SQLException {

        try {

            Folha c = new Folha();
            FolhaDao dao = new FolhaDao();
            c.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
            c.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));

            java.util.Date dateFolha = JDcDataFolha.getDate();
            long dtF = dateFolha.getTime();

            java.sql.Date dataF = new java.sql.Date(dtF);
            c.setDataFolha(dataF);

            dao.createFolha(c);
            listarParcelas();
            habilitarB(3);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("criar ficha para folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void checkCreateRealiza() throws SQLException, ParseException {

        if (EdCodFicha.getText().equals("") || EdCodEve.getText().equals("") || EdConteEve.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve inserir todas as informações", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

        } else {

            realizaEvento();

        }
    }

    public void checkCreateRealizaAdcPar() throws SQLException, ParseException {

        if (EdCodFicha.getText().equals("") || EdValor.getText().equals("") || EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma parcela na tabela", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

        } else {
            realizaEventoAdcParcela();

        }
    }

    public void realizaEvento() throws ParseException, SQLException {

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Funcionário não informado!", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        } else {

            Folha fo = new Folha();
            FolhaDao dao = new FolhaDao();
            fo.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
            fo.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
            fo.setFr_codEvento(Integer.parseInt(EdCodEve.getText()));
            fo.setFr_tipoSalarioFuncionario(EdTipoSalario.getText());
            String conteudo = EdConteEve.getText();
            fo.setConteudoFolha(Double.parseDouble(conteudo.replace(",", ".")));

            try {
                dao.realizaFolha(fo);
                habilitarB(4);
                adicionar();
                listarEvento();
                EdCodEve.requestFocus();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("realizar lançamento do evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }

    }

    public void realizaEventoAdcParcela() throws ParseException, SQLException {

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Funcionário não informado!", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        } else {

            Folha fo = new Folha();
            FolhaDao dao = new FolhaDao();
            fo.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
            fo.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));
            fo.setFr_codEvento(340);
            fo.setFr_tipoSalarioFuncionario(EdTipoSalario.getText());
            String conteudo = EdValor.getText().replaceAll(",", ".");
            fo.setConteudoFolha(Double.parseDouble(conteudo));
            fo.setFr_codParcela(Integer.parseInt(EdCod.getText()));

            try {
                dao.realizaFolha(fo);
                updateStatusParPaga();
                adicionar();
                listarEvento();
                listarParcelas();
                EdCod.setText("");
                EdValor.setText("");

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("realizar lançamento da folha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }

    }

    public void listarEvento() throws ParseException, SQLException {

        DefaultTableModel modelo = (DefaultTableModel) tabelaEventos.getModel();
        modelo.setNumRows(0);
        FolhaDao edao = new FolhaDao();

        try {
            for (Folha f : edao.readFolhaForCod(EdCodFicha.getText())) {
                String conteudo = f.getConteudoFolha().toString().replace(".", ",");
                modelo.addRow(new Object[]{
                    f.getCodRelFolha(),
                    f.getFr_nrFuncionario(),
                    f.getFr_nomeFuncionario(),
                    f.getFr_tipoSalarioFuncionario(),
                    f.getFr_codEvento(),
                    f.getFr_descriEvento(),
                    f.getFr_tipoEvento(),
                    f.getFr_tipoProcessaEvento(),
                    conteudo,
                    f.getFr_codParcela()

                });

                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(f.getDataFolha().toString());
                JDcDataFolha.setDate(date);

                EdCodFuncio.setText(Integer.toString(f.getFr_codFuncionario()));
                listarFuncioCod();
                habilitarB(3);
                habilitarB(4);

            }

        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar eventos da ficha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void geradoFolha() throws ClassNotFoundException {
        FolhaDao fdao = new FolhaDao();
        Folha f = new Folha();

        f.setStatusFolha(2);
        f.setDataFolha((Date) tbListaFolhas.getValueAt(tbListaFolhas.getSelectedRow(), 0));

        try {
            fdao.updateGeradoFolha(f);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("alterar status da folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }

    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();

        for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNrFuncio.setText(Integer.toString(fu.getNrFuncionario()));
            EdNomeFuncio.setText(fu.getNomeFuncionario());
            EdTipoSalario.setText(fu.getTipoSalarioFuncionario());
            EdSalarioBase.setText(Double.toString(fu.getSalarioFuncionario()).replace(".", ","));
            EdCodEve.requestFocus();

        }
    }

    public void validarCodList() {
        EventoDao pdao = new EventoDao();
        String cod = EdCodEve.getText();

        if (EdCodEve.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do Evento!");
        } else {

            try {
                if (pdao.checkCod(EdCodEve.getText())) {

                    listaEveCod();
                } else {
                    JOptionPane.showMessageDialog(null, cod + " Evento não encontrado");

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código do evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    public void listaEveCod() {

        EventoDao pdao = new EventoDao();

        try {
            for (Evento eve : pdao.readEveForCod(EdCodEve.getText())) {
                EdDescriEve.setText(eve.getDescriEvento());
                EdTipoEve.setText(eve.getTipoEvento());
                EdTipoProcessaEve.setText(eve.getTipoProcessaEvento());
                EdConteEve.requestFocus();

                if (EdCodEve.getText().equals("1") || EdCodEve.getText().equals("11")) {
                    calcular();
                } else {
                    EdConteEve.setText("");
                }

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar evento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void deleteEvento() throws ParseException, SQLException {

        FolhaDao dao = new FolhaDao();
        Folha f = new Folha();

        if (EdCodRelFolha.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecionar o procedimento para remover da ficha");
        } else {
            f.setCodRelFolha(Integer.parseInt(EdCodRelFolha.getText()));
            try {
                dao.deleteEvento(f);

                if (!EdCod.getText().equals("")) {
                    updateStatusParNPaga();
                }
                listarParcelas();
                listarEvento();

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar evento");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }

    }

    public void deleteFolha() throws ParseException, SQLException {

        FolhaDao dao = new FolhaDao();
        Folha f = new Folha();

        if (EdCodFicha.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve informar o número da folha");
        } else {
            f.setCodRelFolha(Integer.parseInt(EdCodRelFolha.getText()));
            try {

                dao.deleteEvento(f);
                habilitarB(1);

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar folha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }

        }

    }

    public void updateStatusParPaga() throws ParseException, ClassNotFoundException {

        if (JtListaPar.getSelectedRow() != -1) {
            Parcela p = new Parcela();
            ParcelaDao dao = new ParcelaDao();

            p.setStatusParcela(2);
            p.setCodParcela(Integer.parseInt(EdCod.getText()));

            dao.updateParcela(p);
            EdCod.setText("");
            EdValor.setText("");
        }
    }

    public void updateStatusParNPaga() throws ParseException, ClassNotFoundException {

        Parcela p = new Parcela();
        ParcelaDao dao = new ParcelaDao();

        p.setStatusParcela(1);
        p.setCodParcela(Integer.parseInt(EdCod.getText()));

        dao.updateParcela(p);
        EdCod.setText("");
        EdValor.setText("");

    }

    public void cancelarFolha() throws ClassNotFoundException {

        Folha c = new Folha();
        FolhaDao dao = new FolhaDao();

        int input = JOptionPane.showConfirmDialog(rootPane, "Deseja cancelar o lançamento?!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            if (!dao.checkCreateFolha(EdCodFicha.getText())) {
                c.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
                dao.delete(c);
                habilitarB(1);
                limpaCampos();
                limparTabelaEventos();

            } else {
                String nome = EdNomeFuncio.getText();
                String folha = EdCodFicha.getText();
                JOptionPane.showMessageDialog(rootPane, "A Folha: " + folha + " para o funcionário: " + nome + " permanecerá em aberto.\nPara deletar a folha você deve remover os eventos", "Folha não deletada", JOptionPane.WARNING_MESSAGE);
                habilitarB(1);
                limpaCampos();
                limparTabelaEventos();
            }
        }

    }

    public void limparTabelaEventos() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaEventos.getModel();
        modelo.setNumRows(0);
    }

    public void listarParcelas() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) JtListaPar.getModel();
        modelo.setNumRows(0);
        ParcelaDao dao = new ParcelaDao();
//        DecimalFormat df = new DecimalFormat("##.##");
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

        for (Parcela p : dao.readParForUserAndStatusNPagaForFolha(Integer.parseInt(EdCodFuncio.getText()))) {
            String valor = Double.toString(p.getValorParcela()).replace(".", ",");
            modelo.addRow(new Object[]{
                p.getCodParcela(),
                valor,
                formatdata.format(p.getDataVencPar())});
        }
    }

    public void calcular() {
        if (EdTipoSalario.getText().equals("Horista")) {

            double valorDia = 7.33;
            int dTrab = 0;
            int dRepo = 0;
            double rSaBase = 0;
            double rRepo = 0;
            double somaHora = 0;
            dTrab = Integer.parseInt(EdDiasTrab.getText());

            dRepo = Integer.parseInt(EdDiasRepo.getText());

            rRepo = dRepo * valorDia;

            rSaBase = dTrab * valorDia;
            if (rSaBase == 190.58) {

                if (dTrab + dRepo > 30) {
                    rSaBase = 190.58;
                } else {
                    rSaBase = 190.68;
                }
            }

            double ds = rSaBase;
            BigDecimal bdRB = new BigDecimal(ds).setScale(2, RoundingMode.HALF_EVEN);

            double dr = rRepo;
            BigDecimal bdRR = new BigDecimal(dr).setScale(2, RoundingMode.HALF_EVEN);

            switch (EdCodEve.getText()) {
                case "1":
                    EdConteEve.setText(Double.toString(bdRB.doubleValue()).replace(".", ","));
                    break;
                case "11":
                    EdConteEve.setText(Double.toString(bdRR.doubleValue()).replace(".", ","));
                    break;
                default:
                    break;
            }

            EdRTrab.setText(Double.toString(bdRB.doubleValue()).replace(".", ","));
            EdRepouso.setText(Double.toString(bdRR.doubleValue()).replace(".", ","));

            somaHora = rSaBase + rRepo;

            double dsoma = somaHora;
            BigDecimal bdSoma = new BigDecimal(dsoma).setScale(2, RoundingMode.HALF_EVEN);

            if (somaHora > 220) {
                EdSomaHora.setText(Double.toString(bdSoma.doubleValue()).replace(".", ","));
            } else {
                EdSomaHora.setText(Double.toString(bdSoma.doubleValue()).replace(".", ","));
            }
            if (!EdSalarioBase.getText().equals("")) {
                double salaBase = Double.parseDouble(EdSalarioBase.getText().replace(",", "."));
                double cal = dsoma * salaBase;
                BigDecimal bdCal = new BigDecimal(cal).setScale(2, RoundingMode.HALF_EVEN);
                LbCalcSalario.setText("R$ " + Double.toString(bdCal.doubleValue()).replace(".", ","));
                LbCalcSalario.setVisible(true);
            }
        } else {
            LbCalcSalario.setText("MENSALISTA");
            LbCalcSalario.setVisible(true);
        }
    }

    public void gerarFolha() {

        try {

            Connection con = ConnectionFactory.getConnection();
            Map<String, Object> map = new HashMap<>();

            java.util.Date dataFin = JDcDataFiltro.getDate();
            long dtFinal = dataFin.getTime();
            java.sql.Date dateFiltroFinal = new java.sql.Date(dtFinal);
            int status = Integer.parseInt(tbListaFolhas.getValueAt(tbListaFolhas.getSelectedRow(), 3).toString());

            map.put("statusFolha", status);
            map.put("filtroData", dateFiltroFinal);

            try {

                JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relGeraFolha.jasper", map, con);
                JasperViewer.viewReport(print, false);

            } catch (JRException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("relatório não encontrado");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("gerar relatório da folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
        int input = JOptionPane.showConfirmDialog(null,
                "Deseja alterar o status da folha do mes " + " para gerada?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (input == 0) {
            try {
                geradoFolha();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("alterar status da folha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        } else {

        }

    }

    public void buscaEvento() {
        try {
            this.FDBuscaEvento = new FDBuscaEvento(null, true);

            if (EdCodEve.getText().equals("")) {

                this.FDBuscaEvento.setVisible(true);

                this.EdCodEve.setText(String.valueOf(this.FDBuscaEvento.getCodEvento()));

                if (this.EdCodEve.getText().equals("0")) {
                    EdCodEve.setText("");

                } else {
                    validarCodList();
                }

            } else {

                validarCodList();
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar evento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void buscarFuncionario() throws ClassNotFoundException {

        java.util.Date dateFolha = JDcDataFolha.getDate();
        long dtF = dateFolha.getTime();

        java.sql.Date dataF = new java.sql.Date(dtF);

        this.fDListaFuncioFolha = new FDListaFuncioFolha(dataF, null, true);

        if (EdCodFuncio.getText().equals("")) {
            this.fDListaFuncioFolha.setVisible(true);

            this.EdCodFuncio.setText(String.valueOf(this.fDListaFuncioFolha.getCodFuncio()));

            if (this.EdCodFuncio.getText().equals("0")) {
                EdCodFuncio.setText("");

            } else {
                try {
                    validarCodCadastro();
                } catch (ParseException | SQLException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar funcionário");
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
    }

    public void buscaFolha() {
        try {
            if (EdCodFicha.getText().equals("")) {
                int input = JOptionPane.showConfirmDialog(rootPane, "Ficha não encontrada.\nDeseja iniciar uma nova folha?", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    if (JDcDataFolha.getDate() != null) {
                        habilitarB(2);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Você deve informar a data da folha", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } else {
                listarEvento();
            }

        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JtListaFolha = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        BtListaFichas = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbListaFolhas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        BtGeraFolha2 = new javax.swing.JButton();
        JDcDataFiltro = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaEventos = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        JpEventos = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        BtRemoverEvento = new javax.swing.JButton();
        BtAdcEvento = new javax.swing.JButton();
        EdConteEve = new javax.swing.JTextField();
        EdTipoEve = new javax.swing.JTextField();
        EdTipoProcessaEve = new javax.swing.JTextField();
        EdDescriEve = new javax.swing.JTextField();
        EdCodEve = new javax.swing.JTextField();
        BtBuscarEve = new javax.swing.JButton();
        EdDiasTrab = new javax.swing.JTextField();
        EdRTrab = new javax.swing.JTextField();
        EdDiasRepo = new javax.swing.JTextField();
        EdSomaHora = new javax.swing.JTextField();
        EdRepouso = new javax.swing.JTextField();
        BtCalc = new javax.swing.JButton();
        Bt27m4 = new javax.swing.JButton();
        Bt26m5 = new javax.swing.JButton();
        Bt26m4 = new javax.swing.JButton();
        Bt27m5 = new javax.swing.JButton();
        LbCalcSalario = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtListaPar = new javax.swing.JTable();
        BtAdcEventoPar = new javax.swing.JButton();
        BtBuscarParcelas = new javax.swing.JButton();
        BtRemEventoPar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        BtBuscarFolha = new javax.swing.JButton();
        EdCodFicha = new javax.swing.JTextField();
        JDcDataFolha = new com.toedter.calendar.JDateChooser();
        EdCodRelFolha = new javax.swing.JTextField();
        EdCod = new javax.swing.JTextField();
        EdValor = new javax.swing.JTextField();
        BtNovo = new javax.swing.JButton();
        JpFuncio = new javax.swing.JPanel();
        EdTipoSalario = new javax.swing.JTextField();
        EdCodFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncio = new javax.swing.JButton();
        EdNomeFuncio = new javax.swing.JTextField();
        EdSalarioBase = new javax.swing.JTextField();
        EdNrFuncio = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(0, 0));
        setNormalBounds(new java.awt.Rectangle(0, 0, 0, 0));
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

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(0, 0));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMaximumSize(new java.awt.Dimension(0, 0));

        JtListaFolha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº Ficha", "Funcionário", "Nome funcionário", "Tipo de salário", "Evento", "Descrição do evento", "Tipo evento", "Tipo de Processamento", "Conteúdo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JtListaFolha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtListaFolhaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JtListaFolha);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        BtListaFichas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtListaFichas.setText("Buscar");
        BtListaFichas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtListaFichasActionPerformed(evt);
            }
        });

        tbListaFolhas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "data", "Data folha", "Nº de fichas", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListaFolhas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaFolhasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbListaFolhas);
        if (tbListaFolhas.getColumnModel().getColumnCount() > 0) {
            tbListaFolhas.getColumnModel().getColumn(0).setMinWidth(0);
            tbListaFolhas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbListaFolhas.getColumnModel().getColumn(0).setMaxWidth(0);
            tbListaFolhas.getColumnModel().getColumn(3).setMinWidth(0);
            tbListaFolhas.getColumnModel().getColumn(3).setPreferredWidth(0);
            tbListaFolhas.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel1.setText("Folhas geradas:");

        BtGeraFolha2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print_blue.png"))); // NOI18N
        BtGeraFolha2.setText("Imprimir Folha");
        BtGeraFolha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtGeraFolha2ActionPerformed(evt);
            }
        });

        JDcDataFiltro.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Folha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-black.png"))); // NOI18N
        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtListaFichas)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(BtGeraFolha2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(JDcDataFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtListaFichas)
                            .addComponent(JDcDataFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtGeraFolha2)
                            .addComponent(jButton1))))
                .addContainerGap())
        );

        jLabel2.setText("Fichas da Folha:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Listagem de Fichas", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 590));

        tabelaEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codRel", "Cód. Funcio", "Funcionário", "Tipo Salário", "Evento", "Descrição Evento", "Tipo Evento", "Tipo Processamento", "Conteúdo", "codPar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaEventos.setMaximumSize(new java.awt.Dimension(0, 0));
        tabelaEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaEventosMouseClicked(evt);
            }
        });
        tabelaEventos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaEventosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaEventos);
        if (tabelaEventos.getColumnModel().getColumnCount() > 0) {
            tabelaEventos.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaEventos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelaEventos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelaEventos.getColumnModel().getColumn(9).setMinWidth(0);
            tabelaEventos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabelaEventos.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jLabel17.setText("Ficha de eventos: ");

        JpEventos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(null);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtRemoverEvento.setBackground(new java.awt.Color(255, 102, 102));
        BtRemoverEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_white.png"))); // NOI18N
        BtRemoverEvento.setText("Remover");
        BtRemoverEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemoverEventoActionPerformed(evt);
            }
        });
        jPanel3.add(BtRemoverEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 100, 30));

        BtAdcEvento.setBackground(new java.awt.Color(0, 153, 0));
        BtAdcEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_white.png"))); // NOI18N
        BtAdcEvento.setText("Adcionar");
        BtAdcEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcEventoActionPerformed(evt);
            }
        });
        jPanel3.add(BtAdcEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 100, 30));

        EdConteEve.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Conteúdo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdConteEve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdConteEveKeyPressed(evt);
            }
        });
        jPanel3.add(EdConteEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, -1));

        EdTipoEve.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tipo de Evento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(EdTipoEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, -1));

        EdTipoProcessaEve.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tipo Processamento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(EdTipoProcessaEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 150, -1));

        EdDescriEve.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdDescriEve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdDescriEveActionPerformed(evt);
            }
        });
        jPanel3.add(EdDescriEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, -1));

        EdCodEve.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Evento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodEve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodEveActionPerformed(evt);
            }
        });
        EdCodEve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodEveKeyPressed(evt);
            }
        });
        jPanel3.add(EdCodEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, -1));

        BtBuscarEve.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarEve.setText("Buscar");
        BtBuscarEve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarEveActionPerformed(evt);
            }
        });
        jPanel3.add(BtBuscarEve, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 90, 30));

        EdDiasTrab.setText("26");
        EdDiasTrab.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dias Trabalhados:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdDiasTrab, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 120, -1));

        EdRTrab.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Total Trabalhadas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        EdRTrab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdRTrabMouseClicked(evt);
            }
        });
        jPanel3.add(EdRTrab, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 110, -1));

        EdDiasRepo.setText("4");
        EdDiasRepo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dias Repouso:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdDiasRepo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 120, -1));

        EdSomaHora.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Trabalhadas + Repouso:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdSomaHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 140, -1));

        EdRepouso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Total Repouso:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        EdRepouso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdRepousoMouseClicked(evt);
            }
        });
        jPanel3.add(EdRepouso, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 110, -1));

        BtCalc.setText("Calcular");
        BtCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCalcActionPerformed(evt);
            }
        });
        jPanel3.add(BtCalc, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 80, -1));

        Bt27m4.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        Bt27m4.setText("27+4");
        Bt27m4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bt27m4ActionPerformed(evt);
            }
        });
        jPanel3.add(Bt27m4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, -1, -1));

        Bt26m5.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        Bt26m5.setText("26+5");
        Bt26m5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bt26m5ActionPerformed(evt);
            }
        });
        jPanel3.add(Bt26m5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        Bt26m4.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        Bt26m4.setText("26+4");
        Bt26m4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bt26m4ActionPerformed(evt);
            }
        });
        jPanel3.add(Bt26m4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

        Bt27m5.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        Bt27m5.setText("27+5");
        Bt27m5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bt27m5ActionPerformed(evt);
            }
        });
        jPanel3.add(Bt27m5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        LbCalcSalario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 12)); // NOI18N
        LbCalcSalario.setText("SALÁRIO");
        jPanel3.add(LbCalcSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 100, -1));

        JpEventos.addTab("Lançamento de Eventos", jPanel3);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JtListaPar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Parcela", "Valor Procedimento", "Vencimento"
            }
        ));
        JtListaPar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtListaParMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JtListaPar);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 200));

        BtAdcEventoPar.setBackground(new java.awt.Color(0, 153, 0));
        BtAdcEventoPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_white.png"))); // NOI18N
        BtAdcEventoPar.setText("Adicionar");
        BtAdcEventoPar.setPreferredSize(new java.awt.Dimension(99, 30));
        BtAdcEventoPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcEventoParActionPerformed(evt);
            }
        });
        jPanel7.add(BtAdcEventoPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        BtBuscarParcelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarParcelas.setText("Buscar");
        BtBuscarParcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarParcelasActionPerformed(evt);
            }
        });
        jPanel7.add(BtBuscarParcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, -1, -1));

        BtRemEventoPar.setBackground(new java.awt.Color(255, 102, 102));
        BtRemEventoPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_white.png"))); // NOI18N
        BtRemEventoPar.setText("Remover");
        BtRemEventoPar.setPreferredSize(new java.awt.Dimension(98, 30));
        BtRemEventoPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemEventoParActionPerformed(evt);
            }
        });
        jPanel7.add(BtRemEventoPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, -1, -1));

        JpEventos.addTab("Lançar Parcelas Coparti.", jPanel7);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBuscarFolha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarFolha.setText("Buscar");
        BtBuscarFolha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarFolhaActionPerformed(evt);
            }
        });
        jPanel5.add(BtBuscarFolha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 90, -1));

        EdCodFicha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Ficha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdCodFicha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFichaKeyPressed(evt);
            }
        });
        jPanel5.add(EdCodFicha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 120, -1));

        JDcDataFolha.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataFolha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Folha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel5.add(JDcDataFolha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));
        jPanel5.add(EdCodRelFolha, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 40, 10));
        jPanel5.add(EdCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 40, 10));
        jPanel5.add(EdValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 40, 10));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel5.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 28, 90, 33));

        JpFuncio.setBackground(new java.awt.Color(255, 255, 255));
        JpFuncio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JpFuncio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdTipoSalario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tipo Salário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdTipoSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdTipoSalarioActionPerformed(evt);
            }
        });
        JpFuncio.add(EdTipoSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 160, -1));

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
        JpFuncio.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, -1));

        BtBuscarCodFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncio.setText("Buscar");
        BtBuscarCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncioActionPerformed(evt);
            }
        });
        JpFuncio.add(BtBuscarCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        EdNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdNomeFuncioActionPerformed(evt);
            }
        });
        JpFuncio.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 320, -1));

        EdSalarioBase.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salário Base:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        JpFuncio.add(EdSalarioBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, -1));

        EdNrFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        JpFuncio.add(EdNrFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 90, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JpFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(JpEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(JpFuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JpEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manutenção de Fichas da Folha", jPanel1);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCalcActionPerformed
        calcular();

    }//GEN-LAST:event_BtCalcActionPerformed

    private void EdTipoSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdTipoSalarioActionPerformed

    }//GEN-LAST:event_EdTipoSalarioActionPerformed

    private void EdCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncioActionPerformed

    }//GEN-LAST:event_EdCodFuncioActionPerformed

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

        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void BtBuscarCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncioActionPerformed
        try {
            buscarFuncionario();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar funcionário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarCodFuncioActionPerformed

    private void EdNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdNomeFuncioActionPerformed

    }//GEN-LAST:event_EdNomeFuncioActionPerformed

    private void EdCodEveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodEveActionPerformed

    }//GEN-LAST:event_EdCodEveActionPerformed

    private void EdCodEveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodEveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscaEvento();
        }
    }//GEN-LAST:event_EdCodEveKeyPressed

    private void BtBuscarEveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarEveActionPerformed
        buscaEvento();

    }//GEN-LAST:event_BtBuscarEveActionPerformed

    private void EdDescriEveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdDescriEveActionPerformed

    }//GEN-LAST:event_EdDescriEveActionPerformed

    private void BtAdcEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcEventoActionPerformed
        try {
            checkCreateRealiza();
        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtAdcEventoActionPerformed

    private void BtRemoverEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemoverEventoActionPerformed
        try {
            deleteEvento();

        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar evento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtRemoverEventoActionPerformed

    private void EdCodFichaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFichaKeyPressed

    }//GEN-LAST:event_EdCodFichaKeyPressed

    private void tabelaEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEventosMouseClicked
        if (tabelaEventos.getSelectedRow() != -1) {
            EdCodRelFolha.setText(tabelaEventos.getValueAt(tabelaEventos.getSelectedRow(), 0).toString());
            habilitarB(3);
            habilitarB(4);

            String codParcela = tabelaEventos.getValueAt(tabelaEventos.getSelectedRow(), 9).toString();
            if (codParcela.equals("0")) {
                EdCod.setText("");
            } else {
                EdCod.setText(codParcela);
            }

        }
    }//GEN-LAST:event_tabelaEventosMouseClicked

    private void tabelaEventosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaEventosKeyPressed

    }//GEN-LAST:event_tabelaEventosKeyPressed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        Folha c = new Folha();
        FolhaDao dao = new FolhaDao();
        int input = JOptionPane.showConfirmDialog(rootPane, "Deseja cancelar o lançamento?!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            try {
                if (!dao.checkCreateFolha(EdCodFicha.getText())) {
                    c.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
                    dao.delete(c);
                    habilitarB(1);
                    limpaCampos();
                    limparTabelaEventos();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Para deletar a folha você deve remover os eventos", "Eventos não removidos", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar ficha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        if (JDcDataFolha.getDate() != null) {
            try {

                habilitarB(2);
                buscarFuncionario();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("iniciar novo cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar a data da folha", "Data não informada", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed

        try {
            cancelarFolha();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cancelar folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        Folha c = new Folha();
        FolhaDao dao = new FolhaDao();

        if (EdCodFicha.getText().equals("")) {
            dispose();
        } else {
            int input = JOptionPane.showConfirmDialog(rootPane, "Deseja sair e cancelar o lançamento?!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                try {
                    if (dao.checkCreateFolha(EdCodFicha.getText())) {
                        String nome = EdNomeFuncio.getText();
                        String folha = EdCodFicha.getText();
                        JOptionPane.showMessageDialog(rootPane, "A Folha: " + folha + " para o funcionário: " + nome + " permanecerá em aberto.\nPara deletar a folha você deve remover os eventos", "Folha não deletada", JOptionPane.WARNING_MESSAGE);
                        dispose();

                    } else {
                        c.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
                        dao.delete(c);
                        dispose();

                    }
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("cancelar folha");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        habilitarB(1);
        limpaCampos();
        limparTabelaEventos();

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtBuscarFolhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarFolhaActionPerformed
        buscaFolha();
    }//GEN-LAST:event_BtBuscarFolhaActionPerformed

    private void BtBuscarParcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarParcelasActionPerformed
        try {
            listarParcelas();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtBuscarParcelasActionPerformed

    private void JtListaParMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtListaParMouseClicked

        if (JtListaPar.getSelectedRow() != -1) {

            EdCod.setText(JtListaPar.getValueAt(JtListaPar.getSelectedRow(), 0).toString());

            EdValor.setText(JtListaPar.getValueAt(JtListaPar.getSelectedRow(), 1).toString());

        }
    }//GEN-LAST:event_JtListaParMouseClicked

    private void BtAdcEventoParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcEventoParActionPerformed
        try {
            checkCreateRealizaAdcPar();
        } catch (SQLException | ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText(" folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtAdcEventoParActionPerformed

    private void BtRemEventoParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemEventoParActionPerformed
        try {
            deleteEvento();
        } catch (ParseException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText(" folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtRemEventoParActionPerformed

    private void EdRTrabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdRTrabMouseClicked
        EdConteEve.setText(EdRTrab.getText());
    }//GEN-LAST:event_EdRTrabMouseClicked

    private void EdRepousoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdRepousoMouseClicked
        EdConteEve.setText(EdRepouso.getText());
    }//GEN-LAST:event_EdRepousoMouseClicked

    private void BtListaFichasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtListaFichasActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) JtListaFolha.getModel();
        modelo.setNumRows(0);
        try {
            listaFolhas();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText(" listar eventos");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtListaFichasActionPerformed

    private void EdConteEveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdConteEveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkCreateRealiza();
            } catch (SQLException | ParseException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_EdConteEveKeyPressed

    private void Bt26m5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt26m5ActionPerformed
        EdDiasTrab.setText("26");
        EdDiasRepo.setText("5");
        calcular();

    }//GEN-LAST:event_Bt26m5ActionPerformed

    private void Bt27m4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt27m4ActionPerformed
        EdDiasTrab.setText("27");
        EdDiasRepo.setText("4");
        calcular();
    }//GEN-LAST:event_Bt27m4ActionPerformed

    private void Bt26m4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt26m4ActionPerformed
        EdDiasTrab.setText("26");
        EdDiasRepo.setText("4");
        calcular();
    }//GEN-LAST:event_Bt26m4ActionPerformed

    private void Bt27m5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt27m5ActionPerformed
        EdDiasTrab.setText("27");
        EdDiasRepo.setText("5");
        calcular();
    }//GEN-LAST:event_Bt27m5ActionPerformed

    private void tbListaFolhasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaFolhasMouseClicked

        FolhaDao edao = new FolhaDao();
        DefaultTableModel modelo = (DefaultTableModel) JtListaFolha.getModel();
        modelo.setNumRows(0);
        int status = Integer.parseInt(tbListaFolhas.getValueAt(tbListaFolhas.getSelectedRow(), 3).toString());
        String data = tbListaFolhas.getValueAt(tbListaFolhas.getSelectedRow(), 0).toString();
        JDcDataFiltro.setDate((java.util.Date) tbListaFolhas.getValueAt(tbListaFolhas.getSelectedRow(), 0));

        try {
            for (Folha f : edao.readFolhaForGera(status, data)) {
                String conteudo = f.getConteudoFolha().toString().replace(".", ",");
                modelo.addRow(new Object[]{
                    f.getCodFolha(),
                    f.getFr_nrFuncionario(),
                    f.getFr_nomeFuncionario(),
                    f.getFr_tipoSalarioFuncionario(),
                    f.getFr_codEvento(),
                    f.getFr_descriEvento(),
                    f.getFr_tipoEvento(),
                    f.getFr_tipoProcessaEvento(),
                    conteudo,
                    f.getFr_codParcela()

                });
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar folha");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_tbListaFolhasMouseClicked

    private void BtGeraFolha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtGeraFolha2ActionPerformed
        if (tbListaFolhas.getSelectedRowCount() > 0) {
            FolhaDao fdao = new FolhaDao();

            try {
                java.util.Date dataFin = JDcDataFiltro.getDate();
                long dtFinal = dataFin.getTime();
                java.sql.Date dateFiltroFinal = new java.sql.Date(dtFinal);

                if (fdao.checkGeraFolhaAndFuncioIsCreate(dateFiltroFinal)) {

                    int input = JOptionPane.showConfirmDialog(rootPane, "______________________________________________________\n\n"
                            + "Existem funcionário pendentes de geração de folha!\nDeseja gerar a folha mesmo assim?\n"
                            + "______________________________________________________", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (input == 0) {
                        gerarFolha();
                    } else {

                    }

                } else {
                    gerarFolha();

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("gerar folha");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtGeraFolha2ActionPerformed

    private void JtListaFolhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtListaFolhaMouseClicked
        if (EdCodFicha.getText().equals("")) {
            EdCodFicha.setText(JtListaFolha.getValueAt(JtListaFolha.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_JtListaFolhaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (JtListaFolha.getSelectedRowCount() > 0) {
            buscaFolha();
            jTabbedPane1.setSelectedIndex(1);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if (!EdCodFicha.getText().equals("")) {
            FolhaDao dao = new FolhaDao();
            Folha c = new Folha();
            try {
                if (!dao.checkCreateFolha(EdCodFicha.getText())) {
                    c.setCodFolha(Integer.parseInt(EdCodFicha.getText()));
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
    private javax.swing.JButton Bt26m4;
    private javax.swing.JButton Bt26m5;
    private javax.swing.JButton Bt27m4;
    private javax.swing.JButton Bt27m5;
    private javax.swing.JButton BtAdcEvento;
    private javax.swing.JButton BtAdcEventoPar;
    private javax.swing.JButton BtBuscarCodFuncio;
    private javax.swing.JButton BtBuscarEve;
    private javax.swing.JButton BtBuscarFolha;
    private javax.swing.JButton BtBuscarParcelas;
    private javax.swing.JButton BtCalc;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtGeraFolha2;
    private javax.swing.JButton BtListaFichas;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtRemEventoPar;
    private javax.swing.JButton BtRemoverEvento;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdCodEve;
    private javax.swing.JTextField EdCodFicha;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodRelFolha;
    private javax.swing.JTextField EdConteEve;
    private javax.swing.JTextField EdDescriEve;
    private javax.swing.JTextField EdDiasRepo;
    private javax.swing.JTextField EdDiasTrab;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdNrFuncio;
    private javax.swing.JTextField EdRTrab;
    private javax.swing.JTextField EdRepouso;
    private javax.swing.JTextField EdSalarioBase;
    private javax.swing.JTextField EdSomaHora;
    private javax.swing.JTextField EdTipoEve;
    private javax.swing.JTextField EdTipoProcessaEve;
    private javax.swing.JTextField EdTipoSalario;
    private javax.swing.JTextField EdValor;
    private com.toedter.calendar.JDateChooser JDcDataFiltro;
    private com.toedter.calendar.JDateChooser JDcDataFolha;
    private javax.swing.JTabbedPane JpEventos;
    private javax.swing.JPanel JpFuncio;
    private javax.swing.JTable JtListaFolha;
    private javax.swing.JTable JtListaPar;
    private javax.swing.JLabel LbCalcSalario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabelaEventos;
    private javax.swing.JTable tbListaFolhas;
    // End of variables declaration//GEN-END:variables

}
