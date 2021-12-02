package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class ConfigurationNotificationsActivity extends AppCompatActivity {

    private ConstraintLayout bt_back_config_notif_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_notifications);

        bt_back_config_notif_n = findViewById(R.id.bt_back_config_notif_n);

        bt_back_config_notif_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}