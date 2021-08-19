package hard.rh.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrImportaCopart extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0812.0";

    FDErroOcorrido fdErroOcorrido;
    DefaultTableModel modelo;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrImportaCopart(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Importação de Coparticipações - V" + versao;
        habilitarB(1);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrImportaCopart";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("importar o arquivo .CSV");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void limpaCampos() {
        EdLink.setText(null);
        EdNfe.setText(null);
        JdDataVencCop.setDate(null);
        DefaultTableModel model = (DefaultTableModel) TbImportDados.getModel();
        model.setNumRows(0);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:
                BtBuscaLink.setEnabled(true);
                LbImportado.setVisible(false);
                BtSalvarDados.setEnabled(false);
                limpaCampos();
                break;

            case 2:
                BtBuscaLink.setEnabled(false);
                break;

            case 3:
                BtSalvarDados.setEnabled(true);
                break;
        }
    }

    public void importarDados() throws FileNotFoundException, IOException, ClassNotFoundException {
        int nlinha = 0;
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
                    DefaultTableModel model = (DefaultTableModel) TbImportDados.getModel();

                    Object[] tableLines = br.lines().toArray();

                    for (Object tableLine : tableLines) {
                        //elimina a primeira linhas com o cabeçalho
                        if (nlinha != 0) {
                            String line = tableLine.toString().trim();
                            String[] dataRow = line.split(";");
                            model.addRow(dataRow);
                        }

                        nlinha++;
                    }
                    vaCop();

                    habilitarB(2);
                    LbImportado.setText("Não importado");
                    LbTotalLinhas.setText(Integer.toString(nlinha - 1));
                } catch (FileNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("selecionar o arquivo .CSV");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            } else {
            }
        } else {

            try {
                BufferedReader br = new BufferedReader(new FileReader(EdLink.getText()));
                DefaultTableModel model = (DefaultTableModel) TbImportDados.getModel();

                Object[] tableLines = br.lines().toArray();
                for (Object tableLine : tableLines) {
                    if (nlinha != 0) {
                        String line = tableLine.toString().trim();
                        String[] dataRow = line.split(";");
                        model.addRow(dataRow);
                    }

                    nlinha++;
                }
                vaCop();
                habilitarB(2);
                LbImportado.setText("Não importado");
                LbTotalLinhas.setText(Integer.toString(nlinha - 1));
            } catch (FileNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("ler o arquivo .CVS");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void validaDados() throws ParseException {

        new Thread() {
            int finalizado = 0;
            int numLinhas = 0;
            int linhaEx = 0;

            @Override
            public void run() {
                FuncionarioDao fdao = new FuncionarioDao();

                try {
                    //PEGA O NÚMERO DE LINHAS
                    int totalRows = TbImportDados.getRowCount();

                    //Número de erros e salvos
                    String logImport = "";

                    //REALIZA A VALIDAÇÃO DE NÚMERO DE LINHAS
                    for (int i = 0; i < totalRows; i++) {

                        int codFuncionario = 0;
                        int codBeneficiario = 0;
                        int statusFuncionario = 0;
                        String codPro = "";
                        String erroFuncionario = "";
                        String erroBeneficiario = "";
                        String erroPro = "";
                        String nomePro = "";

                        //BUSCA O CÓDIGO DO FUNCIONARIO POR CPF
                        String cpfFuncio = (String) TbImportDados.getModel().getValueAt(i, 7); //PEGA O CPF DO FUNCIONÁRIO
                        for (Funcionario f : fdao.readFuncionarioForCpf(cpfFuncio)) {// o 0 dentro do filtro é o status do cadastro, pois pode existir mais de um cadastro com o mesmo cpf
                            codFuncionario = f.getCodFuncionario();
                            statusFuncionario = f.getStatusFuncionario();
                        }

                        //BUSCA O CÓDIGO DO BENEFICIARIO POR CPF
                        String cpfDepende = (String) TbImportDados.getModel().getValueAt(i, 8); //PEGA CPF DO DEPENDENTE
                        for (Dependente d : fdao.readDependenteForCpf(codFuncionario, cpfDepende)) {
                            codBeneficiario = d.getCodDependente();
                        }

                        String nomeFuncionario = (String) TbImportDados.getModel().getValueAt(i, 1);
                        String nomeBeneficiario = (String) TbImportDados.getModel().getValueAt(i, 2);

                        if (codFuncionario == 0) {
                            erroFuncionario = "Funcionário não encontrado: " + nomeFuncionario + "\n\n";
                        } else if (statusFuncionario != 0) {
                            if (!cbInativosNao.isSelected()) {
                                erroFuncionario = "Funcionário inativo " + nomeFuncionario + "\n\n";
                            }
                        } else {
                            if (codBeneficiario == 0) {
                                erroBeneficiario = "Beneficiário: " + nomeBeneficiario + " não encontrado no\n cadastro do funcionário: (" + codFuncionario + ") " + nomeFuncionario + "\n\n";
                            }
                        }

                        ProcedimentoDao pdao = new ProcedimentoDao();
                        codPro = (String) TbImportDados.getModel().getValueAt(i, 18); //PEGA CODIGO DO PROCEDIMENTO
                        if (codPro != null) {
                            if (pdao.checkCod(codPro)) {

                            } else {
                                nomePro = (String) TbImportDados.getModel().getValueAt(i, 14); //PEGA O NOME DO PROCEDIMENTO
                                String valor = (String) TbImportDados.getModel().getValueAt(i, 6);
                                erroPro = "Procedimento não encontrado(" + codPro + ")\n " + nomePro + " - valor: " + valor + "\n\n";
                            }

                        }

                        logImport = logImport + erroFuncionario + erroBeneficiario + erroPro;

                        linhaEx++;
                        LbLinhaEx.setText(Integer.toString(linhaEx));
                    }
                    LbImportado.setText("Não importado");
                    JOptionPane.showMessageDialog(rootPane, "Validação finalizada, consulte o log na aba de Conferência", "Atenção", JOptionPane.WARNING_MESSAGE);
                    JtLogImport.setText(logImport);
                    BtValidar.setEnabled(true);
                    if (JtLogImport.getText().equals("")) {
                        habilitarB(3);
                    }

                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha na importação, o processo será abortado");
                    fdErroOcorrido.LbInformaErro.setText("Falha geral na importação");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        }.start();

    }

    public void vaCop() {
        try {

            TbImportDados.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelcted, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String valor = TbImportDados.getValueAt(row, 6).toString();
                    String cpf_titular = TbImportDados.getValueAt(row, 7).toString();
                    String cpf_benefici = TbImportDados.getValueAt(row, 8).toString();

                    if (valor.equals("") || cpf_titular.equals("") || cpf_benefici.equals("")) {

                        label.setForeground(Color.RED);
                        label.setBackground(Color.YELLOW);
                        TbImportDados.setSelectionForeground(Color.GREEN);

                    } else {
                        label.setBackground(Color.WHITE);
                        label.setForeground(Color.BLACK);
                    }

                    return label;
                }
            });
        } catch (NullPointerException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha na importação");
            fdErroOcorrido.LbInformaErro.setText("Falha ao validar linhas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void salvarDados() throws ParseException {
        new Thread() {
            int finalizado = 0;
            int numLinhas = 0;
            int linhaEx = 0;

            @Override
            public void run() {
                FuncionarioDao fdao = new FuncionarioDao();
                CoparticipacaoDao cdao = new CoparticipacaoDao();

                try {
                    //PEGA O NÚMERO DE LINHAS
                    int totalRows = TbImportDados.getRowCount();

                    //Número de erros e salvos
                    String logImport = "";
                    int copSalva = 0;
                    int copErro = 0;

                    //REALIZA A VALIDAÇÃO DE NÚMERO DE LINHAS
                    for (int i = 0; i < totalRows; i++) {

                        int codFuncionario = 0;
                        int codBeneficiario = 0;
                        int codCop = 0;
                        int statusFuncionario = 0;

                        //##### GET DOS DADOS DA TABELA #######
                        //PEGA DATA REALIZADA PROCEDIMENTO
                        //SE O VALOR DO PROCEDIMENTO FOR VAZIO, NÃO REALIZA
                        //BUSCA O CÓDIGO DO FUNCIONÁRIO PELO NÚMERO DO CPF
                        String cpfFuncio = (String) TbImportDados.getModel().getValueAt(i, 7); //PEGA O CPF DO FUNCIONÁRIO

                        for (Funcionario f : fdao.readFuncionarioForCpf(cpfFuncio)) {// o 0 dentro do filtro é o status do cadastro, pois pode existir mais de um cadastro com o mesmo cpf
                            codFuncionario = f.getCodFuncionario();
                            statusFuncionario = f.getStatusFuncionario();
                        }
                        //BUSCA O CÓDIGO DO BENEFICIARIO POR CPF
                        String cpfDepende = (String) TbImportDados.getModel().getValueAt(i, 8); //PEGA CPF DO DEPENDENTE
                        for (Dependente d : fdao.readDependenteForCpf(codFuncionario, cpfDepende)) {
                            codBeneficiario = d.getCodDependente();
                        }

                        String codPro = (String) TbImportDados.getModel().getValueAt(i, 18); //PEGA CODIGO DO PROCEDIMENTO
                        if (codPro == null) {
                            codPro = "0";
                        }

                        if (codFuncionario != 0 && codBeneficiario != 0 && !codPro.equals("0") && statusFuncionario == 0) {
                            try {
                                //BUSCA O MAIOR CÓDIGO DE COPARTICIPAÇÃO E ADICIONA MAIS 1
                                //preparação para criar nova coparticipação, e adiconar o código dela nas parcelas
                                for (Coparticipacao c : cdao.readCodCop()) {
                                    codCop = c.getCodCop() + 1;
                                }

                                //### INÍCIO DO CORPARTI ###
                                Coparticipacao c = new Coparticipacao();
                                c.setCodCop(codCop);
                                c.setFr_codFuncionario(codFuncionario);
                                c.setFr_codBeneficiario(codBeneficiario);
                                c.setGeradoPar(2);
                                //CRIA COPARTI

                                try {
                                    cdao.createCop(c);
                                } catch (ClassNotFoundException ex) {
                                    fdErroOcorrido = new FDErroOcorrido(null, true);
                                    fdErroOcorrido.LbInformaErro.setText("criar coparticipação");
                                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                                    fdErroOcorrido.setVisible(true);
                                }

                                //### INÍCIO DO REALIZA PROCEDIMENTO ###
                                ProcedimentoDao pdao = new ProcedimentoDao();

                                //VERIFICA SE O PROCEDIMENTO NÃO ESTÁ VAZIO
                                int parcelaPro = 0;

                                //VERIFICA SE O PROCEDIMENTO EXISTE
                                if (pdao.checkCod(codPro)) {
                                    for (Procedimento p : pdao.readProForCod(Integer.parseInt(codPro))) {
                                        parcelaPro = p.getParcelaPro();
                                    }

                                } else {
                                    logImport = logImport + "Procedimento não encontrado: " + codPro + "\n";
                                    codPro = "0";
                                    parcelaPro = 1;
                                }

                                c.setFr_codPro(Integer.parseInt(codPro));

                                //DA REPLACE NA VIRGULA DO VALOR DA TABELA
                                String valorPro = TbImportDados.getModel().getValueAt(i, 6).toString(); //PEGA O VALOR DO PROCEDIMETNO
                                double valorProcedimento = Double.parseDouble(valorPro.replace(',', '.'));
                                c.setFr_valorPro(valorProcedimento);

                                //CONVERTE A DATA DE STRING PARA SQL.DATE E DA O SET
                                String dataVazia = "01/01/2021";
                                String dataProcedi = TbImportDados.getModel().getValueAt(i, 15).toString();

                                if (codPro.equals("0")) {

                                } else {

                                    if (!dataProcedi.equals("")) {
                                        dataVazia = dataProcedi;
                                    }
                                }

                                //CONVERTE DATA
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                java.util.Date parsed = format.parse(dataVazia);
                                java.sql.Date data = new java.sql.Date(parsed.getTime());
                                c.setDataPro(data);
                                String localPro = TbImportDados.getModel().getValueAt(i, 13).toString(); //PEGA LOCAL DO PROCEDIMENTO
                                c.setLocalPro(localPro);
                                c.setMedicoPro("");
                                c.setNfe(Integer.parseInt(EdNfe.getText()));

                                //REALIZA O PROCEDIMENTO
                                try {
                                    cdao.realizaProcedi(c);
                                } catch (ClassNotFoundException ex) {
                                    fdErroOcorrido = new FDErroOcorrido(null, true);
                                    fdErroOcorrido.LbInformaErro.setText("salvar log de sessão");
                                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                                    fdErroOcorrido.setVisible(true);
                                }

                                //### INÍCIO GERAÇÃO DE PARCELA ###
                                int par = 0;
                                int parP = parcelaPro;
                                Parcela u = new Parcela();
                                ParcelaDao dao = new ParcelaDao();
                                double valorParcela = valorProcedimento / parcelaPro;//CALCULA O VALOR DE PARCELA

                                try {
                                    while (par < parP) {

                                        u.setFr_codCop(codCop);
                                        u.setFr_codFuncionario(codFuncionario);
                                        u.setFr_codBeneficiario(codBeneficiario);
                                        u.setValorParcela(valorParcela);

                                        java.util.Date dateV = JdDataVencCop.getDate();
                                        long dtV = dateV.getTime();
                                        java.sql.Date dateVence = new java.sql.Date(dtV);
                                        u.setDataVencPar(dateVence);
                                        u.setStatusParcela(1);

                                        dao.createParcela(u);

                                        int numMesPar = par;
                                        int codPar = 0;
                                        ParcelaDao pardao = new ParcelaDao();
                                        for (Parcela p : pardao.readMaxCodPar()) {

                                            codPar = p.getCodParcela();
                                        }
                                        Parcela pa = new Parcela();
                                        ParcelaDao parcedao = new ParcelaDao();

                                        pa.setCodParcela(codPar);
                                        pa.setNumMesParcela(numMesPar);

                                        parcedao.updateDateParcela(pa);

                                        par++;
                                    }
                                } catch (ClassNotFoundException ex) {
                                    logImport = logImport + "Erro ao criar parcela: " + ex + "\n";
                                    fdErroOcorrido = new FDErroOcorrido(null, true);
                                    fdErroOcorrido.LbInformaErro.setText("Erro ao criar parcela");
                                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                                    fdErroOcorrido.setVisible(true);
                                }

                                copSalva++;

                            } catch (HeadlessException | ClassNotFoundException | NumberFormatException | ParseException ex) {
                                logImport = logImport + "Falha gerar na criação da coparticipação: " + ex + "\n";
                                fdErroOcorrido = new FDErroOcorrido(null, true);
                                fdErroOcorrido.LbInformaErro.setText("Erro ao criar coparticiapação");
                                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                                fdErroOcorrido.setVisible(true);
                            }

                        } else {

                            String erroFuncionario = "";
                            String erroBeneficiario = "";
                            String nomeFuncionario = (String) TbImportDados.getModel().getValueAt(i, 1);
                            String nomeBeneficiario = (String) TbImportDados.getModel().getValueAt(i, 2);
                            if (codFuncionario == 0) {

                                erroFuncionario = "Funcionário não encontrado: " + nomeFuncionario + "\n\n";

                            } else if (statusFuncionario != 0) {

                                erroFuncionario = "Funcionário inativo " + nomeFuncionario + "\n\n";

                            } else {

                                if (codBeneficiario == 0) {
                                    erroBeneficiario = "Beneficiário: " + nomeBeneficiario + " não encontrado no\n cadastro do funcionário: (" + codFuncionario + ") " + nomeFuncionario + "\n\n";
                                }
                            }

                            logImport = logImport + erroFuncionario + erroBeneficiario;
                            copErro++;

                        }

                        linhaEx++;
                        LbLinhaEx.setText(Integer.toString(linhaEx));
                    }
                    EdListaNfe.setText(EdNfe.getText());
                    logImport = logImport + "Importadas: " + copSalva + " - Não importadas: " + copErro;
                    JtLogImport.setText(logImport);
                    LbImportado.setText("Importado");
                    salvaDadoNfeImportada();
                    listaCopartiPorTitulo();
                    JOptionPane.showMessageDialog(rootPane, "Acesse a aba de Conferência da Importação para verificar os erros de importação", "Importação Finalizada", JOptionPane.WARNING_MESSAGE);
                    LbImportado.setVisible(true);
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha na importação, o processo será abortado");
                    fdErroOcorrido.LbInformaErro.setText("Falha geral na importação");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        }.start();

    }

    public void salvaDadoNfeImportada() {
        try {

            CoparticipacaoDao dao = new CoparticipacaoDao();
            Coparticipacao c = new Coparticipacao();

            c.setNfe(Integer.parseInt(EdNfe.getText()));
            java.util.Date dateV = JdDataVencCop.getDate();
            long dtV = dateV.getTime();
            java.sql.Date dateVence = new java.sql.Date(dtV);
            c.setDataVenciNfe(dateVence);
            dao.saveNfeImport(c);
            limpaCampos();
        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha ao salvar a NF-e");
            fdErroOcorrido.LbInformaErro.setText("salvar registros");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listaCopartiPorTitulo() throws ClassNotFoundException {
        CoparticipacaoDao dao = new CoparticipacaoDao();
        modelo = (DefaultTableModel) JtListaCopartsTitulo.getModel();
        modelo.setNumRows(0);
        if (!EdListaNfe.getText().equals("")) {
            DecimalFormat df = new DecimalFormat("R$ ##.##");
            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
            for (Coparticipacao c : dao.readCopForNfe(Integer.parseInt(EdListaNfe.getText()))) {
                modelo.addRow(new Object[]{
                    c.getCodCop(),
                    c.getFr_nomeFuncionario(),
                    c.getFr_nomeBeneficiario(),
                    c.getFr_nomePro(),
                    df.format(c.getFr_valorPro()),
                    formatdata.format(c.getDataPro()),
                    c.getFr_parcelaPro(),
                    df.format(c.getFr_valorPar()),
                    formatdata.format(c.getFr_dataVencPar())

                });
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Para listar as coparticipações importadas, informe o número da NF-e", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        EdLink = new javax.swing.JTextField();
        BtBuscaLink = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbImportDados = new javax.swing.JTable();
        JbRevisado = new javax.swing.JCheckBox();
        BtSalvarDados = new javax.swing.JButton();
        BtProteger = new javax.swing.JButton();
        JdDataVencCop = new com.toedter.calendar.JDateChooser();
        LbImportado = new javax.swing.JLabel();
        LbTotalLinhas = new javax.swing.JLabel();
        LbLinhaEx = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LbStatus = new javax.swing.JLabel();
        BtValidar = new javax.swing.JButton();
        BtAdcLinha = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        EdNfe = new javax.swing.JFormattedTextField();
        cbInativosNao = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtLogImport = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        JtListaCopartsTitulo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        EdListaNfe = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Importação de Coparticipações - V1.0-21.0622.0\"");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        EdLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdLinkActionPerformed(evt);
            }
        });

        BtBuscaLink.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtBuscaLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaLink.setText("BUSCAR");
        BtBuscaLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaLinkActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel2.setText("Selecionar arquivo:");

        TbImportDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MATRICULA", "TITULAR", "BENEFICIARIO", "SUBCONTRATO", "DESCRICAO", "DEB_CRED", "VALOR", "CPF_TITULAR", "CPF_BENEFICIARIO", "NASC_BENEFICIARIO", "TIPO", "EVENTO", "AUTORIZACAO", "LOCAL", "PROCEDIMENTO", "DATA", "MATRICULA_EMPRESA", "GRAU_PARENTESCO", "COD_PROC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TbImportDados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbImportDadosMouseClicked(evt);
            }
        });
        TbImportDados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TbImportDadosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TbImportDados);
        if (TbImportDados.getColumnModel().getColumnCount() > 0) {
            TbImportDados.getColumnModel().getColumn(0).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(0).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(3).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(3).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(3).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(4).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(4).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(4).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(5).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(5).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(5).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(9).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(9).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(9).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(10).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(10).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(10).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(11).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(11).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(11).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(12).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(12).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(12).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(13).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(13).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(13).setMaxWidth(0);
            TbImportDados.getColumnModel().getColumn(16).setMinWidth(0);
            TbImportDados.getColumnModel().getColumn(16).setPreferredWidth(0);
            TbImportDados.getColumnModel().getColumn(16).setMaxWidth(0);
        }

        JbRevisado.setText("Campos revisados");

        BtSalvarDados.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtSalvarDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/import-db.png"))); // NOI18N
        BtSalvarDados.setText("IMPORTAR");
        BtSalvarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarDadosActionPerformed(evt);
            }
        });

        BtProteger.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtProteger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/protect.png"))); // NOI18N
        BtProteger.setText("PROTEGER");
        BtProteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProtegerActionPerformed(evt);
            }
        });

        JdDataVencCop.setBackground(new java.awt.Color(255, 255, 255));
        JdDataVencCop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Vencimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N

        LbImportado.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 12)); // NOI18N

        LbTotalLinhas.setText("0");

        LbLinhaEx.setText("0");

        jLabel4.setText("Nº Linhas:");

        LbStatus.setText("STATUS");

        BtValidar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/validation.png"))); // NOI18N
        BtValidar.setText("VALIDAR");
        BtValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtValidarActionPerformed(evt);
            }
        });

        BtAdcLinha.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        BtAdcLinha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mais_green.png"))); // NOI18N
        BtAdcLinha.setText("LINHA");
        BtAdcLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdcLinhaActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos_red.png"))); // NOI18N
        jButton8.setText("LINHA");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        jButton4.setText("CANCELAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        EdNfe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº Nota Fiscal:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdNfe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###########"))));

        cbInativosNao.setText("Ignorar funcionarios Inativos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2))
                    .addComponent(EdLink, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(BtBuscaLink)
                .addGap(18, 18, 18)
                .addComponent(EdNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JdDataVencCop, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BtValidar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbInativosNao)
                .addGap(38, 38, 38))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1326, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(JbRevisado)
                .addGap(43, 43, 43)
                .addComponent(BtAdcLinha)
                .addGap(11, 11, 11)
                .addComponent(BtProteger)
                .addGap(13, 13, 13)
                .addComponent(jButton8)
                .addGap(101, 101, 101)
                .addComponent(jButton4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(408, 408, 408)
                        .addComponent(LbImportado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(3, 3, 3)
                        .addComponent(LbTotalLinhas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(LbStatus)
                        .addGap(24, 24, 24)
                        .addComponent(LbLinhaEx, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addComponent(BtSalvarDados)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addGap(0, 0, 0)
                        .addComponent(EdLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(BtBuscaLink))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(EdNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JdDataVencCop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtValidar)
                            .addComponent(cbInativosNao))))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(JbRevisado))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(BtAdcLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(BtProteger, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LbImportado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(LbTotalLinhas)
                            .addComponent(LbStatus)
                            .addComponent(LbLinhaEx)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtSalvarDados)))
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Importação de Coparticipação", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        JtLogImport.setColumns(20);
        JtLogImport.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
        JtLogImport.setRows(5);
        JtLogImport.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Log de Importação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));
        jScrollPane2.setViewportView(JtLogImport);

        JtListaCopartsTitulo.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
        JtListaCopartsTitulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Cop.", "Funcionário", "Beneficiário", "Procedimento", "Valor Pro.", "Data Procedimento", "Cod. Par.", "Valor Par.", "Venci. Par."
            }
        ));
        jScrollPane3.setViewportView(JtListaCopartsTitulo);
        if (JtListaCopartsTitulo.getColumnModel().getColumnCount() > 0) {
            JtListaCopartsTitulo.getColumnModel().getColumn(0).setMinWidth(30);
            JtListaCopartsTitulo.getColumnModel().getColumn(0).setPreferredWidth(70);
            JtListaCopartsTitulo.getColumnModel().getColumn(1).setMinWidth(100);
            JtListaCopartsTitulo.getColumnModel().getColumn(1).setPreferredWidth(210);
            JtListaCopartsTitulo.getColumnModel().getColumn(2).setMinWidth(100);
            JtListaCopartsTitulo.getColumnModel().getColumn(2).setPreferredWidth(210);
            JtListaCopartsTitulo.getColumnModel().getColumn(3).setMinWidth(60);
            JtListaCopartsTitulo.getColumnModel().getColumn(3).setPreferredWidth(100);
            JtListaCopartsTitulo.getColumnModel().getColumn(4).setMinWidth(30);
            JtListaCopartsTitulo.getColumnModel().getColumn(4).setPreferredWidth(60);
            JtListaCopartsTitulo.getColumnModel().getColumn(5).setMinWidth(60);
            JtListaCopartsTitulo.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel1.setText("Coparticipações Importas:");

        EdListaNfe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdListaNfeKeyPressed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Nº Nota:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(206, 206, 206)
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(EdListaNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1))
                            .addComponent(jLabel3)
                            .addComponent(EdListaNfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                        .addGap(2, 2, 2))))
        );

        jTabbedPane1.addTab("Conferência de Importação", jPanel2);

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

    private void BtBuscaLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaLinkActionPerformed
        try {
            importarDados();
        } catch (IOException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar dados");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_BtBuscaLinkActionPerformed

    private void BtSalvarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarDadosActionPerformed
        if (EdNfe.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar o número da NF", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else if (JdDataVencCop.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar a data da folha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else if (!JbRevisado.isSelected()) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o check-box de campos revisado", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                salvarDados();
                LbStatus.setText("Importando:");
                LbImportado.setVisible(false);
                JtLogImport.setText("");
            } catch (ParseException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("salvar dados");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtSalvarDadosActionPerformed

    private void BtProtegerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProtegerActionPerformed

        if (BtProteger.getText().equals("PROTEGER")) {
            TbImportDados.setEnabled(false);
            BtProteger.setText("LIBERAR");
        } else if (BtProteger.getText().equals("LIBERAR")) {
            TbImportDados.setEnabled(true);
            BtProteger.setText("PROTEGER");
        }
    }//GEN-LAST:event_BtProtegerActionPerformed

    private void EdLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdLinkActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            listaCopartiPorTitulo();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void EdListaNfeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdListaNfeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!EdNfe.getText().equals("")) {
                EdNfe.setText(EdNfe.getText());
                try {
                    listaCopartiPorTitulo();
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("listar coparticipações");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Informe o número do NF-e para listar", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_EdListaNfeKeyPressed

    private void BtValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtValidarActionPerformed
        try {
            validaDados();
            BtValidar.setEnabled(false);
            LbStatus.setText("Validando:");
        } catch (ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar dados");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtValidarActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        modelo = (DefaultTableModel) TbImportDados.getModel();
        if (TbImportDados.getSelectedRow() >= 0) {
            modelo.removeRow(TbImportDados.getSelectedRow());
            TbImportDados.setModel(modelo);
            LbTotalLinhas.setText(Integer.toString(TbImportDados.getRowCount()));
        } else {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma linha para remover", "Linha não selecionada", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TbImportDadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TbImportDadosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            DefaultTableModel dtm = (DefaultTableModel) TbImportDados.getModel();
            if (TbImportDados.getSelectedRow() >= 0) {
                dtm.removeRow(TbImportDados.getSelectedRow());
                TbImportDados.setModel(dtm);
                LbTotalLinhas.setText(Integer.toString(TbImportDados.getRowCount()));
            } else {
                JOptionPane.showMessageDialog(null, "Você deve selecionar uma linha para remover", "Linha não selecionada", JOptionPane.WARNING_MESSAGE);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DefaultTableModel dtm = (DefaultTableModel) TbImportDados.getModel();
            DefaultTableModel model = (DefaultTableModel) TbImportDados.getModel();
            String[] dataRow = null;

            model.addRow(dataRow);
            LbTotalLinhas.setText(Integer.toString(TbImportDados.getRowCount()));
        }
    }//GEN-LAST:event_TbImportDadosKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        habilitarB(1);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TbImportDadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbImportDadosMouseClicked
        TbImportDados.getSelectionForeground().getBlue();
    }//GEN-LAST:event_TbImportDadosMouseClicked

    private void BtAdcLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdcLinhaActionPerformed

        modelo = (DefaultTableModel) TbImportDados.getModel();
        String[] dataRow = null;
        modelo.addRow(dataRow);

    }//GEN-LAST:event_BtAdcLinhaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAdcLinha;
    private javax.swing.JButton BtBuscaLink;
    private javax.swing.JButton BtProteger;
    private javax.swing.JButton BtSalvarDados;
    private javax.swing.JButton BtValidar;
    private javax.swing.JTextField EdLink;
    private javax.swing.JTextField EdListaNfe;
    private javax.swing.JFormattedTextField EdNfe;
    private javax.swing.JCheckBox JbRevisado;
    private com.toedter.calendar.JDateChooser JdDataVencCop;
    private javax.swing.JTable JtListaCopartsTitulo;
    private javax.swing.JTextArea JtLogImport;
    private javax.swing.JLabel LbImportado;
    private javax.swing.JLabel LbLinhaEx;
    private javax.swing.JLabel LbStatus;
    private javax.swing.JLabel LbTotalLinhas;
    private javax.swing.JTable TbImportDados;
    private javax.swing.JCheckBox cbInativosNao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
