package com.example.mousa.moviesm;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.mousa.moviesm.Adapter.Adapter;
import com.example.mousa.moviesm.local_database.Favourite_Movies;
import com.example.mousa.moviesm.model.Movies;
import com.example.mousa.moviesm.utils.JsonUtils;
import com.example.mousa.moviesm.utils.Movie_Interface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   public RecyclerView recyclerView;
    JsonUtils jsonUtils = new JsonUtils();
    String key = BuildConfig.THE_GUARDIAN_API_KEY;
    RecyclerView.LayoutManager layoutManager;
    String title;
    String date;
    String vote;
    String image;
    String overrview;
    int id;
    public static int sort_option ;
    public static ArrayList<Movies> arrayList = new ArrayList<>();
    Adapter adapter;
    public static ArrayList<Movies> array = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        layoutManager = new GridLayoutManager(this, calculateBestSpanCount(342));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

          adapter=new Adapter();

        if (savedInstanceState == null) {
            populate_poular();
        } else {
            if (sort_option == 1) {
                populate_poular();

            } else if (sort_option == 2) {
                populate_top_rated();
            } else if (sort_option == 3) {
                populate_favourite();
            }

        }

    }
    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("list", array);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        switch (menuItemSelected) {
            case R.id.top:
                populate_top_rated();
                return true;
            case R.id.pop:
                populate_poular();
                return true;
            case R.id.favo:
                populate_favourite();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void populate_poular() {
        sort_option = 1;
        arrayList.clear();
        Movie_Interface movie_interface = jsonUtils.getjsonutils().create(Movie_Interface.class);
        Call<ResponseBody> call = movie_interface.popular_movies(key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try{

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movie = jsonArray.getJSONObject(i);
                        id = movie.getInt("id");
                        title = movie.getString("title");
                        date = movie.getString("release_date");
                        vote = movie.getString("vote_average");
                        image = movie.getString("poster_path");
                        overrview = movie.getString("overview");
                        arrayList.add(new Movies(id, title, date, vote, image, overrview));
                    }
                    MainActivity.array = arrayList;
                    adapter = new Adapter(MainActivity.this, arrayList);
                    getSupportActionBar().setTitle(R.string.popular);
                    recyclerView.setAdapter(adapter);
                    adapter.onAttachedToRecyclerView(recyclerView);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void populate_top_rated() {
        sort_option = 2;
        arrayList.clear();
        Movie_Interface movie_interface = jsonUtils.getjsonutils().create(Movie_Interface.class);
        Call<ResponseBody> call = movie_interface.Top_rated(key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movie = jsonArray.getJSONObject(i);
                        id = movie.getInt("id");
                        title = movie.getString("title");
                        date = movie.getString("release_date");
                        vote = movie.getString("vote_average");
                        image = movie.getString("poster_path");
                        overrview = movie.getString("overview");
                        arrayList.add(new Movies(id, title, date, vote, image, overrview));
                    }
                    MainActivity.array = arrayList;
                    adapter = new Adapter(MainActivity.this, arrayList);
                    getSupportActionBar().setTitle(R.string.top);
                    recyclerView.setAdapter(adapter);
                    adapter.onAttachedToRecyclerView(recyclerView);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.O)
    void populate_favourite() {
        sort_option = 3;
        arrayList.clear();
        Cursor c = getContentResolver().query(Favourite_Movies.content_uri, null, null, null,
                null, null);
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(Favourite_Movies._ID));
            title = c.getString(c.getColumnIndex(Favourite_Movies.MOVIE_TITLE));
            date = c.getString(c.getColumnIndex(Favourite_Movies.DATE));
            vote = c.getString(c.getColumnIndex(Favourite_Movies.VOTE));
            image = c.getString(c.getColumnIndex(Favourite_Movies.POSTER_IMAGE));
            overrview = c.getString(c.getColumnIndex(Favourite_Movies.OVERVIEW));
            arrayList.add(new Movies(id, title, date, vote, image, overrview));
            }
        c.close();
        MainActivity.array = arrayList;
        adapter = new Adapter(MainActivity.this, arrayList);
        getSupportActionBar().setTitle(R.string.favourite);
        recyclerView.setAdapter(adapter);

        }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(sort_option==3){
           populate_favourite();
        }

    }

}