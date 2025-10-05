package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Usuario;

public record UsuarioDTO(String email, String nombre, String role) {
    public static UsuarioDTO desdeModelo(Usuario u) {
        return new UsuarioDTO(u.getEmail(), u.getNombre(), "ROLE_USER");
    }
}
