package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.VehiculoDTO;
import ar.edu.unq.spring.controller.dto.VehiculoRequestDTO;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/vehiculo")
public class VehiculoControllerREST {

    private VehiculoService vehiculoService;

    public VehiculoControllerREST(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping()
    public ResponseEntity<Vehiculo> crearVehiculo(@Validated @RequestBody VehiculoRequestDTO vehiculo) {
        Vehiculo vehiculoPers = this.vehiculoService.guardar(vehiculo.aModelo());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoPers);
    }

    @GetMapping()
    public List<VehiculoDTO> getAllVehiculos(){
        return this.vehiculoService.recuperarTodos().stream()
                .map(VehiculoDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> getVehiculoByPatente(@PathVariable String patente) {
        Vehiculo vehiculo = this.vehiculoService.recuperar(patente);
        return ResponseEntity.ok(VehiculoDTO.desdeModelo(vehiculo));
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<String > deleteVehicle(@PathVariable("patente") String patente) {

        this.vehiculoService.eliminar(patente);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PutMapping("/{patente}")
    public ResponseEntity<String > editVehicle(@PathVariable("patente") String patente) {

        Vehiculo vehicle = this.vehiculoService.recuperar(patente);
        this.vehiculoService.guardar(vehicle);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }


}
