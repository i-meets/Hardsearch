
package hard.rh.view;

import hard.config.ConnectionFactory;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.dao.CoparticipacaoDao;
import hard.rh.model.Coparticipacao;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrListaImporta extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;
    int numeroNFe = 0;

    public FrListaImporta(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Listagem de Notas de Coparticipações Importadas V" + versao;
        listaNfs();

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrListaImporta";
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
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 4);
    }

    public void listaNfs() {
        try {

            CoparticipacaoDao dao = new CoparticipacaoDao();
            DefaultTableModel modelo = (DefaultTableModel) tbListaNf.getModel();
            modelo.setNumRows(0);
            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
            for (Coparticipacao c : dao.readNfeImport()) {
                modelo.addRow(new Object[]{
                    c.getNfe(),
                    formatdata.format(c.getDataImporta()),
                    formatdata.format(c.getDataVenciNfe())
                });

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha na importação, o processo será abortado");
            fdErroOcorrido.LbInformaErro.setText("Falha geral na importação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void listaCopartiPorTitulo() throws ClassNotFoundException {
        try {

            CoparticipacaoDao dao = new CoparticipacaoDao();
            DefaultTableModel modelo = (DefaultTableModel) JtListaCopartsTitulo.getModel();
            modelo.setNumRows(0);
            if (numeroNFe != 0) {
                DecimalFormat df = new DecimalFormat("R$ ##.##");
                SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
                for (Coparticipacao c : dao.readCopForNfe(numeroNFe)) {
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
                JOptionPane.showMessageDialog(rootPane, "Número de título não informado, não foi possivél listar coparticipações", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            FDErroOcorrido.LbMensagem.setText("Ocorreu uma falha na importação, o processo será abortado");
            fdErroOcorrido.LbInformaErro.setText("Falha geral na importação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void numeroTotalCoparti() {
        CoparticipacaoDao pdao = new CoparticipacaoDao();
        try {
            for (Coparticipacao c : pdao.readCountCopForNf(numeroNFe)) {
                lbNTotalCop.setText(Integer.toString(c.getNumeroTotalCop()));
            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor total da coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    public void valorTotalPro() {
        CoparticipacaoDao pdao = new CoparticipacaoDao();
        try {
            for (Coparticipacao c : pdao.readSumNfeImport(numeroNFe)) {

                DecimalFormat valorFormata = new DecimalFormat("0.#####");
                String valorFormat = valorFormata.format(c.getValorCop()).replace('.', ',');
                lbValorTotalNfe.setText("R$ " + valorFormat);

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor total da coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListaNf = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        JtListaCopartsTitulo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbNTotalCop = new javax.swing.JLabel();
        lbValorTotalNfe = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbListaNf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº NF-e", "Data Arquivo", "Data Vencimento"
            }
        ));
        tbListaNf.setMaximumSize(new java.awt.Dimension(0, 32767));
        tbListaNf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaNfMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbListaNf);

        JtListaCopartsTitulo.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
        JtListaCopartsTitulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Cop.", "Funcionário", "Beneficiário", "Procedimento", "Valor Pro.", "Data Procedimento", "Cod. Par.", "Valor Par.", "Venci. Par."
            }
        ));
        JtListaCopartsTitulo.setMaximumSize(new java.awt.Dimension(2073741824, 0));
        jScrollPane3.setViewportView(JtListaCopartsTitulo);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update_black.png"))); // NOI18N
        jButton1.setText("ATUALIZAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        jButton2.setText("RELATÓRIO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton3.setText("SAIR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Resultados:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(0, 32767));

        jLabel1.setText("Número total de coparticipações:");

        jLabel3.setText("Valor total da nota:");

        lbNTotalCop.setText("resultado");

        lbValorTotalNfe.setText("resultado");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addGap(7, 7, 7)
                        .addComponent(lbNTotalCop, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(9, 9, 9)
                        .addComponent(lbValorTotalNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(349, 349, 349))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lbNTotalCop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lbValorTotalNfe))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Listagem de Importações", jPanel1);

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

    private void tbListaNfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaNfMouseClicked
        numeroNFe = Integer.parseInt(tbListaNf.getValueAt(tbListaNf.getSelectedRow(), 0).toString());
        try {
            listaCopartiPorTitulo();
            numeroTotalCoparti();
            valorTotalPro();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("ao listar, tente novamente");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_tbListaNfMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listaNfs();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (numeroNFe != 0) {

            try {
                Connection con = ConnectionFactory.getConnection();

                Map<String, Object> map = new HashMap<>();
                int numeNF = numeroNFe;
                try {
                    map.put("FiltroNumeroNfe", numeNF);
                    JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relCopImportForNfe.jasper", map, con);
                    JasperViewer.viewReport(print, false);
                } catch (JRException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("gerar relatório");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar uma NF na tabela", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JtListaCopartsTitulo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbNTotalCop;
    private javax.swing.JLabel lbValorTotalNfe;
    private javax.swing.JTable tbListaNf;
    // End of variables declaration//GEN-END:variables
}
