package com.gabrielboliveira.cadastro.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class DadosPessoaisDTO {

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve conter 11 números ou estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O RG é obrigatório")
    @Pattern(regexp = "[\\d.-]+", message = "RG deve conter apenas números, pontos e traços")
    private String rg;

    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotBlank(message = "O nome da mãe é obrigatório")
    @Size(min = 3, max = 100, message = "Nome da mãe deve ter entre 3 e 100 caracteres")
    private String nomeMae;

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    // Método para formatar o CPF
    public String getCpfFormatado() {
        if (cpf != null && cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
        }
        return cpf;
    }

    // Validação de CPF (Método de Validação Customizada)
    public boolean isCpfValido() {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        // Lógica de validação do CPF (simplificada para este exemplo)
        // Pode ser expandida conforme necessário
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;
        soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;

        return cpf.charAt(9) == (char) ('0' + digito1) && cpf.charAt(10) == (char) ('0' + digito2);
    }
}
