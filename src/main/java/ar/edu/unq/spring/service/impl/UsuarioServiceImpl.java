package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.UserPrefsDTO;
import ar.edu.unq.spring.modelo.*;
import ar.edu.unq.spring.modelo.exception.VehiculoNoRegistradoException;
import ar.edu.unq.spring.persistence.NotificacionDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.persistence.VehiculoDAO;
import ar.edu.unq.spring.persistence.dto.NotificacionJPADTO;
import ar.edu.unq.spring.persistence.dto.NotificationLogJPADTO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;
    private final VehiculoDAO vehiculoDAO;
    private final NotificacionDAO notificacionDAO;

    public UsuarioServiceImpl(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder, VehiculoDAO vehiculoDAO, NotificacionDAO notificacionDAO) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
        this.vehiculoDAO = vehiculoDAO;
        this.notificacionDAO = notificacionDAO;
    }

    @Override
    public Usuario register(Usuario usuario) {
        if (usuarioDAO.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        UsuarioJPADTO dto = UsuarioJPADTO.desdeModelo(usuario);
        UsuarioJPADTO guardado = usuarioDAO.save(dto);
        return guardado.aModelo();
    }


    @Override
    public Usuario recuperarUsuario(String email) {
        return usuarioDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .aModelo();
    }

    @Override
    public Usuario actualizarUsuario(String emailAnetrior, UserPrefsDTO usuario) {
        Usuario existente = usuarioDAO.findByEmail(emailAnetrior)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
                .aModelo();

        if (!existente.getEmail().equals(usuario.email())) {
            usuarioDAO.findByEmail(usuario.email())
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("El email ya está en uso por otro usuario");
                    }
            );
        }

        NotificacionJPADTO noti =
                notificacionDAO.findByTipo(TipoNotificacion.EMAIL, existente.getId())
                                .orElseGet(() -> {
                                    NotificacionEmail nuevaNotificacion = new NotificacionEmail(
                                            usuario.emailEnabled(),
                                            existente.getId());
                                    return NotificacionJPADTO.desdeModelo(nuevaNotificacion);
                                    }
                                );

        NotificacionJPADTO notiPush =
                notificacionDAO.findByTipo(TipoNotificacion.PUSH, existente.getId())
                        .orElseGet(() -> {
                                    NotificacionPush nuevaNotificacion = new NotificacionPush(
                                            usuario.emailEnabled(),
                                            existente.getId());
                                    return NotificacionJPADTO.desdeModelo(nuevaNotificacion);
                                }
                        );

        existente.setEmail(usuario.email());
        existente.setNombre(usuario.nombre());
        existente.setPassword(passwordEncoder.encode(usuario.password()));
        existente.setPushToken(usuario.pushToken());
        existente.agregarNotificacion(noti.aModelo());
        existente.agregarNotificacion(notiPush.aModelo());

        noti.setEnable(usuario.emailEnabled());
        notiPush.setEnable(usuario.pushEnabled());

        notificacionDAO.save(noti);
        notificacionDAO.save(notiPush);
        usuarioDAO.save(UsuarioJPADTO.desdeModelo(existente));
        return existente;
    }

    @Override
    public Usuario actualizarPreferenciasNotificacion(String email, boolean emailEnabled,
                                                      boolean pushEnabled, String pushToken) {
//        Usuario usuario = recuperarUsuario(email);
//        usuario.setEmailNotificationsEnabled(emailEnabled);
//        usuario.setPushNotificationsEnabled(pushEnabled);
//        usuario.setPushToken(pushToken);
//        return actualizarUsuario(usuario);
    return null;
    }

//    @Override
//    public Usuario actualizarPreferenciasNotificacion(String email, boolean emailEnabled) {
//        Usuario usuario = recuperarUsuario(email);
//        usuario.setEmailNotificationsEnabled(emailEnabled);
//        return actualizarUsuario(usuario);
//    }

    @Override
    public void clearAll() {
        usuarioDAO.deleteAll();
    }

    @Override
    public Usuario findById(Long userId) {
        return usuarioDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .aModelo();
    }

    @Override
    public Usuario transferirVehiculo(String patente, String emailDueño, String emailNuevoDueño) {
        Usuario duenio = usuarioDAO.findByEmail(emailDueño)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .aModelo();
        Usuario nuevoDuenio = usuarioDAO.findByEmail(emailNuevoDueño)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .aModelo();

        Vehiculo vehiculo = vehiculoDAO.findByPatente(patente)
                .orElseThrow(VehiculoNoRegistradoException::new)
                .aModelo();


        duenio.transferirVehiculoA(vehiculo, nuevoDuenio);

        usuarioDAO.save(UsuarioJPADTO.desdeModeloBasico(duenio));
        usuarioDAO.save(UsuarioJPADTO.desdeModeloBasico(nuevoDuenio));
        return nuevoDuenio;
    }

}
