package com.example.ailatrieuphu2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ailatrieuphu2.database.entities.ScoreUser;

import java.util.List;
@Dao
public interface ScoreUserDAO {

    @Insert
    void insertScoreUser(ScoreUser scoreUser);

    @Query("SELECT * FROM SCOREUSER WHERE nameUser = :nameUser")
    ScoreUser getScoreUserByName(String nameUser);

    @Query("UPDATE ScoreUser SET score = :newScore WHERE nameUser = :nameUser")
    void updateScoreUserByName(String nameUser, int newScore);

    @Query("SELECT * FROM ScoreUser ORDER BY score DESC LIMIT 20")
    List<ScoreUser> getAllScoreUser();

    @Delete
    void deleteScoreUser(ScoreUser scoreUser);
}
