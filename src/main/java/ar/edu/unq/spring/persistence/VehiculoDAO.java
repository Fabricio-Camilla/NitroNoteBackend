package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Vehiculo;
import ar.edu.unq.spring.persistence.dto.VehiculoJPADTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface VehiculoDAO extends JpaRepository<VehiculoJPADTO, Long> {

    @Query(
            "SELECT v FROM Vehiculo v WHERE v.patente = :patente "
    )
     Optional<VehiculoJPADTO> findByPatente(@Param("patente") String patente);


}
