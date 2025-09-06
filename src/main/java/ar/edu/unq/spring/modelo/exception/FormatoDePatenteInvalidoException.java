package ar.edu.unq.spring.modelo.exception;

public class FormatoDePatenteInvalidoException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Formato de patente invalido";
    }
}
