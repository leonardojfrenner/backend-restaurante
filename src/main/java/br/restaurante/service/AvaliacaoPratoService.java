package br.restaurante.service;

import br.restaurante.model.AvaliacaoPrato;
import br.restaurante.model.Prato;
import br.restaurante.repository.AvaliacaoPratoRepository;
import br.restaurante.repository.PratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoPratoService {

    @Autowired
    private AvaliacaoPratoRepository avaliacaoPratoRepository;

    @Autowired
    private PratoRepository pratoRepository;

    /**
     * Realiza o cadastro de uma nova avaliação de prato, com validações de dados.
     * @param avaliacaoPrato O objeto AvaliacaoPrato a ser salvo.
     * @return O objeto AvaliacaoPrato salvo com o ID gerado.
     * @throws InputMismatchException se os campos obrigatórios não forem preenchidos, a nota for inválida ou o prato não for encontrado.
     */
    public AvaliacaoPrato cadastrarAvaliacaoPrato(AvaliacaoPrato avaliacaoPrato) {
        // --- Programação Defensiva: Validação de dados de entrada ---

        // 1. Validar campos obrigatórios
        if (avaliacaoPrato.getNota() == null) {
            throw new InputMismatchException("A nota da avaliação é obrigatória.");
        }

        // 2. Validar o valor da nota (por exemplo, de 1 a 5)
        if (avaliacaoPrato.getNota() < 1 || avaliacaoPrato.getNota() > 5) {
            throw new InputMismatchException("A nota deve ser um valor entre 1 e 5.");
        }

        // 3. Validar se o prato associado à avaliação existe
        if (avaliacaoPrato.getPrato() == null || avaliacaoPrato.getPrato().getId() == null) {
            throw new InputMismatchException("É necessário associar a avaliação a um prato.");
        }

        Optional<Prato> pratoExistente = pratoRepository.findById(avaliacaoPrato.getPrato().getId());
        if (pratoExistente.isEmpty()) {
            throw new InputMismatchException("Prato não encontrado. Verifique o ID do prato.");
        }

        // 4. Preencher a data da avaliação automaticamente
        avaliacaoPrato.setDataAvaliacao(LocalDateTime.now());

        // 5. Salvar a entidade no banco de dados
        return avaliacaoPratoRepository.save(avaliacaoPrato);
    }

    /**
     * Busca todas as avaliações de um prato específico.
     * @param pratoId O ID do prato.
     * @return Uma lista de avaliações do prato.
     */
    public List<AvaliacaoPrato> buscarAvaliacoesPorPratoId(Long pratoId) {
        Optional<Prato> prato = pratoRepository.findById(pratoId);
        if (prato.isEmpty()) {
            throw new InputMismatchException("Prato não encontrado.");
        }
        return avaliacaoPratoRepository.findByPrato(prato.get());
    }
}