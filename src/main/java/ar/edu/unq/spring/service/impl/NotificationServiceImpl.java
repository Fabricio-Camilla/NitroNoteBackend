package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.persistence.MantenimientoDAO;
import ar.edu.unq.spring.persistence.NotificationLogDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.persistence.dto.NotificationLogJPADTO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import ar.edu.unq.spring.service.interfaces.EmailService;
import ar.edu.unq.spring.service.interfaces.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.util.List;


@Service
public class NotificationServiceImpl implements NotificationService {

    private final MantenimientoDAO mantenimientoDAO;
    private final UsuarioDAO usuarioDAO;
    private final NotificationLogDAO notificationLogDAO;
    private final EmailService emailService;
    private final PushNotificationsService pushNotificationService;

    public NotificationServiceImpl(MantenimientoDAO mantenimientoDAO,
                                   UsuarioDAO usuarioDAO,
                                   NotificationLogDAO notificationLogDAO,
                                   EmailService emailService,
                                   PushNotificationsService pushNotificationService) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.usuarioDAO = usuarioDAO;
        this.notificationLogDAO = notificationLogDAO;
        this.emailService = emailService;
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    @Transactional
    public void enviarRecordatoriosDelDia() {
        LocalDate hoy = LocalDate.now();
        List<MantenimientoJPADTO> vencenHoy = mantenimientoDAO.findMantenimientosQueVencenHoy(hoy);

        for (MantenimientoJPADTO mant : vencenHoy) {
            var vehiculo = mant.getVehiculo();
            if (vehiculo == null) continue;

            Long usuarioId = vehiculo.getUsuarioID();
            if (usuarioId == null) continue;

            UsuarioJPADTO usuario = usuarioDAO.findById(usuarioId).orElse(null);
            if (usuario == null) continue;

            // Evitar duplicados
            boolean yaEnviado = notificationLogDAO
                    .findByMantenimientoIdAndDateAndType(mant.getId(), hoy, "PUSH_DUE_TODAY")
                    .isPresent();
            if (yaEnviado) continue;

            //notif push
            if (usuario.isPushEnabled() && usuario.getExpoPushToken() != null) {
                String titulo = "Recordatorio de mantenimiento";
                String cuerpo = mant.getNombre() + " - Fecha: " + mant.getFechaARealizar();

                pushNotificationService.enviarNotificacion(
                        usuario.getExpoPushToken(), titulo, cuerpo
                );

                NotificationLogJPADTO log = new NotificationLogJPADTO();
                log.setMantenimientoId(mant.getId());
                log.setDate(hoy);
                log.setType("PUSH_DUE_TODAY");
                notificationLogDAO.save(log);
            }

            if (usuario.isEmailNotificationsEnabled()) {
                enviarEmail(usuario, vehiculo, mant, hoy);
            }
        }
    }

    private String cargarTemplateEmail() {
        try (var in = getClass().getResourceAsStream("/MailMantenimiento.html")) {
            return org.springframework.util.StreamUtils.copyToString(in, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar el template del email", e);
        }
    }

    private void enviarEmail(UsuarioJPADTO usuario, VehiculoJPADTO vehiculo, MantenimientoJPADTO mant, LocalDate hoy) {
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

        try {
            emailService.send(to, asunto, cuerpoHtml);
            NotificationLogJPADTO log = new NotificationLogJPADTO();
            log.setMantenimientoId(mant.getId());
            log.setDate(hoy);
            log.setType("EMAIL_DUE_TODAY");
            notificationLogDAO.save(log);
        } catch (Exception e) {
            System.err.println("Error enviando email a " + to + ": " + e.getMessage());
        }
    }

    private String safe(Object o) { return o == null ? "" : o.toString(); }
}
