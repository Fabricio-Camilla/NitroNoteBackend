package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.service.config.NitroNoteTest;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@NitroNoteTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VehiculoService vehiculoService;

    private Usuario usuario;

    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        Usuario usuario1 = usuarioService.register(new Usuario("unNombre", "unMail", "unPassword"));
        usuario = usuario1;
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

    @Test
    public void registrarUsuarioNuevoGuardaConPasswordEncriptada() {
        Usuario usuario = new Usuario("Juan", "juan@mail.com", "12345678");
        Usuario guardado = usuarioService.register(usuario);

        assertNotNull(guardado.getId());
        assertNotEquals("12345678", guardado.getPassword());
    }

    @Test
    public void registrarUsuarioDuplicadoLanzaExcepcion() {
        Usuario usuario1 = new Usuario("Ana", "ana@mail.com", "123456789");
        usuarioService.register(usuario1);

        Usuario usuario2 = new Usuario("Otra Ana", "ana@mail.com", "987654321");

        assertThrows(IllegalArgumentException.class, () -> usuarioService.register(usuario2));
    }

    @Test
    public void recuperarUsuarioInexistenteLanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> usuarioService.recuperarUsuario("noexiste@mail.com"));
    }


    @Test
    public void actualizarUsuarioModificaDatos() {
        Usuario usuario = usuarioService.register(new Usuario("Laura", "laura@mail.com", "pass"));
        usuario.setNombre("Laura Modificada");

        Usuario actualizado = usuarioService.actualizarUsuario(usuario);

        assertEquals("Laura Modificada", actualizado.getNombre());
    }

    @Test
    public void actualizarPreferenciasDeNotificacionPorEmail() {
        // Registrar usuario nuevo (por defecto emailNotificationsEnabled = false)
        Usuario nuevo = usuarioService.register(new Usuario("Carlos", "carlos@gmail.com", "password123"));

        Usuario recuperado = usuarioService.recuperarUsuario("carlos@gmail.com");
        assertFalse(recuperado.isEmailNotificationsEnabled(),
                "Por defecto el usuario no debería tener notificaciones por email habilitadas");

        // Actualizar preferencias (habilitar notificaciones por email)
        recuperado.setEmailNotificationsEnabled(true);
        Usuario actualizado = usuarioService.actualizarUsuario(recuperado);

        // Recuperar de nuevo para comprobar persistencia real
        Usuario desdeBD = usuarioService.recuperarUsuario("carlos@gmail.com");

        assertTrue(actualizado.isEmailNotificationsEnabled(),
                "El usuario actualizado debería tener las notificaciones por email activadas");
        assertTrue(desdeBD.isEmailNotificationsEnabled(),
                "El valor debería persistir correctamente en la base de datos");
    }

    @AfterEach
    public void tearDown() {
        usuarioService.clearAll();
        vehiculoService.deleteAll();
    }

}
