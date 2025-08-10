package br.restaurante.repository;

import br.restaurante.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {

    // Métodos de busca personalizados, que o Spring Data JPA implementa automaticamente.

    /**
     * Busca todos os pratos de um restaurante específico, ordenados pelo nome.
     * @param restauranteId O ID do restaurante.
     * @return Uma lista de pratos.
     */
    List<Prato> findByRestauranteIdOrderByNomeAsc(Long restauranteId);

    /**
     * Busca pratos pelo nome, ignorando maiúsculas e minúsculas.
     * @param nome O nome do prato.
     * @return Uma lista de pratos que correspondem ao nome.
     */
    List<Prato> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca pratos com preço menor ou igual a um valor específico.
     * @param preco O valor máximo do preço.
     * @return Uma lista de pratos que correspondem ao critério.
     */
    List<Prato> findByPrecoLessThanEqual(Double preco);
}