package com.example.cp470_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //just adding toolbar?
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        MenuItem profileItem = m.findItem(R.id.menu_profile);
        if(profileItem != null){
            profileItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        if (mi.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        if (mi.getItemId() == R.id.action_home) {
            Log.d("Toolbar", "Home selected");
            Intent intent = new Intent(this, SubjectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // just clears all previous activity stuff
            startActivity(intent);
            return true;
        }
        if (mi.getItemId() == R.id.action_about) {
            Log.d("Toolbar", "About selected");
            Toast toast = Toast.makeText(this , "Version 1.0, by xlandr, LiamSoup123, eldym, DiwanB, Marushen1366", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(mi);
    }
}

