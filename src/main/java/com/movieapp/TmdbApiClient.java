package com.movieapp;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TmdbApiClient {
    private static final String API_KEY = "your_API_key";
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 1;

        try {
            while (currentPage <= totalPages) {
                String urlString = BASE_URL + "?api_key=" + API_KEY + "&language=en-US&page=" + currentPage;
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject responseObject = new Gson().fromJson(response.toString(), JsonObject.class);
                totalPages = responseObject.get("total_pages").getAsInt();

                // API sayfa sınırını 500 ile sınırla
                if (totalPages > 500) {
                    totalPages = 500;
                }

                JsonArray results = responseObject.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject movieObject = element.getAsJsonObject();

                    int id = movieObject.get("id").getAsInt();
                    String title = movieObject.get("title").getAsString();
                    String releaseDate = movieObject.has("release_date") && !movieObject.get("release_date").isJsonNull()
                            ? movieObject.get("release_date").getAsString() : "Unknown";
                    double rating = movieObject.has("vote_average") && !movieObject.get("vote_average").isJsonNull()
                            ? movieObject.get("vote_average").getAsDouble() : 0.0;

                    // posterPath ve genreIds çıkarıldı
                    Movie movie = new Movie(id, title, releaseDate, rating, null, null);
                    movies.add(movie);
                }

                System.out.println("Page " + currentPage + " of " + totalPages + " loaded.");
                currentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
