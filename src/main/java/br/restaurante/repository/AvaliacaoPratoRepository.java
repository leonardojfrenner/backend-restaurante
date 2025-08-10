package br.restaurante.repository;

import br.restaurante.model.AvaliacaoPrato;
import br.restaurante.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoPratoRepository extends JpaRepository<AvaliacaoPrato, Long> {

    // Com o JpaRepository, você já tem os métodos CRUD básicos.
    // Aqui, podemos adicionar métodos personalizados de busca, que são muito úteis para avaliações.

    /**
     * Busca todas as avaliações de um prato específico.
     * @param prato O objeto Prato para o qual as avaliações são procuradas.
     * @return Uma lista de avaliações do prato.
     */
    List<AvaliacaoPrato> findByPrato(Prato prato);

    /**
     * Busca avaliações por uma nota específica, em um prato específico.
     * @param prato O objeto Prato.
     * @param nota A nota da avaliação.
     * @return Uma lista de avaliações que correspondem aos critérios.
     */
    List<AvaliacaoPrato> findByPratoAndNota(Prato prato, Double nota);

    /**
     * Busca todas as avaliações com nota maior ou igual a um valor.
     * @param nota O valor mínimo da nota.
     * @return Uma lista de avaliações.
     */
    List<AvaliacaoPrato> findByNotaGreaterThanEqual(Double nota);
}