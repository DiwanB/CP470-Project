package com.example.cp470_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4;
    private Button buttonSubmitQuiz;

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
        int percent = (int) ((score * 100.0f) / totalQuestions);
        //show result in a dialog
        new AlertDialog.Builder(this)
                .setTitle("Quiz Results")
                .setMessage("You got " + score + " out of " + totalQuestions +
                        " correct (" + percent + "%). ")
                .setPositiveButton("OK", (dialog, which) -> {
                    //Later:could save this result to the database here
                    //and/or return to previous screen.
                    finish();
                    dialog.dismiss();
                })
                .show();
    }
}
