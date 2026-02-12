package com.example.bullscows;

import java.util.ArrayList;
import java.util.List;

public class NumberGuessingGameTestHarness {
    private static final String TARGET = "1234";
    private static final com.example.bullscows.GameConfig CONFIG = com.example.bullscows.GameConfig.defaultConfig();

    private int passed;
    private int failed;

    public static void main(String[] args) {
        new NumberGuessingGameTestHarness().runAll();
    }

    private void runAll() {
        run("Win on attempt 1", this::testWinOnAttempt1);
        run("Win on attempt 2", this::testWinOnAttempt2);
        run("Win on attempt 3", this::testWinOnAttempt3);
        run("Win on attempt 4", this::testWinOnAttempt4);
        run("Win on attempt 5", this::testWinOnAttempt5);
        run("Win on attempt 6", this::testWinOnAttempt6);
        run("Win on attempt 7", this::testWinOnAttempt7);

        run("Lose with no correct digits", this::testLoseWithNoCorrectDigits);
        run("Lose with 1 correct digit per guess", this::testLoseWithOneCorrectDigit);
        run("Lose with 2 correct digits per guess", this::testLoseWithTwoCorrectDigits);
        run("Lose with 3 correct digits per guess", this::testLoseWithThreeCorrectDigits);
        run("Lose with 4 correct digits but wrong positions", this::testLoseWithFourCorrectDigitsWrongPositions);

        run("Invalid input cases", this::testInvalidInputs);

        System.out.println();
        System.out.println("Tests complete. Passed: " + passed + ", Failed: " + failed);
        if (failed > 0) {
            throw new IllegalStateException("Some tests failed. Check logs above.");
        }
    }

    private void run(String testName, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("[PASS] " + testName);
        } catch (Throwable t) {
            failed++;
            System.out.println("[FAIL] " + testName + " -> " + t.getMessage());
        }
    }

    private void testWinOnAttempt1() {
        testWinOnAttempt(1);
    }

    private void testWinOnAttempt2() {
        testWinOnAttempt(2);
    }

    private void testWinOnAttempt3() {
        testWinOnAttempt(3);
    }

    private void testWinOnAttempt4() {
        testWinOnAttempt(4);
    }

    private void testWinOnAttempt5() {
        testWinOnAttempt(5);
    }

    private void testWinOnAttempt6() {
        testWinOnAttempt(6);
    }

    private void testWinOnAttempt7() {
        testWinOnAttempt(7);
    }

    private void testWinOnAttempt(int winningAttempt) {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        String[] preGuesses = {"5678", "9012", "3456", "6789", "9876", "2468"};

        for (int i = 0; i < winningAttempt - 1; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess(preGuesses[i]);
            assertTrue(result.accepted(), "Pre-guess should be accepted");
            assertFalse(result.won(), "Pre-guess should not win");
            assertTrue(result.score().bulls() >= 0, "Bulls should be non-negative");
            assertTrue(result.score().cows() >= 0, "Cows should be non-negative");
        }

        com.example.bullscows.GameTurnResult win = engine.submitGuess(TARGET);
        assertTrue(win.accepted(), "Winning guess should be accepted");
        assertTrue(win.won(), "Should be marked as won");
        assertEquals(4, win.score().bulls(), "Winning guess should have 4 bulls");
        assertEquals(0, win.score().cows(), "Winning guess should have 0 cows");
        assertEquals(winningAttempt, win.attemptsUsed(), "Attempts used should match winning attempt");
        assertTrue(win.gameOver(), "Game should be over after winning");
    }

    private void testLoseWithNoCorrectDigits() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        for (int i = 0; i < 7; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess("5678");
            assertTrue(result.accepted(), "Guess should be accepted");
            assertEquals(0, result.score().bulls(), "Expected 0 bulls");
            assertEquals(0, result.score().cows(), "Expected 0 cows");
        }
        assertTrue(engine.isGameOver(), "Game should be over");
        assertFalse(engine.isWon(), "Game should be lost");
    }

    private void testLoseWithOneCorrectDigit() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        for (int i = 0; i < 7; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess("1567");
            assertTrue(result.accepted(), "Guess should be accepted");
            assertEquals(1, result.score().bulls() + result.score().cows(), "Expected exactly 1 correct digit");
        }
        assertFalse(engine.isWon(), "Game should be lost");
    }

    private void testLoseWithTwoCorrectDigits() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        for (int i = 0; i < 7; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess("1256");
            assertTrue(result.accepted(), "Guess should be accepted");
            assertEquals(2, result.score().bulls() + result.score().cows(), "Expected exactly 2 correct digits");
        }
        assertFalse(engine.isWon(), "Game should be lost");
    }

    private void testLoseWithThreeCorrectDigits() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        for (int i = 0; i < 7; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess("1245");
            assertTrue(result.accepted(), "Guess should be accepted");
            assertEquals(3, result.score().bulls() + result.score().cows(), "Expected exactly 3 correct digits");
            assertFalse(result.won(), "Should not win");
        }
        assertFalse(engine.isWon(), "Game should be lost");
    }

    private void testLoseWithFourCorrectDigitsWrongPositions() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        for (int i = 0; i < 7; i++) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess("4321");
            assertTrue(result.accepted(), "Guess should be accepted");
            assertEquals(0, result.score().bulls(), "Expected 0 bulls");
            assertEquals(4, result.score().cows(), "Expected 4 cows");
            assertFalse(result.won(), "Should not win");
        }
        assertTrue(engine.isGameOver(), "Game should be over");
        assertFalse(engine.isWon(), "Game should be lost");
    }

    private void testInvalidInputs() {
        com.example.bullscows.GameEngine engine = new com.example.bullscows.GameEngine(TARGET, CONFIG);
        List<String> invalidInputs = new ArrayList<>();
        invalidInputs.add(null);
        invalidInputs.add("");
        invalidInputs.add("123");
        invalidInputs.add("12345");
        invalidInputs.add("12a4");
        invalidInputs.add("0123");
        invalidInputs.add("1223");

        for (String input : invalidInputs) {
            com.example.bullscows.GameTurnResult result = engine.submitGuess(input);
            assertFalse(result.accepted(), "Invalid input should be rejected: " + input);
        }

        assertEquals(7, engine.remainingAttempts(), "Invalid inputs should not consume attempts");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new IllegalStateException(message);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new IllegalStateException(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }

}
