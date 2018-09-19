package com.example.mousa.moviesm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mousa on 4/26/2018.
 */

public class Movies implements Parcelable {
    private int id;
    private String title;
     private String date;
     private String vote;
     private String image;
     private String overrview;

    public Movies(int id, String title, String date, String vote, String image, String overrview) {
       this.id=id;
        this.title = title;
        this.date = date;
        this.vote = vote;
        this.image = image;
        this.overrview = overrview;
    }



    protected Movies(Parcel in) {
        id = in.readInt();
        title = in.readString();
        date = in.readString();
        vote = in.readString();
        image = in.readString();
        overrview = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOverrview(String overrview) {
        this.overrview = overrview;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getVote() {
        return vote;
    }

    public String getImage() {
        return image;
    }

    public String getOverrview() {
        return overrview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(vote);
        dest.writeString(image);
        dest.writeString(overrview);

    }
}
