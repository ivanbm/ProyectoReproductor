package com.izv.android.proyectoreproductor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Ivan on 29/11/2014.
 */
public class Adaptador extends ArrayAdapter<String> {

    private ArrayList<String> lista;
    private Context contexto;
    private int recurso;
    private static LayoutInflater i;


    public Adaptador(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView tv1;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);


            vh = new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tvTitulo);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        String n = lista.get(position).toString();
        String[] nombres = n.split("/");


        vh.position = position;
        vh.tv1.setTag(position);
        //Log.v("LOG",vh.tv1.getTag().toString());
        vh.tv1.setText(nombres[5]);

        return convertView;
    }
}