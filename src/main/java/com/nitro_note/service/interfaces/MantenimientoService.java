package com.nitro_note.service.interfaces;

import com.nitro_note.modelo.Mantenimiento;

import java.util.Set;

public interface MantenimientoService {
    Set<Mantenimiento> allMantenimientos();
    Long guardarMantenimiento(Mantenimiento mantenimiento);
    Mantenimiento recuperarMantenimiento(Long mantenimientoId);
    void deleteMantenimiento(Mantenimiento mantenimiento);
    void clearAll();
}
