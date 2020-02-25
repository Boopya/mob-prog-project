package com.example.minutetotapit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class TapActivity extends AppCompatActivity {
    private Button startButton;
    private ImageButton tapButton;
    private TextView scoreTextView, timerTextView;
    private Timer timer;
    private boolean isTapButtonClickable;
    private int score;
    private final long START_TIME = 3000;
    private final long INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        startButton = findViewById(R.id.startButton);
        tapButton = findViewById(R.id.tapButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        timer = new Timer(START_TIME, INTERVAL);
        score = 0;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTextView.setText(Integer.toString(score));
                isTapButtonClickable = true;
                timer.start();
                startButton.setEnabled(false);
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTapButtonClickable) {
                    score++;
                    scoreTextView.setText(Integer.toString(score));
                }
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

    protected class Timer extends CountDownTimer {
        public Timer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setText(millisUntilFinished / 1000 + " seconds remaining");
        }

        @Override
        public void onFinish() {
            String username = getIntent().getStringExtra("username");
            DatabaseHelper db = new DatabaseHelper(TapActivity.this);
            db.updateScore(username, score);
            isTapButtonClickable = false;
            timerTextView.setText("Time's up! Your score is ");
            startButton.setEnabled(true);
            score = 0;
        }
    }
}