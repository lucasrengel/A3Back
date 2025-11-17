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

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Categoria categoria) {
        try {
            service.adicionar(categoria);
            return ResponseEntity.ok(Map.of("success", true, "message", "Categoria adicionada com sucesso!", "data", categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable long id, @RequestBody Categoria categoria) {
        try {
            categoria.setId(id);
            service.atualizar(categoria);
            return ResponseEntity.ok(Map.of("success", true, "message", "Categoria atualizada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable long id) {
        try {
            service.remover(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Categoria removida com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
