package com.example.allenliang_eosproject;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private Button btnBack;
    private CSVDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = findViewById(R.id.barChart_view);
        btnBack = findViewById(R.id.btnBackToHome);
        dataManager = new CSVDataManager(this);

        setupChart();
        loadChartData();

        btnBack.setOnClickListener(v -> finish());
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(4);

        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
    }

    private void loadChartData() {
        HashMap<Student, Integer> studentsMap = dataManager.loadStudents();
        HashMap<String, Float> averages = dataManager.calculateAverageScoresByLearningStyle(studentsMap);

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        String[] learningStyles = {"Visual", "Auditory", "Reading/Writing", "Kinesthetic"};
        int[] styleColors = {
                Color.rgb(64, 89, 128),   // Visual - Blue
                Color.rgb(149, 165, 124), // Auditory - Green
                Color.rgb(217, 80, 138),  // Reading/Writing - Pink
                Color.rgb(254, 149, 7)    // Kinesthetic - Orange
        };

        for (int i = 0; i < learningStyles.length; i++) {
            String style = learningStyles[i];
            float avgScore = averages.containsKey(style) ? averages.get(style) : 0f;
            entries.add(new BarEntry(i, avgScore));
            labels.add(style);
            colors.add(styleColors[i]);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Average Exam Scores by Learning Style");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.invalidate();
        barChart.animateY(1400);
    }
}

