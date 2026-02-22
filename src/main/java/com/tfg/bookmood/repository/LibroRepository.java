package com.tfg.bookmood.repository;

import com.tfg.bookmood.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("""
    SELECT lea.libro
    FROM LibroEstadoAnimo lea
    WHERE lea.estadoAnimo.idEstadoAnimo = :moodId
""")
    List<Libro> findRecommendedByMood(@Param("moodId") Long moodId);
}