package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.TesteCovid;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TesteCovidDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE JÁ EXISTE O CADASTRO DO FUNCIONÁRIO PARA A EMPRESA INFORMADA
    public boolean checkCadContra(String fr_codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbtestcovid WHERE tbfuncionarios_codFuncionario = ? AND resultadoTeste = 0 ");
            stmt.setString(1, fr_codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //VALIDA O CÓDIGO DO FUNCIONÁRIO (VERIFICA SE O CADASTRO EXISTE NO BANCO)
    public boolean checkFuncionario(String fr_codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbtestcovid WHERE tbfuncionario_codFuncionario = ?");
            stmt.setString(1, fr_codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código do funcionário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //CADASTRA INTEGRAÇÃO
    public void createTesteCovid(TesteCovid c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO tbtestcovid (codTeste, tbfuncionarios_codFuncionario, dataTeste, resultadoTeste)" + "VALUES (?,?,?,?)");

            stmt.setInt(1, c.getCodTeste());
            stmt.setInt(2, c.getFr_codFuncionario());
            stmt.setDate(3, c.getDataTeste());
            stmt.setInt(4, c.getResultadoTeste());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar teste - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //BUSCA AS INTEGRAÇÕES CADASTRADAS, E TRAZ A INFORMAÇÃO DE DIAS EM ATRASO PELO DATEDIFF
    public List<TesteCovid> readTesteCovid(String rTeste, String Funcionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TesteCovid> TesteCovid = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codTeste, fun.codFuncionario, fun.nomeFuncionario, dataTeste, resultadoTeste FROM tbtestcovid con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE resultadoTeste LIKE ? AND fun.nomeFuncionario LIKE ? ORDER BY dataTeste");
            stmt.setString(1, "%" + rTeste + "%");
            stmt.setString(2, "%" + Funcionario + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                TesteCovid te = new TesteCovid();
                te.setCodTeste(rs.getInt("codTeste"));
                te.setFr_codFuncionario(rs.getInt("codFuncionario"));
                te.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                te.setDataTeste(rs.getDate("dataTeste"));
                te.setResultadoTeste(rs.getInt("resultadoTeste"));

                TesteCovid.add(te);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar testes - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return TesteCovid;
    }

    public List<TesteCovid> readMaiorCodTeste() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TesteCovid> TesteCovid = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codTeste) FROM tbtestcovid");
            rs = stmt.executeQuery();

            while (rs.next()) {
                TesteCovid pa = new TesteCovid();
                pa.setCodTeste(rs.getInt("Max(codTeste)") + 1);
                TesteCovid.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return TesteCovid;
    }

    public void updateStatusTesteCovid(TesteCovid i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbtestcovid SET dataTeste = ?, resultadoTeste = ? WHERE codTeste = ?");
            stmt.setDate(1, i.getDataTeste());
            stmt.setInt(2, i.getResultadoTeste());
            stmt.setInt(3, i.getCodTeste());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<TesteCovid> readTesteForCod(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TesteCovid> TesteCovid = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codTeste, fun.codFuncionario, fun.nomeFuncionario, dataTeste, resultadoTeste FROM tbtestcovid con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE codTeste = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();

            while (rs.next()) {

                TesteCovid te = new TesteCovid();
                te.setCodTeste(rs.getInt("codTeste"));
                te.setFr_codFuncionario(rs.getInt("codFuncionario"));
                te.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                te.setDataTeste(rs.getDate("dataTeste"));

                te.setResultadoTeste(rs.getInt("resultadoTeste"));

                TesteCovid.add(te);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar teste - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return TesteCovid;

    }

    //DELETA A INTEGRAÇÃO
    public void deleteTesteCovid(TesteCovid i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbtestcovid WHERE codTeste =?");
            stmt.setInt(1, i.getCodTeste());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
