package com.example.cp470_project;

import android.app.AlertDialog;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizResultsAdapter adapter;
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
        nameText.setText("John Doe");
        emailText.setText("john@example.com");
        statsText.setText("Total quizzes: 12 | Avg score: 82%");

        //recycler view
        recyclerView = view.findViewById(R.id.recyclerViewResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        seedFakeResults(); //temp until database added

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

    private void seedFakeResults() {
        long now = System.currentTimeMillis();
        results.clear();
        results.add(new QuizResult(1, "Math", 90, now - 60 * 60 * 1000));
        results.add(new QuizResult(2, "English", 75, now - 24 * 60 * 60 * 1000));
        results.add(new QuizResult(3, "Science", 88, now - 48 * 60 * 60 * 1000));
        results.add(new QuizResult(4, "Geography", 82, now - 72 * 60 * 60 * 1000));
    }

    private void showDetailDialog(QuizResult result) {
        if (getContext() == null) return;

        String dateString = DateFormat.getDateTimeInstance()
                .format(new Date(result.timestamp));

        String message = "Subject: " + result.subject + "\nScore: " + result.score + "%" + "\nDate: " + dateString;

        new AlertDialog.Builder(getContext()).setTitle("Quiz Result Details").setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}