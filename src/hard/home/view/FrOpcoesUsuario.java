package hard.home.view;

import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.ContVersao;
import hard.home.model.Usuario;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FrOpcoesUsuario extends javax.swing.JInternalFrame {

    FDErroOcorrido fdErroOcorrido;

    int tipoUser;

    public int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }

    String codUsuario = "0";

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrOpcoesUsuario(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        LbUsuario.setText(usuario);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
            }
            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrOpcoesUsuario";
            u.setFr_codTela(codTela);
            ContVersaoDao vdao = new ContVersaoDao();
            for (ContVersao c : vdao.listTela(codTela)) {
                u.setFr_versaoTela(c.getVersaoAtualTela());
            }
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void alteraSenha() throws ClassNotFoundException {
        String novaSenha = EdNovaSenha.getText();
        String repeteSenha = EdRepeteSenha.getText();

        if (repeteSenha.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve repetir a senha!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else if (novaSenha.equals(repeteSenha)) {
            UsuarioDao dao = new UsuarioDao();
            Usuario u = new Usuario();
            u.setSenhaUsuario(EdNovaSenha.getText());
            u.setLoginUsuario(LbUsuario.getText());
            dao.updateSenha(u);
            EdNovaSenha.setText("");
            EdRepeteSenha.setText("");
            EdSenhaAtual.setText("");
        } else {
            JOptionPane.showMessageDialog(rootPane, "As senhas não coincidem", "Atenção", JOptionPane.WARNING_MESSAGE);
            EdNovaSenha.setText("");
            EdNovaSenha.requestFocus();
            EdRepeteSenha.setText("");
        }

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        EdSenhaAtual = new javax.swing.JPasswordField();
        EdNovaSenha = new javax.swing.JPasswordField();
        EdRepeteSenha = new javax.swing.JPasswordField();
        BtSalvar = new javax.swing.JButton();
        LbUsuario = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setTitle("Preferências do Usuário");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setText("Alterar Senha:");
        jPanel1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 10, -1, -1));

        EdSenhaAtual.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Senha atual:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel1.add(EdSenhaAtual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 230, -1));

        EdNovaSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nova senha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel1.add(EdNovaSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 230, -1));

        EdRepeteSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Repita nova senha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel1.add(EdRepeteSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 230, -1));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel1.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 230, 38));

        LbUsuario.setText("USUARIO");
        jPanel1.add(LbUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 40, -1, -1));

        jTabbedPane1.addTab("Alterar senha usuário", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        if (EdSenhaAtual.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve informar a senha atual", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                UsuarioDao dao = new UsuarioDao();
                if (dao.checkLogin(LbUsuario.getText(), EdSenhaAtual.getText())) {
                    alteraSenha();
                } else {
                    JOptionPane.showMessageDialog(null, "Senha atual incorreta", "Atenção", JOptionPane.WARNING_MESSAGE);
                    EdSenhaAtual.setText(null);
                    EdSenhaAtual.requestFocus();

                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtSalvar;
    private javax.swing.JPasswordField EdNovaSenha;
    private javax.swing.JPasswordField EdRepeteSenha;
    private javax.swing.JPasswordField EdSenhaAtual;
    private javax.swing.JLabel LbUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

}
