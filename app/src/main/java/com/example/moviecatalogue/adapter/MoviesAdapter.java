package com.example.moviecatalogue.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.activity.DetailActivity;
import com.example.moviecatalogue.model.ResultsItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    private List<ResultsItem> listGetMovies = new ArrayList<>();

    public MoviesAdapter() {
    }

    public void replaceAll(List<ResultsItem> items) {
        listGetMovies.clear();
        listGetMovies = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movies, viewGroup, false);
        return new MoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        holder.bind(listGetMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return listGetMovies.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favorite_photo)
        ImageView imgPhoto;

        @BindView(R.id.favorite_name)
        TextView tvName;

        @BindView(R.id.favorite_overview)
        TextView tvOverview;

        @BindView(R.id.btnDetail)
        TextView btnDetail;

        MoviesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final ResultsItem item) {
            tvName.setText(item.getOriginalTitle());
            tvOverview.setText(item.getOverview());
            Glide.with(itemView.getContext())
                    .load(BuildConfig.BASE_URL_IMG + "w154" + item.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(imgPhoto);

            btnDetail.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.MOVIE_DETAIL, new Gson().toJson(item));
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public List<ResultsItem> getList(){
        return listGetMovies;
    }

    public void setMovieResult(List<ResultsItem> movieResult){
        this.listGetMovies = movieResult;
    }
}