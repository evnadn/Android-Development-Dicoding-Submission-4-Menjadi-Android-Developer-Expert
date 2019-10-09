package com.example.moviecatalogue.adapter;

import android.content.Intent;
import android.database.Cursor;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor list;

    public FavoriteAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        list = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_movies, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));

     }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private ResultsItem getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new ResultsItem(list);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favorite_photo)
        ImageView imgPhoto;

        @BindView(R.id.favorite_name)
        TextView tvName;

        @BindView(R.id.favorite_overview)
        TextView tvOverview;

        @BindView(R.id.btnDetail)
        TextView btnDetail;

        ViewHolder(View itemView) {
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
}
