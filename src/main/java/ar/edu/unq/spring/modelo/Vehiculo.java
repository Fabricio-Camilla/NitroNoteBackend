package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.CantidadDeKilometrosMenorException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Transient
    private List<Mantenimiento> mantenimientos;

    public Vehiculo(@NonNull String marca, @NonNull String modelo, @NonNull String patente, int anio, int kilometros) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.anio = anio;
        this.kilometros = kilometros;
        this.mantenimientos = new ArrayList<Mantenimiento>();
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
}
