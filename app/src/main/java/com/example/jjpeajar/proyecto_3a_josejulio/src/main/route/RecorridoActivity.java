package com.example.jjpeajar.proyecto_3a_josejulio.src.main.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class RecorridoActivity extends AppCompatActivity {

    private static final String[] WEEK_DAYS = {"", "Mon", "Tue", "Wed",  "Thu", "Fri", "Sat", "Sun" ,""};
    private static final String[] WEEKS_MONTH = {"","Sem 1", "Sem 2", "Sem 3",  "Sem 4", "Sem 5",""};
    private static final String[] MONTHS_YEAR = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido);

        MaterialCardView cardView1 = findViewById(R.id.cardV1);
        MaterialCardView cardView2 = findViewById(R.id.cardV2);
        MaterialCardView cardView3 = findViewById(R.id.cardV3);

        GraphView graphView = (GraphView) findViewById(R.id.gr);
        GraphView graphView2 = (GraphView) findViewById(R.id.gr2);
        GraphView graphView3 = (GraphView) findViewById(R.id.gr3);

        ConstraintLayout bt_back = findViewById(R.id.bt_back);

        LineGraphSeries<DataPoint> series =
                new LineGraphSeries<>();
        series.setColor(Color.BLUE);


        LineGraphSeries<DataPoint> series1 =
                new LineGraphSeries<>();
        series1.setColor(Color.parseColor("#F1CA7E"));



        LineGraphSeries<DataPoint> series2 =
                new LineGraphSeries<>();
        series2.setColor(Color.RED);


        inicializarGraficaCard(graphView, series);
        inicializarGraficaCard(graphView2, series1);
        inicializarGraficaCard(graphView3, series2);


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                initBottomSheet("Distancia");
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                initBottomSheet("Pasos");
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                initBottomSheet("Calorías");
            }
        });
    }


    void inicializarGraficaCard(GraphView graphView, LineGraphSeries<DataPoint> series){
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        double y;
        for (int i =0; i<40; i++){
            y = (int) (Math.random() * 8) + 1;
            series.appendData(new DataPoint(i,y), true, 90);
            graphView.addSeries(series);
        }
    } // ()


    private void initBottomSheet(String titulo){
        //set the bottom sheet
        BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(RecorridoActivity.this);
        //set the layout of the bottom sheet
        optionsBottomSheet.setContentView(R.layout.activity_recorrido_bottomsheet);
        //findbyid del bottom sheet
        GraphView graphViewBs = (GraphView) optionsBottomSheet.findViewById(R.id.graphviewBs);

        graphViewBs.getGridLabelRenderer().setTextSize(30f);
        graphViewBs.getGridLabelRenderer().reloadStyles();
        //findview

        ConstraintLayout constraintLayoutSem = optionsBottomSheet.findViewById(R.id.constraintLayoutSem);
        ConstraintLayout constraintLayoutMes = optionsBottomSheet.findViewById(R.id.constraintLayoutMes);
        ConstraintLayout constraintLayoutAnyo = optionsBottomSheet.findViewById(R.id.constraintLayoutAnyo);

        TextView txtitulo = optionsBottomSheet.findViewById(R.id.txtitulo);
        TextView txsem = optionsBottomSheet.findViewById(R.id.textViewSem);
        TextView txmes = optionsBottomSheet.findViewById(R.id.textViewMes);
        TextView txanyo = optionsBottomSheet.findViewById(R.id.textViewAnyo);

        //onclick methods
        constraintLayoutSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txsem.setTextColor(getResources().getColor(R.color.black));
                txmes.setTextColor(getResources().getColor(R.color.gris_dentro));
                txanyo.setTextColor(getResources().getColor(R.color.gris_dentro));
                inicializarGraficaBSSemanal(graphViewBs);
            }
        });

        constraintLayoutMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txsem.setTextColor(getResources().getColor(R.color.gris_dentro));
                txmes.setTextColor(getResources().getColor(R.color.black));
                txanyo.setTextColor(getResources().getColor(R.color.gris_dentro));
                inicializarGraficaBSMensual(graphViewBs);
            }
        });

        constraintLayoutAnyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txsem.setTextColor(getResources().getColor(R.color.gris_dentro));
                txmes.setTextColor(getResources().getColor(R.color.gris_dentro));
                txanyo.setTextColor(getResources().getColor(R.color.black));
                inicializarGraficaBSAnual(graphViewBs);
            }
        });

        txtitulo.setText(titulo);

        graphViewBs.getGridLabelRenderer().setHorizontalLabelsAngle(120);
        //Por defecto sale el mensual
        inicializarGraficaBSMensual(graphViewBs);

        //Show
        optionsBottomSheet.show();

    } // ()


    void inicializarGraficaBSSemanal(GraphView graphViewBs){

        graphViewBs.removeAllSeries();

        //Rellenar el graph semanal
        DataPoint[] graphViewData = new DataPoint[9];

        graphViewData[0] = new DataPoint(0, 0);
        graphViewData[8] = new DataPoint(8,0);
        for (int i = 1; i < 8; i++) {
            graphViewData[i] = new DataPoint(i, i+4);
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(graphViewData);
        series.setColor(getResources().getColor(R.color.gris_borde));

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphViewBs);
        staticLabelsFormatter.setHorizontalLabels(WEEK_DAYS);
        series.setSpacing(20);

        graphViewBs.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphViewBs.getViewport().setYAxisBoundsManual(true);
        graphViewBs.getViewport().setMinY(0);
        graphViewBs.getViewport().setMaxY(18);


        graphViewBs.addSeries(series);

    }

    void inicializarGraficaBSMensual(GraphView graphViewBs){
        //Rellenar el graph semanal
        graphViewBs.removeAllSeries();

        DataPoint[] graphViewDataMes = new DataPoint[7];


        graphViewDataMes[0] = new DataPoint(0, 0);
        graphViewDataMes[6] = new DataPoint(6,0);
        for (int i = 1; i < 6; i++) {
            graphViewDataMes[i] = new DataPoint(i, i+4);
        }

        BarGraphSeries<DataPoint> seriesmes = new BarGraphSeries<DataPoint>(graphViewDataMes);
        seriesmes.setColor(getResources().getColor(R.color.gris_borde));

        StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(graphViewBs);
        staticLabelsFormatter1.setHorizontalLabels(WEEKS_MONTH);
        seriesmes.setSpacing(20);


        graphViewBs.getViewport().setScrollable(true);
        graphViewBs.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);
        graphViewBs.getViewport().setYAxisBoundsManual(true);
        graphViewBs.getViewport().setMinY(0);
        graphViewBs.getViewport().setMaxY(18);
        graphViewBs.addSeries(seriesmes);
    }

    void inicializarGraficaBSAnual(GraphView graphViewBs){

        graphViewBs.removeAllSeries();

        //Rellenar el graph semanal
        DataPoint[] graphViewData = new DataPoint[14];

        graphViewData[0] = new DataPoint(0, 0);
        graphViewData[13] = new DataPoint(13,0);
        for (int i = 1; i < 13; i++) {
            graphViewData[i] = new DataPoint(i, i+5);
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(graphViewData);
        series.setColor(getResources().getColor(R.color.gris_borde));

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphViewBs);
        staticLabelsFormatter.setHorizontalLabels(MONTHS_YEAR);
        series.setSpacing(20);

        graphViewBs.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphViewBs.getViewport().setYAxisBoundsManual(true);
        graphViewBs.getViewport().setMinY(0);
        graphViewBs.getViewport().setMaxY(18);
        graphViewBs.addSeries(series);

    }
}