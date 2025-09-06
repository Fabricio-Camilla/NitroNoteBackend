package ar.edu.unq.spring.modelo.exception;

public class ModeloInexistenteException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Modelo inexistente";
    }
}
