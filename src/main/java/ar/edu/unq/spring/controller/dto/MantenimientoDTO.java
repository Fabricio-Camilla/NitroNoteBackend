package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;

import java.time.LocalDate;

public record MantenimientoDTO(
        Long id,
        String nombre,
        String fechaARealizar,
        String fechaDeRealizacion,
        Integer kmARealizar,
        boolean finalizado,
        Long vehiculo_id,
        String patente
) {

    public static MantenimientoDTO desdeModelo(Mantenimiento mantenimiento) {
        Vehiculo vehiculo = mantenimiento.getVehiculo();
        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getNombre(),
                mantenimiento.getFechaARealizar() != null ? mantenimiento.getFechaARealizar().toString() : "",
                mantenimiento.getFechaDeRealizacion() != null ? mantenimiento.getFechaDeRealizacion().toString() : "",
                mantenimiento.getKmARealizar(),
                mantenimiento.isFinalizado(),
                vehiculo.getId(),
                vehiculo.getPatente() != null ? vehiculo.getPatente() : ""
        );
    }

    public Mantenimiento aModelo() {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(this.id);
        mantenimiento.setNombre(this.nombre);
        mantenimiento.setFechaARealizar(
                (this.fechaARealizar != null && !this.fechaARealizar.isBlank())
                        ? LocalDate.parse(this.fechaARealizar)
                        : null
        );
        mantenimiento.setFechaDeRealizacion(
                (this.fechaDeRealizacion != null && !this.fechaDeRealizacion.isBlank())
                        ? LocalDate.parse(this.fechaDeRealizacion)
                        : null
        );
        mantenimiento.setKmARealizar(this.kmARealizar != null ? this.kmARealizar : 0);
        mantenimiento.setFinalizado(this.finalizado);
        return mantenimiento;
    }

    public Mantenimiento aModelo(Vehiculo vehiculo) {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(this.id);
        mantenimiento.setNombre(this.nombre);
        mantenimiento.setFechaARealizar(
                (this.fechaARealizar != null && !this.fechaARealizar.isBlank())
                        ? LocalDate.parse(this.fechaARealizar)
                        : null
        );
        mantenimiento.setFechaDeRealizacion(
                (this.fechaDeRealizacion != null && !this.fechaDeRealizacion.isBlank())
                        ? LocalDate.parse(this.fechaDeRealizacion)
                        : null
        );
        mantenimiento.setKmARealizar(this.kmARealizar != null ? this.kmARealizar : 0);
        mantenimiento.setFinalizado(this.finalizado);
        mantenimiento.setVehiculo(vehiculo);
        return mantenimiento;
    }
}
