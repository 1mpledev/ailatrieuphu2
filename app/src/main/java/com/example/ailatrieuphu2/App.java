package com.example.ailatrieuphu2;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;

import com.example.ailatrieuphu2.database.AppDatabase;

public class App extends Application {
    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "Database")
                .createFromAsset("db/Database.db").build();

    }
    public static App getInstance() {return instance;}
    public AppDatabase getDatabase() {return database;}
}
