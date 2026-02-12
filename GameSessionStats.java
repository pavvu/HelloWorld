package com.example.bullscows;

public class GameSessionStats {
    private int roundsPlayed;
    private int wins;
    private int losses;
    private int totalAttemptsUsed;

    public void recordRound(boolean won, int attemptsUsed) {
        roundsPlayed++;
        totalAttemptsUsed += attemptsUsed;
        if (won) {
            wins++;
        } else {
            losses++;
        }
    }

    public int roundsPlayed() {
        return roundsPlayed;
    }

    public int wins() {
        return wins;
    }

    public int losses() {
        return losses;
    }

    public int totalAttemptsUsed() {
        return totalAttemptsUsed;
    }

    public double winRatePercent() {
        if (roundsPlayed == 0) {
            return 0.0;
        }
        return (wins * 100.0) / roundsPlayed;
    }

    public double lossRatePercent() {
        if (roundsPlayed == 0) {
            return 0.0;
        }
        return (losses * 100.0) / roundsPlayed;
    }
}
