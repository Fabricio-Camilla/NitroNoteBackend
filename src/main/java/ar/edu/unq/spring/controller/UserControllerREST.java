package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.LoginRequestDTO;
import ar.edu.unq.spring.controller.dto.LoginResponseDTO;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserControllerREST {

    private UsuarioService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public UserControllerREST(UsuarioService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDTO){
        userService.register(registerDTO.aModelo());
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con exito");
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

    @GetMapping("/user")
    public ResponseEntity<Usuario> getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UsuarioJPADTO user = (UsuarioJPADTO) auth.getPrincipal();

        return ResponseEntity.ok(user.aModelo());
    }


}
