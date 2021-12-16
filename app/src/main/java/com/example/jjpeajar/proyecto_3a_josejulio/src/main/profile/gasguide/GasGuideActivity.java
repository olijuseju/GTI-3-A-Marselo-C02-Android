package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide;

/**
 * @author Belén Grande López
 * GasGuideActivity
 * 2021-11-16
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegociaGases;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide.adapter.GasGuideAdapter;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Gas;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.GasController;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GasGuideActivity extends AppCompatActivity {

    //atributos
    private RecyclerView rv_gases;
    private ConstraintLayout btn_back;
    //lista de gases
    private List<Gas> gases = new ArrayList<Gas>();

    //adapter
    private GasGuideAdapter gasGuideAdapter;

    //SharedPreferences
    private SharedPreferences shared;
    private String access_token;
    //logica
    LogicaNegociaGases logicaNegociaGases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //coockies
        shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        //si ya ha iniciado sesion
        access_token = (shared.getString("access_token", null));

        //setLayout
        setContentView(R.layout.activity_gas_guide);

        //findById
        rv_gases = findViewById(R.id.rv_gases);
        btn_back = findViewById(R.id.bt_back_gasguide);

        //init logica
        logicaNegociaGases = new LogicaNegociaGases();
        //config rv
        initRvGases();
        //get datos de la bd
        getItemRvGases();



        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRvGases(){
        //manejador para declarar la direccion de los items del rv
        rv_gases.setLayoutManager(new GridLayoutManager(this, 2));
        //defino que el rv no tenga fixed size
        rv_gases.setHasFixedSize(false);
        rv_gases.setNestedScrollingEnabled(false);
    }

    private void initAdapter(){
        //creo un adaptador pasandole los elementos al contructor
        gasGuideAdapter = new GasGuideAdapter(gases , this);
        //declaro que cual es el adaptador el rv
        rv_gases.setAdapter(gasGuideAdapter);
    }

    private void getItemRvGases(){
        //clear array
        gases.clear();
        logicaNegociaGases.obtenerGases(access_token, new LogicaNegociaGases.ObtenerGasesCallback() {
            @Override
            public void onCompletedObtenerGases(GasController gasController) {
                gases = gasController.getGases();
                Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                Log.d("pepeupdate", "  CUERPO ->" + gases.size()+"");
                initAdapter();
            }

            @Override
            public void onFailedObtenerGases(boolean res) {

            }
        });
    }
}