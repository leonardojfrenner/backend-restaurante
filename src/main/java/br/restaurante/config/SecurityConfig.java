package br.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/", "/index.html", "/clientes.html", "/restaurante.html", "/dados.html", "/itens.html", "/avaliacao.html", "/css/**", "/js/**").permitAll()
                        // Libera todos os endpoints de clientes e restaurantes para acesso público
                        .requestMatchers("/clientes/**", "/restaurantes/**").permitAll()
                        // Garante que todas as outras requisições (como a de avaliações) exijam autenticação
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}