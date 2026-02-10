package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.UserPrefsDTO;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;

public interface UsuarioService {
    Usuario register(Usuario usuario);
    Usuario recuperarUsuario(String email);
    Usuario actualizarUsuario(String emailAnetrior, UserPrefsDTO userPrefs);
    Usuario actualizarPreferenciasNotificacion(String email, boolean emailEnabled,
                                               boolean pushEnabled, String pushToken);
    void clearAll();
    Usuario findById(Long userId);
    Usuario transferirVehiculo(String patente, String emailDueño, String emailNuevoDueño);
}
