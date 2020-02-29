package com.example.minutetotapit;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
