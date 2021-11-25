package com.example.jjpeajar.proyecto_3a_josejulio.src.main.login;

/**
 * @author Belén Grande López
 * RegisterActivity
 * 2021-11-11
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioTowns;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioUsarios;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.home.HomeFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    // Atributos
    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;
    private TextInputLayout layout_nombreusuario;
    private TextInputLayout layout_correo;
    private TextInputLayout layout_password;
    private TextInputLayout layout_repeatpassword;
    private EditText editText_username;
    private EditText editText_correo;
    private EditText editText_password;
    private EditText editText_repeatpassword;
    private Button button_register;
    private TextView login_txt;
    private String username;
    private String correo;
    private String password;
    private String town;
    private String repeatpassword;
    private List<String> nameTowns = new ArrayList<String>();
    private List<Town> Towns = new ArrayList<Town>();




    // Logica
    private LogicaNegocioUsarios logicaNegocioUsarios;
    private LogicaNegocioTowns logicaNegocioTowns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // logica negocio
        logicaNegocioUsarios = new LogicaNegocioUsarios();
        logicaNegocioTowns = new LogicaNegocioTowns();

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
        layout_repeatpassword = findViewById(R.id.input_repeatpassword);
        editText_repeatpassword = findViewById(R.id.password_repeatregister);




        logicaNegocioTowns.obtenerTown(new LogicaNegocioTowns.ObtenerTownsCallback() {
            @Override
            public void onCompletedObtenerTowns(List<Town> towns) {
                Towns=towns;

                for (Town cadatown: Towns){
                    nameTowns.add(cadatown.getName());
                }
            }

            @Override
            public void onFailedObtenerTowns(boolean res) {

            }
        });



        // Adapter para el array con el layout de los items suelos y el array de los items que contiene el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RegisterActivity.this,
                R.layout.dropdown_item,
                nameTowns
        );

        // Le asocio al input el adapter
        dropdowntxt.setAdapter(adapter);



        // Onclick boton que lleva al login
        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // Onclick boton registro
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtengo los datos que hay en el edit text y los almaceno
                correo = editText_correo.getText().toString();
                username = editText_username.getText().toString();
                password = editText_password.getText().toString();
                town = dropdowntxt.getText().toString();
                repeatpassword = editText_repeatpassword.getText().toString();

                // Compruebo si los campos están vacíos para que si lo están lance un mensaje de error
                // Campo correo
                if(correo.isEmpty()){
                    layout_correo.setErrorEnabled(true);
                    layout_correo.setError(getText(R.string.login_introduce_correo_ok));
                }else{
                    layout_correo.setErrorEnabled(false);
                }

                // Campo nombre de usuario
                if(username.isEmpty()){
                    layout_nombreusuario.setErrorEnabled(true);
                    layout_nombreusuario.setError(getText(R.string.register_introduce_username));
                }else{
                    layout_nombreusuario.setErrorEnabled(false);
                }

                // Campo contraseña
                if(password.isEmpty()){
                    layout_password.setErrorEnabled(true);
                    layout_password.setError(getText(R.string.login_introduce_contra));
                }else{
                    if(password.length()>=6){
                        if(!password.equals(repeatpassword)){
                            layout_password.setErrorEnabled(true);
                            layout_password.setError(getText(R.string.register_repeatpassF));
                        }else{
                            layout_password.setErrorEnabled(false);
                        }
                    }else{
                        layout_password.setErrorEnabled(true);
                        layout_password.setError("La contra no tiene más de 6");
                    }
                }

                // Campo municipio
                if(town.isEmpty()){
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError(getText(R.string.register_introduce_town));
                }else{
                    txtInputLayout.setErrorEnabled(false);
                }



                // SI en los dos inputs hay datos
                if(!correo.isEmpty() && !username.isEmpty() && !password.isEmpty() && !town.isEmpty() && !repeatpassword.isEmpty() && password.length()>=6){

                    if(isValidEmail(correo)){


                        int pos = adapter.getPosition(town);

                        int idTown = Towns.get(pos).getId();


                        logicaNegocioUsarios.registrarUsario(username, correo, password, repeatpassword, idTown , 5, new LogicaNegocioUsarios.RegistroCallback() {
                            @Override
                            public void onCompletedRegistrarUsario(int success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailedRegistrarUsario(boolean resultado) {

                            }
                        });
                    }
                }

            }
        });

    }

    public static boolean isValidEmail(String emailAddress) {
        return !emailAddress.contains(" ") && emailAddress.matches(".+@.+\\.[a-z]+");
    }

    /**
     * La descripción de setSnackbar. Funcion que muestra en pantalla un mensaje que acción.
     *
     * @param snackBarText String con el mensaje que queremos mostrar en pantalla.
     *
     */
    public void setSnackbar(String snackBarText){
        //creamos snackbar
        Snackbar snackBar = Snackbar.make( findViewById(R.id.layout_activity_register), snackBarText,Snackbar.LENGTH_LONG);
        //color boton snackbar
        snackBar.setActionTextColor(Color.CYAN);
        //boton cerrar snackbar
        snackBar.setAction("Cerrar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        //mostrar
        snackBar.show();
    } // ()
}