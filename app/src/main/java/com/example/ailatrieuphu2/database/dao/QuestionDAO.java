package com.example.ailatrieuphu2.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.ailatrieuphu2.database.entities.Question;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Query("SELECT * FROM (\n" +
            "\tSELECT * FROM Question \n" +
            "\tWHERE level <= 15 \n" +
            "\tORDER BY random()) \n" +
            "GROUP BY level\n" +
            "ORDER BY level ASC\n" +
            "LIMIT 15;")
    List<Question> get15QuestionByLevel();
}
