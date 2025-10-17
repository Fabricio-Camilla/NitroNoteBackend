package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.LoginRequestDTO;
import ar.edu.unq.spring.controller.dto.LoginResponseDTO;
import ar.edu.unq.spring.controller.dto.NotificationPrefsDTO;
import ar.edu.unq.spring.controller.dto.RegisterRequestDTO;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import ar.edu.unq.spring.service.impl.JwtService;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:8081")
public class UserControllerREST {

    private final UsuarioService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserControllerREST(UsuarioService userService, JwtService jwtService,
                              AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDTO) {
        try {
            userService.register(registerDTO.aModelo());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
    }

    @PostMapping("/login")
    public LoginResponseDTO login (@RequestBody LoginRequestDTO loginDTO, HttpServletResponse response){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.email(),
                loginDTO.password()));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String  token = jwtService.generateToken(userDetails);
        response.setHeader("Authorization",  "Bearer " + token);

        return LoginResponseDTO.desdeModelo(token, userDetails);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Sesión cerrada con éxito");
    }

    @GetMapping("/user")
    public ResponseEntity<Usuario> getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UsuarioJPADTO user = (UsuarioJPADTO) auth.getPrincipal();

        return ResponseEntity.ok(user.aModelo());
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(
            Authentication authentication,
            @RequestBody Usuario usuarioRequest) {
        try {
            if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isBlank()) {
                if (usuarioRequest.getPassword().length() < 8) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("La contraseña debe tener más de 8 caracteres");
                }
            }

            String currentEmail = authentication.getName();
            Usuario usuario = userService.recuperarUsuario(currentEmail);

            usuario.setNombre(usuarioRequest.getNombre());
            usuario.setEmail(usuarioRequest.getEmail());

            if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isBlank()) {
                usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
            }

            Usuario actualizado = userService.actualizarUsuario(usuario);
            actualizado.setPassword(null);

            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("El email ya se encuentra registrado")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El email ya se encuentra registrado");
            }
            throw e;
        }
    }

    @PatchMapping("/user/notification-preferences")
    public ResponseEntity<?> updateNotificationEmailPreferences(
            Authentication authentication,
            @RequestBody NotificationPrefsDTO prefs) {

        String email = authentication.getName();

        Usuario actualizado = userService.actualizarPreferenciasNotificacion(
                email,
                prefs.isEmailEnabled()
        );

        return ResponseEntity.ok(actualizado);
    }
}
