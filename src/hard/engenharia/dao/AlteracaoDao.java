package hard.engenharia.dao;

import hard.config.ConnectionFactory;
import hard.engenharia.model.Alteracao;
import hard.engenharia.model.Arquivos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlteracaoDao {

    public void createAlteracao(Alteracao i) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO alteracao_rev (cod, item_cod, tbempresas_codEmp, rev_interna_a, data_rev_interna_a, rev_cliente_a, data_rev_cliente_a, tipo_revisao, possui_extencao, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, i.getCod());
            stmt.setInt(2, i.getFr_item_cod());
            stmt.setInt(3, i.getFr_codEmpresa());
            stmt.setInt(4, i.getRevInterna_a());
            stmt.setDate(5, i.getDataRevInterna_a());
            stmt.setString(6, i.getRevCliente_a());
            stmt.setDate(7, i.getDataRevCliente_a());
            stmt.setInt(8, i.getTipoRevisao());
            stmt.setInt(9, i.getPossuiExtencao());
            stmt.setInt(10, i.getStatus());
            stmt.executeUpdate();
        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }


    public List<Alteracao> readMaxCod() throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Alteracao> Alteracao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT Max(cod) FROM alteracao_rev");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Alteracao co = new Alteracao();
                co.setCod(rs.getInt("Max(cod)") + 1);
                Alteracao.add(co);
            }
        } catch (SQLException ex) {
//            fdErroOcorrido = new FDErroOcorrido(null, true);
//            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
//            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
//            fdErroOcorrido.setVisible(true);
            System.out.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Alteracao;
    }

    /**
     *
     * @param codAlte
     * @param codSoli
     * @return
     * @throws ClassNotFoundException
     */
    public List<Alteracao> readAlteracaoForCod(int codAlte, int codSoli) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Alteracao> Alteracao = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT i.cod, i.cod_item, i.descricao, al.rev_interna_a, al.data_rev_interna_a, i.responsavel, e.codEmp, e.nomeEmp,  al.rev_cliente_a, al.data_rev_cliente_a, al.status  FROM alteracao_rev al JOIN item i ON i.cod = al.item_cod JOIN solicita_alteracao s ON s.alteracao_rev_cod = al.cod JOIN tbempresas e ON e.codEmp = al.tbempresas_codEmp WHERE al.cod = ? AND s.cod = ?;");
            stmt.setInt(1, codAlte);
            stmt.setInt(2, codSoli);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Alteracao a = new Alteracao();
                a.setFr_item_cod(rs.getInt("cod"));//CODIGO DO ITEM INTERNO DO SISTEMA
                a.setFr_codItem(rs.getString("cod_item")); //CODIGO DO ITEM DA IMAC
                a.setFr_descricaoItem(rs.getString("descricao"));
                a.setRevInterna_a(rs.getInt("rev_interna_a"));
                a.setDataRevInterna_a(rs.getDate("data_rev_interna_a"));
                a.setResponsavel(rs.getString("responsavel"));
                a.setFr_codEmpresa(rs.getInt("codEmp"));
                a.setFr_nomeEmpresa(rs.getString("nomeEmp"));
                a.setRevCliente_a(rs.getString("rev_cliente_a"));
                a.setDataRevCliente_a(rs.getDate("data_rev_cliente_a"));
                a.setStatus(rs.getInt("status"));
                Alteracao.add(a);
            }
        } catch (SQLException ex) {
//            fdErroOcorrido = new FDErroOcorrido(null, true);
//            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
//            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
//            fdErroOcorrido.setVisible(true);
            System.out.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Alteracao;
    }

    public void saveArqAltera(Arquivos a) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO arquivos_alteracao (cod, tipo_arquivo_cod, link, alteracao_rev_cod) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, a.getCodArquivo());
            stmt.setInt(2, a.getFr_tipoArquivoCod());
            stmt.setString(3, a.getLink());
            stmt.setInt(4, a.getFr_alteracaoRevCod());
            stmt.executeUpdate();
        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

    /**
     *
     * @param codAlte
     * @return
     * @throws ClassNotFoundException
     */
    public List<Arquivos> readArquiForAltera(int codAlte) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Arquivos> Arquivos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT a.cod, t.descricao, a.link FROM arquivos_alteracao a JOIN tipo_arquivo t ON t.cod = a.tipo_arquivo_cod WHERE alteracao_rev_cod = ?;");
            stmt.setInt(1, codAlte);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Arquivos a = new Arquivos();
                a.setCodArquivo(rs.getInt("cod"));
                a.setDescricao(rs.getString("descricao"));
                a.setLink(rs.getString("link"));
                Arquivos.add(a);
            }
        } catch (SQLException ex) {
//            fdErroOcorrido = new FDErroOcorrido(null, true);
//            fdErroOcorrido.LbInformaErro.setText("listar coparticipações - d48");
//            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
//            fdErroOcorrido.setVisible(true);
            System.out.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return Arquivos;
    }

    /**
     *
     * @param a
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void saveTipoArq(Arquivos a) throws ClassNotFoundException, SQLException {

        java.sql.Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tipo_arquivo (cod, descricao) VALUES (?, ?);");
            stmt.setInt(1, a.getCodTipo());
            stmt.setString(2, a.getDescricao());
            stmt.executeUpdate();
        } catch (SQLException ex) {

            System.out.println("Erro: " + ex);

        } finally {
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);
        }
    }

}
