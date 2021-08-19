package hard.engenharia.view;

import hard.config.Config;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import hard.rh.view.FrSobre;
import hard.rh.view.FrcadEmpresa;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrMenuEng extends javax.swing.JFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0813.0";
    FDErroOcorrido fdErroOcorrido;

    int codUsuario = 0;
    private boolean FrCadItem;
    private boolean FrCadEmpresa;
    private boolean FrCadMaquina;
    private boolean FrCadFerramenta;
    private boolean FrCadSolicitaRev;
    private boolean FrProcessaSolicita;
    private boolean FrOcorrenciasProd;

    public boolean isFrCadItem() {
        return FrCadItem;
    }

    public void setFrCadItem(boolean FrCadItem) {
        this.FrCadItem = FrCadItem;
    }

    public boolean isFrCadEmpresa() {
        return FrCadEmpresa;
    }

    public void setFrCadEmpresa(boolean FrCadEmpresa) {
        this.FrCadEmpresa = FrCadEmpresa;
    }

    public boolean isFrCadMaquina() {
        return FrCadMaquina;
    }

    public void setFrCadMaquina(boolean FrCadMaquina) {
        this.FrCadMaquina = FrCadMaquina;
    }

    public boolean isFrCadFerramenta() {
        return FrCadFerramenta;
    }

    public void setFrCadFerramenta(boolean FrCadFerramenta) {
        this.FrCadFerramenta = FrCadFerramenta;
    }

    public boolean isFrCadSolicitaRev() {
        return FrCadSolicitaRev;
    }

    public void setFrCadSolicitaRev(boolean FrCadSolicitaRev) {
        this.FrCadSolicitaRev = FrCadSolicitaRev;
    }

    public boolean isFrProcessaSolicita() {
        return FrProcessaSolicita;
    }

    public void setFrProcessaSolicita(boolean FrProcessaSolicita) {
        this.FrProcessaSolicita = FrProcessaSolicita;
    }

    public boolean isFrOcorrenciasProd() {
        return FrOcorrenciasProd;
    }

    public void setFrOcorrenciasProd(boolean FrOcorrenciasProd) {
        this.FrOcorrenciasProd = FrOcorrenciasProd;
    }

    public FrMenuEng(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException, SQLException {
        initComponents();
        setTitle("System Hardsearch - CONTROLE ENGENHARIA - Versão: " + versao);

        setLocationRelativeTo(null);
        setExtendedState(FrMenuEng.MAXIMIZED_BOTH);

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

            u.setCodUsuario(codUsuario);
            String codTela = "FrMenuEng";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro na aplicação, contate administrador", "Erro-Save-Sessão", JOptionPane.ERROR_MESSAGE);
        }
        validaAcessos();

    }

    public void validaAcessos() throws ClassNotFoundException, SQLException {

        PermissaoDao dao = new PermissaoDao();
        for (FrMenuEng me : dao.listAcessFrMenuEng(codUsuario)) {
            JmCadItem.setEnabled(me.isFrCadItem());
            JmCadEmp.setEnabled(me.isFrCadEmpresa());
            JmCadMaqui.setEnabled(me.isFrCadMaquina());
            JmCadFer.setEnabled(me.isFrCadFerramenta());
            JmSoli.setEnabled(me.isFrCadSolicitaRev());
            JmProSoli.setEnabled(me.isFrProcessaSolicita());
            JmContRev.setEnabled(me.isFrCadSolicitaRev());
            JmLancOco.setEnabled(me.isFrOcorrenciasProd());
        }

    }

    public FrMenuEng() {

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
        JmCadItem = new javax.swing.JMenuItem();
        JmCadEmp = new javax.swing.JMenuItem();
        JmCadMaqui = new javax.swing.JMenuItem();
        JmCadFer = new javax.swing.JMenuItem();
        JmExit = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        JmSoli = new javax.swing.JMenuItem();
        JmProSoli = new javax.swing.JMenuItem();
        JmContRev = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        JmLancOco = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        JmSobre = new javax.swing.JMenuItem();
        JmUsuarioLogado = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register.png"))); // NOI18N
        fileMenu.setMnemonic('f');
        fileMenu.setText("Opções");

        JmCadItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/piece.png"))); // NOI18N
        JmCadItem.setText("Cadastro de Item");
        JmCadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadItemActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadItem);

        JmCadEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/company.png"))); // NOI18N
        JmCadEmp.setText("Cadastro de Empresa");
        JmCadEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadEmpActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadEmp);

        JmCadMaqui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/maquina.png"))); // NOI18N
        JmCadMaqui.setText("Cadastro de Máquina");
        JmCadMaqui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadMaquiActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadMaqui);

        JmCadFer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tool_cnc.png"))); // NOI18N
        JmCadFer.setText("Cadatro de Ferramenta");
        JmCadFer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadFerActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadFer);

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

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/archiveGen.png"))); // NOI18N
        jMenu1.setText("Gerenciamento de Dados");

        JmSoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-adicionar-64 (1).png"))); // NOI18N
        JmSoli.setText("Cadastro de Solicitação de Revisão");
        JmSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmSoliActionPerformed(evt);
            }
        });
        jMenu1.add(JmSoli);

        JmProSoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controle.png"))); // NOI18N
        JmProSoli.setText("Processamento de Solicitações de Revisão");
        JmProSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmProSoliActionPerformed(evt);
            }
        });
        jMenu1.add(JmProSoli);

        JmContRev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calculator (3).png"))); // NOI18N
        JmContRev.setText("Controle de Revisões");
        JmContRev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContRevActionPerformed(evt);
            }
        });
        jMenu1.add(JmContRev);

        menuBar.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/product.png"))); // NOI18N
        jMenu3.setText("Produção");

        JmLancOco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ocorrencias.png"))); // NOI18N
        JmLancOco.setText("Lançamento de Ocorrências de Produção");
        JmLancOco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmLancOcoActionPerformed(evt);
            }
        });
        jMenu3.add(JmLancOco);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search_product.png"))); // NOI18N
        jMenuItem2.setText("Controle de Produção CNCSearch");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        menuBar.add(jMenu3);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/relatorios.png"))); // NOI18N
        jMenu5.setText("Consultas e Relatórios");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controlepro.png"))); // NOI18N
        jMenuItem1.setText("Listagem de Ocorrências");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        menuBar.add(jMenu5);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        jMenu4.setText("Ajuda");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });

        JmSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/about-me.png"))); // NOI18N
        JmSobre.setText("Sobre");
        JmSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmSobreActionPerformed(evt);
            }
        });
        jMenu4.add(JmSobre);

        JmUsuarioLogado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N
        JmUsuarioLogado.setText("Usuário");
        jMenu4.add(JmUsuarioLogado);

        menuBar.add(jMenu4);

        jMenu2.setForeground(new java.awt.Color(255, 0, 0));
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
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
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

    private void JmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmExitActionPerformed
        dispose();
    }//GEN-LAST:event_JmExitActionPerformed

    private void JmSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmSoliActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadSolicitaRev srev = null;
            srev = new FrCadSolicitaRev(user, ipDesktop, nameDesktop);
            desktopPane.add(srev);
            srev.setVisible(true);
            srev.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmSoliActionPerformed

    private void JmProSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmProSoliActionPerformed
        try {
            FrProcessaSolicita crev = null;
            crev = new FrProcessaSolicita();
            desktopPane.add(crev);
            crev.setVisible(true);
            crev.setMaximum(true);
            crev.setPosicao();
        } catch (PropertyVetoException ex) {

        }
    }//GEN-LAST:event_JmProSoliActionPerformed

    private void JmCadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadItemActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadItem ite = null;
            ite = new FrCadItem(user);
            desktopPane.add(ite);
            ite.setVisible(true);
            ite.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmCadItemActionPerformed

    private void JmCadEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadEmpActionPerformed
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

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadEmpActionPerformed

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

    private void JmContRevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContRevActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrContRevisao pdm = null;
            pdm = new FrContRevisao();
            desktopPane.add(pdm);
            pdm.setVisible(true);
//            pdm.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmContRevActionPerformed

    private void JmLancOcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmLancOcoActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrOcorrenciasProd pr = new FrOcorrenciasProd(user, ipDesktop, nameDesktop);
            desktopPane.add(pr);
            pr.setVisible(true);
            pr.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmLancOcoActionPerformed

    private void JmCadMaquiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadMaquiActionPerformed

        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadMaquina m = new FrCadMaquina(user, ipDesktop, nameDesktop);
            desktopPane.add(m);
            m.setVisible(true);
            m.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmCadMaquiActionPerformed

    private void JmCadFerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadFerActionPerformed

        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadFerramenta f = new FrCadFerramenta(user, ipDesktop, nameDesktop);
            desktopPane.add(f);
            f.setVisible(true);
            f.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadFerActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrListaOcorrencias l = new FrListaOcorrencias(user, ipDesktop, nameDesktop);
            desktopPane.add(l);
            l.setVisible(true);
            l.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      
        try {
            java.awt.Desktop.getDesktop().open(new File("\\\\192.168.24.252\\Hardsearch\\CNCSearchView\\dist\\CNCSearchView.jar"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao tentar atualizar\nErro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(FrMenuEng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
            new FrMenuEng().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JmCadEmp;
    private javax.swing.JMenuItem JmCadFer;
    private javax.swing.JMenuItem JmCadItem;
    private javax.swing.JMenuItem JmCadMaqui;
    private javax.swing.JMenuItem JmContRev;
    private javax.swing.JMenuItem JmExit;
    private javax.swing.JMenuItem JmLancOco;
    private javax.swing.JMenuItem JmProSoli;
    private javax.swing.JMenuItem JmSobre;
    private javax.swing.JMenuItem JmSoli;
    private javax.swing.JMenuItem JmUsuarioLogado;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
