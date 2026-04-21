package com.tfg.bookmood.controller;

import com.tfg.bookmood.dto.GoogleBookDto;
import com.tfg.bookmood.service.GoogleBooksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class GoogleBooksController {
    private final GoogleBooksService googleBooksService;

    public GoogleBooksController (GoogleBooksService googleBooksService){
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/buscar")
    public List<GoogleBookDto> buscarLibros (@RequestParam String titulo){
        return googleBooksService.buscarLibrosPorTitulo(titulo);
    }

    @GetMapping("/importar")
    public String importarLibros(
            @RequestParam Long moodId,
            @RequestParam String titulo
    ){
        return  googleBooksService.importarLibrosPorMood(moodId,titulo);
    }

}
