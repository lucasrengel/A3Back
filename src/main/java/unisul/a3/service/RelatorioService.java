package unisul.a3.service;

import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.RelatorioListaPreco;
import unisul.a3.model.RelatorioBalanco;
import unisul.a3.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private ProdutoService produtoService;

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

    public RelatorioBalanco getBalanco() {
        List<RelatorioBalanco.ItemBalanco> itens = new ArrayList<>();
        double valorTotalEstoque = 0;
        String sql = "SELECT nome, quantidadeEstoque, preco FROM produto ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                double valorTotalProduto = rs.getDouble("preco") * rs.getInt("quantidadeEstoque");
                itens.add(new RelatorioBalanco.ItemBalanco(
                    rs.getString("nome"),
                    rs.getInt("quantidadeEstoque"),
                    valorTotalProduto
                ));
                valorTotalEstoque += valorTotalProduto;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório de balanço: " + e.getMessage(), e);
        }
        return new RelatorioBalanco(itens, valorTotalEstoque);
    }

    public List<Produto> getAbaixoMinimo() {
        return produtoService.listarAbaixoMinimo();
    }
}
