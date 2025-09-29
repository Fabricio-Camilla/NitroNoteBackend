package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario register(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        UsuarioJPADTO dto =UsuarioJPADTO.desdeModelo(usuario);
        usuarioDAO.save(dto);
        usuario.setId(dto.getId());
        return usuario;
    }

    @Override
    public Usuario recuperarUsuario(String email) {
        return usuarioDAO.findByEmail(email).orElseThrow().aModelo(); //TODO: paja de hacer exception
    }

    @Override
    public void clearAll() {
        usuarioDAO.deleteAll();
    }

}
