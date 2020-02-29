package com.example.minutetotapit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements NotificationConstants {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.tap)
                .setContentTitle("Hey!")
                .setContentText("Come back, please. Don't leave.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }
}
