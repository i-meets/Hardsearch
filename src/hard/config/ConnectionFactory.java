package hard.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConnectionFactory {

    public void connection() {

    }

    public static Connection con;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "";
    private static final String PASS = "";

    public static Connection getConnection() throws ClassNotFoundException {

        String arq = "C:\\Hardsearch\\config\\map.ini";
        File f = new File(arq);
        String database = null;

        if (!f.exists()) {

            int input = JOptionPane.showConfirmDialog(null,
                    "Não foi encontrado arquivo de mapeamento do banco de dados,\n\nDeseja criar o mapemanteo agora?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (input == 0) {

                String criaArq = "C:\\Hardsearch\\config\\map.ini";

                String texto = JOptionPane.showInputDialog(null, "Informe o novo IP do servidor onde o db se encontra:", "Configuração de Mapeamento db_dp", JOptionPane.WARNING_MESSAGE);

                if (texto != null) {
                    if (Config.Write(criaArq, texto)) {

                        String conteudo = Config.Read(arq);
                        database = conteudo.split(" ")[0];

                        if (arq.isEmpty()) {

                            System.out.println("erro ao ler arquivo");

                        } else {

                        }

                    } else {
                        System.out.println("erro");
                    }
                } else {
                    System.out.println("cancelado");
                }

            } else {
                
                database = "banco_de_dados_teste";
            }

        } else {

            String conteudo = Config.Read(arq);
            database = conteudo.split(":")[0];
            if (arq.isEmpty()) {

                System.out.println("erro ao ler arquivo");

            } else {

            }
        }

        try {
            System.setProperty("jdbc.Drivers", DRIVER); //CASO  OPTAR POR UTILIZAR SSL, REMOVER O CÓDIGO ?USERTIMEZO...ETC
            con = DriverManager.getConnection("jdbc:mysql://" + database + ":3306/db_dp" + "?useTimezone=true&serverTimezone=UTC&useSSL=false", USER, PASS);

        } catch (SQLException ex) {
            String erro = ex.toString();
            if (erro.contains("Communications link failure")) {
                JOptionPane.showMessageDialog(null, "Servidor não encontrado, verifice se o mesmo está online, ou se o IP do servidor está correto em C:/Hardsearch/config/map.ini", "Erro, servidor sem comunicação", JOptionPane.ERROR_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(null, "Atenção! \nHá um problema na conexão com o banco de dados, contate administrador do sistema.\n" + "Erro:  " + ex.getMessage());
            }
            System.out.println(ex.getMessage());
            int input = JOptionPane.showConfirmDialog(null,
                    "O servidor não responde, deseja tentar conectar novamente?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == 0) {
                getConnection();
            } else {
                JOptionPane.showMessageDialog(null, "Sistema precisa ser fechado", "Atenção", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
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
