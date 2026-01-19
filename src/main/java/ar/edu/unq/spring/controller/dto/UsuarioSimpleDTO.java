package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;

public record UsuarioSimpleDTO (Long id, String nombre, String email){

    public static UsuarioSimpleDTO desdeModelo(UsuarioJPADTO user) {
        return new UsuarioSimpleDTO(user.getId(), user.getNombre(), user.getEmail());
    }

}
