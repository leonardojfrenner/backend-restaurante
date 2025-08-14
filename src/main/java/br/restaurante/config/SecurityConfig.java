package br.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.restaurante.repository.ClienteRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                // Desativa a proteção CSRF completamente, o que é comum para APIs
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Libera todas as páginas e recursos estáticos
                        .requestMatchers("/", "/index.html", "/cadastro.html", "/clientes.html", "/restaurante.html", "/dados.html", "/itens.html", "/avaliacao.html", "/restaurantePerfil.html", "/pedidos.html", "/css/**", "/js/**", "/uploads/**").permitAll()
                        // Libera todos os endpoints de clientes e restaurantes para acesso público
                        .requestMatchers("/clientes/**", "/restaurantes/**").permitAll()
                        // Permite leitura pública de itens e avaliações (GET)
                        .requestMatchers(HttpMethod.GET, "/itens/**", "/avaliacoes/**", "/avaliacoes-prato/**").permitAll()
                        // Libera gerenciamento de itens via páginas estáticas (POST/PUT/DELETE)
                        .requestMatchers(HttpMethod.POST, "/itens/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/itens/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/itens/**").permitAll()
                        // Garante que pedidos exijam autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*",
                "http://127.0.0.1:*"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        configuration.setExposedHeaders(Arrays.asList("Location", "Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(ClienteRepository clienteRepository) {
        return username -> clienteRepository.findByEmail(username)
                .map(cliente -> {
                    UserDetails user = User
                            .withUsername(cliente.getEmail())
                            .password(cliente.getSenha())
                            .roles("USER")
                            .build();
                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}