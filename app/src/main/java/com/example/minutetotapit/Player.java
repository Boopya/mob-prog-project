package com.example.minutetotapit;

public class Player implements PlayerConstants {
    private String username;
    private int score;

    public Player() {
        username = DEFAULT_USERNAME;
        score = DEFAULT_SCORE;
    }

    public void setPlayerName(String username) {
        this.username = username;
    }

    public String getPlayerName() {
        return username;
    }

    public void setPlayerScore(int score) {
        this.score = score;
    }

    public int getPlayerScore() {
        return score;
    }
}
