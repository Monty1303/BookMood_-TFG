package com.tfg.bookmood.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table( name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column ( name = "id_usuario")
    private Long idUsuario;
    @Column( name ="nombre", nullable = false)
    private String nombre;
    @Column( name= "email",nullable = false, unique = true)
    private String email;
    @Column( name = "password"  ,nullable = false)
    private String password;
    @Column( name = "fecha_registro"  ,nullable = false)
    private LocalDate fechaRegistro;

    public Usuario() {
        this.fechaRegistro = LocalDate.now();
    }
    public Usuario(String nombre, String email, String password){
        this.nombre = nombre;
        this.email =  email;
        this.password = password;
        this.fechaRegistro = LocalDate.now();
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
}













