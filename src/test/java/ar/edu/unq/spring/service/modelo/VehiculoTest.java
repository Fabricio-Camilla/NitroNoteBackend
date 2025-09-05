package ar.edu.unq.spring.service.modelo;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.CantidadDeKilometrosMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehiculoTest {

    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        vehiculo = new Vehiculo("Chevrolet", "Trakcer", "AD010GI", 2020, 2000);
    }

    @Test
    public void unVehiculoRecienCreadoNoTieneMantenimientos() {
        assertTrue(vehiculo.getMantenimientos().isEmpty());
    }

    @Test
    public void seIntentaActualizarKilometros(){
        assertThrows(CantidadDeKilometrosMenorException.class, () -> {
            vehiculo.actualizarKilometros(200);
        });
    }

    @Test
    public void seAgregaUnMantenimiento(){
        var m = new Mantenimiento("Cambio aceite");
        vehiculo.guardarMantenimiento(m);

        assertTrue(vehiculo.getMantenimientos().contains(m));
    }

    @Test
    public void noSeDeberiaDePoderAgregarDosMantenimientosDelMismoTipo(){

        var m = new Mantenimiento("Cambio aceite");

        vehiculo.guardarMantenimiento(m);
        vehiculo.guardarMantenimiento(new Mantenimiento("Cambio aceite"));

        assertEquals(1,vehiculo.getMantenimientos().size());
    }

}
