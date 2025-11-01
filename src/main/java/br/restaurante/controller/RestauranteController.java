package br.restaurante.controller;

import br.restaurante.dto.LoginRequest;
import br.restaurante.model.Restaurante;
import br.restaurante.service.RestauranteService;
import br.restaurante.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping(value = "/restaurantes", produces = "application/json")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/login") // Endpoint de login
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Restaurante restauranteLogado = restauranteService.loginRestaurante(loginRequest);

        if (restauranteLogado != null) {
            // Cria principal e autenticação para a sessão do Spring Security
            UserDetails principal = User
                    .withUsername(restauranteLogado.getEmail())
                    .password("N/A")
                    .roles("USER")
                    .build();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            // Garante a criação da sessão e a emissão do cookie JSESSIONID e persiste o contexto
            request.getSession(true);
            org.springframework.security.web.context.HttpSessionSecurityContextRepository repo =
                    new org.springframework.security.web.context.HttpSessionSecurityContextRepository();
            repo.saveContext(securityContext, request, response);

            // Login bem-sucedido: retorna os dados do restaurante
            return ResponseEntity.ok(restauranteLogado);
        } else {
            // Login falhou: retorna 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email ou senha incorretos."));
        }
    }

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

    @PostMapping(value = "/upload/{tipo}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadByTipo(@PathVariable String tipo, @RequestPart("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            String url = fileStorageService.saveFile(file, tipo);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadSemTipo(@RequestParam(value = "tipo", required = false) String tipo,
                                           @RequestPart("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            String url = fileStorageService.saveFile(file, tipo);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    // ... (restante dos métodos do controller)

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