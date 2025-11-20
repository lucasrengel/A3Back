package unisul.a3.controller;

import unisul.a3.model.Movimentacao;
import unisul.a3.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @GetMapping
    public List<Movimentacao> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable long id) {
        Movimentacao m = service.buscar(id);
        if (m != null) {
            return ResponseEntity.ok(m);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Movimentacao movimentacao) {
        try {
            String resultado = service.registrar(movimentacao);
            if (resultado.startsWith("ERRO")) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "error", resultado));
            }
            return ResponseEntity.ok(Map.of("success", true, "message", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable long id, @RequestBody Movimentacao movimentacao) {
        try {
            movimentacao.setId(id);
            String resultado = service.atualizar(movimentacao);
            if (resultado.startsWith("ERRO")) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "error", resultado));
            }
            return ResponseEntity.ok(Map.of("success", true, "message", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable long id) {
        try {
            service.remover(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Movimentação removida com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
