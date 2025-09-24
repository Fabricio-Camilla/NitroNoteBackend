package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;

import java.time.LocalDate;

public record MantenimientoDTO(
        Long id,
        String nombre,
        LocalDate fechaARealizar,
        LocalDate fechaDeRealizacion,
        Integer kmARealizar,
        boolean finalizado,
        String vehiculoId
) {

    public static MantenimientoDTO desdeModelo(Mantenimiento mantenimiento) {
        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getNombre(),
                mantenimiento.getFechaARealizar(),
                mantenimiento.getFechaDeRealizacion(),
                mantenimiento.getKmARealizar(),
                mantenimiento.isFinalizado(),
                mantenimiento.getVehiculo() != null ? mantenimiento.getVehiculo().getPatente() : null
        );
    }

    public Mantenimiento aModelo() {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(this.id);
        mantenimiento.setNombre(this.nombre);
        mantenimiento.setFechaARealizar(this.fechaARealizar);
        mantenimiento.setFechaDeRealizacion(this.fechaDeRealizacion);
        mantenimiento.setKmARealizar(this.kmARealizar != null ? this.kmARealizar : 0);
        mantenimiento.setFinalizado(this.finalizado);
        //No seteamos el vehículo acá
        return mantenimiento;
    }
}
