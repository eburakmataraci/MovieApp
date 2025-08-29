package com.movieapp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // ✅ Giriş başarılıysa film arama sayfasına yönlendir
                response.sendRedirect("search.html");
            } else {
                // ❌ Giriş başarısızsa uyarı mesajı göster
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<h2>❌ Geçersiz e-posta veya şifre</h2>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>❌ Veritabanı hatası</h2>");
        }
    }
}
