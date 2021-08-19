package hard.home.dao;

import hard.config.ConnectionFactory;
import hard.rh.dao.CoparticipacaoDao;
import hard.home.model.ContVersao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ContVersaoDao {

    public void newVersao(ContVersao ct) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbversao(idVersao,tbtelas_codTela, novaVersao,dataLibVersao, observaVersao)" + "VALUES(?,?,?,?,?)");
            stmt.setInt(1, ct.getIdVersao());
            stmt.setString(2, ct.getFk_codTela());
            stmt.setString(3, ct.getNovaVersao());
            stmt.setDate(4, ct.getDataLibVersao());
            stmt.setString(5, ct.getObservaVersao());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void createTela(ContVersao te) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbtelas(codTela,versaoAtualTela, dataVersao)" + "VALUES(?,?,NOW())");
            stmt.setString(1, te.getCodTela());
            stmt.setString(2, te.getVersaoAtualTela());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }

    }

    public void updateVersaoTela(ContVersao ct) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbtelas SET versaoAtualTela = ?, dataVersao = ? WHERE codTela = ?");
            stmt.setString(1, ct.getVersaoAtualTela());
            stmt.setDate(2, ct.getDataVersao());
            stmt.setString(3, ct.getCodTela());

            stmt.executeUpdate();
        } catch (SQLException error) {
            System.out.print("Erro ao atualizar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ContVersao> listTela(String CodTela) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContVersao> ContVersao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbtelas where codTela = ?");
            stmt.setString(1, CodTela);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContVersao ct = new ContVersao();
                ct.setCodTela(rs.getString("codTela"));
                ct.setVersaoAtualTela(rs.getString("versaoAtualTela"));
                ct.setDataVersao(rs.getDate("dataVersao"));

                ContVersao.add(ct);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ContVersaoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContVersao;
    }

    public List<ContVersao> listTelas() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContVersao> ContVersao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbtelas");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContVersao ct = new ContVersao();
                ct.setCodTela(rs.getString("codTela"));
                ct.setVersaoAtualTela(rs.getString("versaoAtualTela"));
                ct.setDataVersao(rs.getDate("dataVersao"));

                ContVersao.add(ct);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ContVersaoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContVersao;
    }

    public List<ContVersao> listTelasForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContVersao> ContVersao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbtelas WHERE codTela LIKE ?");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContVersao ct = new ContVersao();
                ct.setCodTela(rs.getString("codTela"));
                ct.setVersaoAtualTela(rs.getString("versaoAtualTela"));
                ct.setDataVersao(rs.getDate("dataVersao"));

                ContVersao.add(ct);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao buscar funcionario" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContVersao;

    }

    public List<ContVersao> listVersao() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContVersao> ContVersao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbversao ORDER BY dataLibVersao DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContVersao ct = new ContVersao();
                ct.setIdVersao(rs.getInt("idVersao"));
                ct.setFk_codTela(rs.getString("tbtelas_codTela"));
                ct.setNovaVersao(rs.getString("novaVersao"));
                ct.setDataLibVersao(rs.getDate("dataLibVersao"));
                ct.setObservaVersao(rs.getString("observaVersao"));

                ContVersao.add(ct);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ContVersaoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContVersao;
    }

    public List<ContVersao> readUltIdVersao() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContVersao> ContVersao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(idVersao) FROM tbversao");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContVersao co = new ContVersao();
                co.setIdVersao(rs.getInt("Max(idVersao)"));
                ContVersao.add(co);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ContVersaoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContVersao;
    }

    public boolean checkVersao(String codTela, String versaoAtual) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("Select * from tbtelas where codTela = ? and versaoAtualTela = ?");
            stmt.setString(1, codTela);
            stmt.setString(2, versaoAtual);
            rs = stmt.executeQuery();
            check = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CoparticipacaoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }
}
