package hard.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConnectionCloudFactory {

    public void connection() {

    }

    public static Connection con;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASS = "";

    public static Connection getConnection() throws ClassNotFoundException {

        try {
            System.setProperty("jdbc.Drivers", DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException ex) {
            String erro = ex.toString();
            if (erro.contains("Communications link failure")) {
                JOptionPane.showMessageDialog(null, "Servidor não encontrado, verifice se o mesmo está online, ou se o IP do servidor está correto em C:/Hardsearch/config/map.ini", "Erro, servidor sem comunicação", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Atenção! \nHá um problema na conexão com o banco de dados, contate administrador do sistema.\n" + "Erro:  " + ex.getMessage());
            }

        }

        return con;

    }

    public static Connection closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
