package ar.edu.unq.spring.modelo.exception;

public class UsuarioNoEncontradoException  extends RuntimeException{

    @Override
    public String getMessage() {
        return "Usuario ya registrado";
    }
}
