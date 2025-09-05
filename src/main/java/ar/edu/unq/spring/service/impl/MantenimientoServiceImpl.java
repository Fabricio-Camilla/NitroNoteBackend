package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.persistence.MantenimientoDAO;
import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
        return mantenimientoDAO.findById(mantenimientoId)
                .orElseThrow(()-> new NoSuchElementException("Mantenimiento not found with id: " + mantenimientoId))
                .aModelo();
    }

    @Override
    public Set<Mantenimiento> allMantenimientos() {
        return mantenimientoDAO.findAll()
                .stream()
                .map(MantenimientoJPADTO::aModelo)
                .collect(Collectors.toSet());
    }

    @Override
    public Long guardarMantenimiento(Mantenimiento mantenimiento) {
        MantenimientoJPADTO mantenimientoJPADTO = MantenimientoJPADTO.desdeModelo(mantenimiento);
        mantenimientoDAO.save(mantenimientoJPADTO);
        mantenimiento.setId(mantenimientoJPADTO.getId());
        return mantenimiento.getId();
    }

    @Override
    public void deleteMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento.getId() == null) {
            throw new IllegalArgumentException("No se puede eliminar un mantenimiento sin ID");
        }
        mantenimientoDAO.deleteById(mantenimiento.getId());
    }

    @Override
    public void clearAll() {
        mantenimientoDAO.deleteAll();
    }


}
