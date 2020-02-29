package com.example.minutetotapit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class LeaderboardActivity extends AppCompatActivity implements PlayerConstants {
    // views and object declaration
    TextView[] playerTextView;
    TextView[] scoreTextView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // create a DatabaseHelper object
        db = new DatabaseHelper(this);

        // initialize player and score text views
        playerTextView = new TextView[TOP_PLAYERS_SIZE];
        scoreTextView = new TextView[TOP_PLAYERS_SIZE];

        // get extras pass from intent in menu activity
        Bundle extras = getIntent().getExtras();

        // loop through 5 times
        for (int i = 0; i < TOP_PLAYERS_SIZE; i++) {
            // initialize to String objects each player and its score id
            String player = "player_" + i;
            String score = "score_" + i;
            // get the id of each player and its score
            int playerID = getResources().getIdentifier(player, "id", getPackageName());
            int scoreID = getResources().getIdentifier(score, "id", getPackageName());
            // map each player and score text view to the program
            playerTextView[i] = findViewById(playerID);
            scoreTextView[i] = findViewById(scoreID);
            // set username and score for each player and score text view
            playerTextView[i].setText(extras.getString("username_" + i));
            scoreTextView[i].setText(Integer.toString(extras.getInt("score_" + i)));
        }
    }

    // create an options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    // add listeners to option menu items
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
