package hard.engenharia.view;

import hard.engenharia.dao.SolicitaDao;
import hard.engenharia.model.Solicita;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrProcessaSolicita extends javax.swing.JInternalFrame {

    private FDProcessaSolita fdContRev;

    String codAltera = "";
    String codSolicita = "";

    public FrProcessaSolicita() {
        initComponents();
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void listarSolicitacao() {
        try {

            SolicitaDao dao = new SolicitaDao();
            DefaultTableModel modelo = (DefaultTableModel) TbListaSolicita.getModel();
            modelo.setNumRows(0);
            int status = 0;
            if (cbFiltaPor.getSelectedItem().equals("AGUARDANDO")) {
                status = 1;
            } else if (cbFiltaPor.getSelectedItem().equals("FINALIZADO")) {
                status = 2;
            }

            for (Solicita s : dao.readSolicita(status)) {
                String situacao = "";
                if (s.getStatus() == 1) {
                    situacao = "Aguardando";
                } else {
                    situacao = "Finalizado";
                }
                modelo.addRow(new Object[]{
                    s.getCod(),
                    s.getFr_codAlteracao(),
                    s.getFr_codItem(),
                    s.getTitulo(),
                    "09/07/2021",
                    "admin",
                    situacao,
                    s.getDescricao()});

            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbListaSolicita = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TaRetorno = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        TaDescricaoSolicita = new javax.swing.JTextArea();
        EdBuscaItem = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        BtNovaRev = new javax.swing.JButton();
        BtEditaRev = new javax.swing.JButton();
        cbFiltaPor = new javax.swing.JComboBox<>();
        BtBuscaItem = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TbListaSolicita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codSolicita", "codAltera", "Cód. Item", "Título", "Data Solicitação", "Usuário", "Status", "descricao"
            }
        ));
        TbListaSolicita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbListaSolicitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbListaSolicita);
        if (TbListaSolicita.getColumnModel().getColumnCount() > 0) {
            TbListaSolicita.getColumnModel().getColumn(0).setMinWidth(0);
            TbListaSolicita.getColumnModel().getColumn(0).setPreferredWidth(0);
            TbListaSolicita.getColumnModel().getColumn(0).setMaxWidth(0);
            TbListaSolicita.getColumnModel().getColumn(1).setMinWidth(0);
            TbListaSolicita.getColumnModel().getColumn(1).setPreferredWidth(0);
            TbListaSolicita.getColumnModel().getColumn(1).setMaxWidth(0);
            TbListaSolicita.getColumnModel().getColumn(7).setMinWidth(0);
            TbListaSolicita.getColumnModel().getColumn(7).setPreferredWidth(0);
            TbListaSolicita.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        TaRetorno.setColumns(20);
        TaRetorno.setRows(5);
        TaRetorno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Retorno da Engenharia:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane2.setViewportView(TaRetorno);

        TaDescricaoSolicita.setColumns(20);
        TaDescricaoSolicita.setRows(5);
        TaDescricaoSolicita.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição da Solicitação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N
        jScrollPane3.setViewportView(TaDescricaoSolicita);

        EdBuscaItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Item:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 15));

        BtNovaRev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovaRev.setText("NOVA REVISÃO");
        BtNovaRev.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtNovaRev.setPreferredSize(new java.awt.Dimension(156, 65));
        BtNovaRev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovaRevActionPerformed(evt);
            }
        });
        jToolBar1.add(BtNovaRev);

        BtEditaRev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-black.png"))); // NOI18N
        BtEditaRev.setText("ALTERAR REVISÃO");
        BtEditaRev.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtEditaRev.setPreferredSize(new java.awt.Dimension(156, 65));
        BtEditaRev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(BtEditaRev);

        cbFiltaPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AGUARDANDO", "FINALIZADO", "TODOS" }));

        BtBuscaItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscaItem.setText("BUSCAR");
        BtBuscaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EdBuscaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtBuscaItem)
                        .addGap(18, 18, 18)
                        .addComponent(cbFiltaPor, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 249, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EdBuscaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtBuscaItem)
                        .addComponent(cbFiltaPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Controle de Solicitações", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtNovaRevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovaRevActionPerformed
        if (codSolicita.equals("") || codSolicita.equals("")) {
            JOptionPane.showMessageDialog(TaRetorno, "Você deve selecionar a linha da solicitação", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            this.fdContRev = new FDProcessaSolita(codAltera, codSolicita, null, true);
            this.fdContRev.setVisible(true);
        }
    }//GEN-LAST:event_BtNovaRevActionPerformed

    private void BtBuscaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaItemActionPerformed
        listarSolicitacao();
    }//GEN-LAST:event_BtBuscaItemActionPerformed

    private void TbListaSolicitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbListaSolicitaMouseClicked
        TaDescricaoSolicita.setText(TbListaSolicita.getValueAt(TbListaSolicita.getSelectedRow(), 7).toString());
        codSolicita = TbListaSolicita.getValueAt(TbListaSolicita.getSelectedRow(), 0).toString();
        codAltera = TbListaSolicita.getValueAt(TbListaSolicita.getSelectedRow(), 1).toString();
    }//GEN-LAST:event_TbListaSolicitaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscaItem;
    private javax.swing.JButton BtEditaRev;
    private javax.swing.JButton BtNovaRev;
    private javax.swing.JTextField EdBuscaItem;
    private javax.swing.JTextArea TaDescricaoSolicita;
    private javax.swing.JTextArea TaRetorno;
    private javax.swing.JTable TbListaSolicita;
    private javax.swing.JComboBox<String> cbFiltaPor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
