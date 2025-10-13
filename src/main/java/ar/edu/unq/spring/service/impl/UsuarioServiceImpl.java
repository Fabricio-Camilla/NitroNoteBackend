package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Usuario actualizarUsuario(Usuario usuario) {
        UsuarioJPADTO existente = usuarioDAO.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!existente.getEmail().equals(usuario.getEmail())) {
            Optional<UsuarioJPADTO> otro = usuarioDAO.findByEmail(usuario.getEmail());
            if (otro.isPresent() && !otro.get().getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("El email ya se encuentra registrado");
            }
            existente.setEmail(usuario.getEmail());
        }

        existente.setNombre(usuario.getNombre());
        existente.setRole(usuario.getRole());
        existente.setPassword(usuario.getPassword());
        existente.setEmailNotificationsEnabled(usuario.isEmailNotificationsEnabled());

        UsuarioJPADTO guardado = usuarioDAO.save(existente);
        return guardado.aModelo();
    }


    @Override
    public Usuario actualizarPreferenciasNotificacion(String email, boolean emailEnabled) {
        Usuario usuario = recuperarUsuario(email);
        usuario.setEmailNotificationsEnabled(emailEnabled);
        return actualizarUsuario(usuario);
    }

    @Override
    public void clearAll() {
        usuarioDAO.deleteAll();
    }

}
