package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.RegisterRequestDTO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerREST {

    private SecurityContextRepository ctxRepo; //TODO: para setear el contexto de la sesion con el login
    private UsuarioService userService;

    public UserControllerREST(UsuarioService userService) {
        this.userService = userService;
        this.ctxRepo = new HttpSessionSecurityContextRepository();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDTO){
        userService.register(registerDTO.aModelo());
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con exito");
    }




}
