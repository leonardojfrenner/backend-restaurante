package br.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/restaurantes/login", "/restaurantes", "/clientes").permitAll() // Endpoints de login e cadastro
                        .requestMatchers("/index.html", "/clientes.html", "/restaurante.html", "/").permitAll() // Páginas HTML
                        .anyRequest().authenticated() // Qualquer outra requisição precisa de autenticação
                )
                .formLogin(form -> form
                        .loginPage("/restaurante.html")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Desabilitar CSRF para facilitar testes (Não recomendado em produção)

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}