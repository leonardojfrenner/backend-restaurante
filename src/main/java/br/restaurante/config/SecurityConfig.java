package br.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desativa a proteção CSRF completamente, o que é comum para APIs
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Libera todas as páginas e recursos estáticos
                        .requestMatchers("/", "/index.html", "/cadastro.html", "/clientes.html", "/restaurante.html", "/dados.html", "/itens.html", "/avaliacao.html", "/restaurantePerfil.html", "/pedidos.html", "/css/**", "/js/**").permitAll()
                        // Libera todos os endpoints de clientes e restaurantes para acesso público
                        .requestMatchers("/clientes/**", "/restaurantes/**").permitAll()
                        // Permite leitura pública de itens e avaliações (GET)
                        .requestMatchers(HttpMethod.GET, "/itens/**", "/avaliacoes/**", "/avaliacoes-prato/**").permitAll()
                        // Libera gerenciamento de itens via páginas estáticas (POST/PUT/DELETE)
                        .requestMatchers(HttpMethod.POST, "/itens/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/itens/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/itens/**").permitAll()
                        // Permite leitura pública de itens e avaliações (GET)
                        .requestMatchers(HttpMethod.GET, "/itens/**", "/avaliacoes/**").permitAll()
                        // Garante que pedidos exijam autenticação
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}