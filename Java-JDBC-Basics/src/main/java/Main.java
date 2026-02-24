import dao.PessoaDAO;
import DB.DatabaseInit;
import model.Pessoa;

public class Main {
    public static void main(String[] args) {

        DatabaseInit.createTables();

        PessoaDAO dao = new PessoaDAO();

        // CREATE
        Pessoa p1 = new Pessoa("Luan", 20);
        int id1 = dao.inserir(p1);
        System.out.println("Inserido: " + p1);

        // READ (ALL)
        System.out.println("\nLista:");
        dao.listar().forEach(System.out::println);

        // READ (BY ID)
        System.out.println("\nBuscar por ID " + id1 + ":");
        System.out.println(dao.buscarPorId(id1).orElse(null));

        // UPDATE
        p1.setNome("Luan Atualizado");
        p1.setIdade(21);
        dao.atualizar(p1);
        System.out.println("\nDepois do update:");
        dao.listar().forEach(System.out::println);

        // DELETE
        dao.removerPorId(id1);
        System.out.println("\nDepois do delete:");
        dao.listar().forEach(System.out::println);
    }
}