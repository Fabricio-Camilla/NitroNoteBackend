package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Notificacion;
import ar.edu.unq.spring.modelo.NotificacionEmail;
import ar.edu.unq.spring.modelo.NotificacionPush;
import ar.edu.unq.spring.modelo.TipoNotificacion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Notificacion")
@Getter @Setter @NoArgsConstructor
public class NotificacionJPADTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TipoNotificacion tipo;

    private boolean enable;

    private long userID;

        public static NotificacionJPADTO desdeModelo(Notificacion notificacion) {
            NotificacionJPADTO dto = new NotificacionJPADTO();
            dto.setId(notificacion.getId());
            dto.setTipo(notificacion.getTipo());
            dto.setEnable(notificacion.isEnable());
            dto.setUserID(notificacion.getUserID());
            return dto;
        }

    public Notificacion aModelo() {
        Notificacion notificacion = switch (this.tipo) {
            case EMAIL -> new NotificacionEmail(this.enable, this.userID);
            case PUSH -> new NotificacionPush(this.enable, this.userID);
        };
        notificacion.setId(this.id);
        return notificacion;
    }
}
