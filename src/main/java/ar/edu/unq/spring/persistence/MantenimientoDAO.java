package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoDAO extends JpaRepository<MantenimientoJPADTO, Long> {
}
