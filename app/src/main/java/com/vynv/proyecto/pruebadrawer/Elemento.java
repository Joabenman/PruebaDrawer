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
    private String tipo;
    private String duracion;
    private Integer visto;
    private Integer favorito;
    private String demografia;
    private String genero;


    //base 11 valores con imagen
    public Elemento(String tit, String aut, String img, String fec, String des, String dura, Integer vis, Integer fav, String tip, String dem, String gen){
        titulo = tit;
        autor = aut;
        rutaimagen = img;
        fecha = fec;
        descripcion = des;
        duracion = dura;
        visto =vis;
        favorito =fav;
        tipo =tip;
        demografia=dem;
        genero=gen;
    }

    //base 10 valores sin imagen
    public Elemento(String tit, String aut, String fec, String des, String dura, Integer vis, Integer fav, String tip, String dem, String gen){
        titulo = tit;
        autor = aut;
        rutaimagen = "";
        fecha = fec;
        descripcion = des;
        duracion = dura;
        visto =vis;
        favorito =fav;
        tipo =tip;
        demografia=dem;
        genero=gen;
    }


    //sin botones 9 valores con imagen
    public Elemento(String tit, String aut, String img, String fec, String des, String dura, String tip, String dem, String gen){
        titulo = tit;
        autor = aut;
        rutaimagen = img;
        fecha = fec;
        descripcion = des;
        duracion = dura;
        tipo =tip;
        demografia=dem;
        genero=gen;
    }

    //sin botones 8 valores sin imagen
    public Elemento(String tit, String aut, String fec, String des, String tip, String dura, String dem, String gen){
        titulo = tit;
        autor = aut;
        rutaimagen = "";
        fecha = fec;
        descripcion = des;
        duracion = dura;
        tipo =tip;
        demografia=dem;
        genero=gen;
    }



    //entradarapida 3 valores con imagen
    public Elemento(String tit,  String aut, String img){
        titulo = tit;
        autor = aut;
        rutaimagen = "img";
    }


    //entradarapida 2 valores sin imagen
    public Elemento(String tit, String aut){
        titulo = tit;
        autor = aut;
        rutaimagen = "";
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



    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getDuracion(){
        return duracion;
    }

    public void setVisto(Integer visto) {
        this.visto = visto;
    }
    public Integer getVisto(){
        return visto;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
    }
    public Integer getFavorito(){
        return favorito;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo(){
        return tipo;
    }

    public void setDemografia(String descripcion) {
        this.demografia = demografia;
    }
    public String getDemografia(){
        return demografia;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getGenero(){
        return genero;
    }


}

