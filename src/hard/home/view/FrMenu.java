package hard.home.view;

import hard.compra.view.FrMenuCom;
import hard.config.Config;
import hard.engenharia.view.FrMenuEng;
import hard.rh.view.FrMenuDP;
import hard.portaria.view.FrMenuPT;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FrMenu extends javax.swing.JFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     * @throws java.lang.ClassNotFoundException
     */
    int codUsuario = 0;

    private boolean FrcadUsuario;
    private boolean FrPermissoes;
    private boolean FrMenuDP;
    private boolean FrMenuPT;
    private boolean FrMenuCom;
    private boolean FrAdmin;
    private boolean FrMenuEng;

    public boolean isFrcadUsuario() {
        return FrcadUsuario;
    }

    public void setFrcadUsuario(boolean FrcadUsuario) {
        this.FrcadUsuario = FrcadUsuario;
    }

    public boolean isFrPermissoes() {
        return FrPermissoes;
    }

    public void setFrPermissoes(boolean FrPermissoes) {
        this.FrPermissoes = FrPermissoes;
    }

    public boolean isFrMenuDP() {
        return FrMenuDP;
    }

    public void setFrMenuDP(boolean FrMenuDP) {
        this.FrMenuDP = FrMenuDP;
    }

    public boolean isFrMenuPT() {
        return FrMenuPT;
    }

    public void setFrMenuPT(boolean FrMenuPT) {
        this.FrMenuPT = FrMenuPT;
    }

    public boolean isFrMenuCom() {
        return FrMenuCom;
    }

    public void setFrMenuCom(boolean FrMenuCom) {
        this.FrMenuCom = FrMenuCom;
    }

    public boolean isFrAdmin() {
        return FrAdmin;
    }

    public void setFrAdmin(boolean FrAdmin) {
        this.FrAdmin = FrAdmin;
    }

    public boolean isFrMenuEng() {
        return FrMenuEng;
    }

    public void setFrMenuEng(boolean FrMenuEng) {
        this.FrMenuEng = FrMenuEng;
    }

    public FrMenu(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException, SQLException {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("System Hardsearch - GERENCIADOR - Versão: " + versao);
        URL LocalIcon = getClass().getResource("/img/hs-icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(LocalIcon);
        this.setIconImage(icone);

        LbUserLogado.setText(usuario);

        //LOG DE ACESSO A TELA - CONTROLE DE SESSÃO
        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = c.getCodUsuario();
            }

            //******* VALIDAÇÕES POR USUÁRIO SEMPRE APÓS A LINHA ABAIXO!!!*********
            u.setCodUsuario(codUsuario);
            //*******//*******//*******

            validaAcessos();
            String codTela = "FrMenu";
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

    public void validaAcessos() throws ClassNotFoundException, SQLException {

        PermissaoDao dao = new PermissaoDao();
        for (FrMenu mn : dao.listAcessFrMenu(codUsuario)) {
            JmCadUser.setEnabled(mn.isFrcadUsuario());
            JmPermissoes.setEnabled(mn.isFrPermissoes());
            BtDP.setEnabled(mn.isFrMenuDP());
            BtPorta.setEnabled(mn.isFrMenuPT());
            BtCompras.setEnabled(mn.isFrMenuCom());
            BtConfig.setEnabled(mn.isFrAdmin());
            BtEng.setEnabled(mn.isFrMenuEng());

        }

    }

    public FrMenu() throws ClassNotFoundException {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        BtDP = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        BtPorta = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        BtCompras = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        BtEng = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        BtConfig = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        LbUserLogado = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        JmCadUser = new javax.swing.JMenuItem();
        JmPermissoes = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jSeparator1.setForeground(new java.awt.Color(0, 204, 255));
        jToolBar1.add(jSeparator1);

        BtDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/humans.png"))); // NOI18N
        BtDP.setText("DPessoal");
        BtDP.setFocusable(false);
        BtDP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtDP.setMaximumSize(new java.awt.Dimension(100, 100));
        BtDP.setMinimumSize(new java.awt.Dimension(100, 100));
        BtDP.setPreferredSize(new java.awt.Dimension(100, 100));
        BtDP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtDPActionPerformed(evt);
            }
        });
        jToolBar1.add(BtDP);

        jSeparator2.setForeground(new java.awt.Color(0, 204, 255));
        jToolBar1.add(jSeparator2);

        BtPorta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/portaria-acesso.png"))); // NOI18N
        BtPorta.setText("Portaria");
        BtPorta.setFocusable(false);
        BtPorta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtPorta.setMaximumSize(new java.awt.Dimension(100, 100));
        BtPorta.setPreferredSize(new java.awt.Dimension(100, 100));
        BtPorta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPortaActionPerformed(evt);
            }
        });
        jToolBar1.add(BtPorta);

        jSeparator3.setForeground(new java.awt.Color(0, 204, 255));
        jToolBar1.add(jSeparator3);

        BtCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compra.png"))); // NOI18N
        BtCompras.setText("Compras");
        BtCompras.setFocusable(false);
        BtCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtCompras.setMaximumSize(new java.awt.Dimension(100, 100));
        BtCompras.setMinimumSize(new java.awt.Dimension(100, 100));
        BtCompras.setPreferredSize(new java.awt.Dimension(100, 100));
        BtCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtComprasActionPerformed(evt);
            }
        });
        jToolBar1.add(BtCompras);

        jSeparator4.setForeground(new java.awt.Color(0, 204, 255));
        jToolBar1.add(jSeparator4);

        BtEng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/engenharia.png"))); // NOI18N
        BtEng.setText("Engenharia");
        BtEng.setFocusable(false);
        BtEng.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtEng.setMaximumSize(new java.awt.Dimension(100, 100));
        BtEng.setMinimumSize(new java.awt.Dimension(100, 100));
        BtEng.setPreferredSize(new java.awt.Dimension(100, 100));
        BtEng.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtEng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEngActionPerformed(evt);
            }
        });
        jToolBar1.add(BtEng);

        jSeparator5.setForeground(new java.awt.Color(0, 204, 255));
        jSeparator5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar1.add(jSeparator5);

        BtConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/config-adm.png"))); // NOI18N
        BtConfig.setText("Configurações");
        BtConfig.setFocusable(false);
        BtConfig.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtConfig.setMaximumSize(new java.awt.Dimension(100, 100));
        BtConfig.setMinimumSize(new java.awt.Dimension(100, 100));
        BtConfig.setPreferredSize(new java.awt.Dimension(100, 100));
        BtConfig.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtConfigActionPerformed(evt);
            }
        });
        jToolBar1.add(BtConfig);

        jSeparator7.setForeground(new java.awt.Color(0, 204, 255));
        jSeparator7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar1.add(jSeparator7);

        jPanel1.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 105));

        LbUserLogado.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        LbUserLogado.setText("usuario");
        jPanel1.add(LbUserLogado, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 50, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/userMenor.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, -1, 20));

        jSeparator6.setBackground(new java.awt.Color(0, 204, 255));
        jSeparator6.setForeground(new java.awt.Color(0, 204, 255));
        jSeparator6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 70, 100, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 40, 120, 40));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register.png"))); // NOI18N
        fileMenu.setMnemonic('f');
        fileMenu.setText("Opções");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login_user.png"))); // NOI18N
        jMenuItem1.setText("Login");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        JmCadUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register_user.png"))); // NOI18N
        JmCadUser.setText("Cadastro de Usuários");
        JmCadUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadUserActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadUser);

        JmPermissoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/permissions.png"))); // NOI18N
        JmPermissoes.setText("Manutenção de Permissões");
        JmPermissoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmPermissoesActionPerformed(evt);
            }
        });
        fileMenu.add(JmPermissoes);

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

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/userMenor.png"))); // NOI18N
        jMenu2.setText("Usuário");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        menuBar.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tools.png"))); // NOI18N
        jMenu3.setText("Ferramentas");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calc-joshua.png"))); // NOI18N
        jMenuItem2.setText("Calculadora Joshua");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mp.png"))); // NOI18N
        jMenuItem3.setText("Calculadora de MP");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        menuBar.add(jMenu3);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        jMenu1.setText("Exit");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                    .addComponent(desktopPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane))
        );

        getAccessibleContext().setAccessibleName("System Hardsearch - CONTROLE DPESSOAL - Gerenciador do Sistema - Versão: 1.0-20.0907.0");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            FrLogin log = new FrLogin();
            log.setVisible(true);
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("Re-login");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void JmCadUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadUserActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrcadUsuario cadU = null;
            cadU = new FrcadUsuario(user, ipDesktop, nameDesktop);
            desktopPane.add(cadU);
            cadU.setVisible(true);
            cadU.requestFocus();
            cadU.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadUserActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrOpcoesUsuario ad = null;
            ad = new FrOpcoesUsuario(user, ipDesktop, nameDesktop);
            desktopPane.add(ad);
            ad.setVisible(true);
            ad.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jMenu2MouseClicked

    private void BtDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtDPActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrMenuDP FrDP = new FrMenuDP(user, ipDesktop, nameDesktop);
            FrDP.setVisible(true);
        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtDPActionPerformed

    private void BtPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPortaActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrMenuPT FrPT = new FrMenuPT(user, ipDesktop, nameDesktop);
            FrPT.setVisible(true);

        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtPortaActionPerformed

    private void BtConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtConfigActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrAdmin ad = null;
            ad = new FrAdmin(user, ipDesktop, nameDesktop);
            desktopPane.add(ad);
            ad.setVisible(true);
            ad.setPosicao();

        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtConfigActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            user = LbUserLogado.getText();
            FrOpcoesUsuario ad = null;
            ad = new FrOpcoesUsuario(user, ipDesktop, nameDesktop);
            desktopPane.add(ad);
            ad.setVisible(true);
            ad.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void BtComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtComprasActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            user = LbUserLogado.getText();
            FrMenuCom FrCom = new FrMenuCom(user, ipDesktop, nameDesktop);
            FrCom.setVisible(true);
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtComprasActionPerformed

    private void JmPermissoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmPermissoesActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrPermissoes per = null;
            per = new FrPermissoes(user, ipDesktop, nameDesktop);
            desktopPane.add(per);
            per.setVisible(true);
            per.requestFocus();
            per.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmPermissoesActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        FrCalcJoshua josh = new FrCalcJoshua();
        desktopPane.add(josh);
        josh.setVisible(true);
        josh.requestFocus();
        josh.setPosicao();

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        FrCalcMp mp = new FrCalcMp();
        desktopPane.add(mp);
        mp.setVisible(true);
        mp.requestFocus();
        mp.setPosicao();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void BtEngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEngActionPerformed

        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = LbUserLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            user = LbUserLogado.getText();
            FrMenuEng eng = new FrMenuEng(user, ipDesktop, nameDesktop);
            eng.setVisible(true);
        } catch (UnknownHostException | ClassNotFoundException | SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("abrir tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_BtEngActionPerformed

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

    //CONFIGURAÇÃO PARA O JFORME NÃO FECHAR TODA A APLICAÇÃO
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
            java.util.logging.Logger.getLogger(FrMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FrMenu().setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FrMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCompras;
    private javax.swing.JButton BtConfig;
    private javax.swing.JButton BtDP;
    private javax.swing.JButton BtEng;
    private javax.swing.JButton BtPorta;
    private javax.swing.JMenuItem JmCadUser;
    private javax.swing.JMenuItem JmPermissoes;
    private javax.swing.JLabel LbUserLogado;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
