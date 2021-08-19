package hard.engenharia.dao;

import hard.config.ConnectionFactory;
import hard.engenharia.model.Ocorrencia;
import hard.home.view.FDErroOcorrido;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

public class OcorrenciaDao {

    FDErroOcorrido fdErroOcorrido;

    public List<Ocorrencia> readMaiorCodBo() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ocorrencia> Ocorrencia = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(cod_bo) FROM ocorrencia_producao");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCod_bo(rs.getInt("Max(cod_bo)") + 1);
                Ocorrencia.add(o);
            }
        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ocorrencia;
    }

    public void createOcorrencia(Ocorrencia o) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            if (o.getData() == null) {
                stmt = con.prepareStatement("INSERT INTO ocorrencia_producao (cod_bo, ferramenta_cod_fer, valor_ultima_compra, maquina_cod_maq, tbfuncionarios_codFuncionario, motivo_quebra, item_cod, data) VALUES (?, ?, ?, ?, ?, ?, ?, DATE(NOW()));");

            } else {
                stmt = con.prepareStatement("INSERT INTO ocorrencia_producao (cod_bo, ferramenta_cod_fer, valor_ultima_compra, maquina_cod_maq, tbfuncionarios_codFuncionario, motivo_quebra, item_cod, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                stmt.setDate(8, o.getData());
            }

            stmt.setInt(1, o.getCod_bo());
            stmt.setInt(2, o.getFr_codFer());
            stmt.setDouble(3, o.getValorUltCompra());
            stmt.setInt(4, o.getFr_codMaq());
            stmt.setInt(5, o.getFr_codFun());
            stmt.setString(6, o.getMotivoQuebra());
            stmt.setInt(7, o.getFr_codItem());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar corrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public List<Ocorrencia> readOcorrencia() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ocorrencia> Ocorrencia = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT  o.cod_bo, f.cod_fer, f.cod_ferramenta, o.valor_ultima_compra, m.cod_maq, m.codigo_maquina, fun.codFuncionario, fun.nomeFuncionario, o.motivo_quebra, i.cod, i.cod_item, o.data FROM ocorrencia_producao o \n"
                    + "JOIN maquina m ON m.cod_maq = o.maquina_cod_maq \n"
                    + "JOIN tbfuncionarios fun ON fun.codFuncionario = o.tbfuncionarios_codFuncionario \n"
                    + "JOIN item i ON i.cod = o.item_cod \n"
                    + "JOIN ferramenta f ON f.cod_fer = o.ferramenta_cod_fer ORDER BY data;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCod_bo(rs.getInt("cod_bo"));
                o.setFr_codFer(rs.getInt("cod_fer"));
                o.setFr_codFerramenta(rs.getString("cod_ferramenta"));
                o.setValorUltCompra(rs.getDouble("valor_ultima_compra"));
                o.setFr_codMaq(rs.getInt("cod_maq"));
                o.setFr_codMaquina(rs.getString("codigo_maquina"));
                o.setFr_codFun(rs.getInt("codFuncionario"));
                o.setFr_nomeFun(rs.getString("nomeFuncionario"));
                o.setMotivoQuebra(rs.getString("motivo_quebra"));
                o.setFr_codItem(rs.getInt("cod"));
                o.setFr_codigoItem(rs.getString("cod_item"));
                o.setData(rs.getDate("data"));
                Ocorrencia.add(o);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ocorrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ocorrencia;
    }

    public List<Ocorrencia> readOcorrenciaForMonth(String mes) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ocorrencia> Ocorrencia = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT o.cod_bo, f.cod_fer, f.cod_ferramenta, o.valor_ultima_compra, m.cod_maq, m.codigo_maquina, fun.codFuncionario, fun.nomeFuncionario, o.motivo_quebra, i.cod, i.cod_item, o.data FROM ocorrencia_producao o JOIN maquina m ON m.cod_maq = o.maquina_cod_maq JOIN tbfuncionarios fun ON fun.codFuncionario = o.tbfuncionarios_codFuncionario JOIN item i ON i.cod = o.item_cod JOIN ferramenta f ON f.cod_fer = o.ferramenta_cod_fer WHERE MONTH(o.data) = ? ORDER BY data;");
            if (mes.equals("")) {
                LocalDate data = LocalDate.now();
                Locale local = new Locale("pt", "BR");
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM", local);
                stmt.setString(1, fmt.format(data));
            } else {
                stmt.setString(1, mes);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCod_bo(rs.getInt("cod_bo"));
                o.setFr_codFer(rs.getInt("cod_fer"));
                o.setFr_codFerramenta(rs.getString("cod_ferramenta"));
                o.setValorUltCompra(rs.getDouble("valor_ultima_compra"));
                o.setFr_codMaq(rs.getInt("cod_maq"));
                o.setFr_codMaquina(rs.getString("codigo_maquina"));
                o.setFr_codFun(rs.getInt("codFuncionario"));
                o.setFr_nomeFun(rs.getString("nomeFuncionario"));
                o.setMotivoQuebra(rs.getString("motivo_quebra"));
                o.setFr_codItem(rs.getInt("cod"));
                o.setFr_codigoItem(rs.getString("cod_item"));
                o.setData(rs.getDate("data"));
                Ocorrencia.add(o);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ocorrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ocorrencia;
    }

    public List<Ocorrencia> listOcorrencia(String ferra, String maq, String opera, String operaTurno, String grupo, Date dataI, Date dataF) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ocorrencia> Ocorrencia = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT f.cod_ferramenta, f.grupo, o.valor_ultima_compra, m.codigo_maquina, fun.nomeFuncionario, fun.turnoFuncionario, i.cod_item, o.motivo_quebra, o.data FROM ocorrencia_producao o "
                    + " JOIN maquina m ON m.cod_maq = o.maquina_cod_maq"
                    + " JOIN tbfuncionarios fun ON fun.codFuncionario = o.tbfuncionarios_codFuncionario"
                    + " JOIN item i ON i.cod = o.item_cod"
                    + " JOIN ferramenta f ON f.cod_fer = o.ferramenta_cod_fer "
                    + " WHERE f.cod_ferramenta LIKE ? AND m.codigo_maquina LIKE ? AND fun.nomeFuncionario LIKE ? AND fun.turnoFuncionario LIKE ? AND f.grupo LIKE ?"
                    + " AND o.data BETWEEN ? AND ? ORDER BY data;");
            stmt.setString(1, "%" + ferra + "%");
            stmt.setString(2, "%" + maq + "%");
            stmt.setString(3, "%" + opera + "%");
            stmt.setString(4, "%" + operaTurno + "%");
            stmt.setString(5, "%" + grupo + "%");
            stmt.setDate(6, dataI);
            stmt.setDate(7, dataF);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setFr_codFerramenta(rs.getString("cod_ferramenta"));
                o.setFr_grupoFerramenta(rs.getString("grupo"));
                o.setValorUltCompra(rs.getDouble("valor_ultima_compra"));
                o.setFr_codMaquina(rs.getString("codigo_maquina"));
                o.setFr_nomeFun(rs.getString("nomeFuncionario"));
                o.setFr_turnoFun(rs.getString("turnoFuncionario"));
                o.setFr_codigoItem(rs.getString("cod_item"));
                o.setMotivoQuebra(rs.getString("motivo_quebra"));
                o.setData(rs.getDate("data"));
                Ocorrencia.add(o);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar ocorrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Ocorrencia;
    }

    public void deleteOcorrencia(Ocorrencia i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM ocorrencia_producao WHERE cod_bo =?");
            stmt.setInt(1, i.getCod_bo());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar ocorrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateOcorrencia(Ocorrencia o) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if (o.getData() == null) {
                stmt = con.prepareStatement("UPDATE ocorrencia_producao SET valor_ultima_compra = ?, maquina_cod_maq = ?, tbfuncionarios_codFuncionario = ?, motivo_quebra = ?, item_cod = ?, data = DATE(NOW()) WHERE cod_bo = ?");
                stmt.setInt(6, o.getCod_bo());
            } else {
                stmt = con.prepareStatement("UPDATE ocorrencia_producao SET valor_ultima_compra = ?, maquina_cod_maq = ?, tbfuncionarios_codFuncionario = ?, motivo_quebra = ?, item_cod = ?, data = ? WHERE cod_bo = ?");
                stmt.setDate(6, o.getData());
                stmt.setInt(7, o.getCod_bo());
            }

            stmt.setDouble(1, o.getValorUltCompra());
            stmt.setInt(2, o.getFr_codMaq());
            stmt.setInt(3, o.getFr_codFun());
            stmt.setString(4, o.getMotivoQuebra());
            stmt.setInt(5, o.getFr_codItem());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar ocorrência - D48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
