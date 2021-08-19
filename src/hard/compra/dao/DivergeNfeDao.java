package hard.compra.dao;

import hard.compra.model.DivergeNFE;
import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DivergeNfeDao {

    FDErroOcorrido fdErroOcorrido;

    public boolean checkNFe(int nfe, String nomeForn) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_entrada WHERE num_nfe = ? AND nomeForne = ?");
            stmt.setInt(1, nfe);
            stmt.setString(2, nomeForn);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkDivergeForNFe(int nfe) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_diverge WHERE fk_nfe_entrada_cod_nfe = ?");
            stmt.setInt(1, nfe);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkDvForStatusAndNfe(int nfe, int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_diverge WHERE fk_nfe_entrada_cod_nfe = ? AND status = ?");
            stmt.setInt(1, nfe);
            stmt.setInt(2, status);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar divergência D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<DivergeNFE> readMaiorCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(cod_nfe) FROM nfe_entrada");
            rs = stmt.executeQuery();

            while (rs.next()) {
                DivergeNFE pa = new DivergeNFE();
                pa.setCod_nfe(rs.getInt("Max(cod_nfe)") + 1);
                DivergeNFE.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public void saveNFE(DivergeNFE d) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO nfe_entrada (cod_nfe, num_nfe, nomeForne, link_nfe, dataEmi, dataInclu, status) VALUES (?, ?, ?, ?,?,DATE(NOW()) , ?)");

            stmt.setInt(1, d.getCod_nfe());
            stmt.setInt(2, d.getNum_nfe());
            stmt.setString(3, d.getNomeForne());
            stmt.setString(4, d.getLink_nfe());
            stmt.setDate(5, d.getDataEmiNfe());
            stmt.setInt(6, d.getStatus_nfe());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<DivergeNFE> readDivergeForSaveNFe(int cod_nfe) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_diverge WHERE fk_nfe_entrada_cod_nfe = ? ");
            stmt.setInt(1, cod_nfe);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setFr_codDv(rs.getInt("cod_dv"));
                d.setFr_tipoDiverge(rs.getString("tipoDiverge"));
                d.setFr_descricao(rs.getString("descricao"));
                d.setFr_dataDv(rs.getDate("data"));
                d.setFr_hora(rs.getString("hora"));
                d.setStatus_dv(rs.getInt("status"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NF-e código D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public List<DivergeNFE> readDvForSaveNFe(int cod_nfe, int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_diverge WHERE fk_nfe_entrada_cod_nfe = ? AND status = ?");
            stmt.setInt(1, cod_nfe);
            stmt.setInt(2, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setFr_codDv(rs.getInt("cod_dv"));
                d.setFr_tipoDiverge(rs.getString("tipoDiverge"));
                d.setFr_descricao(rs.getString("descricao"));
                d.setFr_dataDv(rs.getDate("data"));
                d.setFr_hora(rs.getString("hora"));
                d.setStatus_dv(rs.getInt("status"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar divergência D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public List<DivergeNFE> readDvFrCon(int cod_nfe) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_diverge WHERE fk_nfe_entrada_cod_nfe = ?");
            stmt.setInt(1, cod_nfe);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setFr_codDv(rs.getInt("cod_dv"));
                d.setFr_tipoDiverge(rs.getString("tipoDiverge"));
                d.setFr_descricao(rs.getString("descricao"));
                d.setFr_dataDv(rs.getDate("data"));
                d.setFr_hora(rs.getString("hora"));
                d.setStatus_dv(rs.getInt("status"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar divergência D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    //BUSCA NFE PELO STATUS
    public List<DivergeNFE> readNFe(String num_nfe, String forne, int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_entrada WHERE num_nfe LIKE ? AND nomeForne LIKE ? AND status = ?");
            stmt.setString(1, "%" + num_nfe + "%");
            stmt.setString(2, "%" + forne + "%");
            stmt.setInt(3, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setCod_nfe(rs.getInt("cod_nfe"));
                d.setNum_nfe(rs.getInt("num_nfe"));
                d.setNomeForne(rs.getString("nomeForne"));
                d.setStatus_nfe(rs.getInt("status"));
                d.setObs_compra(rs.getString("obs_compra"));
                d.setDataEmiNfe(rs.getDate("dataEmi"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NFs D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public List<DivergeNFE> readTNFe(String num_nfe, String forne) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_entrada WHERE num_nfe LIKE ? AND nomeForne LIKE ?");
            stmt.setString(1, "%" + num_nfe + "%");
            stmt.setString(2, "%" + forne + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setCod_nfe(rs.getInt("cod_nfe"));
                d.setNum_nfe(rs.getInt("num_nfe"));
                d.setNomeForne(rs.getString("nomeForne"));
                d.setStatus_nfe(rs.getInt("status"));
                d.setObs_compra(rs.getString("obs_compra"));
                d.setDataEmiNfe(rs.getDate("dataEmi"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NFs D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public void saveDivergeNFE(DivergeNFE d) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO nfe_diverge (cod_dv, fk_nfe_entrada_cod_nfe, tipoDiverge, descricao, data, hora, dataAjuste, status) VALUES (?, ?, ?, ?,DATE(NOW()),CURRENT_TIME(),'2000-01-01', ?)");

            stmt.setInt(1, d.getFr_codDv());
            stmt.setInt(2, d.getCod_nfe());
            stmt.setString(3, d.getFr_tipoDiverge());
            stmt.setString(4, d.getFr_descricao());
            stmt.setInt(5, d.getStatus_dv());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("lançar divergência na NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<DivergeNFE> readNFeForDesc(int cod_nfe) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DivergeNFE> DivergeNFE = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM nfe_entrada WHERE cod_nfe = ?");
            stmt.setInt(1, cod_nfe);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DivergeNFE d = new DivergeNFE();
                d.setCod_nfe(rs.getInt("cod_nfe"));
                d.setNum_nfe(rs.getInt("num_nfe"));
                d.setNomeForne(rs.getString("nomeForne"));
                d.setLink_nfe(rs.getString("link_nfe"));
                d.setStatus_nfe(rs.getInt("status"));

                DivergeNFE.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return DivergeNFE;
    }

    public void updateStatusDvForNf(DivergeNFE d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE nfe_diverge SET status = ?, dataAjuste = DATE(NOW()) WHERE cod_dv = ? AND fk_nfe_entrada_cod_nfe = ?");
            stmt.setInt(1, d.getStatus_dv());
            stmt.setInt(2, d.getFr_codDv());
            stmt.setInt(3, d.getCod_nfe());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Linha da NF-e ajustada", "Atenção", JOptionPane.WARNING_MESSAGE);

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("lançar ajuste de NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateStatusNf(DivergeNFE d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE nfe_entrada SET status = ? WHERE cod_nfe = ?");
            stmt.setInt(1, d.getStatus_nfe());
            stmt.setInt(2, d.getCod_nfe());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("lançar ajuste de NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateObservaCompra(DivergeNFE d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE nfe_entrada SET obs_compra = ? WHERE cod_nfe = ?");
            stmt.setString(1, d.getObs_compra());
            stmt.setInt(2, d.getCod_nfe());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "NF-e ajustada", "Atenção", JOptionPane.WARNING_MESSAGE);

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("lançar ajuste de NF-e D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
//

    public void deleteNfe(DivergeNFE nfe) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM nfe_entrada WHERE cod_nfe = ? AND num_nfe = ? AND nomeForne = ?");
            stmt.setInt(1, nfe.getCod_nfe());
            stmt.setInt(2, nfe.getNum_nfe());
            stmt.setString(3, nfe.getNomeForne());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            String validaErro = ex.toString();
            if (validaErro.contains("a foreign key constraint fails")) {
                fdErroOcorrido = new FDErroOcorrido(null, true);

                FDErroOcorrido.LbTipoEvento.setText("AÇÃO NÃO PERMITIDA");
                FDErroOcorrido.LbMensagem.setText("Não é possível deletar NF-e que possue divergência lançada D48");
                fdErroOcorrido.LbInformaErro.setText("deletar NF-e");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            } else {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar NF-e");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteDivergeNfe(DivergeNFE dv) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM nfe_diverge WHERE cod_dv = ? AND fk_nfe_entrada_cod_nfe = ?");
            stmt.setInt(1, dv.getFr_codDv());
            stmt.setInt(2, dv.getCod_nfe());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar divergência D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
