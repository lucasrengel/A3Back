package unisul.a3.service;

import org.junit.jupiter.api.*;
import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.Categoria;
import unisul.a3.model.Movimentacao;
import unisul.a3.model.Produto;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoServiceTest {

    private MovimentacaoService movimentacaoService;
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

        movimentacaoService = new MovimentacaoService();
        produtoService = new ProdutoService();
        categoriaService = new CategoriaService();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM movimentacao");
            stmt.executeUpdate("DELETE FROM produto");
            stmt.executeUpdate("DELETE FROM categoria");
        }
    }

    private long criarProduto(int estoque, int minimo, int maximo) {

        Categoria categoria =
                new Categoria("Bebidas", "1L", "Garrafa");

        categoriaService.adicionar(categoria);

        Produto produto =
                new Produto(
                        "Coca-Cola",
                        10.0,
                        "UN",
                        estoque,
                        minimo,
                        maximo,
                        categoria.getId()
                );

        produtoService.adicionar(produto);

        return produto.getId();
    }

    @Test
    void deveRegistrarEntrada() {

        long produtoId = criarProduto(10, 5, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 20, "ENTRADA")
        );

        assertTrue(resultado.contains("Movimentação registrada"));
    }

    @Test
    void deveRegistrarSaida() {

        long produtoId = criarProduto(50, 5, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "SAIDA")
        );

        assertTrue(resultado.contains("Movimentação registrada"));
    }

    @Test
    void deveRetornarErroQuandoEstoqueInsuficiente() {

        long produtoId = criarProduto(5, 2, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 50, "SAIDA")
        );

        assertTrue(resultado.contains("Estoque insuficiente"));
    }

    @Test
    void deveRetornarErroQuandoProdutoNaoExiste() {

        String resultado = movimentacaoService.registrar(
                new Movimentacao(99999L, 10, "ENTRADA")
        );

        assertTrue(resultado.contains("não encontrado"));
    }

    @Test
    void deveRetornarErroQuandoTipoInvalido() {

        long produtoId = criarProduto(10, 5, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "TESTE")
        );

        assertTrue(resultado.contains("Tipo de movimentação desconhecido"));
    }

    @Test
    void deveGerarAvisoAbaixoDoMinimo() {

        long produtoId = criarProduto(10, 8, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 5, "SAIDA")
        );

        assertTrue(resultado.contains("abaixo do mínimo"));
    }

    @Test
    void deveGerarAvisoAcimaDoMaximo() {

        long produtoId = criarProduto(90, 5, 100);

        String resultado = movimentacaoService.registrar(
                new Movimentacao(produtoId, 20, "ENTRADA")
        );

        assertTrue(resultado.contains("acima do máximo"));
    }

    @Test
    void deveListarMovimentacoes() {

        long produtoId = criarProduto(50, 5, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "ENTRADA")
        );

        List<Movimentacao> lista =
                movimentacaoService.listar();

        assertEquals(1, lista.size());
    }

    @Test
    void deveBuscarMovimentacao() {

        long produtoId = criarProduto(50, 5, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao encontrada =
                movimentacaoService.buscar(mov.getId());

        assertNotNull(encontrada);
    }

    @Test
    void deveRetornarNullQuandoMovimentacaoNaoExiste() {

        assertNull(
                movimentacaoService.buscar(99999L)
        );
    }

    @Test
    void deveBuscarMaiorEntrada() {

        long produtoId = criarProduto(10, 5, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 5, "ENTRADA")
        );

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 30, "ENTRADA")
        );

        Movimentacao maior =
                movimentacaoService.buscarMaiorEntrada();

        assertNotNull(maior);
        assertEquals(30, maior.getQuantidade());
    }

    @Test
    void deveBuscarMaiorSaida() {

        long produtoId = criarProduto(100, 5, 200);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "SAIDA")
        );

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 40, "SAIDA")
        );

        Movimentacao maior =
                movimentacaoService.buscarMaiorSaida();

        assertNotNull(maior);
        assertEquals(40, maior.getQuantidade());
    }

    @Test
    void deveAtualizarMovimentacao() {

        long produtoId = criarProduto(100, 5, 200);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao nova =
                new Movimentacao();

        nova.setId(mov.getId());
        nova.setProdutoId(produtoId);
        nova.setQuantidade(20);
        nova.setTipo("ENTRADA");

        String resultado =
                movimentacaoService.atualizar(nova);

        assertTrue(
                resultado.contains("Movimentação atualizada")
        );
    }

    @Test
    void deveRetornarErroAoAtualizarMovimentacaoInexistente() {

        Movimentacao mov =
                new Movimentacao();

        mov.setId(999999L);
        mov.setProdutoId(1L);
        mov.setQuantidade(10);
        mov.setTipo("ENTRADA");

        String resultado =
                movimentacaoService.atualizar(mov);

        assertTrue(
                resultado.contains("não encontrada")
        );
    }

    @Test
    void deveRemoverMovimentacao() {

        long produtoId = criarProduto(100, 5, 200);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        assertDoesNotThrow(
                () -> movimentacaoService.remover(mov.getId())
        );
    }

    @Test
    void deveFalharAoRemoverMovimentacaoInexistente() {

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> movimentacaoService.remover(999999L)
                );

        assertTrue(
                ex.getMessage().contains("Movimentação não encontrada")
        );
    }

    @Test
    void deveRetornarErroQuandoProdutoNaoExisteAoAtualizar() {

        long produtoId = criarProduto(100, 5, 200);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 10, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao nova =
                new Movimentacao();

        nova.setId(mov.getId());
        nova.setProdutoId(999999L);
        nova.setQuantidade(10);
        nova.setTipo("ENTRADA");

        String resultado =
                movimentacaoService.atualizar(nova);

        assertTrue(
                resultado.contains("Produto não encontrado")
        );
    }

    @Test
    void deveRetornarErroQuandoAtualizacaoGerarEstoqueInsuficiente() {

        long produtoId = criarProduto(10, 5, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 2, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao nova =
                new Movimentacao();

        nova.setId(mov.getId());
        nova.setProdutoId(produtoId);
        nova.setQuantidade(999);
        nova.setTipo("SAIDA");

        String resultado =
                movimentacaoService.atualizar(nova);

        assertTrue(
                resultado.contains("Estoque insuficiente")
        );
    }

    @Test
    void deveGerarAvisoAbaixoDoMinimoAoAtualizar() {

        long produtoId = criarProduto(20, 15, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 2, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao nova =
                new Movimentacao();

        nova.setId(mov.getId());
        nova.setProdutoId(produtoId);
        nova.setQuantidade(10);
        nova.setTipo("SAIDA");

        String resultado =
                movimentacaoService.atualizar(nova);

        assertTrue(
                resultado.contains("abaixo do mínimo")
        );
    }

    @Test
    void deveGerarAvisoAcimaDoMaximoAoAtualizar() {

        long produtoId = criarProduto(90, 5, 100);

        movimentacaoService.registrar(
                new Movimentacao(produtoId, 1, "ENTRADA")
        );

        Movimentacao mov =
                movimentacaoService.listar().get(0);

        Movimentacao nova =
                new Movimentacao();

        nova.setId(mov.getId());
        nova.setProdutoId(produtoId);
        nova.setQuantidade(20);
        nova.setTipo("ENTRADA");

        String resultado =
                movimentacaoService.atualizar(nova);

        assertTrue(
                resultado.contains("acima do máximo")
        );
    }
}