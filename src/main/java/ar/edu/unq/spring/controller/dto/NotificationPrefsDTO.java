package ar.edu.unq.spring.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationPrefsDTO {
    private boolean emailEnabled;
    private boolean pushEnabled;
    private String pushToken;

    public boolean isEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }

    public boolean isPushEnabled() { return pushEnabled; }
    public void setPushEnabled(boolean pushEnabled) { this.pushEnabled = pushEnabled; }

    public String getPushToken() { return pushToken; }
    public void setPushToken(String pushToken) { this.pushToken = pushToken; }


}
