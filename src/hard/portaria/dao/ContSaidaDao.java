package hard.portaria.dao;

import hard.config.ConnectionFactory;
import hard.home.view.FDErroOcorrido;
import hard.portaria.model.ContSaida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ContSaidaDao {

    FDErroOcorrido fdErroOcorrido;

    public void createExit(ContSaida ct) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbcontsaida (codSaida, tbfuncionarios_codFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, motivoSaida, espLocal, responsavel, statusSaida) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, ct.getCodSaida());
            stmt.setInt(2, ct.getFr_codFuncionario());
            stmt.setDate(3, ct.getDataSaidaFuncio());
            stmt.setString(4, ct.getHoraSaidaFuncio());
            stmt.setInt(5, ct.getRetornaTraba());
            stmt.setInt(6, ct.getMotivoSaida());
            stmt.setString(7, ct.getEspLocal());
            stmt.setString(8, ct.getResponsavel());
            stmt.setInt(9, ct.getStatusSaida());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso", "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void updateSaida(ContSaida c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontsaida SET dataSaidaFuncio = ?, horaSaidaFuncio = ?, retornaTrabalho = ?, motivoSaida = ?, espLocal = ? WHERE codSaida = ?");
            stmt.setDate(1, c.getDataSaidaFuncio());
            stmt.setString(2, c.getHoraSaidaFuncio());
            stmt.setInt(3, c.getRetornaTraba());
            stmt.setInt(4, c.getMotivoSaida());
            stmt.setString(5, c.getEspLocal());
            stmt.setInt(6, c.getCodSaida());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(ContSaida c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbcontsaida WHERE codSaida = ?");
            stmt.setInt(1, c.getCodSaida());
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

    public List<ContSaida> readSaida() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, motivoSaida, espLocal, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario  ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setMotivoSaida(rs.getInt("motivoSaida"));
                cs.setEspLocal(rs.getString("espLocal"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> readSaidaNConfirm() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, motivoSaida, espLocal, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusSaida = 1 ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setMotivoSaida(rs.getInt("motivoSaida"));
                cs.setEspLocal(rs.getString("espLocal"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> readSaidaConfirm() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, motivoSaida, espLocal, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusSaida = 2 ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setMotivoSaida(rs.getInt("motivoSaida"));
                cs.setEspLocal(rs.getString("espLocal"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> ContSaida() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codSaida) FROM tbcontsaida");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida co = new ContSaida();
                co.setCodSaida(rs.getInt("Max(codSaida)"));
                ContSaida.add(co);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> readSaidaForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun on fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ?");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));
                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;

    }

    public List<ContSaida> readDescSaidaNConfirm(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun on fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? AND statusSaida = 1");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));
                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;

    }

    public List<ContSaida> readDescSaidaConfirm(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun on fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? AND statusSaida = 2");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));
                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;

    }

    public boolean checkExit(Integer exitConfirm, Integer statuSaida) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbcontsaida where codSaida = ? AND statusSaida = ?");
            stmt.setInt(1, exitConfirm);
            stmt.setInt(2, statuSaida);
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

    public List<ContSaida> readContSaida() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusSaida = 1 ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> readContSaidaNConfirm() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel,  statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusSaida = 1 ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public List<ContSaida> readContSaidaConfirm() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel,  statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun ON fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE statusSaida = 2 ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;
    }

    public void updateConfirmaSaida(ContSaida c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontsaida SET statusSaida = 2 WHERE codSaida = ?");

            stmt.setInt(1, c.getCodSaida());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Saída confirmada!", "Confirmado", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteConfirmaSaida(ContSaida c) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontsaida SET statusSaida = 1 WHERE codSaida = ?");

            stmt.setInt(1, c.getCodSaida());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Confirmação removida!", "Removida", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ContSaida> readSaidaForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel, statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun on fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE codSaida LIKE ?");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();
            ContSaida cs = new ContSaida();

            while (rs.next()) {

                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));

                ContSaida.add(cs);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;

    }

    public List<ContSaida> readContSaidaForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContSaida> ContSaida = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codSaida, codFuncionario, nomeFuncionario, dataSaidaFuncio, horaSaidaFuncio, retornaTrabalho, responsavel,  statusSaida FROM tbcontsaida con JOIN tbfuncionarios fun on fun.codFuncionario = con.tbfuncionarios_codFuncionario WHERE nomeFuncionario LIKE ? AND statusSaida = 1");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContSaida cs = new ContSaida();
                cs.setCodSaida(rs.getInt("codSaida"));
                cs.setFr_codFuncionario(rs.getInt("codFuncionario"));
                cs.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                cs.setDataSaidaFuncio(rs.getDate("dataSaidaFuncio"));
                cs.setHoraSaidaFuncio(rs.getString("horaSaidaFuncio"));
                cs.setRetornaTraba(rs.getInt("retornaTrabalho"));
                cs.setResponsavel(rs.getString("responsavel"));
                cs.setStatusSaida(rs.getInt("statusSaida"));
                ContSaida.add(cs);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContSaida;

    }

}
