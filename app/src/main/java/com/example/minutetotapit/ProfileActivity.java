package com.example.minutetotapit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    // views, objects, and variable declaration
    TextView currentScoreTextView;
    DatabaseHelper db;
    String username;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // create a DatabaseHelper object
        db = new DatabaseHelper(this);

        // fetch username from menu activity
        username = getIntent().getStringExtra("username");

        // get score of user
        score = db.getScore(username);

        // map view to the program
        currentScoreTextView = findViewById(R.id.currentScoreTextView);

        // set the current highest score record of user
        currentScoreTextView.setText(Integer.toString(score));
    }
}
