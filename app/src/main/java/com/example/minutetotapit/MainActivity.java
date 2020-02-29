package com.example.minutetotapit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    // views declaration
    Button pressButton;
    ImageView titleImageView, tapImageView;
    Animation titleAnimation, tapImageAnimation, pressButtonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // map widgets to the program
        pressButton = findViewById(R.id.pressButton);
        titleImageView = findViewById(R.id.titleImageView);
        tapImageView = findViewById(R.id.tapImageView);

        // load animations
        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_animation);
        tapImageAnimation = AnimationUtils.loadAnimation(this, R.anim.tap_img_animation);
        pressButtonAnimation = AnimationUtils.loadAnimation(this, R.anim.press_btn_animation);

        // set the splash screen animation
        titleImageView.setAnimation(titleAnimation);
        tapImageView.setAnimation(tapImageAnimation);
        pressButton.setAnimation(pressButtonAnimation);

        // set an on-click listener
        pressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent object
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // start the login activity
                startActivity(intent);
            }
        });
    }
}
