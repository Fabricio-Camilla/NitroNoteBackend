package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.*;
import ar.edu.unq.spring.modelo.utils.MarcasModeloAutos;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@NoArgsConstructor @Getter @Setter
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

    private List<Mantenimiento> mantenimientos;

    public Vehiculo(@NonNull String marca, @NonNull String modelo, @NonNull String patente, int anio, int kilometros) {
        setMarca(marca);
        setModelo(modelo);
        setPatente(patente);
        this.anio = validarAnio(anio);
        this.kilometros = validarKilometros(kilometros);
        this.mantenimientos = new ArrayList<>();
    }

    public void setPatente(String patente) {
        String regexp = "^([A-Za-z]{2}[0-9]{3}[A-Za-z]{2}|[A-Za-z]{3}[0-9]{3})$";
        if(patente.matches(regexp)) {
            this.patente = patente;
        }else{
            throw new FormatoDePatenteInvalidoException();
        }
    }

    public void setMarca(String marca) {
        validarMarca(marca);
        this.marca = marca;
    }

    private void validarMarca(String marca) {
        if (MarcasModeloAutos.MARCAS.stream().noneMatch(m -> m.equals(marca))){
            throw new MarcaInexistenteException();
        }
    }

    public void setModelo(String modelo) {
        validarModelo(modelo);
        this.modelo = modelo;
    }

    private void validarModelo(String modelo) {
        if (MarcasModeloAutos.MODELOS.stream().noneMatch(m -> m.equals(modelo))) {
            throw new ModeloInexistenteException();
        }
    }

    private int validarKilometros(int kilometros) {
        if(kilometros < 0 ){
            throw new CantidadDeKilometrosInvalidaException();
        }
        return kilometros;
    }

    private int validarAnio(int anio) {
        if(anio < 1990 || anio > 2025 ){
            throw new AnioIngresadoInvalidoException();
        }
        return anio;
    }

    public void setKilometros(int kilometros) {
        this.validarKilometrosParaActualizar(kilometros);
        this.kilometros = kilometros;
    }

    private void validarKilometrosParaActualizar(int kilometros) {
        if(this.kilometros > kilometros) {
            throw new CantidadDeKilometrosMenorException();
        }
    }

    public void setAnio(int anio){
        this.anio = validarAnio(anio);
    }

}
