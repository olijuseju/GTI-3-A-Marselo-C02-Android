package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile;

/**
 * @author Jose Julio Peñaranda
 * ProfileFragment
 * 2021-11-12
 */

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioUsarios;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.login.LoginActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.editar.EditUserActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.vincular.VincularDispositivoActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.route.RecorridoActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.lang.invoke.ConstantCallSite;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //atributos
    private ConstraintLayout btn_vincular;
    private ConstraintLayout clRecorrido;
    private ConstraintLayout clInfo;
    private ConstraintLayout btn_cerrar_sesion;
    private String access_token;
    //logica
    private LogicaNegocioUsarios logicaNegocioUsarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //get access_token from signed user
        //coockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);

        //si ya ha iniciado sesion
        access_token = (shared.getString("access_token", null));

        //init logica
        logicaNegocioUsarios = new LogicaNegocioUsarios();

        clRecorrido = v.findViewById(R.id.constraintLayout3);
        clInfo = v.findViewById(R.id.c5);
        btn_vincular = v.findViewById(R.id.profile_btn_vincular);
        btn_cerrar_sesion = v.findViewById(R.id.profile_btn_cerrar_sesion);

        //onclicks
        btn_vincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), VincularDispositivoActivity.class);
                startActivity(i);
            }
        });

        clRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), RecorridoActivity.class);
                startActivity(i);
            }
        });

        clInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditUserActivity.class);
                startActivity(i);
            }
        });

        //cerrar sesion
        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrar alerta
                initAlertDialog();
            }
        });

        return v;
    }

    /**
     * La descripción de initAlertDialog. Funcion que inicializa una alerta para decirle al usario
     * si quiere hacer una accion o no
     *
     */
    private void initAlertDialog(){
        MaterialAlertDialogBuilder alertDialogBuilder= new MaterialAlertDialogBuilder(getContext());
        alertDialogBuilder.setMessage(R.string.deleteText_dialog);
        //alertDialogBuilder.setMessage("");
        alertDialogBuilder.setNegativeButton(R.string.cancelButton_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setPositiveButton(R.string.deleteConfirmButton_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cerrarSesionUser();
            }
        });
        alertDialogBuilder.show();
    }

    /**
     * La descripción de cerrarSesionUser. Funcion que cierra sesion del user , llamando al metodo de
     * la logica que realiza lo anterior dicho
     *
     */
    private void cerrarSesionUser(){
        //llamar al metodo de la LogicaNegocioUsarios
        logicaNegocioUsarios.CerrarSesion(access_token, new LogicaNegocioUsarios.CerrarSesionCallback() {
            @Override
            public void onCompletedCerrarSesion(String message) {
                Log.d("pepe", " Cerrar sesion COMPLETED  -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + message);
                //Si se limpian todas las preferencias (datos guardados del usario que ha iniciado sesion)
                // devuelve true
                if(clearSharedPreferences()){

                    //reiniciar aplicacion
                    ProcessPhoenix.triggerRebirth(getContext());
                }

            }

            @Override
            public void onFailedCerrarSesion(boolean resultado) {
                Log.d("pepe", " Cerrar sesion FAILED -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + resultado+"");
            }
        });
    }

    private boolean clearSharedPreferences(){
        //coockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();

        Log.d("pepe", " clearSharedPreferences COMPLETED -------------------------------------  ");
        Log.d("pepe", "  CUERPO -> GUCCI");

        return true;
    }
}