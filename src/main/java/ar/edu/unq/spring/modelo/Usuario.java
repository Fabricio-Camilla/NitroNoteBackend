package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nombre;

    private String email;
    private String password;
    private List<Vehiculo> vehiculos;
    private String role;
    private boolean emailNotificationsEnabled;
    private boolean pushNotificationsEnabled;
    private String pushToken;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.vehiculos = new ArrayList<Vehiculo>();
        this.emailNotificationsEnabled = false;
        this.pushNotificationsEnabled = false;
        this.pushToken = null;
    }


    public void agregarVehiculo(Vehiculo vehiculo) {
        this.getVehiculos().add(vehiculo);
    }

    public void eliminarVehiculo(Vehiculo vehiculo) {
        this.getVehiculos().remove(vehiculo);
        vehiculo.setUsuario(null);
    }

    public void transferirVehiculoA(Vehiculo vehiculo, Usuario nuevoDuenio) {
        this.eliminarVehiculo(vehiculo);
        nuevoDuenio.agregarVehiculo(vehiculo);
        vehiculo.setUsuario(nuevoDuenio);
    }

    public boolean tieneAlVehiculo(String patente) {
       return this.getVehiculos().stream().anyMatch(vehiculo -> vehiculo.getPatente().equals(patente));
    }
}
