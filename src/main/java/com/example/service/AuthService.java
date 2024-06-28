package com.example.service;

import com.example.dao.UsuarioDAO;
import com.example.model.Usuario;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UsuarioDAO usuarioDAO;

    public AuthService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean authenticate(String username, String password) {
        try {
            Optional<Usuario> usuarioOptional = usuarioDAO.findByUsername(username);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                if (usuario.getPassword().equals(password)) {
                    return true;
                } else {
                    // Incrementar el número de intentos fallidos
                    usuario.setAttempts(usuario.getAttempts() + 1);
                    usuarioDAO.updateAttempts(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
