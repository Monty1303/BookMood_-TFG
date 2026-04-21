package com.tfg.bookmood.repository;

import com.tfg.bookmood.model.UsuarioLibro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {
    Optional<UsuarioLibro> findByUsuarioIdUsuarioAndLibroIdLibro(Long idUsuario, Long idLibro);

    List<UsuarioLibro> findByUsuarioIdUsuario (Long idUsuario);

    List<UsuarioLibro> findByUsuarioIdUsuarioAndFavoritoTrue(Long idUsuario);

    List<UsuarioLibro> findByUsuarioIdUsuarioAndLeidoTrue (Long idUsaurio);

    List<UsuarioLibro> findByUsuarioIdUsuarioAndQuieroLeerTrue (Long idUsuario);
}