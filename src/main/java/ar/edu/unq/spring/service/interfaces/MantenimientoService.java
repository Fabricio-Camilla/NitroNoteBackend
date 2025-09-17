package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.CreateMantenimientoDTO;
import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;

import java.util.Set;

public interface MantenimientoService {
    Set<Mantenimiento> allMantenimientos();

    Mantenimiento guardarMantenimiento(CreateMantenimientoDTO createMantenimientoDTO);

    //Esto de abajo devolvia Long, pelotudos
    Mantenimiento guardarMantenimiento(Mantenimiento mantenimiento);
    Mantenimiento recuperarMantenimiento(Long mantenimientoId);
    void deleteMantenimiento(Mantenimiento mantenimiento);
    void clearAll();
    //getAllHechas()
    //getAllInProgress()
}
