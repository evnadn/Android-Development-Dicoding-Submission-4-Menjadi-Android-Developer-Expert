package com.example.moviecatalogue.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsModel {

    @SerializedName("results")
    private List<ResultsItem> results;

    public TvShowsModel(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Tvshows{" +
                        ",results = '" + results + '\'' +
                        "}";
    }
}
