package com.example.moviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.provider.DatabaseContract.getColumnInt;
import static com.example.moviecatalogue.provider.DatabaseContract.getColumnString;
import static com.example.moviecatalogue.provider.FavoriteColumns.COLUMN_OVERVIEW;
import static com.example.moviecatalogue.provider.FavoriteColumns.COLUMN_POSTER;
import static com.example.moviecatalogue.provider.FavoriteColumns.COLUMN_TITLE;

public class ResultsItem implements Parcelable {

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultsItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.originalTitle = getColumnString(cursor, COLUMN_TITLE);
        this.overview = getColumnString(cursor, COLUMN_OVERVIEW);
        this.posterPath = getColumnString(cursor, COLUMN_POSTER);
    }

    @NonNull
    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "overview = '" + overview + '\'' +
                        ",original_title = '" + originalTitle + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeString(this.posterPath);
        dest.writeInt(this.id);
    }

    private ResultsItem(Parcel in) {
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.posterPath = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<ResultsItem> CREATOR = new Parcelable.Creator<ResultsItem>() {
        @Override
        public ResultsItem createFromParcel(Parcel source) {
            return new ResultsItem(source);
        }

        @Override
        public ResultsItem[] newArray(int size) {
            return new ResultsItem[size];
        }
    };
}
