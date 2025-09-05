package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.persistence.VehiculoDAO;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;


import java.util.List;
import java.util.Set;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final Validator validator;
    private VehiculoDAO vehiculoDAO;

    public VehiculoServiceImpl(VehiculoDAO vehiculoDAO,  Validator validator) {
        this.vehiculoDAO = vehiculoDAO;
        this.validator = validator;
    }

    @Override
    public void guardar(Vehiculo vehiculo) {
        Set<ConstraintViolation<Vehiculo>> errores = validator.validate(vehiculo);
        if(!errores.isEmpty()) {
            throw new IllegalArgumentException("Los campos ingresados son invalidos");
        }
        vehiculoDAO.save(vehiculo);
    }

    @Override
    public Vehiculo recuperar(String patente) {
        return vehiculoDAO.findByPatente(patente).orElseThrow(VehiculoNoRegistradoException::new);
    }

    @Override
    public void deleteAll() {
        vehiculoDAO.deleteAll();
    }

    @Override
    public List<Vehiculo> recuperarTodos() {
        return vehiculoDAO.findAll();
    }

    @Override
    public void eliminar(String patente) {
        Vehiculo vehiculoAEliminar = vehiculoDAO.findByPatente(patente)
                .orElseThrow(VehiculoNoRegistradoException::new);

        vehiculoDAO.delete(vehiculoAEliminar);
    }

    @Override
    public void agregarMantenimiento(String patente, Mantenimiento mantenimiento) {
        Vehiculo recuperado = vehiculoDAO.findByPatente(patente)
                .orElseThrow(VehiculoNoRegistradoException::new);
//se supone que hago una query para encontrar el mantenimiento asociado al vehiculo y saber si es del
        //tipo que quiere agregar nuevo, si isPresent exception
        mantenimientoDAO.tieneVehiculoElMantenimiento(recuperado.getId(), mantenimiento.getNombre());
        recuperado.guardarMantenimiento(mantenimiento);

        vehiculoDAO.save(recuperado);
    }
}