package unisul.a3.service;

import org.junit.jupiter.api.*;
import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.Categoria;
import unisul.a3.model.Produto;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoServiceTest {

    private ProdutoService produtoService;
    private CategoriaService categoriaService;

    @BeforeAll
    static void configurarBancoTeste() {
        System.setProperty(
                "db.url",
                "jdbc:mysql://localhost:3306/estoque_test?useSSL=false&serverTimezone=UTC"
        );
    }

    @BeforeEach
    void setup() throws Exception {

        produtoService = new ProdutoService();
        categoriaService = new CategoriaService();

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

    @Test
    void deveAdicionarEBuscarProduto() {

        long categoriaId = criarCategoria();

        Produto produto = new Produto(
                "Coca-Cola",
                10.0,
                "UN",
                50,
                10,
                100,
                categoriaId
        );

        produtoService.adicionar(produto);

        Produto resultado =
                produtoService.buscarPorId(produto.getId());

        assertNotNull(resultado);
        assertEquals("Coca-Cola", resultado.getNome());
    }

    @Test
    void deveListarProdutos() {

        long categoriaId = criarCategoria();

        produtoService.adicionar(
                new Produto("Produto A", 10, "UN", 20, 5, 50, categoriaId));

        produtoService.adicionar(
                new Produto("Produto B", 20, "UN", 30, 5, 50, categoriaId));

        List<Produto> produtos =
                produtoService.listar();

        assertEquals(2, produtos.size());
    }

    @Test
    void deveAtualizarProduto() {

        long categoriaId = criarCategoria();

        Produto produto =
                new Produto("Produto", 10, "UN", 20, 5, 50, categoriaId);

        produtoService.adicionar(produto);

        produto.setNome("Produto Atualizado");

        produtoService.atualizar(produto);

        Produto atualizado =
                produtoService.buscarPorId(produto.getId());

        assertEquals("Produto Atualizado", atualizado.getNome());
    }

    @Test
    void deveRemoverProduto() {

        long categoriaId = criarCategoria();

        Produto produto =
                new Produto("Produto", 10, "UN", 20, 5, 50, categoriaId);

        produtoService.adicionar(produto);

        produtoService.remover(produto.getId());

        assertNull(
                produtoService.buscarPorId(produto.getId())
        );
    }

    @Test
    void deveRetornarNullQuandoProdutoNaoExiste() {

        Produto produto =
                produtoService.buscarPorId(99999L);

        assertNull(produto);
    }

    @Test
    void deveListarProdutosAbaixoDoMinimo() {

        long categoriaId = criarCategoria();

        produtoService.adicionar(
                new Produto(
                        "Produto Critico",
                        10,
                        "UN",
                        2,
                        10,
                        50,
                        categoriaId
                )
        );

        List<Produto> abaixoMinimo =
                produtoService.listarAbaixoMinimo();

        assertEquals(1, abaixoMinimo.size());
        assertEquals(
                "Produto Critico",
                abaixoMinimo.get(0).getNome()
        );
    }
}