package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Usuario;

public record RegisterRequestDTO(String nombre, String email, String password) {

    public Usuario aModelo() {
        var user = new Usuario(nombre,email,password);
        user.setRole("ROLE_USER");
        return user;
    }
}
