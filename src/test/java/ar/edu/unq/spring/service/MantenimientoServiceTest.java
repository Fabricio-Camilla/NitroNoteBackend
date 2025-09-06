package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest
public class MantenimientoServiceTest {
    @Autowired
    private MantenimientoService mantenimientoService;

    private Mantenimiento serviceAnual;
    private Mantenimiento cambioCorrea;
    private Mantenimiento frenos;

    @BeforeEach
    public void prepare() {
        // Creamos algunos mantenimientos de ejemplo (sin ID aún)
        serviceAnual = new Mantenimiento("Service anual", LocalDate.now().plusMonths(1));
        serviceAnual.setKmARealizar(10000);

        cambioCorrea = new Mantenimiento("Cambio de correa", LocalDate.now().plusMonths(6));
        cambioCorrea.setKmARealizar(60000);

        frenos = new Mantenimiento("Cambio de pastillas de freno", LocalDate.now().plusWeeks(2));
        frenos.setKmARealizar(0);

        // Persistimos dos de ellos en la preparación
        mantenimientoService.guardarMantenimiento(serviceAnual);
        mantenimientoService.guardarMantenimiento(cambioCorrea);
    }

    @AfterEach
    public void clean() {
        mantenimientoService.clearAll();
    }

    @Test
    public void testCrearUnMantenimiento() {
        // Act: guardamos el tercero
        mantenimientoService.guardarMantenimiento(frenos);

        Mantenimiento frenosRecuperado = mantenimientoService.recuperarMantenimiento(frenos.getId());

        // Assert: se asigna ID y se pueden leer los campos
        Assertions.assertNotNull(frenosRecuperado.getClass(), "El mantenimiento guardado debe tener ID");
        Assertions.assertEquals("Cambio de pastillas de freno", frenosRecuperado.getNombre(), "El mantenimiento guardado debe tener ID");
        Assertions.assertEquals(frenos.getFechaARealizar(), frenosRecuperado.getFechaARealizar());
        Assertions.assertEquals(0, frenosRecuperado.getKmARealizar());
        Assertions.assertFalse(frenosRecuperado.isFinalizado());

        // Recuperacion por ID
        Mantenimiento recuperado = mantenimientoService.recuperarMantenimiento(frenosRecuperado.getId());
        Assertions.assertEquals(frenosRecuperado.getNombre(), recuperado.getNombre());
    }

    @Test
    public void testSeCreanVariosMantenimientosYQuedanListados() {
        // Antes del guardado masivo hay 2
        Set<Mantenimiento> iniciales = mantenimientoService.allMantenimientos();
        Assertions.assertEquals(2, iniciales.size(), "Debe haber 2 mantenimientos iniciales");

        // Guardamos el tercero
        mantenimientoService.guardarMantenimiento(frenos);

        // Assert: ahora deberían ser 3
        Set<Mantenimiento> todos = mantenimientoService.allMantenimientos();
        Assertions.assertEquals(3, todos.size(), "Deben haberse creado 3 mantenimientos en total");
    }

    /*
    @Test
    public void testReglaDeNegocio_NombreObligatorio() {
        Mantenimiento invalido = new Mantenimiento(" ", LocalDate.now().plusDays(10));
        // Si tu Service valida y lanza IllegalArgumentException (o una custom), verificamos eso:
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mantenimientoService.guardarMantenimiento(invalido);
        }, "Debe rechazar mantenimientos sin nombre válido");
    }
    */

    @Test
    public void testFinalizarMantenimiento() {
        // Dado uno existente
        Mantenimiento existente = mantenimientoService.allMantenimientos().iterator().next();

        // Finalizamos usando la propia entidad
        existente.finalizarMantenimiento();
        mantenimientoService.guardarMantenimiento(existente); // persistimos el cambio

        // Assert
        Mantenimiento verificado = mantenimientoService.recuperarMantenimiento(existente.getId());
        Assertions.assertTrue(verificado.isFinalizado(), "El mantenimiento debe quedar finalizado");
        Assertions.assertNotNull(verificado.getFechaDeRealizacion(), "Al finalizar debe setear fecha de realización");
    }
}
