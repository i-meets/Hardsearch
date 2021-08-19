package hard.engenharia.dao;

import hard.config.ConnectionFactory;
import hard.engenharia.model.Solicita;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SolicitaDao {

    public void createSolicita(Solicita i) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO solicita_alteracao (cod, alteracao_rev_cod, titulo, descricao, usuario_criador, data_criacao, status) VALUES (?, ?, ?, ?, ?, DATE(NOW()), ?)");
            stmt.setInt(1, i.getCod());
            stmt.setInt(2, i.getFr_codAlteracao());
            stmt.setString(3, i.getTitulo());
            stmt.setString(4, i.getDescricao());
            stmt.setString(5, i.getUsuarioCriador());
            stmt.setInt(6, i.getStatus());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Solicita> readMaxCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Solicita> Solicita = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(cod) FROM alteracao_rev");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Solicita co = new Solicita();
                co.setCod(rs.getInt("Max(cod)"));
                Solicita.add(co);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Solicita;
    }

    public List<Solicita> readSolicitaForUser(String usuario_criador, int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Solicita> Solicita = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT s.cod as cod_solicita, ar.cod as cod_altera, i.cod_item, s.titulo, s.descricao, s.status FROM solicita_alteracao s JOIN alteracao_rev ar ON ar.cod = s.alteracao_rev_cod JOIN item i ON i.cod = ar.item_cod WHERE s.usuario_criador = ?;");
            stmt.setString(1, usuario_criador);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Solicita i = new Solicita();
                i.setCod(rs.getInt("cod_solicita"));
                i.setFr_codAlteracao(rs.getInt("cod_altera"));
                i.setFr_codItem(rs.getString("cod_item"));
                i.setTitulo(rs.getString("titulo"));
                i.setDescricao(rs.getString("descricao"));
                i.setStatus(rs.getInt("status"));

                Solicita.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Solicita;
    }

    public List<Solicita> readSolicita(int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Solicita> Solicita = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT s.cod as cod_solicita, ar.cod as cod_altera, i.cod_item, s.titulo, s.descricao, s.status FROM solicita_alteracao s JOIN alteracao_rev ar ON ar.cod = s.alteracao_rev_cod JOIN item i ON i.cod = ar.item_cod WHERE s.status = ?;");
            stmt.setInt(1, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Solicita i = new Solicita();
                i.setCod(rs.getInt("cod_solicita"));
                i.setFr_codAlteracao(rs.getInt("cod_altera"));
                i.setFr_codItem(rs.getString("cod_item"));
                i.setTitulo(rs.getString("titulo"));
                i.setDescricao(rs.getString("descricao"));
                i.setStatus(rs.getInt("status"));

                Solicita.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Solicita;
    }
}
