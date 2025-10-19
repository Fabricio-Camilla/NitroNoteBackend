package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Vehiculo;

public record VehiculoSimpleDTO(Long id, String patente,  String marca, String modelo,int anio ,int kilometros, Long usuarioID) {

    public static VehiculoSimpleDTO desdeModelo(Vehiculo vehiculo){
        return new VehiculoSimpleDTO(
                vehiculo.getId(),
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getKilometros(),
                vehiculo.getUsuarioID());
    }
}
