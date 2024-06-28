package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/estudiantes")
public class EstudiantesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dbURL = getServletContext().getInitParameter("dbURL");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        response.setContentType("text/html");
        try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
            String sql = "SELECT * FROM estudiantes";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            response.getWriter().println("<h1>CRUD de Estudiantes</h1>");
            response.getWriter().println("<table border='1'><tr><th>ID</th><th>Nombre</th></tr>");
            while (resultSet.next()) {
                response.getWriter().println("<tr><td>" + resultSet.getInt("id") + "</td><td>" + resultSet.getString("nombre") + "</td></tr>");
            }
            response.getWriter().println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
