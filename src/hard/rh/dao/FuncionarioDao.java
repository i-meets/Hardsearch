package hard.rh.dao;

import hard.config.ConnectionFactory;
import hard.config.ConnectionCloudFactory;
import hard.home.view.FDErroOcorrido;
import hard.rh.model.Dependente;
import hard.rh.model.Funcionario;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FuncionarioDao {

    FDErroOcorrido fdErroOcorrido;

    //VALIDA O CPF DO FUNIONÁRIO (VERIFICA SE O MESMO NÃO EXISTE NO BANCO, PARA NÃO EXISTIR MAIS DE UM FUNIONÁRIO COM O MEMO CPF)
    public boolean checkCpf(String cpfFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbfuncionarios where cpfFuncionario = AES_ENCRYPT(?, 'cpfcript') AND statusFuncionario = 0");
            stmt.setString(1, cpfFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkCodAndCpf(int codFuncionario, String cpfFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbfuncionarios where codFuncionario = ? AND cpfFuncionario = AES_ENCRYPT(?, 'cpfcript') AND statusFuncionario = 0");
            stmt.setInt(1, codFuncionario);
            stmt.setString(2, cpfFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkCodFuncio(int codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbfuncionarios where codFuncionario = ? AND statusFuncionario = 0");
            stmt.setInt(1, codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //INSERT DO FUNIONÁRIO NO BANCO
    public void createFuncionario(Funcionario f) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbfuncionarios (codFuncionario, nrFuncionario, nomeFuncionario, cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario, cboCargoFuncionario, dataAdmiFuncionario, salarioFuncionario, tipoSalarioFuncionario, adcNoturnoFuncionario, insalubriFuncionario, utiliTransFuncionario, utiliRefFuncionario, emailFuncionario, celularFuncionario, numResidencial, statusFuncionario) VALUES (?,?, ?, AES_ENCRYPT(?, 'cpfcript') , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, AES_ENCRYPT(?, 'emailcript'), AES_ENCRYPT(?, 'celularcript'), AES_ENCRYPT(?, 'resicript'), ?)");

            stmt.setInt(1, f.getCodFuncionario());
            stmt.setInt(2, f.getNrFuncionario());
            stmt.setString(3, f.getNomeFuncionario());
            stmt.setString(4, f.getCpfFuncionario());
            stmt.setString(5, f.getSetorFuncionario());
            stmt.setString(6, f.getCargoFuncionario());
            stmt.setString(7, f.getTurnoFuncionario());
            stmt.setString(8, f.getCboCargoFuncionario());
            stmt.setDate(9, f.getDataAdmiFuncionario());
            stmt.setDouble(10, f.getSalarioFuncionario());
            stmt.setString(11, f.getTipoSalarioFuncionario());
            stmt.setInt(12, f.getAdcNoturnoFuncionario());
            stmt.setInt(13, f.getInsalubriFuncionario());
            stmt.setInt(14, f.getUtiliTransFuncionario());
            stmt.setInt(15, f.getUtiliRefFuncionario());
            stmt.setString(16, f.getEmail());
            stmt.setString(17, f.getCelular());
            stmt.setString(18, f.getNumResidencial());
            stmt.setInt(19, f.getStatusFuncionario());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //INSERT DO FUNIONÁRIO NO BANCO
    public void createFuncionarioCloud(Funcionario f) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO funcionario (codFuncionario, nomeFuncionario, cpfFuncionario, setorFuncionario) VALUES (?, ?, AES_ENCRYPT(?, 'b4GQLsRyaDfM') , ?)");

            stmt.setInt(1, f.getCodFuncionario());
            stmt.setString(2, f.getNomeFuncionario());
            stmt.setString(3, f.getCpfFuncionario());
            stmt.setString(4, f.getSetorFuncionario());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar dados cloud - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //VERIFICA SE O CÓDIGO DO FUNCIONÁRIO NÃO EXISTE
    public boolean checkCod(String codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbfuncionarios where codFuncionario=?");
            stmt.setString(1, codFuncionario);
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

    //VERIFICA SE O CÓDIGO DO FUNCIONÁRIO NÃO EXISTE
    public boolean checkCodCloud(String codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from funcionario where codFuncionario=?");
            stmt.setString(1, codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código cloud - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean checkCodAndStatusAtivo(String codFuncionario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbfuncionarios where codFuncionario = ? AND statusFuncionario = 0");
            stmt.setString(1, codFuncionario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //LISTA TODOS FUNCIONÁRIOS
    public List<Funcionario> readFuncionario() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nrFuncionario, nomeFuncionario,  cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, "
                    + "setorFuncionario, "
                    + "cargoFuncionario, turnoFuncionario, cboCargoFuncionario, dataAdmiFuncionario,salarioFuncionario, "
                    + "tipoSalarioFuncionario, adcNoturnoFuncionario, insalubriFuncionario,utiliTransFuncionario,utiliRefFuncionario,"
                    + "cast(AES_DECRYPT(emailFuncionario,'emailcript') as char) as emailFuncionario,"
                    + "cast(AES_DECRYPT(celularFuncionario,'celularcript') as char) as celularFuncionario,"
                    + "cast(AES_DECRYPT(numResidencial,'resicript') as char) as numResidencial,"
                    + "statusFuncionario FROM tbfuncionarios WHERE statusFuncionario = 0 ORDER BY nomeFuncionario");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario fu = new Funcionario();
                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNrFuncionario(rs.getInt("nrFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setCpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));
                fu.setCargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setTurnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setCboCargoFuncionario(rs.getString("cboCargoFuncionario"));
                fu.setDataAdmiFuncionario(rs.getDate("dataAdmiFuncionario"));
                fu.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
                fu.setTipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fu.setAdcNoturnoFuncionario(rs.getInt("adcNoturnoFuncionario"));
                fu.setInsalubriFuncionario(rs.getInt("insalubriFuncionario"));
                fu.setUtiliTransFuncionario(rs.getInt("utiliTransFuncionario"));
                fu.setUtiliRefFuncionario(rs.getInt("utiliRefFuncionario"));
                fu.setStatusFuncionario(rs.getInt("statusFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;
    }

    public List<Funcionario> readFuncionarioForStatus(Integer status, String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nrFuncionario, nomeFuncionario,  cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, "
                    + "setorFuncionario, "
                    + "cargoFuncionario, turnoFuncionario, cboCargoFuncionario, dataAdmiFuncionario,salarioFuncionario, "
                    + "tipoSalarioFuncionario, adcNoturnoFuncionario, insalubriFuncionario,utiliTransFuncionario,utiliRefFuncionario,"
                    + "statusFuncionario FROM tbfuncionarios WHERE statusFuncionario = ? AND nomeFuncionario LIKE ? ORDER BY nomeFuncionario");

            stmt.setInt(1, status);
            stmt.setString(2, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();
                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNrFuncionario(rs.getInt("nrFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setCpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));
                fu.setCargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setTurnoFuncionario(rs.getString("turnoFuncionario"));

                fu.setCboCargoFuncionario(rs.getString("cboCargoFuncionario"));
                fu.setDataAdmiFuncionario(rs.getDate("dataAdmiFuncionario"));
                fu.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
                fu.setTipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fu.setAdcNoturnoFuncionario(rs.getInt("adcNoturnoFuncionario"));
                fu.setInsalubriFuncionario(rs.getInt("insalubriFuncionario"));
                fu.setUtiliTransFuncionario(rs.getInt("utiliTransFuncionario"));
                fu.setUtiliRefFuncionario(rs.getInt("utiliRefFuncionario"));
                fu.setStatusFuncionario(rs.getInt("statusFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;

    }

    //LISTA FUNCIONÁRIO PELO NOME
    public List<Funcionario> readFuncionarioForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nrFuncionario, nomeFuncionario, cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, setorFuncionario, cargoFuncionario, turnoFuncionario FROM tbfuncionarios WHERE statusFuncionario = 0 AND nomeFuncionario LIKE ?");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();

                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNrFuncionario(rs.getInt("nrFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setCpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));
                fu.setCargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setTurnoFuncionario(rs.getString("turnoFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;

    }

    public List<Funcionario> readOperadorForDesc(String desc, String cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nomeFuncionario, setorFuncionario FROM tbfuncionarios WHERE statusFuncionario = 0 AND nomeFuncionario LIKE ? AND codFuncionario LIKE ?;");
            stmt.setString(1, desc + "%");
            stmt.setString(2, cod + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();

                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;

    }

    public List<Funcionario> readFuncionarioForDescFolha(Date data) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nomeFuncionario FROM tbfuncionarios f WHERE f.codFuncionario NOT IN (SELECT tbfuncionarios_codFuncionario FROM tbfolha WHERE dataFolha = ? AND statusFolha = 1) AND statusFuncionario = 0 ORDER BY nomeFuncionario");
            stmt.setDate(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();

                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;

    }

    //LISTA FUNCIONÁRIO PELO CÓDIGO
    public List<Funcionario> readFuncionarioForCod(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codFuncionario, nrFuncionario, nomeFuncionario,  cast(AES_DECRYPT(cpfFuncionario,'cpfcript') as char) as cpfFuncionario, "
                    + "setorFuncionario, "
                    + "cargoFuncionario, turnoFuncionario, cboCargoFuncionario, dataAdmiFuncionario,salarioFuncionario, "
                    + "tipoSalarioFuncionario, adcNoturnoFuncionario, insalubriFuncionario,utiliTransFuncionario,utiliRefFuncionario,"
                    + "cast(AES_DECRYPT(emailFuncionario,'emailcript') as char) as emailFuncionario,"
                    + "cast(AES_DECRYPT(celularFuncionario,'celularcript') as char) as celularFuncionario,"
                    + "cast(AES_DECRYPT(numResidencial,'resicript') as char) as numResidencial,"
                    + "statusFuncionario FROM tbfuncionarios WHERE codFuncionario = ? ");
            stmt.setInt(1, Integer.parseInt(desc));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();
                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNrFuncionario(rs.getInt("nrFuncionario"));

                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setCpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));
                fu.setCargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setTurnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setCboCargoFuncionario(rs.getString("cboCargoFuncionario"));
                fu.setDataAdmiFuncionario(rs.getDate("dataAdmiFuncionario"));
                fu.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
                fu.setTipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fu.setAdcNoturnoFuncionario(rs.getInt("adcNoturnoFuncionario"));
                fu.setInsalubriFuncionario(rs.getInt("insalubriFuncionario"));
                fu.setUtiliTransFuncionario(rs.getInt("utiliTransFuncionario"));
                fu.setUtiliRefFuncionario(rs.getInt("utiliRefFuncionario"));
                fu.setEmail(rs.getString("emailFuncionario"));
                fu.setCelular(rs.getString("celularFuncionario"));
                fu.setNumResidencial(rs.getString("numResidencial"));
                fu.setStatusFuncionario(rs.getInt("statusFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Funcionario;

    }

    public List<Funcionario> readFuncionarioForCpf(String cpf) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbfuncionarios WHERE cpfFuncionario = AES_ENCRYPT(?, 'cpfcript')");
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();
                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setNrFuncionario(rs.getInt("nrFuncionario"));
                fu.setNomeFuncionario(rs.getString("nomeFuncionario"));
                fu.setCpfFuncionario(rs.getString("cpfFuncionario"));
                fu.setSetorFuncionario(rs.getString("setorFuncionario"));
                fu.setCargoFuncionario(rs.getString("cargoFuncionario"));
                fu.setTurnoFuncionario(rs.getString("turnoFuncionario"));
                fu.setCboCargoFuncionario(rs.getString("cboCargoFuncionario"));
                fu.setDataAdmiFuncionario(rs.getDate("dataAdmiFuncionario"));
                fu.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
                fu.setTipoSalarioFuncionario(rs.getString("tipoSalarioFuncionario"));
                fu.setAdcNoturnoFuncionario(rs.getInt("adcNoturnoFuncionario"));
                fu.setInsalubriFuncionario(rs.getInt("insalubriFuncionario"));
                fu.setUtiliTransFuncionario(rs.getInt("utiliTransFuncionario"));
                fu.setUtiliRefFuncionario(rs.getInt("utiliRefFuncionario"));
                fu.setStatusFuncionario(rs.getInt("statusFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        if (Funcionario == null) {
            System.out.println("retornou null");
        }
        return Funcionario;

    }

    public List<Funcionario> readFuncioForCpfImporta(String cpf) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> Funcionario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbfuncionarios WHERE cpfFuncionario = AES_ENCRYPT(?, 'cpfcript')");
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario fu = new Funcionario();
                fu.setCodFuncionario(rs.getInt("codFuncionario"));
                fu.setStatusFuncionario(rs.getInt("statusFuncionario"));

                Funcionario.add(fu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        if (Funcionario == null) {
            System.out.println("retornou null");
        }
        return Funcionario;

    }

    public List<Dependente> readMaiorCodDepende() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Dependente> Dependente = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codDependente) FROM tbdependentes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Dependente d = new Dependente();
                d.setCodDependente(rs.getInt("Max(codDependente)") + 1);
                Dependente.add(d);
            }
        } catch (SQLException ex) {
           fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("validar código - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Dependente;
    }

    //ATUALIZA O FUNCIONÁRIO
    public void updateFuncionario(Funcionario f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbfuncionarios SET nomeFuncionario = ?, nrFuncionario = ?, cpfFuncionario = AES_ENCRYPT(?,'cpfcript'), setorFuncionario= ?, cargoFuncionario= ?, turnoFuncionario = ?, cboCargoFuncionario =?, dataAdmiFuncionario = ?, salarioFuncionario = ?, tipoSalarioFuncionario = ?, adcNoturnoFuncionario = ?, insalubriFuncionario = ?, utiliTransFuncionario = ?, utiliRefFuncionario = ?,emailFuncionario = AES_ENCRYPT(?,'emailcript'),celularFuncionario = AES_ENCRYPT(?,'celularcript'),numResidencial= AES_ENCRYPT(?,'resicript'), statusFuncionario = ? WHERE codFuncionario = ?");

            stmt.setString(1, f.getNomeFuncionario());
            stmt.setInt(2, f.getNrFuncionario());
            stmt.setString(3, f.getCpfFuncionario());
            stmt.setString(4, f.getSetorFuncionario());
            stmt.setString(5, f.getCargoFuncionario());
            stmt.setString(6, f.getTurnoFuncionario());
            stmt.setString(7, f.getCboCargoFuncionario());
            stmt.setDate(8, f.getDataAdmiFuncionario());
            stmt.setDouble(9, f.getSalarioFuncionario());
            stmt.setString(10, f.getTipoSalarioFuncionario());
            stmt.setInt(11, f.getAdcNoturnoFuncionario());
            stmt.setInt(12, f.getInsalubriFuncionario());
            stmt.setInt(13, f.getUtiliTransFuncionario());
            stmt.setInt(14, f.getUtiliRefFuncionario());
            stmt.setString(15, f.getEmail());
            stmt.setString(16, f.getCelular());
            stmt.setString(17, f.getNumResidencial());
            stmt.setInt(18, f.getStatusFuncionario());

            stmt.setInt(19, f.getCodFuncionario());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //ATUALIZA O FUNCIONÁRIO
    public void updateFuncionarioCloud(Funcionario f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE funcionario SET nomeFuncionario = ?, cpfFuncionario = AES_ENCRYPT(?,'b4GQLsRyaDfM'), setorFuncionario= ? WHERE codFuncionario = ?");

            stmt.setString(1, f.getNomeFuncionario());
            stmt.setString(2, f.getCpfFuncionario());
            stmt.setString(3, f.getSetorFuncionario());
            stmt.setInt(4, f.getCodFuncionario());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro cloud - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA O FUNCIONÁRIO
    public void deleteFuncionario(Funcionario f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbfuncionarios WHERE codFuncionario =?");
            stmt.setInt(1, f.getCodFuncionario());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            String validaErro = ex.toString();
            if (validaErro.contains("a foreign key constraint fails")) {
                fdErroOcorrido = new FDErroOcorrido(null, true);

                FDErroOcorrido.LbTipoEvento.setText("AÇÃO NÃO PERMITIDA");
                FDErroOcorrido.LbMensagem.setText("Não é possível deletar cadastros que possuem outros registros no sistema");
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            } else {
                fdErroOcorrido = new FDErroOcorrido(null, true);
                fdErroOcorrido.LbInformaErro.setText("deletar cadastro - d48");
                FDErroOcorrido.TaCodigoErro.setText(ex.toString());
                fdErroOcorrido.setVisible(true);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Dependente> readDependenteForCpf(int codFuncio, String cpf) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Dependente> Dependente = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbdependentes WHERE statusDependente = 1 AND tbfuncionarios_codFuncionario = ? AND cpfDependente = AES_ENCRYPT(?, 'cpfcript') ");
            stmt.setInt(1, codFuncio);
            stmt.setString(2, cpf);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Dependente de = new Dependente();
                de.setCodDependente(rs.getInt("codDependente"));
                de.setNomeDependente(rs.getString("nomeDependente"));
                de.setTipoDependente(rs.getString("tipoDependente"));
                de.setCpfDependente(rs.getString("cpfDependente"));

                Dependente.add(de);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Dependente;

    }

    public boolean checkCpfDependente(String cpfDependente) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbdependentes where cpfDependente = AES_ENCRYPT(?, 'cpfcript') AND statusDependente = 1");
            stmt.setString(1, cpfDependente);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar cpf - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public void desativaDependente(Dependente d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbdependentes SET statusDependente = ? WHERE codDependente = ?");

            stmt.setInt(1, d.getStatusDependente());
            stmt.setInt(2, d.getCodDependente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dependente desativado");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("desativar cadastro - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void addDependente(Dependente d) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbdependentes (codDependente, nomeDependente, tipoDependente, tbfuncionarios_codFuncionario, cpfDependente, statusDependente ) VALUES (? ,?, ?, ?, "
                    + "AES_ENCRYPT(?, 'cpfcript'), ?)");
            stmt.setInt(1, d.getCodDependente());
            stmt.setString(2, d.getNomeDependente());
            stmt.setString(3, d.getTipoDependente());
            stmt.setInt(4, d.getFk_codFuncionario());
            stmt.setString(5, d.getCpfDependente());
            stmt.setInt(6, d.getStatusDependente());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cadastrar Dependente - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void updateDependente(Dependente d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbdependentes SET nomeDependente = ?, cpfDependente= AES_ENCRYPT(?,'cpfcript'), tipoDependente = ? WHERE codDependente = ?");

            stmt.setString(1, d.getNomeDependente());
            stmt.setString(2, d.getCpfDependente());
            stmt.setString(3, d.getTipoDependente());
            stmt.setInt(4, d.getCodDependente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar dependente - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Dependente> readDependenteForFuncio(int codFuncio) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Dependente> Dependente = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT codDependente, nomeDependente, tipoDependente, cast(AES_DECRYPT(cpfDependente,'cpfcript') as char) as cpfDependente FROM tbdependentes WHERE tbfuncionarios_codFuncionario = ? AND statusDependente = 1");
            stmt.setInt(1, codFuncio);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Dependente d = new Dependente();
                d.setCodDependente(rs.getInt("codDependente"));
                d.setNomeDependente(rs.getString("nomeDependente"));
                d.setTipoDependente(rs.getString("tipoDependente"));
                d.setCpfDependente(rs.getString("cpfDependente"));

                Dependente.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar dependetes - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Dependente;

    }

    public List<Dependente> readDependenteForCod(int codDep) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Dependente> Dependente = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT nomeDependente FROM tbdependentes WHERE codDependente = ?");
            stmt.setInt(1, codDep);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Dependente d = new Dependente();
                d.setNomeDependente(rs.getString("nomeDependente"));
                Dependente.add(d);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar dependetes - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Dependente;

    }

    public void deleteDependente(Funcionario f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbdependentes WHERE codDependente = ?");
            stmt.setInt(1, f.getCodDependente());
            stmt.executeUpdate();
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
