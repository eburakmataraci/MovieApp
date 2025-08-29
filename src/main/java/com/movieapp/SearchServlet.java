package com.movieapp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String query = req.getParameter("query");

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println("<h2>🔍 Arama Sonucu: " + query + "</h2>");
    }
}
