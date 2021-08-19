package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Folha;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolhaDao {

    FDErroOcorrido fdErroOcorrido;

    //checkCreate VERIFICA SE A FOLHA NÃO EXISTE
    public boolean checkCreate(String codFo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("Select * from tbfolha where codFolha = ?");
            stmt.setString(1, codFo);
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

    public boolean checkCreateFolhaForFuncioStatusOpen(int codFuncio) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("Select * from tbfolha where tbfuncionarios_codFuncionario = ? AND statusFolha = 1");
            stmt.setInt(1, codFuncio);
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

    public boolean checkGeraFolhaAndFuncioIsCreate(Date data) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nrFuncionario FROM tbfuncionarios f WHERE f.codFuncionario NOT IN (SELECT tbfuncionarios_codFuncionario FROM tbfolha WHERE dataFolha = ?) AND statusFuncionario = 0");
            stmt.setDate(1, data);
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

    public List<Folha> readFolhaForFuncioNGerada(int mes) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nomeFuncionario, nrFuncionario FROM tbfuncionarios f WHERE f.codFuncionario NOT IN (SELECT tbfuncionarios_codFuncionario FROM tbfolha WHERE MONTH(dataFolha) = ? AND statusFolha = 1) AND statusFuncionario = 0 ORDER BY nomeFuncionario");
            stmt.setInt(1, mes);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Folha fo = new Folha();
                fo.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                Folha.add(fo);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastros - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;
    }

    public boolean checkCreateFolha(String codFo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbrealizafolha WHERE tbfolha_codFolha = ?");
            stmt.setString(1, codFo);
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

    public List<Folha> readStatusFolha(Integer codFo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("Select * from tbfolha statusFolha where codFolha = ?");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Folha co = new Folha();
                co.setStatusFolha(rs.getInt("statusFolha"));
                Folha.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar status - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;
    }

    // LISTA O MAIOR CÓDIGO DA FOLHA CADASTRADA NO BANCO
    public List<Folha> readCodFolha() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codFolha) FROM tbfolha");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Folha co = new Folha();
                co.setCodFolha(rs.getInt("Max(codFolha)"));
                Folha.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;
    }

    public void createFolha(Folha c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbfolha(codFolha,tbfuncionarios_codFuncionario,dataFolha, statusFolha) VALUES(?,?,?, 1)");
            stmt.setInt(1, c.getCodFolha());
            stmt.setInt(2, c.getFr_codFuncionario());
            stmt.setDate(3, c.getDataFolha());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //INSERT DOS DADOS DA FOLHA NO BANCO
    public void realizaFolha(Folha c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbrealizafolha(tbfolha_codFolha, tbfolha_tbfuncionarios_codFuncionario, tbeventos_codEvento,tipoSalarioFuncio, conteudoFolha, tbparcela_codPar) VALUES(?,?,?,?,?,?)");

            stmt.setInt(1, c.getCodFolha());
            stmt.setInt(2, c.getFr_codFuncionario());
            stmt.setInt(3, c.getFr_codEvento());
            stmt.setString(4, c.getFr_tipoSalarioFuncionario());
            stmt.setDouble(5, c.getConteudoFolha());
            stmt.setInt(6, c.getFr_codParcela());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS AS FOLHAS CADASTRADAS NO BANCO
    public List<Folha> readFolha() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT codFolha, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, setorFuncionario, codEvento, descriEvento, tipoEvento, tipoProcessaEvento, dataFolha, statusFolha FROM tbrealizafolha rf JOIN tbfuncionarios fun ON fun.codFuncionario = rf.tbfolha_tbfuncionarios_codFuncionario JOIN tbeventos eve ON eve.codEvento = rf.tbEventos_codEvento JOIN tbfolha fol ON rf.tbFolha_codFolha");
            rs = stmt.executeQuery();
            while (rs.next()) {

                Folha co = new Folha();
                co.setCodFolha(rs.getInt("codFolha"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_codEvento(rs.getInt("codEvento"));

                Folha.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;
    }

    //LISTA TODOS AS FOLHAS CADASTRADAS NO BANCO
    public List<Folha> listFolhas() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT dataFolha, COUNT(*) as numeroFolhas, statusFolha FROM `tbfolha` GROUP BY dataFolha");
            rs = stmt.executeQuery();
            while (rs.next()) {

                Folha co = new Folha();
                co.setDataFolha(rs.getDate("dataFolha"));
                co.setNumFichas(rs.getInt("numeroFolhas"));
                co.setStatusFolha(rs.getInt("statusFolha"));

                Folha.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;
    }

    //LISTA A FOLHA PELO CÓDIGO
    public List<Folha> readFolhaForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codRelFolha, codFolha, codFuncionario, nrFuncionario, nomeFuncionario, tipoSalarioFuncionario, codEvento, descriEvento, tipoEvento, tipoProcessaEvento, conteudoFolha, tbparcela_codPar, dataFolha FROM tbrealizafolha reaf JOIN tbfuncionarios fun ON fun.codFuncionario = reaf.tbFolha_tbfuncionarios_codFuncionario JOIN tbeventos eve ON eve.codEvento = reaf.tbEventos_codEvento JOIN tbfolha fo ON fo.codFolha = reaf.tbFolha_codFolha WHERE tbFolha_codFolha = ?");

            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();

            while (rs.next()) {

                Folha fo = new Folha();
                fo.setCodRelFolha(rs.getInt("codRelFolha"));
                fo.setCodFolha(rs.getInt("codFolha"));
                fo.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fo.setFr_nrFuncionario(rs.getInt("nrFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fo.setFr_tipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fo.setFr_codEvento(rs.getInt("codEvento"));
                fo.setFr_descriEvento(rs.getString("descriEvento"));
                fo.setFr_tipoEvento(rs.getString("tipoEvento"));
                fo.setFr_tipoProcessaEvento(rs.getString("tipoProcessaEvento"));
                fo.setConteudoFolha(rs.getDouble("conteudoFolha"));
                fo.setFr_codParcela(rs.getInt("tbparcela_codPar"));
                fo.setDataFolha(rs.getDate("dataFolha"));

                Folha.add(fo);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("lisar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;

    }

    public List<Folha> readFolhaForGera(Integer status, String data) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFolha, nrFuncionario, nomeFuncionario, tipoSalarioFuncionario, codEvento, descriEvento, tipoEvento, tipoProcessaEvento, conteudoFolha FROM tbrealizafolha reaf JOIN tbfuncionarios fun ON fun.codFuncionario = reaf.tbFolha_tbfuncionarios_codFuncionario JOIN tbeventos eve ON eve.codEvento = reaf.tbEventos_codEvento JOIN tbfolha fo ON fo.codFolha = reaf.tbFolha_codFolha WHERE statusFolha = ? AND dataFolha = ? ORDER BY codFolha");
            stmt.setInt(1, status);
            stmt.setString(2, data);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Folha fo = new Folha();
                fo.setCodFolha(rs.getInt("codFolha"));
                fo.setFr_nrFuncionario(rs.getInt("nrFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fo.setFr_tipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fo.setFr_codEvento(rs.getInt("codEvento"));
                fo.setFr_descriEvento(rs.getString("descriEvento"));
                fo.setFr_tipoEvento(rs.getString("tipoEvento"));
                fo.setFr_tipoProcessaEvento(rs.getString("tipoProcessaEvento"));
                fo.setConteudoFolha(rs.getDouble("conteudoFolha"));

                Folha.add(fo);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;

    }

    public void updateGeradoFolha(Folha f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbfolha SET statusFolha = ? WHERE dataFolha = ?");
            stmt.setInt(1, f.getStatusFolha());
            stmt.setDate(2, f.getDataFolha());
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

    public List<Folha> readFolhaForGera(Integer status, Integer ano, Integer mes) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Folha> Folha = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFolha, nrFuncionario, nomeFuncionario, tipoSalarioFuncionario, codEvento, descriEvento, tipoEvento, tipoProcessaEvento, conteudoFolha FROM tbrealizafolha reaf JOIN tbfuncionarios fun ON fun.codFuncionario = reaf.tbFolha_tbfuncionarios_codFuncionario JOIN tbeventos eve ON eve.codEvento = reaf.tbEventos_codEvento JOIN tbfolha fo ON fo.codFolha = reaf.tbFolha_codFolha WHERE statusFolha = ? AND YEAR(dataFolha) = ? AND MONTH(dataFolha) = ? ORDER BY codFuncionario");

            stmt.setInt(1, status);
            stmt.setInt(2, ano);
            stmt.setInt(3, mes);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Folha fo = new Folha();
                fo.setCodFolha(rs.getInt("codFolha"));
                fo.setFr_nrFuncionario(rs.getInt("nrFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fo.setFr_tipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fo.setFr_codEvento(rs.getInt("codEvento"));
                fo.setFr_descriEvento(rs.getString("descriEvento"));
                fo.setFr_tipoEvento(rs.getString("tipoEvento"));
                fo.setFr_tipoProcessaEvento(rs.getString("tipoProcessaEvento"));
                fo.setConteudoFolha(rs.getDouble("conteudoFolha"));

                Folha.add(fo);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Folha;

    }

    //DELETA PROCEDIMENTOsa CADASTRADOS NA FOLHA
    public void delete(Folha c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbfolha WHERE codFolha = ?");
            stmt.setInt(1, c.getCodFolha());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA FOLHA
    public void deleteEvento(Folha p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbrealizafolha WHERE codRelFolha = ?");
            stmt.setInt(1, p.getCodRelFolha());
            stmt.executeUpdate();
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
