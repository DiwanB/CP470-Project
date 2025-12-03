package com.example.cp470_project;

import java.util.ArrayList;

public class User {
    public String Name;
    public String Email;
    public String Password;
    public double MathScore;
    public int MathAttempts;
    public double EnglishScore;
    public int EnglishAttempts;
    public double ScienceScore;
    public int ScienceAttempts;
    public double GeoScore;
    public int GeoAttempts;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String Name, String Email, String Password, double MathScore, int MathAttempts, double EnglishScore, int EnglishAttempts, double ScienceScore, int ScienceAttempts, double GeoScore, int GeoAttempts) {
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.MathScore = MathScore;
        this.MathAttempts = MathAttempts;
        this.EnglishScore = EnglishScore;
        this.EnglishAttempts = EnglishAttempts;
        this.ScienceScore = ScienceScore;
        this.ScienceAttempts = ScienceAttempts;
        this.GeoScore = GeoScore;
        this.GeoAttempts= GeoAttempts;
    }
}
