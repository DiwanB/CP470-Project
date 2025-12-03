package com.example.cp470_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizResultsAdapter adapter;
    private String APP_PREFS = "cp470_prefs";
    private final List<QuizResult> results = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //profile header
        TextView nameText = view.findViewById(R.id.textProfileName);
        TextView emailText = view.findViewById(R.id.textProfileEmail);
        TextView statsText = view.findViewById(R.id.textProfileStats);

        //TODO: later use real data
        SharedPreferences prefs = requireActivity().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(requireContext(), "User data not found!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // read values
                String name = snapshot.child("Name").getValue(String.class);
                String email = snapshot.child("Email").getValue(String.class);
                Double mathScore = snapshot.child("MathScore").getValue(Double.class);
                Integer mathAttempts = snapshot.child("MathAttempts").getValue(Integer.class);
                Double englishScore = snapshot.child("EnglishScore").getValue(Double.class);
                Integer englishAttempts = snapshot.child("EnglishAttempts").getValue(Integer.class);
                Double scienceScore = snapshot.child("ScienceScore").getValue(Double.class);
                Integer scienceAttempts = snapshot.child("ScienceAttempts").getValue(Integer.class);
                Double geoScore = snapshot.child("GeoScore").getValue(Double.class);
                Integer geoAttempts = snapshot.child("GeoAttempts").getValue(Integer.class);

                // null safety defaults
                if (mathScore == null) mathScore = 0.0;
                if (mathAttempts == null) mathAttempts = 0;
                if (englishScore == null) englishScore = 0.0;
                if (englishAttempts == null) englishAttempts = 0;
                if (scienceScore == null) scienceScore = 0.0;
                if (scienceAttempts == null) scienceAttempts = 0;
                if (geoScore == null) geoScore = 0.0;
                if (geoAttempts == null) geoAttempts = 0;

                String nameGreet = "Hey, " + name + "!";
                nameText.setText(nameGreet);
                emailText.setText(email);

                results.clear();
                results.add(new QuizResult(1, "Math", mathAttempts == 0 ? 0 : mathScore / mathAttempts));
                results.add(new QuizResult(2, "English", englishAttempts == 0 ? 0 : englishScore / englishAttempts));
                results.add(new QuizResult(3, "Science", scienceAttempts == 0 ? 0 : scienceScore / scienceAttempts));
                results.add(new QuizResult(4, "Geography", geoAttempts == 0 ? 0 : geoScore / geoAttempts));

                int totalAttempts = mathAttempts + englishAttempts + scienceAttempts + geoAttempts;
                double totalAvg = (totalAttempts == 0) ? 0 : (mathScore + englishScore + scienceScore + geoScore) / totalAttempts;

                String stats = "Total quizzes: " +(mathAttempts+englishAttempts+scienceAttempts+geoAttempts) + " | Avg score: " + totalAvg;
                statsText.setText(stats);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //recycler view
        recyclerView = view.findViewById(R.id.recyclerViewResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new QuizResultsAdapter(results, new QuizResultsAdapter.OnResultClickListener() {
            @Override
            public void onResultClick(QuizResult result) {
                showDetailDialog(result);
            }
            @Override
            public void onResultLongClick(QuizResult result) {
                Toast.makeText(getContext(), "Long-pressed: " + result.subject + " (" + result.score + "%)",
                        Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }


    private void showDetailDialog(QuizResult result) {
        if (getContext() == null) return;

        String message = "Subject: " + result.subject + "\nScore: " + result.score + "%";

        new AlertDialog.Builder(getContext()).setTitle("Quiz Result Details").setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}