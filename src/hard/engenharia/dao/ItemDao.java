package hard.engenharia.dao;

import hard.config.ConnectionFactory;
import hard.engenharia.model.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ItemDao {

    public void createItem(Item i) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO item (cod, cod_item, tbempresas_codEmp, descricao_item, tipo_cont_rev, revisao_interna, data_rev_interna, revisao_cliente, data_rev_cliente, responsavel, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, i.getCod());
            stmt.setString(2, i.getCodItem());
            stmt.setInt(3, i.getFr_codEmpresa());
            stmt.setString(4, i.getDescricao());
            stmt.setInt(5, i.getTipoContRev());
            stmt.setInt(6, i.getRevisaoInterna());
            stmt.setDate(7, i.getData_rev_interna());
            stmt.setString(8, i.getRevisaoCliente());
            stmt.setDate(9, i.getData_rev_cliente());
            stmt.setString(10, i.getFr_usuario());
            stmt.setInt(11, i.getStatus());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Item> readItens(int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> Item = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT cod, cod_item, descricao_item FROM item WHERE status = ?");
            stmt.setInt(1, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item i = new Item();
                i.setCod(rs.getInt("cod"));
                i.setCodItem(rs.getString("cod_item"));
                i.setDescricao(rs.getString("descricao_item"));

                Item.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Item;
    }

    public List<Item> readItemForCod(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> Item = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM item WHERE cod = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item i = new Item();
                i.setCod(rs.getInt("cod"));
                i.setCodItem(rs.getString("cod_item"));
                i.setFr_codEmpresa(rs.getInt("tbempresas_codEmp"));
                i.setDescricao(rs.getString("descricao_item"));
                i.setTipoContRev(rs.getInt("tipo_cont_rev"));
                i.setRevisaoInterna(rs.getInt("revisao_interna"));
                i.setData_rev_interna(rs.getDate("data_rev_interna"));
                i.setRevisaoCliente(rs.getString("revisao_cliente"));
                i.setData_rev_cliente(rs.getDate("data_rev_cliente"));

                i.setStatus(rs.getInt("status"));

                Item.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Item;
    }

    public List<Item> readItemForDescFDBusca(String codItem) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> Item = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT cod, cod_item, descricao_item FROM item WHERE cod_item LIKE ? AND status = 1");
            stmt.setString(1, codItem + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item i = new Item();
                i.setCod(rs.getInt("cod"));
                i.setCodItem(rs.getString("cod_item"));
                i.setDescricao(rs.getString("descricao_item"));
                Item.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Item;
    }

    public void updateItem(Item p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE item SET descricao_item = ?, tipo_cont_rev = ?, status = ? WHERE cod = ?");

            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getTipoContRev());
            stmt.setInt(3, p.getStatus());
            stmt.setInt(4, p.getCod());
            System.out.println("");
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteItem(Item i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM item WHERE cod = ?");
            stmt.setInt(1, i.getCod());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir" + ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!" + "\n\nCÓDIGO ERRO:\n" + ex + "\nContate o administrador" + "\n", "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
