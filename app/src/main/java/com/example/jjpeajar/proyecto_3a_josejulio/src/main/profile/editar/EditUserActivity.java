package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.editar;

/**
 * @author Jose Julio Peñaranda
 * EditUserActivity
 * 2021-11-16
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioTowns;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioUsarios;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.login.LoginActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.login.RegisterActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class EditUserActivity extends AppCompatActivity {

    // Atributos

    //Inputs
    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;
    private TextInputEditText username_edituser;
    private TextInputEditText mail_edituser;
    private TextInputEditText password_edituser;
    private TextInputEditText repeatpassword_edituser;

    //logica
    private LogicaNegocioUsarios logicaNegocioUsarios;

    //Botones arriba
    private ConstraintLayout bt_ok;
    private ConstraintLayout bt_back_edit;

    //Adapter Towns
    private List<String> nameTowns = new ArrayList<String>();
    private List<Town> ListTowns = new ArrayList<Town>();
    private ArrayAdapter<String> adapter;
    private String townName;
    private int Town_id;

    private LogicaNegocioTowns logicaNegocioTowns;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        //si ya ha iniciado sesion
        String access_token = (shared.getString("access_token", null));

        setContentView(R.layout.activity_edit_user);

        //findView
        username_edituser = findViewById(R.id.username_edituser);
        mail_edituser = findViewById(R.id.mail_edituser);
        password_edituser = findViewById(R.id.password_edituser);
        repeatpassword_edituser = findViewById(R.id.repeatpassword_edituser);

        bt_ok = findViewById(R.id.bt_ok);
        bt_back_edit = findViewById(R.id.bt_back_edit);

        txtInputLayout = findViewById(R.id.input_town);
        dropdowntxt = findViewById(R.id.dropDown_txt);

        townName = dropdowntxt.getText().toString();

        //Logica negocio
        logicaNegocioUsarios=new LogicaNegocioUsarios();
        logicaNegocioTowns=new LogicaNegocioTowns();

        //Get usuario para obtener sus datos
        logicaNegocioUsarios.obtenerUsario(Integer.parseInt(shared.getString("user_id", null)), access_token , new LogicaNegocioUsarios.GetUsuariosCallback() {
            @Override
            public void onCompletedGetUsuario(UserController userController) {

                //Guardamos datos en las cookies
                SharedPreferences.Editor editor = getSharedPreferences("com.example.jjpeajar.proyecto_3a_josejulio", MODE_PRIVATE).edit();
                editor.putString("user_name", userController.getUser().getName());
                editor.putString("user_email", userController.getUser().getEmail());
                editor.putInt("town_id", userController.getUserInformation().getTown_id());
                editor.putString("user_id", String.valueOf( (int) userController.getUser().getId() ));
                editor.apply();

                //Rellenamos los inputs con los datos del user
                username_edituser.setText(shared.getString("user_name", null));
                mail_edituser.setText(shared.getString("user_email", null));
                password_edituser.setText("123456789");
                repeatpassword_edituser.setText("123456789");

                Town_id = shared.getInt("town_id", -1);

            }

            @Override
            public void onFailedGetUsuario(boolean res) {

            }
        });


        //Get towns para rellenar el adapter
        logicaNegocioTowns.obtenerTown(new LogicaNegocioTowns.ObtenerTownsCallback() {
            @Override
            public void onCompletedObtenerTowns(List<Town> towns) {
                ListTowns=towns;

                for (Town cadatown: ListTowns){
                    nameTowns.add(cadatown.getName());
                }

                // Adapter para el array con el layout de los items suelos y el array de los items que contiene el desplegable
                adapter = new ArrayAdapter<>(
                        EditUserActivity.this,
                        R.layout.dropdown_item,
                        nameTowns
                );

                // Le asocio al input el adapter
                dropdowntxt.setAdapter(adapter);

                //Pongo el municipio del usuario en el dropdown
                if(Town_id!=-1){
                    Log.d("pepeupdate", "  IDTOWNN -------------------------------------  " + Town_id);
                    dropdowntxt.setText(ListTowns.get(Town_id - 1).getName(), false);
                }

            }

            @Override
            public void onFailedObtenerTowns(boolean res) {

            }
        });


        //On click
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUsuario(shared.getString("user_id", null), access_token);
            }
        });

        bt_back_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Descripcion de editarUsuario. Editamos el usuario cuando hace click en el boton de arriba a la derecha
     *
     * @param id . id del user
     * @param token , access_token del user registrado
     */
    private void editarUsuario(String id, String token){

        townName = dropdowntxt.getText().toString();
        if(!townName.isEmpty()) {
            //Obtenemos el id de la town del bottomsheet
            int pos = adapter.getPosition(townName);
            int idTown = ListTowns.get(pos).getId();

            //Actualizamos usuario
            logicaNegocioUsarios.actualizarUsuario(Integer.parseInt(id), token, username_edituser.getText().toString(), mail_edituser.getText().toString(), password_edituser.getText().toString(), idTown, new LogicaNegocioUsarios.UpdateCallback() {
                @Override
                public void onCompletedUpdateUsuario(UserController userController) {


                    //Guardamos datos en las cookies
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.jjpeajar.proyecto_3a_josejulio", MODE_PRIVATE).edit();
                    editor.putString("user_name", userController.getUser().getName());
                    editor.putString("user_email", userController.getUser().getEmail());
                    editor.putInt("town_id", userController.getUserInformation().getTown_id());
                    editor.putString("user_id", String.valueOf((int) userController.getUser().getId()));
                    editor.apply();

                    //Salimos de la activity al editar el usuario
                    finish();
                }

                @Override
                public void onFailedUpdateUsuario(boolean resultado) {

                }
            });
        }
    }
}