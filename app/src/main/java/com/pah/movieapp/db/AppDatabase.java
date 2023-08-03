package com.pah.movieapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pah.movieapp.model.Episode;
import com.pah.movieapp.model.Movie;
import com.pah.movieapp.model.Series;

@Database(entities = {Movie.class, Series.class, Episode.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract SeriesDao seriesDao();
    public abstract EpisodeDao episodeDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movie_app_database")
                    .build();
        }
        return instance;
    }
}