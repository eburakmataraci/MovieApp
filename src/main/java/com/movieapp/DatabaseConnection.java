package com.movieapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/movies_db";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password"; // kendi MySQL şifren

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver bulunamadı.");
            throw new SQLException(e);
        }
    }
}
