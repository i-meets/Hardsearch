package hard.home.dao;

import hard.compra.view.FrMenuCom;
import hard.config.ConnectionFactory;
import hard.engenharia.view.FrMenuEng;
import hard.home.model.Permissao;
import hard.home.view.FDErroOcorrido;
import hard.home.view.FrMenu;
import hard.portaria.view.FrMenuPT;
import hard.rh.view.FrMenuDP;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissaoDao {

    FDErroOcorrido fdErroOcorrido;

    public List<Permissao> listTela(int codUser, String codTela) throws ClassNotFoundException, SQLException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Permissao> Permissao = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM acesso_user WHERE tbusuarios_codUsuario = ? AND tbtelas_codTela LIKE ?");
            stmt.setInt(1, codUser);
            stmt.setString(2, "%" + codTela + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Permissao pe = new Permissao();
                pe.setCod(rs.getInt("cod"));
                pe.setFr_codTela(rs.getString("tbtelas_codTela"));
                pe.setPermite(rs.getBoolean("permite_acesso"));
                Permissao.add(pe);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar permissões permissões - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Permissao;
    }

    public void createPermissao(Permissao p) throws ClassNotFoundException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO acesso_user (cod, tbtelas_codTela, tbusuarios_codUsuario, permite_acesso) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, p.getCod());
            stmt.setString(2, p.getFr_codTela());
            stmt.setInt(3, p.getFr_codUser());
            stmt.setBoolean(4, p.isPermite());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("cadastrar permissão - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    public boolean checkCreatePermissao(String tela, int codFuncio) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM acesso_user WHERE tbtelas_codTela = ? AND tbusuarios_codUsuario = ?");
            stmt.setString(1, tela);
            stmt.setInt(2, codFuncio);
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("validar permissões - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public void updatePermissao(Permissao p) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE acesso_user SET permite_acesso = ? WHERE tbtelas_codTela = ? AND tbusuarios_codUsuario = ?");
            stmt.setBoolean(1, p.isPermite());
            stmt.setString(2, p.getFr_codTela());
            stmt.setInt(3, p.getFr_codUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar as permissões - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<FrMenu> listAcessFrMenu(int codUser) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FrMenu> FrMenu = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT permite_acesso as FrcadUsuario \n"
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrPermissoes' AND tbusuarios_codUsuario = ?) as FrPermissoes\n"
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrMenuDP' AND tbusuarios_codUsuario = ?) as FrMenuDP\n"
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrMenuPT' AND tbusuarios_codUsuario = ?) as FrMenuPT\n"
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrMenuCom' AND tbusuarios_codUsuario = ?) as FrMenuCom\n"
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrAdmin' AND tbusuarios_codUsuario = ?) as FrAdmin\n "
                    + ",(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrMenuEng' AND tbusuarios_codUsuario = ?) as FrMenuEng\n"
                    + "FROM acesso_user WHERE  tbtelas_codTela LIKE 'FrcadUsuario' AND tbusuarios_codUsuario = ?");
            stmt.setInt(1, codUser);
            stmt.setInt(2, codUser);
            stmt.setInt(3, codUser);
            stmt.setInt(4, codUser);
            stmt.setInt(5, codUser);
            stmt.setInt(6, codUser);
            stmt.setInt(7, codUser);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FrMenu mn = new FrMenu();
                mn.setFrcadUsuario(rs.getBoolean("FrcadUsuario"));
                mn.setFrPermissoes(rs.getBoolean("FrPermissoes"));
                mn.setFrMenuDP(rs.getBoolean("FrMenuDP"));
                mn.setFrMenuPT(rs.getBoolean("FrMenuPT"));
                mn.setFrMenuCom(rs.getBoolean("FrMenuCom"));
                mn.setFrAdmin(rs.getBoolean("FrAdmin"));
                mn.setFrMenuEng(rs.getBoolean("FrMenuEng"));

                FrMenu.add(mn);

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar permissões de acesso MENU - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return FrMenu;
    }

    public List<FrMenuDP> listAcessFrMenuDP(int codUser) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FrMenuDP> FrMenuDP = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT permite_acesso as FrcadFuncionario,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrcadProcedi' AND tbusuarios_codUsuario = ?) as FrcadProcedi,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrcadEventos' AND tbusuarios_codUsuario = ?) as FrcadEventos,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrcadEmpresa' AND tbusuarios_codUsuario = ?) as FrcadEmpresa,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContParticipacao' AND tbusuarios_codUsuario = ?) as FrContParticipacao,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContContrato' AND tbusuarios_codUsuario = ?) as FrContContrato,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContCotista' AND tbusuarios_codUsuario = ?) as FrContCotista,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrGeraFolha' AND tbusuarios_codUsuario = ?) as FrGeraFolha,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrImportaCopart' AND tbusuarios_codUsuario = ?) as FrImportaCopart,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadIntegracao' AND tbusuarios_codUsuario = ?) as FrCadIntegracao,\n"
                    + "(SELECT permite_acesso  FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContTesteCovid' AND tbusuarios_codUsuario = ?) as FrContTesteCovid FROM acesso_user WHERE  tbtelas_codTela LIKE 'FrcadFuncionario' AND tbusuarios_codUsuario = ?");
            stmt.setInt(1, codUser);
            stmt.setInt(2, codUser);
            stmt.setInt(3, codUser);
            stmt.setInt(4, codUser);
            stmt.setInt(5, codUser);
            stmt.setInt(6, codUser);
            stmt.setInt(7, codUser);
            stmt.setInt(8, codUser);
            stmt.setInt(9, codUser);
            stmt.setInt(10, codUser);
            stmt.setInt(11, codUser);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FrMenuDP dp = new FrMenuDP();
                dp.setFrcadFuncionario(rs.getBoolean("FrcadFuncionario"));
                dp.setFrcadProcedi(rs.getBoolean("FrcadProcedi"));
                dp.setFrcadEventos(rs.getBoolean("FrcadEventos"));
                dp.setFrcadEmpresa(rs.getBoolean("FrcadEmpresa"));
                dp.setFrContParticipacao(rs.getBoolean("FrContParticipacao"));
                dp.setFrContContrato(rs.getBoolean("FrContContrato"));
                dp.setFrContCotista(rs.getBoolean("FrContCotista"));
                dp.setFrGeraFolha(rs.getBoolean("FrGeraFolha"));
                dp.setFrImportaCopart(rs.getBoolean("FrImportaCopart"));
                dp.setFrCadIntegracao(rs.getBoolean("FrCadIntegracao"));
                dp.setFrContTesteCovid(rs.getBoolean("FrContTesteCovid"));

                FrMenuDP.add(dp);

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar permissões de acesso DP - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return FrMenuDP;
    }

    public List<FrMenuPT> listAcessFrMenuPT(int codUser) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FrMenuPT> FrMenuPT = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT permite_acesso as FrSolicitaSaida, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContSaida' AND tbusuarios_codUsuario = ?) as FrContSaida, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrContAcesso' AND tbusuarios_codUsuario = ?) as FrContAcesso, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrListaFormulario' AND tbusuarios_codUsuario = ?) as FrListaFormulario FROM acesso_user WHERE tbtelas_codTela LIKE 'FrSolicitaSaida' AND tbusuarios_codUsuario = ?");
            stmt.setInt(1, codUser);
            stmt.setInt(2, codUser);
            stmt.setInt(3, codUser);
            stmt.setInt(4, codUser);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FrMenuPT pt = new FrMenuPT();
                pt.setFrSolicitaSaida(rs.getBoolean("FrSolicitaSaida"));
                pt.setFrContSaida(rs.getBoolean("FrContSaida"));
                pt.setFrContAcesso(rs.getBoolean("FrContAcesso"));
                pt.setFrListaFormulario(rs.getBoolean("FrListaFormulario"));

                FrMenuPT.add(pt);

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar permissões de acesso PT - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return FrMenuPT;
    }

    public List<FrMenuCom> listAcessFrMenuCom(int codUser) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FrMenuCom> FrMenuCom = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT permite_acesso as FrCadEmpresa, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrRatingView' AND tbusuarios_codUsuario = ?) as FrRatingView, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadDivergeNFE' AND tbusuarios_codUsuario = ?) as FrCadDivergeNFE, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrConDivergeNFE' AND tbusuarios_codUsuario = ?) as FrConDivergeNFE FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadEmpresa' AND tbusuarios_codUsuario = ?");
            stmt.setInt(1, codUser);
            stmt.setInt(2, codUser);
            stmt.setInt(3, codUser);
            stmt.setInt(4, codUser);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FrMenuCom co = new FrMenuCom();
                co.setFrCadEmpresa(rs.getBoolean("FrCadEmpresa"));
                co.setFrRatingView(rs.getBoolean("FrRatingView"));
                co.setFrCadDivergeNFE(rs.getBoolean("FrCadDivergeNFE"));
                co.setFrConDivergeNFE(rs.getBoolean("FrConDivergeNFE"));

                FrMenuCom.add(co);

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar permissões de acesso COM - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return FrMenuCom;
    }

    public List<FrMenuEng> listAcessFrMenuEng(int codUser) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FrMenuEng> FrMenuEng = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT permite_acesso as FrCadItem, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadEmpresa' AND tbusuarios_codUsuario = ?) as FrCadEmpresa, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadMaquina' AND tbusuarios_codUsuario = ?) as FrCadMaquina, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadFerramenta' AND tbusuarios_codUsuario = ?) as FrCadFerramenta, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadSolicitaRev' AND tbusuarios_codUsuario = ?) as FrCadSolicitaRev, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrProcessaSolicita' AND tbusuarios_codUsuario = ?) as FrProcessaSolicita, (SELECT permite_acesso FROM acesso_user WHERE tbtelas_codTela LIKE 'FrOcorrenciasProd' AND tbusuarios_codUsuario = ?) as FrOcorrenciasProd FROM acesso_user WHERE tbtelas_codTela LIKE 'FrCadItem' AND tbusuarios_codUsuario = ?");
            stmt.setInt(1, codUser);
            stmt.setInt(2, codUser);
            stmt.setInt(3, codUser);
            stmt.setInt(4, codUser);
            stmt.setInt(5, codUser);
            stmt.setInt(6, codUser);
            stmt.setInt(7, codUser);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FrMenuEng eg = new FrMenuEng();
                eg.setFrCadItem(rs.getBoolean("FrCadItem"));
                eg.setFrCadEmpresa(rs.getBoolean("FrCadEmpresa"));
                eg.setFrCadMaquina(rs.getBoolean("FrCadMaquina"));
                eg.setFrCadFerramenta(rs.getBoolean("FrCadFerramenta"));
                eg.setFrCadSolicitaRev(rs.getBoolean("FrCadSolicitaRev"));
                eg.setFrProcessaSolicita(rs.getBoolean("FrProcessaSolicita"));
                eg.setFrOcorrenciasProd(rs.getBoolean("FrOcorrenciasProd"));

                FrMenuEng.add(eg);

            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("verificar permissões de acesso ENG - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
            System.out.println("erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return FrMenuEng;
    }
}
