package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Vehiculo;
import lombok.NonNull;

public record VehiculoDTO(Long id,
                          @NonNull String patente,
                          @NonNull String marca,
                          @NonNull String modelo,
                          int anio,
                          int kilometros) {

    public static VehiculoDTO desdeModelo(Vehiculo vehiculo) {
        return new VehiculoDTO(
                vehiculo.getId(),
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getKilometros());
    }
}
