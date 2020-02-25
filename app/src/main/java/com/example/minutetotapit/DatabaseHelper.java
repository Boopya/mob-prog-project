package com.example.minutetotapit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Variables declaration
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "users";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";
    public static final String SCORE_COLUMN = "score";

    // SQL Commands
    private final String CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USERNAME_COLUMN + " TEXT PRIMARY KEY, " +
                    PASSWORD_COLUMN + " TEXT, " +
                    SCORE_COLUMN + " INTEGER DEFAULT 0)";

    private static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String QUERY_ENTRIES = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_COLUMN + " = ?";

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

        if(insert == -1)
            return false;

        else
            return true;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_ENTRIES, new String[]{username});

        if(cursor.getCount() > 0)
            return true;

        else
            return false;
    }

    public boolean isValidCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                USERNAME_COLUMN + "=?" + " AND " + PASSWORD_COLUMN + "=?", new String[]{username, password});

        if(cursor.getCount() == 1)
            return true;
        else
            return false;
    }

    public int getScore(String username) {
        int score = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + SCORE_COLUMN + ") FROM " + TABLE_NAME + " WHERE "
                + USERNAME_COLUMN + "=?", new String[]{username});

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
        int rows = db.update(TABLE_NAME, contentValues, USERNAME_COLUMN + "=?", new String[]{username});
    }
}
