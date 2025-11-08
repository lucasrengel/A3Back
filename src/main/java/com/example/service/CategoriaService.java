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
}
