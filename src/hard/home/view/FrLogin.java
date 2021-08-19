package hard.home.view;

import hard.config.Config;
import hard.home.dao.ContVersaoDao;
import hard.home.dao.UsuarioDao;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class FrLogin extends javax.swing.JFrame {

    /**
     * Creates new form FrLogin
     */
    String versao = "1.0-21.0812.0";

    public FrLogin() throws ClassNotFoundException {
        initComponents();
        lbEyeOpen.setVisible(false);
        setLocationRelativeTo(null);
        EdLogin.requestFocus();
        URL LocalIcon = getClass().getResource("/img/hs-icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(LocalIcon);
        this.setIconImage(icone);
        verificaVersao();
        Lbversao.setText("Versão: " + versao);
        IpBanco();
        ListUser();
    }

    public void IpBanco() {

        String arq = "C:\\Hardsearch\\config\\map.ini";
        File f = new File(arq);
        String database = null;

        if (!f.exists()) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao buscar map.ini do Banco de dados.\nIncorreto ou não existe!", "ERRO", JOptionPane.ERROR_MESSAGE);
            LbIpBanco.setText("banco de dados não encontrado");
            LbIpBanco.setForeground(Color.decode("#ff0000"));
        } else {

            String conteudo = Config.Read(arq);
            database = conteudo.split(" ")[0];

            if (arq.isEmpty()) {

                JOptionPane.showMessageDialog(rootPane, "Erro ao ler IP do Banco de dados.\nIncorreto ou não existe!", "ERRO", JOptionPane.ERROR_MESSAGE);
                LbIpBanco.setText("banco de dados não encontrado");
                LbIpBanco.setForeground(Color.decode("#ff0000"));
            } else {

                if (!database.equals("192.168.24.252")) {
                    LbBancoTeste.setText("*BANCO DE TESTE");
                } else {
                    LbBancoTeste.setText(null);
                }

                LbIpBanco.setText(database);

            }

        }
    }

    public void ListUser() {
        String arq = "C:\\Hardsearch\\user.ini";
        File f = new File(arq);
        String iUser = null;

        if (!f.exists()) {
            EdLogin.requestFocus();
        } else {

            String conteudo = Config.Read(arq);
            iUser = conteudo.split(" ")[0];

            if (arq.isEmpty()) {

                EdLogin.requestFocus();

            } else {

                EdLogin.setText(iUser);
                EdSenha.requestFocus();
            }
        }
    }

    public void verificaVersao() throws ClassNotFoundException {

        try {
            ContVersaoDao vdao = new ContVersaoDao();

//        Alterar a cada liberação de versão do sitema
            if (vdao.checkVersao("FrLogin", versao)) {

            } else {
                int input = JOptionPane.showConfirmDialog(null, "  Uma nova versão do sistema está disponível."
                        + "\n  Deseja realizar a atualização?\n\n  Recomendamos sempre manter a aplicação\n atualizada." + "\n_______________________________________\n", "NOVA VERSÃO DISPONÍVEL", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {

                    atualizarApp();
                } else {

                }
            }

        } catch (HeadlessException | IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void checkLogin() throws SQLException, ClassNotFoundException {

        UsuarioDao pdao = new UsuarioDao();
        try {

            if (EdLogin.getText().equals("") || EdSenha.getPassword().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha os dados do login!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                EdLogin.requestFocus();
            } else {
                String user = "";
                String ipDesktop = "";
                String nameDesktop = "";

                if (pdao.checkLogin(EdLogin.getText(), EdSenha.getText())) {

                    user = EdLogin.getText();
                    ipDesktop = InetAddress.getLocalHost().getHostAddress();
                    nameDesktop = InetAddress.getLocalHost().getHostName();

                    FrMenu Fr = new FrMenu(user, ipDesktop, nameDesktop);
                    saveUserLogin();
                    Fr.setVisible(true);

                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Usuário e senha incorretos", "ERRO", JOptionPane.ERROR_MESSAGE);
                    EdSenha.setText(null);

                }

            }
        } catch (HeadlessException | ClassNotFoundException | UnknownHostException e) {
            System.out.println(e);
        }
    }

    public void saveUserLogin() throws ClassNotFoundException {
        try {

            String arq = "C:\\Hardsearch\\user.ini";

            String texto = EdLogin.getText();

            if (texto != null) {
                if (Config.Write(arq, texto)) {

                } else {
                    System.out.println("erro");
                }
            } else {
                System.out.println("cancelado");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        if (operation != DISPOSE_ON_CLOSE) {
            dispose();
        }
    }

    public void atualizarApp() throws IOException {
        try {
            java.awt.Desktop.getDesktop().open(new File("\\\\192.168.24.252\\Hardsearch\\updateHard.bat"));
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao tentar atualizar\nErro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        EdLogin = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Lbversao = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        LbIpBanco = new javax.swing.JLabel();
        LbBancoTeste = new javax.swing.JLabel();
        lbEyeClose = new javax.swing.JLabel();
        lbEyeOpen = new javax.swing.JLabel();
        EdSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("SENHA");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, -1, -1));

        EdLogin.setFont(new java.awt.Font("sansserif", 1, 11)); // NOI18N
        EdLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdLoginKeyPressed(evt);
            }
        });
        jPanel1.add(EdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 149, -1));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 11)); // NOI18N
        jButton1.setText("LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 150, 30));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("USUÁRIO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 11)); // NOI18N
        jLabel6.setText("HARDSEARCH.COM.BR");
        jLabel6.setToolTipText("");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 330, -1, -1));

        Lbversao.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        Lbversao.setForeground(new java.awt.Color(255, 0, 0));
        Lbversao.setText("Versão ");
        jPanel1.add(Lbversao, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update_black.png"))); // NOI18N
        jButton2.setText("Atualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, -1));

        LbIpBanco.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        LbIpBanco.setText("Banco de dados");
        jPanel1.add(LbIpBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, -1, -1));

        LbBancoTeste.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 9)); // NOI18N
        LbBancoTeste.setForeground(new java.awt.Color(255, 0, 51));
        LbBancoTeste.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(LbBancoTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 150, 20));

        lbEyeClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eye_close.png"))); // NOI18N
        lbEyeClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbEyeCloseMouseClicked(evt);
            }
        });
        lbEyeClose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbEyeCloseKeyPressed(evt);
            }
        });
        jPanel1.add(lbEyeClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 182, 18, 24));

        lbEyeOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eye_open.png"))); // NOI18N
        lbEyeOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbEyeOpenMouseClicked(evt);
            }
        });
        lbEyeOpen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbEyeOpenKeyPressed(evt);
            }
        });
        jPanel1.add(lbEyeOpen, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 182, 18, 24));

        EdSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(EdSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 150, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 362));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int input = JOptionPane.showConfirmDialog(null,
                "Deseja realizar a atualização do sistema?\nÉ recomendado manter o sistema sempre atualizado.", "Atualização do Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (input == 0) {

            try {

                atualizarApp();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrLogin.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Atualização cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("http://www.hardsearch.com.br/andreoliveira"));
        } catch (IOException | URISyntaxException e) {
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkLogin();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrLogin.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FrLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            checkLogin();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrLogin.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void EdLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdLoginKeyPressed
        try {

            if (evt.getKeyCode() == KeyEvent.VK_F2) {

                String arq = "C:\\Hardsearch\\config\\map.ini";

                String texto = JOptionPane.showInputDialog(null, "Informe o novo ip do servidor:");

                if (texto != null) {
                    if (Config.Write(arq, texto)) {
                        IpBanco();
                        LbIpBanco.setText(texto);
                        LbIpBanco.setForeground(Color.decode("#000000"));

                    } else {
                        System.out.println("erro");
                    }

                } else {
                    System.out.println("cancelado");
                }

            }
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_EdLoginKeyPressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed

    }//GEN-LAST:event_jPanel1KeyPressed

    private void EdSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkLogin();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FrLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_EdSenhaKeyPressed

    private void lbEyeOpenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbEyeOpenKeyPressed

    }//GEN-LAST:event_lbEyeOpenKeyPressed

    private void lbEyeCloseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbEyeCloseKeyPressed
        lbEyeOpen.setVisible(true);
    }//GEN-LAST:event_lbEyeCloseKeyPressed

    private void lbEyeCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbEyeCloseMouseClicked
        lbEyeOpen.setVisible(true);
        lbEyeClose.setVisible(false);
        if (EdSenha.getEchoChar() != '\u0000') {
            previousEchoChar = EdSenha.getEchoChar();
        }
        EdSenha.setEchoChar('\u0000');
    }//GEN-LAST:event_lbEyeCloseMouseClicked

    private void lbEyeOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbEyeOpenMouseClicked
        lbEyeClose.setVisible(true);
        lbEyeOpen.setVisible(false);
        EdSenha.setEchoChar('*');
    }//GEN-LAST:event_lbEyeOpenMouseClicked

    private char previousEchoChar = '\u2022';

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FrLogin().setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FrLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EdLogin;
    private javax.swing.JPasswordField EdSenha;
    private javax.swing.JLabel LbBancoTeste;
    private javax.swing.JLabel LbIpBanco;
    private javax.swing.JLabel Lbversao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbEyeClose;
    private javax.swing.JLabel lbEyeOpen;
    // End of variables declaration//GEN-END:variables
}
