package com.example.cp470_project;

public class QuizResult {
    public long id;
    public String subject;
    public double score;
    public QuizResult(long id, String subject, double score){
        this.id = id;
        this.subject = subject;
        this.score = score;
    }
}
