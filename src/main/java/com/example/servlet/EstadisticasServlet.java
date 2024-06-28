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

@WebServlet("/estadisticas")
public class EstadisticasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dbURL = getServletContext().getInitParameter("dbURL");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        response.setContentType("text/html");
        try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
            String sql = "SELECT carrera, COUNT(*) AS cantidad FROM estudiantes GROUP BY carrera";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            response.getWriter().println("<h1>Gráfico Estadístico</h1>");
            response.getWriter().println("<table border='1'><tr><th>Carrera</th><th>Cantidad</th></tr>");
            while (resultSet.next()) {
                response.getWriter().println("<tr><td>" + resultSet.getString("carrera") + "</td><td>" + resultSet.getInt("cantidad") + "</td></tr>");
            }
            response.getWriter().println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
