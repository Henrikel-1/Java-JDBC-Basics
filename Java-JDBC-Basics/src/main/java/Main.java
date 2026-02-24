import model.Pessoa;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
                "jdbc:h2:./meubanco",
                "sa",
                ""
        );
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS pessoa (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nome VARCHAR(100) NOT NULL,\n" +
                "    idade INT NOT NULL\n" +
                ")");

        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        Pessoa pessoa = new Pessoa("luan", 20);
        ps.setString(1, pessoa.getName());
        ps.setInt(2, pessoa.getIdade());

        ps.executeUpdate();

        System.out.println("Pessoa inserida!");
        String sqls = "SELECT nome, idade\n" +
                "FROM pessoa\n" +
                "WHERE idade > 18";

        PreparedStatement pss = conn.prepareStatement(sqls);
        ResultSet rs = pss.executeQuery();

        while(rs.next()) {
            String nome = rs.getString("nome");
            int idade = rs.getInt("idade");
            System.out.println("Maior de idade:" + nome +", "+ idade);
        }

        stmt.close();
        conn.close();


    }
}