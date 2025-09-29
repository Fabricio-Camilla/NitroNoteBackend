package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.Vehiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Long usuarioID;

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
        dto.usuarioID = vehiculo.getUsuarioID();
        return dto;
    }

    public Vehiculo aModelo() {
        Vehiculo vehiculo = new Vehiculo(marca, modelo, patente, anio, kilometros, usuarioID);
        vehiculo.setId(id);
        vehiculo.setMantenimientos(mantenimientos.stream()
                .map(m -> m.aModelo(vehiculo))
                .toList());
        return vehiculo;
    }

    public Vehiculo aModelo(Usuario usuario){
        Vehiculo vehiculo = new Vehiculo(marca, modelo, patente, anio, kilometros, usuarioID);
        vehiculo.setId(id);
        vehiculo.setMantenimientos(mantenimientos.stream()
                .map(m -> m.aModelo(vehiculo))
                .toList());
        return vehiculo;
    }

}
