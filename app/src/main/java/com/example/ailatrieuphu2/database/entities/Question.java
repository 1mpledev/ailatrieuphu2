package com.example.ailatrieuphu2.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Question")
public class Question {
    @PrimaryKey
    @ColumnInfo(name = "idQuestion")
    public int idQuestion;

    @ColumnInfo(name = "nameQuestion")
    public String nameQuestion;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "answerA")
    public String answerA;

    @ColumnInfo(name = "answerB")
    public String answerB;

    @ColumnInfo(name = "answerC")
    public String answerC;

    @ColumnInfo(name = "answerD")
    public String answerD;

    @ColumnInfo(name = "answerTrue")
    public int answerTrue;

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", nameQuestion='" + nameQuestion + '\'' +
                ", level=" + level +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}