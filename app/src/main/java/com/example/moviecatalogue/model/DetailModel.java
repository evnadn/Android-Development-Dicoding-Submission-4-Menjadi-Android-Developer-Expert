package com.example.moviecatalogue.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DetailModel {

    @SerializedName("id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "DetailModel{" +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
