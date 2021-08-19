package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Contrato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ContratoDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE JÁ EXISTE O CADASTRO DO FUNCIONÁRIO PARA A EMPRESA INFORMADA
    public boolean checkCadContra(String fr_codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbcontrato WHERE tbfuncionarios_codFuncionario = ? ");
            stmt.setString(1, fr_codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //VALIDA O CÓDIGO DO FUNCIONÁRIO (VERIFICA SE O CADASTRO EXISTE NO BANCO)
    public boolean checkFuncionario(String fr_codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbconcionario WHERE codFuncionario = ?");
            stmt.setString(1, fr_codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //CADASTRA INTEGRAÇÃO
    public void createContrato(Contrato c) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO tbcontrato (codContra, tbfuncionarios_codFuncionario, dataAdmiContra, responsaContra, observaContra, dataVenc30dd, dataVenc90dd, statusContra, statusContra30dd, statusContra90dd)" + "VALUES (?,?,?,?,?,?,?,?,?,?)");

            stmt.setInt(1, c.getCodContra());
            stmt.setInt(2, c.getFr_codFuncionario());
            stmt.setDate(3, c.getDataAdmiContra());
            stmt.setString(4, c.getResponsContra());
            stmt.setString(5, c.getObservaContra());
            stmt.setDate(6, c.getDataVenc30dd());
            stmt.setDate(7, c.getDataVenc90dd());
            stmt.setInt(8, c.getStatusContra());
            stmt.setInt(9, c.getStatusContra30dd());
            stmt.setInt(10, c.getStatusContra90dd());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void updateDateVenc30dd(Contrato co) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET dataVenc30dd = ADDDATE(dataVenc30dd, INTERVAL 29 DAY) WHERE codContra = ?");
            stmt.setInt(1, co.getCodContra());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.print("Erro ao gerar data da parcela:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateDateVenc90dd(Contrato co) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET dataVenc90dd = ADDDATE(dataVenc90dd, INTERVAL 89 DAY ) WHERE codContra = ?");
            stmt.setInt(1, co.getCodContra());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("gerar as datas de parcelas - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //BUSCA AS INTEGRAÇÕES CADASTRADAS, E TRAZ A INFORMAÇÃO DE DIAS EM ATRASO PELO DATEDIFF
    public List<Contrato> readContratoAberto() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT* FROM tbcontrato con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusContra = 2 ORDER BY dataAdmiContra");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contrato co = new Contrato();
                co.setCodContra(rs.getInt("codContra"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setDataAdmiContra(rs.getDate("dataAdmiContra"));
                co.setResponsContra(rs.getString("responsaContra"));
                co.setObservaContra(rs.getString("observaContra"));
                co.setDataVenc30dd(rs.getDate("dataVenc30dd"));
                co.setDataVenc90dd(rs.getDate("dataVenc90dd"));
                co.setStatusContra(rs.getInt("statusContra"));
                co.setStatusContra30dd(rs.getInt("statusContra30dd"));
                co.setStatusContra90dd(rs.getInt("statusContra90dd"));

                Contrato.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;
    }

    public List<Contrato> readContratoFechado(String codFuncio, String status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT* FROM tbcontrato con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE codFuncionario LIKE ? AND  statusContra LIKE ? ORDER BY dataAdmiContra");
            stmt.setString(1, codFuncio + "%");
            stmt.setString(2, status + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contrato co = new Contrato();
                co.setCodContra(rs.getInt("codContra"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setDataAdmiContra(rs.getDate("dataAdmiContra"));
                co.setResponsContra(rs.getString("responsaContra"));
                co.setObservaContra(rs.getString("observaContra"));
                co.setDataVenc30dd(rs.getDate("dataVenc30dd"));
                co.setDataVenc90dd(rs.getDate("dataVenc90dd"));
                co.setStatusContra(rs.getInt("statusContra"));
                co.setStatusContra30dd(rs.getInt("statusContra30dd"));
                co.setStatusContra90dd(rs.getInt("statusContra90dd"));

                Contrato.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;
    }

    public List<Contrato> readMaiorCodContra() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codContra) FROM tbcontrato");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Contrato pa = new Contrato();
                pa.setCodContra(rs.getInt("Max(codContra)") + 1);
                Contrato.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;
    }

    //LISTA INTEGRAÇÃO PELO CÓDIGO DO FUNCIONÁRIO
    public List<Contrato> readStatusContraFoCodContra(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbcontrato con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE codContra = ? ");
            stmt.setString(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Contrato co = new Contrato();
                co.setStatusContra(rs.getInt("statusContra"));
                co.setStatusContra30dd(rs.getInt("statusContra30dd"));
                co.setStatusContra90dd(rs.getInt("statusContra90dd"));
                Contrato.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;

    }

    public List<Contrato> readContratoDIFF(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT DATEDIFF(NOW(), dataVenc30dd)  as dataVenc30dd , DATEDIFF(NOW(), dataVenc90dd) as dataVenc90dd FROM tbcontrato WHERE codContra = ?");
            stmt.setString(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Contrato co = new Contrato();
                co.setDiasRestantes30dd(rs.getInt("dataVenc30dd"));
                co.setDiasRestantes90dd(rs.getInt("dataVenc90dd"));

                Contrato.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;

    }

    public void updateContrato(Contrato c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET dataAdmiContra = ?, responsaContra = ?, observaContra = ?, dataVenc30dd = ?, dataVenc90dd = ?, statusContra = ?, statusContra30dd = ?, statusContra90dd = ? WHERE codContra = ?");

            stmt.setDate(1, c.getDataAdmiContra());
            stmt.setString(2, c.getResponsContra());
            stmt.setString(3, c.getObservaContra());
            stmt.setDate(4, c.getDataVenc30dd());
            stmt.setDate(5, c.getDataVenc90dd());
            stmt.setInt(6, c.getStatusContra());
            stmt.setInt(7, c.getStatusContra30dd());
            stmt.setInt(8, c.getStatusContra90dd());
            stmt.setInt(9, c.getCodContra());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateStatusContrato(Contrato i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET statusContra = ? WHERE codContra = ?");
            stmt.setInt(1, i.getStatusContra());
            stmt.setInt(2, i.getCodContra());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateContrato30dd(Contrato i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET statusContra30dd = ? WHERE codContra = ?");
            stmt.setInt(1, i.getStatusContra30dd());
            stmt.setInt(2, i.getCodContra());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateContrato90dd(Contrato i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontrato SET statusContra90dd = ? WHERE codContra = ?");
            stmt.setInt(1, i.getStatusContra90dd());
            stmt.setInt(2, i.getCodContra());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Contrato> readContratoAlert() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codContra, codFuncionario, nomeFuncionario, dataAdmiContra, statusContra30dd, statusContra90dd, dataVenc30dd, DATEDIFF(NOW(), dataVenc30dd) as diasRestantes30dd, dataVenc90dd, DATEDIFF(NOW(), dataVenc90dd) as diasRestantes90dd FROM tbcontrato con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusContra = 2 ORDER BY diasRestantes90dd DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contrato co = new Contrato();
                co.setCodContra(rs.getInt("codContra"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setDataAdmiContra(rs.getDate("dataAdmiContra"));
                co.setDataVenc30dd(rs.getDate("dataVenc30dd"));
                co.setDiasRestantes30dd(rs.getInt("diasRestantes30dd"));
                co.setDataVenc90dd(rs.getDate("dataVenc90dd"));
                co.setDiasRestantes90dd(rs.getInt("diasRestantes90dd"));
                co.setStatusContra30dd(rs.getInt("statusContra30dd"));
                co.setStatusContra90dd(rs.getInt("statusContra90dd"));

                Contrato.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;
    }

    public List<Contrato> readContForCod(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contrato> Contrato = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbcontrato con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE codContra = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Contrato co = new Contrato();
                co.setCodContra(rs.getInt("codContra"));
                co.setFr_codFuncionario(rs.getInt("codFuncionario"));
                co.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                co.setDataAdmiContra(rs.getDate("dataAdmiContra"));
                co.setResponsContra(rs.getString("responsaContra"));
                co.setObservaContra(rs.getString("observaContra"));
                co.setDataVenc30dd(rs.getDate("dataVenc30dd"));
                co.setDataVenc90dd(rs.getDate("dataVenc90dd"));
                co.setStatusContra(rs.getInt("statusContra"));
                co.setStatusContra30dd(rs.getInt("statusContra30dd"));
                co.setStatusContra90dd(rs.getInt("statusContra90dd"));
                Contrato.add(co);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Contrato;

    }

    //DELETA A INTEGRAÇÃO
    public void deleteContrato(Contrato i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbcontrato WHERE codContra =?");
            stmt.setInt(1, i.getCodContra());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
