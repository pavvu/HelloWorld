package com.example.bullscows;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SecretNumberGenerator {
    private final Random random;

    public SecretNumberGenerator() {
        this(new SecureRandom());
    }

    public SecretNumberGenerator(Random random) {
        this.random = random;
    }

    public String generate(int length) {
        if (length < 1 || length > 10) {
            throw new IllegalArgumentException("length must be between 1 and 10");
        }

        List<Character> digits = new ArrayList<>(10);
        for (char c = '0'; c <= '9'; c++) {
            digits.add(c);
        }

        Collections.shuffle(digits, random);

        if (digits.get(0) == '0') {
            for (int i = 1; i < digits.size(); i++) {
                if (digits.get(i) != '0') {
                    Collections.swap(digits, 0, i);
                    break;
                }
            }
        }

        StringBuilder secret = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            secret.append(digits.get(i));
        }
        System.out.println("Secret Number : " + secret);
        return secret.toString();
    }
}
