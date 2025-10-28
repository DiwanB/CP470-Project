package com.example.cp470_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SubjectActivity extends AppCompatActivity {
    private MaterialButton mathButton;
    private MaterialButton englishButton;
    private MaterialButton scienceButton;
    private MaterialButton geographyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mathButton = findViewById(R.id.math_button);
        englishButton = findViewById(R.id.english_button);
        scienceButton = findViewById(R.id.science_button);
        geographyButton = findViewById(R.id.geography_button);

        //math button clicked
        mathButton.setOnClickListener(v-> {
            Intent intent = new Intent(SubjectActivity.this, MathActivity.class);
            startActivity(intent);
        });
    }
}