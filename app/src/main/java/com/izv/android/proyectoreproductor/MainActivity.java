package com.izv.android.proyectoreproductor;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> listaCanciones;
    private ListView lv;
    private Adaptador ad;
    private static int REPRODUCIR = 1;
    private static int GRABAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.listView);
        obtenerCanciones();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(MainActivity.this,Reproduccion.class);
                //i.putExtra("cancion",listaCanciones.get(position).toString());
                i.putExtra("cancion",position);
                i.putStringArrayListExtra("lista", listaCanciones);
                startActivityForResult(i, REPRODUCIR);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reproduccion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.grabadora) {
            Intent i = new Intent(this, Grabacion.class);
            startActivityForResult(i, GRABAR);
            /*Intent intent= new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            startActivityForResult(intent, GRABAR);*/
        }else if(id == R.id.salir){
                stopService(new Intent(this, ServicioAudio.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerCanciones();
        ad.setNotifyOnChange(true);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode== RESULT_OK &&requestCode== GRABAR) {
            Uri uri = data.getData();
        }
    }

    /*---------------------------------------*/
    /*          OBTENER CANCIONES            */
    /*---------------------------------------*/

    public void obtenerCanciones(){

        String directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString();

        File dir = new File(directorio);
        listaCanciones = new ArrayList<String>();
        File[] fList = dir.listFiles();
        for (File file : fList) {
            String f = file.toString();
            System.out.println("NOMBRE "+f);
            String[] partir = f.split("\\.");

            System.out.println("EXTENSION" + partir[1]);
            if(partir[1].equals("mp3") || partir[1].equals("mp4")){
                listaCanciones.add(file.toString());
            }
        }


        ad = new Adaptador(this, R.layout.detalle, listaCanciones);
        lv.setAdapter(ad);
        registerForContextMenu(lv);

    }
}
