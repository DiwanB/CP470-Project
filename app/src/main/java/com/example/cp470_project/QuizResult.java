package com.example.cp470_project;

public class QuizResult {
    public long id;
    public String subject;
    public int score;
    public long timestamp;
    public QuizResult(long id, String subject, int score, long timestamp){
        this.id = id;
        this.subject = subject;
        this.timestamp = timestamp;
    }
}
