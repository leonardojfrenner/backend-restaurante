package br.restaurante.controller;

import br.restaurante.model.ItemRestaurante;
import br.restaurante.service.ItemRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/itens", produces = "application/json")
public class ItemRestauranteController {

    @Autowired
    private ItemRestauranteService itemRestauranteService;

    @PostMapping
    public ResponseEntity<ItemRestaurante> cadastrarItem(@RequestBody ItemRestaurante itemRestaurante) {
        try {
            ItemRestaurante novoItem = itemRestauranteService.cadastrarItem(itemRestaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemRestaurante>> buscarTodosItens() {
        List<ItemRestaurante> itens = itemRestauranteService.buscarTodosItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRestaurante> buscarItemPorId(@PathVariable Long id) {
        Optional<ItemRestaurante> item = itemRestauranteService.buscarItemPorId(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemRestaurante> atualizarItem(@PathVariable Long id, @RequestBody ItemRestaurante itemAtualizado) {
        Optional<ItemRestaurante> item = itemRestauranteService.atualizarItem(id, itemAtualizado);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemRestauranteService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ItemRestaurante>> buscarItensPorRestaurante(@PathVariable Long restauranteId) {
        List<ItemRestaurante> itens = itemRestauranteService.buscarItensPorRestaurante(restauranteId);
        // Para coleções, é melhor retornar 200 com array vazio do que 404
        return ResponseEntity.ok(itens);
    }
}