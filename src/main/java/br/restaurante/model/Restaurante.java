package br.restaurante.model;

import jakarta.persistence.*;
import lombok.Data; // Para simplificar getters e setters

import java.util.List;

@Entity
@Data // Adiciona getters, setters, toString, etc. do Lombok
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 100)
    private String rua;

    @Column(length = 6)
    private Integer numero;

    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Column(length = 50)
    private String estado;

    @Column(length = 9)
    private String cep;

    @Column(length = 500)
    private String descricao;

    private String horario;

    private Integer lotacao;

    private String site;

    private String facebook;

    private String instagram;

    private String whatsapp;

    // Campos de arquivo, normalmente guardamos o caminho do arquivo e não o arquivo em si
    private String cardapioUrl;
    private String logoUrl;
    private String bannerUrl;

    // Campos booleanos
    private boolean aceitaComunicacao;
    private boolean aceitaMarketing;
    private boolean aceitaProtecaoDados;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    public Restaurante() {
    }

    public Restaurante(Long id, String nome, String cnpj, String telefone, String email, String senha, String rua, Integer numero, String bairro, String cidade, String estado,
                       String cep, String descricao, String horario, Integer lotacao, String site, String facebook, String instagram,
                       String whatsapp, String cardapioUrl, String logoUrl, String bannerUrl, boolean aceitaComunicacao, boolean aceitaMarketing,
                       boolean aceitaProtecaoDados) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.descricao = descricao;
        this.horario = horario;
        this.lotacao = lotacao;
        this.site = site;
        this.facebook = facebook;
        this.instagram = instagram;
        this.whatsapp = whatsapp;
        this.cardapioUrl = cardapioUrl;
        this.logoUrl = logoUrl;
        this.bannerUrl = bannerUrl;
        this.aceitaComunicacao = aceitaComunicacao;
        this.aceitaMarketing = aceitaMarketing;
        this.aceitaProtecaoDados = aceitaProtecaoDados;
    }

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getLotacao() {
        return lotacao;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCardapioUrl() {
        return cardapioUrl;
    }

    public void setCardapioUrl(String cardapioUrl) {
        this.cardapioUrl = cardapioUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public boolean isAceitaComunicacao() {
        return aceitaComunicacao;
    }

    public void setAceitaComunicacao(boolean aceitaComunicacao) {
        this.aceitaComunicacao = aceitaComunicacao;
    }

    public boolean isAceitaMarketing() {
        return aceitaMarketing;
    }

    public void setAceitaMarketing(boolean aceitaMarketing) {
        this.aceitaMarketing = aceitaMarketing;
    }

    public boolean isAceitaProtecaoDados() {
        return aceitaProtecaoDados;
    }

    public void setAceitaProtecaoDados(boolean aceitaProtecaoDados) {
        this.aceitaProtecaoDados = aceitaProtecaoDados;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Double getAvaliacaoMedia() {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return 0.0;
        }
        double soma = 0.0;
        for (Avaliacao avaliacao : avaliacoes) {
            soma += avaliacao.getNota(); // Aqui o getNota() é chamado diretamente
        }
        return soma / avaliacoes.size();
    }
}