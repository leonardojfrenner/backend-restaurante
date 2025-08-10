package br.restaurante.controller;

import br.restaurante.model.Restaurante;
import br.restaurante.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Restaurante restaurante) {
        try {
            Restaurante novoRestaurante = restauranteService.cadastrarRestaurante(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (InputMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> readAll() {
        List<Restaurante> restaurantes = restauranteService.buscarTodos();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> readById(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteService.buscarPorId(id);

        return restaurante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> update(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Optional<Restaurante> restauranteAtualizado = restauranteService.atualizarRestaurante(id, restaurante);

        return restauranteAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (restauranteService.deletarRestaurante(id)) {
            // Retorna 204 No Content se a exclusão foi bem-sucedida
            return ResponseEntity.noContent().build();
        } else {
            // Retorna 404 Not Found se o restaurante não for encontrado
            return ResponseEntity.notFound().build();
        }
    }
}