package com.example.cp470_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivityEnglish extends AppCompatActivity {

    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4;
    private Button buttonSubmitQuiz;
    private String APP_PREFS = "cp470_prefs";

    private TextView textQuestion1, textQuestion2, textQuestion3, textQuestion4;
    private List<Question> questions = new ArrayList<>();
    private int[] correctOptionIds;

    private final int totalQuestions = 4; //TOTAL QUESTION COUNT
    private static class Question {
        String text;
        String[] options; //len 4
        int correctIndex;
        Question(String text, String[] options, int correctIndex) {
            this.text = text;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_science);
        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = findViewById(R.id.radioGroupQ4);

        buttonSubmitQuiz = findViewById(R.id.buttonSubmitQuiz);

        textQuestion1 = findViewById(R.id.textQuestion1);
        textQuestion2 = findViewById(R.id.textQuestion2);
        textQuestion3 = findViewById(R.id.textQuestion3);
        textQuestion4 = findViewById(R.id.textQuestion4);



        questions.add(new Question(
                "What is the punctuation to ask a question?",
                new String[]{".", "!", ",", "?"},
                3 // "4"
        ));
        questions.add(new Question(
                "What is the punctuation to make a statement?",
                new String[]{".", "!", "?", ";"},
                0
        ));
        questions.add(new Question(
                "What is the punctuation to exclaim?",
                new String[]{"!", ".", "+", ":"},
                0
        ));
        questions.add(new Question(
                "What is the proper spelling?",
                new String[]{"Dawg", "Dohg", "Drog", "Dog"},
                2
        ));
        questions.add(new Question(
                "What word is spelt correctly?",
                new String[]{"Helwo", "Hewwo", "Hali", "Hello"},
                3
        ));
        java.util.Collections.shuffle(questions);
        correctOptionIds = new int[totalQuestions];
        bindQuestionsToViews();
        buttonSubmitQuiz.setOnClickListener(v -> checkAnswers());
    }

    private void bindQuestionsToViews() {
        // SLOT 1 (Question 1 area)
        Question q1 = questions.get(0);
        textQuestion1.setText("1) " + q1.text);

        RadioButton q1o1 = findViewById(R.id.q1_option1);
        RadioButton q1o2 = findViewById(R.id.q1_option2);
        RadioButton q1o3 = findViewById(R.id.q1_option3);
        RadioButton q1o4 = findViewById(R.id.q1_option4);

        q1o1.setText(q1.options[0]);
        q1o2.setText(q1.options[1]);
        q1o3.setText(q1.options[2]);
        q1o4.setText(q1.options[3]);

        // store which RadioButton ID is correct for slot 1
        correctOptionIds[0] = getCorrectRadioIdForSlot(q1, q1o1, q1o2, q1o3, q1o4);

        // SLOT 2 (Question 2 area)
        Question q2 = questions.get(1);
        textQuestion2.setText("2) " + q2.text);

        RadioButton q2o1 = findViewById(R.id.q2_option1);
        RadioButton q2o2 = findViewById(R.id.q2_option2);
        RadioButton q2o3 = findViewById(R.id.q2_option3);
        RadioButton q2o4 = findViewById(R.id.q2_option4);

        q2o1.setText(q2.options[0]);
        q2o2.setText(q2.options[1]);
        q2o3.setText(q2.options[2]);
        q2o4.setText(q2.options[3]);

        correctOptionIds[1] = getCorrectRadioIdForSlot(q2, q2o1, q2o2, q2o3, q2o4);

        // SLOT 3 (Question 3 area)
        Question q3 = questions.get(2);
        textQuestion3.setText("3) " + q3.text);

        RadioButton q3o1 = findViewById(R.id.q3_option1);
        RadioButton q3o2 = findViewById(R.id.q3_option2);
        RadioButton q3o3 = findViewById(R.id.q3_option3);
        RadioButton q3o4 = findViewById(R.id.q3_option4);

        q3o1.setText(q3.options[0]);
        q3o2.setText(q3.options[1]);
        q3o3.setText(q3.options[2]);
        q3o4.setText(q3.options[3]);

        correctOptionIds[2] = getCorrectRadioIdForSlot(q3, q3o1, q3o2, q3o3, q3o4);

        Question q4 = questions.get(3);
        textQuestion4.setText("4) " + q4.text);

        RadioButton q4o1 = findViewById(R.id.q4_option1);
        RadioButton q4o2 = findViewById(R.id.q4_option2);
        RadioButton q4o3 = findViewById(R.id.q4_option3);
        RadioButton q4o4 = findViewById(R.id.q4_option4);

        q4o1.setText(q4.options[0]);
        q4o2.setText(q4.options[1]);
        q4o3.setText(q4.options[2]);
        q4o4.setText(q4.options[3]);

        correctOptionIds[3] = getCorrectRadioIdForSlot(q4, q4o1, q4o2, q4o3, q4o4);
    }
    private int getCorrectRadioIdForSlot(Question q,
                                         RadioButton o1, RadioButton o2,
                                         RadioButton o3, RadioButton o4) {
        switch (q.correctIndex) {
            case 0: return o1.getId();
            case 1: return o2.getId();
            case 2: return o3.getId();
            case 3: return o4.getId();
            default: return -1;
        }
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
        if (selectedQ1 == correctOptionIds[0]) {
            score++;
        }

        //q2
        int selectedQ2 = radioGroupQ2.getCheckedRadioButtonId();
        if (selectedQ2 == correctOptionIds[1]) {
            score++;
        }

        //q3
        int selectedQ3 = radioGroupQ3.getCheckedRadioButtonId();
        if (selectedQ3 == correctOptionIds[2]) {
            score++;
        }
        int selectedQ4 = radioGroupQ4.getCheckedRadioButtonId();
        if (selectedQ4 == correctOptionIds[3]) {
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
