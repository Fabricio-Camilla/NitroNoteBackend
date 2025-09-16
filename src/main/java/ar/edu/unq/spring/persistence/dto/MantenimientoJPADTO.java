package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Mantenimiento;
import ar.edu.unq.spring.modelo.Vehiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity(name = "Mantenimiento" )
public class MantenimientoJPADTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    private LocalDate fechaARealizar;
    private LocalDate fechaDeRealizacion;
    private int kmARealizar;
    private boolean finalizado = false;

    @ManyToOne
    @JoinColumn (nullable = false, name = "mantenimiento_id")
    private VehiculoJPADTO vehiculo;

    public static MantenimientoJPADTO desdeModelo(Mantenimiento mantenimiento) {
        MantenimientoJPADTO dto = new MantenimientoJPADTO();
        dto.id = mantenimiento.getId();
        dto.nombre = mantenimiento.getNombre();
        dto.fechaARealizar = mantenimiento.getFechaARealizar();
        dto.fechaDeRealizacion = mantenimiento.getFechaDeRealizacion();
        dto.kmARealizar = mantenimiento.getKmARealizar();
        dto.finalizado = mantenimiento.isFinalizado();
        dto.vehiculo = VehiculoJPADTO.desdeModelo(mantenimiento.getVehiculo());
        return dto;
    }

    public static MantenimientoJPADTO desdeModeloSimple(Mantenimiento mantenimiento) {
        MantenimientoJPADTO dto = new MantenimientoJPADTO();
        dto.id = mantenimiento.getId();
        dto.nombre = mantenimiento.getNombre();
        dto.fechaARealizar = mantenimiento.getFechaARealizar();
        dto.fechaDeRealizacion = mantenimiento.getFechaDeRealizacion();
        dto.kmARealizar = mantenimiento.getKmARealizar();
        dto.finalizado = mantenimiento.isFinalizado();
        return dto;
    }

    public Mantenimiento aModelo() {
        Mantenimiento mantenimiento = new Mantenimiento(nombre, fechaARealizar, kmARealizar);
        mantenimiento.setId(id);
        mantenimiento.setFechaDeRealizacion(fechaDeRealizacion);
        mantenimiento.setFinalizado(finalizado);
        if(vehiculo != null) {
            mantenimiento.setVehiculo(vehiculo.aModelo());
        }
        return mantenimiento;
    }
}
