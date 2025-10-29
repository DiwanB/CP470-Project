package com.example.cp470_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText passwordEditText;
    private Button loginButton;
    private static final String ACTIVITY_NAME ="LoginActivity";
    private static final String PREFS_NAME = "cp470_prefs";
    private static final String KEY_DEFAULT_EMAIL = "DefaultEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginEmail = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedEmail = prefs.getString(KEY_DEFAULT_EMAIL, "email@domain.com");
        loginEmail.setText(savedEmail);

        //save on click then go to main activity
        loginButton.setOnClickListener(v-> {
            String currentEmail = loginEmail.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (currentEmail.isEmpty()) {
                loginEmail.setError(getString(R.string.loginActivityTextEmailEmpty));
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
                loginEmail.setError(getString(R.string.loginActivityTextEmailInvalid));
                return;
            }
            if (password.isEmpty()) {
                passwordEditText.setError(getString(R.string.loginActivityTextPasswordEmpty));
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_DEFAULT_EMAIL, currentEmail);
            boolean ok = editor.commit();

            Log.i(ACTIVITY_NAME, "Saved email to prefs: " + ok + "value:" +currentEmail);

            // same as goToMain
            Intent intent = new Intent(LoginActivity.this, SubjectActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
    }
    @Override
    public void onPause() {
        Log.i(ACTIVITY_NAME, "onPause");
        super.onPause();
    }
    @Override
    public void onStop() {
        Log.i(ACTIVITY_NAME, "onStop");
        super.onStop();
    }
    @Override
    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "onDestroy");
        super.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(ACTIVITY_NAME, "onSaveInstance");
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(ACTIVITY_NAME, "onRestoreInstanceState");
    }
}