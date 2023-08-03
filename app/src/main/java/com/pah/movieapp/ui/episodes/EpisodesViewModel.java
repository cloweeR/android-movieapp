package com.pah.movieapp.ui.episodes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.model.Episode;

import java.util.List;

public class EpisodesViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Episode>> epLiveData;

    public EpisodesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Episode>> getAllEp() {
        if (epLiveData == null) {
            epLiveData = movieRepository.getAllEpFromDB();
        }
        return epLiveData;
    }

    public void getAllEpAPi() {
        movieRepository.getAllEpisodes();
    }

    public LiveData<Episode> getEpById(int epId) {
        return movieRepository.getEpById(epId);
    }

}