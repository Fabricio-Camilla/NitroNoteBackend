package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.persistence.VehiculoDAO;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private VehiculoDAO vehiculoDAO;

    public VehiculoServiceImpl(VehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    @Override
    public void guardar(Vehiculo vehiculo) {
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
        Vehiculo vehiculoAEliminar = vehiculoDAO.findByPatente(patente).orElseThrow(VehiculoNoRegistradoException::new);

        vehiculoDAO.delete(vehiculoAEliminar);
    }
}