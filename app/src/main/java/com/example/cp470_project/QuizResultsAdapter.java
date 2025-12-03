package com.example.cp470_project;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class QuizResultsAdapter extends RecyclerView.Adapter<QuizResultsAdapter.ResultViewHolder> {

    public interface OnResultClickListener {
        void onResultClick(QuizResult result);
        void onResultLongClick(QuizResult result);
    }

    private List<QuizResult> results;
    private OnResultClickListener listener;

    public QuizResultsAdapter(List<QuizResult> results, OnResultClickListener listener) {
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz_result, parent, false);
        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        QuizResult result = results.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void updateData(List<QuizResult> newResults) {
        this.results = newResults;
        notifyDataSetChanged();
    }
    class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView textSubject, textScore, textDate;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubject = itemView.findViewById(R.id.textSubject);
            textScore = itemView.findViewById(R.id.textScore);
        }

        void bind(QuizResult result) {
            textSubject.setText(result.subject);
            textScore.setText(result.score + "%");

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onResultClick(result);
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) listener.onResultLongClick(result);
                return true;
            });
        }
    }
}