package com.vynv.proyecto.pruebadrawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * Created by Msi on 07/06/2015.
 */
public class Visualizar extends Activity {

    private ImageView img;
    private String defecto="/mnt/sdcard/img/book.jpg";

    protected void onCreate(Bundle savedInstanceState) {
        //inicializamos

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar);

        //recogemos el intent juego
        Elemento elemento = (Elemento)getIntent().getSerializableExtra("vynv");
        Intent intent = getIntent();

        //recogemos los textview de la interfaz
        TextView lblTitulo = (TextView)findViewById(R.id.lblTitulo);
        img = (ImageView)findViewById(R.id.imagen);
        TextView lblAutor = (TextView)findViewById(R.id.lblAutor);
        TextView lblFecha = (TextView)findViewById(R.id.lblFecha);
        TextView lblDescripcion = (TextView)findViewById(R.id.lblDescripcion);
        TextView lblDuracion = (TextView)findViewById(R.id.lblDuracion);
        CheckBox checkvisto = (CheckBox)findViewById(R.id.checkvisto);
        CheckBox checkfav = (CheckBox)findViewById(R.id.checkfav);
        TextView lblTipo = (TextView)findViewById(R.id.lblTipo);
        TextView lblDemografia = (TextView)findViewById(R.id.lblDemografia);
        TextView lblGenero = (TextView)findViewById(R.id.lblGenero);



        String titulo =  intent.getStringExtra("titulo");
        String autor =  intent.getStringExtra("autor");
        String imagen =  intent.getStringExtra("imagen");
        Log.d("visualizar", imagen);
        if(imagen==null) {
            imagen = defecto;
        }
        String fecha = intent.getStringExtra("fecha");
        String descripcion = intent.getStringExtra("descripcion");
        String duracion = intent.getStringExtra("duracion");
        Integer visto = intent.getExtras().getInt("visto");
        Integer favorito = intent.getExtras().getInt("favorito");
        String tipoelemento = intent.getStringExtra("tipo");
        String demografia = intent.getStringExtra("demografia");
        String genero = intent.getStringExtra("genero");

        //mostramos los datos recogidos
        lblTitulo.setText(titulo);
        lblAutor.setText(autor);
        Bitmap image = BitmapFactory.decodeFile(imagen);
        img.setImageBitmap(image);
        lblFecha.setText(fecha);
        lblDescripcion.setText(descripcion);
        lblDuracion.setText(duracion);

        if(visto==1){checkvisto.setEnabled(true);}
        else{checkvisto.setEnabled(false);}

        if(favorito==1){checkfav.setEnabled(true);}
        else{checkfav.setEnabled(false);}

        lblTipo.setText(tipoelemento);
        lblDemografia.setText(demografia);
        lblGenero.setText(genero);


    }

}
