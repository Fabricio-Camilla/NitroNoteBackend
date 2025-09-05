package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehiculoServiceTest {

    @Autowired
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;
    private Vehiculo vehiculo2;

    @BeforeEach
    public void setUp() {
        vehiculo = new Vehiculo("Chevrolet", "Trakcer", "AD010GI", 2020, 2000);
        vehiculo2 = new Vehiculo("Ford", "Focus", "AD010GA", 2021, 2000);

    }

    @Test
    public void recuperoUnVehiculoRecienCreado(){
        vehiculoService.guardar(vehiculo);

        Vehiculo vehiculoRecuperado = vehiculoService.recuperar("AD010GI");

        assertEquals("AD010GI", vehiculoRecuperado.getPatente());
    }

    @Test
    public void seIntentaRecuprarUnVehiculoInexistente(){
        assertThrows(VehiculoNoRegistradoException.class, () ->
                vehiculoService.recuperar("AD010GI")
        );
    }

    @Test
    public void seObtieneTodosLosVehiculosRegistrados(){
        vehiculoService.guardar(vehiculo);
        vehiculoService.guardar(vehiculo2);

        List<Vehiculo> vehiculos =  vehiculoService.recuperarTodos();

        assertEquals(2, vehiculos.size());
    }

    @Test
    public void seDebePoderEliminarUnVehiculoPorSuPatente(){

        vehiculoService.guardar(vehiculo);
        vehiculoService.guardar(vehiculo2);

        vehiculoService.eliminar(vehiculo.getPatente());

        List<Vehiculo> vehiculos =  vehiculoService.recuperarTodos();

        assertFalse(vehiculos.contains(vehiculo));
    }

    @AfterEach
    public void tearDown() {
        //vehiculoService.deleteAll();
    }
}
