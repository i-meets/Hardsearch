package hard.compra.dao;

import hard.rh.dao.CoparticipacaoDao;
import hard.config.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import hard.compra.model.Rating;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingDao {

    public void SalvarRating(Rating e) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbavalia ( cod, tbempresas_codEmp, condPgmt, condEntrega, qualPreco, qualidade, avaliaTotal, dataAvalia, statusAvalia) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
//            stmt.setInt(1, e.getId());
            stmt.setInt(1, e.getFk_codEmp());
            stmt.setString(2, e.getCondPgmt());
            stmt.setString(3, e.getCondEntrega());
            stmt.setString(4, e.getQualPreco());
            stmt.setString(5, e.getQualidade());
            stmt.setString(6, e.getAvaliaTotal());
            stmt.setDate(7, e.getDataRating());
            stmt.setInt(8, e.getStatusAvalia());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            String erro = ex.toString();
            if (erro.contains("foreign key constraint fails")) {
                JOptionPane.showMessageDialog(null, "Código do fornecedor código: " + e.getFk_codEmp() + " incorreto.\nAvaliação do fornecedor não foi salva!", "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
            } else if (erro.contains("cannot be null")) {
                JOptionPane.showMessageDialog(null, "Um o mais campos da linha do fornecedor codigo:  " + e.getFk_codEmp() + " está vazia.\nAvaliação do fornecedor não foi salva!", "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Erro não reconhecido, contate o administrador do sistema\nErro: " + ex, "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
            }

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Rating> readAvalia(Date dataAvalia, int statusAvalia) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Rating> Rating = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT cod, emp.codEmp, emp.nomeEmp, emp.mailEmp, condPgmt, condEntrega, qualPreco, qualidade, avaliaTotal, statusAvalia FROM tbavalia ava JOIN tbempresas emp ON emp.codEmp = ava.tbempresas_codEmp WHERE dataAvalia = ? AND statusAvalia = ?");
            stmt.setDate(1, dataAvalia);
            stmt.setInt(2, statusAvalia);
            rs = stmt.executeQuery();
            while (rs.next()) {

                Rating ra = new Rating();
                ra.setCod(rs.getInt("cod"));
                ra.setFk_codEmp(rs.getInt("codEmp"));
                ra.setFk_nomeEmp(rs.getString("nomeEmp"));
                ra.setFk_mailEmp(rs.getString("mailEmp"));
                ra.setCondPgmt(rs.getString("condPgmt"));
                ra.setCondEntrega(rs.getString("condEntrega"));
                ra.setQualPreco(rs.getString("qualPreco"));
                ra.setQualidade(rs.getString("qualidade"));
                ra.setAvaliaTotal(rs.getString("avaliaTotal"));
                ra.setStatusAvalia(rs.getInt("statusAvalia"));

                Rating.add(ra);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoparticipacaoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Rating;
    }

    public void updateStatus(Rating r) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbavalia SET  statusAvalia = ? WHERE cod = ?");
            stmt.setInt(1, r.getStatusAvalia());
            stmt.setInt(2, r.getCod());

            stmt.executeUpdate();
        } catch (SQLException error) {
            System.out.print("Erro ao atualizar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
