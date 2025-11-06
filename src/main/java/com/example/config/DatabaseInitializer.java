package com.example.config;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS categoria (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "tamanho VARCHAR(50)," +
                            "embalagem VARCHAR(100)" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS produto (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "preco DOUBLE NOT NULL," +
                            "unidade VARCHAR(10)," +
                            "quantidadeEstoque INT," +
                            "quantidadeMinima INT," +
                            "quantidadeMaxima INT," +
                            "categoriaId BIGINT," +
                            "FOREIGN KEY (categoriaId) REFERENCES categoria(id)" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS movimentacao (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "produtoId BIGINT NOT NULL," +
                            "tipo VARCHAR(20) NOT NULL," +
                            "quantidade INT NOT NULL," +
                            "dataOperacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "FOREIGN KEY (produtoId) REFERENCES produto(id)" +
                            ")"
            );

            stmt.executeUpdate("INSERT IGNORE INTO categoria (id, nome, tamanho, embalagem) VALUES (1, 'Enlatados', 'Médio', 'Lata')");
            stmt.executeUpdate("INSERT IGNORE INTO categoria (id, nome, tamanho, embalagem) VALUES (2, 'Limpeza', 'Grande', 'Plástico')");
            stmt.executeUpdate("INSERT IGNORE INTO categoria (id, nome, tamanho, embalagem) VALUES (3, 'Vegetais', 'Pequeno', 'Vidro')");

            stmt.executeUpdate("INSERT IGNORE INTO produto (id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (1, 'Milho em Lata', 5.50, 'UN', 50, 20, 100, 1)");
            stmt.executeUpdate("INSERT IGNORE INTO produto (id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (2, 'Ervilha em Lata', 4.80, 'UN', 30, 20, 100, 1)");
            stmt.executeUpdate("INSERT IGNORE INTO produto (id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (3, 'Detergente', 2.99, 'UN', 15, 5, 50, 2)");
            stmt.executeUpdate("INSERT IGNORE INTO produto (id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (4, 'Alface', 3.50, 'UN', 10, 2, 20, 3)");
            stmt.executeUpdate("INSERT IGNORE INTO produto (id, nome, preco, unidade, quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoriaId) VALUES (5, 'Cenoura', 2.00, 'UN', 25, 5, 40, 3)");

            stmt.executeUpdate("INSERT IGNORE INTO movimentacao (id, produtoId, tipo, quantidade, dataOperacao) VALUES (1, 1, 'SAIDA', 5, '2025-11-01')");
            stmt.executeUpdate("INSERT IGNORE INTO movimentacao (id, produtoId, tipo, quantidade, dataOperacao) VALUES (2, 2, 'ENTRADA', 10, '2025-11-02')");
            stmt.executeUpdate("INSERT IGNORE INTO movimentacao (id, produtoId, tipo, quantidade, dataOperacao) VALUES (3, 3, 'SAIDA', 2, '2025-11-03')");
            stmt.executeUpdate("INSERT IGNORE INTO movimentacao (id, produtoId, tipo, quantidade, dataOperacao) VALUES (4, 4, 'ENTRADA', 8, '2025-11-04')");
            stmt.executeUpdate("INSERT IGNORE INTO movimentacao (id, produtoId, tipo, quantidade, dataOperacao) VALUES (5, 5, 'SAIDA', 3, '2025-11-05')");

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar o banco: " + e.getMessage(), e);
        }
    }
}
