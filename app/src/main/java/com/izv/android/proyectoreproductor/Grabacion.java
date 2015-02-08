package com.izv.android.proyectoreproductor;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivan on 07/02/2015.
 */
public class Grabacion extends Activity {

    private MediaRecorder grabador;
    private ImageView ivEstado;
    private String guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grabacion);

        grabador = new MediaRecorder();
        ivEstado = (ImageView)findViewById(R.id.ivGrabando);
    }

    public void iniciar(View v){

        ivEstado.setImageResource(R.drawable.grabando);
        guardar = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/"+ obtenerfecha() +".mp4";
        System.out.println("RUTAAAAAAAAAA "+guardar);
        grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
        grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        grabador.setOutputFile(guardar);
        grabador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            grabador.prepare();
            grabador.start();
        }catch (IOException e){
            System.out.println("ERROR AL GRABAR");
        }


    }

    public void parar(View v){
        ivEstado.setImageResource(R.drawable.parado);
        grabador.stop();
        grabador.release();
        grabador = null;
    }

    public String obtenerfecha(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fecha = sdf.format(new Date());
        return fecha;
    }
}
