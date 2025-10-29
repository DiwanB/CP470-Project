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

public class GeographyActivity extends AppCompatActivity {
    public String ACTIVITY_NAME = "GeographyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_geography);
        Button mL1 = findViewById(R.id.geoL1);
        Button mL2 = findViewById(R.id.geoL2);
        Button mL3 = findViewById(R.id.geoL3);
        Button mL4 = findViewById(R.id.geoL4);
        Button mL5 = findViewById(R.id.geoL5);
        Button mL6 = findViewById(R.id.geoL6);
        Button mQ1 = findViewById(R.id.geoQ1);
        Button mQ2 = findViewById(R.id.geoQ2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 1");
                Intent intent = new Intent(GeographyActivity.this, MathL1Activity.class);
                startActivity(intent);
            }
        });
        mL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 2");
                Intent intent = new Intent(GeographyActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 3");
                Intent intent = new Intent(GeographyActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 4");
                Intent intent = new Intent(GeographyActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 5");
                Intent intent = new Intent(GeographyActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Lesson 6");
                Intent intent = new Intent(GeographyActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
        mQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Quiz 1");
                Intent intent = new Intent(GeographyActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        mQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Quiz 2");
                Intent intent = new Intent(GeographyActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}