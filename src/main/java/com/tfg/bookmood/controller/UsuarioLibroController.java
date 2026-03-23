package com.tfg.bookmood.controller;


import com.tfg.bookmood.dto.UserBookRequest;
import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.model.Usuario;
import com.tfg.bookmood.model.UsuarioLibro;
import com.tfg.bookmood.repository.LibroRepository;
import com.tfg.bookmood.repository.UsuarioLibroRepository;
import com.tfg.bookmood.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/user-books")
public class UsuarioLibroController {

    private final UsuarioLibroRepository usuarioLibroRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;


    public UsuarioLibroController(UsuarioLibroRepository usuarioLibroRepository, UsuarioRepository usuarioRepository, LibroRepository libroRepository) {
        this.usuarioLibroRepository = usuarioLibroRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;

    }

    @PostMapping

    public UsuarioLibro upsertUserBook(@RequestBody UserBookRequest req) {
        Usuario usuario = usuarioRepository.findById(req.userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(req.bookId).orElseThrow(()-> new RuntimeException(" Libro no encontrado "));

        UsuarioLibro ul = usuarioLibroRepository.findByUsuarioIdUsuarioAndLibroIdLibro(req.userId, req.bookId).orElseGet(()->{
            UsuarioLibro nuevo = new UsuarioLibro();
            nuevo.setUsuario(usuario);
            nuevo.setLibro(libro);
            return nuevo;
        });

        if (req.leido != null) ul.setLeido(req.leido);
        if (req.favorito != null) ul.setFavorito(req.favorito);
        if (req.quieroLeer != null) ul.setQuieroLeer(req.quieroLeer);

        return usuarioLibroRepository.save(ul);

    }
    @GetMapping("/{userId}")

    public List<UsuarioLibro> getUserBooks (@PathVariable Long userId){
        return usuarioLibroRepository.findByUsuarioIdUsuario(userId);
    }
}
