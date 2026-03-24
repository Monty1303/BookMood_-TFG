package com.tfg.bookmood.controller;

import com.tfg.bookmood.dto.UsuarioLoginRequest;
import com.tfg.bookmood.dto.UsuarioRegisterRequest;
import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.model.Usuario;
import com.tfg.bookmood.repository.UsuarioLibroRepository;
import com.tfg.bookmood.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioLibroRepository usuarioLibroRepository;

    public  UsuarioController (UsuarioLibroRepository usuarioLibroRepository, UsuarioRepository usuarioRepository){
        this.usuarioLibroRepository = usuarioLibroRepository;
        this.usuarioRepository = usuarioRepository;
    }
    @PostMapping ("/register")

    public Usuario register (@RequestBody UsuarioRegisterRequest request){
        Usuario usuario = new Usuario();
        usuario.setNombre (request.nombre);
        usuario.setEmail (request.email);
        usuario.setPassword (request.password);

        return  usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody UsuarioLoginRequest request){
        Usuario usuario = usuarioRepository.findByEmail(request.email)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        if (!usuario.getPassword().equals(request.password)){
            throw new RuntimeException("Contraseaña incorrecta");
        }
        return usuario;

    }



    @GetMapping("/{id}")

    public Usuario getUsuarioById (@PathVariable Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Usuario no registrado "));
    }

    @GetMapping ("/{id}/favoritos")
     public List<Libro> getFavorito (@PathVariable Long  id){
        return usuarioLibroRepository.findByUsuarioIdUsuarioAndFavoritoTrue(id)
                .stream()
                .map(usuarioLibro -> usuarioLibro.getLibro())
                .collect(Collectors.toList());
    }
    @GetMapping ("/{id}/leidos")
    public List<Libro> getLeido (@PathVariable Long id){
        return  usuarioLibroRepository.findByUsuarioIdUsuarioAndLeidoTrue(id)
                .stream()
                .map (usuarioLibro -> usuarioLibro.getLibro())
                .collect(Collectors.toList());
    }
    @GetMapping ("{id}/quieroLeer")
    public  List<Libro> getQuieroLeer (@PathVariable Long id){
        return  usuarioLibroRepository.findByUsuarioIdUsuarioAndQuieroLeerTrue(id)
                .stream()
                .map (usuarioLibro -> usuarioLibro.getLibro())
                .collect(Collectors.toList());
    }
}
