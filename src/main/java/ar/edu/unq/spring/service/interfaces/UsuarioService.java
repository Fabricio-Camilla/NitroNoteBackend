package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Usuario;

public interface UsuarioService {
    Usuario register(Usuario usuario);
    Usuario recuperarUsuario(String email);
    void clearAll();
}
