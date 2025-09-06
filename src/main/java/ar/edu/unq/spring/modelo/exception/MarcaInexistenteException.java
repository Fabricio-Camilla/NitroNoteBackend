package ar.edu.unq.spring.modelo.exception;

public class MarcaInexistenteException extends RuntimeException{

    @Override
    public String getMessage() {
        return "La marca no existe";
    }
}
