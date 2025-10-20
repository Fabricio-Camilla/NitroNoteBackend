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
import java.util.Map;
import java.util.HashMap;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final MantenimientoDAO mantenimientoDAO;
    private final UsuarioDAO usuarioDAO;
    private final NotificationLogDAO notificationLogDAO;
    private final EmailService emailService;
    private final ExpoPushNotificationService expoPushService;

    public NotificationServiceImpl(MantenimientoDAO mantenimientoDAO,
                                   UsuarioDAO usuarioDAO,
                                   NotificationLogDAO notificationLogDAO,
                                   EmailService emailService) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.usuarioDAO = usuarioDAO;
        this.notificationLogDAO = notificationLogDAO;
        this.emailService = emailService;
        this.expoPushService = new ExpoPushNotificationService();
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

        // Buscar mantenimientos que vencen mañana (recordatorio con 1 día de anticipación)
        List<MantenimientoJPADTO> vencenManana = mantenimientoDAO.findMantenimientosQueVencenEnFecha(manana);

        for (MantenimientoJPADTO mant : vencenManana) {
            var vehiculo = mant.getVehiculo();
            if (vehiculo == null) continue;

            Long usuarioId = vehiculo.getUsuarioID();
            if (usuarioId == null) continue;

            UsuarioJPADTO usuario = usuarioDAO.findById(usuarioId).orElse(null);
            if (usuario == null) continue;

            boolean yaEnviado = notificationLogDAO
                    .findByMantenimientoIdAndDateAndType(mant.getId(), hoy, "REMINDER_1_DAY_BEFORE")
                    .isPresent();
            if (yaEnviado) continue;

            // Enviar email si está habilitado
            if (usuario.isEmailNotificationsEnabled()) {
                enviarEmailRecordatorio(usuario, vehiculo, mant, hoy);
            }

            // Enviar push notification si está habilitado
            if (usuario.isPushNotificationsEnabled() && usuario.getPushToken() != null) {
                enviarPushRecordatorio(usuario, vehiculo, mant, hoy);
            }

            // Registrar log
            NotificationLogJPADTO log = new NotificationLogJPADTO();
            log.setMantenimientoId(mant.getId());
            log.setDate(hoy);
            log.setType("REMINDER_1_DAY_BEFORE");
            notificationLogDAO.save(log);
        }
    }

    private void enviarEmailRecordatorio(UsuarioJPADTO usuario, VehiculoJPADTO vehiculo,
                                         MantenimientoJPADTO mant, LocalDate hoy) {
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

            emailService.send(to, asunto, cuerpoHtml);
        } catch (Exception e) {
            System.err.println("Error enviando email a " + usuario.getEmail() + ": " + e.getMessage());
        }
    }

    private void enviarPushRecordatorio(UsuarioJPADTO usuario, VehiculoJPADTO vehiculo,
                                        MantenimientoJPADTO mant, LocalDate hoy) {
        try {
            String titulo = "Recordatorio de mantenimiento";
            String mensaje = String.format("%s - %s",
                    safe(mant.getNombre()),
                    mant.getFechaARealizar().toString());

            Map<String, Object> data = new HashMap<>();
            data.put("maintenanceId", mant.getId());
            data.put("type", "maintenance_reminder");
            data.put("redirectTo", "MantenimientoDetails");

            expoPushService.sendPushNotification(
                    usuario.getPushToken(),
                    titulo,
                    mensaje,
                    data
            );
        } catch (Exception e) {
            System.err.println("Error enviando push a " + usuario.getEmail() + ": " + e.getMessage());
        }
    }

    private String safe(Object o) { return o == null ? "" : o.toString(); }
}