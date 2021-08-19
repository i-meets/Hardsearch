package hard.portaria.dao;

import hard.config.ConnectionFactory;
import hard.portaria.model.ContAcesso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ContAcessoDao {

    //VERIFICA SE O CÓDIGO DA EMPRESA EXISTE
    public boolean checkCod(String codAcesso) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbcontacesso where codAcesso=?");
            stmt.setString(1, codAcesso);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<ContAcesso> readMaiorCodAcesso() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codAcesso) FROM tbcontacesso");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContAcesso pa = new ContAcesso();
                pa.setCodAcesso(rs.getInt("Max(codAcesso)") + 1);
                ContAcesso.add(pa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;
    }

    //INSERT DOS DADOS DA EMPRESA NO BANCO
    public void createAcesso(ContAcesso p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbcontacesso(codAcesso,nomeEmp,veiculo, placa, nomePessoa, rg, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso) VALUES (?, ?, ?, ?, ?, AES_ENCRYPT(?, 'rgcript'), ?, ?, ?, ?, NOW())");

            stmt.setInt(1, p.getCodAcesso());
            stmt.setString(2, p.getNomeEmp());
            stmt.setString(3, p.getVeiculo());
            stmt.setString(4, p.getPlaca());
            stmt.setString(5, p.getNomePessoa());
            stmt.setString(6, p.getRg());
            stmt.setString(7, p.getObserva());
            stmt.setString(8, p.getHoraEntra());
            stmt.setString(9, p.getHoraSai());
            stmt.setInt(10, p.getStatusAcesso());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS DA EMPRESA CADASTRADAS NO BANCO
    public List<ContAcesso> readAcesso() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE statusAcesso = 1");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContAcesso p = new ContAcesso();
                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;
    }

    public List<ContAcesso> readSaidaConfirm() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE statusAcesso = 2");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContAcesso p = new ContAcesso();
                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;
    }

    public List<ContAcesso> readSaidaConfirmForMes(int mes) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE MONTH(dataAcesso) = ? AND YEAR(dataAcesso) = YEAR(NOW()) AND statusAcesso = 2");
            stmt.setInt(1, mes);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContAcesso p = new ContAcesso();
                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;
    }

    public List<ContAcesso> readEntregadorForAcesso(String empresa) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript FROM tbcontacesso WHERE nomeEmp = ? AND statusAcesso = 2");
            stmt.setString(1, empresa);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ContAcesso p = new ContAcesso();
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));

                ContAcesso.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContAcessoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na aplicação, contate Administrador! \nCódigo erro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;
    }

    //LISTA OS USUÁRIOS PELO SEU NOME
    public List<ContAcesso> readEmpForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE nomeEmp LIKE ?");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContAcesso p = new ContAcesso();

                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException error) {
            System.out.println("Erro ao buscar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;

    }

    public List<ContAcesso> readSaidaConfirmForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE nomeEmp LIKE ? AND statusAcesso = 2");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContAcesso p = new ContAcesso();

                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException error) {
            System.out.println("Erro ao buscar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;

    }

    public List<ContAcesso> readSaidaConfirmForDescForMes(int mes, String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE MONTH(dataAcesso) = ? AND YEAR(dataAcesso) = YEAR(NOW()) AND nomeEmp LIKE ? AND statusAcesso = 2");
            stmt.setInt(1, mes);
            stmt.setString(2, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ContAcesso p = new ContAcesso();

                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataAcesso"));

                ContAcesso.add(p);
            }
        } catch (SQLException error) {
            System.out.println("Erro ao buscar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;

    }

    //LISTA A EMPRESA PELO CÓDIGO
    public List<ContAcesso> readAcessoForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContAcesso> ContAcesso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codAcesso, nomeEmp, veiculo, placa, nomePessoa, cast(AES_DECRYPT(rg,'rgcript') as char) as rgcript, observa, horaEntrada, horaSaida, statusAcesso, dataAcesso FROM tbcontacesso WHERE codAcesso LIKE ?");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();
            ContAcesso p = new ContAcesso();

            while (rs.next()) {

                p.setCodAcesso(rs.getInt("codAcesso"));
                p.setNomeEmp(rs.getString("nomeEmp"));
                p.setVeiculo(rs.getString("veiculo"));
                p.setPlaca(rs.getString("placa"));
                p.setNomePessoa(rs.getString("nomePessoa"));
                p.setRg(rs.getString("rgcript"));
                p.setObserva(rs.getString("observa"));
                p.setHoraEntra(rs.getString("horaEntrada"));
                p.setHoraSai(rs.getString("horaSaida"));
                p.setStatusAcesso(rs.getInt("statusAcesso"));
                p.setDataAcesso(rs.getDate("dataSaida"));

                ContAcesso.add(p);
            }

        } catch (SQLException error) {

            System.out.println("Erro ao buscar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ContAcesso;

    }

    public void apontaSaida(ContAcesso p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontacesso SET horaSaida = ?, statusAcesso = ? WHERE codAcesso = ?");
            stmt.setString(1, p.getHoraSai());
            stmt.setInt(2, p.getStatusAcesso());
            stmt.setInt(3, p.getCodAcesso());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException error) {
            System.out.print("Erro ao atualizar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //ATUALIZA OS DADOS DA EMPRESA
    public void update(ContAcesso p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbcontacesso SET nomeEmp = ?,veiculo = ?, placa = ?, nomePessoa = ?, rg = AES_ENCRYPT('?', 'dpessoal'), observa = ?, horaEntrada = ?, horaSaida = ?, statusAcesso = ? WHERE codAcesso = ?");

            stmt.setString(1, p.getNomeEmp());
            stmt.setString(2, p.getVeiculo());
            stmt.setString(3, p.getPlaca());
            stmt.setString(4, p.getNomePessoa());
            stmt.setString(5, p.getRg());
            stmt.setString(6, p.getObserva());
            stmt.setString(7, p.getHoraEntra());
            stmt.setString(8, p.getHoraSai());
            stmt.setInt(9, p.getStatusAcesso());
            stmt.setInt(10, p.getCodAcesso());

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException error) {
            System.out.print("Erro ao atualizar" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA A EMPRESA
    public void delete(ContAcesso p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbcontacesso WHERE codAcesso = ?");
            stmt.setInt(1, p.getCodAcesso());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException error) {
            System.out.println("Erro ao excluir" + error);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
