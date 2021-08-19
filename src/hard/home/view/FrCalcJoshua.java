package hard.home.view;

import java.awt.Dimension;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrCalcJoshua extends javax.swing.JInternalFrame {

    public FrCalcJoshua() {
        initComponents();
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void validaCalcIcms() {
        if (EdValorInicialIcms.getText().equals("") || EdValorIcms.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve preencher os campos para calcular!", "ERRO", JOptionPane.WARNING_MESSAGE);

        } else {
            calcularIcms();
        }
    }

    public void calcularIcms() {

        DefaultTableModel modelo = (DefaultTableModel) tabelaIcms.getModel();

        double valorInicial = 0.0;
        double valorIcms = 0.0;

        double resultado;

        valorInicial = Double.parseDouble(EdValorInicialIcms.getText());
        valorIcms = Double.parseDouble(EdValorIcms.getText());

        double porcento = (valorIcms * valorInicial) / 100;
        resultado = valorInicial - porcento;
        String calcResult;
        String valorResult;
        String icmsResult;

        calcResult = new DecimalFormat("R$ #,##0.00#").format(resultado);
        valorResult = new DecimalFormat("R$ #,##0.00#").format(valorInicial);
        icmsResult = new DecimalFormat("##,##%").format(valorIcms);

        EdResultadoIcms.setText(calcResult);

        modelo.addRow(new Object[]{
            valorResult,
            icmsResult,
            calcResult

        });

    }

    public void calcularReajuste() {

        DefaultTableModel modelo = (DefaultTableModel) tabelaRea.getModel();

        double valorInicial = 0.0;
        double valorReajuste = 0.0;

        double resultado;

        valorInicial = Double.parseDouble(EdValorInicialRea.getText());
        valorReajuste = Double.parseDouble(EdValorRea.getText());

        double porcento = (valorReajuste * valorInicial) / 100;
        resultado = valorInicial + porcento;

        String result;
        String resulReajust;
        String resultInicial;

        result = new DecimalFormat("R$ #,##0.00#").format(resultado);
        resulReajust = new DecimalFormat("##,##%").format(valorReajuste);
        resultInicial = new DecimalFormat("R$ #,##0.00#").format(valorInicial);

        EdResultadoRea.setText(result);

        modelo.addRow(new Object[]{
            resultInicial,
            resulReajust,
            result

        });

    }

    public void calcularNovoValor() {

        DefaultTableModel modelo = (DefaultTableModel) tabelaNovoValor.getModel();

        double valorInicial = 0.0;
        double valorNovoValor = 0.0;

        valorInicial = Double.parseDouble(EdValorAtual.getText());
        valorNovoValor = Double.parseDouble(EdNovoValor.getText());

        double valorFinal = ((valorNovoValor - valorInicial) / valorInicial) * 100;
//        resultado = valorInicial + valorFinal;
        String result;
        result = new DecimalFormat(" ##,##%").format(valorFinal);

        EdResultadoNovoValor.setText(result);

        modelo.addRow(new Object[]{
            valorInicial,
            valorNovoValor,
            result

        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        EdValorInicialIcms = new javax.swing.JTextField();
        EdValorIcms = new javax.swing.JTextField();
        BtCalcIcms = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaIcms = new javax.swing.JTable();
        EdResultadoIcms = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        EdValorInicialRea = new javax.swing.JTextField();
        EdValorRea = new javax.swing.JTextField();
        EdResultadoRea = new javax.swing.JTextField();
        BtCalcRea = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaRea = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        EdNovoValor = new javax.swing.JTextField();
        EdValorAtual = new javax.swing.JTextField();
        EdResultadoNovoValor = new javax.swing.JTextField();
        BtCalcNovoValor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaNovoValor = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Calculadora Joshua");

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setBorder(null);
        jTabbedPane1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdValorInicialIcms.setText("1000");
        EdValorInicialIcms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel1.add(EdValorInicialIcms, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        EdValorIcms.setText("12");
        EdValorIcms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "% ICMS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel1.add(EdValorIcms, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 110, -1));

        BtCalcIcms.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        BtCalcIcms.setForeground(new java.awt.Color(0, 153, 102));
        BtCalcIcms.setText("Calcular");
        BtCalcIcms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCalcIcmsActionPerformed(evt);
            }
        });
        jPanel1.add(BtCalcIcms, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 110, 40));

        tabelaIcms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Valor", "ICMS", "RESULTADO"
            }
        ));
        jScrollPane1.setViewportView(tabelaIcms);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 150));

        EdResultadoIcms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Resultado:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel1.add(EdResultadoIcms, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, 40));

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Limpar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel1.setText("*Obs. Usar ponto \" . \" no cálculo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 245, -1, -1));

        jTabbedPane1.addTab("ICMS", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdValorInicialRea.setText("6.25");
        EdValorInicialRea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel2.add(EdValorInicialRea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        EdValorRea.setText("2.40");
        EdValorRea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "% Reajuste:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel2.add(EdValorRea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 110, -1));

        EdResultadoRea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Resultado:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel2.add(EdResultadoRea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, 40));

        BtCalcRea.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        BtCalcRea.setForeground(new java.awt.Color(0, 153, 102));
        BtCalcRea.setText("Calcular");
        BtCalcRea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCalcReaActionPerformed(evt);
            }
        });
        jPanel2.add(BtCalcRea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 110, 40));

        tabelaRea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Valor", "Reajuste", "RESULTADO"
            }
        ));
        jScrollPane2.setViewportView(tabelaRea);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 150));

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel2.setText("*Obs. Usar ponto \" . \" no cálculo");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 245, -1, -1));

        jTabbedPane1.addTab("Reajuste", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdNovoValor.setText("20");
        EdNovoValor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Novo valor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdNovoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 110, -1));

        EdValorAtual.setText("11");
        EdValorAtual.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Valor atual:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdValorAtual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        EdResultadoNovoValor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Resultado:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("sansserif", 0, 11))); // NOI18N
        jPanel3.add(EdResultadoNovoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, 40));

        BtCalcNovoValor.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        BtCalcNovoValor.setForeground(new java.awt.Color(0, 153, 102));
        BtCalcNovoValor.setText("Calcular");
        BtCalcNovoValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCalcNovoValorActionPerformed(evt);
            }
        });
        jPanel3.add(BtCalcNovoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 110, 40));

        tabelaNovoValor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Anterior", "Novo", "RESULTADO"
            }
        ));
        jScrollPane3.setViewportView(tabelaNovoValor);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 150));

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Limpar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel3.setText("*Obs. Usar ponto \" . \" no cálculo");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 245, -1, -1));

        jTabbedPane1.addTab("Novo Valor", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtCalcIcmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCalcIcmsActionPerformed
        validaCalcIcms();
    }//GEN-LAST:event_BtCalcIcmsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabelaIcms.getModel();
        modelo.setNumRows(0);
        EdResultadoIcms.setText(null);
        EdValorIcms.setText(null);
        EdValorInicialIcms.setText(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BtCalcReaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCalcReaActionPerformed
        calcularReajuste();
    }//GEN-LAST:event_BtCalcReaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabelaRea.getModel();
        modelo.setNumRows(0);
        EdResultadoRea.setText(null);
        EdValorRea.setText(null);
        EdValorInicialRea.setText(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void BtCalcNovoValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCalcNovoValorActionPerformed
        calcularNovoValor();
    }//GEN-LAST:event_BtCalcNovoValorActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabelaNovoValor.getModel();
        modelo.setNumRows(0);
        EdResultadoNovoValor.setText(null);
        EdNovoValor.setText(null);
        EdValorAtual.setText(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCalcIcms;
    private javax.swing.JButton BtCalcNovoValor;
    private javax.swing.JButton BtCalcRea;
    private javax.swing.JTextField EdNovoValor;
    private javax.swing.JTextField EdResultadoIcms;
    private javax.swing.JTextField EdResultadoNovoValor;
    private javax.swing.JTextField EdResultadoRea;
    private javax.swing.JTextField EdValorAtual;
    private javax.swing.JTextField EdValorIcms;
    private javax.swing.JTextField EdValorInicialIcms;
    private javax.swing.JTextField EdValorInicialRea;
    private javax.swing.JTextField EdValorRea;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabelaIcms;
    private javax.swing.JTable tabelaNovoValor;
    private javax.swing.JTable tabelaRea;
    // End of variables declaration//GEN-END:variables
}
