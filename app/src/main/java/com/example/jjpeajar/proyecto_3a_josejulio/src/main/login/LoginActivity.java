package com.example.jjpeajar.proyecto_3a_josejulio.src.main.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    //atributos
    private TextInputLayout layout_correo;
    private TextInputLayout layout_password;
    private TextInputEditText editText_correo;
    private TextInputEditText editText_password;
    private Button btn_check;
    private TextView register_txt;

    private String correo;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findById
        layout_correo=findViewById(R.id.login_input_correo);
        layout_password=findViewById(R.id.login_input_password);
        editText_correo=findViewById(R.id.login_editText_mail);
        editText_password=findViewById(R.id.login_editText_password);
        btn_check=findViewById(R.id.btn_iniciar_sesion);
        register_txt=findViewById(R.id.login_register_txt);

        //onClick

        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo= editText_correo.getText().toString();
                password = editText_password.getText().toString();

                //verificamos si los inputs estan vacias o no
                if (correo.isEmpty()) { //si el input del mail esta vacio
                    layout_correo.setErrorEnabled(true);
                    layout_correo.setError(getText(R.string.login_introduce_correo_ok));
                    //return;
                }else{
                    layout_correo.setErrorEnabled(false);
                }
                if (password.isEmpty()) { //si el input del mail esta vacio
                    layout_password.setErrorEnabled(true);
                    layout_password.setError(getText(R.string.login_introduce_contra));
                    //return;
                }else{
                    layout_password.setErrorEnabled(false);
                }

                //si en los dos inputs hay data
                if(!correo.isEmpty() && !password.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this, MenuMainActivity.class);
                    startActivity(intent);
                }

            }
        });


    }
}