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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Gas;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class GasInformationActivity extends AppCompatActivity {

    //palette
    private ConstraintLayout btn_back;
    private TextView name;
    private TextView valor;
    private TextView info;
    private TextView alerta;
    private TextView desc;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recibir intent
        Intent intent = getIntent();
        //json
        String json = intent.getStringExtra("gas");
        //convertir json a pojo
        Gson gson= new Gson();
        Gas gas= gson.fromJson(json , Gas.class);

        //set layout
        setContentView(R.layout.activity_gas_information);

        //findById
        btn_back = findViewById(R.id.bt_back_infogas);
        name = findViewById(R.id.gas_information_name);
        valor = findViewById(R.id.gas_information_values);
        info = findViewById(R.id.gas_information_info);
        alerta = findViewById(R.id.gas_information_alert);
        desc = findViewById(R.id.gas_information_desc);
        image = findViewById(R.id.gas_information_image);

        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set gas values
        name.setText(gas.getName());
        valor.setText(gas.getFix_values());
        info.setText(gas.getInformation_values());
        alerta.setText(gas.getAlert_values());
        desc.setText(gas.getDescription());
        Picasso.get().load(gas.getRoute_image()).into(image);
    }
}