package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide;

/**
 * @author Belén Grande López
 * GasGuideActivity
 * 2021-11-16
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.google.android.material.card.MaterialCardView;

public class GasGuideActivity extends AppCompatActivity {

    //atributos
    private MaterialCardView btn_plomo;
    private MaterialCardView btn_cadmio;
    private MaterialCardView btn_particulas;
    private MaterialCardView btn_ozone;
    private MaterialCardView btn_azufe;
    private MaterialCardView btn_dioxido;
    private ConstraintLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_guide);

        //findById
        btn_azufe = findViewById(R.id.btn_azufre);
        btn_cadmio = findViewById(R.id.btn_cadmio);
        btn_plomo = findViewById(R.id.btn_plomo);
        btn_dioxido = findViewById(R.id.btn_dioxido);
        btn_particulas = findViewById(R.id.btn_particulas);
        btn_ozone = findViewById(R.id.btn_ozone);
        btn_back = findViewById(R.id.bt_back_gasguide);


        //onclicks
        btn_azufe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_particulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_ozone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_cadmio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_dioxido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_plomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GasInformationActivity.class);
                startActivity(i);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}