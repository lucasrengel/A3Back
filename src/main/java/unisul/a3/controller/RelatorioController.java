package unisul.a3.controller;

import unisul.a3.model.*;
import unisul.a3.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping("/lista-precos")
    public RelatorioListaPreco listaPrecos() {
        return service.getListaPreco();
    }

    @GetMapping("/balanco")
    public RelatorioBalanco balanco() {
        return service.getBalanco();
    }
}
