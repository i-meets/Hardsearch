package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Empresa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmpresaDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE O CÓDIGO DA EMPRESA EXISTE
    public boolean checkCod(String codEmp) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbempresas where codEmp=?");
            stmt.setString(1, codEmp);
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

    //INSERT DOS DADOS DA EMPRESA NO BANCO
    public void createEmp(Empresa p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbempresas(codEmp,nomeEmp,tipoEmp,mailEmp)" + "VALUES(?,?,?,?)");
            stmt.setInt(1, p.getCodEmp());
            stmt.setString(2, p.getNomeEmp());
            stmt.setString(3, p.getTipoEmp());
            stmt.setString(4, p.getMailEmp());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS DA EMPRESA CADASTRADAS NO BANCO
    public List<Empresa> readEmp(String tipo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Empresa> Empresa = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbempresas WHERE tipoEmp LIKE ?");
            stmt.setString(1, tipo + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Empresa p = new Empresa();

                p.setCodEmp(rs.getInt("codEmp"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setTipoEmp(rs.getString("tipoEmp"));
                p.setMailEmp(rs.getString("mailEmp"));

                Empresa.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar empresas d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Empresa;

    }

    //LISTA OS USUÁRIOS PELO SEU NOME
    public List<Empresa> readEmpForDesc(String desc, String tipo) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Empresa> Empresa = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbempresas WHERE nomeEmp LIKE ? AND tipoEmp LIKE ?");
            stmt.setString(1, desc + "%");
            stmt.setString(2, tipo + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Empresa p = new Empresa();

                p.setCodEmp(rs.getInt("codEmp"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setTipoEmp(rs.getString("tipoEmp"));
                p.setMailEmp(rs.getString("mailEmp"));

                Empresa.add(p);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar empresas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Empresa;

    }

    //LISTA A EMPRESA PELO CÓDIGO
    public List<Empresa> readEmpForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Empresa> Empresa = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbempresas WHERE codEmp LIKE ?");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();
            Empresa p = new Empresa();

            while (rs.next()) {

                p.setCodEmp(rs.getInt("codEmp"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setTipoEmp(rs.getString("tipoEmp"));
                p.setMailEmp(rs.getString("mailEmp"));
                Empresa.add(p);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar empresa - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Empresa;

    }

    //ATUALIZA OS DADOS DA EMPRESA
    public void update(Empresa p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbempresas SET nomeEmp = ?, tipoEmp = ?, mailEmp = ? WHERE codEmp = ?");

            stmt.setString(1, p.getNomeEmp());
            stmt.setString(2, p.getTipoEmp());
            stmt.setInt(4, p.getCodEmp());
            stmt.setString(3, p.getMailEmp());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("editar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA A EMPRESA
    public void delete(Empresa p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbempresas WHERE codEmp=?");
            stmt.setInt(1, p.getCodEmp());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
