package DB;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInit {

    public static void createTables() {
        String sql = """
            CREATE TABLE IF NOT EXISTS pessoa (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                idade INT NOT NULL
            )
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            throw new RuntimeException("Erro criando tabela: " + e.getMessage(), e);
        }
    }
}