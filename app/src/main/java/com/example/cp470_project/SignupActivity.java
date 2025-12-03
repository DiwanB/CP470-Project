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

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText loginName;
    private EditText loginEmail;
    private EditText passwordEditText;
    private Button createAccountButton;
    private Button backToLoginButton;
    private ProgressBar progressBar;
    private static final String ACTIVITY_NAME ="SignupActivity";
    private static final String PREFS_NAME = "cp470_prefs";
    private static final String KEY_DEFAULT_EMAIL = "DefaultEmail";
    private DatabaseReference mDatabase;

    private void createNewUser(User user) {
        String userId = mDatabase.child("users").push().getKey();

        mDatabase.child("users").child(userId).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    prefs.edit().putString("userId", userId).apply(); // saves the user's ID for firebase retrieval

                    startActivity(new Intent(this, SubjectActivity.class));
                } else {
                    Toast.makeText(this, "Failed to create account!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginName = findViewById(R.id.loginName);
        loginEmail = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.passwordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);
        backToLoginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBarLogin);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedEmail = prefs.getString(KEY_DEFAULT_EMAIL, "email@domain.com");
        loginEmail.setText(savedEmail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //save on click then go to main activity
        createAccountButton.setOnClickListener(v-> {
            String name = loginName.getText().toString();
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

            User user = new User(name, currentEmail, password, 0, 0, 0, 0, 0, 0 ,0 ,0);
            progressBar.setVisibility(View.VISIBLE);
            DatabaseReference usersRef = mDatabase.child("users");

            usersRef.orderByChild("Email").equalTo(currentEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);

                    if (snapshot.exists()) {
                        Toast.makeText(SignupActivity.this, "That email already has an account! Please log in instead.", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Integer> empty;
                        createNewUser(new User(name, currentEmail, password, 0, 0, 0, 0, 0, 0 ,0 ,0));
                    }
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }});

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                createAccountButton.setEnabled(true);
            }, 3000);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_DEFAULT_EMAIL, currentEmail);
            boolean ok = editor.commit();

            Log.i(ACTIVITY_NAME, "Saved email to prefs: " + ok + "value:" +currentEmail);
        });

        backToLoginButton.setOnClickListener(v-> {
            finish();
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