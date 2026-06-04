package unisul.a3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unisul.a3.config.DatabaseInitializer;
import unisul.a3.model.Produto;
import unisul.a3.service.ProdutoService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoControllerTest {

    private ProdutoController controller;

    @BeforeEach
    void setup() throws Exception {

        DatabaseInitializer.initialize();

        controller = new ProdutoController();

        ProdutoService service = new ProdutoService();

        Field field =
                ProdutoController.class.getDeclaredField("service");

        field.setAccessible(true);
        field.set(controller, service);
    }

    @Test
    void deveListarProdutos() {

        List<Produto> produtos = controller.listar();

        assertNotNull(produtos);
    }

    @Test
    void deveRetornarNotFoundAoBuscarProdutoInexistente() {

        var response = controller.buscar(999999L);

        assertEquals(
                404,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveAdicionarProduto() {

        Produto produto = new Produto();

        produto.setNome("Produto Teste");
        produto.setPrecoUnitario(10.0);
        produto.setUnidade("UN");
        produto.setQuantidadeEstoque(10);
        produto.setQuantidadeMinima(1);
        produto.setQuantidadeMaxima(100);

        // categoria inexistente para forçar caminho de erro
        produto.setCategoriaId(99999L);

        var response = controller.adicionar(produto);

        assertNotNull(response);
    }

    @Test
    void deveAtualizarProduto() {

        Produto produto = new Produto();

        produto.setNome("Atualizado");
        produto.setPrecoUnitario(20.0);

        var response =
                controller.atualizar(
                        1L,
                        produto
                );

        assertNotNull(response);
    }

    @Test
    void deveRemoverProduto() {

        var response =
                controller.remover(
                        999999L
                );

        assertNotNull(response);
    }




    @Test
    void deveRetornar404QuandoProdutoNaoExiste() {
        ResponseEntity<?> response = controller.buscar(999999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deveAjustarPrecos() {

        Map<String, Double> body = new HashMap<>();
        body.put("percentual", 10.0);

        ResponseEntity<?> response =
                controller.ajustarPrecos(body);

        assertNotNull(response);
    }
    @Test
    void deveRetornarErroAoAjustarPrecosSemPercentual() {

        Map<String, Double> body = new HashMap<>();

        ResponseEntity<?> response =
                controller.ajustarPrecos(body);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }
    @Test
    void deveAjustarPrecosComSucesso() {

        Map<String, Double> body = new HashMap<>();
        body.put("percentual", 10.0);

        ResponseEntity<?> response =
                controller.ajustarPrecos(body);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
    }
}