package hard.home.view;

import hard.home.model.CalcMp;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class FrCalcMp extends javax.swing.JInternalFrame {

    public FrCalcMp() {
        initComponents();
        startTela();
    }

    CalcMp obj = new CalcMp();

    public void startTela() {
        //TELA INICIAL

        //Não Visivel
        altura.setVisible(false);
        taltura.setVisible(false);
        largura.setVisible(false);
        tlargura.setVisible(false);
        comprimento.setVisible(false);
        diametroint.setVisible(false);
        tdiametroint.setVisible(false);
        bedame.setVisible(false);
        mm2.setVisible(false);
        medidabedame.setVisible(false);

        // Visivel
        dimensao.setVisible(true);
        tdimensao.setVisible(true);
        comprimento.setVisible(true);
        tcomprimento.setVisible(true);
        qbarras.setVisible(true);
        tqbarras.setVisible(true);
        resultado.setVisible(true);
        tresultado.setVisible(true);
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void executar() {
        String r = null;
        double vdimensao, vcomprimento, vtotal, vqbarras, vlargura, valtura, vresultado = 0, vresultbarras, vpesott = 0, vcalc1, vcalc2, vbedame = 3, vdiametroint = 0;
        DecimalFormat f = new DecimalFormat("0.000");

        //BEDAME
        //O sistema primeiro verifica se o botão do bedame está selecionado
        if (rbbedame.isSelected()) {

            if (bedame.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA o campo do BEDAME!!!");
            }

            //Insere o valor do campo Bedame nos calculos a baixo, quando o botão bedame está selecionado
            vbedame = Double.parseDouble(bedame.getText());

            //INÍCIO DO SELECT-CALCULO
            /* 1º Select Redondas*/
        }
        if (rbredonda.isSelected()) {

            if (dimensao.getText().equals("") || qbarras.getText().equals("") || comprimento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA todos os campos!!!");

            } else {

                vdimensao = Double.parseDouble(dimensao.getText());
                vcomprimento = Double.parseDouble(comprimento.getText());
                vqbarras = Double.parseDouble(qbarras.getText());

                //cálculo
                vresultado = ((((vdimensao * vdimensao) * 0.00617) * (vcomprimento + vbedame) / 1000) * vqbarras);

                resultado.setVisible(true);

                r = codigo.getText() + " = " + String.valueOf(f.format(vresultado));
                resultado.setText(" " + String.valueOf(f.format(vresultado)));

                obj.salvar(r);
                resultado.requestFocus();
                listaCalculo.setText(obj.CalcMp());

            }

            /* 2º Select Sextavadas*/
        } else if (rbsext.isSelected()) {

            if (dimensao.getText().equals("") || qbarras.getText().equals("") || comprimento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA todos os campos!!!");

            } else {
                vdimensao = Double.parseDouble(dimensao.getText());
                vcomprimento = Double.parseDouble(comprimento.getText());
                vqbarras = Double.parseDouble(qbarras.getText());

                //cálculo
                vresultado = ((((vdimensao * vdimensao) * 0.0068) * (vcomprimento + vbedame) / 1000) * vqbarras);

                resultado.setVisible(true);
                r = codigo.getText() + " = " + String.valueOf(f.format(vresultado));
                resultado.setText(String.valueOf(f.format(vresultado)));

                obj.salvar(r);
                resultado.requestFocus();
                listaCalculo.setText(obj.CalcMp());
            }

            /* 3º Select Quadradas e Retangulares*/
        } else if (rbquad.isSelected()) {

            if (largura.getText().equals("") || qbarras.getText().equals("") || altura.getText().equals("") || comprimento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA todos os campos!!!");

            } else {
                vlargura = Double.parseDouble(largura.getText());
                valtura = Double.parseDouble(altura.getText());
                vcomprimento = Double.parseDouble(comprimento.getText());
                vqbarras = Double.parseDouble(qbarras.getText());

                //cálculo
                vresultado = ((((valtura * vlargura) * 0.00785) * (vcomprimento + vbedame) / 1000) * vqbarras);

                resultado.setVisible(true);
                r = codigo.getText() + " = " + String.valueOf(f.format(vresultado));
                resultado.setText(String.valueOf(f.format(vresultado)));
                obj.salvar(r);
                resultado.requestFocus();
                listaCalculo.setText(obj.CalcMp());
            }

            /* 4º Select Tubo*/
        } else if (rbtubo.isSelected()) {

            if (dimensao.getText().equals("") || diametroint.getText().equals("") || qbarras.getText().equals("") || comprimento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA todos os campos!!!");

            } else {
                vdimensao = Double.parseDouble(dimensao.getText());
                vcomprimento = Double.parseDouble(comprimento.getText());
                vdiametroint = Double.parseDouble(diametroint.getText());
                vqbarras = Double.parseDouble(qbarras.getText());

                //cálculo
                vcalc1 = ((((vdiametroint * vdiametroint) * 0.00617) * (vcomprimento + vbedame) / 1000));
                vcalc2 = ((((vdimensao * vdimensao) * 0.00617) * (vcomprimento + vbedame) / 1000));
                vresultado = ((vcalc2 - vcalc1) * vqbarras);

                resultado.setVisible(true);
                r = codigo.getText() + " = " + String.valueOf(f.format(vresultado));
                resultado.setText(String.valueOf(f.format(vresultado)));

                obj.salvar(r);
                resultado.requestFocus();
                listaCalculo.setText(obj.CalcMp());

            }
        }

        //Calculo de número total de barras
        if (checkbarras.isSelected()) {

            if (pesott.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor PREENCHA o campo de PESO TOTAL!!!");

            } else {
                /*Calculo da quantidade total de barras */
                vcomprimento = Double.parseDouble(comprimento.getText());
                vpesott = Double.parseDouble(pesott.getText());
                vresultbarras = (vpesott / vresultado);
                vtotal = vcomprimento * vresultbarras;
                resultbarras.setVisible(true);
                resultbarras.setText(String.valueOf(f.format(vresultbarras)));
                metrostotal.setVisible(true);
                metrostotal.setText(String.valueOf(f.format(vtotal)));

            }
        }

    }

    public void limpar() {
        dimensao.setText(null);
        comprimento.setText(null);
        altura.setText(null);
        resultado.setText(null);
        qbarras.setText(null);
        codigo.setText(null);
        comprimento.setText(null);
        diametroint.setText(null);
        bedame.setText(null);
        lote.setText(null);
        largura.setText(null);
        pesott.setText(null);
        resultbarras.setText(null);
        metrostotal.setText(null);
        infseq.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calculo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        dimensao = new javax.swing.JTextField();
        comprimento = new javax.swing.JTextField();
        qbarras = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tdimensao = new javax.swing.JLabel();
        tcomprimento = new javax.swing.JLabel();
        tqbarras = new javax.swing.JLabel();
        resultado = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        tresultado = new javax.swing.JLabel();
        rbredonda = new javax.swing.JRadioButton();
        rbsext = new javax.swing.JRadioButton();
        rbquad = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        taltura = new javax.swing.JLabel();
        altura = new javax.swing.JTextField();
        tlargura = new javax.swing.JLabel();
        largura = new javax.swing.JTextField();
        btlimpar = new javax.swing.JButton();
        rbtubo = new javax.swing.JRadioButton();
        tdiametroint = new javax.swing.JLabel();
        diametroint = new javax.swing.JTextField();
        pbedame = new javax.swing.JRadioButton();
        rbbedame = new javax.swing.JRadioButton();
        bedame = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        kg1 = new javax.swing.JLabel();
        mm1 = new javax.swing.JLabel();
        mm2 = new javax.swing.JLabel();
        mm3 = new javax.swing.JLabel();
        un1 = new javax.swing.JLabel();
        medidabedame = new javax.swing.JLabel();
        infbedamequad = new javax.swing.JLabel();
        infbedame = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        pesott = new javax.swing.JTextField();
        resultbarras = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        checkbarras = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        metrostotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lote = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaCalculo = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        infseq = new javax.swing.JTextField();
        btexcluir = new javax.swing.JButton();
        bteditar = new javax.swing.JButton();
        btlimpalista = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(26, 35, 38));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Britannic Bold", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Calc.MP.Imac");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 23));

        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        jPanel1.add(codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 30));

        dimensao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dimensaoActionPerformed(evt);
            }
        });
        dimensao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dimensaoKeyReleased(evt);
            }
        });
        jPanel1.add(dimensao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 70, 30));

        comprimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprimentoActionPerformed(evt);
            }
        });
        comprimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comprimentoKeyReleased(evt);
            }
        });
        jPanel1.add(comprimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 70, 30));

        qbarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qbarrasActionPerformed(evt);
            }
        });
        qbarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qbarrasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qbarrasKeyReleased(evt);
            }
        });
        jPanel1.add(qbarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 70, 30));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Código do item");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        tdimensao.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        tdimensao.setForeground(new java.awt.Color(255, 255, 255));
        tdimensao.setText("Ø Externo");
        jPanel1.add(tdimensao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        tcomprimento.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        tcomprimento.setForeground(new java.awt.Color(255, 255, 255));
        tcomprimento.setText("Comprimento");
        jPanel1.add(tcomprimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        tqbarras.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        tqbarras.setForeground(new java.awt.Color(255, 255, 255));
        tqbarras.setText("Nº Barras");
        jPanel1.add(tqbarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, 20));

        resultado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultadoActionPerformed(evt);
            }
        });
        jPanel1.add(resultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 130, 60));

        jButton1.setText("Calcular");
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
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 160, 50));

        tresultado.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        tresultado.setForeground(new java.awt.Color(255, 255, 255));
        tresultado.setText("Resultado");
        jPanel1.add(tresultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, -1));

        rbredonda.setBackground(new java.awt.Color(75, 103, 112));
        calculo.add(rbredonda);
        rbredonda.setForeground(new java.awt.Color(255, 255, 255));
        rbredonda.setSelected(true);
        rbredonda.setText("Redondas");
        rbredonda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbredondaMouseClicked(evt);
            }
        });
        rbredonda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbredondaActionPerformed(evt);
            }
        });
        jPanel1.add(rbredonda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        rbsext.setBackground(new java.awt.Color(75, 103, 112));
        calculo.add(rbsext);
        rbsext.setForeground(new java.awt.Color(255, 255, 255));
        rbsext.setText("Sextavadas");
        rbsext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbsextMouseClicked(evt);
            }
        });
        rbsext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbsextActionPerformed(evt);
            }
        });
        jPanel1.add(rbsext, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));

        rbquad.setBackground(new java.awt.Color(75, 103, 112));
        calculo.add(rbquad);
        rbquad.setForeground(new java.awt.Color(255, 255, 255));
        rbquad.setText("Quad. Ret.");
        rbquad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbquadMouseClicked(evt);
            }
        });
        rbquad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbquadActionPerformed(evt);
            }
        });
        jPanel1.add(rbquad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, -1, -1));

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 80, 40));

        taltura.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        taltura.setForeground(new java.awt.Color(255, 255, 255));
        taltura.setText("Altura");
        jPanel1.add(taltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        altura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                alturaKeyReleased(evt);
            }
        });
        jPanel1.add(altura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 70, 30));

        tlargura.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        tlargura.setForeground(new java.awt.Color(255, 255, 255));
        tlargura.setText("Largura");
        jPanel1.add(tlargura, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, 20));

        largura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                larguraKeyReleased(evt);
            }
        });
        jPanel1.add(largura, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 70, 30));

        btlimpar.setText("Limpar");
        btlimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlimparActionPerformed(evt);
            }
        });
        btlimpar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btlimparKeyPressed(evt);
            }
        });
        jPanel1.add(btlimpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 70, 40));

        rbtubo.setBackground(new java.awt.Color(75, 103, 112));
        calculo.add(rbtubo);
        rbtubo.setForeground(new java.awt.Color(255, 255, 255));
        rbtubo.setText("Tubo");
        rbtubo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtuboMouseClicked(evt);
            }
        });
        rbtubo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtuboActionPerformed(evt);
            }
        });
        jPanel1.add(rbtubo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        tdiametroint.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        tdiametroint.setForeground(new java.awt.Color(255, 255, 255));
        tdiametroint.setText("Ø Interno");
        jPanel1.add(tdiametroint, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 90, 20));

        diametroint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diametrointKeyReleased(evt);
            }
        });
        jPanel1.add(diametroint, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 70, 30));

        pbedame.setForeground(new java.awt.Color(255, 255, 255));
        pbedame.setSelected(true);
        pbedame.setText(" Padrão");
        pbedame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pbedameActionPerformed(evt);
            }
        });
        jPanel1.add(pbedame, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        rbbedame.setForeground(new java.awt.Color(255, 255, 255));
        rbbedame.setText("Informar");
        rbbedame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbbedameActionPerformed(evt);
            }
        });
        jPanel1.add(rbbedame, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, -1));

        bedame.setText("3");
        jPanel1.add(bedame, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 60, 30));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel5.setText("Bedame");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, -1, -1));

        kg1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        kg1.setForeground(new java.awt.Color(255, 255, 255));
        kg1.setText("Kg");
        jPanel1.add(kg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, -1, -1));

        mm1.setForeground(new java.awt.Color(255, 255, 255));
        mm1.setText("mm");
        jPanel1.add(mm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        mm2.setForeground(new java.awt.Color(255, 255, 255));
        mm2.setText("mm");
        jPanel1.add(mm2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));

        mm3.setForeground(new java.awt.Color(255, 255, 255));
        mm3.setText("mm");
        jPanel1.add(mm3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        un1.setForeground(new java.awt.Color(255, 255, 255));
        un1.setText("Un");
        jPanel1.add(un1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, -1, -1));

        medidabedame.setForeground(new java.awt.Color(255, 255, 255));
        medidabedame.setText("mm");
        jPanel1.add(medidabedame, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, -1, -1));

        infbedamequad.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPanel1.add(infbedamequad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 20, -1));

        infbedame.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        infbedame.setText("= 3");
        jPanel1.add(infbedame, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 193, 20, 30));

        jPanel2.setBackground(new java.awt.Color(75, 103, 112));
        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Peso Total");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 40, 70, 21);

        pesott.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesottActionPerformed(evt);
            }
        });
        pesott.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pesottKeyPressed(evt);
            }
        });
        jPanel2.add(pesott);
        pesott.setBounds(10, 70, 60, 30);
        jPanel2.add(resultbarras);
        resultbarras.setBounds(120, 70, 60, 30);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nº Total Barras");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(100, 40, 104, 21);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kg");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(70, 90, 40, 20);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Un");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(180, 90, 30, 16);

        checkbarras.setBackground(new java.awt.Color(75, 103, 112));
        checkbarras.setForeground(new java.awt.Color(255, 255, 255));
        checkbarras.setText("Calcular Número Total de Barras");
        checkbarras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkbarrasMouseClicked(evt);
            }
        });
        jPanel2.add(checkbarras);
        checkbarras.setBounds(10, 10, 210, 18);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("=");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(90, 70, 20, 30);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Medida Total");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(220, 40, 100, 20);

        metrostotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metrostotalActionPerformed(evt);
            }
        });
        jPanel2.add(metrostotal);
        metrostotal.setBounds(220, 70, 80, 30);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("mm");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(300, 90, 30, 16);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 340, 110));

        jPanel3.setBackground(new java.awt.Color(75, 103, 112));
        jPanel3.setLayout(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4);
        jPanel4.setBounds(-10, -40, 350, 40);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Número do Lote");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(170, 50, 112, 21);

        lote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loteKeyReleased(evt);
            }
        });
        jPanel3.add(lote);
        lote.setBounds(170, 80, 150, 30);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 340, 120));

        jPanel5.setBackground(new java.awt.Color(75, 103, 112));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("RESULTADOS");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        listaCalculo.setColumns(20);
        listaCalculo.setRows(5);
        jScrollPane1.setViewportView(listaCalculo);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 240, 320));

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Informe a Sequência Para Excluir");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        infseq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infseqActionPerformed(evt);
            }
        });
        infseq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                infseqKeyReleased(evt);
            }
        });
        jPanel5.add(infseq, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 70, 30));

        btexcluir.setText("Excluir");
        btexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btexcluirActionPerformed(evt);
            }
        });
        jPanel5.add(btexcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 70, 30));

        bteditar.setText("Editar");
        bteditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditarActionPerformed(evt);
            }
        });
        jPanel5.add(bteditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 80, 30));

        btlimpalista.setText("Limpar");
        btlimpalista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlimpalistaActionPerformed(evt);
            }
        });
        jPanel5.add(btlimpalista, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 70, 30));

        jButton3.setText("Exportar");
        jPanel5.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 80, 30));

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 70, 30));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 270, 490));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 551));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        
    }//GEN-LAST:event_codigoActionPerformed

    private void dimensaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dimensaoActionPerformed
       
    }//GEN-LAST:event_dimensaoActionPerformed

    private void dimensaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dimensaoKeyReleased

    }//GEN-LAST:event_dimensaoKeyReleased

    private void comprimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprimentoActionPerformed
       
    }//GEN-LAST:event_comprimentoActionPerformed

    private void comprimentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comprimentoKeyReleased

    }//GEN-LAST:event_comprimentoKeyReleased

    private void qbarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qbarrasActionPerformed
        
    }//GEN-LAST:event_qbarrasActionPerformed

    private void qbarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qbarrasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            executar();
        }
    }//GEN-LAST:event_qbarrasKeyPressed

    private void qbarrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qbarrasKeyReleased

    }//GEN-LAST:event_qbarrasKeyReleased

    private void resultadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultadoActionPerformed

    }//GEN-LAST:event_resultadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        executar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            executar();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void rbredondaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbredondaMouseClicked
        //Calculo REDONDAS

        // NÃO VISIVEL
        altura.setVisible(false);
        taltura.setVisible(false);
        largura.setVisible(false);
        tlargura.setVisible(false);
        comprimento.setVisible(false);
        diametroint.setVisible(false);
        tdiametroint.setVisible(false);
        mm2.setVisible(false);

        // VISIVEL
        dimensao.setVisible(true);
        tdimensao.setVisible(true);
        comprimento.setVisible(true);
        tcomprimento.setVisible(true);
        qbarras.setVisible(true);
        tqbarras.setVisible(true);
    }//GEN-LAST:event_rbredondaMouseClicked

    private void rbredondaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbredondaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbredondaActionPerformed

    private void rbsextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbsextMouseClicked
        altura.setVisible(false);
        taltura.setVisible(false);
        largura.setVisible(false);
        tlargura.setVisible(false);
        comprimento.setVisible(false);
        diametroint.setVisible(false);
        tdiametroint.setVisible(false);
        mm2.setVisible(false);

        dimensao.setVisible(true);
        tdimensao.setVisible(true);
        comprimento.setVisible(true);
        tcomprimento.setVisible(true);
        qbarras.setVisible(true);
        tqbarras.setVisible(true);

    }//GEN-LAST:event_rbsextMouseClicked

    private void rbsextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbsextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbsextActionPerformed

    private void rbquadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbquadMouseClicked
        dimensao.setVisible(false);
        tdimensao.setVisible(false);
        comprimento.setVisible(false);
        diametroint.setVisible(false);
        tdiametroint.setVisible(false);

        altura.setVisible(true);
        taltura.setVisible(true);
        largura.setVisible(true);
        tlargura.setVisible(true);
        comprimento.setVisible(true);
        tcomprimento.setVisible(true);
        qbarras.setVisible(true);
        tqbarras.setVisible(true);
        mm2.setVisible(true);

    }//GEN-LAST:event_rbquadMouseClicked

    private void rbquadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbquadActionPerformed

    }//GEN-LAST:event_rbquadActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dispose();
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void alturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alturaKeyReleased

    }//GEN-LAST:event_alturaKeyReleased

    private void larguraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_larguraKeyReleased

    }//GEN-LAST:event_larguraKeyReleased

    private void btlimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlimparActionPerformed
        limpar();

    }//GEN-LAST:event_btlimparActionPerformed

    private void btlimparKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btlimparKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            limpar();
        }
    }//GEN-LAST:event_btlimparKeyPressed

    private void rbtuboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtuboMouseClicked
        altura.setVisible(false);
        taltura.setVisible(false);
        largura.setVisible(false);
        tlargura.setVisible(false);

        comprimento.setVisible(true);
        tdiametroint.setVisible(true);
        diametroint.setVisible(true);
        dimensao.setVisible(true);
        tdimensao.setVisible(true);
        comprimento.setVisible(true);
        tcomprimento.setVisible(true);
        qbarras.setVisible(true);
        tqbarras.setVisible(true);
        mm2.setVisible(true);
    }//GEN-LAST:event_rbtuboMouseClicked

    private void rbtuboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtuboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtuboActionPerformed

    private void diametrointKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diametrointKeyReleased

    }//GEN-LAST:event_diametrointKeyReleased

    private void pbedameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pbedameActionPerformed
        bedame.setVisible(false);
        medidabedame.setVisible(false);
    }//GEN-LAST:event_pbedameActionPerformed

    private void rbbedameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbbedameActionPerformed
        bedame.setVisible(true);
        medidabedame.setVisible(true);

    }//GEN-LAST:event_rbbedameActionPerformed

    private void pesottActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesottActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesottActionPerformed

    private void pesottKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesottKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesottKeyPressed

    private void checkbarrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkbarrasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbarrasMouseClicked

    private void metrostotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metrostotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_metrostotalActionPerformed

    private void loteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loteKeyReleased

    }//GEN-LAST:event_loteKeyReleased

    private void infseqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infseqActionPerformed

    }//GEN-LAST:event_infseqActionPerformed

    private void infseqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_infseqKeyReleased

    }//GEN-LAST:event_infseqKeyReleased

    private void btexcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btexcluirActionPerformed
        if (!infseq.getText().equals("")) {
            obj.excluir(Integer.parseInt(infseq.getText()));
        } else {
            JOptionPane.showMessageDialog(null, "Insira uma Sequência para excluír");
        }
        listaCalculo.setText(obj.CalcMp());
    }//GEN-LAST:event_btexcluirActionPerformed

    private void bteditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditarActionPerformed
        if (!listaCalculo.getText().equals("")) {
            obj.editar(Integer.parseInt(infseq.getText()));
        } else {
            JOptionPane.showMessageDialog(null, "Insira um código");
        }
        listaCalculo.setText(obj.CalcMp());

    }//GEN-LAST:event_bteditarActionPerformed

    private void btlimpalistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlimpalistaActionPerformed
        obj.limpar();
        listaCalculo.setText(obj.CalcMp());
    }//GEN-LAST:event_btlimpalistaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField altura;
    private javax.swing.JTextField bedame;
    private javax.swing.JButton bteditar;
    private javax.swing.JButton btexcluir;
    private javax.swing.JButton btlimpalista;
    private javax.swing.JButton btlimpar;
    private javax.swing.ButtonGroup calculo;
    private javax.swing.JCheckBox checkbarras;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField comprimento;
    private javax.swing.JTextField diametroint;
    private javax.swing.JTextField dimensao;
    private javax.swing.JLabel infbedame;
    private javax.swing.JLabel infbedamequad;
    private javax.swing.JTextField infseq;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel kg1;
    private javax.swing.JTextField largura;
    private javax.swing.JTextArea listaCalculo;
    private javax.swing.JTextField lote;
    private javax.swing.JLabel medidabedame;
    private javax.swing.JTextField metrostotal;
    private javax.swing.JLabel mm1;
    private javax.swing.JLabel mm2;
    private javax.swing.JLabel mm3;
    private javax.swing.JRadioButton pbedame;
    private javax.swing.JTextField pesott;
    private javax.swing.JTextField qbarras;
    private javax.swing.JRadioButton rbbedame;
    private javax.swing.JRadioButton rbquad;
    private javax.swing.JRadioButton rbredonda;
    private javax.swing.JRadioButton rbsext;
    private javax.swing.JRadioButton rbtubo;
    private javax.swing.JTextField resultado;
    private javax.swing.JTextField resultbarras;
    private javax.swing.JLabel taltura;
    private javax.swing.JLabel tcomprimento;
    private javax.swing.JLabel tdiametroint;
    private javax.swing.JLabel tdimensao;
    private javax.swing.JLabel tlargura;
    private javax.swing.JLabel tqbarras;
    private javax.swing.JLabel tresultado;
    private javax.swing.JLabel un1;
    // End of variables declaration//GEN-END:variables
}
