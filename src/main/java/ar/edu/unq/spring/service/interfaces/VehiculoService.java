package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Vehiculo;

import java.util.List;

public interface VehiculoService {

    Vehiculo guardar(Vehiculo vehiculo);

    Vehiculo recuperar(String patente);

    void deleteAll();

    List<Vehiculo> recuperarTodos();

    void eliminar(String patente);

    void actualizar(Vehiculo vehiculo);
}
