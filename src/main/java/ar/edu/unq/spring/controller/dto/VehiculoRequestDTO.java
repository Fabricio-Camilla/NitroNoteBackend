package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Vehiculo;
import lombok.NonNull;

public record VehiculoRequestDTO (Long id,
                                  @NonNull String patente,
                                  @NonNull String marca,
                                  @NonNull String modelo,
                                  int anio,
                                  int kilometros ){

    public Vehiculo aModelo(){
        return new Vehiculo(this.marca, this.modelo, this.patente, this.anio, this.kilometros);
    }
}