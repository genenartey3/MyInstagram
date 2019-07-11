package com.example.myinstagram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    // initialize layout variables
    private EditText usernameInput;
    private EditText passwordInput;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start login activity animation
        ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        // establish references to layout variables
        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // gets string input from reference
                final String username = usernameInput.getText().toString();
                final String password  = passwordInput.getText().toString();

                login(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get string input from reference
                final String newUsername = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                // fire new method to create new user in parse database
                createNewUser(newUsername, password);
            }
        });



    }

    private void createNewUser(String newUsername, String password) {
        // create new user
        ParseUser user = new ParseUser();

        // set username to newUsername String and password to password String
        user.setUsername(newUsername);
        user.setPassword(password);
        user.setEmail(newUsername + "@gmail.com");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if ( e == null ) {
                    Log.d("MainActivity","SignUp Success!!");

                    Intent toHome = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(toHome);
                    finish();
                } else {
                    e.printStackTrace();
                    Log.d("MainActivity", "SignUp Failure :( ");
                }
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
                    usernameInput.setText("");
                    passwordInput.setText("");
                    Log.d("LoginActivity", "Login Failed");
                    e.printStackTrace();
                }
            }
        });

    }


}
