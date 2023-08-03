package com.pah.movieapp.api;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.pah.movieapp.BaseApplication;
import com.pah.movieapp.db.AppDatabase;
import com.pah.movieapp.db.EpisodeDao;
import com.pah.movieapp.db.MovieDao;
import com.pah.movieapp.db.SeriesDao;
import com.pah.movieapp.model.Episode;
import com.pah.movieapp.model.Movie;
import com.pah.movieapp.model.Series;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ApiService apiService;
    private MovieDao movieDao;
    private SeriesDao seriesDao;
    private EpisodeDao episodeDao;

    public MovieRepository(ApiService apiService) {
        this.apiService =  apiService;
        AppDatabase appDatabase = BaseApplication.getInstance().getAppDatabase();
        this.movieDao = appDatabase.movieDao();
        this.seriesDao = appDatabase.seriesDao();
        this.episodeDao = appDatabase.episodeDao();
    }

    //Movies

    public void getAllMovies() {
        // Fetch data from the API using apiService
        Call<List<Movie>> call = apiService.getAllMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body();
                    saveMoviesToDB(movies);
                } else {
                    Log.e("moviesViewModel", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                // Handle API call failure
                Log.v("moviesViewModel", t.getLocalizedMessage());
            }
        });
    }

    private void saveMoviesToDB(List<Movie> movies) {
        // Run the database insert operation in a background thread
        new Thread(() -> {
            movieDao.deleteAllMovies();
            movieDao.insertAllMovies(movies);
        }).start();
    }

    public LiveData<List<Movie>> getAllMoviesFromDatabase() {
        return movieDao.getAllMovies();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return movieDao.getMovieById(movieId);
    }


    //Series

    public void getAllSeries() {
        // Fetch data from the API using apiService
        Call<List<Series>> call = apiService.getAllSeries();
        call.enqueue(new Callback<List<Series>>() {
            @Override
            public void onResponse(@NonNull Call<List<Series>> call, @NonNull Response<List<Series>> response) {
                if (response.isSuccessful()) {
                    List<Series> series = response.body();
                    saveSeriesToDB(series);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Series>> call, @NonNull Throwable t) {
            }
        });
    }

    private void saveSeriesToDB(List<Series> series) {
        // Run the database insert operation in a background thread
        new Thread(() -> {
            seriesDao.deleteAllSeries();
            seriesDao.insertAllSeries(series);
        }).start();
    }

    public LiveData<List<Series>> getAllSeriesFromDB() {
        return seriesDao.getAllSeries();
    }

    public LiveData<Series> getSeriesById(int seriesId) {
        return seriesDao.getSeriesById(seriesId);
    }


    //Episodes

    public void getAllEpisodes() {

        Call<List<Episode>> call = apiService.getAllEpisodes();
        call.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(@NonNull Call<List<Episode>> call, @NonNull Response<List<Episode>> response) {
                if (response.isSuccessful()) {
                    List<Episode> ep = response.body();
                    saveEpToDB(ep);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Episode>> call, @NonNull Throwable t) {
            }
        });
    }

    private void saveEpToDB(List<Episode> episodes) {
        new Thread(() -> {
            episodeDao.deleteAllEp();
            episodeDao.insertAllEpisodes(episodes);
        }).start();
    }

    public LiveData<List<Episode>> getAllEpFromDB() {
        return episodeDao.getAllEp();
    }

    public LiveData<Episode> getEpById(int epId) {
        return episodeDao.getEpById(epId);
    }

}
