package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.NoCopySpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreDialog {
    private Context mContext;
    private Dialog finalScoreDialog;
    private Quiz quizActivity;
    private TextView textViewFinalScore;

    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog(int correctAns, int wrongAns, int totalSizeofQuiz){
        finalScoreDialog = new Dialog(mContext);
        quizActivity = new Quiz();

        finalScoreDialog.setContentView(R.layout.final_score_dialog);
        final Button btnwrongDialog = (Button)finalScoreDialog.findViewById(R.id.btn_finalDialog);
        
        finalScore(correctAns, wrongAns, totalSizeofQuiz);

        btnwrongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalScoreDialog.dismiss();
                Intent intent = new Intent(mContext, Quiz.class);
                mContext.startActivity(intent);
            }
        });
        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);
    }

    private void finalScore(int correctAns, int wrongAns, int totalSizeofQuiz) {
        int tempScore = 0;
        textViewFinalScore = (TextView)finalScoreDialog.findViewById(R.id.textView_final_score);
        if(correctAns == totalSizeofQuiz){
            tempScore = (correctAns * 10);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else if(wrongAns == totalSizeofQuiz){
            tempScore = 0;
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else if(correctAns >= wrongAns){
            tempScore = (correctAns * 10) - (wrongAns * 5);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else {
            tempScore = 0;
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
    }
}
