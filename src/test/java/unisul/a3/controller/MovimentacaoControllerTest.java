package unisul.a3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unisul.a3.model.Movimentacao;
import unisul.a3.service.MovimentacaoService;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoControllerTest {

    private MovimentacaoController controller;

    @BeforeEach
    void setup() throws Exception {

        controller = new MovimentacaoController();

        MovimentacaoService service =
                new MovimentacaoService();

        Field field =
                MovimentacaoController.class
                        .getDeclaredField("service");

        field.setAccessible(true);
        field.set(controller, service);
    }

    @Test
    void deveListarMovimentacoes() {

        List<Movimentacao> lista =
                controller.listar();

        assertNotNull(lista);
    }

    @Test
    void deveRetornarNotFoundAoBuscarMovimentacaoInexistente() {

        var response =
                controller.buscar(999999L);

        assertEquals(
                404,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveRetornarErroAoRegistrarProdutoInexistente() {

        Movimentacao mov =
                new Movimentacao();

        mov.setProdutoId(999999L);
        mov.setQuantidade(5);
        mov.setTipo("ENTRADA");

        var response =
                controller.registrar(mov);

        assertEquals(
                400,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveRetornarErroAoAtualizarMovimentacaoInexistente() {

        Movimentacao mov =
                new Movimentacao();

        mov.setProdutoId(1L);
        mov.setQuantidade(10);
        mov.setTipo("ENTRADA");

        var response =
                controller.atualizar(
                        999999L,
                        mov
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveRetornarErroAoRemoverMovimentacaoInexistente() {

        var response =
                controller.remover(
                        999999L
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveRetornar404QuandoMovimentacaoNaoExiste() {
        ResponseEntity<?> response = controller.buscar(999999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}