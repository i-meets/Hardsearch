package hard.engenharia.dao;

import hard.config.ConnectionColFire;
import hard.config.ConnectionFactory;
import hard.engenharia.model.Ferramenta;
import hard.home.view.FDErroOcorrido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FerramentaDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE JÁ EXISTE O CADASTRO
    public boolean checkCadFer(String codFerramenta) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM ferramenta WHERE cod_ferramenta = ?");
            stmt.setString(1, codFerramenta);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar cadastro da ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public void createFerramenta(Ferramenta f) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO ferramenta (cod_fer, cod_ferramenta, grupo) VALUES (?, ?, ?)");
            stmt.setInt(1, f.getCod_fer());
            stmt.setString(2, f.getCodFerramenta());
            stmt.setString(3, f.getGrupo());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cadastrar ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Ferramenta> readFerramenta(String cod_ferramenta) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ferramenta> Ferramenta = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM ferramenta WHERE cod_ferramenta LIKE ?");
            stmt.setString(1, cod_ferramenta + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ferramenta f = new Ferramenta();
                f.setCod_fer(rs.getInt("cod_fer"));
                f.setCodFerramenta(rs.getString("cod_ferramenta"));
                f.setGrupo(rs.getString("grupo"));

                Ferramenta.add(f);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ferramenta;
    }

    public List<Ferramenta> listaOcPorItem(String codFer) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionColFire.getConnection();
        PreparedStatement stmt = null;

        ResultSet rs = null;
        List<Ferramenta> Ferramenta = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT MAX(IC.NUMERO_OC) AS OC FROM ITENS_COMPRA IC WHERE (IC.CODIGO_ARTIGO = ?) ");
            stmt.setString(1, codFer);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ferramenta f = new Ferramenta();
                f.setNumeroOc(rs.getInt("OC"));
                Ferramenta.add(f);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar OC de última compra - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionColFire.closeConnection(con, stmt, rs);
        }
        return Ferramenta;

    }

    public List<Ferramenta> listaUltimaOcsColet(int numeroOc, String item) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionColFire.getConnection();
        PreparedStatement stmt = null;

        ResultSet rs = null;
        List<Ferramenta> Ferramenta = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT I.CODIGO_ARTIGO, I.NUMERO_OC, I.PRECO_OFERECIDO FROM ITENS_COMPRA I WHERE (I.NUMERO_OC = ?) AND (I.CODIGO_ARTIGO = ?)");
            stmt.setInt(1, numeroOc);
            stmt.setString(2, item);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ferramenta f = new Ferramenta();
                f.setValor_compra(rs.getDouble("PRECO_OFERECIDO"));
                Ferramenta.add(f);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar valor de compra da ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionColFire.closeConnection(con, stmt, rs);
        }
        return Ferramenta;

    }

    public List<Ferramenta> readFerramentaForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ferramenta> Ferramenta = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM ferramenta WHERE cod_ferramenta LIKE ? ");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ferramenta f = new Ferramenta();
                f.setCod_fer(rs.getInt("cod_fer"));
                f.setCodFerramenta(rs.getString("cod_ferramenta"));
                f.setGrupo(rs.getString("grupo"));

                Ferramenta.add(f);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ferramenta;
    }

    public void updateFerramenta(Ferramenta f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE ferramenta SET grupo = ? WHERE cod_fer = ?");
            stmt.setString(1, f.getGrupo());
            stmt.setInt(2, f.getCod_fer());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("alterar ferramenta - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteFerramenta(Ferramenta i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM ferramenta WHERE cod_fer =?");
            stmt.setInt(1, i.getCod_fer());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            if (ex.toString().contains("Cannot delete or update a parent row: a foreign key constraint fails")) {
                JOptionPane.showMessageDialog(null, "Você não pode excluír a ferramenta, pois está vinculada a uma ocorrência", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            } else {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("excluír ferramenta - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
