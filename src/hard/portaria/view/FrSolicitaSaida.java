package hard.portaria.view;

import hard.home.dao.UsuarioDao;
import hard.home.model.Usuario;
import hard.portaria.dao.ContSaidaDao;
import hard.rh.dao.FuncionarioDao;
import hard.portaria.model.ContSaida;
import hard.rh.model.Funcionario;
import hard.home.view.FDBuscaFuncio;
import hard.home.view.FDErroOcorrido;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class FrSolicitaSaida extends javax.swing.JInternalFrame {

    //CONTROLE DE VERSAO
    String versao = "1.0-21.0625.0";

    FDErroOcorrido fdErroOcorrido;

    private FDBuscaFuncio FDBuscaFuncio;

    /**
     * @param usuario
     * @param ipDesktop
     * @param nameDesktop
     */
    public FrSolicitaSaida(String usuario, String ipDesktop, String nameDesktop) {
        initComponents();
        title = "Apontamento de Saídas - V" + versao;

        JcSaidaN.setSelected(true);
        habilitarB(1);
        LbResponsavel.setVisible(true);

        try {
            Usuario u = new Usuario();
            UsuarioDao dao = new UsuarioDao();

            String codUsuario = "0";
            for (Usuario c : dao.readUserForDesc(usuario)) {
                codUsuario = Integer.toString(c.getCodUsuario());
                LbResponsavel.setText(c.getNomeUsuario());
            }

            u.setCodUsuario(Integer.parseInt(codUsuario));
            String codTela = "FrSolicitaSaida";
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

    //HABILITA OU DESABILITA ITENS DO FRONT
    public void habilitarB(int op) {
        switch (op) {
            case 1:

                BtNovo.setEnabled(true);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setVisible(true);
                BtExcluir.setEnabled(false);

                EdCodSaida.setEnabled(true);
                EdCodFuncio.setEnabled(true);
                EdNomeFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdSetorFuncio.setEnabled(false);

                EdHoraSaida.setEnabled(false);
                JDcDataSaida.setEnabled(false);
                JcRetornoNao.setEnabled(false);
                JcRetornoSim.setEnabled(false);
                JcMotivoMedico.setEnabled(false);
                JcMotivoPartricular.setEnabled(false);
                JcMotivoOutros.setEnabled(false);
                JtLocalSaida.setEditable(false);

                break;

            case 2:
                ultimoCodCop();

                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(true);
                BtSalvar.setVisible(true);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(false);
                BtExcluir.setEnabled(false);
                BtCancelar.setEnabled(true);

                EdCodSaida.setEnabled(true);
                EdCodFuncio.setEnabled(true);
                EdNomeFuncio.setEnabled(true);
                EdCargoFuncio.setEnabled(true);
                EdSetorFuncio.setEnabled(true);

                EdHoraSaida.setEnabled(true);
                JDcDataSaida.setEnabled(true);
                JcRetornoNao.setEnabled(true);
                JcRetornoSim.setEnabled(true);
                JcMotivoMedico.setEnabled(true);
                JcMotivoPartricular.setEnabled(true);
                JcMotivoOutros.setEnabled(true);
                JtLocalSaida.setEditable(true);

                break;

            case 3:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(true);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(true);
                BtCancelar.setEnabled(true);

                EdCodSaida.setEnabled(false);
                EdCodFuncio.setEnabled(false);
                EdNomeFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdSetorFuncio.setEnabled(false);

                EdHoraSaida.setEnabled(true);
                JDcDataSaida.setEnabled(true);
                JcRetornoNao.setEnabled(true);
                JcRetornoSim.setEnabled(true);
                JcMotivoMedico.setEnabled(true);
                JcMotivoPartricular.setEnabled(true);
                JcMotivoOutros.setEnabled(true);
                JtLocalSaida.setEditable(true);

                break;
            case 4:
                BtNovo.setEnabled(false);
                BtSalvar.setEnabled(false);
                BtSalvar.setVisible(false);
                BtSalvarEdit.setEnabled(false);
                BtSalvarEdit.setVisible(true);
                BtExcluir.setEnabled(false);
                BtCancelar.setEnabled(true);

                EdCodSaida.setEnabled(false);
                EdCodFuncio.setEnabled(false);
                EdNomeFuncio.setEnabled(false);
                EdCargoFuncio.setEnabled(false);
                EdSetorFuncio.setEnabled(false);

                EdHoraSaida.setEnabled(false);
                JDcDataSaida.setEnabled(false);
                JcRetornoNao.setEnabled(false);
                JcRetornoSim.setEnabled(false);
                JcMotivoMedico.setEnabled(false);
                JcMotivoPartricular.setEnabled(false);
                JcMotivoOutros.setEnabled(false);
                JtLocalSaida.setEditable(false);

                break;
        }
    }

    //LIMPA CAMPOS DO FRONT
    public void limpaCampos() {
        EdCodSaida.setText(null);
        EdCodFuncio.setText(null);
        EdNomeFuncio.setText(null);
        EdCargoFuncio.setText(null);
        EdSetorFuncio.setText(null);

        EdHoraSaida.setText(null);
        JDcDataSaida.setDate(null);
        JcRetornoNao.setSelected(false);
        JcRetornoSim.setSelected(false);
        JcMotivoMedico.setSelected(false);
        JcMotivoPartricular.setSelected(false);
        JcMotivoOutros.setSelected(false);
        JtLocalSaida.setText(null);
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 4);
    }

    public void validarCodCadastro() throws ParseException, SQLException {
        FuncionarioDao pdao = new FuncionarioDao();

        if (EdCodFuncio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do funcionário", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                if (pdao.checkCod(EdCodFuncio.getText())) {
                    listarFuncioCod();

                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado", "Atenção", JOptionPane.WARNING_MESSAGE);

                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void listarFuncioCod() throws ClassNotFoundException {

        FuncionarioDao pdao = new FuncionarioDao();

        for (Funcionario fu : pdao.readFuncionarioForCod(EdCodFuncio.getText())) {
            EdNomeFuncio.setText(fu.getNomeFuncionario());
            EdSetorFuncio.setText(fu.getSetorFuncionario());
            EdCargoFuncio.setText(fu.getCargoFuncionario());

        }
    }

    public void ultimoCodCop() {
        ContSaidaDao dao = new ContSaidaDao();

        try {
            for (ContSaida c : dao.ContSaida()) {

                int codSaida;
                codSaida = c.getCodSaida() + 1;
                EdCodSaida.setText(Integer.toString(codSaida));

            }

        } catch (ClassNotFoundException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }

    }

    public void checkCreateSaida() {

        if (EdCodFuncio.getText().equals("") || JDcDataSaida.getDate() == null || EdHoraSaida.getText().equals("  :  ") || JtLocalSaida.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            createSaida();
        }

    }

    public void createSaida() {

        try {

            ContSaidaDao cdao = new ContSaidaDao();
            ContSaida s = new ContSaida();

            s.setCodSaida(Integer.parseInt(EdCodSaida.getText()));
            s.setFr_codFuncionario(Integer.parseInt(EdCodFuncio.getText()));

            java.util.Date dateSai = JDcDataSaida.getDate();
            long dtS = dateSai.getTime();
            java.sql.Date dataSaida = new java.sql.Date(dtS);
            s.setDataSaidaFuncio(dataSaida);

            s.setHoraSaidaFuncio(EdHoraSaida.getText());

            if (JcRetornoNao.isSelected()) {
                s.setRetornaTraba(2);
            } else if (JcRetornoSim.isSelected()) {
                s.setRetornaTraba(1);
            } else {
            }

            if (JcMotivoMedico.isSelected()) {

                s.setMotivoSaida(1);
            } else if (JcMotivoPartricular.isSelected()) {

                s.setMotivoSaida(2);
            } else if (JcMotivoOutros.isSelected()) {

                s.setMotivoSaida(3);
            }

            s.setEspLocal(JtLocalSaida.getText());

            s.setResponsavel(LbResponsavel.getText());

            s.setStatusSaida(1);

            cdao.createExit(s);
            habilitarB(1);
            limpaCampos();

        } catch (ClassNotFoundException | NumberFormatException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("criar saída");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }

    public void readSaida() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        ContSaidaDao cdao = new ContSaidaDao();

        if (EdBuscaNome.getText().equals("")) {

            try {

                if (JcSaidaN.isSelected()) {

                    for (ContSaida c : cdao.readSaidaNConfirm()) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }

                } else if (JcSaidaS.isSelected()) {

                    for (ContSaida c : cdao.readSaidaConfirm()) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }

                } else {

                    for (ContSaida c : cdao.readSaida()) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        } else {

            try {

                if (JcSaidaN.isSelected()) {

                    for (ContSaida c : cdao.readDescSaidaNConfirm(EdBuscaNome.getText())) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }

                } else if (JcSaidaS.isSelected()) {

                    for (ContSaida c : cdao.readDescSaidaConfirm(EdBuscaNome.getText())) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }

                } else {

                    for (ContSaida c : cdao.readSaidaForDesc(EdBuscaNome.getText())) {
                        modelo.addRow(new Object[]{
                            c.getCodSaida(),
                            c.getFr_codFuncionario(),
                            c.getFr_nomeFuncionario(),
                            c.getDataSaidaFuncio(),
                            c.getHoraSaidaFuncio(),
                            c.getRetornaTraba(),
                            c.getMotivoSaida(),
                            c.getEspLocal(),
                            c.getResponsavel(),
                            c.getStatusSaida()
                        });
                    }
                }
            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }

    public void EditarSaida() throws ClassNotFoundException {

        if (EdCodFuncio.getText().equals("") || JDcDataSaida.getDate() == null || EdHoraSaida.getText().equals("  :  ") || JtLocalSaida.getText().equals("") || JtLocalSaida.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            String funcio = EdNomeFuncio.getText();
            int input = JOptionPane.showConfirmDialog(null,
                    "Deseja alterar a saída do funcionario: " + funcio, "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (input == 0) {
                ContSaida s = new ContSaida();
                ContSaidaDao dao = new ContSaidaDao();

                s.setCodSaida(Integer.parseInt(EdCodSaida.getText()));

                java.util.Date dateSai = JDcDataSaida.getDate();
                long dtS = dateSai.getTime();
                java.sql.Date dataSaida = new java.sql.Date(dtS);
                s.setDataSaidaFuncio(dataSaida);

                s.setHoraSaidaFuncio(EdHoraSaida.getText());

                if (JcRetornoNao.isSelected()) {
                    s.setRetornaTraba(2);
                } else if (JcRetornoSim.isSelected()) {
                    s.setRetornaTraba(1);
                } else {

                }

                if (JcMotivoMedico.isSelected()) {

                    s.setMotivoSaida(1);
                } else if (JcMotivoPartricular.isSelected()) {

                    s.setMotivoSaida(2);
                } else if (JcMotivoOutros.isSelected()) {

                    s.setMotivoSaida(3);
                }

                s.setEspLocal(JtLocalSaida.getText());

                dao.updateSaida(s);
                limpaCampos();
                readSaida();
            }
        }
    }

    public void deletarContSaida() {
        ContSaida c = new ContSaida();
        ContSaidaDao dao = new ContSaidaDao();

        if (EdCodSaida.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma solicitação para excluír");
        } else {

            c.setCodSaida(Integer.parseInt(EdCodSaida.getText()));
            try {

                String nome = EdNomeFuncio.getText();

                int input = JOptionPane.showConfirmDialog(null,
                        "Deseja mesmo excluír a solicitação de saída do funcionário  " + nome, "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    dao.delete(c);
                    habilitarB(1);
                    limpaCampos();
                    readSaida();
                } else {

                    JOptionPane.showMessageDialog(null, "Exclusão cancelada", "Atenção", JOptionPane.WARNING_MESSAGE);
                }

            } catch (ClassNotFoundException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);

            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        EdSetorFuncio = new javax.swing.JTextField();
        EdCodFuncio = new javax.swing.JTextField();
        BtBuscarCodFuncio = new javax.swing.JButton();
        EdNomeFuncio = new javax.swing.JTextField();
        EdCargoFuncio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        JDcDataSaida = new com.toedter.calendar.JDateChooser();
        EdHoraSaida = new javax.swing.JFormattedTextField();
        JcRetornoNao = new javax.swing.JCheckBox();
        JcRetornoSim = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JcMotivoMedico = new javax.swing.JCheckBox();
        JcMotivoPartricular = new javax.swing.JCheckBox();
        JcMotivoOutros = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtLocalSaida = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtNovo = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        EdCodSaida = new javax.swing.JTextField();
        BtBuscarCodSaida = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        LbStatus = new javax.swing.JLabel();
        LbResponsavel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        JcSaidaN = new javax.swing.JCheckBox();
        JcSaidaS = new javax.swing.JCheckBox();
        EdBuscaNome = new javax.swing.JTextField();
        BtBuscarNomeFuncio = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EdSetorFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdSetorFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdSetorFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(EdSetorFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 260, -1));

        EdCodFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Funcionário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdCodFuncioActionPerformed(evt);
            }
        });
        EdCodFuncio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdCodFuncioKeyPressed(evt);
            }
        });
        jPanel6.add(EdCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        BtBuscarCodFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodFuncio.setText("Buscar");
        BtBuscarCodFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(BtBuscarCodFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        EdNomeFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdNomeFuncioActionPerformed(evt);
            }
        });
        jPanel6.add(EdNomeFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, -1));

        EdCargoFuncio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cargo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel6.add(EdCargoFuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 280, 180));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Retorna ao trabalho:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 148, -1, 20));

        JDcDataSaida.setBackground(new java.awt.Color(255, 255, 255));
        JDcDataSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data saída:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        jPanel2.add(JDcDataSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        EdHoraSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hora saída:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        try {
            EdHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        EdHoraSaida.setToolTipText("");
        jPanel2.add(EdHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 80, -1));

        JcRetornoNao.setText("Não");
        JcRetornoNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcRetornoNaoActionPerformed(evt);
            }
        });
        jPanel2.add(JcRetornoNao, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        JcRetornoSim.setText("Sim");
        JcRetornoSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcRetornoSimActionPerformed(evt);
            }
        });
        jPanel2.add(JcRetornoSim, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 250, 180));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Motivo:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, -1, -1));

        JcMotivoMedico.setText("Médico");
        JcMotivoMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcMotivoMedicoActionPerformed(evt);
            }
        });
        jPanel3.add(JcMotivoMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        JcMotivoPartricular.setText("Particular");
        JcMotivoPartricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcMotivoPartricularActionPerformed(evt);
            }
        });
        jPanel3.add(JcMotivoPartricular, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        JcMotivoOutros.setText("Outros");
        JcMotivoOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcMotivoOutrosActionPerformed(evt);
            }
        });
        jPanel3.add(JcMotivoOutros, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        JtLocalSaida.setColumns(20);
        JtLocalSaida.setRows(5);
        JtLocalSaida.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Especificar local:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        JtLocalSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtLocalSaidaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JtLocalSaida);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 390, 80));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 540, 110));

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel5.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, 33));

        BtNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new.png"))); // NOI18N
        BtNovo.setText("NOVO");
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });
        jPanel5.add(BtNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel5.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 120, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel5.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 78, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel5.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 33));

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });
        jPanel5.add(BtSalvarEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 33));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 353, 560, 55));
        jPanel1.add(EdCodSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 12, 68, -1));

        BtBuscarCodSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarCodSaida.setText("Buscar");
        BtBuscarCodSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarCodSaidaActionPerformed(evt);
            }
        });
        jPanel1.add(BtBuscarCodSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(94, 13, -1, 27));

        jLabel11.setText("Status:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        LbStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel1.add(LbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 30, 160, 20));
        jPanel1.add(LbResponsavel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 120, 20));

        jTabbedPane1.addTab("Solicitar Saída", jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Saída", "Cód. Funcionario", "Nome Funcionário", "Data Saída", "Hora Saída", "Retorno", "Motivo", "Local", "Responsável", "StatusSaida"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(1).setMinWidth(0);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(1).setMaxWidth(0);
            tabela.getColumnModel().getColumn(5).setMinWidth(0);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(5).setMaxWidth(0);
            tabela.getColumnModel().getColumn(6).setMinWidth(0);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(6).setMaxWidth(0);
            tabela.getColumnModel().getColumn(7).setMinWidth(0);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(7).setMaxWidth(0);
            tabela.getColumnModel().getColumn(8).setMinWidth(0);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(8).setMaxWidth(0);
            tabela.getColumnModel().getColumn(9).setMinWidth(0);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 560, 320));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        JcSaidaN.setText("Saída não confirmada");
        JcSaidaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcSaidaNActionPerformed(evt);
            }
        });

        JcSaidaS.setText("Saída confirmada");
        JcSaidaS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcSaidaSActionPerformed(evt);
            }
        });

        EdBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Busca nome:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 11))); // NOI18N
        EdBuscaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdBuscaNomeKeyPressed(evt);
            }
        });

        BtBuscarNomeFuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-localizar-e-substituir-24 (1).png"))); // NOI18N
        BtBuscarNomeFuncio.setText("Buscar");
        BtBuscarNomeFuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarNomeFuncioActionPerformed(evt);
            }
        });

        jLabel9.setText("Filtrar por:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdBuscaNome, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtBuscarNomeFuncio)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JcSaidaN)
                    .addComponent(JcSaidaS)
                    .addComponent(jLabel9))
                .addGap(65, 65, 65))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(3, 3, 3)
                        .addComponent(JcSaidaS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JcSaidaN))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EdBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtBuscarNomeFuncio)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 80));
        jPanel7.getAccessibleContext().setAccessibleName("Consulta");

        jTabbedPane1.addTab("Listagem de Saídas", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EdSetorFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdSetorFuncioActionPerformed

    }//GEN-LAST:event_EdSetorFuncioActionPerformed

    private void EdCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdCodFuncioActionPerformed

    }//GEN-LAST:event_EdCodFuncioActionPerformed

    private void EdCodFuncioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdCodFuncioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                validarCodCadastro();
            } catch (ParseException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_EdCodFuncioKeyPressed

    private void BtBuscarCodFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodFuncioActionPerformed
        this.FDBuscaFuncio = new FDBuscaFuncio(null, true);

        if (EdCodFuncio.getText().equals("")) {
            this.FDBuscaFuncio.setVisible(true);

            this.EdCodFuncio.setText(String.valueOf(this.FDBuscaFuncio.getCodFuncio()));

            if (this.EdCodFuncio.getText().equals("0")) {
                EdCodFuncio.setText("");

            } else {
                try {
                    validarCodCadastro();
                } catch (ParseException | SQLException ex) {
                    fdErroOcorrido = new FDErroOcorrido(null, true);
                    fdErroOcorrido.LbInformaErro.setText("buscar cadastro funcionário");
                    FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                    fdErroOcorrido.setVisible(true);
                }
            }

        } else {

            try {
                validarCodCadastro();
            } catch (ParseException | SQLException ex) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtBuscarCodFuncioActionPerformed

    private void EdNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdNomeFuncioActionPerformed

    }//GEN-LAST:event_EdNomeFuncioActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        deletarContSaida();
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        habilitarB(2);
        EdCodFuncio.requestFocus();
    }//GEN-LAST:event_BtNovoActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed
        habilitarB(1);
        limpaCampos();
        LbStatus.setText("");
        LbStatus.setForeground(Color.BLACK);

    }//GEN-LAST:event_BtCancelarActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed
        if (JcRetornoSim.isSelected() || JcRetornoNao.isSelected()) {
            checkCreateSaida();
        } else {
            JOptionPane.showMessageDialog(JcRetornoNao, "Você deve selecionar se retorna ou não para o trabalho", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed
        try {
            EditarSaida();
        } catch (ClassNotFoundException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar edição");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        }
    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtBuscarNomeFuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarNomeFuncioActionPerformed
        readSaida();
    }//GEN-LAST:event_BtBuscarNomeFuncioActionPerformed

    private void JcMotivoMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcMotivoMedicoActionPerformed
        JcMotivoOutros.setSelected(false);
        JcMotivoPartricular.setSelected(false);
        JtLocalSaida.setText("Médico");
    }//GEN-LAST:event_JcMotivoMedicoActionPerformed

    private void JcMotivoPartricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcMotivoPartricularActionPerformed
        JcMotivoOutros.setSelected(false);
        JcMotivoMedico.setSelected(false);
        JtLocalSaida.setText("Particular");
    }//GEN-LAST:event_JcMotivoPartricularActionPerformed

    private void JcMotivoOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcMotivoOutrosActionPerformed
        JcMotivoMedico.setSelected(false);
        JcMotivoPartricular.setSelected(false);
        JtLocalSaida.setText("");
        JtLocalSaida.requestFocus();
    }//GEN-LAST:event_JcMotivoOutrosActionPerformed

    private void JcRetornoSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcRetornoSimActionPerformed
        JcRetornoNao.setSelected(false);
    }//GEN-LAST:event_JcRetornoSimActionPerformed

    private void JcRetornoNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcRetornoNaoActionPerformed
        JcRetornoSim.setSelected(false);
    }//GEN-LAST:event_JcRetornoNaoActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        try {

            if (tabela.getSelectedRow() != -1) {
                DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                int seectedRow = tabela.getSelectedRow();
                jTabbedPane1.setSelectedIndex(0);

                EdCodSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                EdCodFuncio.setText(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
                validarCodCadastro();
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(seectedRow, 3).toString());
                JDcDataSaida.setDate(date);
                EdHoraSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());

                int retorno = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 5).toString());

                switch (retorno) {
                    case 1:
                        JcRetornoSim.setSelected(true);
                        JcRetornoNao.setSelected(false);
                        break;
                    case 2:
                        JcRetornoNao.setSelected(true);
                        JcRetornoSim.setSelected(false);
                        break;
                    default:
                        JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro na aplicação, contate administrador do sistema\nErro: buscar_retorno(retorno)", "Erro", JOptionPane.ERROR_MESSAGE);
                        break;
                }

                int motivo = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 6).toString());

                switch (motivo) {
                    case 1:
                        JcMotivoMedico.setSelected(true);
                        JcMotivoPartricular.setSelected(false);
                        JcMotivoOutros.setSelected(false);
                        break;
                    case 2:
                        JcMotivoMedico.setSelected(false);
                        JcMotivoPartricular.setSelected(true);
                        JcMotivoOutros.setSelected(false);
                        break;
                    case 3:
                        JcMotivoMedico.setSelected(false);
                        JcMotivoPartricular.setSelected(false);
                        JcMotivoOutros.setSelected(true);
                        break;
                    default:
                        fdErroOcorrido = new FDErroOcorrido(null, true);
                        fdErroOcorrido.LbInformaErro.setText("listar cadastro");
                        FDErroOcorrido.TaCodigoErro.setText("listar motivos saída");
                        fdErroOcorrido.setVisible(true);
                        break;
                }

                JtLocalSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 7).toString());

                int status = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 9).toString());

                switch (status) {
                    case 1:
                        LbStatus.setText("Saída não confirmada");
                        LbStatus.setForeground(Color.decode("#ff0000"));
                        habilitarB(3);
                        break;
                    case 2:
                        LbStatus.setText("Saída confirmada");
                        LbStatus.setForeground(Color.decode("#0f943b"));
                        habilitarB(4);
                        break;
                    default:
                        System.out.println("erro ao buscar status: " + status);
                        break;
                }

            }
        } catch (NumberFormatException | SQLException | ParseException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void BtBuscarCodSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarCodSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtBuscarCodSaidaActionPerformed

    private void JcSaidaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcSaidaNActionPerformed
        JcSaidaS.setSelected(false);
    }//GEN-LAST:event_JcSaidaNActionPerformed

    private void JcSaidaSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcSaidaSActionPerformed
        JcSaidaN.setSelected(false);
    }//GEN-LAST:event_JcSaidaSActionPerformed

    private void JtLocalSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtLocalSaidaMouseClicked

    }//GEN-LAST:event_JtLocalSaidaMouseClicked

    private void tabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseEntered

    }//GEN-LAST:event_tabelaMouseEntered

    private void EdBuscaNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdBuscaNomeKeyPressed
        readSaida();
    }//GEN-LAST:event_EdBuscaNomeKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscarCodFuncio;
    private javax.swing.JButton BtBuscarCodSaida;
    private javax.swing.JButton BtBuscarNomeFuncio;
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtNovo;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JTextField EdBuscaNome;
    private javax.swing.JTextField EdCargoFuncio;
    private javax.swing.JTextField EdCodFuncio;
    private javax.swing.JTextField EdCodSaida;
    private javax.swing.JFormattedTextField EdHoraSaida;
    private javax.swing.JTextField EdNomeFuncio;
    private javax.swing.JTextField EdSetorFuncio;
    private com.toedter.calendar.JDateChooser JDcDataSaida;
    private javax.swing.JCheckBox JcMotivoMedico;
    private javax.swing.JCheckBox JcMotivoOutros;
    private javax.swing.JCheckBox JcMotivoPartricular;
    private javax.swing.JCheckBox JcRetornoNao;
    private javax.swing.JCheckBox JcRetornoSim;
    private javax.swing.JCheckBox JcSaidaN;
    private javax.swing.JCheckBox JcSaidaS;
    private javax.swing.JTextArea JtLocalSaida;
    private javax.swing.JLabel LbResponsavel;
    private javax.swing.JLabel LbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
