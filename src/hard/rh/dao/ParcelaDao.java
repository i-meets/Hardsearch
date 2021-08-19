package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Coparticipacao;
import hard.rh.model.Parcela;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ParcelaDao {

    FDErroOcorrido fdErroOcorrido;

    //VERIFICA SE A PARCELA EXISTE NO BANCO
    public boolean checkCreate(String codParcela) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbparcela where codParcela=?");
            stmt.setString(1, codParcela);
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

    //LISTA PARCELA POR CODIGO
    public boolean checkBtSalvarPro(String codPro) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbrealiza where tbcoparti_codCop = ?");
            stmt.setString(1, codPro);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cadastro - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkParcelaCreate(String codParcela) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbparcela where tbcoparti_codCop = ?");
            stmt.setString(1, codParcela);
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

    //BUSCAR O MAIOR CÓDIGO DE PARCELA CADASTRADO NO BANCO
    public List<Parcela> readMaxCodPar() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codParcela) FROM tbparcela");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela pa = new Parcela();
                pa.setCodParcela(rs.getInt("Max(codParcela)"));
                Parcela.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    //RECEBE OS DADOS DO BACK-END, E REALIZA O INSERT NA TABELA DO BANCO DE DADOS
    public void createParcela(Parcela p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbparcela(codParcela, "
                    + "tbcoparti_codCop, "
                    + "tbcoparti_tbfuncionarios_codFuncionario,"
                    + "tbcoparti_tbdependentes_codDependente, "
                    + "valorParcela, "
                    + "dataVencParcela,"
                    + " statusParcela)"
                    + "VALUES(?,?,?,?,?,?,?)");

            stmt.setInt(1, p.getCodParcela());
            stmt.setInt(2, p.getFr_codCop());
            stmt.setInt(3, p.getFr_codFuncionario());
            stmt.setInt(4, p.getFr_codBeneficiario());
            stmt.setDouble(5, p.getValorParcela());
            stmt.setDate(6, p.getDataVencPar());
            stmt.setInt(7, p.getStatusParcela());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void updateGeradoParcela(Coparticipacao cop) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbcoparti SET geradoPar = ? WHERE codCop = ?");
            stmt.setInt(1, cop.getGeradoPar());
            stmt.setInt(2, cop.getCodCop());
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

    //ATUALIZA A DATA DA PARCELA NO BANCO BANCO, ISSO OCORRE QUANDO A COPARTICIPAÇÃO POSSUI MAIS DE UMA PARCELA
    public void updateDateParcela(Parcela pa) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbparcela SET dataVencParcela = ADDDATE(dataVencParcela, INTERVAL ? MONTH) WHERE codParcela = ?");
            stmt.setInt(1, pa.getNumMesParcela());
            stmt.setInt(2, pa.getCodParcela());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //LISTA TODOS AS PARCELAS CADASTRADOS NO BANCO
    public List<Parcela> readParcela() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario  ORDER BY `diasVencPar` DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {

                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    public List<Parcela> readParcelaForCod(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE codParcela = ? ORDER BY `diasVencPar` DESC");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    public List<Parcela> readComparaParcelaMes(Date dataInicial, Date dataFinal) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT dataVencParcela, SUM(valorParcela) as valorTotal, COUNT(*) AS numeroParcelas FROM tbparcela WHERE dataVencParcela BETWEEN ? AND ? GROUP BY YEAR(dataVencParcela), MONTH(dataVencParcela);");
            stmt.setDate(1, dataInicial);
            stmt.setDate(2, dataFinal);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();

                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setValorTotal(rs.getDouble("valorTotal"));
                par.setNumeroDeParcela(rs.getInt("numeroParcelas"));
                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    //REALIZA A SOMA DO TOTAL DE PARCELAS EXISTENTES PARA UMA COPARTICIPAÇÃO
    public List<Parcela> readNumParCad(Integer desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("Select COUNT(*) as totalParcelas From tbparcela WHERE tbcoparti_codCop = ?");
            stmt.setInt(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela pa = new Parcela();

                pa.setNumeroDeParcela(rs.getInt("totalParcelas"));

                Parcela.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar número de parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    public List<Parcela> readDataForNumCop(Integer desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbparcela ,(SELECT MIN(codParcela) as codPar FROM tbparcela WHERE tbcoparti_codCop = ?) as result WHERE codParcela = result.codPar");
            stmt.setInt(1, desc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela pa = new Parcela();

                pa.setDataVencPar(rs.getDate("dataVencParcela"));

                Parcela.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar data coparticipação - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    public List<Parcela> readParForDataAndPaga(Date dataInicial, Date dataFinal) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, codFuncionario, nomeFuncionario, de.codDependente, de.nomeDependente, codCop, valorParcela, dataVencParcela, statusParcela, DATEDIFF(NOW(), dataVencParcela ) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = par.tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun ON fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE statusParcela = 2 AND dataVencParcela BETWEEN ? AND ? ORDER BY fun.nomeFuncionario AND diasVencPar DESC");
            stmt.setDate(1, (java.sql.Date) dataInicial);
            stmt.setDate(2, (java.sql.Date) dataFinal);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar listar parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    public List<Parcela> readParForDataAndNPaga(Date dataInicial, Date dataFinal) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, codFuncionario, nomeFuncionario, de.codDependente, de.nomeDependente, codCop, valorParcela, dataVencParcela, statusParcela, DATEDIFF(NOW(), dataVencParcela ) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = par.tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun ON fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario  WHERE statusParcela = 1 AND dataVencParcela BETWEEN ? AND ? ORDER BY fun.nomeFuncionario AND diasVencPar DESC");
            stmt.setDate(1, (java.sql.Date) dataInicial);
            stmt.setDate(2, (java.sql.Date) dataFinal);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar data parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    //LISTA PARCELAS COM STATUS DE NÃO PAGA(1) PELO CÓDIGO DO FUNCIONÁRIO
    //LISTA OS USUÁRIO PELO CÓDIGO DO FUNCIONÁRIO
    public List<Parcela> readParForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? ORDER BY fun.nomeFuncionario AND diasVencPar DESC");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    public List<Parcela> readParForUserAndStatusPaga(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? AND statusParcela = 2 ORDER BY fun.nomeFuncionario AND diasVencPar DESC");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas - 48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    //LISTA PARCELAS COM STATUS DE NÃO PAGA(1) PELO CÓDIGO DO FUNCIONÁRIO
    public List<Parcela> readParForUserAndStatusNPaga(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? AND statusParcela = 1 ORDER BY diasVencPar DESC");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela par = new Parcela();

                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("buscar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    public List<Parcela> readParForUserAndStatusNPagaForFolha(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE codFuncionario = ? AND statusParcela = 1 ORDER BY dataVencParcela ASC");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela par = new Parcela();

                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    //LSITA TODAS AS PARCELAS COM STATUS PAGA(2)
    public List<Parcela> readParForStatusPaga() throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE statusParcela = 2 ORDER BY diasVencPar DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));

                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    //LISTA TODAS AS PARCELAS COM O STATUS NÃO PAGA(1)
    public List<Parcela> readParForStatusNPaga() throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE statusParcela = 1 ORDER BY diasVencPar DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                par.setStatusParcela(rs.getInt("statusParcela"));

                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    //SOMA VALOR TOTAL DAS PARCELAS POR CÓDIGO DO FUNCIONÁRIO
    public List<Parcela> readValorParForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT SUM(valorParcela) FROM tbparcela WHERE tbcoparti_tbfuncionarios_codFuncionario = ?");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();
            Parcela par = new Parcela();

            while (rs.next()) {
                par.setValorTotalPar(rs.getDouble("SUM(valorParcela)"));
                Parcela.add(par);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("realizar calculo de parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;

    }

    public List<Parcela> readParForStatusNPagaAlert() throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Parcela> Parcela = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codParcela, tbcoparti_codCop, fun.codFuncionario, fun.nomeFuncionario, de.codDependente, de.nomeDependente, valorParcela, dataVencParcela , statusParcela, DATEDIFF(NOW(), dataVencParcela) as diasVencPar FROM tbparcela par JOIN tbcoparti cop ON cop.codCop = tbcoparti_codCop JOIN tbdependentes de ON de.codDependente = par.tbcoparti_tbdependentes_codDependente JOIN tbfuncionarios fun on fun.codFuncionario = par.tbcoparti_tbfuncionarios_codFuncionario WHERE statusParcela = 1 ORDER BY dataVencParcela");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcela par = new Parcela();
                par.setCodParcela(rs.getInt("codParcela"));
                par.setFr_codFuncionario(rs.getInt("codFuncionario"));
                par.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                par.setFr_codBeneficiario(rs.getInt("codDependente"));
                par.setFr_nomeBeneficiario(rs.getString("nomeDependente"));
                par.setFr_codCop(rs.getInt("tbcoparti_codCop"));
                par.setValorParcela(rs.getDouble("valorParcela"));
                par.setDataVencPar(rs.getDate("dataVencParcela"));
                par.setStatusParcela(rs.getInt("statusParcela"));
                par.setDiasAtrasoPar(rs.getInt("diasVencPar"));
                Parcela.add(par);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar parcelas - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Parcela;
    }

    //ATUALIZA OS DADOS DA PARCELA
    public void updateParcela(Parcela p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbparcela SET statusParcela = ? WHERE codParcela = ?");
            stmt.setInt(1, p.getStatusParcela());
            stmt.setInt(2, p.getCodParcela());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar parcela - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA A PARCELA
    public void delete(Parcela p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbparcela WHERE codParcela=?");
            stmt.setInt(1, p.getCodParcela());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("excluír parcela");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
