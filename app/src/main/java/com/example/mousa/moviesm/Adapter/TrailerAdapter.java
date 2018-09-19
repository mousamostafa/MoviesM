package com.example.mousa.moviesm.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.mousa.moviesm.R;
import com.example.mousa.moviesm.model.MovieInfo;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
    Context context;
    ArrayList<MovieInfo> trailerList=new ArrayList<MovieInfo>();

    public TrailerAdapter(Context context, ArrayList<MovieInfo> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }
    public class TrailerHolder extends RecyclerView.ViewHolder{
        TextView bt;
        public TrailerHolder(View itemView) {
            super(itemView);
            bt=(TextView) itemView.findViewById(R.id.btt);

        }


    }

    @Override
    public TrailerHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_trailer,parent,false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, final int position) {
        holder.bt.setText(trailerList.get(position).getTraileName());
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(context,trailerList.get(position).getMovieKey());
            }
        });

    }


    @Override
    public int getItemCount() {
        return trailerList.size();
    }


    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}