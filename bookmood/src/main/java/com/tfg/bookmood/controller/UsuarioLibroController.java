package com.tfg.bookmood.controller;


import com.tfg.bookmood.dto.UsuarioLibroRequest;
import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.model.Usuario;
import com.tfg.bookmood.model.UsuarioLibro;
import com.tfg.bookmood.repository.LibroRepository;
import com.tfg.bookmood.repository.UsuarioLibroRepository;
import com.tfg.bookmood.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/usuarioLibro")
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

    public UsuarioLibro upsertUserBook(@RequestBody UsuarioLibroRequest req) {
        Usuario usuario = usuarioRepository.findById(req.idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(req.idLibro).orElseThrow(()-> new RuntimeException(" Libro no encontrado "));

        UsuarioLibro ul = usuarioLibroRepository.findByUsuarioIdUsuarioAndLibroIdLibro(req.idUsuario, req.idLibro).orElseGet(()->{
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
    @GetMapping("/{idUsuario}")

    public List<UsuarioLibro> getUserBooks (@PathVariable Long idUsuario){
        return usuarioLibroRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
