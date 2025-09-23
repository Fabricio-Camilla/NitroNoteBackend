package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.persistence.MantenimientoDAO;
import ar.edu.unq.spring.persistence.VehiculoDAO;
import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    private final MantenimientoDAO mantenimientoDAO;
    private final VehiculoDAO vehiculoDAO;

    public MantenimientoServiceImpl(MantenimientoDAO mantenimientoDAO, VehiculoDAO vehiculoDAO) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.vehiculoDAO = vehiculoDAO;
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
    public Mantenimiento crearMantenimiento(Mantenimiento mantenimiento, Long vehiculoId) {
        VehiculoJPADTO vehiculoDTO = vehiculoDAO.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        // Asociar el vehículo al mantenimiento ANTES de convertirlo a DTO
        mantenimiento.setVehiculo(vehiculoDTO.aModelo());

        MantenimientoJPADTO mantenimientoJPADTO = MantenimientoJPADTO.desdeModelo(mantenimiento);
        MantenimientoJPADTO guardado = mantenimientoDAO.save(mantenimientoJPADTO);

        mantenimiento.setId(guardado.getId());
        return mantenimiento;
    }

    @Override
    public Mantenimiento guardarMantenimiento(Mantenimiento mantenimiento, Long vehiculoId) {
        vincularVehiculo(mantenimiento, vehiculoId);
        MantenimientoJPADTO mantenimientoJPADTO = MantenimientoJPADTO.desdeModelo(mantenimiento);
        MantenimientoJPADTO guardado = mantenimientoDAO.save(mantenimientoJPADTO);
        mantenimiento.setId(guardado.getId());
        return mantenimiento;
    }

    private void vincularVehiculo(Mantenimiento mantenimiento, Long vehiculoId) {
        VehiculoJPADTO vehiculoDTO = vehiculoDAO.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
        mantenimiento.setVehiculo(vehiculoDTO.aModelo());
    }

    @Override
    public Mantenimiento actualizarMantenimiento(Mantenimiento mantenimiento) {
        Mantenimiento existente = mantenimientoDAO.findById(mantenimiento.getId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Mantenimiento no encontrado con id: " + mantenimiento.getId()
                ))
                .aModelo();

        existente.setFechaARealizar(mantenimiento.getFechaARealizar());
        existente.setFinalizado(mantenimiento.isFinalizado());
        existente.setKmARealizar(mantenimiento.getKmARealizar());

        MantenimientoJPADTO dto = MantenimientoJPADTO.desdeModelo(existente);
        return mantenimientoDAO.save(dto).aModelo();

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
