import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
                "jdbc:h2:./meubanco",
                "sa",
                ""
        );

        System.out.println("Conectado com sucesso!");
        conn.close();
    }
}