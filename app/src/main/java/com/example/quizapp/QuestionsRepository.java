package com.example.quizapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {

    private QuestionDao mQuestionsDao;
    private LiveData<List<Questions>> mAllQuestions;
    private String grade = Grade.getGrade();

    private String subject = Subject.getSubject();

    public QuestionsRepository(Application application){
        QuestionRoomDatabase db = QuestionRoomDatabase.getInstance(application);
        mQuestionsDao = db.questionDao();
        mAllQuestions = mQuestionsDao.getAllQuestions(grade, subject);
    }

    public LiveData<List<Questions>> getmAllQuestions() {
        return mAllQuestions;
    }
}
