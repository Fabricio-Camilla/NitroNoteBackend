package ar.edu.unq.spring.modelo;

import java.util.Map;

public record Recordatorio (
        String to,
        String asunto,
        String cuerpo,
        Map<String, Object> data,
        TipoNotificacion tipo
) {
}
