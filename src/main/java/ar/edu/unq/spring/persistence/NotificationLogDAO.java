package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.persistence.dto.NotificationLogJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface NotificationLogDAO extends JpaRepository<NotificationLogJPADTO, Long> {
    Optional<NotificationLogJPADTO> findByMantenimientoIdAndDateAndType(Long mantenimientoId,
                                                                        LocalDate date,
                                                                        String type);
}