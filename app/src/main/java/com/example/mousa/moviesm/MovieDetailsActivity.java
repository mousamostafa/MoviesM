package com.example.mousa.moviesm;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mousa.moviesm.Adapter.ReviewsAdapter;
import com.example.mousa.moviesm.Adapter.TrailerAdapter;
import com.example.mousa.moviesm.local_database.Favourite_Movies;
import com.example.mousa.moviesm.model.MovieInfo;
import com.example.mousa.moviesm.utils.Movie_Interface;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    ImageView img;
    TextView datee;
    TextView rate;
   Button checkfavourite;
    RecyclerView review_recycle;
    String autrname;
    String revinfo;
    String rvewurl;
    String tralname;
    String traltype;
    String tralkey;
    String   id;
    ReviewsAdapter adapter;
    TrailerAdapter trailerAdapter;
    Bundle bundle;
    String key = BuildConfig.THE_GUARDIAN_API_KEY;
    ArrayList<MovieInfo> movieInfos = new ArrayList<MovieInfo>();
    ArrayList<MovieInfo> movieInfo = new ArrayList<MovieInfo>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManagerr;
    TextView revword;
    ImageView posterImg;
    TextView overview;
   public static String revurl;
   Button bttrail;
    RecyclerView re;
    Dialog dialog;
   public static boolean flagcheck=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        img = (ImageView) findViewById(R.id.movie_image);
        datee = (TextView) findViewById(R.id.movie_date);
        rate = (TextView) findViewById(R.id.movie_rate);
        checkfavourite = (Button) findViewById(R.id.check);
        revword=(TextView)findViewById(R.id.reviesword);
        bttrail=(Button)findViewById(R.id.play);
        posterImg=(ImageView)findViewById(R.id.posterImg) ;
        overview=(TextView)findViewById(R.id.overvew);
        review_recycle = (RecyclerView) findViewById(R.id.review);
        layoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        review_recycle.setLayoutManager(layoutManager);
        review_recycle.setHasFixedSize(true);
        dialog=new Dialog(MovieDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setTitle(" Trailer ");
        dialog.setContentView(R.layout.dialogrecycle);
        re=(RecyclerView)dialog.findViewById(R.id.traillist);
        re.setEnabled(true);
        layoutManagerr=new LinearLayoutManager(dialog.getContext(), LinearLayout.VERTICAL,false);
        re.setLayoutManager(layoutManagerr);
        re.setHasFixedSize(true);
        populatUi();
        getreviews();
        gettrailer();

        //**********check favourite*************
        Cursor c = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            c = getContentResolver().query(Favourite_Movies.content_uri, null, null, null,
                    null, null);

            while (c.moveToNext()) {
                int idd = c.getInt(c.getColumnIndex(Favourite_Movies._ID));
                if (idd == bundle.getInt("id")) {
                    checkfavourite.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    flagcheck = false;
                    break;
                } else {
                    checkfavourite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);

                    flagcheck = true;
                }
            }
            c.close();
        }
        //*******************************************************************
        bttrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });



    }



    void populatUi() {
        revurl=getString(com.example.mousa.moviesm.R.string.pathrevew)+bundle.getInt("id")+"/";
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342/"+bundle.getString("img")).placeholder(new ColorDrawable(Color.BLUE)).error(new ColorDrawable(Color.RED)).into(posterImg);
        getSupportActionBar().setTitle(bundle.getString("title"));
        datee.setText(bundle.getString("date"));
        rate.setText(bundle.getString("rate")+"/10 ");
        overview.setText(bundle.getString("overview"));
        Picasso.with(MovieDetailsActivity.this).load("http://image.tmdb.org/t/p/w185/"+ bundle.getString("img")).placeholder(new ColorDrawable(Color.BLUE)).error(new ColorDrawable(Color.RED)).into(img);

    }
    public void onbuttonclick(View view) {
            if (!flagcheck) {
                Uri uri=Favourite_Movies.BASE_CONTENT_URI.buildUpon().appendPath(String.valueOf(bundle.getInt("id"))).build();
                getContentResolver().delete(Favourite_Movies.content_uri,Favourite_Movies._ID+"="+String.valueOf(bundle.getInt("id")),null);
                Toast.makeText(getApplicationContext(), "Remove from Favourite List", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    checkfavourite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                }
                flagcheck=true;





        }
        else {
                checkfavourite.setBackgroundResource(R.drawable.ic_star_black_24dp);
                ContentValues contentValues = new ContentValues();
                contentValues.put(Favourite_Movies._ID, bundle.getInt("id"));
                contentValues.put(Favourite_Movies.MOVIE_TITLE, bundle.getString("title"));
                contentValues.put(Favourite_Movies.DATE, bundle.getString("date"));
                contentValues.put(Favourite_Movies.VOTE, bundle.getString("rate"));
                contentValues.put(Favourite_Movies.OVERVIEW, bundle.getString("overview"));
                contentValues.put(Favourite_Movies.POSTER_IMAGE, bundle.getString("img"));
                Uri uri = getContentResolver().insert(Favourite_Movies.content_uri, contentValues);
                Toast.makeText(getApplicationContext(), "ADD To Favourite", Toast.LENGTH_SHORT).show();
                flagcheck=false;


        }
    }


    public void gettrailer() {

        Retrofit retrofits = null;

            retrofits = new Retrofit.Builder().baseUrl(revurl).build();
            Movie_Interface movie_interface = retrofits.create(Movie_Interface.class);
        Call<ResponseBody> caltral = movie_interface.Trailer(key);
        caltral.enqueue(new Callback<ResponseBody>() {
                          @Override
                          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                              try{
                                  JSONObject jsonObject = new JSONObject(response.body().string());
                                  JSONArray jsonArray = jsonObject.getJSONArray("results");
                                  for (int  i=0;i<jsonArray.length();i++){
                                      JSONObject trail=jsonArray.getJSONObject(i);
                                      id=trail.getString("id");
                                      tralkey=trail.getString("key");
                                      tralname=trail.getString("name");
                                      traltype=trail.getString("type");
                                      movieInfo.add(new MovieInfo(id,tralkey,tralname,traltype));
                                  }
                                  trailerAdapter=new TrailerAdapter(dialog.getContext(),movieInfo);
                                  re.setAdapter(trailerAdapter);
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }
                          }

                          @Override
                          public void onFailure(Call<ResponseBody> call, Throwable t) {
                              t.printStackTrace(); }
                      }
        );
    }

    public void getreviews() {
         Retrofit retrofit = null;
            retrofit = new Retrofit.Builder().baseUrl(revurl).build();
        Movie_Interface movie_interface = retrofit.create(Movie_Interface.class);
        Call<ResponseBody> calll = movie_interface.Reviews(key);
        calll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                   JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int  i=0;i<jsonArray.length();i++){
                        JSONObject trail=jsonArray.getJSONObject(i);
                        autrname=trail.getString("author");
                        revinfo=trail.getString("content");
                        rvewurl=trail.getString("url");
                        if(revinfo == null){
                            revword.setVisibility(View.INVISIBLE);
                        }
                        else {
                            revword.setVisibility(View.VISIBLE);
                        }
                        movieInfos.add(new MovieInfo(autrname+" : ",revinfo,rvewurl));
                    }
                    adapter=new ReviewsAdapter(MovieDetailsActivity.this,movieInfos);
                    review_recycle.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace(); }
        }
        );
    }

    }
