package com.example.bullscows;

import java.util.Scanner;

public class NumberGuessingGameCli {
    private static final String ATTEMPT_SEPARATOR = "------------------------------";
    private static final String ROUND_SEPARATOR = "========================================";
    private static final String BULL_EMOJI = "🐂";
    private static final String COW_EMOJI = "🐄";

    public static void main(String[] args) {
        com.example.bullscows.GameConfig config = com.example.bullscows.GameConfig.defaultConfig();
        com.example.bullscows.SecretNumberGenerator generator = new com.example.bullscows.SecretNumberGenerator();
        com.example.bullscows.GameSessionStats stats = new com.example.bullscows.GameSessionStats();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(ROUND_SEPARATOR);
            System.out.println("Welcome to Bulls & Cows");
            System.out.println("Guess the " + config.numberLength() + "-digit secret number.");
            System.out.println("Rules: unique digits, no leading zero, attempts: " + config.maxAttempts());
            System.out.println(ROUND_SEPARATOR);

            boolean continuePlaying = true;
            while (continuePlaying) {
                com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(generator.generate(config.numberLength()), config);
                int roundNumber = stats.roundsPlayed() + 1;
                printRoundHeader(roundNumber);
                playSingleRound(scanner, engine, config, roundNumber);
                recordRound(stats, engine, config);
                printRoundScore(stats);
                continuePlaying = askToContinue(scanner);
            }
        }

        printFinalScore(stats);
    }

    static void playSingleRound(Scanner scanner, com.example.bullscows.GameEngine engine, com.example.bullscows.GameConfig config, int roundNumber) {
        while (!engine.isGameOver()) {
            int attemptNumber = (config.maxAttempts() - engine.remainingAttempts()) + 1;
            System.out.println(ATTEMPT_SEPARATOR);
            System.out.println("Round " + roundNumber + " | Attempt " + attemptNumber + "/" + config.maxAttempts());
            System.out.print("Enter guess: ");
            String input = scanner.nextLine().trim();

            com.example.bullscows.GameTurnResult result = engine.submitGuess(input);
            if (!result.accepted()) {
                System.out.println("Invalid guess: " + result.message());
                System.out.println(ATTEMPT_SEPARATOR);
                continue;
            }

            System.out.println(result.message());
            System.out.println(BULL_EMOJI + " Bulls: " + result.score().bulls()
                    + " | " + COW_EMOJI + " Cows: " + result.score().cows());
            System.out.println("Attempts left: " + result.attemptsLeft());
            System.out.println(ATTEMPT_SEPARATOR);
        }

        if (engine.isWon()) {
            System.out.println("You won this round!");
        } else {
            System.out.println("You lost this round. Secret number was: " + engine.revealTarget());
        }
    }

    static void recordRound(com.example.bullscows.GameSessionStats stats, com.example.bullscows.GameEngine engine, com.example.bullscows.GameConfig config) {
        int attemptsUsed = config.maxAttempts() - engine.remainingAttempts();
        stats.recordRound(engine.isWon(), attemptsUsed);
    }

    static void printRoundScore(com.example.bullscows.GameSessionStats stats) {
        System.out.println(ROUND_SEPARATOR);
        System.out.println("Score so far -> Wins: " + stats.wins() + ", Losses: " + stats.losses());
        System.out.println(ROUND_SEPARATOR);
    }

    static void printRoundHeader(int roundNumber) {
        System.out.println();
        System.out.println(ROUND_SEPARATOR);
        System.out.println("Starting Round " + roundNumber);
        System.out.println(ROUND_SEPARATOR);
    }

    static boolean askToContinue(Scanner scanner) {
        while (true) {
            System.out.print("Play another round? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(input) || "yes".equals(input)) {
                return true;
            }
            if ("n".equals(input) || "no".equals(input)) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }

    static void printFinalScore(com.example.bullscows.GameSessionStats stats) {
        System.out.println();
        System.out.println(ROUND_SEPARATOR);
        System.out.println("Final score");
        System.out.println("Rounds played: " + stats.roundsPlayed());
        System.out.println("Wins: " + stats.wins());
        System.out.println("Losses: " + stats.losses());
        System.out.printf("Win rate: %.2f%%%n", stats.winRatePercent());
        System.out.printf("Loss rate: %.2f%%%n", stats.lossRatePercent());
        System.out.println(ROUND_SEPARATOR);
    }
}
