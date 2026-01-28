package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Vehiculo;

public record VehiculoLoginDTO(Long id,
                               String marca,
                               String modelo,
                               String patente,
                               int kilometros,
                               int anio) {
    public static VehiculoLoginDTO desdeModelo(Vehiculo vehiculo) {
        return new VehiculoLoginDTO(
                vehiculo.getId(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getPatente(),
                vehiculo.getKilometros(),
                vehiculo.getAnio()
        );
    }
}
