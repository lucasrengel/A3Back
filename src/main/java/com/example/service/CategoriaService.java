package com.example.service;

import com.example.config.DatabaseConnection;
import com.example.model.Categoria;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    public void adicionar(Categoria c) {
        String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTamanho());
            stmt.setString(3, c.getEmbalagem());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
            System.out.println("Categoria adicionada: " + c.getNome());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar categoria: " + e.getMessage(), e);
        }
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nome, tamanho, embalagem FROM categoria";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("tamanho"),
                    rs.getString("embalagem")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar categorias: " + e.getMessage(), e);
        }
        return lista;
    }

    public Categoria buscar(long id) {
        String sql = "SELECT id, nome, tamanho, embalagem FROM categoria WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar categoria: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Categoria c) {
        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTamanho());
            stmt.setString(3, c.getEmbalagem());
            stmt.setLong(4, c.getId());
            stmt.executeUpdate();
            System.out.println("Categoria atualizada: " + c.getNome());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar categoria: " + e.getMessage(), e);
        }
    }

    public void remover(long id) {
        String sqlCheck = "SELECT COUNT(*) FROM produto WHERE categoriaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setLong(1, id);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Não é possível remover a categoria pois existem produtos vinculados a ela.");
                }
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar produtos da categoria: " + e.getMessage(), e);
        }

        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Categoria removida: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover categoria: " + e.getMessage(), e);
        }
    }
}
