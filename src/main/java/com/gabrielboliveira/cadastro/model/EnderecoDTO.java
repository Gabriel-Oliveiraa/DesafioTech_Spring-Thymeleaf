package com.gabrielboliveira.cadastro.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 3, max = 100, message = "O logradouro deve ter entre 3 e 100 caracteres")
    private String logradouro;

    @NotBlank(message = "O número é obrigatório")
    @Pattern(regexp = "\\d+", message = "O número deve conter apenas números")
    private String numero;

    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(min = 3, max = 50, message = "O bairro deve ter entre 3 e 50 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 3, max = 50, message = "A cidade deve ter entre 3 e 50 caracteres")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "O estado deve conter exatamente 2 caracteres (sigla do estado)")
    private String estado;

    // Construtor com todos os atributos
    public EnderecoDTO(String cep, String logradouro, String numero, String complemento, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Construtor vazio para o Spring criar instâncias sem valores
    public EnderecoDTO() {}

    // Construtor de cópia
    public EnderecoDTO(EnderecoDTO outro) {
        this.cep = outro.cep;
        this.logradouro = outro.logradouro;
        this.numero = outro.numero;
        this.complemento = outro.complemento;
        this.bairro = outro.bairro;
        this.cidade = outro.cidade;
        this.estado = outro.estado;
    }

    // Getters e Setters
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    // Para formatar o endereço completo
    public String getEnderecoCompleto() {
        return logradouro + ", " + numero + (complemento != null && !complemento.isEmpty() ? " - " + complemento : "")
                + ", " + bairro + " - " + cidade + " / " + estado + " - " + cep;
    }

}