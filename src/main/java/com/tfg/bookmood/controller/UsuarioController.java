package com.tfg.bookmood.controller;

import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.repository.UsuarioLibroRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/users")
public class UsuarioController {
    private final UsuarioLibroRepository usuarioLibroRepository;

    public  UsuarioController (UsuarioLibroRepository usuarioLibroRepository){
        this.usuarioLibroRepository = usuarioLibroRepository;
    }
    @GetMapping ("/{userId}/favorites")
     public List<Libro> getFavorites (@PathVariable Long userId){
        return usuarioLibroRepository.findByUsuarioIdUsuarioAndFavoritoTrue(userId)
                .stream()
                .map(ul->ul.getLibro())
                .toList();
    }
}
