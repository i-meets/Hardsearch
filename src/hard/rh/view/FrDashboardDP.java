package hard.rh.view;

import hard.home.view.FDErroOcorrido;
import hard.rh.dao.ContratoDao;
import hard.rh.dao.CotistaDao;
import hard.rh.dao.IntegracaoDao;
import hard.rh.dao.ParcelaDao;
import hard.rh.model.Contrato;
import hard.rh.model.Cotista;
import hard.rh.model.Integracao;
import hard.rh.model.Parcela;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Suporte-01
 */
public class FrDashboardDP extends javax.swing.JInternalFrame {

    FDErroOcorrido fdErroOcorrido;

    public FrDashboardDP() throws ClassNotFoundException {
        initComponents();
        startAlerts();

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 1, (d.height - this.getSize().height) / 1);
    }

    public void startAlerts() throws ClassNotFoundException {
        listarContratos();
        listarInteVencida();
        listarParcelaVencida();
        listarCotasVencidas();
    }

    public void listarContratos() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TbAlertContrato.getModel();
        modelo.setNumRows(0);
        ContratoDao cdao = new ContratoDao();
        Contrato co = new Contrato();
        if (co.getDiasRestantes30dd() <= 0) {

            for (Contrato c : cdao.readContratoAlert()) {

                SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");

                modelo.addRow(new Object[]{
                    c.getCodContra(),
                    c.getFr_codFuncionario(),
                    c.getFr_nomeFuncionario(),
                    c.getDataAdmiContra(),
                    formatdata.format(c.getDataVenc30dd()),
                    (c.getDiasRestantes30dd() * -1),
                    formatdata.format(c.getDataVenc90dd()),
                    (c.getDiasRestantes90dd() * -1)
                });

            }

        }

    }

    public void listarInteVencida() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) TbAlertIntegra.getModel();
        modelo.setNumRows(0);
        IntegracaoDao pdao = new IntegracaoDao();
        Integracao in = new Integracao();
        if (in.getDiasVencidos() <= 0) {
            for (Integracao i : pdao.readIntegracao()) {
                modelo.addRow(new Object[]{
                    i.getCodInt(),
                    i.getFr_codFuncionario(),
                    i.getFr_nomeFuncionario(),
                    i.getFr_nomeEmpresa(),
                    (i.getDiasVencidos() * -1)
                });
            }

        }

    }

    public void listarParcelaVencida() throws ClassNotFoundException {
        ParcelaDao dao = new ParcelaDao();
        DefaultTableModel modelo = (DefaultTableModel) TbAlertParcela.getModel();
        modelo.setNumRows(0);
        DecimalFormat df = new DecimalFormat("R$ ##.##");
        for (Parcela p : dao.readParForStatusNPagaAlert()) {
            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
            modelo.addRow(new Object[]{
                p.getCodParcela(),
                p.getFr_codCop(),
                p.getFr_codFuncionario(),
                p.getFr_nomeFuncionario(),
                df.format(p.getValorParcela()),
                formatdata.format(p.getDataVencPar()),
                p.getDiasAtrasoPar() * -1});

        }

    }

    public void listarCotasVencidas() throws ClassNotFoundException {
        DefaultTableModel modelo = (DefaultTableModel) TbAlertaCota.getModel();
        modelo.setNumRows(0);
        CotistaDao pdao = new CotistaDao();

        for (Cotista c : pdao.readCotistaVencidas()) {
            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
            modelo.addRow(new Object[]{
                c.getCodCota(),
                c.getFr_codFuncionario(),
                c.getFr_nomeFuncionario(),
                formatdata.format(c.getDataAdmi()),
                formatdata.format(c.getDataVenci()),
                (c.getDiasVencidos() * -1)});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbAlertContrato = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbAlertIntegra = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TbAlertParcela = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TbAlertaCota = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Dashboard Vencimentos - V1.0-20.0813.0");
        setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 51, 51)));

        TbAlertContrato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Contrato", "Cód. Funcionário", "Nome", "Data Admissão", "Data Term. 30d", "Dias para termino", "Data Term. 90d", "Dias para termino"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbAlertContrato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbAlertContratoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbAlertContrato);
        if (TbAlertContrato.getColumnModel().getColumnCount() > 0) {
            TbAlertContrato.getColumnModel().getColumn(0).setMinWidth(30);
            TbAlertContrato.getColumnModel().getColumn(0).setPreferredWidth(80);
            TbAlertContrato.getColumnModel().getColumn(0).setMaxWidth(100);
            TbAlertContrato.getColumnModel().getColumn(1).setMinWidth(30);
            TbAlertContrato.getColumnModel().getColumn(1).setPreferredWidth(95);
            TbAlertContrato.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel1.setText("Listagem de Contratos por vencimento:");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Listagem de Integrações por vencimento:");

        TbAlertIntegra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Integra.", "Cód. Funcionário", "Nome Funcionário", "Empresa", "Dias para Vencimento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TbAlertIntegra);
        if (TbAlertIntegra.getColumnModel().getColumnCount() > 0) {
            TbAlertIntegra.getColumnModel().getColumn(0).setMinWidth(30);
            TbAlertIntegra.getColumnModel().getColumn(0).setPreferredWidth(80);
            TbAlertIntegra.getColumnModel().getColumn(0).setMaxWidth(90);
            TbAlertIntegra.getColumnModel().getColumn(1).setMinWidth(30);
            TbAlertIntegra.getColumnModel().getColumn(1).setPreferredWidth(95);
            TbAlertIntegra.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        TbAlertParcela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Parcela", "Cód. Coparti.", "Cód. Funcionário", "Nome Funcionário", "Valor da Parcela", "Data Vencimento", "Dias para Vencimento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TbAlertParcela);
        if (TbAlertParcela.getColumnModel().getColumnCount() > 0) {
            TbAlertParcela.getColumnModel().getColumn(0).setMinWidth(30);
            TbAlertParcela.getColumnModel().getColumn(0).setPreferredWidth(80);
            TbAlertParcela.getColumnModel().getColumn(0).setMaxWidth(90);
            TbAlertParcela.getColumnModel().getColumn(1).setMinWidth(30);
            TbAlertParcela.getColumnModel().getColumn(1).setPreferredWidth(80);
            TbAlertParcela.getColumnModel().getColumn(1).setMaxWidth(90);
            TbAlertParcela.getColumnModel().getColumn(2).setMinWidth(30);
            TbAlertParcela.getColumnModel().getColumn(2).setPreferredWidth(95);
            TbAlertParcela.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Listagem de Cotas Vencidas:");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel4.setText("Listagem de Parcelas por vencimento:");

        TbAlertaCota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Cota", "Cod. Funcionário", "Nome Funcionário", "Data Admissão", "Data Vencimento", "Dias Vencidos"
            }
        ));
        jScrollPane4.setViewportView(TbAlertaCota);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update_black.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(655, 655, 655)
                        .addComponent(jButton1))
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Dashboard Departamento Pessoal", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TbAlertContratoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbAlertContratoMouseClicked

    }//GEN-LAST:event_TbAlertContratoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            startAlerts();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar alertas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TbAlertContrato;
    private javax.swing.JTable TbAlertIntegra;
    private javax.swing.JTable TbAlertParcela;
    private javax.swing.JTable TbAlertaCota;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
