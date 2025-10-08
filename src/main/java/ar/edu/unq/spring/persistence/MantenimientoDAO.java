package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.persistence.dto.MantenimientoJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MantenimientoDAO extends JpaRepository<MantenimientoJPADTO, Long> {

    @Query("SELECT m FROM Mantenimiento m WHERE m.vehiculo.usuarioID = :usuarioId")
    List<MantenimientoJPADTO> findByVehiculoUsuarioID(@Param("usuarioId") Long usuarioId);

    @Query("""
    SELECT m
    FROM Mantenimiento m
    JOIN FETCH m.vehiculo v
    WHERE m.fechaARealizar = :hoy
      AND m.finalizado = false
""")
    List<MantenimientoJPADTO> findMantenimientosQueVencenHoy(LocalDate hoy);
}
