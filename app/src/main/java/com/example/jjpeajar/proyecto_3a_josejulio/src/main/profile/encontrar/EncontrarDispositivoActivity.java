package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.encontrar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.google.android.material.snackbar.Snackbar;

public class EncontrarDispositivoActivity extends AppCompatActivity {

    //palette
    private TextView txt_distance;
    private ConstraintLayout btn_back;
    private ProgressBar progressBar;

    //atributos
    private int currentProgress= 0;

    //sharedPreferences
    SharedPreferences shared;

    // para llamar al metodo de obtener la distancia cada cierto tiempo
    Handler handler = new Handler();
    private final int TIEMPO = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init sharedPrefereces
        shared = getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);
        //set layout
        setContentView(R.layout.activity_encontrar_dispositivo);

        //findById
        txt_distance = findViewById(R.id.distancia);
        btn_back= findViewById(R.id.back_encontrarDispositivo);
        progressBar= findViewById(R.id.encontrar_dispositivo_progress_bar);

        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //llamar a la funcion cada cierto tiempo para recuperar el valor de la media de las distancias
        // captadas de los beacons

        //obtener la distancia
        setDistanciaToProgressBar(); //se llama por primera para establecer los valores del inicio
        //llamar al metodo cada 10 segundos
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //llamar al metodo
                setDistanciaToProgressBar();

                handler.postDelayed(this , TIEMPO);

            }
        }, TIEMPO);

    }

    /**
     * La descripción de setDistanciaToProgressBar. Funcion que recoge el valor de la ultima media de
     * distancia leida de los beacons y inicializa el progress bar con ese valor y su maximo.
     *
     *
     */
    private void setDistanciaToProgressBar(){

        //get distance last distance
        //sacamos el id del user
        String distance_coockies = shared.getString("media_distancia", null);

        if(distance_coockies != null){ // si no esta vacío

            //redondear a int el valor de la distancia
            currentProgress = (int) Math.round(Double.valueOf(distance_coockies));

            Log.d("pepe", "setDistanciaToProgressBar() RESULTADO GUCCI -> " + currentProgress);

            //restamos para que el progress bar se rellene cuando esta más cerca el dispositivo
            currentProgress = 250 - currentProgress;

            //set progress bar value
            progressBar.setProgress(currentProgress); //set el valor del progress bar
            progressBar.setMax(250); //maximo valor del progress bar

            Log.d("pepe", "setDistanciaToProgressBar() getProgress -> " + progressBar.getProgress());

            estimarDistancia(currentProgress);

            currentProgress = 0;
        }else{
            String message = " Buscando dispositivo ... ";
            setSnackbar(message);

            txt_distance.setText(message);
        }

    }

    /**
     * La descripción de estimarDistancia. Funcion que estima la distancia actual.
     *
     * @params distancia , distancia actual que se guarda en los sharedPreferences
     *
     */
    private void estimarDistancia(int distancia){

        if(distancia >= 0 && distancia <=50){
            txt_distance.setText("Muy lejos");
        }else if(distancia > 50 && distancia <= 100 ){
            txt_distance.setText("Lejos");
        }else if(distancia > 100 && distancia <= 200){
            txt_distance.setText("Cerca");
        }else if(distancia > 200){
            txt_distance.setText("Muy cerca");
        }

    }


    /**
     * La descripción de setSnackbar. Funcion que muestra en pantalla un mensaje que acción.
     *
     * @param snackBarText String con el mensaje que queremos mostrar en pantalla.
     *
     */
    public void setSnackbar(String snackBarText){
        //creamos snackbar
        Snackbar snackBar = Snackbar.make( findViewById(R.id.layout_encontrar_dispositivo), snackBarText,Snackbar.LENGTH_LONG);
        //color boton snackbar
        snackBar.setActionTextColor(Color.CYAN);
        //boton cerrar snackbar
        snackBar.setAction("Cerrar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        //mostrar
        snackBar.show();
    } // ()
}