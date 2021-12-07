package com.example.bluetooth_tuto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Mygraphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygraphs);
        BarChart barChart =findViewById(R.id.barChart);
        ArrayList<BarEntry> conso =new ArrayList<>();
        conso.add(new BarEntry(1,2000));
        conso.add(new BarEntry(2,2200));
        conso.add(new BarEntry(3,2000));
        conso.add(new BarEntry(4,2000));
        conso.add(new BarEntry(5,3100));
        conso.add(new BarEntry(6,3300));
        conso.add(new BarEntry(7,3300));
        conso.add(new BarEntry(8,4300));
        conso.add(new BarEntry(9,3300));
        conso.add(new BarEntry(10,3100));
        conso.add(new BarEntry(11,2200));
        conso.add(new BarEntry(12,2500));

        BarDataSet barDataSet =new BarDataSet(conso,"Consomation mensuelle");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData=new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("mWh");
        barChart.animateY(5000);

    }
}
