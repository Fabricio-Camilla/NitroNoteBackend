package ar.edu.unq.spring.service.scheduler;

import ar.edu.unq.spring.service.interfaces.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final NotificationService notificationService;

    public EmailScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Ejecuta todos los días a las 9:00 AM hora Argentina
    @Scheduled(cron = "00 50 21 * * *", zone = "America/Argentina/Buenos_Aires")
    public void enviarRecordatorios() {
        System.out.println("Ejecutando job diario de notificaciones...");
        notificationService.enviarRecordatoriosDelDia();
    }
}
