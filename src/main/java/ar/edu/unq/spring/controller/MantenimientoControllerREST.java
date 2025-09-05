package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MantenimientoDTO;
import ar.edu.unq.spring.service.interfaces.MantenimientoService;
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
        return mantenimientoService.guardarMantenimiento(mantenimiento.aModelo());
    }

    @GetMapping("/{id}")
    public MantenimientoDTO getMantenimiento(@PathVariable Long id) {
        return MantenimientoDTO.desdeModelo(mantenimientoService.recuperarMantenimiento(id));
    }

}
