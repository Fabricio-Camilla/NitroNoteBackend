package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;

public record LoginRequestDTO(String email, String password) {

    public UsuarioJPADTO aModelo(){
        UsuarioJPADTO usuario = new UsuarioJPADTO();
        usuario.setEmail(email);
        usuario.setPassword(password);
        return usuario;
    }
}
