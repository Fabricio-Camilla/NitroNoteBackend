package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.NotificationPrefsDTO;
import ar.edu.unq.spring.modelo.Usuario;

public interface UsuarioService {
    Usuario register(Usuario usuario);
    Usuario recuperarUsuario(String email);
    Usuario actualizarUsuario(Usuario usuario);
    Usuario actualizarPreferenciasNotificacion(String email, NotificationPrefsDTO prefs);

    void clearAll();
}
