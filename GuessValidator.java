package com.example.bullscows;

import java.util.HashSet;
import java.util.Set;

public class GuessValidator {
    private final int numberLength;

    public GuessValidator(int numberLength) {
        this.numberLength = numberLength;
    }

    public com.example.bullscows.ValidationResult validate(String guess) {
        if (guess == null) {
            return com.example.bullscows.ValidationResult.error("Guess cannot be null");
        }
        if (guess.length() != numberLength) {
            return com.example.bullscows.ValidationResult.error("Guess must be exactly " + numberLength + " digits long");
        }
        if (!guess.chars().allMatch(Character::isDigit)) {
            return com.example.bullscows.ValidationResult.error("Guess must contain only digits");
        }
        if (guess.charAt(0) == '0') {
            return com.example.bullscows.ValidationResult.error("Leading zero is not allowed");
        }
        if (!hasUniqueDigits(guess)) {
            return com.example.bullscows.ValidationResult.error("Digits must be unique");
        }
        return com.example.bullscows.ValidationResult.ok();
    }

    private boolean hasUniqueDigits(String value) {
        Set<Character> seen = new HashSet<>();
        for (char c : value.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }
}
