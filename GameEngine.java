package com.example.bullscows;

public class GameEngine {
    private final String target;
    private final com.example.bullscows.GameConfig config;
    private final com.example.bullscows.GuessValidator validator;
    private final com.example.bullscows.BullsCowsScorer scorer;

    private int attemptsUsed;
    private boolean won;

    public GameEngine(String target, com.example.bullscows.GameConfig config) {
        this(target, config, new com.example.bullscows.GuessValidator(config.numberLength()), new com.example.bullscows.BullsCowsScorer());
    }

    GameEngine(String target, com.example.bullscows.GameConfig config, com.example.bullscows.GuessValidator validator, com.example.bullscows.BullsCowsScorer scorer) {
        this.target = target;
        this.config = config;
        this.validator = validator;
        this.scorer = scorer;

        com.example.bullscows.ValidationResult targetValidation = validator.validate(target);
        if (!targetValidation.valid()) {
            throw new IllegalArgumentException("Invalid target: " + targetValidation.message());
        }
    }

    public com.example.bullscows.GameTurnResult submitGuess(String guess) {
        if (isGameOver()) {
            return new com.example.bullscows.GameTurnResult(false, "Game is already over", null, attemptsUsed, 0, won, true);
        }

        com.example.bullscows.ValidationResult validation = validator.validate(guess);
        if (!validation.valid()) {
            return new com.example.bullscows.GameTurnResult(false, validation.message(), null, attemptsUsed, remainingAttempts(), false, false);
        }

        attemptsUsed++;
        com.example.bullscows.Score score = scorer.score(target, guess);
        won = score.isExactMatch(config.numberLength());

        boolean gameOver = isGameOver();
        String statusMessage = won ? "Correct!" : "Wrong";

        return new com.example.bullscows.GameTurnResult(
                true,
                statusMessage,
                score,
                attemptsUsed,
                remainingAttempts(),
                won,
                gameOver
        );
    }

    public boolean isGameOver() {
        return won || attemptsUsed >= config.maxAttempts();
    }

    public boolean isWon() {
        return won;
    }

    public int remainingAttempts() {
        return config.maxAttempts() - attemptsUsed;
    }

    public String revealTarget() {
        return target;
    }
}
