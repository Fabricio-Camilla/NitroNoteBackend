package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.VehiculoDTO;
import ar.edu.unq.spring.controller.dto.VehiculoRequestDTO;
import ar.edu.unq.spring.controller.dto.VehiculoSimpleDTO;
import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.service.interfaces.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/vehiculo")
public class VehiculoControllerREST {

    private final VehiculoService vehiculoService;

    public VehiculoControllerREST(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping()
    public ResponseEntity<Vehiculo> crearVehiculo(@Validated @RequestBody VehiculoRequestDTO vehiculo) {
        Vehiculo vehiculoPers = this.vehiculoService.guardar(vehiculo.aModelo());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoPers);
    }

    @GetMapping("/{userId}")
    public List<VehiculoDTO> getVehiculoDeUserById(@PathVariable Long userId) {
        return this.vehiculoService.vehiculosByUserId(userId).stream()
                .map(VehiculoDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<VehiculoDTO> getAllVehiculos(){
        return this.vehiculoService.recuperarTodos().stream()
                .map(VehiculoDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<String > deleteVehicle(@PathVariable("patente") String patente) {

        this.vehiculoService.eliminar(patente);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PutMapping()
    public ResponseEntity<String > editVehicle(@RequestBody VehiculoRequestDTO vehiculo) {
        this.vehiculoService.actualizar(vehiculo.aModelo());

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @GetMapping("by-patente/{patente}")
    public VehiculoDTO getVehiculo(@PathVariable("patente") String patente){
        return VehiculoDTO.desdeModelo(this.vehiculoService.recuperar(patente));
    }
}
