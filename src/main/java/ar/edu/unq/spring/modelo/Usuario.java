package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private List<Notificacion> notificationPreferences;
    private String pushToken;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.vehiculos = new ArrayList<Vehiculo>();
        this.notificationPreferences = new ArrayList<>();
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

    public List<Recordatorio> enviarEmail(Mantenimiento mant, Vehiculo vehiculo, LocalDate hoy) {
        List<Recordatorio> rec = new ArrayList<>();
        for (Notificacion notificacion : this.notificationPreferences) {
            var recordatorio = notificacion.enviarRecordatorio(this, vehiculo, mant, hoy);
            if (recordatorio != null){
                rec.add(recordatorio);
            }
        }
        return rec;
    }

    public void agregarNotificacion(Notificacion notificacion) {
        this.getNotificationPreferences().add(notificacion);
    }
}
