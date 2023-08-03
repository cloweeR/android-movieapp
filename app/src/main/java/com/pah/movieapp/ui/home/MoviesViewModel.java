package com.pah.movieapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.model.Movie;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> movieLiveData;

    public MoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getAllMovies() {
        if (movieLiveData == null) {
            movieLiveData = movieRepository.getAllMoviesFromDatabase();
        }
        return movieLiveData;
    }

    public void getAllMovieAPi() {
        movieRepository.getAllMovies();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return movieRepository.getMovieById(movieId);
    }
}