package ar.edu.unq.spring.controller.dto;


import jakarta.validation.constraints.Size;

public record UserPrefsDTO(
        String email,
        String nombre,

        @Size(min = 8, message = "La contraseña no puede tener menos de 8 caracteres")
        String password,

        boolean emailEnabled,
        boolean pushEnabled,
        String pushToken){



}
