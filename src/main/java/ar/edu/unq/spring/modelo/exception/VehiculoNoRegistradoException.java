package ar.edu.unq.spring.modelo.exception;

public class VehiculoNoRegistradoException extends RuntimeException {
    public VehiculoNoRegistradoException(String message) {
        super(message);
    }

    public VehiculoNoRegistradoException() {
        super("Vehículo no registrado");
    }
}
