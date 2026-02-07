package ar.edu.unq.spring.controller.dto;


import ar.edu.unq.spring.modelo.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UserLoginDTO(Long id,
                           String nombre,
                           String email,
                           String role,
                           boolean emailNotificationsEnabled,
                           boolean pushNotificationsEnabled,
                           List<VehiculoLoginDTO> vehiculos) {

    public static UserLoginDTO desdeModelo(Usuario user) {
        return new UserLoginDTO(
                user.getId(),
                user.getNombre(),
                user.getEmail(),
                user.getRole(),
                user.isEmailNotificationsEnabled(),
                user.isPushNotificationsEnabled(),
                user.getVehiculos()
                        .stream()
                        .map(VehiculoLoginDTO::desdeModelo)
                        .collect(Collectors.toList())
        );
    }
}
