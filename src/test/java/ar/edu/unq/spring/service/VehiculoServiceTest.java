package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.modelo.exception.CantidadDeKilometrosMenorException;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.service.config.NitroNoteTest;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@NitroNoteTest
public class VehiculoServiceTest {

    @Autowired
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;
    private Vehiculo vehiculo2;
    private Usuario usuario;
    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario( "unNombre", "unMail", "unPassword");
        Usuario usuario1 = usuarioService.register(usuario);
        vehiculo = new Vehiculo("Chevrolet", "Tracker", "AD010GI", 2020, 2000, usuario1.getId());
        vehiculo2 = new Vehiculo("Ford", "Focus", "AD010GA", 2021, 2000, usuario1.getId());
    }

    @Test
    public void recuperoUnVehiculoRecienCreado(){


        Vehiculo vehiculoRecuperado = vehiculoService.guardar(vehiculo);;

        assertEquals("AD010GI", vehiculoRecuperado.getPatente());
        assertEquals("Chevrolet", vehiculoRecuperado.getMarca());
        assertEquals("Tracker", vehiculoRecuperado.getModelo());
        assertEquals(2020, vehiculoRecuperado.getAnio());
        assertEquals(2000, vehiculoRecuperado.getKilometros());
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

    @Test
    public void seActualizaElKilometrajeDeUnVehiculoYaRegistrado(){
        vehiculoService.guardar(vehiculo);

        vehiculo.setKilometros(300000);

        vehiculoService.actualizar(vehiculo);

        Vehiculo recuperado = vehiculoService.recuperar(vehiculo.getPatente());

        assertEquals(300000, recuperado.getKilometros());
    }

    @Test
    public void seIntentaActualizarElKilometrajeMenorAlQueTeniaYLanzaUnaException(){
        vehiculoService.guardar(vehiculo);

        assertThrows(CantidadDeKilometrosMenorException.class, () -> {
            vehiculo.setKilometros(200);
        });
    }

   @AfterEach
   public void tearDown() {
        vehiculoService.deleteAll();
        usuarioService.clearAll();
   }
}
