package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;

public record RegisterRequestDTO(String nombre, String email, String password) {

    public UsuarioJPADTO aModelo() {
        var user = new UsuarioJPADTO(nombre,email,password);
        user.setRole("ROLE_USER");
        return user;
    }
}
