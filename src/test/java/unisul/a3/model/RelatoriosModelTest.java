package unisul.a3.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RelatoriosModelTest {

    @Test
    void deveTestarRelatorioBalanco() {

        RelatorioBalanco relatorio =
                new RelatorioBalanco();

        relatorio.setItens(new ArrayList<>());
        relatorio.setValorTotalEstoque(100.0);

        assertEquals(100.0,
                relatorio.getValorTotalEstoque());
    }

    @Test
    void deveTestarItemBalanco() {

        RelatorioBalanco.ItemBalanco item =
                new RelatorioBalanco.ItemBalanco(
                        "Produto",
                        10,
                        50.0
                );

        assertEquals("Produto",
                item.getNome());

        assertEquals(10,
                item.getQuantidade());

        assertEquals(50.0,
                item.getValorTotal());
    }

    @Test
    void deveTestarRelatorioListaPreco() {

        RelatorioListaPreco relatorio =
                new RelatorioListaPreco();

        relatorio.setItens(new ArrayList<>());

        assertNotNull(relatorio.getItens());
    }

    @Test
    void deveTestarItemListaPreco() {

        RelatorioListaPreco.ItemListaPreco item =
                new RelatorioListaPreco.ItemListaPreco(
                        "Coca",
                        8.5,
                        "UN",
                        "Bebidas"
                );

        assertEquals("Coca",
                item.getNome());

        assertEquals("Bebidas",
                item.getNomeCategoria());
    }

    @Test
    void deveTestarRelatorioProdutosPorCategoria() {

        RelatorioProdutosPorCategoria relatorio =
                new RelatorioProdutosPorCategoria();

        assertNotNull(relatorio.getItens());
    }

    @Test
    void deveTestarItemCategoria() {

        RelatorioProdutosPorCategoria.ItemCategoria item =
                new RelatorioProdutosPorCategoria.ItemCategoria(
                        "Bebidas",
                        10
                );

        assertEquals("Bebidas",
                item.getNomeCategoria());

        assertEquals(10,
                item.getQuantidade());
    }

    @Test
    void deveTestarRelatorioMaioresMovimentacoes() {

        Movimentacao entrada =
                new Movimentacao(
                        1L,
                        "2025-01-01",
                        10,
                        "ENTRADA"
                );

        Movimentacao saida =
                new Movimentacao(
                        1L,
                        "2025-01-01",
                        5,
                        "SAIDA"
                );

        RelatorioMaioresMovimentacoes relatorio =
                new RelatorioMaioresMovimentacoes();

        relatorio.setMaiorEntrada(entrada);
        relatorio.setMaiorSaida(saida);

        assertNotNull(relatorio.getMaiorEntrada());
        assertNotNull(relatorio.getMaiorSaida());
    }

    @Test
    void deveTestarSettersItemListaPreco() {

        RelatorioListaPreco.ItemListaPreco item =
                new RelatorioListaPreco.ItemListaPreco();

        item.setNome("Coca");

        item.setPreco(10.5);

        item.setUnidade("UN");

        item.setNomeCategoria("Bebidas");

        assertEquals("Coca", item.getNome());

        assertEquals(10.5, item.getPreco());

        assertEquals("UN", item.getUnidade());

        assertEquals("Bebidas", item.getNomeCategoria());
    }

    @Test
    void deveTestarSettersItemBalanco() {

        RelatorioBalanco.ItemBalanco item =
                new RelatorioBalanco.ItemBalanco();

        item.setNome("Produto");

        item.setQuantidade(5);

        item.setValorTotal(50);

        assertEquals("Produto", item.getNome());

        assertEquals(5, item.getQuantidade());

        assertEquals(50, item.getValorTotal());
    }

    @Test
    void deveTestarSettersItemCategoria() {

        RelatorioProdutosPorCategoria.ItemCategoria item =
                new RelatorioProdutosPorCategoria.ItemCategoria();

        item.setNomeCategoria("Bebidas");

        item.setQuantidade(10);

        assertEquals(
                "Bebidas",
                item.getNomeCategoria()
        );

        assertEquals(
                10,
                item.getQuantidade()
        );
    }

    @Test
    void deveTestarSettersRelatorioProdutosPorCategoria() {

        RelatorioProdutosPorCategoria relatorio =
                new RelatorioProdutosPorCategoria();

        relatorio.setItens(new ArrayList<>());

        assertNotNull(
                relatorio.getItens()
        );
    }

    @Test
    void deveTestarConstrutorRelatorioMaioresMovimentacoes() {

        Movimentacao entrada =
                new Movimentacao();

        Movimentacao saida =
                new Movimentacao();

        RelatorioMaioresMovimentacoes relatorio =
                new RelatorioMaioresMovimentacoes(
                        entrada,
                        saida
                );

        assertNotNull(
                relatorio.getMaiorEntrada()
        );

        assertNotNull(
                relatorio.getMaiorSaida()
        );
    }
}