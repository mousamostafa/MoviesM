package com.example.mousa.moviesm.utils;

import com.example.mousa.moviesm.model.MovieInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mousa on 4/27/2018.
 */

public interface Movie_Interface {

    @GET("popular") Call<ResponseBody> popular_movies(@Query("api_key") String key);
    @GET("top_rated") Call<ResponseBody> Top_rated(@Query("api_key") String key);
    @GET("videos")Call<ResponseBody> Trailer(@Query("api_key")String key);
    @GET("reviews")Call<ResponseBody> Reviews(@Query("api_key")String key);

}
