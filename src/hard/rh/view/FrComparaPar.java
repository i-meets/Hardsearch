package hard.rh.view;

import hard.home.view.FDErroOcorrido;
import hard.rh.dao.ParcelaDao;
import hard.rh.model.Parcela;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrComparaPar extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "0.1-21.0622.0";

    FDErroOcorrido fdErroOcorrido;

    public FrComparaPar() {
        initComponents();
        title = "Listagem de parcelas mês a mês - V" + versao;
    }

    public void geraListagem() {
        try {
            DecimalFormat df = new DecimalFormat("R$ ,###.##");
            SimpleDateFormat data = new SimpleDateFormat("MM/yyyy");
            DefaultTableModel modelo = (DefaultTableModel) tbListagem.getModel();
            modelo.setNumRows(0);

            java.util.Date dataIni = JdcDataInicial.getDate();
            long dtInicial = dataIni.getTime();
            java.sql.Date dateFiltroInicial = new java.sql.Date(dtInicial);

            java.util.Date dataFin = JdcDataFinal.getDate();
            long dtFinal = dataFin.getTime();
            java.sql.Date dateFiltroFinal = new java.sql.Date(dtFinal);

            ParcelaDao dao = new ParcelaDao();

            for (Parcela p : dao.readComparaParcelaMes(dateFiltroInicial, dateFiltroFinal)) {
                modelo.addRow(new Object[]{
                    data.format(p.getDataVencPar()),
                    df.format(p.getValorTotal()),
                    p.getNumeroDeParcela()
                });
            }
        } catch (ClassNotFoundException | NullPointerException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas mês a mês");
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
        tbListagem = new javax.swing.JTable();
        JdcDataInicial = new com.toedter.calendar.JDateChooser();
        JdcDataFinal = new com.toedter.calendar.JDateChooser();
        btBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbListagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Valor", "Parcelas"
            }
        ));
        jScrollPane1.setViewportView(tbListagem);

        JdcDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        JdcDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Comparativo de parcelas mês a mês", jPanel1);

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

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
        if (JdcDataFinal.getDate() == null || JdcDataInicial.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar o período", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            geraListagem();
        }
    }//GEN-LAST:event_btBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JdcDataFinal;
    private com.toedter.calendar.JDateChooser JdcDataInicial;
    private javax.swing.JButton btBuscar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbListagem;
    // End of variables declaration//GEN-END:variables
}
