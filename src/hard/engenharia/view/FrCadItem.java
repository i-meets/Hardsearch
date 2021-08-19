package hard.engenharia.view;

import hard.engenharia.dao.ItemDao;
import hard.engenharia.model.Item;
import hard.home.view.FDBuscaEmp;
import hard.rh.dao.EmpresaDao;
import hard.rh.model.Empresa;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrCadItem extends javax.swing.JInternalFrame {

    int cod = 0;
    private FDBuscaEmp FDBuscaEmp;
    String usuarioLogado = "";

    public FrCadItem(String usuario) {
        initComponents();
        habilitarB(1);
        usuarioLogado = usuario;
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void habilitarB(int op) {
        switch (op) {
            case 1:
                //INICIO
                limpaCampos();
                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(true);
                EdCodItem.setEditable(true);
                EdDescricaoItem.setEditable(false);
                RbUtiliza1.setEnabled(false);
                RbUtilizaA.setEnabled(false);
                break;

            case 2:
                //SALVAR
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(false);
                BtCancelar.setEnabled(true);
                EdCodItem.setEditable(true);
                EdDescricaoItem.setEditable(true);
                RbUtiliza1.setEnabled(true);
                RbUtilizaA.setEnabled(true);
                EdCodItem.requestFocus();
                break;
            case 3:
//                EDITAR
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtBuscar.setEnabled(true);
                BtCancelar.setEnabled(true);
                EdCodItem.setEditable(false);
                EdDescricaoItem.setEditable(true);
                RbUtiliza1.setEnabled(true);
                RbUtilizaA.setEnabled(true);
                EdCodItem.requestFocus();
                break;

            case 4:
                //INATIVO
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(false);
                BtBuscar.setEnabled(false);
                BtCancelar.setEnabled(true);
                EdCodItem.setEditable(false);
                EdDescricaoItem.setEditable(false);
                RbUtiliza1.setEnabled(false);
                RbUtilizaA.setEnabled(false);
                EdDescricaoItem.requestFocus();
                break;
        }
    }

    public void limpaCampos() {
        EdCodItem.setText(null);
        EdDescricaoItem.setText(null);
        CbStatus.setSelectedItem("<Selecione>");
        RbUtiliza1.setSelected(false);
        RbUtilizaA.setSelected(false);
        LbRevInternaAtual.setText("0");
        LbRevClienteAtual.setText("0");
        EdCodCliente.setText(null);
        LbCliente.setText("Cliente");
        JdcRevCliente.setDate(null);
        JdcRevInterna.setDate(null);
        cod = 0;
    }

    public void salvarItem() throws ClassNotFoundException, SQLException {
        try {

            ItemDao dao = new ItemDao();
            Item i = new Item();
            i.setCodItem(EdCodItem.getText());
            i.setFr_codEmpresa(Integer.parseInt(EdCodCliente.getText()));
            i.setDescricao(EdDescricaoItem.getText());

            if (RbUtilizaA.isSelected()) {
                i.setTipoContRev(0);
            } else {
                i.setTipoContRev(1);
            }

            java.util.Date dataRevI = JdcRevInterna.getDate();
            long dtInterna = dataRevI.getTime();
            java.sql.Date dataRevInterna = new java.sql.Date(dtInterna);
            i.setData_rev_interna(dataRevInterna);

            java.util.Date dataRevC = JdcRevInterna.getDate();
            long dtCliente = dataRevC.getTime();
            java.sql.Date dataRevCliente = new java.sql.Date(dtCliente);
            i.setData_rev_cliente(dataRevCliente);

            i.setRevisaoInterna(1);
            i.setRevisaoCliente("1");
            i.setFr_usuario(usuarioLogado);
            i.setStatus(1);
            dao.createItem(i);
            habilitarB(1);
            listItens();
        } catch (ClassNotFoundException | NumberFormatException | SQLException ex) {
            System.out.println("Erro: " + ex);
        }

    }

    public void listItens() throws ClassNotFoundException {
        ItemDao dao = new ItemDao();
        int status = 1;
        if (CbListaInativos.isSelected()) {
            status = 0;
        }
        DefaultTableModel modelo = (DefaultTableModel) TbListaItens.getModel();
        modelo.setNumRows(0);
        for (Item i : dao.readItens(status)) {
            modelo.addRow(new Object[]{
                i.getCod(),
                i.getCodItem(),
                i.getDescricao()});
        }
    }

    private void listaItem() throws ClassNotFoundException {
        ItemDao dao = new ItemDao();

        for (Item i : dao.readItemForCod(cod)) {
            EdCodItem.setText(i.getCodItem());
            EdDescricaoItem.setText(i.getDescricao());
            EdCodCliente.setText(Integer.toString(i.getFr_codEmpresa()));
            listarEmpresaCod();

            if (i.getTipoContRev() == 1) {
                RbUtiliza1.setSelected(true);
                RbUtilizaA.setSelected(false);
            } else {
                RbUtiliza1.setSelected(false);
                RbUtilizaA.setSelected(true);
            }
            LbRevInternaAtual.setText(Integer.toString(i.getRevisaoInterna()));
            LbRevClienteAtual.setText(i.getRevisaoCliente());

            if (i.getStatus() == 1) {
                CbStatus.setSelectedItem("ATIVO");
                habilitarB(3);
            } else {
                CbStatus.setSelectedItem("INATIVO");
                habilitarB(4);
            }
        }
    }

    public void editarItem() throws ClassNotFoundException {
        Item i = new Item();

        i.setCod(cod);
        i.setDescricao(EdDescricaoItem.getText());
        if (RbUtiliza1.isSelected()) {
            i.setTipoContRev(1);
        } else {
            i.setTipoContRev(0);
        }
        if (CbStatus.getSelectedItem().equals("ATIVO")) {
            i.setStatus(1);
        } else {
            i.setStatus(0);
        }
        ItemDao dao = new ItemDao();

        dao.updateItem(i);
        listItens();
    }

    public void clickBuscaEmpresa() throws ClassNotFoundException, ClassNotFoundException {
        this.FDBuscaEmp = new FDBuscaEmp("Cliente", null, true);

        if (EdCodCliente.getText().equals("")) {
            this.FDBuscaEmp.setVisible(true);

            this.LbCliente.setText(String.valueOf(this.FDBuscaEmp.getNomeEmp()));
            this.EdCodCliente.setText(String.valueOf(this.FDBuscaEmp.getCodEmp()));

            if (EdCodCliente.getText().equals("0")) {
                EdCodCliente.setText(null);
                LbCliente.setText("Cliente");

            } else {
                habilitarB(2);
            }
        }
    }

    public void listarEmpresaCod() throws ClassNotFoundException {

        EmpresaDao pdao = new EmpresaDao();
        if (!EdCodCliente.getText().equals("")) {
            for (Empresa em : pdao.readEmpForCod(EdCodCliente.getText())) {
                LbCliente.setText(em.getNomeEmp());
                if (em.getTipoEmp().equals("Fornecedor")) {
                    JOptionPane.showMessageDialog(rootPane, "Essa empresa está cadastrada como tipo fornecedor", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void deletarItem() {
        if (cod != 0) {
            try {

                Item i = new Item();

                i.setCod(cod);
                ItemDao dao = new ItemDao();
                dao.deleteItem(i);
                habilitarB(1);
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Informe o código do item", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoTipoRevisao = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        EdCodItem = new javax.swing.JTextField();
        EdDescricaoItem = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        RbUtilizaA = new javax.swing.JRadioButton();
        RbUtiliza1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        CbStatus = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LbRevInternaAtual = new javax.swing.JLabel();
        LbRevClienteAtual = new javax.swing.JLabel();
        EdCodCliente = new javax.swing.JTextField();
        LbCliente = new javax.swing.JLabel();
        JdcRevInterna = new com.toedter.calendar.JDateChooser();
        JdcRevCliente = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        BtBuscaItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaItens = new javax.swing.JTable();
        CbListaInativos = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Arquivo"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Revisão", "Tipo", "Data"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/archive.png"))); // NOI18N
        jButton9.setText("ABRIR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdDescricaoItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("BUSCAR");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo controle revisão:");

        grupoTipoRevisao.add(RbUtilizaA);
        RbUtilizaA.setText("Utiliza padrão A");

        grupoTipoRevisao.add(RbUtiliza1);
        RbUtiliza1.setText("Utiliza padrão 1");

        jLabel1.setText("Situação:");

        CbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "ATIVO", "INATIVO" }));

        jLabel3.setText("REV-Cliente:");

        jLabel4.setText("REV-Interna:");

        LbRevInternaAtual.setText("0");
        LbRevInternaAtual.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LbRevClienteAtual.setText("0");
        LbRevClienteAtual.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Cliente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdCodCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdCodClienteMouseClicked(evt);
            }
        });
        EdCodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodClienteActionPerformed(evt);
            }
        });
        EdCodCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodClienteKeyPressed(evt);
            }
        });

        LbCliente.setText("Cliente");

        JdcRevInterna.setBackground(new java.awt.Color(255, 255, 255));
        JdcRevInterna.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Rev. Interna:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        JdcRevCliente.setBackground(new java.awt.Color(255, 255, 255));
        JdcRevCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Rev. Cliente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jLabel5.setText("Responsável:");

        jLabel6.setText("USUÁRIO");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtBuscar))
                    .addComponent(EdDescricaoItem)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(RbUtiliza1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RbUtilizaA)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(LbCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(EdCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(CbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JdcRevInterna, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(LbRevInternaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JdcRevCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(LbRevClienteAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(EdDescricaoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtBuscar))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(EdCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LbCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RbUtilizaA)
                            .addComponent(RbUtiliza1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(CbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LbRevInternaAtual)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(LbRevClienteAtual)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JdcRevInterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JdcRevCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(24, 24, 24))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setMaximumSize(new java.awt.Dimension(32767, 320));

        jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtBuscaItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaItem.setText("BUSCAR");
        BtBuscaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaItemActionPerformed(evt);
            }
        });

        TbListaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cod", "Código", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbListaItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaItensMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TbListaItensMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaItens);
        if (TbListaItens.getColumnModel().getColumnCount() > 0) {
            TbListaItens.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaItens.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaItens.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        CbListaInativos.setText("Listar inativos");
        CbListaInativos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbListaInativosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtBuscaItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CbListaInativos)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtBuscaItem)
                    .addComponent(CbListaInativos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cadastro de Item", jPanel1);

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

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtSalvarEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvarEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarItem();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        try {
            salvarItem();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao salvar: " + ex);

        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        try {
            editarItem();

        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }

    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtBuscaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaItemActionPerformed
        try {
            listItens();
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro:" + ex);
        }

    }//GEN-LAST:event_BtBuscaItemActionPerformed

    private void TbListaItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaItensMouseClicked
        cod = Integer.parseInt(TbListaItens.getValueAt(TbListaItens.getSelectedRow(), 0).toString());
        try {
            listaItem();
        } catch (ClassNotFoundException ex) {
            System.out.println("erro " + ex);
        }
    }//GEN-LAST:event_TbListaItensMouseClicked

    private void CbListaInativosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbListaInativosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbListaInativosActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        try {
            listaItem();
        } catch (ClassNotFoundException ex) {
            System.out.println("erro " + ex);
        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void EdCodClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdCodClienteMouseClicked
        try {
            clickBuscaEmpresa();
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }//GEN-LAST:event_EdCodClienteMouseClicked

    private void EdCodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodClienteActionPerformed
       
    }//GEN-LAST:event_EdCodClienteActionPerformed
    private void TbListaItensMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaItensMouseEntered
     
    }//GEN-LAST:event_TbListaItensMouseEntered

    private void EdCodClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodClienteKeyPressed
        try {
            listarEmpresaCod();
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }

    }//GEN-LAST:event_EdCodClienteKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscaItem;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JCheckBox CbListaInativos;
    private javax.swing.JComboBox<String> CbStatus;
    private javax.swing.JTextField EdCodCliente;
    private javax.swing.JTextField EdCodItem;
    private javax.swing.JTextField EdDescricaoItem;
    private com.toedter.calendar.JDateChooser JdcRevCliente;
    private com.toedter.calendar.JDateChooser JdcRevInterna;
    private javax.swing.JLabel LbCliente;
    private javax.swing.JLabel LbRevClienteAtual;
    private javax.swing.JLabel LbRevInternaAtual;
    private javax.swing.JRadioButton RbUtiliza1;
    private javax.swing.JRadioButton RbUtilizaA;
    private javax.swing.JTable TbListaItens;
    private javax.swing.ButtonGroup grupoTipoRevisao;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}
