package com.pah.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pah.movieapp.R;
import com.pah.movieapp.api.ApiService;
import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.api.RetrofitClient;
import com.pah.movieapp.databinding.ActivityMovieDetailBinding;
import com.pah.movieapp.model.Episode;
import com.pah.movieapp.model.Movie;
import com.pah.movieapp.model.Series;
import com.pah.movieapp.model.ViewModelFactory;
import com.pah.movieapp.ui.episodes.EpisodesViewModel;
import com.pah.movieapp.ui.home.MoviesViewModel;
import com.pah.movieapp.ui.series.SeriesViewModel;


public class MovieDetailActivity extends AppCompatActivity {

    MoviesViewModel movieViewModel;
    SeriesViewModel seriesViewModel;
    EpisodesViewModel episodesViewModel;
    ApiService apiService;
    MovieRepository movieRepository;

    Movie movie;
    Series series;
    Episode episode;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMovieDetailBinding binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupView(binding);

    }

    private void setupView(ActivityMovieDetailBinding binding) {
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable backIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_ios_new_24);
        getSupportActionBar().setHomeAsUpIndicator(backIcon);
        getSupportActionBar().setTitle("");

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        movieRepository = new MovieRepository(apiService);
        movieViewModel = new ViewModelProvider(this, new ViewModelFactory(movieRepository)).get(MoviesViewModel.class);
        seriesViewModel = new ViewModelProvider(this, new ViewModelFactory(movieRepository)).get(SeriesViewModel.class);
        episodesViewModel = new ViewModelProvider(this, new ViewModelFactory(movieRepository)).get(EpisodesViewModel.class);

        if(getIntent().hasExtra("movie")) {
            String movieJson = getIntent().getStringExtra("movie");
            movie = new Gson().fromJson(movieJson, Movie.class);

            movieViewModel.getMovieById(movie.getId()).observe(this, movie -> {
                if (movie != null) {
                    // Bind the movie data to the layout using data binding
                    binding.setSelection("movie");
                    binding.setMovie(movie);
                } else {
                    Toast.makeText(this, "Movie not found!", Toast.LENGTH_SHORT).show();
                }
            });

        } else if(getIntent().hasExtra("series")){
            String seriesJson = getIntent().getStringExtra("series");
            series = new Gson().fromJson(seriesJson, Series.class);

            seriesViewModel.getSeriesById(series.getId()).observe(this, series -> {
                if (series != null) {
                    binding.setSelection("series");
                    binding.setSeries(series);
                } else {
                    Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
                }
            });

        } else if(getIntent().hasExtra("episode")){
            String epJson = getIntent().getStringExtra("episode");
            episode = new Gson().fromJson(epJson, Episode.class);

            episodesViewModel.getEpById(episode.getId()).observe(this, episode -> {
                if (episode != null) {
                    binding.setSelection("episode");
                    binding.setEpisode(episode);
                } else {
                    Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}