package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface VehiculoDAO extends JpaRepository<Vehiculo, Long> {

    @Query(
            "SELECT v FROM Vehiculo v WHERE v.patente = :patente "
    )
     Optional<Vehiculo> findByPatente(@Param("patente") String patente);
}
