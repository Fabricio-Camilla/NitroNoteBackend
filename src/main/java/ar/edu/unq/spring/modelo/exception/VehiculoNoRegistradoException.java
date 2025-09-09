package ar.edu.unq.spring.modelo.exception;

public class VehiculoNoRegistradoException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Vehiculo no registrado";
    }
}
