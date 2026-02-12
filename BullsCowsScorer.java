package com.example.bullscows;

import java.util.HashSet;
import java.util.Set;

public class BullsCowsScorer {
    public com.example.bullscows.Score score(String target, String guess) {
        if (target == null || guess == null) {
            throw new IllegalArgumentException("target and guess must not be null");
        }
        if (target.length() != guess.length()) {
            throw new IllegalArgumentException("target and guess must have the same length");
        }

        int bulls = 0;
        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) == guess.charAt(i)) {
                bulls++;
            }
        }

        Set<Character> targetDigits = new HashSet<>();
        for (char c : target.toCharArray()) {
            targetDigits.add(c);
        }

        int matchesIgnoringPosition = 0;
        for (char c : guess.toCharArray()) {
            if (targetDigits.contains(c)) {
                matchesIgnoringPosition++;
            }
        }

        int cows = matchesIgnoringPosition - bulls;
        return new com.example.bullscows.Score(bulls, cows);
    }
}
