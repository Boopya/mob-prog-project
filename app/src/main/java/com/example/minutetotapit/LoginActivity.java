package com.example.minutetotapit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    // views and object declaration
    EditText usernameLoginEditText, passwordLoginEditText;
    Button signInButton;
    TextView signUpTextView;
    DatabaseHelper db;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // create DatabaseHelper object
        db = new DatabaseHelper(this);

        // map widgets to the program
        usernameLoginEditText = findViewById(R.id.usernameLoginEditText);
        passwordLoginEditText = findViewById(R.id.passwordLoginEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpTextView = findViewById(R.id.signUpTextView);

        // set an on-click listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text from the username and password field
                username = usernameLoginEditText.getText().toString();
                password = passwordLoginEditText.getText().toString();

                // check if the username and password match and exist in the database
                if(db.isValidCredentials(username, password)) {
                    // create an Intent object
                    Intent intent =  new Intent(LoginActivity.this, MenuActivity.class);
                    // pass the username to menu activity through the intent object
                    intent.putExtra("username", username);
                    // start the menu activity
                    startActivity(intent);
                    // display a Toast object
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    // close the current activity
                    finish();
                }
                else
                    // display a Toast object
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // set an on-click listener
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent object
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // start the register activity
                startActivity(intent);
                // close the current activity
                finish();
            }
        });
    }
}
