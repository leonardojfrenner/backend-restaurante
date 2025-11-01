package br.restaurante.repository;

import br.restaurante.model.Pedido;
import br.restaurante.model.Cliente;
import br.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteOrderByCriadoEmDesc(Cliente cliente);
    List<Pedido> findByRestauranteOrderByCriadoEmDesc(Restaurante restaurante);
}

