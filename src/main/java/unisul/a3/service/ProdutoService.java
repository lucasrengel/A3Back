package unisul.a3.service;

import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.Produto;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    public void adicionar(Produto p) {
        String sql = "INSERT INTO produto (nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPrecoUnitario());
            stmt.setString(3, p.getUnidade());
            stmt.setInt(4, p.getQuantidadeEstoque());
            stmt.setInt(5, p.getQuantidadeMinima());
            stmt.setInt(6, p.getQuantidadeMaxima());
            stmt.setLong(7, p.getCategoriaId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getLong(1));
                }
            }
            System.out.println("Produto adicionado: " + p.getNome());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar produto: " + e.getMessage(), e);
        }
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco"));
                p.setUnidade(rs.getString("unidade"));
                p.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                p.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                p.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                p.setCategoriaId(rs.getLong("categoriaId"));
                produtos.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }

    public Produto buscarPorId(long id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPrecoUnitario(rs.getDouble("preco"));
                    p.setUnidade(rs.getString("unidade"));
                    p.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                    p.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                    p.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                    p.setCategoriaId(rs.getLong("categoriaId"));
                    return p;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, unidade = ?, quantidadeEstoque = ?, quantidadeMinima = ?, quantidadeMaxima = ?, categoriaId = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPrecoUnitario());
            stmt.setString(3, p.getUnidade());
            stmt.setInt(4, p.getQuantidadeEstoque());
            stmt.setInt(5, p.getQuantidadeMinima());
            stmt.setInt(6, p.getQuantidadeMaxima());
            stmt.setLong(7, p.getCategoriaId());
            stmt.setLong(8, p.getId());
            stmt.executeUpdate();
            System.out.println("Produto atualizado: " + p.getNome());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public void remover(long id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Produto removido: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover produto: " + e.getMessage(), e);
        }
    }

    public List<Produto> listarAbaixoMinimo() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId FROM produto WHERE quantidadeEstoque < quantidadeMinima";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco"));
                p.setUnidade(rs.getString("unidade"));
                p.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                p.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                p.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                p.setCategoriaId(rs.getLong("categoriaId"));
                lista.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos abaixo do mínimo: " + e.getMessage(), e);
        }
        return lista;
    }
}
