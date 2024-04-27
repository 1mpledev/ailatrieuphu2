package com.example.ailatrieuphu2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ScoreUser")
public class ScoreUser {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "nameUser")
    public String nameUser;

    @NonNull
    @ColumnInfo(name = "score")
    public int score;

    public ScoreUser(@NonNull String nameUser, @NonNull int score) {
        this.nameUser = nameUser;
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return "ScoreUser{" +
                "id=" + id +
                ", nameUser='" + nameUser + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}