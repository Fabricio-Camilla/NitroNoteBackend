package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Notificacion;
import ar.edu.unq.spring.modelo.NotificacionEmail;

public interface NotificationService {
    void enviarRecordatoriosDelDia();


    void guardar(Notificacion notificacionEmail);

    void clearAll();
}
