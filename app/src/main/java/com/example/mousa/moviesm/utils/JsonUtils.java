package com.example.mousa.moviesm.utils;

import retrofit2.Retrofit;

/**
 * Created by Mousa on 4/26/2018.
 */

public class JsonUtils {
    public static final String baseUrl = "http://api.themoviedb.org/3/movie/";
    public static Retrofit retrofit = null;

    public Retrofit getjsonutils() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        }
        return retrofit;
    }


    }

