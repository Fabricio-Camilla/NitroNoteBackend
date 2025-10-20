package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.service.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    public EmailServiceImpl(JavaMailSender mailSender) { this.mailSender = mailSender; }

    @Override
    public void send(String to, String subject, String htmlBody) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML
            mailSender.send(msg);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo enviar email a " + to, e);
        }
    }
}
