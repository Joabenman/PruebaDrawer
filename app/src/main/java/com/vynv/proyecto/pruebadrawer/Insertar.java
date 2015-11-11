package com.vynv.proyecto.pruebadrawer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;


public class Insertar extends Activity implements View.OnClickListener {

    ElementoAdaptador adaptador;

    ArrayList<Elemento> datoslibros;
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    private ImageView img;

    //inicializamos la interfaz
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicializamos interfaz
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar);

        Button botonGuardar;

        //registrar boton
        botonGuardar = (Button)findViewById(R.id.cmdGuardar);


        //registramos clics en boton guardar
        botonGuardar.setOnClickListener(this);


        img = (ImageView)findViewById(R.id.ImageView01);

        ((Button) findViewById(R.id.Button01))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }


    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onClick(View v) {

        //registramos los edittext
        EditText txtTitulo = (EditText)findViewById(R.id.txtTitulo);
        EditText txtAutor = (EditText)findViewById(R.id.txtAutor);
        ImageView ImageView01 = (ImageView)findViewById(R.id.ImageView01);
        EditText txtFecha = (EditText)findViewById(R.id.txtFecha);
        EditText txtDescripcion = (EditText)findViewById(R.id.txtDescripcion);


        boolean hayError = false;

        String titulo = txtTitulo.getText().toString(); //recogemos txttitulo
        String autor = txtAutor.getText().toString();
        String imagen = selectedImagePath;
        String descripcion = txtDescripcion.getText().toString(); //recogemos txteditorial
        String fecha = txtFecha.getText().toString();




        if ((titulo.length() < 1 || titulo.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre del disco no es válido.");
            hayError = true;
        }

        if ((autor.length() < 1 || autor.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre del grupo no es válido.");
            hayError = true;
        }

        if ((fecha.length() < 1 || fecha.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre de la fecha no es válido.");
            hayError = true;
        }

        if ((descripcion.length() < 1 || descripcion.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre de la descripcion no es válido.");
            hayError = true;
        }




        //si no hay ningun error ingresamos los nuevos datos de disco
        if (hayError == false) {
            Log.d("intercambio", "devuelve insertar" + titulo + autor + fecha + descripcion);
            Intent i=new Intent(this,MainActivity.class);
            i.putExtra("titulo",titulo);
            i.putExtra("autor",autor);
            i.putExtra("imagen",imagen);
            i.putExtra("fecha",fecha);
            i.putExtra("descripcion",descripcion);

            setResult(RESULT_OK, i);

        }

        finish();

    }

    private void mostrarMensaje(String mensaje) {

        // Toast.makeText(this.getBaseContext(), mensaje, Toast.LENGTH_LONG).show();

    }

}
