package com.example.jjpeajar.proyecto_3a_josejulio.src.main.home;

/**
 * @author Andrey Kuzmin
 * HomeFragment
 * 2021-11-19
 */

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioMediciones;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu.MenuMainActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.GPSTracker;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
    private String access_token;
    private TextView fragment_home_btn_desconectar;
    private TextView txt_medicion_calidad_aire;
    private TextView txt_medicion_temp;
    private TextView txt_medicion_hum;

    private TextView txt_distancia_diaria;
    private TextView txt_pasos_diaria;
    private TextView txt_cal_diaria;
    //logicas
    private LogicaNegocioMediciones logicaNegocioMediciones = new LogicaNegocioMediciones();
    // para llamar al metodo de obtener mediciones cada cierto tiempo
    Handler handler = new Handler();
    private final int TIEMPO = 10000;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get access_token from signed user
        //cockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);

        //si ya ha iniciado sesion
        name_user = (shared.getString("user_name", null));
        access_token = shared.getString("access_token", null);

        //llamar al metodo al principio 1 vez , para que el resultado sea más rapido
        getUltimasMediciones();

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        //findById
        conect=v.findViewById(R.id.fragment_home_btn_conect);
        txt_bienvenida=v.findViewById(R.id.fragment_home_txt_bienvenida);
        fragment_home_btn_desconectar=v.findViewById(R.id.fragment_home_btn_desconectar);
        txt_medicion_calidad_aire=v.findViewById(R.id.txt_medicion_calidad_aire);
        txt_medicion_temp= v.findViewById(R.id.txt_medicion_temp);
        txt_medicion_hum= v.findViewById(R.id.txt_medicion_hum);
        txt_distancia_diaria = v.findViewById(R.id.distancia_diaria);
        txt_pasos_diaria = v.findViewById(R.id.pasos_diarios);
        txt_cal_diaria = v.findViewById(R.id.cal_diarias);

        //onclick
        conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //init GPS class
                GPSTracker gpsTracker=  new GPSTracker(getContext());

                //comprobar si tiene el gps activado
                if (!gpsTracker.getIsGPSTrackingEnabled()){
                    gpsTracker.showSettingsAlert(); //mostrar dialog para activar el GPS
                }else{  // si ya esta activado , llamar a la funcion
                    ((MenuMainActivity)getActivity()).botonBuscarNuestroDispositivoBTLEPulsado();
                }
            }
        });

        fragment_home_btn_desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamar a la funcion
                ((MenuMainActivity)getActivity()).botonDetenerBusquedaDispositivosBTLEPulsado();
            }
        });

        //set bienvenida text con el nombre del user
        String bienvenida= getText(R.string.txt_home_bienvenida) + " " + name_user;
        txt_bienvenida.setText(bienvenida);

        //obtener las ultimas mediciones
        //llamar al metodo cada 10 segundos
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //llamar al metodo
                getUltimasMediciones();

                handler.postDelayed(this , TIEMPO);

            }
        }, TIEMPO);

        return v;
    }

    /**
     * La descripción de estimacionCalidadAire. Funcion estima mediante una media de calidad de aire.
     *
     * @param media double , media de la medicion de calidad de aire que obtenemos
     * @return String , estimacion resultante -> Alta,buena,moderada,baja,mala
     */
    private String estimacionCalidadAire(double media){

        String estimacion = "Sin datos";

        //umbrales de la calidad de aire
        if(media <= 35){
            estimacion = "Buena";
        }else if(media > 35 && media <= 75) {
            estimacion = "Moderada";
        }else if(media > 75 && media <= 185){
            estimacion = "Baja";
        }else if(media > 185){
            estimacion = "Mala";
        }

        return estimacion;

    }
    /**
     * La descripción de getUltimasMediciones. Funcion que llama a la logica de mediciones para
     * obtener las ultimas mediciones de cada tipo
     *
     */
    private void getUltimasMediciones(){
        if(access_token != null){

            //llamar a la logica
            logicaNegocioMediciones.obtenerMedicionesByIdUser(access_token, new LogicaNegocioMediciones.ObtenerMedicionesByIdUserCallback() {
                @Override
                public void onCompletedObtenerMediasDeMedicionesDiarias(double calidadAire, double temp, double hum) {
                    Log.d("pepe", "getUltimasMediciones() RESULTADO GUCCI -> " + calidadAire);
                    //estimar la calidad de aire
                    String estimacionCalidadAire = estimacionCalidadAire(calidadAire);
                    //convertir resultado en string
                    String medicionTemp = String.valueOf(temp) +"°C";
                    String medicionHum = String.valueOf(hum) +"%";

                    // mostrar en la app
                    txt_medicion_calidad_aire.setText(estimacionCalidadAire);
                    txt_medicion_temp.setText(medicionTemp);
                    txt_medicion_hum.setText(medicionHum);

                }

                @Override
                public void onCompletedObtenerMedicionesDiarias(List<Medicion> mediciones) {
                    Log.d("pepe", " MEDICIONES DIARIAS SIZE -> " + mediciones.size());
                    //comprobamos que la lista no esta vacía
                    if(mediciones.size() != 0){
                        //le pasamos el array al metodo que calcula la distancia , pasos y cal.
                        obtenerActividadDiariaDelUser(mediciones);
                    }
                }

                @Override
                public void onCompletedObtenerMedicionesVacio(boolean res) {
                    Log.d("pepe", "getUltimasMediciones() RESULTADO VACIO -> " + res);

                    // mostrar en la app
                    String vacio = "Sin datos";
                    txt_medicion_calidad_aire.setText(vacio);
                    txt_medicion_temp.setText(vacio);
                    txt_medicion_hum.setText(vacio);
                }

                @Override
                public void onFailedObtenerMedicionesByIdUser(boolean res) {
                    Log.d("pepe", "getUltimasMediciones() RESULTADO FALLIDO -> " + res);
                }
            });

        }
    }

    /**
     * La descripción de obtenerActividadDiariaDelUser. Funcion que mediante la lista de mediciones del user calcula la
     *  distancia recorrida , los pasos y las cal.
     *
     * @param mediciones lista de mediciones diarias del usario
     */
    private void obtenerActividadDiariaDelUser(List<Medicion> mediciones){

        int distancia = 0;
        //Sacamos la distancia total
        for (int i = 0 ; i< mediciones.size() - 1 ;i++) {
            Location locationA = new Location("punto A");

            locationA.setLatitude(mediciones.get(i).getLatitude());
            locationA.setLongitude(mediciones.get(i).getLongitude());

            Location locationB = new Location("punto B");

            locationB.setLatitude(mediciones.get(i+1).getLatitude());
            locationB.setLongitude(mediciones.get(i+1).getLongitude());

            distancia += locationA.distanceTo(locationB);


        }

        double distanciaKm = 0.0;

        distanciaKm = (float) distancia/ 1000;
        // pum 2 decimales
        distanciaKm=Math.round(distanciaKm*100.0)/100.0;

        //Obtenemos pasos y kcal por medio de la distancia
        int pasos = (int) (distanciaKm*717.77203560149);
        int kcalorias = (int) (40*distanciaKm);

        //comprobamos si esta vacio
        // si hay datos convertimos en string
        if(distanciaKm <= 0){
            txt_distancia_diaria.setText("Sin datos");
        }else{
            String distanciaString = distanciaKm + " km";
            txt_distancia_diaria.setText(distanciaString);
        }
        if(pasos <= 0){
            txt_pasos_diaria.setText("Sin datos");
        }else{
            String pasosString = pasos + " pasos";
            txt_pasos_diaria.setText(pasosString);
        }
        if(kcalorias <= 0){
            txt_cal_diaria.setText("Sin datos");
        }else{
            String kcaloriasString = kcalorias + " cal";
            txt_cal_diaria.setText(kcaloriasString);
        }
    }
}