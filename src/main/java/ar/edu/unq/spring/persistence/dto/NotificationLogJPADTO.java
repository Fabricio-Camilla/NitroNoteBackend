package ar.edu.unq.spring.persistence.dto;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.time.LocalDate;

@Entity @Table(name = "notification_log",
        uniqueConstraints = @UniqueConstraint(columnNames = {"mantenimiento_id","date","type"}))
@Getter @Setter
public class NotificationLogJPADTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mantenimiento_id", nullable = false)
    private Long mantenimientoId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 40)
    private String type; // e.g. "EMAIL_DUE_TODAY"
}
