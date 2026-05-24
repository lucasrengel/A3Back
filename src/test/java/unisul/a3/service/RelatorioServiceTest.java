package unisul.a3.service;

import org.junit.jupiter.api.*;
import unisul.a3.config.DatabaseConnection;
import unisul.a3.config.DatabaseInitializer;
import unisul.a3.model.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioServiceTest {

    private RelatorioService relatorioService;
    private ProdutoService produtoService;
    private CategoriaService categoriaService;
    private MovimentacaoService movimentacaoService;

    @BeforeAll
    static void configurarBancoTeste() {
        System.setProperty(
                "db.url",
                "jdbc:mysql://localhost:3306/estoque_test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
        );
    }

    @BeforeEach
    void setup() throws Exception {

        DatabaseInitializer.initialize();

        relatorioService = new RelatorioService();
        produtoService = new ProdutoService();
        categoriaService = new CategoriaService();
        movimentacaoService = new MovimentacaoService();

        Field produtoField =
                RelatorioService.class.getDeclaredField("produtoService");
        produtoField.setAccessible(true);
        produtoField.set(relatorioService, produtoService);

        Field movimentacaoField =
                RelatorioService.class.getDeclaredField("movimentacaoService");
        movimentacaoField.setAccessible(true);
        movimentacaoField.set(relatorioService, movimentacaoService);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM movimentacao");
            stmt.executeUpdate("DELETE FROM produto");
            stmt.executeUpdate("DELETE FROM categoria");
        }
    }

    private long criarCategoria() {

        Categoria categoria =
                new Categoria("Bebidas", "1L", "Garrafa");

        categoriaService.adicionar(categoria);

        return categoria.getId();
    }

    private long criarProduto(
            String nome,
            double preco,
            int estoque,
            int minimo,
            int maximo) {

        long categoriaId = criarCategoria();

        Produto produto =
                new Produto(
                        nome,
                        preco,
                        "UN",
                        estoque,
                        minimo,
                        maximo,
                        categoriaId
                );

        produtoService.adicionar(produto);

        return produto.getId();
    }

    @Test
    void deveGerarListaPreco() {

        criarProduto("Coca-Cola", 10.0, 50, 5, 100);

        RelatorioListaPreco relatorio =
                relatorioService.getListaPreco();

        assertEquals(1, relatorio.getItens().size());
        assertEquals(
                "Coca-Cola",
                relatorio.getItens().get(0).getNome()
        );
    }

    @Test
    void deveGerarBalanco() {

        criarProduto("Produto A", 10.0, 5, 1, 100);

        RelatorioBalanco relatorio =
                relatorioService.getBalanco();

        assertEquals(50.0,
                relatorio.getValorTotalEstoque());
    }

    @Test
    void deveListarProdutosAbaixoDoMinimo() {

        criarProduto("Produto Critico", 10.0, 2, 5, 100);

        List<Produto> produtos =
                relatorioService.getAbaixoMinimo();

        assertEquals(1, produtos.size());
    }

    @Test
    void deveGerarProdutosPorCategoria() {

        long categoriaId = criarCategoria();

        produtoService.adicionar(
                new Produto(
                        "Produto A",
                        10,
                        "UN",
                        10,
                        1,
                        100,
                        categoriaId
                )
        );

        produtoService.adicionar(
                new Produto(
                        "Produto B",
                        20,
                        "UN",
                        10,
                        1,
                        100,
                        categoriaId
                )
        );

        RelatorioProdutosPorCategoria relatorio =
                relatorioService.getProdutosPorCategoria();

        assertFalse(relatorio.getItens().isEmpty());
        assertEquals(
                2,
                relatorio.getItens().get(0).getQuantidade()
        );
    }

    @Test
    void deveGerarMaioresMovimentacoes() {

        long produtoId =
                criarProduto(
                        "Produto",
                        10,
                        100,
                        5,
                        200
                );

        movimentacaoService.registrar(
                new Movimentacao(
                        produtoId,
                        50,
                        "ENTRADA"
                )
        );

        movimentacaoService.registrar(
                new Movimentacao(
                        produtoId,
                        30,
                        "SAIDA"
                )
        );

        RelatorioMaioresMovimentacoes relatorio =
                relatorioService.getMaioresMovimentacoes();

        assertNotNull(relatorio.getMaiorEntrada());
        assertNotNull(relatorio.getMaiorSaida());

        assertEquals(
                50,
                relatorio.getMaiorEntrada().getQuantidade()
        );

        assertEquals(
                30,
                relatorio.getMaiorSaida().getQuantidade()
        );
    }
}