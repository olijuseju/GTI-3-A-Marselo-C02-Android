package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class ConfigurationNotificationsActivity extends AppCompatActivity {

    private ConstraintLayout bt_back_config_notif_n;
    private CheckBox checksis;
    private CheckBox checknon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_notifications);

        bt_back_config_notif_n = findViewById(R.id.bt_back_config_notif_n);

        checksis=findViewById(R.id.checksis);
        checknon=findViewById(R.id.checknon);

        bt_back_config_notif_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checksis.setChecked(true);
        checknon.setChecked(false);

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