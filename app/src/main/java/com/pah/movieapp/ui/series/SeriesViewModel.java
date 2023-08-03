package com.pah.movieapp.ui.series;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.model.Series;

import java.util.List;

public class SeriesViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Series>> seriesLiveData;

    public SeriesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Series>> getAllSeries() {
        if (seriesLiveData == null) {
            seriesLiveData = movieRepository.getAllSeriesFromDB();
        }
        return seriesLiveData;
    }

    public void getAllSeriesAPi() {
        movieRepository.getAllSeries();
    }

    public LiveData<Series> getSeriesById(int seriesId) {
        return movieRepository.getSeriesById(seriesId);
    }


}