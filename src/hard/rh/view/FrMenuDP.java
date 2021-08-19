package hard.rh.view;

import hard.config.Config;
import hard.config.ConnectionFactory;
import hard.home.dao.PermissaoDao;
import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FrMenuDP extends javax.swing.JFrame {

    // CONTROLE DE VERSÃO DO SISTEMA
    String versao = "1.0-21.0625.0";

    int codUsuario = 0;
    int tipoUser;
    FDErroOcorrido fdErroOcorrido;

    public int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }

    private boolean FrcadFuncionario;
    private boolean FrcadProcedi;
    private boolean FrcadEventos;
    private boolean FrcadEmpresa;
    private boolean FrContParticipacao;
    private boolean FrContContrato;
    private boolean FrContCotista;
    private boolean FrGeraFolha;
    private boolean FrImportaCopart;
    private boolean FrCadIntegracao;
    private boolean FrContTesteCovid;

    public boolean isFrcadFuncionario() {
        return FrcadFuncionario;
    }

    public void setFrcadFuncionario(boolean FrcadFuncionario) {
        this.FrcadFuncionario = FrcadFuncionario;
    }

    public boolean isFrcadProcedi() {
        return FrcadProcedi;
    }

    public void setFrcadProcedi(boolean FrcadProcedi) {
        this.FrcadProcedi = FrcadProcedi;
    }

    public boolean isFrcadEventos() {
        return FrcadEventos;
    }

    public void setFrcadEventos(boolean FrcadEventos) {
        this.FrcadEventos = FrcadEventos;
    }

    public boolean isFrcadEmpresa() {
        return FrcadEmpresa;
    }

    public void setFrcadEmpresa(boolean FrcadEmpresa) {
        this.FrcadEmpresa = FrcadEmpresa;
    }

    public boolean isFrContParticipacao() {
        return FrContParticipacao;
    }

    public void setFrContParticipacao(boolean FrContParticipacao) {
        this.FrContParticipacao = FrContParticipacao;
    }

    public boolean isFrContContrato() {
        return FrContContrato;
    }

    public void setFrContContrato(boolean FrContContrato) {
        this.FrContContrato = FrContContrato;
    }

    public boolean isFrContCotista() {
        return FrContCotista;
    }

    public void setFrContCotista(boolean FrContCotista) {
        this.FrContCotista = FrContCotista;
    }

    public boolean isFrGeraFolha() {
        return FrGeraFolha;
    }

    public void setFrGeraFolha(boolean FrGeraFolha) {
        this.FrGeraFolha = FrGeraFolha;
    }

    public boolean isFrImportaCopart() {
        return FrImportaCopart;
    }

    public void setFrImportaCopart(boolean FrImportaCopart) {
        this.FrImportaCopart = FrImportaCopart;
    }

    public boolean isFrCadIntegracao() {
        return FrCadIntegracao;
    }

    public void setFrCadIntegracao(boolean FrCadIntegracao) {
        this.FrCadIntegracao = FrCadIntegracao;
    }

    public boolean isFrContTesteCovid() {
        return FrContTesteCovid;
    }

    public void setFrContTesteCovid(boolean FrContTesteCovid) {
        this.FrContTesteCovid = FrContTesteCovid;
    }

    public FrMenuDP(String usuario, String ipDesktop, String nameDesktop) throws ClassNotFoundException {
        initComponents();
        setTitle("System Hardsearch - CONTROLE DPESSOAL - Versão: " + versao);

        setLocationRelativeTo(null);
        setExtendedState(FrMenuDP.MAXIMIZED_BOTH);
        URL LocalIcon = getClass().getResource("/img/hs-icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(LocalIcon);
        this.setIconImage(icone);

        JmUsuarioLogado.setText(usuario);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            int vDash = 0;
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = c.getCodUsuario();
                vDash = c.getFr_vDash();
            }

            validaAcesso();

            if (vDash == 1) {
                dashDP();
            }
            u.setCodUsuario(codUsuario);
            String codTela = "FrMenuDP";
            u.setFr_codTela(codTela);
            u.setFr_versaoTela(versao);
            u.setIpDesktop(ipDesktop);
            u.setNameDesktop(nameDesktop);
            dao.saveSessaoUser(u);

        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public FrMenuDP() {

    }

    public void validaAcesso() throws ClassNotFoundException {
        PermissaoDao dao = new PermissaoDao();
        for (FrMenuDP dp : dao.listAcessFrMenuDP(codUsuario)) {
            JmCadFuncio.setEnabled(dp.isFrcadFuncionario());
            BtCadFuncio.setEnabled(dp.isFrcadFuncionario());
            JmCadPro.setEnabled(dp.isFrcadProcedi());
            BtCadPro.setEnabled(dp.isFrcadProcedi());
            JmCadEvento.setEnabled(dp.isFrcadEventos());
            JmCadEmp.setEnabled(dp.isFrcadEmpresa());
            JmContCop.setEnabled(dp.isFrContParticipacao());
            BtContCop.setEnabled(dp.isFrContParticipacao());
            JmContContra.setEnabled(dp.isFrContContrato());
            BtContContra.setEnabled(dp.isFrContContrato());
            JmContCoti.setEnabled(dp.isFrContCotista());
            JmContFolha.setEnabled(dp.isFrGeraFolha());
            BtContFolha.setEnabled(dp.isFrGeraFolha());
            JmImpCop.setEnabled(dp.isFrImportaCopart());
            JmContIntegra.setEnabled(dp.isFrCadIntegracao());
            JmContCovid.setEnabled(dp.isFrContTesteCovid());
        }

    }

    public void dashDP() throws ClassNotFoundException {
        FrDashboardDP FrAler = null;
        FrAler = new FrDashboardDP();
        desktopPane.add(FrAler);
        FrAler.setVisible(true);
        FrAler.requestFocus();
        FrAler.setPosicao();

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
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        BtCadFuncio = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        BtCadPro = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        BtContCop = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        BtContFolha1 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        BtContContra = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        BtContFolha = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        JmCadFuncio = new javax.swing.JMenuItem();
        JmCadPro = new javax.swing.JMenuItem();
        JmCadEvento = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        JmCadEmp = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        JmExit = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        JmContCop = new javax.swing.JMenuItem();
        JmContContra = new javax.swing.JMenuItem();
        JmContCoti = new javax.swing.JMenuItem();
        JmContFolha = new javax.swing.JMenuItem();
        JmImpCop = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        JmContIntegra = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        JmContCovid = new javax.swing.JMenuItem();
        JMRelPar = new javax.swing.JMenu();
        JmRelParcela = new javax.swing.JMenuItem();
        JmRelCopCad = new javax.swing.JMenuItem();
        JmListaCop = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        JmRelInteVencida = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        JmRelRazao = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        JmSobre = new javax.swing.JMenuItem();
        JmUsuarioLogado = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("System Hardsearch - CONTROLE DPESSOAL - Versão: 1.0-21.0601.0");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        desktopPane.setAlignmentX(0.0F);
        desktopPane.setAlignmentY(0.0F);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setAlignmentX(0.0F);
        jToolBar1.setAlignmentY(0.0F);
        jToolBar1.setBorderPainted(false);

        BtCadFuncio.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtCadFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/funcionario_icon.png"))); // NOI18N
        BtCadFuncio.setText("Funcionários");
        BtCadFuncio.setAlignmentY(0.0F);
        BtCadFuncio.setFocusable(false);
        BtCadFuncio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtCadFuncio.setMaximumSize(new java.awt.Dimension(80, 80));
        BtCadFuncio.setMinimumSize(new java.awt.Dimension(80, 80));
        BtCadFuncio.setPreferredSize(new java.awt.Dimension(80, 60));
        BtCadFuncio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtCadFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCadFuncioActionPerformed(evt);
            }
        });
        jToolBar1.add(BtCadFuncio);

        jSeparator7.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator7.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator7);

        BtCadPro.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtCadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/receituario_icon.png"))); // NOI18N
        BtCadPro.setText("Procedimentos");
        BtCadPro.setAlignmentY(0.0F);
        BtCadPro.setFocusable(false);
        BtCadPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtCadPro.setMaximumSize(new java.awt.Dimension(80, 80));
        BtCadPro.setMinimumSize(new java.awt.Dimension(80, 80));
        BtCadPro.setPreferredSize(new java.awt.Dimension(80, 60));
        BtCadPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCadProActionPerformed(evt);
            }
        });
        jToolBar1.add(BtCadPro);

        jSeparator8.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator8.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator8);

        jButton6.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/evento_icon.png"))); // NOI18N
        jButton6.setText("Eventos");
        jButton6.setAlignmentY(0.0F);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(80, 80));
        jButton6.setMinimumSize(new java.awt.Dimension(80, 80));
        jButton6.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jSeparator12.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator12.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator12);

        BtContCop.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtContCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coparti_icon.png"))); // NOI18N
        BtContCop.setText("Coparticipações");
        BtContCop.setAlignmentY(0.0F);
        BtContCop.setFocusable(false);
        BtContCop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtContCop.setMaximumSize(new java.awt.Dimension(80, 80));
        BtContCop.setMinimumSize(new java.awt.Dimension(80, 80));
        BtContCop.setPreferredSize(new java.awt.Dimension(80, 60));
        BtContCop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtContCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtContCopActionPerformed(evt);
            }
        });
        jToolBar1.add(BtContCop);

        jSeparator13.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator13.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator13);

        BtContFolha1.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtContFolha1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/listar.png"))); // NOI18N
        BtContFolha1.setText("Importadas");
        BtContFolha1.setAlignmentY(0.0F);
        BtContFolha1.setFocusable(false);
        BtContFolha1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtContFolha1.setMaximumSize(new java.awt.Dimension(80, 80));
        BtContFolha1.setMinimumSize(new java.awt.Dimension(80, 80));
        BtContFolha1.setPreferredSize(new java.awt.Dimension(80, 60));
        BtContFolha1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtContFolha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtContFolha1ActionPerformed(evt);
            }
        });
        jToolBar1.add(BtContFolha1);

        jSeparator9.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator9);

        BtContContra.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtContContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calendar_icon.png"))); // NOI18N
        BtContContra.setText("Contratos");
        BtContContra.setAlignmentY(0.0F);
        BtContContra.setFocusable(false);
        BtContContra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtContContra.setMaximumSize(new java.awt.Dimension(80, 80));
        BtContContra.setMinimumSize(new java.awt.Dimension(80, 80));
        BtContContra.setPreferredSize(new java.awt.Dimension(80, 60));
        BtContContra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtContContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtContContraActionPerformed(evt);
            }
        });
        jToolBar1.add(BtContContra);

        jSeparator10.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator10.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator10);

        BtContFolha.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        BtContFolha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/folha_icon.png"))); // NOI18N
        BtContFolha.setText("Folha");
        BtContFolha.setAlignmentY(0.0F);
        BtContFolha.setFocusable(false);
        BtContFolha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtContFolha.setMaximumSize(new java.awt.Dimension(80, 80));
        BtContFolha.setMinimumSize(new java.awt.Dimension(80, 80));
        BtContFolha.setPreferredSize(new java.awt.Dimension(80, 60));
        BtContFolha.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtContFolha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtContFolhaActionPerformed(evt);
            }
        });
        jToolBar1.add(BtContFolha);

        jSeparator11.setBackground(new java.awt.Color(51, 255, 255));
        jSeparator11.setForeground(new java.awt.Color(51, 204, 255));
        jToolBar1.add(jSeparator11);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/register.png"))); // NOI18N
        fileMenu.setMnemonic('f');
        fileMenu.setText("Cadrastrar");
        fileMenu.add(jSeparator1);

        JmCadFuncio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        JmCadFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/funcionario.png"))); // NOI18N
        JmCadFuncio.setMnemonic('s');
        JmCadFuncio.setText("Cadastrar Funcionário");
        JmCadFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadFuncioActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadFuncio);

        JmCadPro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        JmCadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/procedimento.png"))); // NOI18N
        JmCadPro.setMnemonic('y');
        JmCadPro.setText("Cadastrar Procedimento");
        JmCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadProActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadPro);

        JmCadEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/evento.png"))); // NOI18N
        JmCadEvento.setText("Cadastrar Eventos");
        JmCadEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadEventoActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadEvento);
        fileMenu.add(jSeparator5);

        JmCadEmp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        JmCadEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/company.png"))); // NOI18N
        JmCadEmp.setText("Cadastrar Empresa");
        JmCadEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmCadEmpActionPerformed(evt);
            }
        });
        fileMenu.add(JmCadEmp);
        fileMenu.add(jSeparator2);

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

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coparti.png"))); // NOI18N
        jMenu1.setText("Recursos Humanos");

        JmContCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controle.png"))); // NOI18N
        JmContCop.setText("Controle de Coparticipação");
        JmContCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContCopActionPerformed(evt);
            }
        });
        jMenu1.add(JmContCop);

        JmContContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/integracao.png"))); // NOI18N
        JmContContra.setText("Controle de Contratos");
        JmContContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContContraActionPerformed(evt);
            }
        });
        jMenu1.add(JmContContra);

        JmContCoti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cotista-bag.png"))); // NOI18N
        JmContCoti.setText("Controle de Cotas e Estágios");
        JmContCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContCotiActionPerformed(evt);
            }
        });
        jMenu1.add(JmContCoti);

        JmContFolha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salary-money.png"))); // NOI18N
        JmContFolha.setText("Geração de Folha");
        JmContFolha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContFolhaActionPerformed(evt);
            }
        });
        jMenu1.add(JmContFolha);

        JmImpCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/import-cop.png"))); // NOI18N
        JmImpCop.setText("Importação de Coparticipações");
        JmImpCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmImpCopActionPerformed(evt);
            }
        });
        jMenu1.add(JmImpCop);

        menuBar.add(jMenu1);

        helpMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seguranca.png"))); // NOI18N
        helpMenu.setMnemonic('h');
        helpMenu.setText("Seg. Trabalho");

        JmContIntegra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/integracao.png"))); // NOI18N
        JmContIntegra.setMnemonic('c');
        JmContIntegra.setText("Controle Integração");
        JmContIntegra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContIntegraActionPerformed(evt);
            }
        });
        helpMenu.add(JmContIntegra);

        menuBar.add(helpMenu);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/covid.png"))); // NOI18N
        jMenu3.setText("COVID-19");

        JmContCovid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/teste-covid.png"))); // NOI18N
        JmContCovid.setText("Controle de Testes ");
        JmContCovid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmContCovidActionPerformed(evt);
            }
        });
        jMenu3.add(JmContCovid);

        menuBar.add(jMenu3);

        JMRelPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/relatorios.png"))); // NOI18N
        JMRelPar.setText("Consultas e Relatórios");

        JmRelParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        JmRelParcela.setText("Relat. Parcelas");
        JmRelParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmRelParcelaActionPerformed(evt);
            }
        });
        JMRelPar.add(JmRelParcela);

        JmRelCopCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        JmRelCopCad.setText("Relat. Coparticipações Cadastradas");
        JmRelCopCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmRelCopCadActionPerformed(evt);
            }
        });
        JMRelPar.add(JmRelCopCad);

        JmListaCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controlepro.png"))); // NOI18N
        JmListaCop.setText("Listagem de Coparticipações P/Conferência");
        JmListaCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmListaCopActionPerformed(evt);
            }
        });
        JMRelPar.add(JmListaCop);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/controlepro.png"))); // NOI18N
        jMenuItem7.setText("Listagem de Importação de Coparticipação");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        JMRelPar.add(jMenuItem7);
        JMRelPar.add(jSeparator6);

        JmRelInteVencida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        JmRelInteVencida.setText("Relatório de Integrações");
        JmRelInteVencida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmRelInteVencidaActionPerformed(evt);
            }
        });
        JMRelPar.add(JmRelInteVencida);
        JMRelPar.add(jSeparator3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alert.png"))); // NOI18N
        jMenuItem4.setText("Alertas DP");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        JMRelPar.add(jMenuItem4);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/notfound.png"))); // NOI18N
        jMenuItem3.setText("Listagem de Folhas Não Geradas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        JMRelPar.add(jMenuItem3);
        JMRelPar.add(jSeparator14);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cash_pay.png"))); // NOI18N
        jMenu5.setText("Listagem de Pagamentos");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compare.png"))); // NOI18N
        jMenuItem1.setText("Comparativo de Parcelas mês a mês ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        JmRelRazao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rela.png"))); // NOI18N
        JmRelRazao.setText("Relatório Razão Pagamento x Arrecadação Coparti.");
        JmRelRazao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmRelRazaoActionPerformed(evt);
            }
        });
        jMenu5.add(JmRelRazao);

        JMRelPar.add(jMenu5);

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

        JmSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/about-me.png"))); // NOI18N
        JmSobre.setText("Sobre");
        JmSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmSobreActionPerformed(evt);
            }
        });
        jMenu4.add(JmSobre);

        JmUsuarioLogado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/userMenor.png"))); // NOI18N
        JmUsuarioLogado.setText("Usuário");
        jMenu4.add(JmUsuarioLogado);

        menuBar.add(jMenu4);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmExitActionPerformed
        dispose();
    }//GEN-LAST:event_JmExitActionPerformed

    private void JmCadFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadFuncioActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrcadFuncionario cadF = null;
            cadF = new FrcadFuncionario(user, ipDesktop, nameDesktop);
            desktopPane.add(cadF);
            cadF.setVisible(true);
            cadF.requestFocus();
            cadF.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadFuncioActionPerformed

    private void JmCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadProActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrcadProcedi pro = null;
            pro = new FrcadProcedi(user, ipDesktop, nameDesktop);
            desktopPane.add(pro);
            pro.setVisible(true);
            pro.requestFocus();
            pro.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadProActionPerformed

    private void JmContCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContCopActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrContParticipacao par = null;
            par = new FrContParticipacao(user, ipDesktop, nameDesktop);
            desktopPane.add(par);
            par.setVisible(true);
            par.requestFocus();
            par.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmContCopActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        dispose();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void JmContIntegraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContIntegraActionPerformed

        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrCadIntegracao integra = null;
            integra = new FrCadIntegracao(user, ipDesktop, nameDesktop);
            desktopPane.add(integra);
            integra.setVisible(true);
            integra.setPosicao();
        } catch (ClassNotFoundException | UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmContIntegraActionPerformed

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

    private void JmRelRazaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmRelRazaoActionPerformed
        try {

            Connection con = ConnectionFactory.getConnection();
            int confirma = JOptionPane.showConfirmDialog(null, "Deseja emitir o relatório?", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {

                    JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\relRazaoPagoNPago.jasper", null, con);
                    JasperViewer.viewReport(print, false);
                } catch (JRException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("iniciar tela");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            }
        } catch (ClassNotFoundException ex) {
          fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmRelRazaoActionPerformed

    private void JmRelCopCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmRelCopCadActionPerformed
        try {

            Connection con = ConnectionFactory.getConnection();
            int confirma = JOptionPane.showConfirmDialog(null, "Deseja emitir o relatório de coparticipações cadastradas??", "ATENÇÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {

                    JasperPrint print = JasperFillManager.fillReport("\\\\C:\\Hardsearch\\MyReports\\copartiRel.jasper", null, con);
                    JasperViewer.viewReport(print, false);
                } catch (JRException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("iniciar tela");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }

            }
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmRelCopCadActionPerformed

    private void JmRelParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmRelParcelaActionPerformed

        FrRelatorioCopart rel = null;
        rel = new FrRelatorioCopart();
        desktopPane.add(rel);
        rel.setVisible(true);
        rel.requestFocus();
        rel.setPosicao();

    }//GEN-LAST:event_JmRelParcelaActionPerformed

    private void JmRelInteVencidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmRelInteVencidaActionPerformed

        FrRelatorioIntegra inte = null;
        inte = new FrRelatorioIntegra();
        desktopPane.add(inte);
        inte.setVisible(true);
        inte.setPosicao();
    }//GEN-LAST:event_JmRelInteVencidaActionPerformed

    private void JmContContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContContraActionPerformed

        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrContContrato cc = null;
            cc = new FrContContrato(user, ipDesktop, nameDesktop);
            desktopPane.add(cc);
            cc.setVisible(true);
            cc.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmContContraActionPerformed

    private void JmListaCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmListaCopActionPerformed
        FrListarCop co = null;
        co = new FrListarCop();

        desktopPane.add(co);
        co.setVisible(true);

    }//GEN-LAST:event_JmListaCopActionPerformed

    private void JmContCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContCotiActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrContCotista co = null;
            co = new FrContCotista(user, ipDesktop, nameDesktop);
            desktopPane.add(co);
            co.setVisible(true);
            co.setPosicao();
        } catch (ClassNotFoundException | UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmContCotiActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            dashDP();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar dash");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked

    }//GEN-LAST:event_jMenu4MouseClicked

    private void JmSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmSobreActionPerformed
        FrSobre sob = null;
        sob = new FrSobre();
        desktopPane.add(sob);
        sob.setVisible(true);
        sob.setPosicao();
    }//GEN-LAST:event_JmSobreActionPerformed

    private void JmCadEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmCadEventoActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrcadEventos eve = null;
            eve = new FrcadEventos(user, ipDesktop, nameDesktop);
            desktopPane.add(eve);
            eve.setVisible(true);
            eve.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmCadEventoActionPerformed

    private void JmContFolhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContFolhaActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrGeraFolha ad = null;
            ad = new FrGeraFolha(user, ipDesktop, nameDesktop);
            desktopPane.add(ad);
            ad.setVisible(true);
            ad.setPosicao();
        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmContFolhaActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        FrConsultaFolha fo = null;
        fo = new FrConsultaFolha();
        desktopPane.add(fo);
        fo.setVisible(true);
        fo.setPosicao();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void JmImpCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmImpCopActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrImportaCopart fo = null;
            fo = new FrImportaCopart(user, ipDesktop, nameDesktop);
            desktopPane.add(fo);
            fo.setVisible(true);
            fo.setPosicao();
        } catch (Exception ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_JmImpCopActionPerformed

    private void JmContCovidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmContCovidActionPerformed

        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrContTesteCovid te = null;
            te = new FrContTesteCovid(user, ipDesktop, nameDesktop);
            desktopPane.add(te);
            te.setVisible(true);
        } catch (ClassNotFoundException | UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }//GEN-LAST:event_JmContCovidActionPerformed

    private void BtCadFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCadFuncioActionPerformed
        try {

            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";

            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrcadFuncionario cadF = null;
            cadF = new FrcadFuncionario(user, ipDesktop, nameDesktop);
            desktopPane.add(cadF);
            cadF.setVisible(true);
            cadF.requestFocus();
            cadF.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtCadFuncioActionPerformed

    private void BtCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCadProActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrcadProcedi pro = null;
            pro = new FrcadProcedi(user, ipDesktop, nameDesktop);
            desktopPane.add(pro);
            pro.setVisible(true);
            pro.requestFocus();
            pro.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtCadProActionPerformed

    private void BtContCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtContCopActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrContParticipacao par = null;
            par = new FrContParticipacao(user, ipDesktop, nameDesktop);
            desktopPane.add(par);
            par.setVisible(true);
            par.requestFocus();
            par.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtContCopActionPerformed

    private void BtContContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtContContraActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrContContrato cc = null;
            cc = new FrContContrato(user, ipDesktop, nameDesktop);
            desktopPane.add(cc);
            cc.setVisible(true);
            cc.setPosicao();
        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtContContraActionPerformed

    private void BtContFolhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtContFolhaActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrGeraFolha ad = null;
            ad = new FrGeraFolha(user, ipDesktop, nameDesktop);
            desktopPane.add(ad);
            ad.setVisible(true);
            ad.setPosicao();
        } catch (UnknownHostException | ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtContFolhaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
            FrcadEventos eve = null;
            eve = new FrcadEventos(user, ipDesktop, nameDesktop);
            desktopPane.add(eve);
            eve.setVisible(true);
            eve.setPosicao();

        } catch (UnknownHostException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrListaImporta li = null;
            li = new FrListaImporta(user, ipDesktop, nameDesktop);
            desktopPane.add(li);
            li.setVisible(true);
            li.setMaximum(true);
            li.setPosicao();

        } catch (UnknownHostException | PropertyVetoException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void BtContFolha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtContFolha1ActionPerformed
        try {
            String user = "";
            String ipDesktop = "";
            String nameDesktop = "";
            user = JmUsuarioLogado.getText();
            ipDesktop = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();

            FrListaImporta li = null;
            li = new FrListaImporta(user, ipDesktop, nameDesktop);
            desktopPane.add(li);
            li.setVisible(true);
            li.setMaximum(true);
            li.setPosicao();

        } catch (UnknownHostException | PropertyVetoException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("iniciar tela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtContFolha1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        FrComparaPar par = new FrComparaPar();
        desktopPane.add(par);
        par.setVisible(true);

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

    private void formWindowActivated(java.awt.event.WindowEvent evt) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        try {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//        } catch (Exception ex) {
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
            java.util.logging.Logger.getLogger(FrMenuDP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrMenuDP().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCadFuncio;
    private javax.swing.JButton BtCadPro;
    private javax.swing.JButton BtContContra;
    private javax.swing.JButton BtContCop;
    private javax.swing.JButton BtContFolha;
    private javax.swing.JButton BtContFolha1;
    private javax.swing.JMenu JMRelPar;
    private javax.swing.JMenuItem JmCadEmp;
    private javax.swing.JMenuItem JmCadEvento;
    private javax.swing.JMenuItem JmCadFuncio;
    private javax.swing.JMenuItem JmCadPro;
    private javax.swing.JMenuItem JmContContra;
    private javax.swing.JMenuItem JmContCop;
    private javax.swing.JMenuItem JmContCoti;
    private javax.swing.JMenuItem JmContCovid;
    private javax.swing.JMenuItem JmContFolha;
    private javax.swing.JMenuItem JmContIntegra;
    private javax.swing.JMenuItem JmExit;
    private javax.swing.JMenuItem JmImpCop;
    private javax.swing.JMenuItem JmListaCop;
    private javax.swing.JMenuItem JmRelCopCad;
    private javax.swing.JMenuItem JmRelInteVencida;
    private javax.swing.JMenuItem JmRelParcela;
    private javax.swing.JMenuItem JmRelRazao;
    private javax.swing.JMenuItem JmSobre;
    private javax.swing.JMenuItem JmUsuarioLogado;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
