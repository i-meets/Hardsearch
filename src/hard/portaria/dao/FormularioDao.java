package hard.portaria.dao;

import hard.config.ConnectionCloudFactory;
import hard.home.view.FDErroOcorrido;
import hard.portaria.model.Formulario;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormularioDao {

    FDErroOcorrido fdErroOcorrido;

    //LISTA TODOS FUNCIONÁRIOS
    public List<Formulario> readFormulario(String codFun, String nomeFun) throws ClassNotFoundException, SQLException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Formulario> Formulario = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT fo.codFormulario, fu.codFuncionario, fu.nomeFuncionario, fu.setorFuncionario, "
                    + "cast(AES_DECRYPT(fu.cpfFuncionario,'b4GQLsRyaDfM') as char) as cpfFuncionario,"
                    + "fo.sintomas_covid, fo.contato_covid, fo.febre, fo.tosse, fo.d_respirar, fo.calafrios, fo.dor_muscular,"
                    + "fo.dor_garganta, fo.p_olfato, fo.nausea, fo.dor_cabeca, fo.data_sintoma, fo.2_metros, fo.data_p, fo.status "
                    + "FROM formulario fo JOIN funcionario fu ON fu.codFuncionario = fo.funcionario_codFuncionario WHERE codFuncionario LIKE ? AND fu.nomeFuncionario LIKE ? AND fo.status = 0 AND fo.data_p = DATE(NOW())");
            stmt.setString(1, codFun + "%");
            stmt.setString(2, "%" + nomeFun + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Formulario fo = new Formulario();
                fo.setCodFormulario(rs.getInt("codFormulario"));
                fo.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fo.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fo.setSintomas(rs.getInt("sintomas_covid"));
                fo.setContato(rs.getInt("contato_covid"));
                fo.setFebre(rs.getInt("febre"));
                fo.setTosse(rs.getInt("tosse"));
                fo.setD_respirar(rs.getInt("d_respirar"));
                fo.setCalafrios(rs.getInt("calafrios"));
                fo.setDor_muscular(rs.getInt("dor_muscular"));
                fo.setDor_garganta(rs.getInt("dor_garganta"));
                fo.setP_olfato(rs.getInt("p_olfato"));
                fo.setNausea(rs.getInt("nausea"));
                fo.setDor_cabeca(rs.getInt("dor_cabeca"));
                fo.setData_sintoma(rs.getDate("data_sintoma"));
                fo.setMetros_2(rs.getInt("2_metros"));
                fo.setData_p(rs.getDate("data_p"));
                fo.setStatus(rs.getInt("status"));

                Formulario.add(fo);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar formulário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionCloudFactory.closeConnection(con, stmt, rs);
        }
        return Formulario;
    }

    public List<Formulario> listFormulario(String codFun, String nomeFun, Date inicial, Date fin) throws ClassNotFoundException, SQLException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Formulario> Formulario = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT fo.codFormulario, fu.codFuncionario, fu.nomeFuncionario, fu.setorFuncionario, "
                    + "cast(AES_DECRYPT(fu.cpfFuncionario,'b4GQLsRyaDfM') as char) as cpfFuncionario,"
                    + "fo.sintomas_covid, fo.contato_covid, fo.febre, fo.tosse, fo.d_respirar, fo.calafrios, fo.dor_muscular,"
                    + "fo.dor_garganta, fo.p_olfato, fo.nausea, fo.dor_cabeca, fo.data_sintoma, fo.2_metros, fo.data_p, fo.status, fo.temp "
                    + "FROM formulario fo JOIN funcionario fu ON fu.codFuncionario = fo.funcionario_codFuncionario WHERE codFuncionario LIKE ? AND fu.nomeFuncionario LIKE ? AND fo.data_p BETWEEN ? AND ?");
            stmt.setString(1, codFun + "%");
            stmt.setString(2, "%" + nomeFun + "%");
            stmt.setDate(3, inicial);
            stmt.setDate(4, fin);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Formulario fo = new Formulario();
                fo.setCodFormulario(rs.getInt("codFormulario"));
                fo.setFr_codFuncionario(rs.getInt("codFuncionario"));
                fo.setFr_nomeFuncionario(rs.getString("nomeFuncionario"));
                fo.setFr_setorFuncionario(rs.getString("setorFuncionario"));
                fo.setSintomas(rs.getInt("sintomas_covid"));
                fo.setContato(rs.getInt("contato_covid"));
                fo.setFebre(rs.getInt("febre"));
                fo.setTosse(rs.getInt("tosse"));
                fo.setD_respirar(rs.getInt("d_respirar"));
                fo.setCalafrios(rs.getInt("calafrios"));
                fo.setDor_muscular(rs.getInt("dor_muscular"));
                fo.setDor_garganta(rs.getInt("dor_garganta"));
                fo.setP_olfato(rs.getInt("p_olfato"));
                fo.setNausea(rs.getInt("nausea"));
                fo.setDor_cabeca(rs.getInt("dor_cabeca"));
                fo.setData_sintoma(rs.getDate("data_sintoma"));
                fo.setMetros_2(rs.getInt("2_metros"));
                fo.setData_p(rs.getDate("data_p"));
                fo.setStatus(rs.getInt("status"));
                fo.setTemp(rs.getDouble("temp"));

                Formulario.add(fo);
            }
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("listar formulário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionCloudFactory.closeConnection(con, stmt, rs);
        }
        return Formulario;
    }

    public void updateFormulario(Formulario d) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE formulario SET status = ?, temp = ? WHERE codFormulario = ?");
            stmt.setInt(1, d.getStatus());
            stmt.setDouble(2, d.getTemp());
            stmt.setInt(3, d.getCodFormulario());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("atualizar status formulario - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);

        } finally {
            ConnectionCloudFactory.closeConnection(con, stmt);
        }
    }

    //DELETA
    public void delete(Formulario f) throws ClassNotFoundException {
        java.sql.Connection con = ConnectionCloudFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM formulario WHERE codFormulario = ?");
            stmt.setInt(1, f.getCodFormulario());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            fdErroOcorrido = new FDErroOcorrido(null, true);
            fdErroOcorrido.LbInformaErro.setText("excluír formulário - d48");
            FDErroOcorrido.TaCodigoErro.setText(ex.toString());
            fdErroOcorrido.setVisible(true);
        } finally {
            ConnectionCloudFactory.closeConnection(con, stmt);
        }
    }

}
