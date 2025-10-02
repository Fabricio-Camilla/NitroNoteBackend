package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MantenimientoDAO extends JpaRepository<MantenimientoJPADTO, Long> {
    List<MantenimientoJPADTO> findByVehiculoUsuarioID(Long usuarioID);
}
