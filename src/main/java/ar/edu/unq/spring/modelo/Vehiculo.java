package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.CantidadDeKilometrosMenorException;
import ar.edu.unq.spring.modelo.exception.MantenimientoYaAgregadoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity @NoArgsConstructor @Getter @Setter
public class Vehiculo {

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

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Mantenimiento> mantenimientos;

    public Vehiculo(@NonNull String marca, @NonNull String modelo, @NonNull String patente, int anio, int kilometros) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.anio = anio;
        this.kilometros = kilometros;
        this.mantenimientos = new HashSet<Mantenimiento>();
    }

    public void actualizarKilometros(int kilometros) {
        this.validarKilometrosParaActualizar(kilometros);
        this.kilometros = kilometros;
    }

    private void validarKilometrosParaActualizar(int kilometros) {
        if(this.kilometros > kilometros) {
            throw new CantidadDeKilometrosMenorException();
        }
    }

    public void guardarMantenimiento(Mantenimiento mantenimiento) {
       // this.validarManteniemientoParaAgregar(mantenimiento);
        this.mantenimientos.add(mantenimiento);
        mantenimiento.setVehiculo(this);
    }

    private void validarManteniemientoParaAgregar(Mantenimiento mantenimiento) {
        if(this.tieneTipoDeMantenimiento(mantenimiento)){
            throw new MantenimientoYaAgregadoException();
        }
    }

    private boolean tieneTipoDeMantenimiento(Mantenimiento mantenimiento) {
        return this.mantenimientos.stream().anyMatch(m -> mantenimiento.getTipo() == m.getTipo());
        //es en memoria?????+
    }
}
