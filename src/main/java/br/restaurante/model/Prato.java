package br.restaurante.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Prato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "prato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvaliacaoPrato> avaliacoes;

    // Construtor, getters e setters (gerados manualmente ou pelo Lombok)

    public Prato() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public List<AvaliacaoPrato> getAvaliacoes() {
        return avaliacoes;
    }

    // Setter ajustado para definir a lista completa
    public void setAvaliacoes(List<AvaliacaoPrato> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    // Método opcional para adicionar uma avaliação individualmente
    public void addAvaliacao(AvaliacaoPrato avaliacao) {
        this.avaliacoes.add(avaliacao);
    }
}