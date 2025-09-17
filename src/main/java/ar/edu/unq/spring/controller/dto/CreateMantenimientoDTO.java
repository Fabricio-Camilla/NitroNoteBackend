package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Mantenimiento;

import java.time.LocalDate;

public record CreateMantenimientoDTO(
        String nombre,
        LocalDate fechaARealizar,
        Integer kmARealizar,
        Long vehiculoId  // Solo recibimos el ID del vehículo
) {
    public Mantenimiento aModelo() {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setNombre(this.nombre);
        mantenimiento.setFechaARealizar(this.fechaARealizar);
        mantenimiento.setKmARealizar(this.kmARealizar != null ? this.kmARealizar : 0);
        // No pongo el vehículo aca, lo hago en el servicio
        return mantenimiento;
    }
}
