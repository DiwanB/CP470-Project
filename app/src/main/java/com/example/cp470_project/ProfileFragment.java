package com.example.cp470_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button resetAccountButton;
        Button deleteAccountButton;

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

                String stats = "Total quizzes: " + (mathAttempts + englishAttempts + scienceAttempts + geoAttempts) + " | Avg score: " + totalAvg;
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

        resetAccountButton = view.findViewById(R.id.resetButton);
        deleteAccountButton = view.findViewById(R.id.deleteButton);

        deleteAccountButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                .setTitle("Delete Account?")
                .setMessage("Are you sure you want to permanently delete your account? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {

                    DatabaseReference usersRef = FirebaseDatabase.getInstance()
                            .getReference("users");

                    usersRef.child(userId).removeValue((error, ref) -> {
                        if (error == null) {
                            Toast.makeText(requireContext(), "Account deleted.", Toast.LENGTH_SHORT).show();

                            // clear stored session
                            prefs.edit().remove("userId").apply();

                            // go back to login
                            Intent intent = new Intent(requireActivity(), LoginActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(requireContext(), "Error deleting account", Toast.LENGTH_SHORT).show();
                        }
                    });

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
        });

        resetAccountButton.setOnClickListener(v -> {

            new AlertDialog.Builder(requireContext())
                .setTitle("Reset Account Stats?")
                .setMessage("Are you sure you want to reset all quiz data? This cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {

                    DatabaseReference resetRef = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(userId);

                    resetRef.child("MathAttempts").setValue(0);
                    resetRef.child("MathScore").setValue(0.0);
                    resetRef.child("EnglishAttempts").setValue(0);
                    resetRef.child("EnglishScore").setValue(0.0);
                    resetRef.child("ScienceAttempts").setValue(0);
                    resetRef.child("ScienceScore").setValue(0.0);
                    resetRef.child("GeoAttempts").setValue(0);
                    resetRef.child("GeoScore").setValue(0.0);

                    // Update list immediately
                    results.clear();
                    results.add(new QuizResult(1, "Math", 0));
                    results.add(new QuizResult(2, "English", 0));
                    results.add(new QuizResult(3, "Science", 0));
                    results.add(new QuizResult(4, "Geography", 0));
                    adapter.notifyDataSetChanged();

                    statsText.setText("Total quizzes: 0 | Avg score: 0");
                    Toast.makeText(requireContext(), "Scores reset.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
        });
    }

    private void showDetailDialog(QuizResult result) {
        if (getContext() == null) return;

        String message = "Subject: " + result.subject + "\nScore: " + result.score + "%";

        new AlertDialog.Builder(getContext()).setTitle("Quiz Result Details").setMessage(message).setPositiveButton("OK", null).show();
    }
}