package com.example.ailatrieuphu2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ailatrieuphu2.database.dao.QuestionDAO;
import com.example.ailatrieuphu2.database.dao.ScoreUserDAO;
import com.example.ailatrieuphu2.database.entities.Question;
import com.example.ailatrieuphu2.database.entities.ScoreUser;

@Database(entities = {Question.class, ScoreUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDAO getQuestionDAO();
    public abstract ScoreUserDAO getScoreUserDAO();
}
