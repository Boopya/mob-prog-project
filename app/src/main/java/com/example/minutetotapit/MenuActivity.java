package com.example.minutetotapit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MenuActivity extends AppCompatActivity {
    // views and object declaration
    Button playButton, highScoreButton;
    TextView signOutTextView, viewProfileTextView;
    DatabaseHelper db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // create a DatabaseHelper object
        db = new DatabaseHelper(this);

        // fetch username from login activity
        username = getIntent().getStringExtra("username");

        // map widgets to the program
        playButton = findViewById(R.id.playButton);
        highScoreButton = findViewById(R.id.highScoreButton);
        signOutTextView = findViewById(R.id.signOutTextView);
        viewProfileTextView = findViewById(R.id.viewProfileTextView);

        // set an on-click listener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent object
                Intent intent = new Intent(MenuActivity.this, TapActivity.class);
                // pass the username to tap activity for later score recording
                intent.putExtra("username", username);
                // start the tap activity
                startActivity(intent);
            }
        });

        // set an on-click listener
        viewProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent object
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                // pass the username to profile activity through the intent object
                intent.putExtra("username", username);
                // start the profile activity
                startActivity(intent);
            }
        });

        // set an on-click listener
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fetch the leaderboard
                Player[] leaderboard = db.getLeaderboard();
                // create an Intent object
                Intent intent = new Intent(MenuActivity.this, LeaderboardActivity.class);
                // pass the top players' username and score through the intent object
                for(int i = 0; i < leaderboard.length; i++) {
                    intent.putExtra("username_" + i, leaderboard[i].getPlayerName());
                    intent.putExtra("score_" + i, leaderboard[i].getPlayerScore());
                }
                // start the leaderboard activity
                startActivity(intent);
            }
        });

        // set an on-click listener
        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent object
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                // start the login activity
                startActivity(intent);
                // close the current activity
                finish();
            }
        });
    }

    // create an options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    // create an options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                DialogFragment settingsDialogFragment = new SettingsDialogFragment(username, getApplicationContext());
                settingsDialogFragment.show(getSupportFragmentManager(), "settings");
                return true;
            case R.id.help:
                DialogFragment helpDialogFragment = new HelpDialogFragment();
                helpDialogFragment.show(getSupportFragmentManager(), "help");
                return true;
            case R.id.about:
                DialogFragment aboutDialogFragment = new AboutDialogFragment();
                aboutDialogFragment.show(getSupportFragmentManager(), "about");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
