package com.example.cp470_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
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

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

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
        englishButton.setOnClickListener(v-> {
            Intent intent = new Intent(SubjectActivity.this, EnglishActivity.class);
            startActivity(intent);
        });
        scienceButton.setOnClickListener(v-> {
            Intent intent = new Intent(SubjectActivity.this, ScienceActivity.class);
            startActivity(intent);
        });
        geographyButton.setOnClickListener(v-> {
            Intent intent = new Intent(SubjectActivity.this, GeographyActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem homeItem = menu.findItem(R.id.action_home);
        if (homeItem != null) {
            homeItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Toast.makeText(this,
                    "Version 1.0, by xlandr, LiamSoup123, eldym, DiwanB, Marushen1366",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menu_profile) {
            Intent intent = new Intent(SubjectActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}