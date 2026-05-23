package unisul.a3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unisul.a3.model.RelatorioBalanco;
import unisul.a3.model.RelatorioListaPreco;
import unisul.a3.model.RelatorioMaioresMovimentacoes;
import unisul.a3.model.RelatorioProdutosPorCategoria;
import unisul.a3.service.MovimentacaoService;
import unisul.a3.service.ProdutoService;
import unisul.a3.service.RelatorioService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioControllerTest {

    private RelatorioController controller;

    @BeforeEach
    void setup() throws Exception {

        controller = new RelatorioController();

        RelatorioService service = new RelatorioService();

        Field produtoField =
                RelatorioService.class
                        .getDeclaredField("produtoService");

        produtoField.setAccessible(true);
        produtoField.set(
                service,
                new ProdutoService()
        );

        Field movimentacaoField =
                RelatorioService.class
                        .getDeclaredField("movimentacaoService");

        movimentacaoField.setAccessible(true);
        movimentacaoField.set(
                service,
                new MovimentacaoService()
        );

        Field controllerField =
                RelatorioController.class
                        .getDeclaredField("service");

        controllerField.setAccessible(true);
        controllerField.set(
                controller,
                service
        );
    }

    @Test
    void deveGerarListaPrecos() {

        RelatorioListaPreco relatorio =
                controller.listaPrecos();

        assertNotNull(relatorio);
    }

    @Test
    void deveGerarBalanco() {

        RelatorioBalanco relatorio =
                controller.balanco();

        assertNotNull(relatorio);
    }

    @Test
    void deveListarProdutosAbaixoMinimo() {

        var lista =
                controller.abaixoMinimo();

        assertNotNull(lista);
    }

    @Test
    void deveGerarRelatorioPorCategoria() {

        RelatorioProdutosPorCategoria relatorio =
                controller.porCategoria();

        assertNotNull(relatorio);
    }

    @Test
    void deveGerarRelatorioMaioresMovimentacoes() {

        RelatorioMaioresMovimentacoes relatorio =
                controller.maioresMovimentacoes();

        assertNotNull(relatorio);
    }
}