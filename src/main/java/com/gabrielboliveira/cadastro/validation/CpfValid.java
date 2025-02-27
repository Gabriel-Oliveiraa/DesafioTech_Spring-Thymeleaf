package com.gabrielboliveira.cadastro.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Constraint(validatedBy = CpfValidator.class)
public @interface CpfValid {

    String message() default "CPF inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class CpfValidator implements ConstraintValidator<CpfValid, String> {

    // Regex para garantir que o CPF tenha exatamente 11 dígitos numéricos
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    @Override
    public void initialize(CpfValid constraintAnnotation) {
        // Não há necessidade de inicializar algo nesse caso.
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        // Verificar se o CPF é nulo ou não contém 11 caracteres
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        // Verificar se o CPF é composto apenas por números
        Matcher matcher = CPF_PATTERN.matcher(cpf);
        if (!matcher.matches()) {
            return false;
        }

        // Verificar se o CPF não é uma sequência repetida de números (exemplo: 11111111111)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validar os dígitos verificadores
        return validarCpf(cpf);
    }

    /*
     * Valida o CPF com base nos 9 primeiros dígitos e nos dois dígitos verificadores.
     * @param cpf o CPF a ser validado.
     * @return true se o CPF for válido, false caso contrário.
     */
    private boolean validarCpf(String cpf) {
        int[] peso1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String digitos = cpf.substring(0, 9);
        int soma = 0;

        // Validação do primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(digitos.charAt(i))) * peso1[i];
        }
        int primeiroDigito = (soma * 10) % 11;
        if (primeiroDigito == 10) {
            primeiroDigito = 0;
        }

        // Validação do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(digitos.charAt(i))) * peso2[i];
        }
        soma += primeiroDigito * 2;
        int segundoDigito = (soma * 10) % 11;
        if (segundoDigito == 10) {
            segundoDigito = 0;
        }

        // Comparando os dois últimos dígitos do CPF
        return cpf.endsWith(String.valueOf(primeiroDigito) + segundoDigito);
    }
}
