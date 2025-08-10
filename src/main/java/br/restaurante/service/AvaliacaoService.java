package br.restaurante.service;

import br.restaurante.model.Avaliacao;
import br.restaurante.model.Restaurante;
import br.restaurante.repository.AvaliacaoRepository;
import br.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Realiza o cadastro de uma nova avaliação de restaurante, com validações de dados.
     * @param avaliacao O objeto Avaliacao a ser salvo.
     * @return O objeto Avaliacao salvo com o ID gerado.
     * @throws InputMismatchException se os campos obrigatórios não forem preenchidos, a nota for inválida ou o restaurante não for encontrado.
     */
    public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) {
        // --- Programação Defensiva: Validação de dados de entrada ---

        // 1. Validar campos obrigatórios
        if (avaliacao.getNota() == null) {
            throw new InputMismatchException("A nota da avaliação é obrigatória.");
        }

        // 2. Validar o valor da nota (por exemplo, de 1 a 5)
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new InputMismatchException("A nota deve ser um valor entre 1 e 5.");
        }

        // 3. Validar se o restaurante associado à avaliação existe
        if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getId() == null) {
            throw new InputMismatchException("É necessário associar a avaliação a um restaurante.");
        }

        Optional<Restaurante> restauranteExistente = restauranteRepository.findById(avaliacao.getRestaurante().getId());
        if (restauranteExistente.isEmpty()) {
            throw new InputMismatchException("Restaurante não encontrado. Verifique o ID do restaurante.");
        }

        // 4. Preencher a data da avaliação automaticamente
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        // 5. Salvar a entidade no banco de dados
        return avaliacaoRepository.save(avaliacao);
    }

    /**
     * Busca todas as avaliações de um restaurante específico.
     * @param restauranteId O ID do restaurante.
     * @return Uma lista de avaliações do restaurante.
     */
    public List<Avaliacao> buscarAvaliacoesPorRestauranteId(Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
        if (restaurante.isEmpty()) {
            throw new InputMismatchException("Restaurante não encontrado.");
        }
        return avaliacaoRepository.findByRestaurante(restaurante.get());
    }
}