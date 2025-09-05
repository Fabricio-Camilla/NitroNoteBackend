package ar.edu.unq.spring.modelo.exception;

public class MantenimientoYaAgregadoException extends RuntimeException{

    @Override
    public String getMessage() {
        return "El mantenimiento ya se encuentra agregado";
    }
}

