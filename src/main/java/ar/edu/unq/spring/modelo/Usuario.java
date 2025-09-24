package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private String nombre;

    private String email;

    private String password;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
}
