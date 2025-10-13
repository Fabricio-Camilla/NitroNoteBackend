package ar.edu.unq.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NitroNoteApp {

    public static void main(String[] args) {
        SpringApplication.run(NitroNoteApp.class, args);
    }
}