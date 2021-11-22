package com.example.jjpeajar.proyecto_3a_josejulio.src.main.route;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class RecorridoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido);

        GraphView graphView = (GraphView) findViewById(R.id.gr);
        GraphView graphView2 = (GraphView) findViewById(R.id.gr2);
        GraphView graphView3 = (GraphView) findViewById(R.id.gr3);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        graphView2.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView2.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        graphView3.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView3.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        LineGraphSeries<DataPoint> series =
                new LineGraphSeries<>();
        series.setColor(Color.BLUE);


        double y;
        for (int i =0; i<40; i++){
            y = (int) (Math.random() * 8) + 1;
            series.appendData(new DataPoint(i,y), true, 90);
            graphView.addSeries(series);
        }


        LineGraphSeries<DataPoint> series1 =
                new LineGraphSeries<>();
        series1.setColor(Color.parseColor("#F1CA7E"));


        double y2;
        for (int i =0; i<40; i++){
            y2 = (int) (Math.random() * 8) + 1;
            series1.appendData(new DataPoint(i,y2), true, 90);
            graphView2.addSeries(series1);
        }


        LineGraphSeries<DataPoint> series2 =
                new LineGraphSeries<>();
        series2.setColor(Color.RED);


        double y1;
        for (int i =0; i<40; i++){
            y1 = (int) (Math.random() * 8) + 1;
            series2.appendData(new DataPoint(i,y1), true, 90);
            graphView3.addSeries(series2);
        }

    }
}