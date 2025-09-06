package ar.edu.unq.spring.modelo.exception;

public class CantidadDeKilometrosInvalidaException extends RuntimeException{

    @Override
    public String getMessage() {
        return "La cantidad de kilometros tiene que ser mayor o igual a 0";
    }
}

