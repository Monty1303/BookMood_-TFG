package com.tfg.bookmood.controller;
import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.repository.LibroRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/books")
    public class LibroController {

        private final LibroRepository libroRepository;

        public LibroController(LibroRepository libroRepository) {
            this.libroRepository = libroRepository;
        }

        @GetMapping("/recommendations")
        public List<Libro> getRecommendations(@RequestParam Long moodId) {
            return libroRepository.findRecommendedByMood(moodId);
        }
    }

