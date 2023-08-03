package com.pah.movieapp.ui.episodes;

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
import com.pah.movieapp.adapter.EpisodeAdapter;
import com.pah.movieapp.api.ApiService;
import com.pah.movieapp.api.MovieRepository;
import com.pah.movieapp.api.RetrofitClient;
import com.pah.movieapp.databinding.FragmentEpisodesBinding;
import com.pah.movieapp.model.Episode;
import com.pah.movieapp.model.ViewModelFactory;
import com.pah.movieapp.ui.MovieDetailActivity;
import com.pah.movieapp.ui.series.SeriesViewModel;


public class EpisodesFragment extends Fragment {

    private FragmentEpisodesBinding binding;
    EpisodesViewModel episodesViewModel;
    EpisodeAdapter episodeAdapter;
    ApiService apiService;
    MovieRepository movieRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEpisodesBinding.inflate(inflater, container, false);
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
        episodesViewModel = new ViewModelProvider(this,
                new ViewModelFactory(movieRepository)).get(EpisodesViewModel.class);
        episodeAdapter = new EpisodeAdapter();
        binding.recyclerView.setAdapter(episodeAdapter);

        episodesViewModel.getAllEpAPi();
        episodesViewModel.getAllEp().observe(getActivity(),episodes -> {
            episodeAdapter.setEpisodeList(episodes);
        });

        episodeAdapter.setOnItemClickListener(episode -> {
            Gson gson = new Gson();
            String ep = gson.toJson(episode);
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("episode", ep);
            getActivity().startActivity(intent);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}