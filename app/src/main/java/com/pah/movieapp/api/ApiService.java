package com.pah.movieapp.api;

import com.pah.movieapp.model.Episode;
import com.pah.movieapp.model.Movie;
import com.pah.movieapp.model.Series;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movies")
    Call<List<Movie>> getAllMovies();

    @GET("series")
    Call<List<Series>> getAllSeries();

    @GET("episodes")
    Call<List<Episode>> getAllEpisodes();


}

