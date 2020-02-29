package com.example.minutetotapit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseConstants, SQLStatements {
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        if(cursor.getCount() > 0) { return true; }
        else { return false; }
    }

    public boolean isValidCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(CHECK_CREDENTIALS, new String[]{username, password});
        if(cursor.getCount() == 1) return true;
        else return false;
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
        Player[] leaderboard = new Player[(int)count];
        int i = 0;

        Cursor cursor = db.rawQuery(GET_LEADERBOARD, null);
        if(cursor.moveToFirst()) {
            do {
                leaderboard[i] = new Player();
                leaderboard[i].setPlayerName(cursor.getString(0));
                leaderboard[i].setPlayerScore(cursor.getInt(1));
                i++;
            } while(cursor.moveToNext() && i < leaderboard.length);
        }

        return leaderboard;
    }

    public long getUsersCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }
}
