package com.example.minutetotapit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    TextView[] playerTextView;
    TextView[] scoreTextView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        db = new DatabaseHelper(this);
        int size = (int) db.getUsersCount();
        playerTextView = new TextView[size];
        scoreTextView = new TextView[size];
        Bundle extras = getIntent().getExtras();

        for (int i = 0; i < size; i++) {
            String player = "player_" + i;
            String score = "score_" + i;
            int playerID = getResources().getIdentifier(player, "id", getPackageName());
            int scoreID = getResources().getIdentifier(score, "id", getPackageName());
            playerTextView[i] = findViewById(playerID);
            scoreTextView[i] = findViewById(scoreID);
            playerTextView[i].setText(extras.getString("username_" + i));
            scoreTextView[i].setText(Integer.toString(extras.getInt("score_" + i)));

        }
    }
}
