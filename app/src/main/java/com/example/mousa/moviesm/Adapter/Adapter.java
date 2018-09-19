package com.example.mousa.moviesm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mousa.moviesm.MovieDetailsActivity;
import com.example.mousa.moviesm.R;
import com.example.mousa.moviesm.model.Movies;
//import com.example.mousa.moviesm.utils.JsonUtilsInfo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Mousa on 4/26/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MovieHolder> {
    Context context;
    ArrayList<Movies> movieList = new ArrayList<Movies>();
    public Adapter(){

    }

    public Adapter(Context context, ArrayList<Movies> movieList) {
        this.context = context;
        this.movieList = movieList;
    }




    public static class MovieHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        CardView cardView;
        public MovieHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.home_img);
           cardView=(CardView)itemView.findViewById(R.id.carr);
        }
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342/"+movieList.get(position).getImage()).into(holder.poster);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("ff",movieList);
                intent.putExtra("id", movieList.get(position).getId());
                intent.putExtra("title", movieList.get(position).getTitle());
                intent.putExtra("date", movieList.get(position).getDate());
                intent.putExtra("rate", movieList.get(position).getVote());
                intent.putExtra("overview", movieList.get(position).getOverrview());
                intent.putExtra("img", movieList.get(position).getImage());
                context.startActivity(intent);    }  });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}


