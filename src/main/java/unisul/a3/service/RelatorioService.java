package unisul.a3.service;

import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.RelatorioListaPreco;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    public RelatorioListaPreco getListaPreco() {
        List<RelatorioListaPreco.ItemListaPreco> itens = new ArrayList<>();
        String sql = "SELECT p.nome, p.preco, p.unidade, c.nome as nomeCategoria FROM produto p LEFT JOIN categoria c ON p.categoriaId = c.id ORDER BY p.nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                itens.add(new RelatorioListaPreco.ItemListaPreco(
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("unidade"),
                    rs.getString("nomeCategoria")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório de lista de preços: " + e.getMessage(), e);
        }
        return new RelatorioListaPreco(itens);
    }
}
