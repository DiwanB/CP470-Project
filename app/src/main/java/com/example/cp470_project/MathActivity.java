package com.example.cp470_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MathActivity extends AppCompatActivity {
    public String ACTIVITY_NAME = "MathActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_math);
        Button mL1 = findViewById(R.id.mathL1);
        Button mL2 = findViewById(R.id.mathL2);
        Button mL3 = findViewById(R.id.mathL3);
        Button mL4 = findViewById(R.id.mathL4);
        Button mL5 = findViewById(R.id.mathL5);
        Button mL6 = findViewById(R.id.mathL6);
        Button mQ1 = findViewById(R.id.mathQ1);
        Button mQ2 = findViewById(R.id.mathQ2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 1");
                Intent intent = new Intent(MathActivity.this, MathL1Activity.class);
                startActivity(intent);
            }
        });
        mL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 2");
                Intent intent = new Intent(MathActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 3");
                Intent intent = new Intent(MathActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 4");
                Intent intent = new Intent(MathActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 5");
                Intent intent = new Intent(MathActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 6");
                Intent intent = new Intent(MathActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Quiz 1");
                Intent intent = new Intent(MathActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        mQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Quiz 2");
                Intent intent = new Intent(MathActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}