package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Cotista;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CotistaDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE JÁ EXISTE O CADASTRO DO FUNCIONÁRIO PARA A EMPRESA INFORMADA
    public boolean checkCadCotista(String codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbcotista WHERE tbfuncionarios_codFuncionario = ?");
            stmt.setString(1, codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //VALIDA O CÓDIGO DO FUNCIONÁRIO (VERIFICA SE O CADASTRO EXISTE NO BANCO)
    public boolean checkFuncionario(String codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbcotista WHERE tbfuncionarios_codFuncionario = ?");
            stmt.setString(1, codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código funcionário - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<Cotista> readMaiorCodCota() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codCota) FROM tbcotista");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cotista pa = new Cotista();
                pa.setCodCota(rs.getInt("Max(codCota)") + 1);
                Cotista.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;
    }

    //CADASTRA INTEGRAÇÃO
    public void createCota(Cotista i) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO tbcotista (codCota, tbfuncionarios_codFuncionario,  dataAdmi, dataVenci, observa) VALUES  (?,?,?,?,?)");
            stmt.setInt(1, i.getCodCota());
            stmt.setInt(2, i.getFr_codFuncionario());
            stmt.setDate(3, i.getDataAdmi());
            stmt.setDate(4, i.getDataVenci());
            stmt.setString(5, i.getObserva());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //BUSCA AS INTEGRAÇÕES CADASTRADAS, E TRAZ A INFORMAÇÃO DE DIAS EM ATRASO PELO DATEDIFF
    public List<Cotista> readCotista() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, dataAdmi, dataVenci, observa, DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista cot JOIN tbfuncionarios fun ON fun.codFuncionario = cot.tbfuncionarios_codFuncionario ORDER BY dataVenci");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));
                co.setObserva(rs.getString("observa"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;
    }

    //LISTA AS INTEGRAÇÕES QUE ESTÃO VENCIDAS
    public List<Cotista> readCotistaVencidas() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario,  cargoFuncionario, dataAdmi, dataVenci, DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista cota JOIN tbfuncionarios fun ON fun.codFuncionario = cota.tbfuncionarios_codFuncionario  WHERE dataVenci BETWEEN CURDATE() - INTERVAL 365 DAY AND CURDATE() ORDER BY dataVenci");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;
    }

    public List<Cotista> readCotaForData(Date dataInicial, Date dataFinal) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario,  cargoFuncionario, dataAdmi, dataVenci, DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista cota JOIN tbfuncionarios fun ON fun.codFuncionario = cota.tbfuncionarios_codFuncionario WHERE dataVenci BETWEEN ? AND ? ORDER BY dataVenci");
            stmt.setDate(1, (java.sql.Date) dataInicial);
            stmt.setDate(2, (java.sql.Date) dataFinal);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));
                Cotista.add(co);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;
    }

    //LISTA INTEGRAÇÃO PELO CÓDIGO DO FUNCIONÁRIO
    public List<Cotista> readCotistaForFuncionario(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, dataAdmi, dataVenci, observa, DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista cot JOIN tbfuncionarios fun ON fun.codFuncionario = cot.tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? ORDER BY dataVenci");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;

    }

    //LISTA TODAS AS INTEGRAÇÕES
    public List<Cotista> readCotista(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, dataAdmi, dataVenci,  DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista WHERE nomeFuncionario LIKE ? ORDER BY dataVenci");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Cotista;

    }

    public List<Cotista> readCotaForCod(String desc) throws ClassNotFoundException, SQLException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codCota, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, cargoFuncionario, dataAdmi, dataVenci, observa, DATEDIFF(NOW(), dataVenci) as dataVencimento FROM tbcotista WHERE codCota LIKE ?");
            stmt.setString(1, desc);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cotista co = new Cotista();
                co.setCodCota(rs.getInt("codCota"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                co.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));
                co.setDiasVencidos(rs.getInt("dataVencimento"));
                co.setObserva(rs.getString("observa"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return Cotista;

    }

    public List<Cotista> readObservaAndDateForCod(String desc) throws ClassNotFoundException, SQLException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cotista> Cotista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT observa, dataAdmi, dataVenci FROM tbcotista WHERE codCota LIKE ?");
            stmt.setString(1, desc);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cotista co = new Cotista();
                co.setObserva(rs.getString("observa"));
                co.setDataAdmi(rs.getDate("dataAdmi"));
                co.setDataVenci(rs.getDate("dataVenci"));

                Cotista.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return Cotista;

    }

    //ATUALIZA INTEGRAÇÃO
    public void updateCotista(Cotista i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcotista SET  dataAdmi = ?, dataVenci = ?, observa = ? WHERE codCota = ?");
            stmt.setDate(1, i.getDataAdmi());
            stmt.setDate(2, i.getDataVenci());
            stmt.setString(3, i.getObserva());
            stmt.setInt(4, i.getCodCota());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA A INTEGRAÇÃO
    public void deleteCotista(Cotista i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbcotista WHERE codCota =?");
            stmt.setInt(1, i.getCodCota());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadatro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
