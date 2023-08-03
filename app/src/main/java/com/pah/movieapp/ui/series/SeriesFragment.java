package com.pah.movieapp.ui.series;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.pah.movieapp.adapter.MovieAdapter;
import com.pah.movieapp.adapter.SeriesAdapter;
import com.pah.movieapp.api.ApiService;
import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.api.RetrofitClient;
import com.pah.movieapp.databinding.FragmentSeriesBinding;
import com.pah.movieapp.model.Series;
import com.pah.movieapp.model.ViewModelFactory;
import com.pah.movieapp.ui.MovieDetailActivity;
import com.pah.movieapp.ui.home.MoviesViewModel;


public class SeriesFragment extends Fragment {

    private FragmentSeriesBinding binding;
    SeriesViewModel seriesViewModel;
    SeriesAdapter seriesAdapter;
    ApiService apiService;
    MovieRepository movieRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSeriesBinding.inflate(inflater, container, false);
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
        seriesViewModel = new ViewModelProvider(this,
                new ViewModelFactory(movieRepository)).get(SeriesViewModel.class);
        seriesAdapter = new SeriesAdapter();
        binding.recyclerView.setAdapter(seriesAdapter);

        seriesViewModel.getAllSeriesAPi();
        seriesViewModel.getAllSeries().observe(getActivity(),series -> {
            seriesAdapter.setSeriesList(series);
        });

        seriesAdapter.setOnItemClickListener(series -> {
            Gson gson = new Gson();
            String seriesJson = gson.toJson(series);
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("series", seriesJson);
            getActivity().startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}