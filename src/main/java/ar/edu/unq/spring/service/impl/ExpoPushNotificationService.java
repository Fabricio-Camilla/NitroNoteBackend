package ar.edu.unq.spring.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ExpoPushNotificationService {

    private final RestTemplate restTemplate;
    private final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    public ExpoPushNotificationService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendPushNotification(String pushToken, String title, String body, Map<String, Object> data) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("to", pushToken);
            message.put("title", title);
            message.put("body", body);
            message.put("sound", "default");
            message.put("data", data);
            message.put("channelId", "default");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(message, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    EXPO_PUSH_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                System.err.println("Error enviando push: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Exception enviando push: " + e.getMessage());
        }
    }
}
