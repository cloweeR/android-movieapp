package com.pah.movieapp.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.ui.episodes.EpisodesViewModel;
import com.pah.movieapp.ui.home.MoviesViewModel;
import com.pah.movieapp.ui.series.SeriesViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private MovieRepository movieRepository;

    public ViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            return (T) new MoviesViewModel(movieRepository);
        } else if (modelClass.isAssignableFrom(SeriesViewModel.class)) {
            return (T) new SeriesViewModel(movieRepository);
        } else if (modelClass.isAssignableFrom(EpisodesViewModel.class)) {
            return (T) new EpisodesViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
