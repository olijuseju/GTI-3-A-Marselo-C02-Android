package com.example.jjpeajar.proyecto_3a_josejulio.src.main.home;

/**
 * @author Andrey Kuzmin
 * HomeFragment
 * 2021-11-19
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.service.ServicioEscuharBeacons;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    //atributtes
    private Button conect;
    private TextView txt_bienvenida;
    private String name_user;
    private TextView fragment_home_btn_desconectar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get access_token from signed user
        //coockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);

        //si ya ha iniciado sesion
        name_user = (shared.getString("user_name", null));

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        //findById
        conect=v.findViewById(R.id.fragment_home_btn_conect);
        txt_bienvenida=v.findViewById(R.id.fragment_home_txt_bienvenida);
        fragment_home_btn_desconectar=v.findViewById(R.id.fragment_home_btn_desconectar);

        //onclick
        conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuMainActivity)getActivity()).botonBuscarNuestroDispositivoBTLEPulsado();
            }
        });

        fragment_home_btn_desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuMainActivity)getActivity()).botonDetenerBusquedaDispositivosBTLEPulsado();
            }
        });

        //set bienvenida text
        String bienvenida= getText(R.string.txt_home_bienvenida) + " " + name_user;
        txt_bienvenida.setText(bienvenida);

        return v;
    }
}