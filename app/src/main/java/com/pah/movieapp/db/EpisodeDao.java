package com.pah.movieapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pah.movieapp.model.Episode;

import java.util.List;

@Dao
public interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEpisodes(List<Episode> episodes);

    @Query("SELECT * FROM episodes")
    LiveData<List<Episode>> getAllEp();

    @Query("SELECT * FROM episodes WHERE id = :epId")
    LiveData<Episode> getEpById(int epId);

    @Query("DELETE FROM episodes")
    void deleteAllEp();

}
