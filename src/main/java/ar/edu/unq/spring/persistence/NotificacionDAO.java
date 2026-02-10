package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.TipoNotificacion;
import ar.edu.unq.spring.persistence.dto.NotificacionJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionDAO extends JpaRepository<NotificacionJPADTO, Long> {

    @Query("SELECT n FROM Notificacion n WHERE n.userID = :userID")
    List<NotificacionJPADTO> notificationesDeUsuario(@Param("userID") Long userID);


    @Query("SELECT n FROM Notificacion n WHERE n.tipo = :tipo AND n.userID = :userID")
    Optional<NotificacionJPADTO> findByTipo(@Param("tipo")TipoNotificacion tipoNotificacion, @Param("userID") Long userID);
}
