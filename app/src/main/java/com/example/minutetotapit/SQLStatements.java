package com.example.minutetotapit;

public interface SQLStatements extends DatabaseConstants {
    String CREATE_ENTRIES =
        "CREATE TABLE " + TABLE_NAME + " (" +
                USERNAME_COLUMN + " TEXT PRIMARY KEY, " +
                PASSWORD_COLUMN + " TEXT, " +
                SCORE_COLUMN + " INTEGER DEFAULT 0" +
        ")";

    String DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    String QUERY_ENTRIES =
        "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_COLUMN + " = ?";

    String GET_USER_SCORE =
        "SELECT MAX(" + SCORE_COLUMN + ") FROM " + TABLE_NAME + " WHERE " + USERNAME_COLUMN + "=?";

    String CHECK_CREDENTIALS =
        "SELECT * FROM " + TABLE_NAME + " WHERE " +
            USERNAME_COLUMN + "=?" + " AND " +
            PASSWORD_COLUMN + "=?";

    String GET_LEADERBOARD =
        "SELECT " + USERNAME_COLUMN + ", " + SCORE_COLUMN + " FROM " + TABLE_NAME + " WHERE ROWNUM = 5";
}
