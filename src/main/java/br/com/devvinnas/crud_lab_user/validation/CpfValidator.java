package br.com.devvinnas.crud_lab_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public void initialize(Cpf constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Remove non-numeric characters
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF must have 11 digits
        if (cpf.length() != 11) {
            return false;
        }

        // Check for known invalid CPFs (all digits equal)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calculate first verifying digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier > 9) {
            firstVerifier = 0;
        }

        // Check first verifying digit
        if ((cpf.charAt(9) - '0') != firstVerifier) {
            return false;
        }

        // Calculate second verifying digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier > 9) {
            secondVerifier = 0;
        }

        // Check second verifying digit
        return (cpf.charAt(10) - '0') == secondVerifier;
    }
}