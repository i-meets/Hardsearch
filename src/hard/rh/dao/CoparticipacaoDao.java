package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Coparticipacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CoparticipacaoDao {

    FDErroOcorrido fdErroOcorrido;

    private String lberro;
    private String taerro;

    public String getLberro() {
        return lberro;
    }

    public String getTaerro() {
        return taerro;
    }

    //checkCreate VERIFICA SE A COPARTICIPAÇÃO NÃO EXISTE
    public boolean checkCreate(String codCop) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("Select * from tbcoparti where codCop = ?");
            stmt.setString(1, codCop);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkCreatePro(String codCop) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM `tbrealiza` WHERE tbcoparti_codCop = ?");
            stmt.setString(1, codCop);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<Coparticipacao> readGeradoPar(Integer codCop) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("Select * from tbcoparti geradoPar where codCop = ?");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Coparticipacao co = new Coparticipacao();
                co.setGeradoPar(rs.getInt("geradoPar"));
                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    // LISTA O ´MAIOR CÓDIGO DA COPARTICIÁÇÃO CADASTRADA NO BANCO
    public List<Coparticipacao> readCodCop() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codCop) FROM tbcoparti");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Coparticipacao co = new Coparticipacao();
                co.setCodCop(rs.getInt("Max(codCop)"));
                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    //REALIZA A SOMA DOS VALORES DOS PROCEDIMENTOS CADASTRADOS NA COPARTICIPAÇÃO PELO CÓDIGO DA COPARTICIPÁÇÃO
    public List<Coparticipacao> readSumCop(Integer desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT SUM(valorProcedimento) FROM tbrealiza WHERE tbcoparti_codCop = ?");
            stmt.setInt(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Coparticipacao co = new Coparticipacao();

                co.setValorCop(rs.getDouble("SUM(valorProcedimento)"));

                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor procedimentos - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    public void createCop(Coparticipacao c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbcoparti(codCop,tbfuncionarios_codFuncionario, tbdependentes_codDependente, geradoPar) VALUES(?, ?, ?, ?)");
            stmt.setInt(1, c.getCodCop());
            stmt.setInt(2, c.getFr_codFuncionario());
            stmt.setInt(3, c.getFr_codBeneficiario());
            stmt.setInt(4, c.getGeradoPar());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar coparticipação - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void saveNfeImport(Coparticipacao c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO cop_nfe_i (numero_nfe, dataImporta, dataVencimento) VALUES (?, DATE(NOW()), ?)");

            stmt.setInt(1, c.getNfe());
            stmt.setDate(2, c.getDataVenciNfe());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar coparticipação - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Coparticipacao> readNfeImport() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT * FROM cop_nfe_i");
            rs = stmt.executeQuery();
            while (rs.next()) {

                Coparticipacao co = new Coparticipacao();
                co.setNfe(rs.getInt("numero_nfe"));
                co.setDataImporta(rs.getDate("dataImporta"));
                co.setDataVenciNfe(rs.getDate("dataVencimento"));
                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NF-e importada - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    //INSERT DOS DADOS DA COPARTICIPAÇÃO NO BANCO
    public void realizaProcedi(Coparticipacao c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbrealiza( tbprocedi_codPro, tbcoparti_codCop, valorProcedimento, tbcoparti_tbfuncionarios_codFuncionario, tbcoparti_tbdependentes_codDependente, dataPro, medicoPro, localPro, nTitulo) VALUES(?,?,?,?,?,?,?,?,?)");

            stmt.setInt(1, c.getFr_codPro());
            stmt.setInt(2, c.getCodCop());
            stmt.setDouble(3, c.getFr_valorPro());
            stmt.setInt(4, c.getFr_codFuncionario());
            stmt.setInt(5, c.getFr_codBeneficiario());
            stmt.setDate(6, c.getDataPro());
            stmt.setString(7, c.getMedicoPro());
            stmt.setString(8, c.getLocalPro());
            stmt.setInt(9, c.getNfe());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            int codPro = c.getFr_codPro();
            int codCop = c.getCodCop();
            double valor = c.getFr_valorPro();
            int codFun = c.getFr_codFuncionario();
            int codBen = c.getFr_codBeneficiario();
            String data = c.getDataPro().toString();
            int nf = c.getNfe();
            String log = "Dados da importação: codPro: " + codPro + " codCop: " + codCop + " valor: " + valor + " codFun: " + codFun + " codBen: " + codBen + " data: " + data + " nf: " + nf;

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("realizar procedimento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString() + "\n\n" + log);
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS AS COPARTICIPAÇÃOS CADASTRADAS NO BANCO
    public List<Coparticipacao> readCop() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT codRel, codCop, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, setorFuncionario, codPro, nomePro, valorProcedimento, localPro, medicoPro, dataPro, geradoPar FROM tbrealiza rel JOIN tbfuncionarios fun ON fun.codFuncionario = rel.tbcoparti_tbfuncionarios_codFuncionario JOIN tbprocedi pro ON pro.codPro = rel.tbprocedi_codPro JOIN tbcoparti cop ON cop.codCop = rel.tbcoparti_codCop");
            rs = stmt.executeQuery();
            while (rs.next()) {

                Coparticipacao co = new Coparticipacao();
                co.setCodCop(rs.getInt("codCop"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                co.setFr_codPro(rs.getInt("codPro"));
                co.setFr_nomePro(rs.getString("nomePro"));
                co.setFr_valorPro(rs.getDouble("valorProcedimento"));
                co.setLocalPro(rs.getString("localPro"));
                co.setMedicoPro(rs.getString("medicoPro"));
                co.setDataPro(rs.getDate("dataPro"));

                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    //LISTA A COPARTICIPAÇÃO PELO CÓDIGO
    public List<Coparticipacao> readCopForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codRel, codCop, codFuncionario, nomeFuncionario, codDependente, nomeDependente, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, setorFuncionario, codPro, nomePro, valorProcedimento, localPro, medicoPro, dataPro, geradoPar FROM tbrealiza rel JOIN tbfuncionarios fun ON fun.codFuncionario = rel.tbcoparti_tbfuncionarios_codFuncionario JOIN tbdependentes d ON d.codDependente = rel.tbcoparti_tbdependentes_codDependente JOIN tbprocedi pro ON pro.codPro = rel.tbprocedi_codPro JOIN tbcoparti cop ON cop.codCop = rel.tbcoparti_codCop WHERE codCop = ?");

            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();

            while (rs.next()) {

                Coparticipacao co = new Coparticipacao();
                co.setCod(rs.getInt("codRel"));
                co.setCodCop(rs.getInt("codCop"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_codBeneficiario(rs.getInt("codDependente"));
                co.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                co.setFr_codPro(rs.getInt("codPro"));
                co.setFr_nomePro(rs.getString("nomePro"));
                co.setFr_valorPro(rs.getDouble("valorProcedimento"));
                co.setLocalPro(rs.getString("localPro"));
                co.setMedicoPro(rs.getString("medicoPro"));
                co.setDataPro(rs.getDate("dataPro"));
                co.setGeradoPar(rs.getInt("geradoPar"));
                Coparticipacao.add(co);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;

    }

    public List<Coparticipacao> readCopForNfe(int titulo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT cop.codCop, fun.nomeFuncionario, de.nomeDependente, pro.nomePro, valorProcedimento, dataPro, par.codParcela, par.valorParcela, par.dataVencParcela FROM tbrealiza rea JOIN tbfuncionarios fun ON fun.codFuncionario = rea.tbcoparti_tbfuncionarios_codFuncionario JOIN tbdependentes de ON de.codDependente = rea.tbcoparti_tbdependentes_codDependente JOIN tbprocedi pro ON pro.codPro = rea.tbprocedi_codPro JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbparcela par ON par.tbcoparti_codCop = cop.codCop WHERE nTitulo = ?");
            stmt.setInt(1, titulo);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Coparticipacao co = new Coparticipacao();
                co.setCodCop(rs.getInt("codCop"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                co.setFr_nomePro(rs.getString("nomePro"));
                co.setFr_valorPro(rs.getDouble("valorProcedimento"));
                co.setDataPro(rs.getDate("dataPro"));
                co.setFr_parcelaPro(rs.getInt("codParcela"));
                co.setFr_valorPar(rs.getDouble("valorParcela"));
                co.setFr_dataVencPar(rs.getDate("dataVencParcela"));
                Coparticipacao.add(co);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;

    }

    public List<Coparticipacao> readCopForCodUser(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codRel, codCop, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, setorFuncionario, codPro, nomePro, valorProcedimento, localPro, medicoPro, dataPro, geradoPar FROM tbrealiza rel JOIN tbfuncionarios fun ON fun.codFuncionario = rel.tbcoparti_tbfuncionarios_codFuncionario JOIN tbprocedi pro ON pro.codPro = rel.tbprocedi_codPro JOIN tbcoparti cop ON cop.codCop = rel.tbcoparti_codCop WHERE fun.codFuncionario = ?");

            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();

            while (rs.next()) {

                Coparticipacao co = new Coparticipacao();
                co.setCod(rs.getInt("codRel"));
                co.setCodCop(rs.getInt("codCop"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                co.setFr_codPro(rs.getInt("codPro"));
                co.setFr_nomePro(rs.getString("nomePro"));
                co.setFr_valorPro(rs.getDouble("valorProcedimento"));
                co.setLocalPro(rs.getString("localPro"));
                co.setMedicoPro(rs.getString("medicoPro"));
                co.setDataPro(rs.getDate("dataPro"));
                co.setGeradoPar(rs.getInt("geradoPar"));
                Coparticipacao.add(co);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;

    }

    //ATUALIZA OS DADOS DA COPARTICIPAÇÃO
    public void update(Coparticipacao c) throws ClassNotFoundException, ParseException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcoparti SET "
                    + "fr_codFuncionario = ?, "
                    + "fr_nomeFuncionario = ?, "
                    + "fr_cpfFuncionario = ?, "
                    + "fr_cargoFuncionario = ?, "
                    + "fr_setorFuncionario = ?,"
                    + "fr_codPro = ?,"
                    + "fr_nomePro = ?,"
                    + "valorProcedimento = ?,"
                    + "fr_parcelaPro = ?,"
                    + "localPro = ?,"
                    + "medicoPro = ?,"
                    + "dataPro = ?,"
                    + "dataVencPro = ? "
                    + "WHERE codCop = ? ");

            stmt.setInt(1, c.getFr_codFuncionario());
            stmt.setString(2, c.getFr_nomeFuncionario());
            stmt.setString(3, c.getFr_cpfFuncionario());
            stmt.setString(4, c.getFr_cargoFuncionario());
            stmt.setString(5, c.getFr_setorFuncionario());
            stmt.setInt(6, c.getFr_codPro());
            stmt.setString(7, c.getFr_nomePro());
            stmt.setDouble(8, c.getFr_valorPro());
            stmt.setInt(9, c.getFr_parcelaPro());
            stmt.setString(10, c.getLocalPro());
            stmt.setString(11, c.getMedicoPro());
            stmt.setDate(12, c.getDataPro());
            stmt.setDate(13, c.getDataVencPro());
            stmt.setInt(14, c.getCodCop());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("editar coparticipação - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA PROCEDIMENTOS CADASTRADOS NA COPARTICIPAÇÃO
    public void delete(Coparticipacao c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbcoparti WHERE codCop =?");
            stmt.setInt(1, c.getCodCop());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar coparticipação - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA COPARTICIPAÇÃO
    public void deleteForCod(Coparticipacao c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbrealiza WHERE codRel = ? ");
            stmt.setInt(1, c.getCod());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar coparticipação");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Coparticipacao> readSumNfeImport(Integer desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT SUM(valorProcedimento) FROM tbrealiza WHERE nTitulo = ?");
            stmt.setInt(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Coparticipacao co = new Coparticipacao();

                co.setValorCop(rs.getDouble("SUM(valorProcedimento)"));

                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor procedimentos - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }

    public List<Coparticipacao> readCountCopForNf(Integer desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Coparticipacao> Coparticipacao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT COUNT(*) as resultado FROM tbrealiza WHERE nTitulo = ?");
            stmt.setInt(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Coparticipacao co = new Coparticipacao();

                co.setNumeroTotalCop(rs.getInt("resultado"));

                Coparticipacao.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("calcular valor procedimentos - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Coparticipacao;
    }
}
