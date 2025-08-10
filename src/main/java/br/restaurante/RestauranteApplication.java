package br.restaurante;

import br.restaurante.model.Cliente;
import br.restaurante.model.Restaurante;
import br.restaurante.repository.ClienteRepository;
import br.restaurante.repository.RestauranteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class RestauranteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteApplication.class, args);
		System.out.println("hello world");
	}

//	@Bean
//	public CommandLineRunner run(RestauranteRepository restauranteRepository, ClienteRepository clienteRepository) {
//		return args -> {
//			// 1. Instanciar e salvar um Restaurante
//			System.out.println("--- Salvando Restaurante no Banco ---");
//			Restaurante restaurante = new Restaurante();
//			restaurante.setNome("Sabor da Casa");
//			restaurante.setCnpj("12345678000190");
//			restaurante.setEmail("contato@sabordacasa.com");
//			restaurante.setCep("12345-678");
//
//			Restaurante restauranteSalvo = restauranteRepository.save(restaurante);
//			System.out.println("Restaurante salvo com ID: " + restauranteSalvo.getId());
//
//			// 2. Instanciar e salvar um Cliente
//			System.out.println("\n--- Salvando Cliente no Banco ---");
//			Cliente cliente = new Cliente();
//			cliente.setNome("Maria da Silva");
//			cliente.setCpf("98765432100");
//			cliente.setEmail("maria.silva@email.com");
//			cliente.setTelefone("9988776655");
//			cliente.setSenha("senha123");
//
//			Cliente clienteSalvo = clienteRepository.save(cliente);
//			System.out.println("Cliente salvo com ID: " + clienteSalvo.getId());
//
//			// 3. Recuperar todos os restaurantes e clientes do banco para verificar
//			System.out.println("\n--- Buscando todos os Restaurantes ---");
//			List<Restaurante> todosRestaurantes = restauranteRepository.findAll();
//			todosRestaurantes.forEach(r -> System.out.println("ID: " + r.getId() + ", Nome: " + r.getNome()));
//
//			System.out.println("\n--- Buscando todos os Clientes ---");
//			List<Cliente> todosClientes = clienteRepository.findAll();
//			todosClientes.forEach(c -> System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome()));
//		};
//	}
}