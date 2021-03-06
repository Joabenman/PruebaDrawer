package com.vynv.proyecto.pruebadrawer;

import android.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Socket miCliente;

    String direccion = "192.168.56.1";//oJo suele cambiar
    int puerto = 6000;



    ArrayList<Elemento> datosElementos = new ArrayList();
    ElementoAdaptador adaptador;
    EditText editsearch;

    String titulo="";
    String autor="";
    String imagen="";
    String fecha="";
    String descripcion="";
    String duracion="";
    Integer visto=0;
    Integer favorito=0;
    String tipoelemento="";
    String demografia="";
    String genero="";




    String nfichero="recibelibros.xml";

    private ListView listElemento;
    private int selectedItem;
    private TextView lblEtiqueta;
    private ListView lstOpciones;


    String menusocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FloatingActionButton subTip = (FloatingActionButton) findViewById(R.id.fab);
        subTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Insertar.class);
                startActivityForResult(intent, 1);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lblEtiqueta = (TextView) findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView) findViewById(R.id.ListViewLayout);

        //enables filtering for the contents of the given ListView
        lstOpciones.setTextFilterEnabled(true);

        //Log.d("tag",datoslibros.get(0).getTitulo());
        adaptador = new ElementoAdaptador(this, datosElementos);

        lstOpciones.setAdapter(adaptador);
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Alternativa 1:
                String opcionSeleccionada =
                        ((Elemento) a.getAdapter().getItem(position)).getTitulo();

                lblEtiqueta.setText("Libro seleccionado: " + opcionSeleccionada);
            }
        });

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }

        });
        listElemento = (ListView) findViewById(R.id.ListViewLayout);

        //filtro
        EditText myFilter = (EditText) findViewById(R.id.search);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptador.getFilter().filter(s.toString());
            }
        });
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
   // @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_libros) {
            // Handle the camera action
        } else if (id == R.id.nav_series) {

        } else if (id == R.id.nav_peliculas) {

        } else if (id == R.id.nav_opciones) {

        } else if (id == R.id.nav_login) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //cargamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void onResume() {

        super.onResume();
        if(datosElementos.size()==0){
            cargarElementos();
        }
        registerForContextMenu(listElemento);
    }

    @Override
    public void onStop() {
        super.onStop();
        guardarElementosSincronizado();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {//visualizar
            if (data.hasExtra("titulo")) {
                titulo = data.getExtras().getString("titulo");
            }
            if (data.hasExtra("autor")) {
                autor = data.getExtras().getString("autor");
            }
            if (data.hasExtra("imagen")) {
                imagen = data.getExtras().getString("imagen");
            }
            if (data.hasExtra("fecha")) {
                fecha = data.getExtras().getString("fecha");
            }
            if (data.hasExtra("descripcion")) {
                descripcion = data.getExtras().getString("descripcion");
            }
            if (data.hasExtra("duracion")) {
                duracion = data.getExtras().getString("duracion");
            }
            if (data.hasExtra("tipo")) {
                tipoelemento = data.getExtras().getString("tipo");
            }
            if (data.hasExtra("demografia")) {
                demografia = data.getExtras().getString("demografia");
            }
            if (data.hasExtra("genero")) {
                genero = data.getExtras().getString("genero");
            }


            Log.d("tag", titulo);
            if(fecha==null){//ojo//
                datosElementos.add(new Elemento(titulo, autor));
            }
            Log.d("intercambio", "pilla insertar" + titulo + autor + fecha + descripcion + duracion +visto+ favorito+ tipoelemento + demografia + genero);
            datosElementos.add(new Elemento(titulo, autor, imagen, fecha, descripcion, duracion, tipoelemento, demografia, genero));
            guardarElementosSincronizado();
            adaptador.notifyDataSetChanged();

        }//editar
        if (resultCode == RESULT_OK && requestCode == 3) {
            if (data.hasExtra("titulo")) {
                titulo = data.getExtras().getString("titulo");
            }
            if (data.hasExtra("autor")) {
                autor = data.getExtras().getString("autor");
            }
            if (data.hasExtra("imagen")) {
                imagen = data.getExtras().getString("imagen");
            }
            if (data.hasExtra("fecha")) {
                fecha = data.getExtras().getString("fecha");
            }
            if (data.hasExtra("descripcion")) {
                descripcion = data.getExtras().getString("descripcion");
            }
            if (data.hasExtra("duracion")) {
                duracion = data.getExtras().getString("duracion");
            }
            if (data.hasExtra("tipo")) {
                tipoelemento = data.getExtras().getString("tipo");
            }
            if (data.hasExtra("demografia")) {
                demografia = data.getExtras().getString("demografia");
            }
            if (data.hasExtra("genero")) {
                genero = data.getExtras().getString("genero");
            }

            if(fecha==null){//ojo//
                datosElementos.add(new Elemento(titulo, autor));
            }
            Log.d("intercambio", "pilla editar" + titulo + autor + fecha + descripcion + duracion +visto+ favorito+ tipoelemento + demografia + genero);
            datosElementos.add(new Elemento(titulo, autor, imagen, fecha, descripcion, duracion, tipoelemento, demografia, genero));

            adaptador.notifyDataSetChanged();
            guardarElementosSincronizado();
        }
    }

    //opciones del menu de la barra de herramientas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.insertar:
                Intent intent = new Intent(this, Insertar.class);

                startActivityForResult(intent, 1);

                break;

            case R.id.guardar:
                guardarElementosSincronizado();
                break;

            case R.id.cargar:
                cargarElementos();
                break;

            /*case R.id.importar:



                break;

            case R.id.exportar:


                break;

*/


            default:

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItem = info.position;
        menu.setHeaderTitle(datosElementos.get(info.position).getTitulo());
        inflater.inflate(R.menu.popup_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.mnuVisualizar:

                Intent intentVisualiza = new Intent(this, Visualizar.class);
                titulo =(datosElementos.get(info.position).getTitulo());
                autor =(datosElementos.get(info.position).getAutor());
                imagen =(datosElementos.get(info.position).getRutaimagen());
                fecha =(datosElementos.get(info.position).getFecha());
                descripcion =(datosElementos.get(info.position).getDescripcion());
                duracion =(datosElementos.get(info.position).getDuracion());
                visto =(datosElementos.get(info.position).getVisto());
                favorito =(datosElementos.get(info.position).getFavorito());
                tipoelemento =(datosElementos.get(info.position).getTipo());
                demografia =(datosElementos.get(info.position).getDemografia());
                genero =(datosElementos.get(info.position).getGenero());

                intentVisualiza.putExtra("titulo",titulo);
                intentVisualiza.putExtra("autor", autor);
                intentVisualiza.putExtra("imagen", imagen);
                intentVisualiza.putExtra("fecha",fecha);
                intentVisualiza.putExtra("descripcion",descripcion);
                intentVisualiza.putExtra("duracion",duracion);
                intentVisualiza.putExtra("visto",visto);
                intentVisualiza.putExtra("favorito",favorito);
                intentVisualiza.putExtra("tipo",tipoelemento);
                intentVisualiza.putExtra("demografia",demografia);
                intentVisualiza.putExtra("genero",genero);

                startActivity(intentVisualiza);
                Log.d("intercambio", "envia insertar" + titulo + autor + fecha + descripcion + duracion +visto+ favorito+ tipoelemento + demografia + genero);
                break;

            case R.id.mnuEditar:

                Intent intentEdita = new Intent(this, Editar.class);
                titulo =(datosElementos.get(info.position).getTitulo());
                autor =(datosElementos.get(info.position).getAutor());
                imagen =(datosElementos.get(info.position).getRutaimagen());
                fecha =(datosElementos.get(info.position).getFecha());
                descripcion =(datosElementos.get(info.position).getDescripcion());
                duracion = (datosElementos.get(info.position).getDuracion());
                tipoelemento = (datosElementos.get(info.position).getTipo());
                demografia =(datosElementos.get(info.position).getDemografia());
                genero =(datosElementos.get(info.position).getGenero());

                Log.d("titulo", titulo);
                intentEdita.putExtra("titulo",titulo);
                intentEdita.putExtra("autor", autor);
                intentEdita.putExtra("imagen", imagen);
                intentEdita.putExtra("fecha", fecha);
                intentEdita.putExtra("descripcion",descripcion);
                intentEdita.putExtra("duracion",duracion);
                intentEdita.putExtra("tipo",tipoelemento);
                intentEdita.putExtra("demografia",demografia);
                intentEdita.putExtra("genero",genero);

                Log.d("intercambio", "envia editar"  + titulo + autor + fecha + descripcion + duracion + tipoelemento + demografia + genero);
                startActivityForResult(intentEdita, 3);
                break;


            case R.id.mnuBorrar:
                //lo borramos de la base de datos
                borrar(info);

                //lo borramos del listview y lo actualizamos
                datosElementos.remove(selectedItem);

                cargarElementos();
                break;

        }

        return true;

    }

    private void guardarElementosSincronizado() {
        //guardamos los elementos en la base de datos
        ElementosSQLiteHelper Elementosdbh = new ElementosSQLiteHelper(this, "DBElementos", null, 1);
        SQLiteDatabase db = Elementosdbh.getWritableDatabase();
        ArrayList<Elemento> enviadatos = new ArrayList();
        enviadatos = datosElementos;
        datosElementos=Elementosdbh.saveSincronizado(enviadatos);
        db.close();

        adaptador = new ElementoAdaptador(this, datosElementos); //actualizamos el listview
        lstOpciones.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();
        Toast.makeText(this, "datos guardados de forma sincronizada ", Toast.LENGTH_SHORT).show();

    }



    private void cargarElementos() {
        //cargamos los elementos desde la base de datos
        Log.d("socket", "cargamos datos");
        ElementosSQLiteHelper Elementosdbh = new ElementosSQLiteHelper(this, "DBElementos", null, 1);
        SQLiteDatabase db = Elementosdbh.getReadableDatabase();

        datosElementos= Elementosdbh.load();
        db.close();

        adaptador = new ElementoAdaptador(this, datosElementos); //actualizamos el listview
        lstOpciones.setAdapter(adaptador);
        // adaptador.notifyDataSetChanged();
        Toast.makeText(this, "datos cargados ", Toast.LENGTH_SHORT).show();
    }

    private void borrar(AdapterView.AdapterContextMenuInfo info){
        //lo borramos de la base de datos
        ElementosSQLiteHelper Elementosdbh = new ElementosSQLiteHelper(this, "DBElementos", null, 1);
        titulo =(datosElementos.get(info.position).getTitulo()); //pillamos el titulo del elemento seleccionado
        Elementosdbh.delete(titulo);

    }



    //drawer layout

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
  /*  public static class FragmentElementos extends Fragment {
        public static final String ARG_NUMERO_OPCIONESDRAWER = "planet_number";


        public FragmentElementos() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_libros, container, false);
            int i = getArguments().getInt(ARG_NUMERO_OPCIONESDRAWER);


            String opciondrawer = getResources().getStringArray(R.array.opciones_array)[i];

            int imageId = getResources().getIdentifier(opciondrawer.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(opciondrawer);
            return rootView;
        }
    }*/

}
