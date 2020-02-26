package com.example.minutetotapit;

public interface SQLMethods {
    boolean insertData(String username, String password);
    boolean checkUsername(String username);
    boolean isValidCredentials(String username, String password);
    int getScore(String username);
    void updateScore(String username, int score);
}
