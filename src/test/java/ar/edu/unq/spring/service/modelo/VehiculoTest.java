package ar.edu.unq.spring.service.modelo;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculoTest {

    private Vehiculo vehiculo;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("unNombre", "unMail", "unPassword");
        vehiculo = new Vehiculo("Chevrolet", "Tracker", "AD010GI", 2020, 2000, usuario);
    }

    @Test
    public void crearVehiculo() {
        assertEquals("Chevrolet", vehiculo.getMarca());
        assertEquals("Tracker", vehiculo.getModelo());
        assertEquals("AD010GI", vehiculo.getPatente());
        assertEquals(2020, vehiculo.getAnio());
        assertEquals(2000, vehiculo.getKilometros());
    }


    @Test
    public void seQuiereModificarUnModeloInvalido(){
        assertThrows(ModeloInexistenteException.class,() -> {

            vehiculo.setModelo("este");
        });
    }

    @Test
    public void seQuiereModificarUnaMarcaInvalida(){
        assertThrows(MarcaInexistenteException.class,() -> {

            vehiculo.setMarca("este");
        });
    }


    @Test
    public void seIntentaActualizarKilometrosMenorALosQueYaTeniaYSeLanzaUnException(){

        assertEquals(2000, vehiculo.getKilometros());
        assertThrows(CantidadDeKilometrosMenorException.class, () -> {
            vehiculo.setKilometros(200);
        });
    }

    @Test
    public void seLeIntentaCambiarAUnAnioInvalido(){

        assertEquals(2020, vehiculo.getAnio());
        assertThrows(AnioIngresadoInvalidoException.class, () -> {
            vehiculo.setAnio(0);
        });
    }

    @Test
    public void seIntentaInstanciarUnVehiculoConKilometroInvalido(){

        assertThrows(CantidadDeKilometrosInvalidaException.class, () -> {
            Vehiculo v = new Vehiculo(
                    "Chevrolet",
                    "Tracker",
                    "AD010GI",
                    2020,
                    -1,
                    usuario);
        });
    }


    @Test
    public void eliminarUnVehiculo(){
        Usuario user = new Usuario("Nombre", "email", "pass");
        assertFalse(user.getVehiculos().contains(vehiculo));

        user.agregarVehiculo(vehiculo);
        vehiculo.setUsuario(user);

        assertTrue(user.getVehiculos().contains(vehiculo));

        user.eliminarVehiculo(vehiculo);
        assertFalse(user.getVehiculos().contains(vehiculo));
    }
}
