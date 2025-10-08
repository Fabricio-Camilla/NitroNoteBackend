package ar.edu.unq.spring.service.interfaces;

public interface EmailService {
    void send(String to, String subject, String htmlBody);
}