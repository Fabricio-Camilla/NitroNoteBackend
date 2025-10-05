package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Usuario;

public record UpdateUserResponseDTO(String token, UsuarioDTO user) {

    public static UpdateUserResponseDTO desdeModelo(String token, Usuario usuario) {
        return new UpdateUserResponseDTO(token, UsuarioDTO.desdeModelo(usuario));
    }
}
