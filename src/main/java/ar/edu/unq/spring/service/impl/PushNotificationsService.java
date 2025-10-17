package ar.edu.unq.spring.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PushNotificationsService {

    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";

    public void enviarNotificacion(String expoPushToken, String titulo, String cuerpo) {
        if (expoPushToken == null || !expoPushToken.startsWith("ExponentPushToken")) {
            System.out.println("Token inválido o inexistente, no se envía push");
            return;
        }

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("to", expoPushToken);
        mensaje.put("sound", "default");
        mensaje.put("title", titulo);
        mensaje.put("body", cuerpo);
        mensaje.put("data", Map.of("abrirApp", true));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(mensaje, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(EXPO_API_URL, request, String.class);
            System.out.println("Push enviada a " + expoPushToken + " -> " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Error enviando push a " + expoPushToken + ": " + e.getMessage());
        }
    }
}

