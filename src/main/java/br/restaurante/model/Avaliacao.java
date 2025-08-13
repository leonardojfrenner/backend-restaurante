package br.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double nota; // Representa a avaliação (e.g., de 1 a 5)

    @Column(length = 500)
    private String comentario;

    // Relacionamento com a entidade Restaurante
    @ManyToOne
    @JsonIgnoreProperties({"avaliacoes", "senha"})
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    // Relacionamento com a entidade Cliente que fez a avaliação
    @ManyToOne
    @JsonIgnoreProperties({"senha"})
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime dataAvaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
}