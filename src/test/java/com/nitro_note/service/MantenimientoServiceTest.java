package com.nitro_note.service;

import com.nitro_note.modelo.Mantenimiento;
import com.nitro_note.service.interfaces.MantenimientoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;

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

    /*@AfterEach
    public void clean() {
        // Si tu service tiene un método para limpiar, usalo. Si no, borrá uno a uno.
        // Ejemplo defensivo:
        for (Mantenimiento m : mantenimientoService.allMantenimientos()) {
            // Podrías tener mantener un método eliminar(id) si tu interfaz lo provee
            // mantenimientoService.eliminarMantenimiento(m.getId());
        }
    }*/

    @Test
    public void testCrearUnMantenimiento() {
        // Act: guardamos el tercero
        Long guardado = mantenimientoService.guardarMantenimiento(frenos);

        Mantenimiento mantenimientoFrenos = mantenimientoService.recuperarMantenimiento(guardado);
        // Assert: se asigna ID y se pueden leer los campos
        Assertions.assertNotNull(guardado.getClass(), "El mantenimiento guardado debe tener ID");
        Assertions.assertEquals("Cambio de pastillas de freno", guardado.getClass().getName(), "El mantenimiento guardado debe tener ID");
        Assertions.assertEquals(frenos.getFechaARealizar(), guardado.getFechaARealizar());
        Assertions.assertEquals(0, guardado.getKmARealizar());
        Assertions.assertFalse(guardado.isFinalizado());

        // Recuperación por ID
        Mantenimiento recuperado = mantenimientoService.recuperarMantenimiento(guardado.getClass());
        Assertions.assertEquals(guardado.getNombre(), recuperado.getNombre());
    }

    @Test
    public void testSeCreanVariosMantenimientosYQuedanListados() {
        // Arrange: antes del guardado masivo hay 2 (del @BeforeEach)
        Collection<Mantenimiento> iniciales = mantenimientoService.allMantenimientos();
        Assertions.assertEquals(2, iniciales.size(), "Debe haber 2 mantenimientos iniciales");

        // Act: guardamos el tercero
        mantenimientoService.guardarMantenimiento(frenos);

        // Assert: ahora deberían ser 3
        Collection<Mantenimiento> todos = mantenimientoService.allMantenimientos();
        Assertions.assertEquals(3, todos.size(), "Deben haberse creado 3 mantenimientos en total");
    }

    @Test
    public void testReglaDeNegocio_NombreObligatorio() {
        Mantenimiento invalido = new Mantenimiento("   ", LocalDate.now().plusDays(10));
        // Si tu Service valida y lanza IllegalArgumentException (o una custom), verificamos eso:
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mantenimientoService.guardarMantenimiento(invalido);
        }, "Debe rechazar mantenimientos sin nombre válido");
    }

    @Test
    public void testFinalizarMantenimiento() {
        // Dado uno existente
        Mantenimiento existente = mantenimientoService.allMantenimientos().iterator().next();
        Long id = existente.getId();

        // Act: finalizamos usando la propia entidad (tu modelo ya trae un método)
        existente.finalizarMantenimiento();
        mantenimientoService.guardarMantenimiento(existente); // persistimos el cambio

        // Assert
        Mantenimiento verificado = mantenimientoService.recuperarMantenimiento(id);
        Assertions.assertTrue(verificado.isFinalizado(), "El mantenimiento debe quedar finalizado");
        Assertions.assertNotNull(verificado.getFechaDeRealizacion(), "Al finalizar debe setear fecha de realización");
    }
}
