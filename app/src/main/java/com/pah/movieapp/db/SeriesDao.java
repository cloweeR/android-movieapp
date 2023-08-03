package com.pah.movieapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pah.movieapp.model.Series;

import java.util.List;

@Dao
public interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSeries(List<Series> movies);

    @Query("SELECT * FROM series")
    LiveData<List<Series>> getAllSeries();

    @Query("SELECT * FROM series WHERE id = :seriesId")
    LiveData<Series> getSeriesById(int seriesId);

    @Query("DELETE FROM series")
    void deleteAllSeries();
}
