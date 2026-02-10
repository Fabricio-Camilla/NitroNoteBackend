package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificacionEmail extends Notificacion {

   public NotificacionEmail(boolean enabled, long userID) {
       super(TipoNotificacion.EMAIL, enabled, userID);
   }
    @Override
    public Recordatorio enviarRecordatorio(Usuario usuario, Vehiculo vehiculo, Mantenimiento mant, LocalDate hoy) {
        if(!this.isEnable()) {
            return null;
        }
        try {
            String to = usuario.getEmail();
            String asunto = "Recordatorio de mantenimiento – " + safe(vehiculo.getModelo());
            String template = cargarTemplateEmail();
            String cuerpoHtml = template
                    .replace("{{nombreUsuario}}", safe(usuario.getNombre()))
                    .replace("{{nombreMantenimiento}}", safe(mant.getNombre()))
                    .replace("{{marcaVehiculo}}", safe(vehiculo.getMarca()))
                    .replace("{{modeloVehiculo}}", safe(vehiculo.getModelo()))
                    .replace("{{patenteVehiculo}}", safe(vehiculo.getPatente()))
                    .replace("{{fecha}}", String.valueOf(mant.getFechaARealizar()));
            return new Recordatorio(to ,asunto, cuerpoHtml, null, TipoNotificacion.EMAIL);
        } catch (Exception e) {
            System.err.println("Error enviando email a " + usuario.getEmail() + ": " + e.getMessage());
            return null;
        }
    }
    private String cargarTemplateEmail() {
        try (var in = getClass().getResourceAsStream("/MailMantenimiento.html")) {
            return org.springframework.util.StreamUtils.copyToString(in, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar el template del email", e);
        }
    }
}
