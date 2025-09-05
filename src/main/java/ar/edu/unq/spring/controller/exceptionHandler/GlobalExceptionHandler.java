package ar.edu.unq.spring.controller.exceptionHandler;


import ar.edu.unq.spring.controller.VehiculoControllerREST;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = VehiculoControllerREST.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BodyError> handleDuplcicateEntry(HttpServletRequest request ){
        BodyError body = new BodyError(HttpStatus.CONFLICT, "El vehiculo ya se encuentra registrado", request.getRequestURI());
        return new ResponseEntity<>( body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BodyError> handleNullPointer(HttpServletRequest request ){
        BodyError body = new BodyError(HttpStatus.CONFLICT, "No puede contener campos vacios", request.getRequestURI());
        return new ResponseEntity<>( body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BodyError> handleIlegalArgumentException(HttpServletRequest request ){
        BodyError body = new BodyError(HttpStatus.CONFLICT, "Los campos ingresados son invalidos", request.getRequestURI());
        return new ResponseEntity<>( body, HttpStatus.CONFLICT);
    }

}
