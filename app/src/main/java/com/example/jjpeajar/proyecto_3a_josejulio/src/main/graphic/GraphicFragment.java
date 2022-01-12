package com.example.jjpeajar.proyecto_3a_josejulio.src.main.graphic;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioMediciones;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrey Kuzmin
 * GraphicFragment
 * 2021-11-19
 */

public class GraphicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //contructor
    public GraphicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphicFragment newInstance(String param1, String param2) {
        GraphicFragment fragment = new GraphicFragment();
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
    private GraphView graphView;
    private String access_token;

    List<Medicion> medicionesDeCalidad = new ArrayList<>();
    List<Medicion> medicionesDeTemperatura = new ArrayList<>();
    List<Medicion> medicionesDeHumedad = new ArrayList<>();

    //btn
    private Button btn_calidadAire;
    private Button btn_temp;
    private Button btn_hum;

    //txt
    private TextView txt_nivel;

    //Logica
    LogicaNegocioMediciones logicaNegocioMediciones = new LogicaNegocioMediciones();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get access_token from signed user
        //cockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);

        //si ya ha iniciado sesion
        access_token = shared.getString("access_token", null);

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_graphic, container, false);

        //findById
        graphView = v.findViewById(R.id.fragment_graphic_grafica);
        btn_calidadAire = v.findViewById(R.id.fragment_graphic_btn_calidad);
        btn_temp = v.findViewById(R.id.fragment_graphic_btn_temp);
        btn_hum = v.findViewById(R.id.fragment_graphic_btn_hum);
        txt_nivel= v.findViewById(R.id.fragment_graphic_txt_nivel);

        //series de puntos para las graficas
        //Creamos series
        LineGraphSeries<DataPoint> series =
                new LineGraphSeries<>();
        series.setColor(Color.BLUE);

        //onclick
        getMedicionesCalidadAireDelUser(series);

        btn_calidadAire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cambiamos txt
                txt_nivel.setText("Nivel de C.aire de las últimas 20 mediciones");
                //Creamos series
                LineGraphSeries<DataPoint> series =
                        new LineGraphSeries<>();
                series.setColor(Color.BLUE);

                getMedicionesCalidadAireDelUser(series);
            }
        });

        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cambiamos txt
                txt_nivel.setText("Nivel de temperatura de las últimas 20 mediciones");

                LineGraphSeries<DataPoint> seriesTemp =
                        new LineGraphSeries<>();
                seriesTemp.setColor(Color.CYAN);
                getMedicionesTemperaturaDelUser(seriesTemp);
            }
        });

        btn_hum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cambiamos txt
                txt_nivel.setText("Nivel de humedad de las últimas 20 mediciones");

                LineGraphSeries<DataPoint> seriesHum =
                        new LineGraphSeries<>();
                seriesHum.setColor(Color.MAGENTA);
                getMedicionesHumedadDelUser(seriesHum);
            }
        });

        return v;
    }

    private void getMedicionesCalidadAireDelUser(LineGraphSeries<DataPoint> series){
        if(access_token != null){
            //sacar todas las mediciones de cada tipo
            logicaNegocioMediciones.obtenerMedicionesByIdUser(access_token, new LogicaNegocioMediciones.ObtenerMedicionesByIdUserCallback() {
                @Override
                public void onCompletedObtenerMediasDeMedicionesDiarias(double calidadAire, double temp, double hum) {

                }

                @Override
                public void onCompletedObtenerMedicionesDiarias(List<Medicion> mediciones) {

                }

                @Override
                public void onCompletedObtenerMedicionesVacio(boolean res) {

                }

                @Override
                public void onFailedObtenerMedicionesByIdUser(boolean res) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeCalidadAire(List<Medicion> medicionesCalidadAire) {
                    if(medicionesCalidadAire.size() != 0){

                        initGrafica(medicionesCalidadAire , series);
                    }else{
                        txt_nivel.setText("Sin datos de C.aire");
                        graphView.removeAllSeries();
                    }
                }

                @Override
                public void onCompletedObtenerMedicionesDeTemperatura(List<Medicion> medicionesTemperatura) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeHumedad(List<Medicion> medicionesHumedad) {


                }
            });

        }
    }

    private void getMedicionesTemperaturaDelUser(LineGraphSeries<DataPoint> series){
        if(access_token != null){
            //sacar todas las mediciones de cada tipo
            logicaNegocioMediciones.obtenerMedicionesByIdUser(access_token, new LogicaNegocioMediciones.ObtenerMedicionesByIdUserCallback() {
                @Override
                public void onCompletedObtenerMediasDeMedicionesDiarias(double calidadAire, double temp, double hum) {

                }

                @Override
                public void onCompletedObtenerMedicionesDiarias(List<Medicion> mediciones) {

                }

                @Override
                public void onCompletedObtenerMedicionesVacio(boolean res) {

                }

                @Override
                public void onFailedObtenerMedicionesByIdUser(boolean res) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeCalidadAire(List<Medicion> medicionesCalidadAire) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeTemperatura(List<Medicion> medicionesTemperatura) {
                    if(medicionesTemperatura.size() != 0){

                        initGrafica(medicionesTemperatura , series);
                    }else{
                        txt_nivel.setText("Sin datos de temperatura");
                        graphView.removeAllSeries();
                    }
                }

                @Override
                public void onCompletedObtenerMedicionesDeHumedad(List<Medicion> medicionesHumedad) {


                }
            });

        }
    }

    private void getMedicionesHumedadDelUser(LineGraphSeries<DataPoint> series){
        if(access_token != null){
            //sacar todas las mediciones de cada tipo
            logicaNegocioMediciones.obtenerMedicionesByIdUser(access_token, new LogicaNegocioMediciones.ObtenerMedicionesByIdUserCallback() {
                @Override
                public void onCompletedObtenerMediasDeMedicionesDiarias(double calidadAire, double temp, double hum) {

                }

                @Override
                public void onCompletedObtenerMedicionesDiarias(List<Medicion> mediciones) {

                }

                @Override
                public void onCompletedObtenerMedicionesVacio(boolean res) {

                }

                @Override
                public void onFailedObtenerMedicionesByIdUser(boolean res) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeCalidadAire(List<Medicion> medicionesCalidadAire) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeTemperatura(List<Medicion> medicionesTemperatura) {

                }

                @Override
                public void onCompletedObtenerMedicionesDeHumedad(List<Medicion> medicionesHumedad) {
                    if(medicionesHumedad.size() != 0){

                        initGrafica(medicionesHumedad , series);
                    }else{
                        txt_nivel.setText("Sin datos de humedad");
                        graphView.removeAllSeries();
                    }

                }
            });

        }
    }



    private void initGrafica(List<Medicion> mediciones, LineGraphSeries<DataPoint> series){

        //limpiar todas los datos de la grafica
        graphView.removeAllSeries();
        int cont = 0;
        double maxValor = 0;
        String[] fechas = new String[20];

        //filtro array

        Log.d("pepe", "initGrafica() array size -> " + mediciones.size());

        //invertir una lista
        Collections.reverse(mediciones);

        Log.d("pepe", "initGrafica() ultimo index lista -> " + mediciones.get(0).getValue() )  ;

        //recorre la lista
        for (Medicion medicion: mediciones){

            series.appendData(new DataPoint(cont , medicion.getValue()) , true , mediciones.size() );



            if(cont <= 20){

                String[] parts = medicion.getDate().split("-");
                String mes = parts[1]; //mes
                String dia = parts[2]; //dia

                Log.d("pepe", "initGrafica() array size -> " + mes + "-" + dia);

                fechas[cont] = mes+"-"+dia;
            }

            if(medicion.getValue() >= maxValor){
                maxValor = medicion.getValue();
            }


            cont++;

            //Estética de la grafica
            StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(graphView);
            staticLabelsFormatter1.setHorizontalLabels(fechas);
            graphView.getGridLabelRenderer().setTextSize(30f);
            graphView.getGridLabelRenderer().reloadStyles();
            graphView.getGridLabelRenderer().setHorizontalLabelsAngle(120);
            graphView.getGridLabelRenderer().setLabelHorizontalHeight(90);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);
            //graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(0);
            graphView.getViewport().setMaxY(maxValor);
            graphView.addSeries(series);
        }


    }
}