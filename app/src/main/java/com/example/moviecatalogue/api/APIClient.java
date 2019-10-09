package com.example.moviecatalogue.api;

import com.example.moviecatalogue.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private final APIInterface apiCall;

    public APIClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url()
                            .newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .build();

                    original = original.newBuilder()
                            .url(httpUrl)
                            .build();

                    return chain.proceed(original);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL_)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCall = retrofit.create(APIInterface.class);
    }

    public APIInterface getService() {
        return apiCall;
    }
}
