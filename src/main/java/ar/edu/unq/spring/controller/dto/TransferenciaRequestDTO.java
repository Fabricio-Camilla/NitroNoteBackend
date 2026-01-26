package ar.edu.unq.spring.controller.dto;

import lombok.Getter;

public record TransferenciaRequestDTO(String patente, String emailDuenio, String emailNuevoDuenio) {
}
