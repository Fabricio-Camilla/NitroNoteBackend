package ar.edu.unq.spring.modelo.exception;

public class AnioIngresadoInvalidoException extends RuntimeException{

    @Override
    public String getMessage() {
        return "El anio ingresado tiene que ser mayor a 1990 o menor e igual a 2025";
    }
}
