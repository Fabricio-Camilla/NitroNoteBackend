package com.nitro_note.controller;


import com.nitro_note.controller.dto.MantenimientoDTO;
import com.nitro_note.service.interfaces.MantenimientoService;
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
    public MantenimientoDTO getMantenimiento(@PathVariable Long id) {
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
    public MantenimientoDTO update(@PathVariable Long id, @RequestBody MantenimientoDTO mantenimiento) {
        var modelo = mantenimiento.aModelo();
        modelo.setId(id);
        mantenimientoService.guardarMantenimiento(modelo);
        return MantenimientoDTO.desdeModelo(mantenimientoService.recuperarMantenimiento(id));
    }

    // PATCH acción de negocio (finalizar)
    @PatchMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id);
        mantenimiento.finalizarMantenimiento();
        mantenimientoService.guardarMantenimiento(mantenimiento);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var mantenimiento = mantenimientoService.recuperarMantenimiento(id); // lanza si no existe
        mantenimientoService.deleteMantenimiento(mantenimiento);
    }


}
