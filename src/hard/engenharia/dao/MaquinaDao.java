package hard.engenharia.dao;

import hard.config.ConnectionFactory;
import hard.engenharia.model.Maquina;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MaquinaDao {

    //VERIFICA SE JÁ EXISTE O CADASTRO
    public boolean checkCadMaq(String codMaquina) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM maquina WHERE codigo_maquina = ?");
            stmt.setString(1, codMaquina);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public void createMaquina(Maquina m) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO maquina (cod_maq, codigo_maquina, descricao) VALUES (?, ?, ?);");
            stmt.setInt(1, m.getCod_maq());
            stmt.setString(2, m.getCodMaquina());
            stmt.setString(3, m.getDescricao_maq());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Maquina> readMaquina(String codMaquina) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Maquina> Maquina = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM maquina WHERE codigo_maquina LIKE ?");
            stmt.setString(1, codMaquina + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Maquina m = new Maquina();
                m.setCod_maq(rs.getInt("cod_maq"));
                m.setCodMaquina(rs.getString("codigo_maquina"));
                m.setDescricao_maq(rs.getString("descricao"));

                Maquina.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Maquina;
    }

    public List<Maquina> readMaquinaForDesc(String codMaquina) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Maquina> Maquina = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM maquina WHERE codigo_maquina LIKE ?");
            stmt.setString(1, codMaquina + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Maquina m = new Maquina();
                m.setCod_maq(rs.getInt("cod_maq"));
                m.setCodMaquina(rs.getString("codigo_maquina"));
                m.setDescricao_maq(rs.getString("descricao"));

                Maquina.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Maquina;
    }

    public void updateMaquina(Maquina f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE maquina SET descricao = ? WHERE cod_maq = ?");
            stmt.setString(1, f.getDescricao_maq());
            stmt.setInt(2, f.getCod_maq());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void deleteMaquina(Maquina m) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM maquina WHERE cod_maq =?");
            stmt.setInt(1, m.getCod_maq());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            if (ex.toString().contains("Cannot delete or update a parent row: a foreign key constraint fails")) {
                JOptionPane.showMessageDialog(null, "Você não pode excluír a máquina, pois está vinculada a uma ocorrência", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                System.out.println("Erro ao excluir" + ex + "\n" + stmt);
                JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
