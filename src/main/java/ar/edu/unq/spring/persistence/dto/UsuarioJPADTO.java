package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@NoArgsConstructor
@Getter @Setter
public class UsuarioJPADTO implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    @Column(name = "email_notifications_enabled", nullable = false)
    private boolean emailNotificationsEnabled;

    @Column(name = "expo_push_token")
    private String expoPushToken;

    @Column(name = "push_enabled")
    private boolean pushEnabled;

    public static UsuarioJPADTO desdeModelo(Usuario usuario) {
        UsuarioJPADTO usuarioJPADTO = new UsuarioJPADTO();
        usuarioJPADTO.setNombre(usuario.getNombre());
        usuarioJPADTO.setEmail(usuario.getEmail());
        usuarioJPADTO.setPassword(usuario.getPassword());
        usuarioJPADTO.setRole(usuario.getRole());
        usuarioJPADTO.setId(usuario.getId());
        usuarioJPADTO.setEmailNotificationsEnabled(usuario.isEmailNotificationsEnabled());
        return usuarioJPADTO;
    }

    public Usuario aModelo(){
        Usuario usuario = new Usuario(nombre, email, password);
        usuario.setRole(role);
        usuario.setEmailNotificationsEnabled(emailNotificationsEnabled);
        usuario.setId(id);
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((new SimpleGrantedAuthority(role)));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
