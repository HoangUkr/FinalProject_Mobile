package com.example.quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question_table WHERE grade = :grade and subject = :subject")
    LiveData<List<Questions>> getAllQuestions(String grade, String subject);

    @Insert
    void insert(Questions questions);
}
