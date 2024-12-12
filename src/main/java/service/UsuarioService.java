package service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;

public class UsuarioService {
    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    public boolean crearUsuario(Usuario usuario) {
        // Verificar si ya existe un usuario con el mismo username
        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        usuarios.add(usuario);
        return true;
    }

    public Usuario buscarUsuario(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarNombre(String username, String nuevoNombre) {
        Usuario usuario = buscarUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        usuario.setNombre(nuevoNombre);
        return true;
    }

    public boolean actualizarPassword(String username, String nuevaPassword) {
        Usuario usuario = buscarUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        usuario.setPassword(nuevaPassword);
        return true;
    }

    public boolean actualizarRol(String username, String nuevoRol) {
        Usuario usuario = buscarUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        usuario.setRol(nuevoRol);
        return true;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
}