import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String NOME_BANCO = "ods11_maringa";
    private static final String HOST = "127.0.0.1";
    private static final String PORTA = "3306";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + NOME_BANCO +
            "?useSSL=false&serverTimezone=UTC";

    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados MySQL: " + e.getMessage());
            throw new RuntimeException("Falha na conexão com o MySQL", e);
        }
    }
}