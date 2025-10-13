package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Usuario {

    private Long id;

    private String nombre;

    private String email;

    private String password;

    private List<Vehiculo> vehiculos;
    
    private String role;

    private boolean emailNotificationsEnabled;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.vehiculos = new ArrayList<Vehiculo>();
        this.emailNotificationsEnabled = false;
    }
}
