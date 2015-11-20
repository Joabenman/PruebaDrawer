package com.vynv.proyecto.pruebadrawer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class Insertar extends Activity implements View.OnClickListener {



    ElementoAdaptador adaptador;

    ArrayList<Elemento> datoslibros;
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    private ImageView img;

    private String spinTipoData = null;
    private String spinDemografiaData = null;
    private String spinGeneroData = null;




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


        Log.d("intercambio", "recorreinsertar" + 1);
        //Spiners

        Spinner spintipo, spindemografia, spingenero;
        spintipo = (Spinner) findViewById(R.id.spinTipo);
        spindemografia = (Spinner) findViewById(R.id.spinDemografia);
        spingenero = (Spinner) findViewById(R.id.spinGenero);

       spintipo.setOnItemSelectedListener( myListener);
        //spindemografia.setOnItemSelectedListener( myListener);
        //spingenero.setOnItemSelectedListener( myListener);



        Log.d("intercambio", "recorreinsertar" + 2);
    }




    OnItemSelectedListener myListener=new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Log.d("intercambio", "recorreinsertar" + 3);
            String selectedItem = arg0.getItemAtPosition(arg2).toString();
            Log.d("intercambio", "clicado" + arg2);
            switch (arg0.getId()) {
                case R.id.spinTipo:
                    //make sure the country was already selected during the onCreate
                    Log.d("intercambio", "tipo cambiado" +spinTipoData);
                    if(spinTipoData != null){
                        Toast.makeText(arg0.getContext(), "Tipo seleccionado:  " + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }

                    spinTipoData = selectedItem;
                    Log.d("intercambio", "tipo cambiado" + spinTipoData);
                    break;
                case R.id.spinGenero:

                    if(spinDemografiaData != null){
                        Toast.makeText(arg0.getContext(), "Demografia seleccionada: " + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    spinDemografiaData = selectedItem;
                    Log.d("intercambio", "demografia cambiado" + spinDemografiaData);
                    break;
                case R.id.spinDemografia:

                    if(spinGeneroData != null){
                        Toast.makeText(arg0.getContext(), "Genero seleccionado:" + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    spinGeneroData = selectedItem;
                    Log.d("intercambio", "genero cambiado" + spinGeneroData);
            }
            Log.d("intercambio", "recorreinsertar" + 4);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            Log.d("intercambio", "recorreinsertar" + 5);
        }
    };



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
        EditText txtDuracion = (EditText)findViewById(R.id.txtDuracion);


        boolean hayError = false;

        //variables que guardaran los datos a insertar
        Log.d("intercambio", "recorreinsertar" + 6);
        String titulo = txtTitulo.getText().toString(); //recogemos txttitulo
        String autor = txtAutor.getText().toString();
        String imagen = selectedImagePath;
        String descripcion = txtDescripcion.getText().toString(); //recogemos txteditorial
        String fecha = txtFecha.getText().toString();
        String duracion = txtDuracion.getText().toString();

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

        if ((duracion.length() < 1 || duracion.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre de la descripcion no es válido.");
            hayError = true;
        }

        if(spinTipoData==null){
            String[] Stipoelemento = getResources().getStringArray(R.array.spinner_tipo);
            spinTipoData= Stipoelemento[0];
        }

        if(spinDemografiaData==null){
            String[] Sdemografia = getResources().getStringArray(R.array.spinner_demografia);
            spinDemografiaData= Sdemografia[0];
        }

        if(spinGeneroData==null){
            String[] Sgenero = getResources().getStringArray(R.array.spinner_genero);
            spinGeneroData= Sgenero[0];
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
            i.putExtra("duracion",duracion);
            i.putExtra("tipo",spinTipoData);
            i.putExtra("demografia",spinDemografiaData);
            i.putExtra("genero",spinGeneroData);

            setResult(RESULT_OK, i);

        }

        finish();

    }

    private void mostrarMensaje(String mensaje) {

        // Toast.makeText(this.getBaseContext(), mensaje, Toast.LENGTH_LONG).show();

    }

}
