package com.example.dao;

import com.example.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<Usuario> findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setAttempts(rs.getInt("attempts"));
                    user.setRole(rs.getString("role"));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    public void updateAttempts(Usuario user) throws SQLException {
        String query = "UPDATE usuarios SET attempts = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getAttempts());
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
        }
    }
}
