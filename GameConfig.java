package com.example.bullscows;

public record GameConfig(int numberLength, int maxAttempts) {
    public GameConfig {
        if (numberLength < 1) {
            throw new IllegalArgumentException("numberLength must be positive");
        }
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be positive");
        }
    }

    public static GameConfig defaultConfig() {
        return new GameConfig(4, 7);
    }
}
