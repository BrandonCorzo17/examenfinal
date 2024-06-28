package com.example.dao;

import com.example.model.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    private final Connection connection;

    public EstudianteDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Estudiante estudiante) throws SQLException {
        String query = "INSERT INTO estudiantes (nombre, apellido, carrera) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getCarrera());
            stmt.executeUpdate();
        }
    }

    public void update(Estudiante estudiante) throws SQLException {
        String query = "UPDATE estudiantes SET nombre = ?, apellido = ?, carrera = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getCarrera());
            stmt.setInt(4, estudiante.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM estudiantes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Estudiante> findAll() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT * FROM estudiantes";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setId(rs.getInt("id"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setCarrera(rs.getString("carrera"));
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public Estudiante findById(int id) throws SQLException {
        String query = "SELECT * FROM estudiantes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(rs.getInt("id"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApellido(rs.getString("apellido"));
                    estudiante.setCarrera(rs.getString("carrera"));
                    return estudiante;
                }
            }
        }
        return null;
    }
}
