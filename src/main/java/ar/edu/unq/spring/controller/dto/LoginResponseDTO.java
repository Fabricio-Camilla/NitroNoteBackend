package ar.edu.unq.spring.controller.dto;

import org.springframework.security.core.userdetails.UserDetails;

public record LoginResponseDTO(String token, UserDetails user) {

    public static LoginResponseDTO desdeModelo(String token, UserDetails user) {
        return new LoginResponseDTO(token, user);
    }
}
