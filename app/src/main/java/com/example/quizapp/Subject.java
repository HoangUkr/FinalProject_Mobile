package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Subject extends AppCompatActivity {

    CardView math;
    CardView physics;
    CardView chemistry;

    TextView subject;
    private static String subject_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        math = (CardView)findViewById(R.id.math);
        physics = (CardView)findViewById(R.id.physics);
        chemistry = (CardView)findViewById(R.id.chemistry);

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenQuiz(v);
                subject = findViewById(R.id.text1);
                subject_name = subject.getText().toString();
            }
        });
        physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenQuiz(v);
                subject = findViewById(R.id.text2);
                subject_name = subject.getText().toString();
            }
        });
        chemistry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                OpenQuiz(v);
                subject = findViewById(R.id.text3);
                subject_name = subject.getText().toString();
            }
        });
    }

    public void OpenQuiz(View view){
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }
    public static String getSubject()
    {
        return subject_name;
    }
}
