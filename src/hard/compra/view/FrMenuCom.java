package hard.compra.view;

import hard.config.Config;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.portaria.view.FrMenuPT;
import hard.rh.view.FrSobre;
import hard.rh.view.FrcadEmpresa;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class FrMenuCom extends javax.swing.JFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";

    int codUsuario = 0;
    private boolean FrCadEmpresa;
    private boolean FrRatingView;
    private boolean FrCadDivergeNFE;
    private boolean FrConDivergeNFE;

    public boolean isFrCadEmpresa() {
        return FrCadEmpresa;
    }

    public void setFrCadEmpresa(boolean FrCadEmpresa) {
        this.FrCadEmpresa = FrCadEmpresa;
    }

    public boolean isFrRatingView() {
        return FrRatingView;
    }

    public void setFrRatingView(boolean FrRatingView) {
        this.FrRatingView = FrRatingView;
    }

    public boolean isFrCadDivergeNFE() {
        return FrCadDivergeNFE;
    }

    public void setFrCadDivergeNFE(boolean FrCadDivergeNFE) {
        this.FrCadDivergeNFE = FrCadDivergeNFE;
    }

    public boolean isFrConDivergeNFE() {
        return FrConDivergeNFE;
    }

    public void setFrConDivergeNFE(boolean FrConDivergeNFE) {
        this.FrConDivergeNFE = FrConDivergeNFE;
    }

    public FrMenuCom() {

    }

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrMenuCom(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        setTitle("System Hardsearch - CONTROLE COMPRAS - Versão: " + versao);

        setLocationRelativeTo(null);
        setExtendedState(FrMenuPT.MAXIMIZED_BOTH);

        URL LocalIcon = getClass().getResource("/img/hs-icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(LocalIcon);
        this.setIconImage(icone);

        JmUsuarioLogado.setText(usuario);

        //LOG DE ACESSO A TELA - CONTROLE DE SESSÃO
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = c.getCodUsuario();
            }

            validaAcesso();

            u.setCodUsuario(codUsuario);
            String codTela = "FrMenuCom";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void validaAcesso() throws ClassNotFoundException {
        PermissaoDao pdao = new PermissaoDao();
        for (FrMenuCom co : pdao.listAcessFrMenuCom(codUsuario)) {
            JmCadEmpresa.setEnabled(co.isFrCadEmpresa());
            JmImportaAvalia.setEnabled(co.isFrRatingView());
            JmCadDiverge.setEnabled(co.isFrCadDivergeNFE());
            JmConDiverge.setEnabled(co.isFrConDivergeNFE());
        }

    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        if (operation != DISPOSE_ON_CLOSE) {
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        JmCadEmpresa = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        JmImportaAvalia = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        JmCadDiverge = new javax.swing.JMenuItem();
        JmConDiverge = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        JmUsuarioLogado = new javax.swing.JMenuItem();
        contentMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register.png"))); // NOI18N
        fileMenu.setMnemonic('f');
        fileMenu.setText("Cadastrar");

        JmCadEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/company.png"))); // NOI18N
        JmCadEmpresa.setText("Cadastro de Empresa");
        JmCadEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadEmpresaActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadEmpresa);

        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buy.png"))); // NOI18N
        jMenu1.setText("Compras");

        JmImportaAvalia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/import.png"))); // NOI18N
        JmImportaAvalia.setMnemonic('o');
        JmImportaAvalia.setText("Importação e Envio de Avaliação");
        JmImportaAvalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmImportaAvaliaActionPerformed(evt);
            }
        });
        jMenu1.add(JmImportaAvalia);

        menuBar.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nfe-icon.png"))); // NOI18N
        jMenu3.setText("NF-e");

        JmCadDiverge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/warning-buy.png"))); // NOI18N
        JmCadDiverge.setText("Lançamento de divergência");
        JmCadDiverge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadDivergeActionPerformed(evt);
            }
        });
        jMenu3.add(JmCadDiverge);

        JmConDiverge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lanca_ajuste.png"))); // NOI18N
        JmConDiverge.setText("Listagem de divergências por NF-e");
        JmConDiverge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmConDivergeActionPerformed(evt);
            }
        });
        jMenu3.add(JmConDiverge);

        menuBar.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/relatorios.png"))); // NOI18N
        jMenu4.setText("Consultas e Relatórios");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        jMenuItem1.setText("Relatório de Divergencias");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        menuBar.add(jMenu4);

        helpMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        JmUsuarioLogado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/userMenor.png"))); // NOI18N
        JmUsuarioLogado.setMnemonic('a');
        JmUsuarioLogado.setText("Usuario");
        helpMenu.add(JmUsuarioLogado);

        contentMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/about-me.png"))); // NOI18N
        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Sobre");
        contentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentMenuItem);

        menuBar.add(helpMenu);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jMenu2.setText("Exit");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1141, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void JmImportaAvaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmImportaAvaliaActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrRatingView rt = new FrRatingView(user, ipDesktop, nameDesktop);
            desktopPane.add(rt);
            rt.setVisible(true);
            rt.setPosicao();
        } catch (UnknownHostException e) {
        }
    }//GEN-LAST:event_JmImportaAvaliaActionPerformed

    private void JmCadEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadEmpresaActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrcadEmpresa Emp = null;
            Emp = new FrcadEmpresa(user, ipDesktop, nameDesktop);
            desktopPane.add(Emp);
            Emp.setVisible(true);
            Emp.setPosicao();

        } catch (UnknownHostException e) {
        }
    }//GEN-LAST:event_JmCadEmpresaActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        dispose();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentMenuItemActionPerformed
        FrSobre sob = null;
        sob = new FrSobre();

        desktopPane.add(sob);
        sob.setVisible(true);
        sob.setPosicao();
    }//GEN-LAST:event_contentMenuItemActionPerformed

    private void JmCadDivergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadDivergeActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadDivergeNFE nfe = null;
            nfe = new FrCadDivergeNFE(user, ipDesktop, nameDesktop);
            desktopPane.add(nfe);
            nfe.setPosicao();
            nfe.setVisible(true);
        } catch (UnknownHostException e) {
        }

    }//GEN-LAST:event_JmCadDivergeActionPerformed

    private void JmConDivergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmConDivergeActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrConDivergeNFE nfed = null;
            nfed = new FrConDivergeNFE(user, ipDesktop, nameDesktop);
            desktopPane.add(nfed);
            nfed.setPosicao();
            nfed.setVisible(true);
        } catch (UnknownHostException e) {
        }
    }//GEN-LAST:event_JmConDivergeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        FrRelatorioNFe nfe = null;
        nfe = new FrRelatorioNFe();
        desktopPane.add(nfe);
        nfe.setPosicao();
        nfe.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrMenuCom().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JmCadDiverge;
    private javax.swing.JMenuItem JmCadEmpresa;
    private javax.swing.JMenuItem JmConDiverge;
    private javax.swing.JMenuItem JmImportaAvalia;
    private javax.swing.JMenuItem JmUsuarioLogado;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
