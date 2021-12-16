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
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;

public class UserInformationActivity extends AppCompatActivity {

    //atributos
    private ConstraintLayout btn_editar;
    private ConstraintLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        //findById
        btn_back = findViewById(R.id.btn_back_userinfo);
        btn_editar = findViewById(R.id.bt_editar);

        //salir
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //cambiar de activity
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditUserActivity.class);
                startActivity(i);
            }
        });
    }
}