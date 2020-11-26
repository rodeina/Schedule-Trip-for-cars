package com.example.projectforcars.retrofit.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://178.77.99.228/android/";
    private static Retrofit retrofit = null;

    private static ApiClient instance;

    public static ApiClient getInstance(){

        if(instance == null){
            instance = new ApiClient();
        }

        return instance;
    }

    public Retrofit getApiClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}

