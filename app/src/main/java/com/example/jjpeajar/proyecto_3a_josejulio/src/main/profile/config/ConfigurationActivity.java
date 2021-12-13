package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.config;

/**
 * @author Jose Julio Peñaranda Jara
 * ConfigurationActivity
 * 2021-11-23
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class ConfigurationActivity extends AppCompatActivity {

    //atributos
    private ConstraintLayout bt_back_config;
    private ConstraintLayout clTema;
    private ConstraintLayout clNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        //findById
        bt_back_config = findViewById(R.id.bt_back_config);
        clTema = findViewById(R.id.clTema);
        clNotificaciones = findViewById(R.id.clNotificaciones);
        //salir del activity
        bt_back_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //entrar en otro activity
        clTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConfigurationThemeActivity.class);
                startActivity(i);
            }
        });
        //entrar en otro activity
        clNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConfigurationNotificationsActivity.class);
                startActivity(i);
            }
        });
    }
}