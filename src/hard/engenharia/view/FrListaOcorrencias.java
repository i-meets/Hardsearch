package hard.engenharia.view;

import hard.config.ConnectionFactory;
import hard.engenharia.dao.OcorrenciaDao;
import hard.engenharia.model.Ocorrencia;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import java.awt.Dimension;
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

public class FrListaOcorrencias extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "0.0";

    FDErroOcorrido fdErroOcorrido;

    public FrListaOcorrencias(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();

        title = "Listagem de Ocorrências - V" + versao;

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrListaOcorrencias";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 4);
    }

    public void listaOcorrencia() {
        try {

            OcorrenciaDao dao = new OcorrenciaDao();
            DefaultTableModel modelo = (DefaultTableModel) TbListaOcorre.getModel();
            modelo.setNumRows(0);
            DecimalFormat df = new DecimalFormat("R$ ##.##");
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

            String ferramenta = EdFerramenta.getText();
            String maqui = EdMaqui.getText();
            String opera = EdOpera.getText();
            String operaTurno = CbTurno.getSelectedItem().toString();
            if (operaTurno.equals("TURNO")) {
                operaTurno = "";
            }
            String grupo = CbGrupo.getSelectedItem().toString();
            if (grupo.equals("GRUPO")) {
                grupo = "";
            }
            java.util.Date datai = JdcDataInicial.getDate();
            long dti = datai.getTime();
            java.sql.Date dateInicial = new java.sql.Date(dti);

            java.util.Date dataf = JdcDataFinal.getDate();
            long dtf = dataf.getTime();
            java.sql.Date dateFinal = new java.sql.Date(dtf);
            Double valorTotal = 0.0;

            for (Ocorrencia o : dao.listOcorrencia(ferramenta, maqui, opera, operaTurno, grupo, dateInicial, dateFinal)) {
                valorTotal = valorTotal + o.getValorUltCompra();
                modelo.addRow(new Object[]{
                    o.getFr_codFerramenta(),
                    df.format(o.getValorUltCompra()),
                    o.getFr_grupoFerramenta(),
                    o.getFr_codMaquina(),
                    o.getFr_nomeFun(),
                    o.getFr_turnoFun(),
                    o.getFr_codigoItem(),
                    o.getMotivoQuebra(),
                    dt.format(o.getData())

                });
            }

            LbValorTotal.setText(df.format(valorTotal));
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ocorrência");
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
        JdcDataInicial = new com.toedter.calendar.JDateChooser();
        JdcDataFinal = new com.toedter.calendar.JDateChooser();
        CbTurno = new javax.swing.JComboBox<>();
        EdOpera = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        EdMaqui = new javax.swing.JTextField();
        EdFerramenta = new javax.swing.JTextField();
        BtRela = new javax.swing.JButton();
        CbGrupo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LbValorTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TbListaOcorre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ferramenta", "Valor", "Grupo", "Máquina", "Operador", "Turno", "Item", "Motivo", "Data"
            }
        ));
        jScrollPane1.setViewportView(TbListaOcorre);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        JdcDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Inicial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        JdcDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        JdcDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Final:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        CbTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TURNO", "NORMAL", "MANHA", "TARDE", "NOITE" }));

        EdOpera.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome operador:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscar.setText("BUSCAR");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        EdMaqui.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Máquina:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdFerramenta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Ferramenta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        BtRela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        BtRela.setText("RELATÓRIO");
        BtRela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRelaActionPerformed(evt);
            }
        });

        CbGrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GRUPO", "AÇO RAPIDO", "BITS ESPECIAIS", "BROCAS ESPECIAIS", "BROCHAS", "CANAL HELICOIDAL", "CANAL RETO", "CENTRAR", "CINTA", "COMPONENTES", "CORTE/CANAL", "DISCO", "DIVERSOS", "FERRAMENTA PERFILADA", "FIXAÇÃO DE FERRAMENTAS", "FIXAÇÃO DE MATERIAL", "FOLHA", "FRESAMENTO", "FURAÇÃO", "HASTE CÔNICA", "HASTE PARALELA", "LIMAS", "LIMAS ROTATIVAS", "MACHOS ESPECIAIS", "METAL DURO", "ÓLEOS/GRAXAS", "PONTA HELICOIDAL", "PORTA-FERRAMENTA", "ROSCA MÉTRICA", "ROSCA POLEGADA", "ROSQUEAMENTO", "SERRA/REBARBAGEM", "SOLDA", "TORNEAMENTO", "TORNEAMENTO EXTERNO", "TORNEAMENTO INTERNO", "OUTROS" }));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("CUSTO TOTAL:");

        LbValorTotal.setText("R$ 0,00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LbValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(LbValorTotal))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(JdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtRela)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EdFerramenta)
                    .addComponent(CbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(CbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EdMaqui, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(EdOpera, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(EdFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(EdOpera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EdMaqui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BtRela)
                                    .addComponent(BtBuscar)
                                    .addComponent(CbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listagem de Ocorrências", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        if (JdcDataInicial.getDate() == null || JdcDataFinal.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você precisa informar as datas para gerar a listagem", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            listaOcorrencia();
        }
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void BtRelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRelaActionPerformed
        try {
            Connection con = ConnectionFactory.getConnection();

            Map<String, Object> map = new HashMap<>();
            String codFerramenta = "%" + EdFerramenta.getText() + "%";
            String codMaquina = "%" + EdMaqui.getText() + "%";
            String operador = "%" + EdOpera.getText() + "%";
            String turnoOperador = CbTurno.getSelectedItem().toString();
            if (turnoOperador.equals("TURNO")) {
                turnoOperador = "%%";
            }
            String grupoFerra = CbGrupo.getSelectedItem().toString();
            if (grupoFerra.equals("GRUPO")) {
                grupoFerra = "%%";
            }

            java.util.Date dataIni = JdcDataInicial.getDate();
            long dtInicial = dataIni.getTime();
            java.sql.Date dataInicial = new java.sql.Date(dtInicial);

            java.util.Date dataFin = JdcDataFinal.getDate();
            long dtFinal = dataFin.getTime();
            java.sql.Date dataFinal = new java.sql.Date(dtFinal);

            try {
                map.put("codFerramenta", codFerramenta);
                map.put("codMaquina", codMaquina);
                map.put("operador", operador);
                map.put("turnoOperador", turnoOperador);
                map.put("grupoFerra", grupoFerra);
                map.put("dataInicial", dataInicial);
                map.put("dataFinal", dataFinal);
                JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relOcorreciasProd.jasper", map, con);
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

    }//GEN-LAST:event_BtRelaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtRela;
    private javax.swing.JComboBox<String> CbGrupo;
    private javax.swing.JComboBox<String> CbTurno;
    private javax.swing.JTextField EdFerramenta;
    private javax.swing.JTextField EdMaqui;
    private javax.swing.JTextField EdOpera;
    private com.toedter.calendar.JDateChooser JdcDataFinal;
    private com.toedter.calendar.JDateChooser JdcDataInicial;
    private javax.swing.JLabel LbValorTotal;
    private javax.swing.JTable TbListaOcorre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
