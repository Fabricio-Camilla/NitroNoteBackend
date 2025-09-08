package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Vehiculo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;

public record VehiculoRequestDTO (Long id,
                                  @NonNull
                                  @NotBlank(message = "La patente no puede estar vacia")
                                  @Pattern(regexp = "^([A-Za-z]{2}[0-9]{3}[A-Za-z]{2}|[A-Za-z]{3}[0-9]{3})$")
                                  String patente,

                                  @NonNull
                                  @NotBlank(message = "La marca no puede estar vacia")
                                  @Pattern(regexp = "^[A-Za-z]+$")
                                  String marca,

                                  @NonNull
                                  @NotBlank(message = "El modelo no puede estar vacio")
                                  @Pattern(regexp = "^[A-Za-z]+$")
                                  String modelo,

                                  int anio,

                                  int kilometros ){

    public Vehiculo aModelo(){
        return new Vehiculo(
                this.marca.isBlank() ? null : this.marca,
                this.modelo.isBlank() ? null : this.modelo,
                this.patente.isBlank() ? null : this.patente.toUpperCase(),
                this.anio,
                this.kilometros);
    }
}