package hard.portaria.view;

import hard.config.Config;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.view.FrSobre;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class FrMenuPT extends javax.swing.JFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";
    FDErroOcorrido fdErroOcorrido;

    int codUsuario = 0;

    private boolean FrSolicitaSaida;
    private boolean FrContSaida;
    private boolean FrContAcesso;
    private boolean FrListaFormulario;

    public boolean isFrSolicitaSaida() {
        return FrSolicitaSaida;
    }

    public void setFrSolicitaSaida(boolean FrSolicitaSaida) {
        this.FrSolicitaSaida = FrSolicitaSaida;
    }

    public boolean isFrContSaida() {
        return FrContSaida;
    }

    public void setFrContSaida(boolean FrContSaida) {
        this.FrContSaida = FrContSaida;
    }

    public boolean isFrContAcesso() {
        return FrContAcesso;
    }

    public void setFrContAcesso(boolean FrContAcesso) {
        this.FrContAcesso = FrContAcesso;
    }

    public boolean isFrListaFormulario() {
        return FrListaFormulario;
    }

    public void setFrListaFormulario(boolean FrListaFormulario) {
        this.FrListaFormulario = FrListaFormulario;
    }

    public FrMenuPT(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        setTitle("System Hardsearch - CONTROLE PORTARIA - Versão: " + versao);

        setLocationRelativeTo(null);
        setExtendedState(FrMenuPT.MAXIMIZED_BOTH);

        URL LocalIcon = getClass().getResource("/img/hs-icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(LocalIcon);
        this.setIconImage(icone);

        JmUsuarioLogado.setText(usuario);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = c.getCodUsuario();
            }
            validaAcesso();

            u.setCodUsuario(codUsuario);
            String codTela = "FrMenuPT";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
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

    public FrMenuPT() {

    }

    public void validaAcesso() throws ClassNotFoundException {
        PermissaoDao pdao = new PermissaoDao();
        for (FrMenuPT pt : pdao.listAcessFrMenuPT(codUsuario)) {
            JmSolicitaSaida.setEnabled(pt.isFrSolicitaSaida());
            JmControlaSaida.setEnabled(pt.isFrContSaida());
            JmControlAcesso.setEnabled(pt.isFrContAcesso());
            JmControlAcesso.setEnabled(pt.isFrContAcesso());
            JmListaFormulario.setEnabled(pt.isFrListaFormulario());
        }

    }

    //CONFIGURAÇÃO PARA O JFORME NÃO FECHAR TODA A APLICAÇÃO
    @Override
    public void setDefaultCloseOperation(int operation) {
        if (operation != DISPOSE_ON_CLOSE) {
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator4 = new javax.swing.JSeparator();
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        JmExit = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        JmSolicitaSaida = new javax.swing.JMenuItem();
        JmControlaSaida = new javax.swing.JMenuItem();
        JmControlAcesso = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        JMRelPar = new javax.swing.JMenu();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        JmRelaAcessos = new javax.swing.JMenuItem();
        JmListaFormulario = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        JmSobre = new javax.swing.JMenuItem();
        JmUsuarioLogado = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register.png"))); // NOI18N
        fileMenu.setMnemonic('f');
        fileMenu.setText("Opções");

        JmExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        JmExit.setForeground(new java.awt.Color(255, 0, 51));
        JmExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        JmExit.setMnemonic('x');
        JmExit.setText("Exit");
        JmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmExitActionPerformed(evt);
            }
        });
        fileMenu.add(JmExit);

        menuBar.add(fileMenu);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/porteiro20.png"))); // NOI18N
        jMenu3.setText("Portaria");

        JmSolicitaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/saidaPessoa.png"))); // NOI18N
        JmSolicitaSaida.setText("Solicitação de Saída");
        JmSolicitaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmSolicitaSaidaActionPerformed(evt);
            }
        });
        jMenu3.add(JmSolicitaSaida);

        JmControlaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/porteiro.png"))); // NOI18N
        JmControlaSaida.setText("Controle de Solicitações de Saídas");
        JmControlaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmControlaSaidaActionPerformed(evt);
            }
        });
        jMenu3.add(JmControlaSaida);

        JmControlAcesso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pare.png"))); // NOI18N
        JmControlAcesso.setText("Controle de Acessos");
        JmControlAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmControlAcessoActionPerformed(evt);
            }
        });
        jMenu3.add(JmControlAcesso);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/face.png"))); // NOI18N
        jMenuItem1.setText("Validação de Acesso");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        menuBar.add(jMenu3);

        JMRelPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/relatorios.png"))); // NOI18N
        JMRelPar.setText("Consultas e Relatórios");
        JMRelPar.add(jSeparator6);

        JmRelaAcessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        JmRelaAcessos.setText("Relatório de Acessos");
        JmRelaAcessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmRelaAcessosActionPerformed(evt);
            }
        });
        JMRelPar.add(JmRelaAcessos);

        JmListaFormulario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controlepro.png"))); // NOI18N
        JmListaFormulario.setText("Listagem de Autodeclaração");
        JmListaFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmListaFormularioActionPerformed(evt);
            }
        });
        JMRelPar.add(JmListaFormulario);

        menuBar.add(JMRelPar);

        jMenu2.setForeground(new java.awt.Color(255, 0, 0));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jMenu2.setText("Exit");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        menuBar.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        jMenu4.setText("Ajuda");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });

        JmSobre.setText("Sobre");
        JmSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmSobreActionPerformed(evt);
            }
        });
        jMenu4.add(JmSobre);

        JmUsuarioLogado.setText("Usuário");
        jMenu4.add(JmUsuarioLogado);

        menuBar.add(jMenu4);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        dispose();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked

    }//GEN-LAST:event_jMenu4MouseClicked

    private void JmSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmSobreActionPerformed
        FrSobre sob = null;
        sob = new FrSobre();

        desktopPane.add(sob);
        sob.setVisible(true);
        sob.setPosicao();
    }//GEN-LAST:event_JmSobreActionPerformed

    private void JmSolicitaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmSolicitaSaidaActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrSolicitaSaida soli = new FrSolicitaSaida(user, ipDesktop, nameDesktop);
            desktopPane.add(soli);
            soli.setVisible(true);
            soli.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmSolicitaSaidaActionPerformed

    private void JmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmExitActionPerformed
        dispose();
    }//GEN-LAST:event_JmExitActionPerformed

    private void JmControlaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmControlaSaidaActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrContSaida ct;
            ct = new FrContSaida(user, ipDesktop, nameDesktop);
            desktopPane.add(ct);
            ct.setVisible(true);
            ct.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmControlaSaidaActionPerformed

    private void JmRelaAcessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmRelaAcessosActionPerformed
        FrRelatorioAcessos ct;
        ct = new FrRelatorioAcessos();
        desktopPane.add(ct);
        ct.setVisible(true);
        ct.setPosicao();
    }//GEN-LAST:event_JmRelaAcessosActionPerformed

    private void JmControlAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmControlAcessoActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrContAcesso ca;
            ca = new FrContAcesso(user, ipDesktop, nameDesktop);
            desktopPane.add(ca);
            ca.setVisible(true);
            ca.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmControlAcessoActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            String arq = "C:\\Hardsearch\\config\\map.ini";
            File f = new File(arq);
            String database = null;
            String conteudo = Config.Read(arq);
            database = conteudo.split(" ")[0];
            if (!f.exists()) {
                JOptionPane.showMessageDialog(rootPane, "Erro ao ler IP do Banco de dados.\nIncorreto ou não existe!", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else {
                if (arq.isEmpty()) {

                    JOptionPane.showMessageDialog(rootPane, "Erro ao ler IP do Banco de dados.\nIncorreto ou não existe!", "ERRO", JOptionPane.ERROR_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(rootPane, "BANCO DE DADOS: " + database + ":3306\\db_dp", "HARDSEARCH_DB_INFO", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        FrAvaliaAcesso a = null;
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            a = new FrAvaliaAcesso(user, ipDesktop, nameDesktop);
            desktopPane.add(a);
            a.setVisible(true);
            a.setPosicao();

        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void JmListaFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmListaFormularioActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrListaFormulario fl = new FrListaFormulario(user, ipDesktop, nameDesktop);
            desktopPane.add(fl);
            fl.setVisible(true);
            fl.setMaximum(true);
        } catch (PropertyVetoException | UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmListaFormularioActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        try {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//        } catch (Exception e) {
//        };
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
            java.util.logging.Logger.getLogger(FrMenuPT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrMenuPT().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMRelPar;
    private javax.swing.JMenuItem JmControlAcesso;
    private javax.swing.JMenuItem JmControlaSaida;
    private javax.swing.JMenuItem JmExit;
    private javax.swing.JMenuItem JmListaFormulario;
    private javax.swing.JMenuItem JmRelaAcessos;
    private javax.swing.JMenuItem JmSobre;
    private javax.swing.JMenuItem JmSolicitaSaida;
    private javax.swing.JMenuItem JmUsuarioLogado;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
