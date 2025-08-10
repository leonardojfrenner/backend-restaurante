package br.restaurante.service;

import br.restaurante.model.Prato;
import br.restaurante.model.Restaurante;
import br.restaurante.repository.PratoRepository;
import br.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Realiza o cadastro de um novo prato, com validações de dados.
     * @param prato O objeto Prato a ser salvo.
     * @return O objeto Prato salvo com o ID gerado.
     * @throws InputMismatchException se os campos obrigatórios não forem preenchidos ou o restaurante não for encontrado.
     */
    public Prato cadastrarPrato(Prato prato) {
        // --- Programação Defensiva: Validação de dados de entrada ---

        // 1. Validar campos obrigatórios do prato
        if (prato.getNome() == null || prato.getNome().isBlank() ||
                prato.getPreco() == null || prato.getPreco() < 0) {
            throw new InputMismatchException("O nome e o preço do prato são obrigatórios e o preço deve ser positivo.");
        }

        // 2. Validar se o restaurante associado ao prato existe
        if (prato.getRestaurante() == null || prato.getRestaurante().getId() == null) {
            throw new InputMismatchException("É necessário associar o prato a um restaurante.");
        }

        Optional<Restaurante> restauranteExistente = restauranteRepository.findById(prato.getRestaurante().getId());
        if (restauranteExistente.isEmpty()) {
            throw new InputMismatchException("Restaurante não encontrado. Verifique o ID do restaurante.");
        }

        // Atribui a referência completa do restaurante à entidade prato antes de salvar
        prato.setRestaurante(restauranteExistente.get());

        // 3. Salvar a entidade no banco de dados
        return pratoRepository.save(prato);
    }

    /**
     * Busca todos os pratos de um restaurante específico.
     * @param restauranteId O ID do restaurante.
     * @return Uma lista de pratos do restaurante.
     */
    public List<Prato> buscarPratosPorRestaurante(Long restauranteId) {
        // Usa o método personalizado do repositório para buscar pratos por ID do restaurante
        return pratoRepository.findByRestauranteIdOrderByNomeAsc(restauranteId);
    }

    /**
     * Busca um prato pelo seu ID.
     * @param id O ID do prato.
     * @return Um Optional contendo o prato, se encontrado.
     */
    public Optional<Prato> buscarPorId(Long id) {
        return pratoRepository.findById(id);
    }
}