package com.movieapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/movies")
public class


MovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM movies";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>🎬 Tüm Filmler</h1>");
            out.println("<ul>");

            while (rs.next()) {
                String title = rs.getString("title");
                String director = rs.getString("director");
                double rating = rs.getDouble("rating");

                out.println("<li>" + title + " - " + director + " - Puan: " + rating + "</li>");
            }

            out.println("</ul>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("❗ Film verileri alınamadı: " + e.getMessage());
        }
    }
}
