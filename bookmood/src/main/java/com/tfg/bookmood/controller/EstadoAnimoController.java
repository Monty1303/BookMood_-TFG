package com.tfg.bookmood.controller;

import com.tfg.bookmood.model.EstadoAnimo;
import com.tfg.bookmood.repository.EstadoAnimoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/moods")

public class EstadoAnimoController {
    private final EstadoAnimoRepository estadoAnimoRepository;

    public EstadoAnimoController( EstadoAnimoRepository estadoAnimoRepository){
        this.estadoAnimoRepository = estadoAnimoRepository;
    }
    @GetMapping
    public List<EstadoAnimo> getAllMoods(){
        return estadoAnimoRepository.findAll();
    }
    @PostMapping
    public EstadoAnimo createMood(@RequestBody EstadoAnimo mood) {
        return estadoAnimoRepository.save(mood);
    }
}
