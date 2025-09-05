package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MantenimientoDTO;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoControllerREST {
    private final MantenimientoService mantenimientoService;

    public MantenimientoControllerREST(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createMantenimiento(@RequestBody MantenimientoDTO mantenimiento){
        return mantenimientoService.guardarMantenimiento(mantenimiento.aModelo());
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
    public MantenimientoDTO updateMantenimiento(@PathVariable("id") Long id, @RequestBody MantenimientoDTO mantenimiento) {
        var modelo = mantenimiento.aModelo();
        modelo.setId(id);
        mantenimientoService.guardarMantenimiento(modelo);
        return MantenimientoDTO.desdeModelo(mantenimientoService.recuperarMantenimiento(id));
    }

    // PATCH (finalizar)
    @PatchMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizarMantenimiento(@PathVariable("id") Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id);
        mantenimiento.finalizarMantenimiento();
        mantenimientoService.guardarMantenimiento(mantenimiento);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMantenimiento(@PathVariable("id") Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id); // lanza si no existe
        mantenimientoService.deleteMantenimiento(mantenimiento);
    }


}
