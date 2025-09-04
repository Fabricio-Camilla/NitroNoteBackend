package com.nitro_note.persistence.dao;

import com.nitro_note.modelo.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoDAO extends JpaRepository<Mantenimiento, Long>{}

