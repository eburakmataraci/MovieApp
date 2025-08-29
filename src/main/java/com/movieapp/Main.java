package com.movieapp;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TmdbApiClient apiClient = new TmdbApiClient();
        MovieDatabase movieDb = new MovieDatabase();

        System.out.println("Filmler TMDb API'den çekiliyor...");
        List<Movie> movies = apiClient.getAllMovies();

        System.out.println("Filmler veritabanına kaydediliyor...");
        movieDb.saveMoviesToDatabase(movies);

        System.out.println("İşlem tamamlandı.");
    }
}
