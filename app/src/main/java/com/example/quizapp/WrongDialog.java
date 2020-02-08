package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WrongDialog {
    private Context mContext;
    private Dialog wrongDialog;
    private Quiz quizActivity;
    private TextView textViewFinalScore;

    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void WrongDialog(String correctAnswer){
        wrongDialog = new Dialog(mContext);
        quizActivity = new Quiz();

        wrongDialog.setContentView(R.layout.wrong_dialog);
        final Button btnwrongDialog = (Button)wrongDialog.findViewById(R.id.btn_wrongDialog);
        TextView textView = (TextView) wrongDialog.findViewById(R.id.textView_Wrong_Answer);
        textView.setText(correctAnswer);

        btnwrongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongDialog.dismiss();
                //Intent intent = new Intent(mContext, Quiz.class);
                //mContext.startActivity(intent);
            }
        });
        wrongDialog.show();
        wrongDialog.setCancelable(false);
        wrongDialog.setCanceledOnTouchOutside(false);
    }

}
