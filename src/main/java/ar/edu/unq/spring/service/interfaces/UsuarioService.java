package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;

public interface UsuarioService {
    void register(UsuarioJPADTO usuarioJPADTO);
}
