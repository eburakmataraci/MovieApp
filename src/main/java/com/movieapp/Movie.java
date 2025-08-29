// Movie.java
package com.movieapp;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private double rating;
    private String posterPath;
    private List<Integer> genreIds;

    public Movie(int id, String title, String releaseDate, double rating, String posterPath, List<Integer> genreIds) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
        this.genreIds = genreIds;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }
    public String getPosterPath() { return posterPath; }
    public List<Integer> getGenreIds() { return genreIds; }
}
