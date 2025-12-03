package com.example.cp470_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jspecify.annotations.NonNull;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText passwordEditText;
    private Button loginButton;
    private Button createAccountButton;
    private ProgressBar progressBar;
    private static final String ACTIVITY_NAME ="LoginActivity";
    private static final String PREFS_NAME = "cp470_prefs";
    private static final String KEY_DEFAULT_EMAIL = "DefaultEmail";
    private DatabaseReference mDatabase;

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
        createAccountButton = findViewById(R.id.createAccountButton);
        progressBar = findViewById(R.id.progressBarLogin);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedEmail = prefs.getString(KEY_DEFAULT_EMAIL, "email@domain.com");
        loginEmail.setText(savedEmail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //save on click then go to main activity
        loginButton.setOnClickListener(v-> {
            String currentEmail = loginEmail.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (currentEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter a email and/or password.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
                Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference usersRef = mDatabase.child("users");

            usersRef.orderByChild("Email").equalTo(currentEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setEnabled(true);

                    if (!snapshot.exists()) {
                        Toast.makeText(LoginActivity.this, "Email not found.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("Password").getValue(String.class);

                        if (storedPassword != null && storedPassword.equals(password)) {
                            // Login success
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            String userId = userSnapshot.getKey();

                            Intent intent = new Intent(LoginActivity.this, SubjectActivity.class);
                            intent.putExtra("userId", userId);

                            prefs.edit().putString("userId", userId).apply(); // saves the user's ID for firebase retrieval

                            startActivity(intent);
                            finish();

                        } else {
                            // Wrong password
                            Toast.makeText(LoginActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);
            }, 3000);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_DEFAULT_EMAIL, currentEmail);
            boolean ok = editor.commit();

            Log.i(ACTIVITY_NAME, "Saved email to prefs: " + ok + "value:" +currentEmail);
        });

        createAccountButton.setOnClickListener(v-> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
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