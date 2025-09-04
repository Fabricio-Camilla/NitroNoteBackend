package com.nitro_note.service.impl;

import com.nitro_note.modelo.Mantenimiento;
import com.nitro_note.persistence.dao.MantenimientoDAO;
import com.nitro_note.service.interfaces.MantenimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {
    private final MantenimientoDAO mantenimientoDAO;

    public MantenimientoServiceImpl(MantenimientoDAO mantenimientoDAO) {
        this.mantenimientoDAO = mantenimientoDAO;
    }


    @Override
    public Mantenimiento recuperarMantenimiento(Long mantenimientoId) {
        return mantenimientoDAO.findById(mantenimientoId).orElse(null);
    }

    @Override
    public Set<Mantenimiento> allMantenimientos() {
        return Set.of();
    }

    @Override
    public Long guardarMantenimiento(Mantenimiento mantenimiento) {
        return mantenimientoDAO.save(mantenimiento).getId();
    }

    @Override
    public void deleteMantenimiento(Mantenimiento mantenimiento) {
        mantenimientoDAO.delete(mantenimiento);
    }

    @Override
    public void clearAll() {

    }


}
