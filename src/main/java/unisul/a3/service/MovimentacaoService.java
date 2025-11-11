package unisul.a3.service;

import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.Movimentacao;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovimentacaoService {

    public String registrar(Movimentacao m) {
        String selectSql = "SELECT nome, quantidadeEstoque, quantidadeMinima, quantidadeMaxima FROM produto WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE produto SET quantidadeEstoque = ? WHERE id = ?";
        String insertSql = "INSERT INTO movimentacao (produtoId, tipo, quantidade, dataOperacao) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setLong(1, m.getProdutoId());
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return "ERRO: Produto com ID " + m.getProdutoId() + " não encontrado.";
                        }

                        String nome = rs.getString("nome");
                        int quantidadeEstoque = rs.getInt("quantidadeEstoque");
                        int quantidadeMinima = rs.getInt("quantidadeMinima");
                        int quantidadeMaxima = rs.getInt("quantidadeMaxima");

                        int novoEstoque = quantidadeEstoque;
                        StringBuilder aviso = new StringBuilder("Movimentação registrada.");

                        if ("SAIDA".equalsIgnoreCase(m.getTipo())) {
                            if (quantidadeEstoque < m.getQuantidade()) {
                                conn.rollback();
                                return "ERRO: Estoque insuficiente para " + nome + ".";
                            }
                            novoEstoque = quantidadeEstoque - m.getQuantidade();
                            if (novoEstoque < quantidadeMinima) {
                                aviso.append("\nAVISO: Produto '").append(nome).append("' abaixo do mínimo.");
                            }
                        } else if ("ENTRADA".equalsIgnoreCase(m.getTipo())) {
                            novoEstoque = quantidadeEstoque + m.getQuantidade();
                            if (novoEstoque > quantidadeMaxima) {
                                aviso.append("\nAVISO: Produto '").append(nome).append("' acima do máximo.");
                            }
                        } else {
                            conn.rollback();
                            return "ERRO: Tipo de movimentação desconhecido.";
                        }

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, novoEstoque);
                            updateStmt.setLong(2, m.getProdutoId());
                            updateStmt.executeUpdate();
                        }

                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setLong(1, m.getProdutoId());
                            insertStmt.setString(2, m.getTipo());
                            insertStmt.setInt(3, m.getQuantidade());
                            insertStmt.executeUpdate();
                        }

                        conn.commit();
                        System.out.println(aviso.toString().replace("\n", " "));
                        return aviso.toString();
                    }
                }
            } catch (Exception e) {
                try { conn.rollback(); } catch (Exception ex) { /* ignore */ }
                throw new RuntimeException("Erro ao registrar movimentação: " + e.getMessage(), e);
            } finally {
                try { conn.setAutoCommit(true); } catch (Exception ex) { /* ignore */ }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar movimentação: " + e.getMessage(), e);
        }
    }

    public List<Movimentacao> listar() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT id, produtoId, tipo, quantidade, DATE_FORMAT(dataOperacao, '%Y-%m-%d') as data FROM movimentacao ORDER BY dataOperacao DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Movimentacao(
                    rs.getLong("id"),
                    rs.getLong("produtoId"),
                    rs.getString("data"),
                    rs.getInt("quantidade"),
                    rs.getString("tipo")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar movimentações: " + e.getMessage(), e);
        }
        return lista;
    }

    public Movimentacao buscar(long id) {
        String sql = "SELECT id, produtoId, tipo, quantidade, DATE_FORMAT(dataOperacao, '%Y-%m-%d') as data FROM movimentacao WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Movimentacao(
                        rs.getLong("id"),
                        rs.getLong("produtoId"),
                        rs.getString("data"),
                        rs.getInt("quantidade"),
                        rs.getString("tipo")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar movimentação: " + e.getMessage(), e);
        }
        return null;
    }
}
