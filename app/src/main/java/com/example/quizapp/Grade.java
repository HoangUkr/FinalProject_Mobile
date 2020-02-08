package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Grade extends AppCompatActivity {

    TextView grade;
    private static String grade_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        CardView tenth = (CardView)findViewById(R.id.tenth);
        tenth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grade.this, Subject.class);
                grade = findViewById(R.id.tenth_grade);
                grade_number = grade.getText().toString();
                startActivity(intent);
            }
        });
        CardView eleventh = (CardView)findViewById(R.id.eleventh);
        eleventh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grade.this, Subject.class);
                grade = findViewById(R.id.eleventh_grade);
                grade_number = grade.getText().toString();
                startActivity(intent);
            }
        });
        CardView twelveth = (CardView)findViewById(R.id.twelveth);
        twelveth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grade.this, Subject.class);
                grade = findViewById(R.id.twelveth_grade);
                grade_number = grade.getText().toString();
                startActivity(intent);
            }
        });


    }
    /*
    public void OpenSubject(View view){
        Intent intent = new Intent(this, Subject.class);
        startActivity(intent);
    }

     */
    public static String getGrade(){
        return grade_number;
    }
}
