package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.config;

/**
 * @author Jose Julio Peñaranda Jara
 * ConfigurationNotificationsActivity
 * 2021-11-23
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class ConfigurationThemeActivity extends AppCompatActivity {

    //atributos
    private ConstraintLayout bt_back_config;
    private CheckBox checksis;
    private CheckBox checknon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_theme);
        //findById
        bt_back_config = findViewById(R.id.bt_back_config_t);
        checksis=findViewById(R.id.checkman);
        checknon=findViewById(R.id.checkd2);
        //salir del activity
        bt_back_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //checks
        checksis.setChecked(true);
        checknon.setChecked(false);
        //cambiar check
        checksis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checknon.setChecked(false);
                }else{
                    checknon.setChecked(true);
                }
            }
        });
        //cambiar check
        checknon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checksis.setChecked(false);
                }else{
                    checksis.setChecked(true);
                }
            }
        });

    }
}