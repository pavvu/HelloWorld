package com.example.bullscows;

public record GameTurnResult(
        boolean accepted,
        String message,
        com.example.bullscows.Score score,
        int attemptsUsed,
        int attemptsLeft,
        boolean won,
        boolean gameOver
) {
}
