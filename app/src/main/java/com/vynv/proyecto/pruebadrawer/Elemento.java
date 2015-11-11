package com.vynv.proyecto.pruebadrawer;

import java.io.Serializable;

/**
 * Created by Kinstorm on 04/11/2015.
 */

public class Elemento implements Serializable {
/* por ahora lo hacemos con las tres labels*/

    private String titulo;
    private String autor;
    private String rutaimagen;
    private String fecha;
    private String descripcion;

    public Elemento(String tit, String aut){
        titulo = tit;
        autor = aut;
    }

    public Elemento(){
        titulo = "";
        autor = "";
        rutaimagen = "";
        fecha = "";
        descripcion = "";
    }


    public Elemento(String tit, String aut, String ruta, String fec, String des){
        titulo = tit;
        autor = aut;
        rutaimagen = ruta;
        fecha = fec;
        descripcion = des;
    }

    public Elemento(String tit, String aut, String fec, String des){
        titulo = tit;
        autor = aut;
        rutaimagen = "";
        fecha = fec;
        descripcion = des;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }

    public void setRutaimagen(String rutaimagen) {
        this.rutaimagen = rutaimagen;
    }
    public String getRutaimagen(){
        return rutaimagen;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() { return autor; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getFecha(){
        return fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion(){
        return descripcion;
    }

    public static final int longitudTotal = 40 + 40 + 40 + 40 +40; //40 por cada string

    private int longitudCadenas = 50;

}

