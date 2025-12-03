package com.example.cp470_project;  // <-- change to match your actual package

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4;
    private Button buttonSubmitQuiz;
    private String APP_PREFS = "cp470_prefs";

    private final int totalQuestions = 4; //TOTAL QUESTION COUNT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = findViewById(R.id.radioGroupQ4);

        buttonSubmitQuiz = findViewById(R.id.buttonSubmitQuiz);

        buttonSubmitQuiz.setOnClickListener(v -> checkAnswers());
    }

    private void checkAnswers() {
        int score = 0;

        //checking all answers have an answer selected

        if (radioGroupQ1.getCheckedRadioButtonId() == -1 ||
                radioGroupQ2.getCheckedRadioButtonId() == -1 ||
                radioGroupQ3.getCheckedRadioButtonId() == -1 ||
                radioGroupQ4.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this,
                    "Please answer all questions before submitting.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //q1
        int selectedQ1 = radioGroupQ1.getCheckedRadioButtonId();
        if (selectedQ1 == R.id.q1_option4) {
            score++;
        }
        //q2
        int selectedQ2 = radioGroupQ2.getCheckedRadioButtonId();
        if (selectedQ2 == R.id.q2_option3) {
            score++;
        }
        //q3
        int selectedQ3 = radioGroupQ3.getCheckedRadioButtonId();
        if (selectedQ3 == R.id.q3_option2) {
            score++;
        }
        //a4
        int selectedQ4 = radioGroupQ4.getCheckedRadioButtonId();
        if (selectedQ4 == R.id.q4_option1) {
            score++;
        }

        //calculate percentage
        double percent = (score * 100.0f) / totalQuestions;
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long attempts = 0;
                if (snapshot.hasChild("MathAttempts")) {
                    Object atVal = snapshot.child("MathAttempts").getValue();
                    if (atVal instanceof Long) attempts = (Long) atVal;
                }

                double oldScore = 0;
                if (snapshot.hasChild("MathScore")) {
                    Object scVal = snapshot.child("MathScore").getValue();
                    if (scVal instanceof Long) oldScore = ((Long) scVal).doubleValue();
                    else if (scVal instanceof Double) oldScore = (Double) scVal;
                }

                userRef.child("MathAttempts").setValue(attempts + 1);
                userRef.child("MathScore").setValue(oldScore + percent);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        //show result in a dialog
        new AlertDialog.Builder(this).setTitle("Quiz Results").setMessage("You got " + score + " out of " + totalQuestions + " correct (" + (int) percent + "%).")
                .setPositiveButton("OK", (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                })
                .show();
    }
}
