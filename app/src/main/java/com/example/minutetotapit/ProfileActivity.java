package com.example.minutetotapit;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView currentScoreTextView;
    DatabaseHelper db;
    String username;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);

        username = getIntent().getStringExtra("username");
        currentScoreTextView = findViewById(R.id.currentScoreTextView);
        score = db.getScore(username);
        currentScoreTextView.setText(String.valueOf(score));
    }
}
