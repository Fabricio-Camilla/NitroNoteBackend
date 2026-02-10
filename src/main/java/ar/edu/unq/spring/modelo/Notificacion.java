package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public abstract class Notificacion {

    private long id;

    protected TipoNotificacion tipo;

    protected boolean enable;

    protected long userID;

    public Notificacion(TipoNotificacion tipoNotificacion, boolean enabled, long userID) {
        this.enable = enabled;
        this.tipo = tipoNotificacion;
        this.userID = userID;
    }

    public abstract Recordatorio enviarRecordatorio(Usuario usuario, Vehiculo vehiculo,
                                                    Mantenimiento mant, LocalDate hoy);

    protected String safe(Object o) { return o == null ? "" : o.toString(); }
}
