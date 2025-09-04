package com.nitro_note.controller;


import com.nitro_note.controller.dto.MantenimientoDTO;
import com.nitro_note.modelo.Mantenimiento;
import com.nitro_note.service.interfaces.MantenimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return mantenimientoService.guardarMantenimiento(mantenimiento);
    }
}
