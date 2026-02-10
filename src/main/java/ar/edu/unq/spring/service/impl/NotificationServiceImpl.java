package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.*;
import ar.edu.unq.spring.persistence.MantenimientoDAO;
import ar.edu.unq.spring.persistence.NotificacionDAO;
import ar.edu.unq.spring.persistence.NotificationLogDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.persistence.dto.NotificacionJPADTO;
import ar.edu.unq.spring.persistence.dto.NotificationLogJPADTO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.service.interfaces.EmailService;
import ar.edu.unq.spring.service.interfaces.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final MantenimientoDAO mantenimientoDAO;
    private final UsuarioDAO usuarioDAO;
    private final NotificationLogDAO notificationLogDAO;
    private final EmailService emailService;
    private final ExpoPushNotificationService expoPushService;
    private final NotificacionDAO notificacionDAO;

    public NotificationServiceImpl(MantenimientoDAO mantenimientoDAO,
                                   UsuarioDAO usuarioDAO,
                                   NotificationLogDAO notificationLogDAO,
                                   EmailService emailService, NotificacionDAO notificacionDAO) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.usuarioDAO = usuarioDAO;
        this.notificationLogDAO = notificationLogDAO;
        this.emailService = emailService;
        this.expoPushService = new ExpoPushNotificationService();
        this.notificacionDAO = notificacionDAO;
    }

    private String cargarTemplateEmail() {
        try (var in = getClass().getResourceAsStream("/MailMantenimiento.html")) {
            return org.springframework.util.StreamUtils.copyToString(in, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar el template del email", e);
        }
    }

    @Override
    @Transactional
    public void enviarRecordatoriosDelDia() {
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        System.out.println("Hoy: " + manana);
        // Buscar mantenimientos que vencen mañana (recordatorio con 1 día de anticipación)
        List<MantenimientoJPADTO> vencenManana = mantenimientoDAO.findMantenimientosQueVencenEnFecha(manana);

        for (MantenimientoJPADTO mant : vencenManana) {
            var vehiculo = mant.getVehiculo().aModelo();
            if (vehiculo == null) continue;

            Long usuarioId = vehiculo.getUsuario().getId();
            if (usuarioId == null) continue;

            UsuarioJPADTO usuario = usuarioDAO.findById(usuarioId).orElse(null);
            if (usuario == null) continue;
            var not = notificacionDAO.notificationesDeUsuario(usuarioId)
                    .stream()
                    .map(NotificacionJPADTO::aModelo).collect(Collectors.toList());

            var usuario1 = usuario.aModeloSimple();
            usuario1.setNotificationPreferences(not);

            boolean yaEnviado = notificationLogDAO
                    .findByMantenimientoIdAndDateAndType(mant.getId(), hoy, "REMINDER_1_DAY_BEFORE")
                    .isPresent();
            if (yaEnviado) continue;

            // Enviar email si está habilitado

            var recordatorios = usuario1.enviarEmail(mant.aModelo(vehiculo), vehiculo, hoy);

            if (!recordatorios.isEmpty()) {
                for (Recordatorio rec : recordatorios) {
                    switch (rec.tipo()) {
                        case TipoNotificacion.EMAIL ->
                                emailService.send(rec.to(), rec.asunto(), rec.cuerpo());
                        case TipoNotificacion.PUSH ->
                                expoPushService.sendPushNotification(
                                usuario1.getPushToken(),
                                rec.to(),
                                rec.cuerpo(),
                                rec.data()
                        );
                    }
                }


                // Registrar log
                NotificationLogJPADTO log = new NotificationLogJPADTO();
                log.setMantenimientoId(mant.getId());
                log.setDate(hoy);
                log.setType("REMINDER_1_DAY_BEFORE");
                notificationLogDAO.save(log);
            }
        }

    }

    @Override
    public void guardar(Notificacion notificacionEmail) {
        notificacionDAO.save(NotificacionJPADTO.desdeModelo(notificacionEmail));
    }

    @Override
    public void clearAll() {
        notificacionDAO.deleteAll();
    }
}