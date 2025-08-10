package br.restaurante.service;

import br.restaurante.model.Endereco;
import br.restaurante.model.Restaurante;
import br.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ViaCepService viaCepService;

    public Restaurante cadastrarRestaurante(Restaurante restaurante) {
        // --- Programação Defensiva: Validação de dados de entrada ---

        // 1. Validar campos obrigatórios
        if (restaurante.getNome() == null || restaurante.getNome().isBlank() ||
                restaurante.getCnpj() == null || restaurante.getCnpj().isBlank() ||
                restaurante.getEmail() == null || restaurante.getEmail().isBlank()) {
            throw new InputMismatchException("Os campos Nome, CNPJ e Email são obrigatórios.");
        }

        // 2. Limpar a formatação do CNPJ e validar o tamanho
        String cnpjLimpo = restaurante.getCnpj().replaceAll("[^0-9]", "");
        restaurante.setCnpj(cnpjLimpo);
        if (cnpjLimpo.length() != 14) {
            throw new InputMismatchException("O CNPJ deve conter 14 dígitos.");
        }

        // 3. Verificar unicidade do CNPJ e Email
        Optional<Restaurante> restauranteExistenteCnpj = restauranteRepository.findByCnpj(restaurante.getCnpj());
        if (restauranteExistenteCnpj.isPresent()) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }
        Optional<Restaurante> restauranteExistenteEmail = restauranteRepository.findByEmail(restaurante.getEmail());
        if (restauranteExistenteEmail.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        // 4. Buscar e preencher o endereço a partir do CEP
        if (restaurante.getCep() != null && !restaurante.getCep().isBlank()) {
            Endereco endereco = viaCepService.buscaEnderecoPorCep(restaurante.getCep());
            restaurante.setRua(endereco.rua());
            restaurante.setBairro(endereco.bairro());
            restaurante.setCidade(endereco.cidade());
            restaurante.setEstado(endereco.estado());

            // O número é um campo que o usuário preenche.
            // A sua lógica já garante que ele será mantido
            // no objeto `restaurante` que o front-end enviou.
        }

        // 5. Salvar a entidade no banco de dados
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }
}