package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Quiz extends AppCompatActivity {

    private QuestionViewModel questionViewModel;

    TextView txtQuestion;
    TextView textViewScore, textViewQuestionCount,textViewCountDownTimer;
    TextView textViewCorrect, textViewWrong;

    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup rbGroup;
    Button buttonNext;

    boolean answer = false;

    List<Questions> quesList;
    Questions currentQ;

    private int questionCounter = 0, questionTotalCount;

    private ColorStateList textColorofButtons;

    private Handler handler = new Handler();

    private int correctAns = 0, wrongAns = 0, score = 0;

    private FinalScoreDialog finalScoreDialog;
    //private WrongDialog wrongDialog;
    private int totalSizeofQuiz;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        settingup();

        textColorofButtons = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);
        //wrongDialog = new WrongDialog(this);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(@Nullable List<Questions> questions) {
                Toast.makeText(Quiz.this, "Got it", Toast.LENGTH_SHORT).show();

                fetchContent(questions);
            }
        });
    }

    private void fetchContent(List<Questions> questions) {
        quesList = questions;

        startQuiz();
    }

    private void startQuiz() {
        setQuestionView();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    break;
                    case R.id.radio_button2:
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        break;
                    case R.id.radio_button3:
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        break;
                    case R.id.radio_button4:
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        break;
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answer){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        quizOperation();
                    }else{
                        Toast.makeText(Quiz.this, "Please select answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void quizOperation() {
        answer = true;
        countDownTimer.cancel();
        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNumber = rbGroup.indexOfChild(rbselected)+1;
        checkSolution(rbselected, answerNumber);

    }

    private void checkSolution(RadioButton rbselected, int answerNumber) {
        switch (currentQ.getAnswer()){
            case 1:
                if(currentQ.getAnswer() == answerNumber){
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score = score + 10;
                    textViewScore.setText("Score: " + String.valueOf(score));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);
                }else{
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
                    rb1.setTextColor(Color.WHITE);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+String.valueOf(wrongAns));

                    //final String correctAnswer = (String) rb1.getText();
                    //wrongDialog.WrongDialog(correctAnswer);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);setQuestionView();

                }
            break;
            case 2:
                if(currentQ.getAnswer() == answerNumber){
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score = score + 10;
                    textViewScore.setText("Score: " + String.valueOf(score));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);
                }else{
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
                    rb2.setTextColor(Color.WHITE);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+String.valueOf(wrongAns));

                    //final String correctAnswer = (String) rb2.getText();
                    //wrongDialog.WrongDialog(correctAnswer);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);

                }
                break;
            case 3:
                if(currentQ.getAnswer() == answerNumber){
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score = score + 10;
                    textViewScore.setText("Score: " + String.valueOf(score));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);
                }else{
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
                    rb3.setTextColor(Color.WHITE);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+String.valueOf(wrongAns));

                    //final String correctAnswer = (String) rb3.getText();
                    //wrongDialog.WrongDialog(correctAnswer);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);

                }
                break;
            case 4:
                if(currentQ.getAnswer() == answerNumber){
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score = score + 10;
                    textViewScore.setText("Score: " + String.valueOf(score));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);
                }else{
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
                    rb4.setTextColor(Color.WHITE);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+String.valueOf(wrongAns));

                    //final String correctAnswer = (String) rb4.getText();
                    //wrongDialog.WrongDialog(correctAnswer);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    },500);setQuestionView();

                }
                break;
        }
        if(questionCounter<questionTotalCount){
            buttonNext.setText("Confirm and Finish");
        }
    }

    private void changetoIncorrect(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
    }

    void settingup(){
        textViewCorrect = (TextView)findViewById(R.id.txtCorrect);
        textViewCountDownTimer = (TextView)findViewById(R.id.txtTimer);
        textViewWrong = (TextView)findViewById(R.id.txtWrong);
        textViewCorrect = (TextView)findViewById(R.id.txtCorrect);
        textViewScore = (TextView)findViewById(R.id.txtScore);
        textViewQuestionCount = (TextView)findViewById(R.id.txtQuestionNum);
        txtQuestion = (TextView)findViewById(R.id.txtQuestionContainer);

        rbGroup = (RadioGroup)findViewById(R.id.radio_group);
        rb1 = (RadioButton)findViewById(R.id.radio_button1);
        rb2 = (RadioButton)findViewById(R.id.radio_button2);
        rb3 = (RadioButton)findViewById(R.id.radio_button3);
        rb4 = (RadioButton)findViewById(R.id.radio_button4);

        buttonNext = (Button)findViewById(R.id.button_next);
    }

    private void setQuestionView(){
        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        questionTotalCount = quesList.size();
        Collections.shuffle(quesList);

        if(questionCounter < questionTotalCount -1 ){
            currentQ = quesList.get(questionCounter);

            txtQuestion.setText(currentQ.getQuestion());

            rb1.setText(currentQ.getOpt1());
            rb2.setText(currentQ.getOpt2());
            rb3.setText(currentQ.getOpt3());
            rb4.setText(currentQ.getOpt4());

            questionCounter++;

            answer = false;

            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionTotalCount);
            
            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }else{
            Toast.makeText(this, "Quiz finished", Toast.LENGTH_SHORT).show();

            //Intent intent = new Intent(getApplicationContext(), Quiz.class);
            //startActivity(intent);
            totalSizeofQuiz = quesList.size();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //finalScoreDialog.finalScoreDialog(correctAns, wrongAns, totalSizeofQuiz);
                    resultData();
                }
            },2000);
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftinMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftinMillis = 0;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int)  (timeLeftinMillis / 1000) / 60;
        int seconds = (int) (timeLeftinMillis / 1000);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDownTimer.setText(timeFormatted);

        if(timeLeftinMillis < 5000){
            textViewCountDownTimer.setTextColor(Color.RED);
        }
        else{
            textViewCountDownTimer.setTextColor(textColorofButtons);
        }

        if(timeLeftinMillis == 0){
            Toast.makeText(this, "Times up", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), Quiz.class);
                    startActivity(intent);
                }
            },2000);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(countDownTimer == null){
            countDownTimer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    private void resultData(){
        Intent intent = new Intent(Quiz.this, Result.class);
        intent.putExtra("UserScore", score);
        intent.putExtra("TotalQuizQuestion", questionTotalCount);
        intent.putExtra("CorrectQuestion", correctAns);
        intent.putExtra("WrongQuestion", wrongAns);

        startActivity(intent);
    }
}
