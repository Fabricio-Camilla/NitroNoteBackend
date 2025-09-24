package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.LoginRequestDTO;
import ar.edu.unq.spring.controller.dto.RegisterRequestDTO;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.service.impl.JwtService;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerREST {

    private SecurityContextRepository ctxRepo; //TODO: para setear el contexto de la sesion con el login
    private UsuarioService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public UserControllerREST(UsuarioService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.ctxRepo = new HttpSessionSecurityContextRepository();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDTO){
        userService.register(registerDTO.aModelo());
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con exito");
    }

    @PostMapping("/login")
    public UserDetails login (@RequestBody LoginRequestDTO loginDTO, HttpServletResponse response){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.email(),
                loginDTO.password()));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        response.setHeader("Authorization",  "Bearer " + jwtService.generateToken(userDetails));

        return userDetails;
    }


}
