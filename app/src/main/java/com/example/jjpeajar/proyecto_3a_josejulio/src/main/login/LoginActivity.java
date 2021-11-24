package com.example.jjpeajar.proyecto_3a_josejulio.src.main.login;

/**
 * @author Andrey Kuzmin
 * 2021-11-11
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioUsarios;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.android.material.snackbar.Snackbar;
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

    //Logica
    private LogicaNegocioUsarios logicaNegocioUsarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //coockies
        SharedPreferences shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        //si ya ha iniciado sesion
        String access_token = (shared.getString("access_token", null));

        if(access_token != null){
            //cambio de actividad
            Intent intent = new Intent(LoginActivity.this, MenuMainActivity.class);
            startActivity(intent);
        }else{
            // creamos la activity
            setContentView(R.layout.activity_login);

            //logica negocia
            logicaNegocioUsarios= new LogicaNegocioUsarios();

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


                        if(isValidEmail(correo)){
                            logicaNegocioUsarios.login(correo, password, new LogicaNegocioUsarios.UsariosCallback() {
                                @Override
                                public void onCompletedIniciarSesion(UserController userController) {
                                    Log.d("pepe",userController.getSuccess()+"");

                                    //coockies
                                    SharedPreferences shared= getSharedPreferences(
                                            "com.example.jjpeajar.proyecto_3a_josejulio"
                                            , MODE_PRIVATE);
                                    SharedPreferences.Editor editor = shared.edit();
                                    editor.putString("user_name", userController.getUser().getName());
                                    editor.putString("user_email", userController.getUser().getEmail());
                                    editor.putString("access_token", userController.getAccess_token());
                                    editor.putString("user_id", String.valueOf( (int) userController.getUser().getId() ));
                                    editor.commit();

                                    //cambio de actividad
                                    Intent intent = new Intent(LoginActivity.this, MenuMainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailedIniciarSesion(boolean res) {
                                    Log.d("pepe","el correo no existe en la base de datos");
                                    setSnackbar((String) getText(R.string.login_datos_incorrectos));
                                }

                                @Override
                                public void onCompletedRegistrarUsario(int success) {

                                }
                                @Override
                                public void onFailedRegistrarUsario(int success) {

                                }
                            });
                            layout_correo.setErrorEnabled(false);
                        }else{
                            layout_correo.setErrorEnabled(true);
                            layout_correo.setError(getText(R.string.login_introduce_correo_ok));
                        }
                    }
                }
            });
        }
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
        Snackbar snackBar = Snackbar.make( findViewById(R.id.layout_activity_login), snackBarText,Snackbar.LENGTH_LONG);
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