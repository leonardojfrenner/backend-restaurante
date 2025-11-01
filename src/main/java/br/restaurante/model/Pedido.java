package br.restaurante.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"senha"})
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id")
    @JsonIgnoreProperties({"senha", "avaliacoes"})
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    private String observacoesGerais;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(length = 20)
    private String status; // NOVO, EM_PREPARO, CONCLUIDO, CANCELADO

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        if (this.status == null) this.status = "NOVO";
    }

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Restaurante getRestaurante() { return restaurante; }
    public void setRestaurante(Restaurante restaurante) { this.restaurante = restaurante; }
    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    public String getObservacoesGerais() { return observacoesGerais; }
    public void setObservacoesGerais(String observacoesGerais) { this.observacoesGerais = observacoesGerais; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

