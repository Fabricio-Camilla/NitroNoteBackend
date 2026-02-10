package ar.edu.unq.spring.modelo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NotificacionPush extends Notificacion{

    public NotificacionPush(boolean enabled, long userID) {
        super(TipoNotificacion.PUSH, enabled, userID);
    }
    @Override
    public Recordatorio enviarRecordatorio(Usuario usuario, Vehiculo vehiculo, Mantenimiento mant, LocalDate hoy) {
        if (!this.isEnable()) {
            return null;
        }
        try {
            String titulo = "Recordatorio de mantenimiento";
            String mensaje = String.format("%s - %s",
                    safe(mant.getNombre()),
                    mant.getFechaARealizar().toString());

            Map<String, Object> data = new HashMap<>();
            data.put("maintenanceId", mant.getId());
            data.put("type", "maintenance_reminder");
            data.put("redirectTo", "MantenimientoDetails");
            System.out.println("Token " + usuario.getPushToken());
            return new Recordatorio(titulo, null, mensaje, data, TipoNotificacion.PUSH);

        } catch (Exception e) {
            System.err.println("Error enviando push a " + usuario.getEmail() + ": " + e.getMessage());
        }
        return null;
    }
}
