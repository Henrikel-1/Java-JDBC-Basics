package dao;

import DB.ConnectionFactory;
import model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PessoaDAO {

    // CREATE
    public int inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getIdade());

            ps.executeUpdate();

            // pega o ID gerado (AUTO_INCREMENT)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    pessoa.setId(idGerado);
                    return idGerado;
                }
            }
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir: " + e.getMessage(), e);
        }
    }

    // READ (ALL)
    public List<Pessoa> listar() {
        String sql = "SELECT id, nome, idade FROM pessoa ORDER BY id";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pessoas.add(new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("idade")
                ));
            }

            return pessoas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar: " + e.getMessage(), e);
        }
    }

    // READ (BY ID)
    public Optional<Pessoa> buscarPorId(int id) {
        String sql = "SELECT id, nome, idade FROM pessoa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Pessoa(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("idade")
                    ));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscarPorId: " + e.getMessage(), e);
        }
    }

    // UPDATE
    public int atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getIdade());
            ps.setInt(3, pessoa.getId());

            return ps.executeUpdate(); // retorna quantas linhas foram alteradas

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    // DELETE
    public int removerPorId(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate(); // retorna quantas linhas foram removidas

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao removerPorId: " + e.getMessage(), e);
        }
    }
}