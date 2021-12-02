package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class ConfigurationActivity extends AppCompatActivity {


    private ConstraintLayout bt_back_config;
    private ConstraintLayout clTema;
    private ConstraintLayout clNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        bt_back_config = findViewById(R.id.bt_back_config);
        clTema = findViewById(R.id.clTema);
        clNotificaciones = findViewById(R.id.clNotificaciones);

        bt_back_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConfigurationThemeActivity.class);
                startActivity(i);
            }
        });

        clNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConfigurationNotificationsActivity.class);
                startActivity(i);
            }
        });
    }
}