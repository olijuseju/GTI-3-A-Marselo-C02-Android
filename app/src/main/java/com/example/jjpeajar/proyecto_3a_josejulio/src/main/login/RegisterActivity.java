package com.example.jjpeajar.proyecto_3a_josejulio.src.main.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.home.HomeFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    // Atributos

    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;
    private TextInputLayout layout_nombreusuario;
    private TextInputLayout layout_correo;
    private TextInputLayout layout_password;
    private EditText editText_username;
    private EditText editText_correo;
    private EditText editText_password;
    private Button button_register;
    private TextView login_txt;
    private String username;
    private String correo;
    private String password;
    private String town;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // FindViewById
        txtInputLayout = findViewById(R.id.register_input_town);
        dropdowntxt = findViewById(R.id.dropDown_txt_register);
        layout_nombreusuario = findViewById(R.id.register_input_username);
        layout_correo = findViewById(R.id.register_input_mail);
        layout_password = findViewById(R.id.register_input_password);
        editText_username = findViewById(R.id.username_register);
        editText_correo = findViewById(R.id.mail_register);
        editText_password = findViewById(R.id.password_register);
        button_register = findViewById(R.id.button_register);
        login_txt = findViewById(R.id.button_go_login);


        String[] items = new String[]{
               "Teulada",
                "Albaida",
                "Valencia"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RegisterActivity.this,
                R.layout.dropdown_item,
                items
        );

        dropdowntxt.setAdapter(adapter);

        // Onclick

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = editText_correo.getText().toString();
                username = editText_username.getText().toString();
                password = editText_password.getText().toString();
                town = dropdowntxt.getText().toString();

                if(correo.isEmpty()){
                    layout_correo.setErrorEnabled(true);
                    layout_correo.setError(getText(R.string.login_introduce_correo_ok));
                }else{
                    layout_correo.setErrorEnabled(false);
                }

                if(username.isEmpty()){
                    layout_nombreusuario.setErrorEnabled(true);
                    layout_nombreusuario.setError(getText(R.string.register_introduce_username));
                }else{
                    layout_nombreusuario.setErrorEnabled(false);
                }

                if(password.isEmpty()){
                    layout_password.setErrorEnabled(true);
                    layout_password.setError(getText(R.string.login_introduce_contra));
                }else{
                    layout_password.setErrorEnabled(false);
                }

                if(town.isEmpty()){
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError(getText(R.string.register_introduce_town));
                }else{
                    txtInputLayout.setErrorEnabled(false);
                }

                // SI en los dos inputs hay datos

                if(!correo.isEmpty() && !username.isEmpty() && !password.isEmpty() && !town.isEmpty()){
                    Intent intent = new Intent(RegisterActivity.this, MenuMainActivity.class);
                    startActivity(intent);
                }


            }
        });




    }
}