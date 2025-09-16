package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Vehiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity(name = "Vehiculo" )

public class VehiculoJPADTO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;

    @Column(nullable = false, unique = true)
    private String patente;

    @Min(0)
    private int kilometros;

    @Min(1990) @Max(2025)
    private int anio;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MantenimientoJPADTO> mantenimientos = new ArrayList<MantenimientoJPADTO>();

    public static VehiculoJPADTO desdeModelo(Vehiculo vehiculo){
        VehiculoJPADTO dto = new VehiculoJPADTO();
        dto.id = vehiculo.getId();
        dto.marca = vehiculo.getMarca();
        dto.modelo = vehiculo.getModelo();
        dto.patente = vehiculo.getPatente();
        dto.kilometros = vehiculo.getKilometros();
        dto.anio = vehiculo.getAnio();
        dto.mantenimientos = vehiculo.getMantenimientos()
                .stream()
                .map(MantenimientoJPADTO::desdeModeloSimple)
                .toList();
        return dto;
    }

    public Vehiculo aModelo() {
        Vehiculo vehiculo = new Vehiculo(marca, modelo, patente, anio, kilometros);
        vehiculo.setId(id);
        vehiculo.setMantenimientos(mantenimientos.stream().map(MantenimientoJPADTO::aModelo).collect(Collectors.toList()));
        return vehiculo;
    }

}
