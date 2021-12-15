package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.encontrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class EncontrarDispositivoActivity extends AppCompatActivity {

    //palette
    private TextView txt_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init sharedPrefereces
        SharedPreferences shared = getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);
        //get distance last distance
        //sacamos el id del user
        String distance_coockies = shared.getString("media_distancia", null);
        //set layout
        setContentView(R.layout.activity_encontrar_dispositivo);


        if(distance_coockies != null){
            txt_distance.setText(distance_coockies);
        }


    }
}