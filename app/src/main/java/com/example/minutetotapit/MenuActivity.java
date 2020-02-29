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

    Button playButton, highScoreButton;
    TextView signOutTextView, viewProfileTextView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = findViewById(R.id.playButton);
        highScoreButton = findViewById(R.id.highScoreButton);
        signOutTextView = findViewById(R.id.signOutTextView);
        viewProfileTextView = findViewById(R.id.viewProfileTextView);
        db = new DatabaseHelper(this);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(MenuActivity.this, TapActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player[] leaderboard = db.getLeaderboard();
                Intent intent = new Intent(MenuActivity.this, LeaderboardActivity.class);
                for(int i = 0; i < leaderboard.length; i++) {
                    intent.putExtra("username_" + i, leaderboard[i].getPlayerName());
                    intent.putExtra("score_" + i, leaderboard[i].getPlayerScore());
                }
                startActivity(intent);
            }
        });

        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                DialogFragment settingsDialogFragment = new SettingsDialogFragment();
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
