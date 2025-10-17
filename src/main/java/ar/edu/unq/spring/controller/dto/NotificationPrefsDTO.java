package ar.edu.unq.spring.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationPrefsDTO {
    private boolean emailEnabled;
    private boolean pushEnabled;
    private String expoPushToken;
}
