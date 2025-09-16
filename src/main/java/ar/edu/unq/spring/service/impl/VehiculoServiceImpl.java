package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.persistence.VehiculoDAO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final Validator validator;
    private final VehiculoDAO vehiculoDAO;

    public VehiculoServiceImpl(VehiculoDAO vehiculoDAO,  Validator validator) {
        this.vehiculoDAO = vehiculoDAO;
        this.validator = validator;
    }

    @Override
    public Vehiculo guardar(Vehiculo vehiculo) {
        Set<ConstraintViolation<Vehiculo>> errores = validator.validate(vehiculo);
        if(!errores.isEmpty()) {
            throw new IllegalArgumentException("Los campos ingresados son inválidos");
        }
        VehiculoJPADTO dto = VehiculoJPADTO.desdeModelo(vehiculo);
        vehiculoDAO.save(dto);
        vehiculo.setId(dto.getId());

        return vehiculo;
    }

    @Override
    public Vehiculo recuperar(String patente) {
        return vehiculoDAO.findByPatente(patente).orElseThrow(VehiculoNoRegistradoException::new).aModelo();
    }

    @Override
    public void deleteAll() {
        vehiculoDAO.deleteAll();
    }

    @Override
    public List<Vehiculo> recuperarTodos() {
        return vehiculoDAO.findAll().stream().map(VehiculoJPADTO::aModelo).collect(Collectors.toList());
    }

    @Override
    public void eliminar(String patente) {
        vehiculoDAO.eliminarByPatente(patente);
    }

    @Override
    public void actualizar(Vehiculo vehiculo) {
        Vehiculo vehiculoActualizar = vehiculoDAO.findByPatente(vehiculo.getPatente())
                .orElseThrow(VehiculoNoRegistradoException::new).aModelo();

        vehiculoActualizar.setKilometros(vehiculo.getKilometros());

        vehiculoDAO.save(VehiculoJPADTO.desdeModelo(vehiculoActualizar)).aModelo();
    }


}
