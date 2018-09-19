package com.example.mousa.moviesm.local_database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Mousa on 4/30/2018.
 */

public class Favourite_Movies implements BaseColumns {

    public Favourite_Movies(){
    }

    public static final String Table_NAME="favourite_movies";
    public static final String POSTER_IMAGE="image";
    public static final String MOVIE_TITLE ="title";
    public static final String DATE="date";
    public static final String VOTE="vote";
    public static final String OVERVIEW="overview";
    public static final String AUTHORITY="com.example.mousa.moviesm";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITY);
    public static final String PATH="favourite_movies";
    public static final Uri content_uri=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();





}
