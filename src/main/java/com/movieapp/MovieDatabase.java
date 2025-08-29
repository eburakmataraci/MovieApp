package com.movieapp;

import java.sql.*;
import java.util.List;

public class MovieDatabase {
    private Connection connection;

    public MovieDatabase() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMoviesToDatabase(List<Movie> movies) {
        String query = "INSERT INTO movies (id, title, releaseDate, rating) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "title=VALUES(title), releaseDate=VALUES(releaseDate), rating=VALUES(rating)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Movie movie : movies) {
                statement.setInt(1, movie.getId());
                statement.setString(2, movie.getTitle());
                statement.setString(3, movie.getReleaseDate());
                statement.setDouble(4, movie.getRating());

                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("Movies saved to database.");
        } catch (SQLException e) {
            System.err.println("Filmleri kaydederken hata oluştu:");
            e.printStackTrace();
        }
    }
}
