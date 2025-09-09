package ar.edu.unq.spring.modelo.exception;

public class CantidadDeKilometrosMenorException extends RuntimeException{

    @Override
    public String getMessage() {
        return "No se puede actulizar los kilometros menor a lo que ya tenias";
    }
}

