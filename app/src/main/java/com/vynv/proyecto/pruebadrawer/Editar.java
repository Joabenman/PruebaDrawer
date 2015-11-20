package com.vynv.proyecto.pruebadrawer;

/**
 * Created by Msi on 15/04/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Editar extends Activity implements View.OnClickListener {

    ElementoAdaptador adaptador;

    private static final int SELECT_PICTURE = 1;
    private boolean hayimagen=false;
    private String selectedImagePath;
    private String rutaoriginal="";
    private ImageView img;

    private String spinTipoData = null;
    private String spinDemografiaData = null;
    private String spinGeneroData = null;


    String imagen="";
    //inicializamos la interfaz
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicializamos interfaz
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        Button botonActualiza;

        //registrar boton
        botonActualiza = (Button)findViewById(R.id.cmdActualizar);

        //registramos clics en boton actualizar
        botonActualiza.setOnClickListener(this);

        //recogemos los textview de la interfaz
        EditText txtTitulo = (EditText)findViewById(R.id.txtTitulo);
        EditText txtAutor = (EditText)findViewById(R.id.txtAutor);
        EditText txtfecha = (EditText)findViewById(R.id.txtFecha);
        EditText txtDescripcion = (EditText)findViewById(R.id.txtDescripcion);
        EditText txtDuracion = (EditText)findViewById(R.id.txtDuracion);

        img = (ImageView)findViewById(R.id.ImageView01);


        //pillamos datos del intent
        Intent intent = getIntent();



        String titulo =  intent.getStringExtra("titulo");
        String autor =  intent.getStringExtra("autor");
        String imagen =  intent.getStringExtra("imagen");
        rutaoriginal =  imagen;
        String fecha =  intent.getStringExtra("fecha");
        String descripcion =  intent.getStringExtra("descripcion");
        String duracion =  intent.getStringExtra("duracion");
        spinTipoData =  intent.getStringExtra("tipo");
        spinDemografiaData =  intent.getStringExtra("demografia");
        spinGeneroData =  intent.getStringExtra("genero");

        Log.d("intercambio", "arranca editar" + titulo + autor + fecha + descripcion + duracion +spinTipoData +spinDemografiaData +spinGeneroData);

        //guardamos los datos recogidos del intent
        txtTitulo.setText(titulo);
        txtAutor.setText(autor);
        Bitmap image = BitmapFactory.decodeFile(imagen);
        img.setImageBitmap(image);
        txtfecha.setText(fecha);
        txtDescripcion.setText(descripcion);
        txtDuracion.setText(duracion);


        // img = (ImageView)findViewById(R.id.ImageView01);

        ((Button) findViewById(R.id.Button01))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        hayimagen=true;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture2"), SELECT_PICTURE);
                    }
                });

    }




    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {

                //spinner de tipo
                case R.id.spinTipo:
                    //make sure the country was already selected during the onCreate
                    if(spinTipoData != null){
                        Toast.makeText(parent.getContext(), "Tipo seleccionado:  " + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    spinTipoData = selectedItem;
                    break;

                //spinner de demografia
                case R.id.spinDemografia:
                    //make sure the animal was already selected during the onCreate
                    if(spinDemografiaData != null){
                        Toast.makeText(parent.getContext(), "Demografia seleccionada: " + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    spinDemografiaData = selectedItem;
                    break;

                //spinner de genero
                case R.id.spinGenero:
                    //make sure the animal was already selected during the onCreate
                    if(spinGeneroData != null){
                        Toast.makeText(parent.getContext(), "Genero seleccionado:" + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    spinGeneroData = selectedItem;
                    break;
            }

            Log.d("intercambio", "fin spinner");
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                /*
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;*/
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

                //        System.out.println("Image Path : " + selectedImagePath);

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

        String titulo2 = txtTitulo.getText().toString(); //recogemos txttitulo
        String autor2 = txtAutor.getText().toString(); //recogemos txtautor
        String rutaimagen="";
        if(hayimagen==true) { //comprobamos si hay una imagen seleccionada
            rutaimagen = selectedImagePath;
        }else{
            rutaimagen = rutaoriginal;
        }
        String fecha2 = txtFecha.getText().toString(); //recogemos txteditorial
        String descripcion2 = txtDescripcion.getText().toString(); //recogemos txteditorial
        String duracion2 = txtDuracion.getText().toString();//recogemos duracion





        if ((titulo2.length() < 1 || titulo2.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre del titulo no es válido.");
            hayError = true;
        }

        //si el nombre del grupo no es valido
        if ((autor2.length() < 1 || autor2.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre del autor no es válido.");
            hayError = true;
        }

        if ((fecha2.length() < 1 || fecha2.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre de la fecha no es válido.");
            hayError = true;
        }

        if ((descripcion2.length() < 1 || descripcion2.length() > 50) && hayError == false) {
            mostrarMensaje("El nombre de la descripcion no es válido.");
            hayError = true;
        }
        if ((duracion2.length() < 1 || duracion2.length() > 50) && hayError == false) {
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
            Log.d("intercambio", "devuelve editar" + titulo2 + autor2 + fecha2 + descripcion2 + duracion2 +spinTipoData +spinDemografiaData +spinGeneroData);
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("titulo",titulo2);
            i.putExtra("autor",autor2);
            i.putExtra("imagen", rutaimagen);
            i.putExtra("fecha",fecha2);
            i.putExtra("descripcion",descripcion2);
            i.putExtra("duracion",duracion2);
            i.putExtra("tipo",spinTipoData);
            i.putExtra("demografia",spinDemografiaData);
            i.putExtra("genero",spinGeneroData);

            setResult(RESULT_OK, i);
        }

        finish();

    }

    private void mostrarMensaje(String mensaje) {

        Toast.makeText(this.getBaseContext(), mensaje, Toast.LENGTH_LONG).show();

    }

}
