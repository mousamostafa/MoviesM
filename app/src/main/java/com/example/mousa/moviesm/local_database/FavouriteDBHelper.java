package com.example.mousa.moviesm.local_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mousa on 4/30/2018.
 */

public class FavouriteDBHelper extends SQLiteOpenHelper{
    public static final String DATEABASE_NAME="favo_movies.db";
    public static final int DATEABASE_VERSION=1;

    public FavouriteDBHelper(Context context) {
        super(context, DATEABASE_NAME, null, DATEABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String crate_db= "CREATE TABLE "+Favourite_Movies.Table_NAME+ " ( "+
                Favourite_Movies._ID+" INTEGER PRIMARY KEY,"+
                Favourite_Movies.MOVIE_TITLE+" TEXT NOT NULL,"+
                Favourite_Movies.DATE+ " TEXT NOT NULL,"+
                Favourite_Movies.VOTE+" TEXT NOT NULL,"+
                Favourite_Movies.POSTER_IMAGE+ " TEXT NOT NULL ,"+
                Favourite_Movies.OVERVIEW+" TEXT NOT NULL "+" );";
        db.execSQL(crate_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
