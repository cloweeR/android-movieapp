package com.pah.movieapp;

import android.app.Application;

import androidx.room.Room;

import com.pah.movieapp.db.AppDatabase;

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = AppDatabase.getInstance(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

