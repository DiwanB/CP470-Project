package com.example.cp470_project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class GeographyActivity extends AppCompatActivity {
    public String ACTIVITY_NAME = "GeographyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_geography);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon.setTint(ContextCompat.getColor(this, android.R.color.black));
        }

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

    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        if (mi.getItemId() == R.id.action_home) {
            Log.d("Toolbar", "Home selected");
            Intent intent = new Intent(this, SubjectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // just clears all previous activity stuff
            startActivity(intent);
        } else if (mi.getItemId() == R.id.action_about) {
            Log.d("Toolbar", "About selected");
            Toast toast = Toast.makeText(this , "Version 1.0, by xlandr, LiamSoup123, eldym, DiwanB, Marushen1366", Toast.LENGTH_SHORT);
            toast.show();
        }
        return true;
    }
}