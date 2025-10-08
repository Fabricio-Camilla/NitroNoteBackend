package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.service.interfaces.EmailService;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import ar.edu.unq.spring.service.interfaces.NotificationService;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private MantenimientoService mantenimientoService;

    @MockBean
    private EmailService emailService; // mockeamos para no enviar correos reales

    private Usuario usuario;
    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        // Crear un usuario con notificaciones activadas
        usuario = new Usuario("Leandro", "leandro@test.com", "12345678");
        usuario.setEmailNotificationsEnabled(true);
        usuario = usuarioService.register(usuario);

        // Crear vehículo asociado
        vehiculo = new Vehiculo("Toyota", "Corolla", "AD123BC", 2022, 10000, usuario.getId());
        vehiculo = vehiculoService.guardar(vehiculo);

        // Crear mantenimiento que vence hoy
        Mantenimiento mantenimiento = new Mantenimiento(
                "Cambio de aceite", LocalDate.now(), 15000);
        mantenimiento.setVehiculo(vehiculo);
        mantenimientoService.guardarMantenimiento(mantenimiento, vehiculo.getId());
    }

    @Test
    public void seEnviaUnEmailCuandoHayMantenimientoVenciendoHoy() {
        notificationService.enviarRecordatoriosDelDia();

        // Verificamos que se envió el mail al usuario correcto
        verify(emailService, times(1)).send(
                eq("leandro@test.com"),
                contains("Recordatorio"),
                contains("Cambio de aceite")
        );
    }

    @Test
    public void noSeEnviaEmailSiElUsuarioTieneNotificacionesDesactivadas() {
        usuario.setEmailNotificationsEnabled(false);
        usuarioService.actualizarUsuario(usuario);

        notificationService.enviarRecordatoriosDelDia();

        // No se envía ningún correo
        verify(emailService, never()).send(any(), any(), any());
    }

    @Test
    public void noSeEnviaEmailSiNoHayMantenimientosVenciendoHoy() {
        // Eliminar mantenimientos
        mantenimientoService.clearAll();
        entityManager.flush();
        entityManager.clear();

        notificationService.enviarRecordatoriosDelDia();

        // Verificar que no se envió nada
        verify(emailService, never()).send(any(), any(), any());
    }

    @AfterEach
    public void tearDown() {
        mantenimientoService.clearAll();
        vehiculoService.deleteAll();
        usuarioService.clearAll();
    }
}
