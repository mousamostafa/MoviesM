package com.example.mousa.moviesm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mousa.moviesm.R;
import com.example.mousa.moviesm.model.MovieInfo;
import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{
    Context context;
    ArrayList<MovieInfo> reviewList = new ArrayList<MovieInfo>();

    public ReviewsAdapter(Context context, ArrayList<MovieInfo> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }


    public class ReviewHolder extends RecyclerView.ViewHolder{
        TextView authr;
        TextView reviewinfo;
        TextView reviewUrl;
        public ReviewHolder(View itemView) {
            super(itemView);
            authr=(TextView)itemView.findViewById(R.id.author);
            reviewinfo=(TextView)itemView.findViewById(R.id.reviewInfo);
            reviewUrl=(TextView)itemView.findViewById(R.id.reviewurl);
        }
    }
    @Override
    public ReviewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.row_reviews,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.authr.setText(reviewList.get(position).getAuthorname());
        holder.reviewinfo.append(reviewList.get(position).getReviewsdetail());
        holder.reviewUrl.append(" ReviewsUrl : "+reviewList.get(position).getReviewurl());

    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
