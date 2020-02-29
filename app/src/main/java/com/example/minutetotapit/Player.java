package com.example.minutetotapit;

public class Player {
    private String username;
    private int score;

    public Player() {
        username = "";
        score = 0;
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
