package com.example.minutetotapit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class LoginActivity extends AppCompatActivity {

    EditText usernameLoginEditText, passwordLoginEditText;
    Button signInButton;
    TextView signUpTextView;
    DatabaseHelper db;

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

        // set an onClickListener for signInButton
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text from the username and password field
                String username = usernameLoginEditText.getText().toString();
                String password = passwordLoginEditText.getText().toString();

                // check if the username and password match and they exist in the database
                if(db.isValidCredentials(username, password)) {
                    // create an Intent object
                    Intent intent =  new Intent(LoginActivity.this, MenuActivity.class);
                    // pass the username to MenuActivity through the intent object
                    intent.putExtra("username", username);
                    // start the activity
                    startActivity(intent);
                    // display a Toast object
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    // close the activity
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // set an onClickListener for signUpButton
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
