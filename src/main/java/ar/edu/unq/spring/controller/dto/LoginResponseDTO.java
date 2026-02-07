package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Usuario;

public record LoginResponseDTO(String token, UserLoginDTO user) {

    public static LoginResponseDTO desdeModelo(String token, Usuario usuario) {
        UserLoginDTO userDTO = UserLoginDTO.desdeModelo(usuario);
        return new LoginResponseDTO(token, userDTO);
    }
}
