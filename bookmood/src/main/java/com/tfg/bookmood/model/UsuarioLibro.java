package com.tfg.bookmood.model;

import jakarta.persistence.*;



@Entity
@Table ( name = "usuario_libro", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario","id_libro"}))

public class UsuarioLibro {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario_libro")
    private Long idUsuarioLibro;

    @ManyToOne (optional = false)
    @JoinColumn (name = "id_usuario",nullable = false)
    private Usuario usuario;

    @ManyToOne (optional = false)
    @JoinColumn (name = "id_libro",nullable = false)
    private Libro libro;

    @Column (nullable = false)
    private Boolean leido = false;

    @Column (nullable = false)
    private Boolean favorito = false;

    @Column ( name = "quiero_leer",nullable = false)
    private Boolean quieroLeer = false;

    public UsuarioLibro(){

    }

    public Long getIdUsuarioLibro() {
        return idUsuarioLibro;
    }

    public void setIdUsuarioLibro(Long idUsuarioLibro) {
        this.idUsuarioLibro = idUsuarioLibro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Boolean getQuieroLeer() {
        return quieroLeer;
    }

    public void setQuieroLeer(Boolean quieroLeer) {
        this.quieroLeer = quieroLeer;
    }
}
