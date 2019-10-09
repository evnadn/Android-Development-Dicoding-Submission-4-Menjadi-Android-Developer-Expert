package com.example.moviecatalogue.api;

import com.example.moviecatalogue.model.DetailModel;
import com.example.moviecatalogue.model.MoviesModel;
import com.example.moviecatalogue.model.TvShowsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/now_playing")
    Call<MoviesModel> getMovies();

    @GET("movie/top_rated")
    Call<TvShowsModel> getTvshows();

    @GET("movie/{movie_id}")
    Call<DetailModel> getDetailMovie(@Path("movie_id") String movie_id);

}
