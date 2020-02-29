package com.example.minutetotapit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class RegisterActivity extends AppCompatActivity {
    // views and object declaration
    EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    Button registerButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize db object
        db = new DatabaseHelper(this);

        // map the widgets to the program
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        // set an on-click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize and store text read from text fields to username, password, and confirmPassword object
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // check whether one of the text fields is empty
                if(username.equals("") || password.equals("") || confirmPassword.equals("")) {
                    // display a Toast object
                    Toast.makeText(getApplicationContext(), "Please fill the fields completely", Toast.LENGTH_SHORT).show();
                }

                // if the fields are complete, do the following
                else {
                    // check if password text field is equal to confirm password text field
                    if(password.equals(confirmPassword)) {
                        // check for the existence of username in the database
                        boolean usernameExists = db.checkUsername(username);
                        // if the username does not exist yet, do the following
                        if(!usernameExists) {
                            // insert newly registered account to database
                            boolean isInserted = db.insertData(username, password);
                            // check if the account was successfully inserted
                            if(isInserted) {
                                // create an Intent object
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                // start the login activity
                                startActivity(intent);
                                // display a Toast object
                                Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                // close the current activity
                                finish();
                            }
                        }
                        // if the username does exist already, do the following
                        else {
                            // display a Toast object
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // if the password text field is not equal to the confirm password text field, do the following
                    else {
                        // display a Toast object
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
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