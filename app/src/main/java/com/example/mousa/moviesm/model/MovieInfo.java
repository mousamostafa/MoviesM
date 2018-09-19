package com.example.mousa.moviesm.model;

public class MovieInfo {
    private String authorname;
    private String reviewsdetail;
    private String reviewurl;
    private String movieKey;
    private String traileName;
    private String trailerType;
    private String trailerId;



    public MovieInfo(String authorname, String reviewsdetail, String reviewurl) {
        this.authorname = authorname;
        this.reviewsdetail = reviewsdetail;
        this.reviewurl=reviewurl;
    }


    public MovieInfo(String trailerId,String movieKey, String traileName, String trailerType) {
        this.trailerId=trailerId;
        this.movieKey = movieKey;
        this.traileName = traileName;
        this.trailerType = trailerType;
    }

    public String getReviewurl() {
        return reviewurl;
    }

    public void setReviewurl(String reviewurl) {
        this.reviewurl = reviewurl;
    }


    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getReviewsdetail() {
        return reviewsdetail;
    }

    public void setReviewsdetail(String reviewsdetail) {
        this.reviewsdetail = reviewsdetail;
    }


    public String getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(String movieKey) {
        this.movieKey = movieKey;
    }

    public String getTraileName() {
        return traileName;
    }

    public void setTraileName(String traileName) {
        this.traileName = traileName;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

}
