package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.editar;

/**
 * @author Belén Grande López
 * UserInformationActivity
 * 2021-11-15
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.gasguide.GasInformationActivity;

public class UserInformationActivity extends AppCompatActivity {

    private ConstraintLayout btn_editar;
    private ConstraintLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        btn_back = findViewById(R.id.btn_back_userinfo);
        btn_editar = findViewById(R.id.bt_editar);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditUserActivity.class);
                startActivity(i);
            }
        });
    }
}