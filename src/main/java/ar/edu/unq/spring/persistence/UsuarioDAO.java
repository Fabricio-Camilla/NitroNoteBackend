package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.persistence.dto.UsuarioJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<UsuarioJPADTO, Long> {

    @Query(
            "SELECT u FROM Usuario u WHERE u.email = :username"
    )
    Optional<UsuarioJPADTO> findByEmail(@Param("username") String username);

}
