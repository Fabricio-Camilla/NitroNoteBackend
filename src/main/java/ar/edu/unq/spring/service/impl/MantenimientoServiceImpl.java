package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.CreateMantenimientoDTO;
import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.persistence.MantenimientoDAO;
import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    private final MantenimientoDAO mantenimientoDAO;
    private final VehiculoService vehiculoService; // Inyectamos el servicio de vehículos

    public MantenimientoServiceImpl(MantenimientoDAO mantenimientoDAO, VehiculoService vehiculoService) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.vehiculoService = vehiculoService;
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
    public Mantenimiento guardarMantenimiento(CreateMantenimientoDTO createMantenimientoDTO) {
        // Buscar el vehículo por ID
        Vehiculo vehiculo = vehiculoService.recuperarPorId(createMantenimientoDTO.vehiculoId());

        // Crear el mantenimiento desde el DTO
        Mantenimiento mantenimiento = createMantenimientoDTO.aModelo();

        // Vincular el mantenimiento con el vehículo
        mantenimiento.setVehiculo(vehiculo);

        // Guardar el mantenimiento
        MantenimientoJPADTO mantenimientoJPADTO = MantenimientoJPADTO.desdeModelo(mantenimiento);
        mantenimientoDAO.save(mantenimientoJPADTO);
        mantenimiento.setId(mantenimientoJPADTO.getId());

        return mantenimiento;
    }

    @Override
    public Mantenimiento guardarMantenimiento(Mantenimiento mantenimiento) {
        MantenimientoJPADTO mantenimientoJPADTO = MantenimientoJPADTO.desdeModelo(mantenimiento);
        mantenimientoDAO.save(mantenimientoJPADTO);
        mantenimiento.setId(mantenimientoJPADTO.getId());
        return mantenimiento;
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
