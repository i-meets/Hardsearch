package hard.engenharia.view;

import hard.engenharia.dao.FerramentaDao;
import hard.engenharia.dao.ItemDao;
import hard.engenharia.dao.OcorrenciaDao;
import hard.engenharia.model.Ferramenta;
import hard.engenharia.model.Item;
import hard.engenharia.model.Ocorrencia;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrOcorrenciasProd extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0728.0";

    private FDBuscaItem fDBuscaItem;
    private FDBuscaOperador FDBuscaOperador;
    private FDBuscaFerramenta FDBuscaFerramenta;
    private FDBuscaMaquina FDBuscaMaquina;
    int fr_codItem = 0;
    int codOperador = 0;
    int codFerramenta = 0;
    int codMaquina = 0;

    FDErroOcorrido fdErroOcorrido;

    public FrOcorrenciasProd(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        habilitarB(1);
        title = "Controle de Ocorrências de Produção - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrOcorrenciasProd";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

            if (codUsuario.equals("12") || codUsuario.equals("1")) {
                JdcDataOco.setVisible(true);
            } else {
                JdcDataOco.setVisible(false);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 3, (d.height - this.getSize().height) / 6);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:
                limpaCampos();
                EdCod.setEditable(true);
                EdCodFerramenta.setEditable(true);
                EdCodItem.setEnabled(false);
                EdCodMaqui.setEnabled(false);
                EdOperador.setEnabled(false);
                TaMotivo.setEnabled(false);

                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                break;
            case 2:
                maiorCodBO();
                EdCod.setEditable(false);
                EdCodFerramenta.setEditable(true);
                EdCodItem.setEnabled(true);
                EdCodMaqui.setEnabled(true);
                EdOperador.setEnabled(true);
                TaMotivo.setEnabled(true);

                BtBuscar.setEnabled(false);
                BtCancelar.setEnabled(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                break;

            case 3:
                EdCod.setEditable(false);
                EdCodFerramenta.setEditable(true);
                EdCodItem.setEnabled(true);
                EdCodMaqui.setEnabled(true);
                EdOperador.setEnabled(true);
                TaMotivo.setEnabled(true);

                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                break;

        }

    }

    public void limpaCampos() {
        EdCod.setText(null);
        EdCodFerramenta.setText(null);
        EdCodItem.setText(null);
        EdCodMaqui.setText(null);
        EdOperador.setText(null);
        TaMotivo.setText(null);
        EdValorCompra.setText(null);
        LbDescricaoFerramenta.setText("GRUPO");
        LbDescricaoItem.setText("DESCRIÇÃO");
    }

    public void maiorCodBO() {
        try {
            OcorrenciaDao dao = new OcorrenciaDao();
            for (Ocorrencia a : dao.readMaiorCodBo()) {
                EdCod.setText(Integer.toString(a.getCod_bo()));
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void listaItem() throws ClassNotFoundException {
        ItemDao dao = new ItemDao();
        fr_codItem = Integer.parseInt(EdCodItem.getText());

        for (Item i : dao.readItemForCod(fr_codItem)) {
            EdCodItem.setText(i.getCodItem());
            LbDescricaoItem.setText(i.getDescricao());

        }
    }

    public void salvarOcorrencia() {
        try {

            Ocorrencia o = new Ocorrencia();
            OcorrenciaDao dao = new OcorrenciaDao();

            o.setFr_codFer(codFerramenta);
            o.setValorUltCompra(Double.parseDouble(EdValorCompra.getText().replace(",", ".")));
            o.setFr_codMaq(codMaquina);
            o.setFr_codFun(codOperador);
            o.setMotivoQuebra(TaMotivo.getText());
            o.setFr_codItem(fr_codItem);

            if (JdcDataOco.getDate() != null) {
                java.util.Date dataO = JdcDataOco.getDate();
                long dtO = dataO.getTime();
                java.sql.Date dateOcorrencia = new java.sql.Date(dtO);
                o.setData(dateOcorrencia);
            }
            dao.createOcorrencia(o);
            listaOcorrenciaPorMes();
            habilitarB(1);

        } catch (ClassNotFoundException | NumberFormatException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listaOcorrenciaPorMes() {
        try {

            OcorrenciaDao dao = new OcorrenciaDao();
            DefaultTableModel modelo = (DefaultTableModel) TbListaOcorre.getModel();
            modelo.setNumRows(0);
            DecimalFormat df = new DecimalFormat("R$ ##.##");
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

            String mes = "";
            if (CbSelecionaMes.getSelectedItem().equals("Janeiro")) {
                mes = "01";
            } else if (CbSelecionaMes.getSelectedItem().equals("Fevereiro")) {
                mes = "02";
            } else if (CbSelecionaMes.getSelectedItem().equals("Março")) {
                mes = "03";
            } else if (CbSelecionaMes.getSelectedItem().equals("Abril")) {
                mes = "04";
            } else if (CbSelecionaMes.getSelectedItem().equals("Maio")) {
                mes = "05";
            } else if (CbSelecionaMes.getSelectedItem().equals("Junho")) {
                mes = "06";
            } else if (CbSelecionaMes.getSelectedItem().equals("Julho")) {
                mes = "07";
            } else if (CbSelecionaMes.getSelectedItem().equals("Agosto")) {
                mes = "08";
            } else if (CbSelecionaMes.getSelectedItem().equals("Setembro")) {
                mes = "09";
            } else if (CbSelecionaMes.getSelectedItem().equals("Outubro")) {
                mes = "10";
            } else if (CbSelecionaMes.getSelectedItem().equals("Novembro")) {
                mes = "11";
            } else if (CbSelecionaMes.getSelectedItem().equals("Dezembro")) {
                mes = "12";
            }

            for (Ocorrencia o : dao.readOcorrenciaForMonth(mes)) {
                modelo.addRow(new Object[]{
                    o.getCod_bo(),
                    o.getFr_codFer(),
                    o.getFr_codFerramenta(),
                    df.format(o.getValorUltCompra()),
                    o.getFr_codMaq(),
                    o.getFr_codMaquina(),
                    o.getFr_codFun(),
                    o.getFr_nomeFun(),
                    o.getMotivoQuebra(),
                    o.getFr_codItem(),
                    o.getFr_codigoItem(),
                    dt.format(o.getData())

                });

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void deletarOcorrencia() throws ClassNotFoundException {
        Ocorrencia i = new Ocorrencia();
        OcorrenciaDao dao = new OcorrenciaDao();

        if (EdCod.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar uma ocorrência para deletar", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            i.setCod_bo(Integer.parseInt(EdCod.getText()));
            try {
                String nome = EdCodItem.getText();

                int input = JOptionPane.showConfirmDialog(rootPane,
                        "Deseja mesmo excluír o lançamento da ocorrência para o item  " + nome, "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (input == 0) {
                    dao.deleteOcorrencia(i);
                    habilitarB(1);
                    limpaCampos();
                    listaOcorrenciaPorMes();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Exclusão cancelada", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar ocorrência");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }

    }

    public void editarFerramenta() {
        try {
            OcorrenciaDao dao = new OcorrenciaDao();
            Ocorrencia o = new Ocorrencia();

            String valor = EdValorCompra.getText().replace("R$ ", "");
            o.setValorUltCompra(Double.parseDouble(valor.replace(",", ".")));
            o.setFr_codMaq(codMaquina);
            o.setFr_codFun(codOperador);
            o.setMotivoQuebra(TaMotivo.getText());
            o.setFr_codItem(fr_codItem);
            o.setCod_bo(Integer.parseInt(EdCod.getText()));
            if (JdcDataOco.getDate() != null) {
                java.util.Date dataO = JdcDataOco.getDate();
                long dtO = dataO.getTime();
                java.sql.Date dateOcorrencia = new java.sql.Date(dtO);
                o.setData(dateOcorrencia);
            }
            dao.updateOcorrencia(o);
            listaOcorrenciaPorMes();
            habilitarB(1);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("editar ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void buscaFerramenta() {
        FerramentaDao dao = new FerramentaDao();
        try {
            this.FDBuscaFerramenta = new FDBuscaFerramenta(null, true);

            if (EdCodFerramenta.getText().equals("")) {
                this.FDBuscaFerramenta.setVisible(true);
                EdCodFerramenta.setText("");
                EdValorCompra.setText("");

                this.EdCodFerramenta.setText(String.valueOf(this.FDBuscaFerramenta.getCod()));
                codFerramenta = Integer.parseInt(EdCodFerramenta.getText());
                this.EdCodFerramenta.setText(String.valueOf(this.FDBuscaFerramenta.getCodItem()));
                this.LbDescricaoFerramenta.setText(String.valueOf(this.FDBuscaFerramenta.getDescricao()));
                habilitarB(2);

                if (this.EdCodFerramenta.getText().equals("null")) {
                    EdCodFerramenta.setText("");
                    LbDescricaoFerramenta.setText("DESCRIÇÃO");
                    habilitarB(1);
                } else {

                    try {

                        for (Ferramenta f : dao.listaOcPorItem(EdCodFerramenta.getText())) {
                            int numeroOc = f.getNumeroOc();
                            for (Ferramenta fe : dao.listaUltimaOcsColet(numeroOc, EdCodFerramenta.getText())) {

                                EdValorCompra.setText(Double.toString(fe.getValor_compra()).replace(".", ","));
                                lbNumeroOc.setText(Integer.toString(numeroOc));
                            }
                        }

                        if (EdValorCompra.getText().equals("")) {
                            JOptionPane.showMessageDialog(rootPane, "Não foi encontrado valores para está ferramenta", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (ClassNotFoundException ex) {
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("buscar ocorrência");
                        FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                        fdErroOcorrido.setVisible(true);
                    }

                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaOcorre = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        EdCodFerramenta = new javax.swing.JTextField();
        LbDescricaoFerramenta = new javax.swing.JLabel();
        EdCodMaqui = new javax.swing.JTextField();
        EdOperador = new javax.swing.JTextField();
        EdValorCompra = new javax.swing.JTextField();
        LbDescMaq = new javax.swing.JLabel();
        lbNumeroOc = new javax.swing.JLabel();
        EdCod = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TaMotivo = new javax.swing.JTextArea();
        LbDescricaoItem = new javax.swing.JLabel();
        EdCodItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CbSelecionaMes = new javax.swing.JComboBox<>();
        BtBuscar = new javax.swing.JButton();
        JdcDataOco = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TbListaOcorre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod_bo", "cod_fer", "Ferramenta", "Valor ult. compra", " cod_maq", "Máquina", "codFuncionario", "Operador", "motivo_quebra", "item_cod", "Item", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, true, false, true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbListaOcorre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaOcorreMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaOcorre);
        if (TbListaOcorre.getColumnModel().getColumnCount() > 0) {
            TbListaOcorre.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(0).setMaxWidth(0);
            TbListaOcorre.getColumnModel().getColumn(1).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(1).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(1).setMaxWidth(0);
            TbListaOcorre.getColumnModel().getColumn(4).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(4).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(4).setMaxWidth(0);
            TbListaOcorre.getColumnModel().getColumn(6).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(6).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(6).setMaxWidth(0);
            TbListaOcorre.getColumnModel().getColumn(8).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(8).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(8).setMaxWidth(0);
            TbListaOcorre.getColumnModel().getColumn(9).setMinWidth(0);
            TbListaOcorre.getColumnModel().getColumn(9).setPreferredWidth(0);
            TbListaOcorre.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodFerramenta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Ferramenta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdCodFerramenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdCodFerramentaMouseClicked(evt);
            }
        });
        EdCodFerramenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFerramentaKeyPressed(evt);
            }
        });

        LbDescricaoFerramenta.setText("GRUPO");

        EdCodMaqui.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Máquina:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdCodMaqui.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdCodMaquiMouseClicked(evt);
            }
        });
        EdCodMaqui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodMaquiActionPerformed(evt);
            }
        });
        EdCodMaqui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodMaquiKeyPressed(evt);
            }
        });

        EdOperador.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Operador:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdOperador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdOperadorMouseClicked(evt);
            }
        });
        EdOperador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdOperadorKeyPressed(evt);
            }
        });

        EdValorCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor últi. Compra:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        LbDescMaq.setText("DESCRIÇÃO");

        lbNumeroOc.setText("NUMERO OC");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(EdOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EdCodMaqui)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                                .addComponent(LbDescMaq, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EdCodFerramenta)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(LbDescricaoFerramenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EdValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbNumeroOc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdCodFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LbDescricaoFerramenta)
                    .addComponent(lbNumeroOc))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdCodMaqui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LbDescMaq)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EdCod.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TaMotivo.setColumns(20);
        TaMotivo.setRows(5);
        TaMotivo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Motivo Quebra:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane2.setViewportView(TaMotivo);

        LbDescricaoItem.setText("DESCRIÇÃO");

        EdCodItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Item:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdCodItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdCodItemMouseClicked(evt);
            }
        });
        EdCodItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodItemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(LbDescricaoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LbDescricaoItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Listagem de Ocorrências do dia:");

        CbSelecionaMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecionar>", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("BUSCAR");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        JdcDataOco.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataOco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Ocorrência:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jLabel1.setText("Filtro mês:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JdcDataOco, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CbSelecionaMes, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(47, 47, 47)))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(EdCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(JdcDataOco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CbSelecionaMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtBuscar)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lançamento de Ocorrências de Produção", jPanel1);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
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

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvarEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvarEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        try {

            deletarOcorrencia();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("excluir ocorrência");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        if (EdCodFerramenta.getText().equals("") || EdCodItem.getText().equals("") || EdCodMaqui.getText().equals("") || EdOperador.getText().equals("") || EdValorCompra.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            if (codFerramenta == 0 || codMaquina == 0 || codOperador == 0) {
                JOptionPane.showMessageDialog(rootPane, "Você deve preencher todos os campos", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                salvarOcorrencia();
            }
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        editarFerramenta();
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed

        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void EdCodFerramentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdCodFerramentaMouseClicked
        buscaFerramenta();
    }//GEN-LAST:event_EdCodFerramentaMouseClicked

    private void EdCodItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdCodItemMouseClicked
        try {
            this.fDBuscaItem = new FDBuscaItem(null, true);
            if (EdCodItem.getText().equals("")) {
                this.fDBuscaItem.setVisible(true);

                this.EdCodItem.setText(String.valueOf(this.fDBuscaItem.getCod()));

                if (this.EdCodItem.getText().equals("0")) {
                    EdCodItem.setText("");

                } else {
                    listaItem();
                }

            } else {

                listaItem();

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar item");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdCodItemMouseClicked

    private void EdOperadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdOperadorMouseClicked
        try {
            this.FDBuscaOperador = new FDBuscaOperador(null, true);

            if (EdOperador.getText().equals("")) {
                this.FDBuscaOperador.setVisible(true);

                this.EdOperador.setText(String.valueOf(this.FDBuscaOperador.getCodFuncio()));
                codOperador = Integer.parseInt(EdOperador.getText());
                this.EdOperador.setText(String.valueOf(this.FDBuscaOperador.getNomeFuncio()));

                if (this.EdOperador.getText().equals("null")) {
                    EdOperador.setText("");
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar operador");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdOperadorMouseClicked

    private void EdCodMaquiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodMaquiActionPerformed
        
    }//GEN-LAST:event_EdCodMaquiActionPerformed

    private void EdCodMaquiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodMaquiKeyPressed
        try {

            this.FDBuscaMaquina = new FDBuscaMaquina(null, true);

            if (EdCodMaqui.getText().equals("")) {
                this.FDBuscaMaquina.setVisible(true);

                this.EdCodMaqui.setText(String.valueOf(this.FDBuscaMaquina.getCod()));
                codMaquina = Integer.parseInt(EdCodMaqui.getText());
                this.EdCodMaqui.setText(String.valueOf(this.FDBuscaMaquina.getCodMaquina()));
                this.LbDescMaq.setText(String.valueOf(this.FDBuscaMaquina.getDescMaq()));

                if (this.EdCodMaqui.getText().equals("null")) {
                    EdCodMaqui.setText("");
                    LbDescMaq.setText("DESCRIÇÃO");
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código máquina");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdCodMaquiKeyPressed

    private void EdCodMaquiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdCodMaquiMouseClicked

        try {

            this.FDBuscaMaquina = new FDBuscaMaquina(null, true);

            if (EdCodMaqui.getText().equals("")) {
                this.FDBuscaMaquina.setVisible(true);

                this.EdCodMaqui.setText(String.valueOf(this.FDBuscaMaquina.getCod()));
                codMaquina = Integer.parseInt(EdCodMaqui.getText());
                this.EdCodMaqui.setText(String.valueOf(this.FDBuscaMaquina.getCodMaquina()));
                this.LbDescMaq.setText(String.valueOf(this.FDBuscaMaquina.getDescMaq()));

                if (this.EdCodMaqui.getText().equals("null")) {
                    EdCodMaqui.setText("");
                    LbDescMaq.setText("DESCRIÇÃO");
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código máquina");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdCodMaquiMouseClicked

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        listaOcorrenciaPorMes();
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void TbListaOcorreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaOcorreMouseClicked
        EdCod.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 0).toString());
        codFerramenta = Integer.parseInt(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 1).toString());
        EdCodFerramenta.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 2).toString());

        EdValorCompra.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 3).toString());
        codMaquina = Integer.parseInt(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 4).toString());
        EdCodMaqui.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 5).toString());

        codOperador = Integer.parseInt(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 6).toString());
        EdOperador.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 7).toString());
        TaMotivo.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 8).toString());

        fr_codItem = Integer.parseInt(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 9).toString());
        EdCodItem.setText(TbListaOcorre.getValueAt(TbListaOcorre.getSelectedRow(), 10).toString());

        habilitarB(3);

    }//GEN-LAST:event_TbListaOcorreMouseClicked

    private void EdCodFerramentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFerramentaKeyPressed
        buscaFerramenta();
    }//GEN-LAST:event_EdCodFerramentaKeyPressed

    private void EdOperadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdOperadorKeyPressed
        try {
            this.FDBuscaOperador = new FDBuscaOperador(null, true);

            if (EdOperador.getText().equals("")) {
                this.FDBuscaOperador.setVisible(true);

                this.EdOperador.setText(String.valueOf(this.FDBuscaOperador.getCodFuncio()));
                codOperador = Integer.parseInt(EdOperador.getText());
                this.EdOperador.setText(String.valueOf(this.FDBuscaOperador.getNomeFuncio()));

                if (this.EdOperador.getText().equals("null")) {
                    EdOperador.setText("");
                }
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar operador");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdOperadorKeyPressed

    private void EdCodItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodItemKeyPressed
        try {
            this.fDBuscaItem = new FDBuscaItem(null, true);
            if (EdCodItem.getText().equals("")) {
                this.fDBuscaItem.setVisible(true);

                this.EdCodItem.setText(String.valueOf(this.fDBuscaItem.getCod()));

                if (this.EdCodItem.getText().equals("0")) {
                    EdCodItem.setText("");

                } else {
                    listaItem();
                }

            } else {

                listaItem();

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar item");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_EdCodItemKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JComboBox<String> CbSelecionaMes;
    private javax.swing.JTextField EdCod;
    private javax.swing.JTextField EdCodFerramenta;
    private javax.swing.JTextField EdCodItem;
    private javax.swing.JTextField EdCodMaqui;
    private javax.swing.JTextField EdOperador;
    private javax.swing.JTextField EdValorCompra;
    private com.toedter.calendar.JDateChooser JdcDataOco;
    private javax.swing.JLabel LbDescMaq;
    private javax.swing.JLabel LbDescricaoFerramenta;
    private javax.swing.JLabel LbDescricaoItem;
    private javax.swing.JTextArea TaMotivo;
    private javax.swing.JTable TbListaOcorre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbNumeroOc;
    // End of variables declaration//GEN-END:variables
}
