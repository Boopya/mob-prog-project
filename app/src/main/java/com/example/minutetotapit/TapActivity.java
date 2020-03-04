package com.example.minutetotapit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TapActivity extends AppCompatActivity implements TimerConstants {
    // views, objects, and variables declaration
    Button startButton;
    ImageView tapButton;
    TextView scoreTextView, timerTextView;
    Timer timer;
    DatabaseHelper db;
    String username;
    boolean tapButtonIsClickable;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        // set score initially to 0
        score = 0;

        // create DatabaseHelper object
        db = new DatabaseHelper(this);

        // create Timer object
        timer = new Timer(START_TIME, INTERVAL);

        // fetch username from menu activity
        username = getIntent().getStringExtra("username");

        // map widgets to the program
        startButton = findViewById(R.id.startButton);
        tapButton = findViewById(R.id.tapButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);

        // set an on-click listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set score text view to 0
                scoreTextView.setText(Integer.toString(score));
                // make tap button clickable
                tapButtonIsClickable = true;
                // start the timer
                timer.start();
                // disable the start button
                startButton.setVisibility(View.INVISIBLE);
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if tap button is clickable
                if(tapButtonIsClickable) {
                    // increment score
                    score++;
                    // update score text view to current score's value
                    scoreTextView.setText(Integer.toString(score));
                }
            }
        });
    }

    // create an inner Timer class for the actual game timer
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
            timerTextView.setText("Time's up! Your score is ");
            if(score > db.getScore(username)) {
                db.updateScore(username, score);
            }
            tapButtonIsClickable = false;
            startButton.setVisibility(View.VISIBLE);
            score = 0;
        }
    }
}