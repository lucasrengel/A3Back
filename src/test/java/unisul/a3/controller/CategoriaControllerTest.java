package unisul.a3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unisul.a3.config.DatabaseInitializer;
import unisul.a3.model.Categoria;
import unisul.a3.service.CategoriaService;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaControllerTest {

    private CategoriaController controller;

    @BeforeEach
    void setup() throws Exception {

        DatabaseInitializer.initialize();


        controller = new CategoriaController();

        CategoriaService service = new CategoriaService();

        Field field =
                CategoriaController.class.getDeclaredField("service");

        field.setAccessible(true);
        field.set(controller, service);
    }

    @Test
    void deveListarCategorias() {

        List<Categoria> categorias =
                controller.listar();

        assertNotNull(categorias);
    }

    @Test
    void deveRetornarNotFoundAoBuscarCategoriaInexistente() {

        var response = controller.buscar(999999L);

        assertEquals(
                404,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveAdicionarCategoria() {

        Categoria categoria =
                new Categoria(
                        "Teste",
                        "1L",
                        "Garrafa"
                );

        var response =
                controller.adicionar(categoria);

        assertEquals(
                200,
                response.getStatusCode().value()
        );
    }

    @Test
    void deveAtualizarCategoria() {

        Categoria categoria =
                new Categoria(
                        "Atualizada",
                        "2L",
                        "PET"
                );

        var response =
                controller.atualizar(
                        1L,
                        categoria
                );

        assertNotNull(response);
    }

    @Test
    void deveRemoverCategoria() {

        var response =
                controller.remover(999999L);

        assertNotNull(response);
    }

    @Test
    void deveRetornar404QuandoCategoriaNaoExiste() {
        ResponseEntity<?> response = controller.buscar(999999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deveBuscarCategoriaExistente() {

        Categoria categoria =
                new Categoria(
                        "Categoria Busca",
                        "1L",
                        "Garrafa"
                );

        controller.adicionar(categoria);

        ResponseEntity<?> response =
                controller.buscar(categoria.getId());

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());
    }
}