package com.example.mousa.moviesm.local_database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * Created by Mousa on 4/30/2018.
 */

public class MovieProvider extends ContentProvider {

    FavouriteDBHelper fv_db;
    public static final int FAVOURITE=100;// this for table_number so first table is 100 and second is 200 and so all 300,...
    public static final int FAVORITE_WITH_ID=101;//this for row of table so first row will be 101 and second 102 and so all
    public static final UriMatcher urimatcher= build_uriMatcher();




    public static UriMatcher build_uriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Favourite_Movies.AUTHORITY,Favourite_Movies.PATH,FAVOURITE);
        uriMatcher.addURI(Favourite_Movies.AUTHORITY, Favourite_Movies.PATH+"#",FAVORITE_WITH_ID);
        return uriMatcher;

    }




    @Override
    public boolean onCreate() {
        Context context=getContext();
        fv_db=new FavouriteDBHelper(context);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Cursor query ( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db;
        fv_db=new FavouriteDBHelper(getContext());
        db=fv_db.getReadableDatabase();
        int match=urimatcher.match(uri);
        Cursor retcursor;
        switch (match) {
            case FAVOURITE:
                    retcursor= db.query(Favourite_Movies.Table_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Favourite_Movies._ID);
                    break;
            case FAVORITE_WITH_ID:
                String id=uri.getPathSegments().get(1);
                String MSelection="_id=?";
                String[] MSelectionArgs=new  String[]{id};
                retcursor= db.query(Favourite_Movies.Table_NAME,
                        null,
                        MSelection,
                        MSelectionArgs,
                        null,
                        null,
                        Favourite_Movies._ID);
                break;


            default: throw new UnsupportedOperationException("Unknown Uri " +uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        return retcursor;
    }




    @Override
    public String getType(  Uri uri) {
        return null;
    }

   Uri return_Uri;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Uri insert(  Uri uri, ContentValues values) {
        final SQLiteDatabase db;
        fv_db=new FavouriteDBHelper(getContext());
        db=fv_db.getWritableDatabase();
        int match=urimatcher.match(uri);
        switch (match) {
            case FAVOURITE:
                long id = db.insert(Favourite_Movies.Table_NAME, null, values);//if insert sucess return 1 else return -1
                if (id > 0) {
                    //sucess
                    return_Uri = ContentUris.withAppendedId(Favourite_Movies.content_uri, id);

                } else {
                    throw new android.database.SQLException("faild to insert row  " + uri);
                }
                break;

        default: throw new UnsupportedOperationException("Unknown Uri " +uri);
        }
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        return return_Uri;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int delete(  Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db;
        fv_db=new FavouriteDBHelper(getContext());
        db=fv_db.getWritableDatabase();
        int match=urimatcher.match(uri);

        int id;
        switch (match) {
            case FAVOURITE:
                 id = db.delete(Favourite_Movies.Table_NAME, selection, selectionArgs);//if delete sucess return 1 else return -1
                break;
            case FAVORITE_WITH_ID:
                String idd=uri.getPathSegments().get(1);
                String MSelection=Favourite_Movies._ID+"=?";
                String[] MSelectionArgs=new  String[]{idd};
                id = db.delete(Favourite_Movies.Table_NAME, MSelection, MSelectionArgs);//if delete sucess return 1 else return -1

            default: throw new UnsupportedOperationException("Unknown Uri " +uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        return id;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int update(  Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db;
        fv_db=new FavouriteDBHelper(getContext());
        db=fv_db.getWritableDatabase();
        int match=urimatcher.match(uri);

        int id;
        switch (match) {
            case FAVOURITE:
                id = db.update(Favourite_Movies.Table_NAME, values,selection, selectionArgs);//if update sucess return 1 else return -1
                break;

            default: throw new UnsupportedOperationException("Unknown Uri " +uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        return id;
    }
}
