package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VehiculoService vehiculoService;

    private Usuario usuario;

    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("unNombre", "unMail", "unPassword");

        Usuario usuario1 = usuarioService.register(usuario);
        vehiculo = new Vehiculo("Ford", "Focus", "AD010GA", 2021, 2000, usuario1.getId());
    }

    @Test
    public void unUsuarioRecienGuardadoNoTieneVehiculos(){
        var user = usuarioService.recuperarUsuario(usuario.getEmail());

        assertTrue(user.getVehiculos().isEmpty());
    }

    @Test
    public void aUnUsuarioSeLeGuardaUnVehiculo(){
        vehiculoService.guardar(vehiculo);

        var vehiculosDeUser = vehiculoService.vehiculosByUserId(usuario.getId());

        assertEquals(1, vehiculosDeUser.size());
    }

    @AfterEach
    public void tearDown() {
        usuarioService.clearAll();
        vehiculoService.deleteAll();
    }

}
