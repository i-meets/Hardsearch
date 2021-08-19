package hard.home.dao;

import hard.config.ConnectionFactory;
import hard.home.model.Usuario;
import hard.home.view.FDErroOcorrido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDao {

    FDErroOcorrido fdErroOcorrido;

    private String Mlogin;

    public String getMlogin() {
        return Mlogin;
    }

    public void setMlogin(String Mlogin) {
        this.Mlogin = Mlogin;
    }

    //VERIFICAR USUÁRIO E SENHA PARA REALIZAR O LOGIN
    public boolean checkLogin(String loginUsuario, String senhaUsuario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {

            stmt = con.prepareStatement("Select * from tbusuarios where loginUsuario = ? and binary senhaUsuario = AES_ENCRYPT(?, 'senhacript')");
            stmt.setString(1, loginUsuario);
            stmt.setString(2, senhaUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {

                check = true;

            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar o login - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;

    }

    public List<Usuario> readMaiorCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(codUsuario) FROM tbusuarios");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario pa = new Usuario();
                pa.setCodUsuario(rs.getInt("Max(codUsuario)") + 1);
                Usuario.add(pa);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuario;
    }

    // VERIFICA SE O LOGIN DO USUÁRIO NÃO EXISTE
    public boolean checkCreate(String loginUsuario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbusuarios where loginUsuario=?");
            stmt.setString(1, loginUsuario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar login do usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    // VERIFICA SE O CÓDIGO DO USUÁRIO EXISTE
    public boolean checkCod(String codUsuario) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("Select * from tbusuarios where codUsuario=?");
            stmt.setString(1, codUsuario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar código do usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    //INSERT DOS DADOS DO USUÁRIO NO BANCO
    public void createUser(Usuario u) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbusuarios(codUsuario,nomeUsuario, setorUsuario,cargoUsuario, loginUsuario, senhaUsuario, emailUsuario)" + "VALUES(?,?,?,?,?,AES_ENCRYPT(?, 'senhacript'),?)");
            stmt.setInt(1, u.getCodUsuario());
            stmt.setString(2, u.getNomeUsuario());
            stmt.setString(3, u.getSetorUsuario());
            stmt.setString(4, u.getCargoUsuario());
            stmt.setString(5, u.getLoginUsuario());
            stmt.setString(6, u.getSenhaUsuario());
            stmt.setString(7, u.getEmailUsuario());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "salvo com sucesso");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("criar usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public void importeTelas(Usuario u) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO acesso_user (cod, tbtelas_codTela, tbusuarios_codUsuario, permite_acesso) SELECT 0, codTela, ?, 0 FROM tbtelas;");
            stmt.setInt(1, u.getCodUsuario());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Telas importadas com sucesso. Não esqueça de acessar a tela de permissões", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("importar telas usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    //LISTA TODOS OS USUÁRIOS CADASTRADOS NO BANCO
    public List<Usuario> readUser() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuarios = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbusuarios");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usu = new Usuario();
                usu.setCodUsuario(rs.getInt("codUsuario"));
                usu.setNomeUsuario(rs.getString("nomeUsuario"));
                usu.setSetorUsuario(rs.getString("setorUsuario"));
                usu.setCargoUsuario(rs.getString("cargoUsuario"));
                usu.setLoginUsuario(rs.getString("loginUsuario"));
                usu.setEmailUsuario(rs.getString("emailUsuario"));

                Usuarios.add(usu);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuarios;
    }

    //LISTA OS USUÁRIOS PELO SEU NOME
    public List<Usuario> readUserForDesc(String desc) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbusuarios us JOIN tbpre_user pre ON pre.cod = us.codUsuario WHERE nomeUsuario LIKE ?");
            stmt.setString(1, desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();

                u.setCodUsuario(rs.getInt("codUsuario"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setSetorUsuario(rs.getString("setorUsuario"));
                u.setCargoUsuario(rs.getString("cargoUsuario"));
                u.setLoginUsuario(rs.getString("loginUsuario"));
                u.setEmailUsuario(rs.getString("emailUsuario"));
                u.setFr_vDash(rs.getInt("v_dash_dp"));
                Usuario.add(u);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuario;

    }

    //LISTA OS USUÁRIO PELO CÓDIGO
    public List<Usuario> readUserForCod(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbusuarios WHERE codUsuario = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            Usuario u = new Usuario();

            while (rs.next()) {

                u.setCodUsuario(rs.getInt("codUsuario"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setSetorUsuario(rs.getString("setorUsuario"));
                u.setCargoUsuario(rs.getString("cargoUsuario"));
                u.setLoginUsuario(rs.getString("loginUsuario"));
                u.setEmailUsuario(rs.getString("emailUsuario"));
                Usuario.add(u);
            }

        } catch (SQLException ex) {

            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar o usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuario;

    }

    //ATUALIZA OS DADOS DO USUÁRIO
    public void update(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbusuarios SET nomeUsuario = ?, setorUsuario = ?, cargoUsuario = ?, emailUsuario = ? WHERE codUsuario = ?");
            stmt.setString(1, u.getNomeUsuario());
            stmt.setString(2, u.getSetorUsuario());
            stmt.setString(3, u.getCargoUsuario());
            stmt.setString(4, u.getEmailUsuario());
            stmt.setInt(5, u.getCodUsuario());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar cadastro do usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateSenha(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbusuarios SET senhaUsuario = AES_ENCRYPT(?, 'senhacript') WHERE loginUsuario = ?");
            stmt.setString(1, u.getSenhaUsuario());
            stmt.setString(2, u.getLoginUsuario());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar senha - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteConfig(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbpre_user WHERE cod = ?");
            stmt.setInt(1, u.getCodUsuario());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("tentar excluír cadastro de usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //DELETA O USUÁRIO PELO NÚMERO DO CÓDIGO
    public void delete(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbusuarios WHERE codUsuario =?");
            stmt.setInt(1, u.getCodUsuario());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("tentar excluír cadastro de usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deletePermissao(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM acesso_user WHERE tbusuarios_codUsuario = ?");
            stmt.setInt(1, u.getCodUsuario());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("tentar excluír permissões de usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void saveSessaoUser(Usuario u) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbsessao (codSessao, tbusuarios_codUsuario, tbtelas_codTela, fk_versaoAtualTela, nomeDesktop, ipDesktop, dataSessao, horaSessao) VALUES (null, ?, ?, ?, ?, ?, DATE(NOW()), CURRENT_TIME())");
            stmt.setInt(1, u.getCodUsuario());
            stmt.setString(2, u.getFr_codTela());
            stmt.setString(3, u.getFr_versaoTela());
            stmt.setString(4, u.getNameDesktop());
            stmt.setString(5, u.getIpDesktop());
            stmt.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
        deletarRegistro();
    }

    public List<Usuario> readSessao() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuarios = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT u.loginUsuario, te.codTela, te.versaoAtualTela, fk_versaoAtualTela, nomeDesktop, ipDesktop, dataSessao, horaSessao FROM tbsessao se JOIN tbusuarios u ON u.codUsuario = se.tbusuarios_codUsuario JOIN tbtelas te ON te.codTela = se.tbtelas_codTela ORDER BY dataSessao DESC, horaSessao DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usu = new Usuario();
                usu.setLoginUsuario(rs.getString("loginUsuario"));
                usu.setFr_codTela(rs.getString("codTela"));
                usu.setFr_versaoNoUsuario(rs.getString("fk_versaoAtualTela"));
                usu.setFr_versaoTela(rs.getString("versaoAtualTela"));
                usu.setNameDesktop(rs.getString("nomeDesktop"));
                usu.setIpDesktop(rs.getString("ipDesktop"));
                usu.setDataSessao(rs.getDate("dataSessao"));
                usu.setHoraSessao(rs.getString("horaSessao"));

                Usuarios.add(usu);
            }
        } catch (SQLException ex) {

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuarios;
    }

    public void deletarRegistro() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbsessao WHERE dataSessao < (DATE_SUB(CURDATE(), INTERVAL 30 DAY))");
            stmt.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void saveConfig(Usuario u) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbpre_user (cod, tbusuarios_codUser, v_dash_dp, r_email_v_integral, r_email_v_par, r_email_v_contra, r_email_nf_compras, r_email_nf_fiscal, r_email_oco_prod ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, u.getFr_codConfig());
            stmt.setInt(2, u.getCodUsuario());
            stmt.setInt(3, u.getFr_vDash());
            stmt.setInt(4, u.getFr_rEmailIntegra());
            stmt.setInt(5, u.getFr_rEmailPar());
            stmt.setInt(6, u.getFr_rEmailContr());
            stmt.setInt(7, u.getFr_rEmailNfeCompras());
            stmt.setInt(8, u.getFr_rEmailNfeFiscal());
            stmt.setInt(9, u.getFr_rEmailOcoProd());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("salvar configurações do usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
        deletarRegistro();
    }

    public void updateConfig(Usuario u) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbpre_user SET v_dash_dp = ?, r_email_v_integral = ?, r_email_v_par = ?,  r_email_v_contra = ?, r_email_nf_compras = ?, r_email_nf_fiscal = ?, r_email_oco_prod = ? WHERE cod = ? AND tbusuarios_codUser = ?");
            stmt.setInt(1, u.getFr_vDash());
            stmt.setInt(2, u.getFr_rEmailIntegra());
            stmt.setInt(3, u.getFr_rEmailPar());
            stmt.setInt(4, u.getFr_rEmailContr());
            stmt.setInt(5, u.getFr_rEmailNfeCompras());
            stmt.setInt(6, u.getFr_rEmailNfeFiscal());
            stmt.setInt(7, u.getFr_rEmailOcoProd());
            stmt.setInt(8, u.getFr_codConfig());
            stmt.setInt(9, u.getCodUsuario());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar configurações do usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Usuario> readConfig(int cod) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbpre_user WHERE tbusuarios_codUser = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            Usuario u = new Usuario();

            while (rs.next()) {

                u.setFr_codConfig(rs.getInt("cod"));
                u.setFr_vDash(rs.getInt("v_dash_dp"));
                u.setFr_rEmailIntegra(rs.getInt("r_email_v_integral"));
                u.setFr_rEmailContr(rs.getInt("r_email_v_contra"));
                u.setFr_rEmailPar(rs.getInt("r_email_v_par"));
                u.setFr_rEmailNfeCompras(rs.getInt("r_email_nf_compras"));
                u.setFr_rEmailNfeFiscal(rs.getInt("r_email_nf_fiscal"));
                u.setFr_rEmailOcoProd(rs.getInt("r_email_oco_prod"));
                Usuario.add(u);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar o usuário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuario;

    }

    //NOTIFICAÇÃO DE DIVERGENCIA DE NFE
    public List<Usuario> readConfigNtfy() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> Usuario = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT cod, tbusuarios_codUser, r_email_nf_compras, r_email_nf_fiscal, u.emailUsuario, u.nomeUsuario FROM tbpre_user pre JOIN tbusuarios u on u.codUsuario = pre.tbusuarios_codUser WHERE r_email_nf_compras != 0 || r_email_nf_fiscal != 0");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setFr_codConfig(rs.getInt("cod"));
                u.setFr_rEmailNfeCompras(rs.getInt("r_email_nf_compras"));
                u.setFr_rEmailNfeFiscal(rs.getInt("r_email_nf_fiscal"));
                u.setEmailUsuario(rs.getString("emailUsuario"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                Usuario.add(u);
            }

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("ler configurações de notificação - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Usuario;

    }
}
