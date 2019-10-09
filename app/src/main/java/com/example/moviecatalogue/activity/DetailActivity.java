package com.example.moviecatalogue.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.api.APIClient;
import com.example.moviecatalogue.database.FavoriteHelper;
import com.example.moviecatalogue.model.DetailModel;
import com.example.moviecatalogue.model.ResultsItem;
import com.example.moviecatalogue.provider.FavoriteColumns;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.provider.DatabaseContract.CONTENT_URI;
import static java.lang.String.valueOf;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE_DETAIL = "movie_detail";

    private ProgressBar progressBar;

    @BindView(R.id.movies_detail_name)
    TextView tv_title;

    @BindView(R.id.movies_detail_photo)
    ImageView img_poster;

    @BindView(R.id.movies_detail_overview)
    TextView tv_overview;

    @BindView(R.id.movies_detail_favorite)
    ImageView iv_fav;

    private Call<DetailModel> apiCall;
    private APIClient apiClient = new APIClient();

    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;
    private Gson gson = new Gson();

    private ResultsItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);

        ButterKnife.bind(this);

        String json = getIntent().getStringExtra(MOVIE_DETAIL);
        item = gson.fromJson(json, ResultsItem.class);
        loadData();


        showLoading();

        iv_fav.setOnClickListener(view -> {
            if (isFavorite) FavoriteRemove();
            else FavoriteSave();

            isFavorite = !isFavorite;
            favoriteSet();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
        if (favoriteHelper != null) favoriteHelper.close();
    }

    private void favoriteSet() {
        if (isFavorite) iv_fav.setImageResource(R.drawable.ic_favorite);
        else iv_fav.setImageResource(R.drawable.ic_favorite_border);
    }

    private void loadData() {
        loadDataSQLite();
        loadDataInServer(valueOf(item.getId()));

        tv_title.setText(item.getOriginalTitle());
        tv_overview.setText(item.getOverview());

        Glide.with(this)
                .load(BuildConfig.BASE_URL_IMG + "w154" + item.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(img_poster);
    }

    private void loadDataInServer(String item) {
        apiCall = apiClient.getService().getDetailMovie(item);
        apiCall.enqueue(new Callback<DetailModel>() {
            @Override
            public void onResponse(@NonNull Call<DetailModel> call, @NonNull Response<DetailModel> response) {
            }
            @Override
            public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this, "Error load data!", Toast.LENGTH_SHORT).show();
    }

    private void loadDataSQLite() {
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        favoriteSet();
    }

    private void FavoriteSave() {
        ContentValues cv = new ContentValues();
        cv.put(FavoriteColumns.COLUMN_ID, item.getId());
        cv.put(FavoriteColumns.COLUMN_TITLE, item.getOriginalTitle());
        cv.put(FavoriteColumns.COLUMN_POSTER, item.getPosterPath());
        cv.put(FavoriteColumns.COLUMN_OVERVIEW, item.getOverview());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, "This movie add to Favorite", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null
        );
        Toast.makeText(this, "This movie remove from Favorite", Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }
}