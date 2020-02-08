package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView txtHighScore, txtTotalQuizQuestion, txtCorrectQuestion, txtWrongQuestion;
    Button btStartQuizAgain, btMainMenu;

    int highScore = 0;
    private static final String SHRED_PREFERENCE = "shared_preference";
    private static final String SHRED_PREFERENCE_HIGH_SCORE = "shared_preference_high_score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtHighScore = findViewById(R.id.result_highScore);
        txtTotalQuizQuestion = findViewById(R.id.totalQuestion);
        txtCorrectQuestion = findViewById(R.id.correct);
        txtWrongQuestion = findViewById(R.id.wrong);

        btMainMenu = findViewById(R.id.home);
        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Grade.class);
                startActivity(intent);
            }
        });
        btStartQuizAgain = findViewById(R.id.play_again);
        btStartQuizAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Quiz.class);
                startActivity(intent);
            }
        });

        loadHighScore();

        Intent intent = getIntent();
        int score = intent.getIntExtra("UserScore", 0);
        int totalQuestion = intent.getIntExtra("TotalQuizQuestion", 0);
        int correctQuestion = intent.getIntExtra("CorrectQuestion", 0);
        int wrongQuestion = intent.getIntExtra("WrongQuestion", 0);

        txtTotalQuizQuestion.setText("Total Question: "+ String.valueOf(totalQuestion));
        txtCorrectQuestion.setText("Correct: "+ String.valueOf(correctQuestion));
        txtWrongQuestion.setText("Wrong: "+ String.valueOf(wrongQuestion));

        if(score > highScore){
            updateScore(score);
        }
    }

    private void updateScore(int score) {
        highScore = score;
        txtHighScore.setText("High Score: " + String.valueOf(highScore));
        SharedPreferences sharedPreferences = getSharedPreferences(SHRED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHRED_PREFERENCE_HIGH_SCORE, highScore);
        editor.apply();

    }

    private void loadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHRED_PREFERENCE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHRED_PREFERENCE_HIGH_SCORE, 0);
        txtHighScore.setText("High Score: " + String.valueOf(highScore));

    }
}
