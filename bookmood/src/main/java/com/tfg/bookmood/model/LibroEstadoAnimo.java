package com.tfg.bookmood.model;

import jakarta.persistence.*;

@Entity
@Table (name = "libro_estado_animo", uniqueConstraints = @UniqueConstraint(columnNames = {"id_libro","id_estado_animo"}))

public class LibroEstadoAnimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro_estado")
    private Long idLibroEstado;

    @ManyToOne(optional = false)
    @JoinColumn (name = "id_libro",nullable = false)
    private Libro libro;

    @ManyToOne (optional = false)
    @JoinColumn( name = "id_estado_animo",nullable = false)
    private EstadoAnimo estadoAnimo;

    public LibroEstadoAnimo(){
    }

    public Long getIdLibroEstado() {
        return idLibroEstado;
    }

    public void setIdLibroEstado(Long idLibroEstado) {
        this.idLibroEstado = idLibroEstado;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public EstadoAnimo getEstadoAnimo() {
        return estadoAnimo;
    }

    public void setEstadoAnimo(EstadoAnimo estadoAnimo) {
        this.estadoAnimo = estadoAnimo;
    }
}