package com.vynv.proyecto.pruebadrawer;

import java.io.Serializable;

/**
 * Created by Kinstorm on 04/11/2015.
 */


/**
 * Created by Usuario on 07/04/2015.
 */

public class Elemento implements Serializable {
/* por ahora lo hacemos con las tres labels*/

    private String titulo;
    private String rutaimagen;
    private String fecha;
    private String descripcion;

    private String autor;

    public Elemento(){
        titulo = "";
        rutaimagen = "";
        fecha = "";
        descripcion = "";
        autor = "";
    }

    public Elemento(String tit, String ruta, String fec, String des, String aut){
        titulo = tit;
        rutaimagen = ruta;
        fecha = fec;
        descripcion = des;
        autor = aut;
    }

    public Elemento(String tit,String fec, String des, String aut){
        titulo = tit;
        rutaimagen = "";
        fecha = fec;
        descripcion = des;
        autor = aut;
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


    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() { return autor; }



    public static final int longitudTotal = 40 + 40 + 40 + 40 +40; //40 por cada string

    private int longitudCadenas = 50;





}

