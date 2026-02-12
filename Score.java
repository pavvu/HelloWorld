package com.example.bullscows;

public record Score(int bulls, int cows) {
    public Score {
        if (bulls < 0 || cows < 0) {
            throw new IllegalArgumentException("bulls and cows cannot be negative");
        }
    }

    public boolean isExactMatch(int numberLength) {
        return bulls == numberLength;
    }
}
