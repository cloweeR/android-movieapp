package com.pah.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pah.movieapp.R;
import com.pah.movieapp.adapter.MovieAdapter;
import com.pah.movieapp.api.ApiService;
import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.api.RetrofitClient;
import com.pah.movieapp.databinding.FragmentMoviesBinding;
import com.pah.movieapp.db.AppDatabase;
import com.pah.movieapp.model.ViewModelFactory;
import com.pah.movieapp.ui.MovieDetailActivity;
import com.pah.movieapp.ui.SearchActivity;

public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding binding;
    MoviesViewModel moviesViewModel;
    MovieAdapter movieAdapter;
    ApiService apiService;
    MovieRepository movieRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupView();

        return root;
    }

    private void setupView() {
        Toolbar toolbar =  binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        movieRepository = new MovieRepository(apiService);
        moviesViewModel = new ViewModelProvider(this, new ViewModelFactory(movieRepository)).get(MoviesViewModel.class);

        movieAdapter = new MovieAdapter();
        binding.recyclerView.setAdapter(movieAdapter);

        moviesViewModel.getAllMovieAPi();

        moviesViewModel.getAllMovies().observe(getActivity(), movies -> {
            Log.v("moviesViewModel", movies.toString());
            movieAdapter.setMovieList(movies);
        });

        movieAdapter.setOnItemClickListener(movie -> {
            Gson gson = new Gson();
            String movieJson = gson.toJson(movie);
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("movie", movieJson);
            getActivity().startActivity(intent);
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}