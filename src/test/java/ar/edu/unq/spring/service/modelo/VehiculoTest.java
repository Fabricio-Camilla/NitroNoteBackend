package ar.edu.unq.spring.service.modelo;

import ar.edu.unq.spring.modelo.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehiculoTest {

    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        vehiculo = new Vehiculo("Chevrolet", "Trakcer", "AD010GI", 2020, 200);
    }

    @Test
    public void unVehiculoRecienCreadoNoTieneMantenimientos() {
        assertTrue(vehiculo.getMantenimientos().isEmpty());
    }



}
