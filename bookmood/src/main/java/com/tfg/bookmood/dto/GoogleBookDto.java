package com.tfg.bookmood.dto;

public class GoogleBookDto {
    public String titulo;
    public String autor;
    public String descripcion;
    public String portadaUrl;

    public GoogleBookDto(){

    }
    public GoogleBookDto (String titulo, String autor, String descripcion, String portadaUrl){
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
        this.portadaUrl = portadaUrl;
    }
}
