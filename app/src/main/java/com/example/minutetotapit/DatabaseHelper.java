package com.example.minutetotapit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseConstants, PlayerConstants, SQLStatements {

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME_COLUMN, username);
        contentValues.put(PASSWORD_COLUMN, password);

        long insert = db.insert(TABLE_NAME, null, contentValues);

        return insert == -1 ? false :  true;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_ENTRIES, new String[]{username});

        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isValidCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(CHECK_CREDENTIALS, new String[]{username, password});

        if(cursor.getCount() == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getScore(String username) {
        int score = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_USER_SCORE, new String[]{username});

        if(cursor.moveToFirst()) {
            do {
                score = cursor.getInt(0);
            } while(cursor.moveToNext());
        }

        cursor.close();

        return score;
    }

    public void updateScore(String username, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SCORE_COLUMN, score);

        db.update(TABLE_NAME, contentValues, USERNAME_COLUMN + "=?", new String[]{username});
    }

    public Player[] getLeaderboard() {
        SQLiteDatabase db = this.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        Player[] players = new Player[(int)count];
        Player[] leaderboard = new Player[TOP_PLAYERS_SIZE];

        Cursor cursor = db.rawQuery(GET_LEADERBOARD, null);

        int i = 0;

        if(cursor.moveToFirst()) {
            do {
                players[i] = new Player();
                players[i].setPlayerName(cursor.getString(0));
                players[i].setPlayerScore(cursor.getInt(1));
                i++;
            } while(cursor.moveToNext() && i < players.length);
        }

        // selection sort for sorting users based on their scores
        // from highest to lowest
        for(i = 1; i < count; i++) {
            Player player = players[i];
            int key = players[i].getPlayerScore();
            int j = i - 1;
            while(j > -1 && players[j].getPlayerScore() < key) {
                players[j+1] = players[j];
                j--;
            }
            players[j+1] = player;
        }

        for(i = 0; i < TOP_PLAYERS_SIZE; i++) {
            leaderboard[i] = new Player();

            if(i >= players.length) {
                Player player = new Player();
                leaderboard[i].setPlayerName(player.getPlayerName());
                System.out.println(leaderboard[i].getPlayerName());
                leaderboard[i].setPlayerScore(player.getPlayerScore());
                System.out.println(leaderboard[i].getPlayerScore());
            }
            else {
                leaderboard[i] = players[i];
            }
        }

        return leaderboard;
    }

    public void deleteAccount(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, USERNAME_COLUMN + "=?", new String[]{username});
    }
}
