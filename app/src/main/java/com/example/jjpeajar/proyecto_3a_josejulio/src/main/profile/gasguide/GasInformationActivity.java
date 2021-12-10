package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide;

/**
 * @author Belén Grande López
 * GasInformationActivity
 * 2021-11-22
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class GasInformationActivity extends AppCompatActivity {

    private ConstraintLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_information);

        btn_back = findViewById(R.id.bt_back_infogas);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}