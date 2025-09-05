package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor @Setter
@Getter
public class Mantenimiento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="vehiculo_id")
    private Vehiculo vehiculo;

    private String nombre;

    public Mantenimiento(String nombre) {
        this.nombre = nombre;
    }
}
