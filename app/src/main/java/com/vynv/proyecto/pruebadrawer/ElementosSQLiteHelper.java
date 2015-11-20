package com.vynv.proyecto.pruebadrawer;

/**
 * Created by Msi on 27/04/2015.
 */


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ElementosSQLiteHelper extends SQLiteOpenHelper {

    ElementoAdaptador adaptador;


    String dbName = "BDElementos";

    //Sentencia SQL para crear la tabla de +"dbName+"
    String sqlCreate = "CREATE TABLE "+dbName+" (codigo INT PRIMARY KEY, titulo varchar2(50), autor varchar2 (50), imagen varchar2(250), fecha  varchar2 (50), descripcion  varchar2 (250), duracion  varchar2 (50), visto int, favorito int, tipo  varchar2 (50), demografia  varchar2 (50), genero  varchar2 (50) );";
    String sqlDrop = "DROP TABLE IF EXISTS "+dbName+"";

    public ElementosSQLiteHelper(Context contexto, String nombre,
                                 CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + dbName + "");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }


    private boolean ExisteDatabase(SQLiteDatabase db) {

        SQLiteDatabase checkDB = db;
        Boolean devuelve = false;

        if (checkDB != null) {// si la base de datos existe.}
            devuelve=true;
        }
        return devuelve;
    }


    public ArrayList<Elemento> load() {

         SQLiteDatabase db = this.getReadableDatabase();
         ArrayList<Elemento> cargaelementossqlite = new ArrayList();

        if(ExisteDatabase(db)==false){
            db.execSQL(sqlCreate);
        }


        if (db != null) {

            Cursor c = db.rawQuery("SELECT * FROM "+dbName+"", null);

            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                     cargaelementossqlite.add(new Elemento(/*c.getString(0),*/c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7), c.getInt(8), c.getString(9), c.getString(10), c.getString(11)));
                } while(c.moveToNext());
            }
        }
        db.close();
        return cargaelementossqlite;
    }

    public ArrayList<Elemento> saveSincronizado(ArrayList<Elemento> al) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Elemento> cargaelementossqlite = new ArrayList();

        int Vistodb = 0;
        int Favoritodb = 0;

        if (db != null) {

            //db.execSQL(sqlDrop);
            //db.execSQL(sqlCreate);
            int existe=0;
            long codigo = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + dbName + ";", null);


            for (int i = 0; i < al.size(); i++) { //recorremos todos los datos para guardarlos
                //cogemos los datos que esten en esa posicion del listview
                Elemento ele = al.get(i);

                int iddb = (int)codigo+1;
                String Titulodb = ele.getTitulo();
                String Autordb = ele.getAutor();
                String Imagendb = ele.getRutaimagen();
                String Fechadb = ele.getFecha();
                String Descripciondb = ele.getDescripcion();
                String Duraciondb = ele.getDuracion();
               if( ele.getVisto()!=null){ Vistodb = ele.getVisto();}
                if( ele.getFavorito()!=null){ Favoritodb = ele.getFavorito();}
                String TipoElementodb = ele.getTipo();
                String Demografiadb = ele.getDemografia();
                String Generodb = ele.getGenero();




                //comprobamos si el elemento ya existe

                Cursor c = db.rawQuery("SELECT COUNT(*) FROM "+dbName+" WHERE titulo='"+Titulodb+"';", null);

                if (c.moveToFirst()) {//Recorremos el cursor hasta que no haya más registros
                    do {
                        existe= c.getInt(0);
                    } while(c.moveToNext());
                }

                if(existe>0){ //si ya existe el elemento introducido modificamos el antiguo valor
                    //actualizamos el campo
                    Log.d("guardado", "guardando, existe " + existe + Titulodb);
                    db.execSQL("UPDATE "+dbName+" set autor='"+Autordb+"', imagen ='"+Imagendb+"', fecha='"+Fechadb+"', descripcion='"+Descripciondb+"', duracion='"+Duraciondb+"', visto ='"+Vistodb+"', favorito ='"+Favoritodb+"', tipo='"+TipoElementodb+"', demografia='"+Demografiadb+"', genero='"+Generodb+"' WHERE titulo='"+Titulodb+"';");
                }
                else{ //si no existe el elemento lo introducimos
                    //insertamos
                    Log.d("guardado", "guardando, no existe" +iddb +  Titulodb + Autordb + Fechadb + Descripciondb + Duraciondb + TipoElementodb + Demografiadb + Generodb);
                    db.execSQL("INSERT INTO "+dbName+" (codigo, titulo, autor, imagen, fecha, descripcion, duracion, tipo, demografia, genero ) values (" + iddb + ", '" + Titulodb + "', '" + Autordb + "', '" + Imagendb + "', '" + Fechadb + "', '" + Descripciondb + "', '" + Duraciondb + "', '" + TipoElementodb + "', '" + Demografiadb + "', '" + Generodb +"');");
                }
            }
            //cargamos los datos
            Cursor d = db.rawQuery("SELECT * FROM "+dbName+"", null);


        }
        db.close();
        return cargaelementossqlite;
    }


    public int[] saveestado(ArrayList<Elemento> al) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Elemento> cargaelementossqlite = new ArrayList();
        int Vistodb = 0;
        int Favoritodb = 0;
        if (db != null) {

            //db.execSQL(sqlDrop);
            //db.execSQL(sqlCreate);
            int existe=0;

            for (int i = 0; i < al.size(); i++) { //recorremos todos los datos para guardarlos
                //cogemos los datos que esten en esa posicion del listview
                Elemento ele = al.get(i);

                int iddb = i;
                String Titulodb = ele.getTitulo();
                 Vistodb = ele.getVisto();
                 Favoritodb = ele.getFavorito();


                //comprobamos si el elemento ya existe

                Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + dbName + " WHERE titulo='" + Titulodb+"';", null);

                if (c.moveToFirst()) {//Recorremos el cursor hasta que no haya más registros
                    do {
                        existe= c.getInt(0);
                    } while(c.moveToNext());
                }

                if(existe>0){ //si ya existe el elemento introducido modificamos el antiguo valor
                    //actualizamos el campo
                    Log.d("guardado", "guardando, existe " + existe + Titulodb);
                    db.execSQL("UPDATE "+dbName+" set visto ='"+Vistodb+"', favorito ='"+Favoritodb+"' WHERE titulo='"+Titulodb+"';");
                }
            }
        }
        db.close();

        return new int[] {Vistodb, Favoritodb};
    }


    public void delete(String titulodb){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            // borramos el elemento de la base de datos
            db.execSQL("DELETE from "+dbName+" where titulo='"+titulodb+"';");
        }

        db.close();


    }


}