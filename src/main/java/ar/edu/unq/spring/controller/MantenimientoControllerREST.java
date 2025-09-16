package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MantenimientoDTO;
import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoControllerREST {
    private final MantenimientoService mantenimientoService;

    public MantenimientoControllerREST(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @PostMapping("/{vehiculoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mantenimiento createMantenimiento(@PathVariable Long vehiculoId,
                                             @RequestBody MantenimientoDTO mantenimiento){
        return mantenimientoService.crearMantenimiento(mantenimiento.aModelo(), vehiculoId);
    }


    @GetMapping("/{id}")
    public MantenimientoDTO getMantenimiento(@PathVariable("id") Long id) {
        return MantenimientoDTO.desdeModelo(mantenimientoService.recuperarMantenimiento(id));
    }


    @GetMapping("/all")
    public Set<MantenimientoDTO> getAllMantenimientos() {
        return mantenimientoService.allMantenimientos().stream()
                .map(MantenimientoDTO::desdeModelo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // UPDATE
    @PutMapping("/{id}")
    public MantenimientoDTO updateMantenimiento(@PathVariable("id") Long id,
                                                @RequestBody MantenimientoDTO mantenimientoDTO) {
        var modelo = mantenimientoDTO.aModelo();
        modelo.setId(id);

        var actualizado = mantenimientoService.actualizarMantenimiento(modelo);
        return MantenimientoDTO.desdeModelo(actualizado);
    }

    // PATCH (finalizar)
    @PatchMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizarMantenimiento(@PathVariable("id") Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id);
        mantenimiento.finalizarMantenimiento();
        mantenimientoService.actualizarMantenimiento(mantenimiento);
    }


    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMantenimiento(@PathVariable("id") Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id); // lanza si no existe
        mantenimientoService.deleteMantenimiento(mantenimiento);
    }


}
