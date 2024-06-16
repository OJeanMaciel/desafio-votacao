package com.example.voting_system.util;

public class CpfUtil {

    public static String cleanCpf(String cpf) {
        return cpf.replaceAll("\\D", ""); // Remove todos os caracteres que não são dígitos
    }

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // Remove non-digit characters

        if (cpf.length() != 11 || hasAllSameDigits(cpf)) {
            return false;
        }

        return checkDigits(cpf, 9, 10) && checkDigits(cpf, 10, 11);
    }

    private static boolean hasAllSameDigits(String cpf) {
        char firstChar = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkDigits(String cpf, int length, int position) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * (position - i);
        }

        int digit = 11 - (sum % 11);
        if (digit == 10 || digit == 11) {
            digit = 0;
        }

        return digit == cpf.charAt(length) - '0';
    }
}