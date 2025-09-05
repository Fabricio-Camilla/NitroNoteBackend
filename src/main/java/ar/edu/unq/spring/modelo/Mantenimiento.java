package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Mantenimiento {

    private Long id;
    private String nombre;
    private LocalDate fechaARealizar;
    private LocalDate fechaDeRealizacion;
    private int kmARealizar;
    private boolean finalizado = false;

    public Mantenimiento() {}

    public Mantenimiento(String nombre, LocalDate fechaARealizar) {
        this.nombre = nombre;
        this.fechaARealizar = fechaARealizar;
    }

    public Mantenimiento(String nombre, LocalDate fechaARealizar, int kmARealizar) {
        this.nombre = nombre;
        this.fechaARealizar = fechaARealizar;
        this.kmARealizar = kmARealizar;
    }

    public void finalizarMantenimiento(){
        this.finalizado = true;
        this.fechaDeRealizacion = LocalDate.now();
    }
}
