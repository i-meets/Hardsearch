package hard.engenharia.view;

import hard.engenharia.dao.AlteracaoDao;
import hard.engenharia.model.Alteracao;
import hard.engenharia.model.Arquivos;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FDProcessaSolita extends javax.swing.JDialog {

    String raiz = "\\\\192.168.24.251\\Imac\\Engenharia\\Privado\\Desktop\\hard_arq\\";
    private final String diretorio;
    int codAlte = 0;
    int codSoli = 0;
    String link_arquivo = "";

    /**
     * Creates new form FDContRev
     *
     * @param codAl
     * @param codSol
     * @param parent
     * @param modal
     */
    public FDProcessaSolita(String codAl, String codSol, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.diretorio = "\\\\192.168.24.251\\Imac\\Engenharia\\Privado\\Desenhos\\";
        initComponents();
        setLocationRelativeTo(null);
        EdCodAlteracao.setText(codAl);
        EdCodSolicitacao.setText(codSol);
        EdCodAlteracao.setEditable(false);
        EdCodSolicitacao.setEditable(false);
        listarAlteracao();
    }

    private FDProcessaSolita(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void listarAlteracao() {
        try {

            AlteracaoDao dao = new AlteracaoDao();
            codAlte = Integer.parseInt(EdCodAlteracao.getText());
            codSoli = Integer.parseInt(EdCodSolicitacao.getText());
            SimpleDateFormat fdt = new SimpleDateFormat("dd/MM/yyyy");
            for (Alteracao a : dao.readAlteracaoForCod(codAlte, codSoli)) {
                EdCodItem.setText(a.getFr_codItem());
                lbDescriItem.setText(a.getFr_descricaoItem());
                LbRevInternaAtual.setText(Integer.toString(a.getRevInterna_a()));
                EdDataAlteraInterna.setText(fdt.format(a.getDataRevInterna_a()));
                EdResponsavel.setText(a.getResponsavel());
                EdCodCliente.setText(Integer.toString(a.getFr_codEmpresa()));
                lbNomeCliente.setText(a.getFr_nomeEmpresa());
                LbRevClienteAtual.setText(a.getRevCliente_a());
                EdDataAlteraCliente.setText(fdt.format(a.getDataRevCliente_a()));
                int status = a.getStatus();
                switch (status) {
                    case 1:
                        CbSituacao.setSelectedItem("NÃO PROCESSADO");
                        break;
                    case 2:
                        CbSituacao.setSelectedItem("EM ANÁLISE");
                        break;
                    case 3:
                        CbSituacao.setSelectedItem("CONCLUÍDO");
                        break;
                    case 4:
                        CbSituacao.setSelectedItem("CANCELADO");
                        break;

                }
            }
            listarArquivos();

        } catch (ClassNotFoundException | NumberFormatException ex) {
            System.out.println("Erro: " + ex);
        }

    }

    public void listarArquivos() {
        try {

            AlteracaoDao dao = new AlteracaoDao();
            DefaultTableModel modelo = (DefaultTableModel) tbListaNomeArquivos.getModel();
            modelo.setNumRows(0);

            for (Arquivos ar : dao.readArquiForAltera(codAlte)) {
                String cliente = lbNomeCliente.getText();
                String codItem = EdCodItem.getText();
                File dir = new File(raiz + cliente + "\\" + codItem + "\\" + codAlte + "_" + codItem + "\\" + codItem);

                String descricao = ar.getLink();

                File dirNameArq = new File(dir + "\\" + descricao);
                System.out.println(dirNameArq);

                if (dirNameArq.exists()) {
                    modelo.addRow(new Object[]{
                        ar.getLink(),
                        true
                    });
                } else {
                    modelo.addRow(new Object[]{
                        ar.getLink(),
                        false
                    });
                }

            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public void importaArquivos() throws IOException, ClassNotFoundException, SQLException {

        AlteracaoDao adao = new AlteracaoDao();
        Arquivos ar = new Arquivos();

        DefaultTableModel modelo = (DefaultTableModel) tbListaNomeArquivos.getModel();
        modelo.setNumRows(0);

        try {
            String cliente = lbNomeCliente.getText();
            String codItem = EdCodItem.getText();
            String revClienteAtual = LbRevClienteAtual.getText();
            String revInternaAtual = LbRevInternaAtual.getText();

            //CAMINHO DA PASTA DESENHOS + CLIENTE + ITEM + REVINTERNA_ITEM_REVCLIENTE + CODITEM_PRIV
            File file = new File(diretorio + cliente + "\\" + codItem + "\\" + revClienteAtual + "_" + codItem + "_" + revInternaAtual + "\\" + codItem + "_PRIV");
            File afile[] = file.listFiles();
            int i = 0;

//########## INICIO DA BAGAÇA ##################            
//FOR QUE LISTA TODOS OS ITENS DA PASTA DESENHOS
            for (int j = afile.length; i < j; i++) {
                File link = afile[i];
                String caminho = link.getAbsolutePath();
                caminho = caminho.replace('\\', '/');
                String arquivo = new File(caminho).getName();

                // FAZ UM SPLIT PARA A SEPARAÇÃO DOS DADOS DO ITEM, COMO EXTERSÃO, REVISÃO E CÓDIGO, PARA O NOME SER MANIPULADO.
                String[] splitted = arquivo.split("_|[.]");
                String codIt = splitted[0];
//                String revCli = splitted[1];
                String seq = splitted[2];
//                String revInt = splitted[3];
                String ext = splitted[4];

                int iRevInterna = Integer.parseInt(revInternaAtual);
                int iRevCliente = Integer.parseInt(revClienteAtual);

                //VALIDAÇÃO DA REVISÃO SELECIONADA
                if (cbTipoRevisao.getSelectedItem().equals("Revisão interna")) {
//                    iRevInterna++;
                } else if (cbTipoRevisao.getSelectedItem().equals("Revisão de clinte")) {
//                    iRevCliente++;
//                    iRevInterna = 1;
                }

//LINK DA PASTA DESKTOP, COM VALIDAÇÃO, VERIFICANDO SE A PASTA COM OS DADOS DOS ITENS EXISTE, SE NÃO EXISTE CRIAR PARA PODE COPIAR OS ITENS DA PASTA DESENHOS
//                File dir = new File(raiz + cliente + "\\" + codItem + "\\" + codAlte + codItem + "\\" + codItem + "_PRIV");
                File dir = new File(raiz + cliente + "\\" + codItem + "\\" + codAlte + "_" + codItem);
//                 String nomeArquivo = codIt + "_" + iRevCliente + "_" + seq + "_" + iRevInterna + "." + ext;
                String nomeArquivo = codIt + "_" + seq + "." + ext;

                File dirNameArq = new File(dir + "\\" + nomeArquivo);

                if (!dir.exists()) {//VERIFICA SE O DIRETORIO EXISTE
                    dir.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios. 
                    if (dirNameArq.exists()) {
                        //NÃO COPIA O ARQUIVO QUE JÁ EXISTE NA PASTA, SÓ LISTA ELE NA TABELA
                        modelo.addRow(new Object[]{
                            nomeArquivo,
                            false
                        });
                    } else {
                        copyFile(link, dirNameArq);
                        if (dirNameArq.exists()) {
                            modelo.addRow(new Object[]{
                                nomeArquivo,
                                true
                            });

                            ar.setFr_tipoArquivoCod(1);//validar o tipo de arquivo
                            ar.setLink(nomeArquivo);
                            ar.setFr_alteracaoRevCod(iRevInterna);
                            adao.saveArqAltera(ar);

                        } else {
                            modelo.addRow(new Object[]{
                                nomeArquivo,
                                false
                            });
                        }
                    }

                } else {

                    if (dirNameArq.exists()) {
                        //NÃO COPIA O ARQUIVO QUE JÁ EXISTE NA PASTA, SÓ LISTA ELE NA TABELA
                        modelo.addRow(new Object[]{
                            nomeArquivo,
                            false
                        });
                    } else {
                        copyFile(link, dirNameArq);
                        if (dirNameArq.exists()) {
                            modelo.addRow(new Object[]{
                                nomeArquivo,
                                true
                            });
                            ar.setFr_tipoArquivoCod(1);//validar o tipo de arquivo
                            ar.setLink(nomeArquivo);
                            ar.setFr_alteracaoRevCod(iRevInterna);
                            adao.saveArqAltera(ar);
                        } else {
                            modelo.addRow(new Object[]{
                                nomeArquivo,
                                false
                            });
                        }
                    }
//                    }
                }
            }
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(rootPane, "Pasta não encontrada em desenhos!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        EdCodCliente = new javax.swing.JTextField();
        EdDataAlteraCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        LbRevClienteAtual = new javax.swing.JLabel();
        lbNomeCliente = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        EdCodItem = new javax.swing.JTextField();
        EdResponsavel = new javax.swing.JTextField();
        EdDataAlteraInterna = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        LbRevInternaAtual = new javax.swing.JLabel();
        lbDescriItem = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        EdCodAlteracao = new javax.swing.JTextField();
        EdCodSolicitacao = new javax.swing.JTextField();
        CbSituacao = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaNomeArquivos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        BtSeleciManual = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cbTipoRevisao = new javax.swing.JComboBox<>();
        cbPossuiExt = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        BtExcluir = new javax.swing.JButton();
        BtSair = new javax.swing.JButton();
        BtSalvar = new javax.swing.JButton();
        BtSalvarEdit = new javax.swing.JButton();
        BtCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Cliente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdDataAlteraCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jLabel3.setText("Rev. Cliente Atual:");

        LbRevClienteAtual.setText("0");
        LbRevClienteAtual.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbNomeCliente.setText("DESCRIÇÃO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LbRevClienteAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdDataAlteraCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdDataAlteraCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNomeCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LbRevClienteAtual)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Item:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdResponsavel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Responsável:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdDataAlteraInterna.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        jLabel1.setText("Rev. Interna Atual:");

        LbRevInternaAtual.setText("0");
        LbRevInternaAtual.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbDescriItem.setText("DESCRIÇÃO");
        lbDescriItem.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbDescriItem.setPreferredSize(new java.awt.Dimension(68, 16));
        lbDescriItem.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LbRevInternaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbDescriItem, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EdDataAlteraInterna, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(EdResponsavel))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EdCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdDataAlteraInterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbDescriItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(LbRevInternaAtual)))
                    .addComponent(EdResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EdCodAlteracao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cód. Alteração:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        EdCodSolicitacao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código Solicitação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Yu Gothic UI Semibold", 0, 10))); // NOI18N

        CbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NÃO PROCESSADO", "EM ANÁLISE", "CONCLUÍDO", "CANCELADO" }));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/list_solicita.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EdCodAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdCodSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EdCodAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EdCodSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbListaNomeArquivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Arquivo", "Link"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListaNomeArquivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaNomeArquivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListaNomeArquivos);
        if (tbListaNomeArquivos.getColumnModel().getColumnCount() > 0) {
            tbListaNomeArquivos.getColumnModel().getColumn(1).setMinWidth(60);
            tbListaNomeArquivos.getColumnModel().getColumn(1).setPreferredWidth(60);
            tbListaNomeArquivos.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/padClose.png"))); // NOI18N
        jButton3.setText("FECHAR");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/padOpen.png"))); // NOI18N
        jButton6.setText("REABRIR");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upload.png"))); // NOI18N
        jButton8.setText("GERAR");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 10)); // NOI18N
        jLabel6.setText("Gerar arquivos públicos:");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 10)); // NOI18N
        jLabel2.setText("Conclusão da Solicitação:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6))
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Adicionar arquivos:");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/attach.png"))); // NOI18N
        jButton4.setText("IMPORTAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        jButton5.setText("EXCLUÍR");

        BtSeleciManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/manual.png"))); // NOI18N
        BtSeleciManual.setText("MANUAL");
        BtSeleciManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSeleciManualActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/archive.png"))); // NOI18N
        jButton9.setText("ABRIR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbTipoRevisao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Tipo de Revisão>", "Revisão interna", "Revisão de clinte", "Ambas" }));

        cbPossuiExt.setText("Possui Extenção");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbTipoRevisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPossuiExt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipoRevisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPossuiExt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("TIPO_ARQUIVO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 3, Short.MAX_VALUE)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtSeleciManual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)
                                .addGap(42, 42, 42)
                                .addComponent(jButton5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(115, 115, 115)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(BtSeleciManual)
                            .addComponent(jButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Processamento de Solicitações", jPanel1);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_red.png"))); // NOI18N
        BtExcluir.setText("EXCLUÍR");
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });
        jPanel6.add(BtExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 100, 33));

        BtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_black.png"))); // NOI18N
        BtSair.setText("SAIR");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });
        jPanel6.add(BtSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 80, 33));

        BtSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvar.setText("SALVAR");
        BtSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarActionPerformed(evt);
            }
        });
        jPanel6.add(BtSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 100, 33));

        BtSalvarEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save-blue.png"))); // NOI18N
        BtSalvarEdit.setText("SALVAR");
        BtSalvarEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSalvarEditActionPerformed(evt);
            }
        });
        jPanel6.add(BtSalvarEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 100, 33));

        BtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return_red.png"))); // NOI18N
        BtCancelar.setText("CANCELAR");
        BtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelarActionPerformed(evt);
            }
        });
        jPanel6.add(BtCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 33));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed

    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        dispose();
    }//GEN-LAST:event_BtSairActionPerformed

    private void BtSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarActionPerformed

    }//GEN-LAST:event_BtSalvarActionPerformed

    private void BtSalvarEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSalvarEditActionPerformed

    }//GEN-LAST:event_BtSalvarEditActionPerformed

    private void BtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelarActionPerformed

    }//GEN-LAST:event_BtCancelarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (cbTipoRevisao.getSelectedItem().equals("<Tipo de Revisão>")) {
            JOptionPane.showMessageDialog(rootPane, "Você deve informar o tipo de revisão", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                importaArquivos();
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FDProcessaSolita.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void BtSeleciManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSeleciManualActionPerformed
        if (cbPossuiExt.isSelected()) {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(this);

            try {

                File arquivo = fc.getSelectedFile();
                String caminho = arquivo.getAbsolutePath();
                caminho = caminho.replace('\\', '/');
                String nomeArq = new File(caminho).getName();
                System.out.println(nomeArq);

            } catch (Exception e) {
                System.out.println("Deu tudo errado...");
            }
        }
    }//GEN-LAST:event_BtSeleciManualActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (!link_arquivo.equals("")) {
            try {

                Desktop d = Desktop.getDesktop();
                d.open(new File(link_arquivo));

            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
//            fdErroOcorrido = new FDErroOcorrido(null, true);
//            fdErroOcorrido.LbInformaErro.setText("arquivo não encontrado");
//            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
//            fdErroOcorrido.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione o item na tabela para abrir", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tbListaNomeArquivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaNomeArquivosMouseClicked
        String cliente = lbNomeCliente.getText();
        String codItem = EdCodItem.getText();

        link_arquivo = raiz + cliente + "\\" + codItem + "\\" + codAlte + "_" + codItem + "\\" + codItem + "_PRIV" + "\\" + tbListaNomeArquivos.getValueAt(tbListaNomeArquivos.getSelectedRow(), 0).toString();
    }//GEN-LAST:event_tbListaNomeArquivosMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FDProcessaSolita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDProcessaSolita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDProcessaSolita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDProcessaSolita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FDProcessaSolita dialog = new FDProcessaSolita(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCancelar;
    private javax.swing.JButton BtExcluir;
    private javax.swing.JButton BtSair;
    private javax.swing.JButton BtSalvar;
    private javax.swing.JButton BtSalvarEdit;
    private javax.swing.JButton BtSeleciManual;
    private javax.swing.JComboBox<String> CbSituacao;
    private javax.swing.JTextField EdCodAlteracao;
    private javax.swing.JTextField EdCodCliente;
    private javax.swing.JTextField EdCodItem;
    private javax.swing.JTextField EdCodSolicitacao;
    private javax.swing.JTextField EdDataAlteraCliente;
    private javax.swing.JTextField EdDataAlteraInterna;
    private javax.swing.JTextField EdResponsavel;
    private javax.swing.JLabel LbRevClienteAtual;
    private javax.swing.JLabel LbRevInternaAtual;
    private javax.swing.JCheckBox cbPossuiExt;
    private javax.swing.JComboBox<String> cbTipoRevisao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbDescriItem;
    private javax.swing.JLabel lbNomeCliente;
    private javax.swing.JTable tbListaNomeArquivos;
    // End of variables declaration//GEN-END:variables
}
