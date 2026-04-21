package com.tfg.bookmood.model;

import jakarta.persistence.*;

@Entity
@Table (name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_libro")
    private Long idLibro;
    @Column(  name = "titulo" ,nullable = false)
    private String titulo;
    @Column (name = "autor")
    private String autor;
    @Column (name = "sinopsis",columnDefinition = "TEXT")
    private String sinopsis;
    @Column (name = "portada_url",columnDefinition = "TEXT")
    private String portadaUrl;
    @Column ( name = "genero")
    private String genero;

    public Libro(){

    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public String getGenero() {
        return genero;
    }
}