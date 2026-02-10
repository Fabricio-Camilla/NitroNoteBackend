package ar.edu.unq.spring.controller.dto;


import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.dto.NotificacionJPADTO;

import java.util.List;
import java.util.stream.Collectors;

public record UserLoginDTO(Long id,
                           String nombre,
                           String email,
                           String role,
                           List<NotificacionJPADTO> notificaciones,
                           List<VehiculoLoginDTO> vehiculos) {

    public static UserLoginDTO desdeModelo(Usuario user) {
        return new UserLoginDTO(
                user.getId(),
                user.getNombre(),
                user.getEmail(),
                user.getRole(),
                user.getNotificationPreferences()
                        .stream()
                        .map(NotificacionJPADTO::desdeModelo)
                        .collect(Collectors.toList()),
                user.getVehiculos()
                        .stream()
                        .map(VehiculoLoginDTO::desdeModelo)
                        .collect(Collectors.toList())
        );
    }
}
