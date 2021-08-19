package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Evento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EventoDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE O CÓDIGO DO EVENTO EXISTE NO BANCO
    public boolean checkCod(String codEve) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbeventos where codEvento = ?");
            stmt.setString(1, codEve);
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

    public List<Evento> readMaiorCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> Evento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codEvento) FROM tbeventos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Evento ev = new Evento();
                ev.setCodEvento(rs.getInt("Max(codEvento)") + 1);
                Evento.add(ev);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código  - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Evento;
    }

    public List<Evento> readCodEve() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> Evento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codEvento) FROM tbeventos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Evento co = new Evento();
                co.setCodEvento(rs.getInt("Max(codEvento)"));
                Evento.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar código  - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Evento;
    }

    //INSERT DOS DADOS DO EVENTO NO BANCO
    public void createEve(Evento p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbeventos(codEvento,descriEvento, tipoEvento, tipoProcessaEvento)" + "VALUES(?,?,?,?)");
            stmt.setInt(1, p.getCodEvento());
            stmt.setString(2, p.getDescriEvento());
            stmt.setString(3, p.getTipoEvento());
            stmt.setString(4, p.getTipoProcessaEvento());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "salvo com sucesso");

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar evento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS OS EVENTO CADASTRADOS NO BANCO
    public List<Evento> readEve() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> Evento = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbeventos");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Evento p = new Evento();
                p.setCodEvento(rs.getInt("codEvento"));
                p.setDescriEvento(rs.getString("descriEvento"));
                p.setTipoEvento(rs.getString("tipoEvento"));
                p.setTipoProcessaEvento(rs.getString("tipoProcessaEvento"));

                Evento.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar eventos - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Evento;
    }

    //LISTA OS EVENTOS PELO SEU NOME
    public List<Evento> readEveForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> Evento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbeventos WHERE descriEvento LIKE ? ORDER BY descriEvento");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Evento p = new Evento();

                p.setCodEvento(rs.getInt("codEvento"));
                p.setDescriEvento(rs.getString("descriEvento"));
                p.setTipoEvento(rs.getString("tipoEvento"));
                p.setTipoProcessaEvento(rs.getString("tipoProcessaEvento"));

                Evento.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar eventos - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Evento;

    }

    //LISTA OS EVENTOS PELO CÓDIGO
    public List<Evento> readEveForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Evento> Evento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbeventos WHERE codEvento LIKE ?");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();
            Evento p = new Evento();

            while (rs.next()) {

                p.setCodEvento(rs.getInt("codEvento"));
                p.setDescriEvento(rs.getString("descriEvento"));
                p.setTipoEvento(rs.getString("tipoEvento"));
                p.setTipoProcessaEvento(rs.getString("tipoProcessaEvento"));
                Evento.add(p);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar evento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Evento;

    }

    //ATUALIZA OS DADOS DO EVENTO
    public void update(Evento p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbeventos SET descriEvento = ?, tipoEvento = ?, tipoProcessaEvento = ? WHERE codEvento = ?");

            stmt.setString(1, p.getDescriEvento());
            stmt.setString(2, p.getTipoEvento());
            stmt.setString(3, p.getTipoProcessaEvento());
            stmt.setInt(4, p.getCodEvento());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("editar evento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA O EVENTO
    public void delete(Evento p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbeventos WHERE codEvento=?");
            stmt.setInt(1, p.getCodEvento());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar evento");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
