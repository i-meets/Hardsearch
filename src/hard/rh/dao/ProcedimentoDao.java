package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Procedimento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProcedimentoDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE O CÓDIGO DO PROCEDIMENTO EXISTE NO BANCO
    public boolean checkCod(String codPro) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbprocedi where codPro = ?");
            stmt.setString(1, codPro);
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

    public List<Procedimento> readMaiorCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Procedimento> Procedimento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codPro) FROM tbprocedi");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Procedimento pa = new Procedimento();
                pa.setCodPro(rs.getInt("Max(codPro)") + 1);
                Procedimento.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar maior código - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Procedimento;
    }

    //INSERT DOS DADOS DO PROCEDIMENTO NO BANCO
    public void createPro(Procedimento p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbprocedi(codPro,nomePro, valorPro, parcelaPro, statusPro)" + "VALUES(?,?,?,?,?)");
            stmt.setInt(1, p.getCodPro());
            stmt.setString(2, p.getNomePro());
            stmt.setDouble(3, p.getValorPro());
            stmt.setInt(4, p.getParcelaPro());
            stmt.setInt(5, p.getStatusPro());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar procedimento  - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS OS PROCEDIMENTO CADASTRADOS NO BANCO
    public List<Procedimento> readPro(int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Procedimento> Procedimento = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbprocedi WHERE statusPro = ?");
            stmt.setInt(1, status);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Procedimento p = new Procedimento();
                p.setCodPro(rs.getInt("codPro"));
                p.setNomePro(rs.getString("nomePro"));
                p.setValorPro(rs.getDouble("valorPro"));
                p.setParcelaPro(rs.getInt("parcelaPro"));
                p.setStatusPro(rs.getInt("statusPro"));

                Procedimento.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar procedimento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Procedimento;
    }

    //LISTA OS PROCEDIMENTOS PELO SEU NOME
    public List<Procedimento> readProForDesc(String desc, int status) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Procedimento> Procedimento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbprocedi WHERE nomePro LIKE ? AND statusPro = ?");
            stmt.setString(1, desc + "%");
            stmt.setInt(2, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Procedimento p = new Procedimento();

                p.setCodPro(rs.getInt("codPro"));
                p.setNomePro(rs.getString("nomePro"));
                p.setValorPro(rs.getDouble("valorPro"));
                p.setParcelaPro(rs.getInt("parcelaPro"));
                p.setStatusPro(rs.getInt("statusPro"));

                Procedimento.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar procedimento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Procedimento;

    }

    //LISTA OS PROCEDIMENTOS PELO CÓDIGO
    public List<Procedimento> readProForCod(int codPro) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Procedimento> Procedimento = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbprocedi WHERE codPro = ?");
            stmt.setInt(1, codPro);
            rs = stmt.executeQuery();
            Procedimento p = new Procedimento();

            while (rs.next()) {

                p.setCodPro(rs.getInt("codPro"));
                p.setNomePro(rs.getString("nomePro"));
                p.setValorPro(rs.getDouble("valorPro"));
                p.setParcelaPro(rs.getInt("parcelaPro"));
                p.setStatusPro(rs.getInt("statusPro"));
                Procedimento.add(p);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar procedimento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Procedimento;

    }

    //ATUALIZA OS DADOS DO PROCEDIMENTO
    public void update(Procedimento p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbprocedi SET nomePro = ?, valorPro = ?, parcelaPro = ?, statusPro = ? WHERE codPro = ?");

            stmt.setString(1, p.getNomePro());
            stmt.setDouble(2, p.getValorPro());
            stmt.setInt(3, p.getParcelaPro());
            stmt.setInt(4, p.getStatusPro());
            stmt.setInt(5, p.getCodPro());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar procedimento - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA O PROCEDIMENTO
    public void delete(Procedimento p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbprocedi WHERE codPro=?");
            stmt.setInt(1, p.getCodPro());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            String erro = ex.toString();

            if (erro.contains("a foreign key constraint fails")) {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                FDErroOcorrido.LbTipoEvento.setText("AÇÃO NÃO PERMITIDA");
                FDErroOcorrido.LbMensagem.setText("Não é possível deletar procedimentos que possuem outros registros no sistema");
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            } else {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar procedimento - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
