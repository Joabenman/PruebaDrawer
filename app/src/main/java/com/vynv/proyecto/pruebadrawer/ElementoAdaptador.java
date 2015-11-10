package com.vynv.proyecto.pruebadrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kinstorm on 08/11/2015.
 */
//adaptador del listview
class ElementoAdaptador extends ArrayAdapter<Elemento> {

    Activity context;
    private ArrayList<Elemento> listaElementosAdaptador;
    private ArrayList<Elemento> listaElementosFiltro;
    private FiltroElementos filter;
    ArrayList<Elemento> datosElementos = new ArrayList();

    public ElementoAdaptador(Context context, ArrayList<Elemento> datoslib) {
        super(context, R.layout.listitem_elemento, datoslib);

        this.listaElementosFiltro = new ArrayList<Elemento>();
        this.listaElementosFiltro.addAll(datoslib);

        this.listaElementosAdaptador = new ArrayList<Elemento>();
        this.listaElementosAdaptador.addAll(datoslib);


        this.datosElementos = new ArrayList<Elemento>();
        this.datosElementos.addAll(datoslib);

    }

    public View getView(int position, View convertView, ViewGroup parent) { //mostramos en el listview

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_elemento, null);

        TextView LblTitulo = (TextView)item.findViewById(R.id.LblListaTitulo);
        LblTitulo.setText(datosElementos.get(position).getTitulo());

        ImageView ImgElemento = (ImageView)item.findViewById(R.id.ImgListaElemento);

        Bitmap image = BitmapFactory.decodeFile(datosElementos.get(position).getRutaimagen());

        ImgElemento.setImageBitmap(image);

        TextView LblAutor = (TextView) item.findViewById(R.id.LblListaAutor);
        LblAutor.setText(datosElementos.get(position).getAutor());

        return(item);
    }


    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new FiltroElementos();
        }
        return filter;
    }

    private class FiltroElementos extends Filter //filtro
    {

        @Override
        protected FilterResults performFiltering(CharSequence contenido) {

            contenido = contenido.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(contenido != null && contenido.toString().length() > 0) //contenido mayor a 0
            {
                ArrayList<Elemento> ElementosFiltrados = new ArrayList<Elemento>();

                for(int i = 0, l = listaElementosFiltro.size(); i < l; i++)
                {
                    Log.d("filtro", listaElementosFiltro.size() + "  " + listaElementosFiltro.get(i) + "  " + contenido);
                    Elemento elemento = listaElementosFiltro.get(i);

                    if(elemento.getTitulo().toLowerCase().contains(contenido)) {
                        ElementosFiltrados.add(elemento);
                    }
                }
                result.count = ElementosFiltrados.size();
                result.values = ElementosFiltrados;
            }
            else
            {
                synchronized(this) //cuando no hay nada escrito en el filtro
                {
                    result.values = listaElementosAdaptador;
                    result.count = listaElementosAdaptador.size();
                }
            }
            return result;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, //si hay escrito algo en el filtro
                                      FilterResults results) {
            clear();
            listaElementosFiltro = (ArrayList<Elemento>)results.values;
            notifyDataSetChanged();

            for(int i = 0; i < listaElementosFiltro.size(); i++) {
                Log.d("filtro", listaElementosFiltro.size()+"");
                add(listaElementosFiltro.get(i));
            }
            notifyDataSetInvalidated();

        }
    }
}
