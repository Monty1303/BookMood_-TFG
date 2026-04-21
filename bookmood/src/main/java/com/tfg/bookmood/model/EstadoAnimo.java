package com.tfg.bookmood.model;

import jakarta.persistence.*;

@Entity
@Table (name = "estado_animo")

public class EstadoAnimo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_estado_animo")
    private Long idEstadoAnimo;

    @Column ( name = "nombre"   ,nullable = false)
    private String nombre;

    @Column ( name = "descripcion"  ,columnDefinition = "TEXT")
    private String descripcion;



    public EstadoAnimo(){

    }
    public Long getIdEstadoAnimo() {
        return idEstadoAnimo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdEstadoAnimo(Long idEstadoAnimo) {
        this.idEstadoAnimo = idEstadoAnimo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}