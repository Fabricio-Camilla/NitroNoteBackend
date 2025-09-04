package com.nitro_note.service.interfaces;

import com.nitro_note.modelo.Mantenimiento;

public interface MantenimientoService {
    Mantenimiento getMantenimiento(Long id);
    Long guardarMantenimiento(Mantenimiento mantenimiento);
    void deleteMantenimiento(Mantenimiento mantenimiento);

}
