package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Integracao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class IntegracaoDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE JÁ EXISTE O CADASTRO DO FUNCIONÁRIO PARA A EMPRESA INFORMADA
    public boolean checkCadIntegra(String fr_codFuncionario, String fr_codEmpresa) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbintegra WHERE tbfuncionarios_codFuncionario = ? AND tbempresas_codEmp = ?");
            stmt.setString(1, fr_codFuncionario);
            stmt.setString(2, fr_codEmpresa);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - d48");
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
            stmt = con.prepareStatement("SELECT * FROM tbintegra WHERE tbfuncionarios_codFuncionario = ?");
            stmt.setString(1, fr_codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código do funcionário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //VALIDA CÓDIGO DA EMPRESA (VERIFICA SE O CADASTRO EXISTE NO BANCO)
    public boolean checkEmpresa(String fr_codEmpresa) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tbintegra WHERE tbempresas_codEmp = ? ");
            stmt.setString(1, fr_codEmpresa);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código da empresa - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //CADASTRA INTEGRAÇÃO
    public void createIntegracao(Integracao i) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO tbintegra (codIntegra, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, tbfuncionarios_codFuncionario, tbempresas_codEmp) VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, i.getCodInt());
            stmt.setDate(2, i.getDataUltiInt());
            stmt.setDate(3, i.getDataVencInt());
            stmt.setDate(4, i.getDataUltiAso());
            stmt.setDate(5, i.getDataVencAso());
            stmt.setInt(6, i.getFr_codFuncionario());
            stmt.setInt(7, i.getFr_codEmpresa());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cadastrar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //BUSCA AS INTEGRAÇÕES CADASTRADAS, E TRAZ A INFORMAÇÃO DE DIAS EM ATRASO PELO DATEDIFF
    public List<Integracao> readIntegracao() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 ORDER BY dataVencIntegra");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));

                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;
    }

    public List<Integracao> readIntegracaoForCod(int codIntegra) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE codIntegra = ?");
            stmt.setInt(1, codIntegra);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));

                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;
    }

    //LISTA AS INTEGRAÇÕES QUE ESTÃO VENCIDAS
    public List<Integracao> readIntegracaoVencidas() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 AND dataVencIntegra BETWEEN CURDATE() - INTERVAL 365 DAY AND CURDATE() ORDER BY dataVencIntegra");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));

                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;
    }

    //LISTA INTEGRAÇÃO PARA CONFERÊNCIA NA TELA PRINCIPAL DE CADASTRO DE INTEGRAÇÃO
    public List<Integracao> readIntegracaoFront() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 ORDER BY dataVencIntegra");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));

                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;
    }

    //LISTA INTEGRAÇÃO PELO CÓDIGO DO FUNCIONÁRIO
    public List<Integracao> readIntegracaoForFuncionario(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 AND fun.nomeFuncionario LIKE ? ORDER BY dataVencIntegra");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));

                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;

    }

    //LISTA AS INTEGRAÇÕES PELO CÓDIGO DA EMPRESA
    public List<Integracao> readIntegracaoForCodEmpresa(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 AND codEmp LIKE ? ORDER BY dataVencIntegra");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));
                Integracao.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;

    }

    public List<Integracao> readParForData(Date dataInicial, Date dataFinal) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integracao> Integracao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codIntegra, codFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, codEmp, nomeEmp, dataUltiIntegra, dataVencIntegra, dataUltiAso, dataVencAso, DATEDIFF(NOW(), dataVencIntegra) as diasVenci FROM tbintegra inte JOIN tbfuncionarios fun ON fun.codFuncionario = inte.tbfuncionarios_codFuncionario JOIN tbempresas emp ON emp.codEmp = inte.tbempresas_codEmp WHERE fun.statusFuncionario = 0 AND dataVencIntegra BETWEEN ? AND ? ORDER BY dataVencIntegra");
            stmt.setDate(1, (java.sql.Date) dataInicial);
            stmt.setDate(2, (java.sql.Date) dataFinal);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Integracao fu = new Integracao();
                fu.setCodInt(rs.getInt("codIntegra"));
                fu.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fu.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setFr_cpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fu.setFr_cargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setFr_turnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setFr_codEmpresa(rs.getInt("codEmp"));
                fu.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                fu.setDataUltiInt(rs.getDate("dataUltiIntegra"));
                fu.setDataVencInt(rs.getDate("dataVencIntegra"));
                fu.setDataUltiAso(rs.getDate("dataUltiAso"));
                fu.setDataVencAso(rs.getDate("dataVencAso"));
                fu.setDiasVencidos(rs.getInt("diasVenci"));
                Integracao.add(fu);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar integração - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Integracao;

    }

    //ATUALIZA INTEGRAÇÃO
    public void updateIntegracao(Integracao i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbintegra SET dataUltiIntegra = ?, dataVencIntegra = ?, dataUltiAso= ?, dataVencAso= ? WHERE codIntegra = ?");
            stmt.setDate(1, i.getDataUltiInt());
            stmt.setDate(2, i.getDataVencInt());
            stmt.setDate(3, i.getDataUltiAso());
            stmt.setDate(4, i.getDataVencAso());
            stmt.setInt(5, i.getCodInt());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar integração");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA A INTEGRAÇÃO
    public void deleteIntegracao(Integracao i) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbintegra WHERE codIntegra =?");
            stmt.setInt(1, i.getCodInt());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
