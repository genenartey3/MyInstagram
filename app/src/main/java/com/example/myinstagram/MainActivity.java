package com.example.myinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    // initialize layout variables
    private EditText usernameInput;
    private EditText passwordInput;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // establish references to layout variables
        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // gets string input from reference
                final String username = usernameInput.getText().toString();
                final String password  = passwordInput.getText().toString();

                login(username, password);
            }
        });



    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if ( e == null) {
                    Log.d("LoginActivity", "Login successful!!");

                    Intent toHome = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(toHome);
                    finish();
                } else {
                    Log.d("LoginActivity", "Login Failed");
                    e.printStackTrace();
                }
            }
        });

    }
}
