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
}
