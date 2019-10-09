package com.example.moviecatalogue.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MoviesAdapter;
import com.example.moviecatalogue.api.APIClient;
import com.example.moviecatalogue.model.ResultsItem;
import com.example.moviecatalogue.model.TvShowsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {
    private Context context;
    private ProgressBar progressBar;

    @BindView(R.id.rv_tvshows)
    RecyclerView recyclerView;

    private MoviesAdapter adapter;

    private APIClient apiClient = new APIClient();

    public TvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        context = view.getContext();

        ButterKnife.bind(this, view);

        progressBar = view.findViewById(R.id.progressBar);

        setupList();
        loadData();

        if(savedInstanceState!=null){
            ArrayList<ResultsItem> list;
            list = savedInstanceState.getParcelableArrayList("movies");
            adapter.setMovieResult(list);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    private void setupList() {
        adapter = new MoviesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    }

    private void loadData() {
        Call<TvShowsModel> apiCall = apiClient.getService().getTvshows();
        apiCall.enqueue(new Callback<TvShowsModel>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsModel> call, @NonNull Response<TvShowsModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    adapter.replaceAll(response.body().getResults());
                    showLoading();
                } else loadFailed();
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsModel> call, @NonNull Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(context, "Error Load Data!!", Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", new ArrayList<>(adapter.getList()));
    }
}
