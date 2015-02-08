package com.izv.android.proyectoreproductor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ivan on 07/02/2015.
 */
public class Reproduccion extends Activity {

    private String cancion;
    private String[] partir;
    private int pos;
    private TextView tvSonando;
    private ArrayList<String> listaCanciones;
    private String ruta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproduccion);

        //cancion = getIntent().getExtras().getString("cancion");
        pos = getIntent().getExtras().getInt("cancion");

        listaCanciones = new ArrayList<String>();
        listaCanciones = getIntent().getExtras().getStringArrayList("lista");
        add(listaCanciones.get(pos));


        setTitulo();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    public void add(String ruta){
        Intent intent = new Intent(this, ServicioAudio.class);
        intent.putExtra("cancion",ruta);
        intent.setAction(ServicioAudio.ADD);
        startService(intent);
    }

    public void play(View v){
        Intent intent = new Intent(this, ServicioAudio.class);
        intent.putExtra("cancion", ruta);
        intent.setAction(ServicioAudio.PLAY);
        startService(intent);
    }

    public void stop(View v){
        Intent intent = new Intent(this, ServicioAudio.class);
        intent.setAction(ServicioAudio.STOP);
        startService(intent);
    }

    public void pausa(View v){
        Intent intent = new Intent(this, ServicioAudio.class);
        intent.setAction(ServicioAudio.PAUSE);
        startService(intent);
    }

    public void siguiente(View v){
        pos = pos+1;
        if (listaCanciones.size()<=pos){
            ruta = listaCanciones.get(0);
            add(ruta);
            pos=0;
            setTitulo();
        }else{
            ruta = listaCanciones.get(pos);
            add(ruta);
            setTitulo();
            System.out.println("POSICION "+pos);
        }
    }

    public void anterior(View v){
        pos = pos-1;
        if (pos<0){
            add(listaCanciones.get(listaCanciones.size()-1));
            pos = listaCanciones.size()-1;
            setTitulo();
        }else{
            add(listaCanciones.get(pos));
            setTitulo();
            System.out.println("POSICION "+pos);
        }
    }

    public void setTitulo(){
        partir = listaCanciones.get(pos).split("/");
        tvSonando = (TextView)findViewById(R.id.tvSonando);
        tvSonando.setText(partir[5]);
    }


    public void pararServicio(){
        stopService(new Intent(this, ServicioAudio.class));
    }
}
