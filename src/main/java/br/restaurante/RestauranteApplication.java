package br.restaurante;

import br.restaurante.model.Cliente;
import br.restaurante.model.Restaurante;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestauranteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteApplication.class, args);
		System.out.println("hello world");
	}

	// Este método é executado logo após a inicialização da aplicação
	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Instanciando um Restaurante
			Restaurante restaurante = new Restaurante();
			restaurante.setNome("Sabor da Casa");
			restaurante.setCnpj("12.345.678/0001-90");
			restaurante.setEmail("contato@sabordacasa.com");
			restaurante.setCep("12345-678");

			System.out.println("--- Dados do Restaurante ---");
			System.out.println("ID: " + restaurante.getId());
			System.out.println("Nome: " + restaurante.getNome());
			System.out.println("CNPJ: " + restaurante.getCnpj());
			System.out.println("Email: " + restaurante.getEmail());
			System.out.println("--------------------------\n");

			// Instanciando um Cliente
			Cliente cliente = new Cliente();
			cliente.setNome("Maria da Silva");
			cliente.setCpf("98765432100");
			cliente.setEmail("maria.silva@email.com");
			cliente.setTelefone("9988776655");
			cliente.setSenha("senha123");

			System.out.println("--- Dados do Cliente ---");
			System.out.println("ID: " + cliente.getId());
			System.out.println("Nome: " + cliente.getNome());
			System.out.println("CPF: " + cliente.getCpf());
			System.out.println("Email: " + cliente.getEmail());
			System.out.println("--------------------------");
		};
	}
}