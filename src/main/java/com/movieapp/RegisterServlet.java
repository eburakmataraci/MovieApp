package com.movieapp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        resp.setContentType("text/html; charset=UTF-8");

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ Veritabanına bağlantı başarılı");

            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rows = stmt.executeUpdate();
            System.out.println("🟢 Eklenen satır sayısı: " + rows);

            if (rows > 0) {
                resp.getWriter().println("<h2>✅ Kayıt başarılı: " + username + "</h2>");
            } else {
                resp.getWriter().println("<h2>❌ Kayıt başarısız</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().println("<h2>❌ Hata oluştu: " + e.getMessage() + "</h2>");
        }
    }
}
