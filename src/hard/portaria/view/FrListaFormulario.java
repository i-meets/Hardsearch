package hard.portaria.view;

import hard.config.ConnectionCloudFactory;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.portaria.dao.FormularioDao;
import hard.portaria.model.Formulario;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrListaFormulario extends javax.swing.JInternalFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0811.0";

    FDErroOcorrido fdErroOcorrido;
    int codForm = 0;
    String nomeFun = "";

    public FrListaFormulario(String usuario, String ipDesktop, String nameDesktop) {
        title = "Listagem de Autodeclarações Preenchidas Online - V" + versao;
        initComponents();

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            BtDelete.setVisible(false);
            if (codUsuario.equals("1") || codUsuario.equals("2")) {
                BtDelete.setVisible(true);
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrListaFormulario";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("valida usuário");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void listaFormulario() {
        FormularioDao dao = new FormularioDao();
        DefaultTableModel model = (DefaultTableModel) TbListaFormulario.getModel();
        model.setRowCount(0);
        try {
            String codFun = EdBuscaCracha.getText();
            String nomeFuncio = EdBuscaNome.getText();
            java.util.Date dataIni = JDcDataInicial.getDate();
            long dtInicial = dataIni.getTime();
            java.sql.Date dateInicial = new java.sql.Date(dtInicial);

            java.util.Date dataFin = JDcDataFinal.getDate();
            long dtFinal = dataFin.getTime();
            java.sql.Date dateFinal = new java.sql.Date(dtFinal);

            for (Formulario fo : dao.listFormulario(codFun, nomeFuncio, dateInicial, dateFinal)) {

                boolean p1 = false;
                boolean p2 = false;
                boolean p3 = false;
                boolean p4 = false;
                boolean p5 = false;
                boolean p6 = false;
                boolean p7 = false;
                boolean p8 = false;
                boolean p9 = false;
                boolean p10 = false;
                boolean p11 = false;
                boolean p12 = false;

                if (fo.getSintomas() == 1) {
                    p1 = true;
                }
                if (fo.getContato() == 1) {
                    p2 = true;
                }
                if (fo.getFebre() == 1) {
                    p3 = true;
                }
                if (fo.getTosse() == 1) {
                    p4 = true;
                }
                if (fo.getD_respirar() == 1) {
                    p5 = true;
                }
                if (fo.getCalafrios() == 1) {
                    p6 = true;
                }
                if (fo.getDor_muscular() == 1) {
                    p7 = true;
                }
                if (fo.getDor_garganta() == 1) {
                    p8 = true;
                }
                if (fo.getP_olfato() == 1) {
                    p9 = true;
                }
                if (fo.getNausea() == 1) {
                    p10 = true;
                }
                if (fo.getDor_cabeca() == 1) {
                    p11 = true;
                }
                if (fo.getMetros_2() == 1) {
                    p12 = true;
                }

                String status = "";
                switch (fo.getStatus()) {
                    case 0:
                        status = "NÃO REALIZADA";
                        break;
                    case 1:
                        status = "AUTORIZADA";
                        break;
                    case 2:
                        status = "NÃO PERMITIDA";
                        break;
                    default:
                        break;
                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String data = "";
                if (dt.format(fo.getData_sintoma()).equals("01/01/2000")) {
                    data = "";
                } else {
                    data = dt.format(fo.getData_sintoma());
                }

                model.addRow(new Object[]{
                    fo.getCodFormulario(),
                    fo.getFr_codFuncionario(),
                    fo.getFr_nomeFuncionario(),
                    fo.getFr_setorFuncionario(),
                    p1,
                    p2,
                    p3,
                    p4,
                    p5,
                    p6,
                    p7,
                    p8,
                    p9,
                    p10,
                    p11,
                    data,
                    p12,
                    dt.format(fo.getData_p()),
                    status,
                    fo.getTemp() + "º"
                });

            }

        } catch (ClassNotFoundException | SQLException | NullPointerException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar respostas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        EdBuscaNome = new javax.swing.JTextField();
        EdBuscaCracha = new javax.swing.JTextField();
        JDcDataInicial = new com.toedter.calendar.JDateChooser();
        JDcDataFinal = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaFormulario = new javax.swing.JTable();
        BtPrint = new javax.swing.JButton();
        BtDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });

        EdBuscaCracha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Crachá:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        EdBuscaCracha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaCrachaKeyPressed(evt);
            }
        });

        JDcDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data inicial:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        JDcDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data final:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdBuscaCracha, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JDcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(JDcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JDcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JDcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(EdBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EdBuscaCracha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        TbListaFormulario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codFor", "Cód. Fun.", "Funcionário", "Setor", "Sintomas 7 dias", "Teve Contato", "Febre", "Tosse", "Dificul. Respirar", "Calafrios", "Dor muscular", "Dor garganta", "Perda olfato", "Nausea", "Dor cabeça", "Data sintomas", "Menos 2 metros", "Data Formulário", "Entrada", "Temperatura"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbListaFormulario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaFormularioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaFormulario);
        if (TbListaFormulario.getColumnModel().getColumnCount() > 0) {
            TbListaFormulario.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaFormulario.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaFormulario.getColumnModel().getColumn(0).setMaxWidth(0);
            TbListaFormulario.getColumnModel().getColumn(1).setMinWidth(30);
            TbListaFormulario.getColumnModel().getColumn(1).setPreferredWidth(75);
            TbListaFormulario.getColumnModel().getColumn(1).setMaxWidth(110);
            TbListaFormulario.getColumnModel().getColumn(2).setMinWidth(130);
            TbListaFormulario.getColumnModel().getColumn(2).setPreferredWidth(250);
            TbListaFormulario.getColumnModel().getColumn(2).setMaxWidth(300);
            TbListaFormulario.getColumnModel().getColumn(3).setMinWidth(50);
            TbListaFormulario.getColumnModel().getColumn(3).setPreferredWidth(110);
            TbListaFormulario.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        BtPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        BtPrint.setText("IMPRIMIR");
        BtPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPrintActionPerformed(evt);
            }
        });

        BtDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtDelete.setText("EXCLUÍR");
        BtDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(BtPrint)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtPrint)
                        .addComponent(BtDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listagem de Formulários Preenchidos", jPanel1);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (JDcDataFinal.getDate() == null || JDcDataInicial.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar a data no filtro", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            listaFormulario();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (JDcDataFinal.getDate() == null || JDcDataInicial.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Você deve informar a data no filtro", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                listaFormulario();
            }
        }
    }//GEN-LAST:event_EdBuscaNomeKeyPressed

    private void EdBuscaCrachaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaCrachaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (JDcDataFinal.getDate() == null || JDcDataInicial.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Você deve informar a data no filtro", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                listaFormulario();
            }
        }
    }//GEN-LAST:event_EdBuscaCrachaKeyPressed

    private void BtPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPrintActionPerformed
        if (codForm != 0) {

            try {

                Connection con = ConnectionCloudFactory.getConnection();
                Map<String, Object> map = new HashMap<>();
                map.put("codFormulario", codForm);
                JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\formularioAutoDeclara.jasper", map, con);
                JasperViewer.viewReport(print, false);

            } catch (ClassNotFoundException | JRException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("gerar formulário impresso");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar um formulário na tabela", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtPrintActionPerformed

    private void TbListaFormularioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaFormularioMouseClicked

        codForm = Integer.parseInt(TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 0).toString());
        nomeFun = TbListaFormulario.getValueAt(TbListaFormulario.getSelectedRow(), 2).toString();

    }//GEN-LAST:event_TbListaFormularioMouseClicked

    private void BtDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtDeleteActionPerformed
        if (codForm != 0) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Deseja mesmo excluír o formulário do funcionário: " + nomeFun + " ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                try {
                    FormularioDao dao = new FormularioDao();
                    Formulario f = new Formulario();
                    f.setCodFormulario(codForm);
                    dao.delete(f);
                    listaFormulario();
                    codForm = 0;
                } catch (ClassNotFoundException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("deletar formulário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o formulário para excluír", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtDelete;
    private javax.swing.JButton BtPrint;
    private javax.swing.JTextField EdBuscaCracha;
    private javax.swing.JTextField EdBuscaNome;
    private com.toedter.calendar.JDateChooser JDcDataFinal;
    private com.toedter.calendar.JDateChooser JDcDataInicial;
    private javax.swing.JTable TbListaFormulario;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
