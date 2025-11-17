package unisul.a3.controller;

import unisul.a3.model.Categoria;
import unisul.a3.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable long id) {
        Categoria c = service.buscar(id);
        if (c != null) {
            return ResponseEntity.ok(c);
        }
        return ResponseEntity.notFound().build();
    }
}
