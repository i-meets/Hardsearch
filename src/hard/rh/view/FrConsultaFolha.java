package hard.rh.view;

import hard.home.view.FDErroOcorrido;
import hard.rh.dao.FolhaDao;
import hard.rh.model.Folha;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrConsultaFolha extends javax.swing.JInternalFrame {

    FDErroOcorrido fdErroOcorrido;
    
    public FrConsultaFolha() {
        initComponents();
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 4, (d.height - this.getSize().height) / 4);
    }

    public void listarFolhaNCadFuncio() throws ClassNotFoundException {

        DefaultTableModel modelo = (DefaultTableModel) JtListaFuncio.getModel();
        modelo.setNumRows(0);
        FolhaDao fdao = new FolhaDao();

        int mes = 0;

        if (JbSelectMes.getSelectedItem().equals("1 - Janeiro")) {
            mes = 1;
        } else if (JbSelectMes.getSelectedItem().equals("2 - Fevereiro")) {
            mes = 2;
        } else if (JbSelectMes.getSelectedItem().equals("3 - Março")) {
            mes = 3;
        } else if (JbSelectMes.getSelectedItem().equals("4 - Abril")) {
            mes = 4;
        } else if (JbSelectMes.getSelectedItem().equals("5 - Maio")) {
            mes = 5;
        } else if (JbSelectMes.getSelectedItem().equals("6 - Junho")) {
            mes = 6;
        } else if (JbSelectMes.getSelectedItem().equals("7 - Julho")) {
            mes = 7;
        } else if (JbSelectMes.getSelectedItem().equals("8 - Agosto")) {
            mes = 8;
        } else if (JbSelectMes.getSelectedItem().equals("9 - Setembro")) {
            mes = 9;
        } else if (JbSelectMes.getSelectedItem().equals("10 - Outubro")) {
            mes = 10;
        } else if (JbSelectMes.getSelectedItem().equals("11 - Novembro")) {
            mes = 11;
        } else if (JbSelectMes.getSelectedItem().equals("12 - Dezembro")) {
            mes = 12;
        } else {
            System.out.println("erro");
        }
        int numero = 1;
        for (Folha f : fdao.readFolhaForFuncioNGerada(mes)) {

            modelo.addRow(new Object[]{
                numero,
                f.getFr_codFuncionario(),
                f.getFr_nomeFuncionario()

            });
            numero++;

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtListaFuncio = new javax.swing.JTable();
        JbSelectMes = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Consulta folhas não Geradas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        JtListaFuncio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Qtd", "Cod. Funcionário", "Nome Funcionário"
            }
        ));
        jScrollPane2.setViewportView(JtListaFuncio);
        if (JtListaFuncio.getColumnModel().getColumnCount() > 0) {
            JtListaFuncio.getColumnModel().getColumn(0).setMinWidth(50);
            JtListaFuncio.getColumnModel().getColumn(0).setPreferredWidth(50);
            JtListaFuncio.getColumnModel().getColumn(0).setMaxWidth(50);
            JtListaFuncio.getColumnModel().getColumn(1).setPreferredWidth(120);
            JtListaFuncio.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        JbSelectMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<SELECIONE>", "1 - Janeiro", "2 - Fevereiro", "3 - Março", "4 - Abril", "5 - Maio", "6 - Junho", "7 - Julho", "8 - Agosto", "9 - Setembro", "10 - Outubro", "11 - Novembro", "12 - Dezembro" }));
        JbSelectMes.setMinimumSize(new java.awt.Dimension(110, 25));
        JbSelectMes.setName(""); // NOI18N
        JbSelectMes.setPreferredSize(new java.awt.Dimension(110, 26));

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JbSelectMes, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JbSelectMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Consulta de folha não gerada", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (JbSelectMes.getSelectedItem().equals("<SELECIONE>")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve selecionar o mês da folha");
        } else {

            try {
                listarFolhaNCadFuncio();
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar funcionários");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JbSelectMes;
    private javax.swing.JTable JtListaFuncio;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
