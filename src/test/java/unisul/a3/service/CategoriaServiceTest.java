package unisul.a3.service;

import org.junit.jupiter.api.*;
import unisul.a3.config.DatabaseConnection;
import unisul.a3.model.Categoria;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaServiceTest {

    private CategoriaService service;

    @BeforeAll
    static void configurarBancoTeste() {
        System.setProperty(
                "db.url",
                "jdbc:mysql://localhost:3306/estoque_test?useSSL=false&serverTimezone=UTC"
        );
    }

    @BeforeEach
    void setup() throws Exception {
        service = new CategoriaService();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM movimentacao");
            stmt.executeUpdate("DELETE FROM produto");
            stmt.executeUpdate("DELETE FROM categoria");
        }
    }

    @Test
    void deveAdicionarEBuscarCategoria() {

        Categoria categoria =
                new Categoria("Bebidas", "1L", "Garrafa");

        service.adicionar(categoria);

        Categoria resultado =
                service.buscar(categoria.getId());

        assertNotNull(resultado);
        assertEquals("Bebidas", resultado.getNome());
        assertEquals("1L", resultado.getTamanho());
        assertEquals("Garrafa", resultado.getEmbalagem());
    }

    @Test
    void deveListarCategorias() {

        service.adicionar(
                new Categoria("Bebidas", "1L", "Garrafa"));

        service.adicionar(
                new Categoria("Doces", "100g", "Pacote"));

        assertEquals(2, service.listar().size());
    }

    @Test
    void deveAtualizarCategoria() {

        Categoria categoria =
                new Categoria("Bebidas", "1L", "Garrafa");

        service.adicionar(categoria);

        categoria.setNome("Refrigerantes");

        service.atualizar(categoria);

        Categoria atualizada =
                service.buscar(categoria.getId());

        assertEquals("Refrigerantes", atualizada.getNome());
    }

    @Test
    void deveRetornarNullQuandoCategoriaNaoExiste() {

        Categoria categoria = service.buscar(99999L);

        assertNull(categoria);
    }

    @Test
    void deveRemoverCategoria() {

        Categoria categoria =
                new Categoria("Bebidas", "1L", "Garrafa");

        service.adicionar(categoria);

        service.remover(categoria.getId());

        assertNull(service.buscar(categoria.getId()));
    }
}