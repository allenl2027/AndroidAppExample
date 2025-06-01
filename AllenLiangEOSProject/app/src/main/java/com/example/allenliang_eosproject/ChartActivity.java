package com.example.allenliang_eosproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = findViewById(R.id.barChart_view);

        // Parse CSV and get averages
        Map<String, Float> averages = getAverageExamScoresByLearningStyle(this);

        // Prepare data entries and labels
        ArrayList<BarEntry> entries = getBarEntries(averages);
        ArrayList<String> labels = getLearningStyles(averages);

        // Setup BarDataSet and BarData
        BarDataSet dataSet = new BarDataSet(entries, "Avg Exam Score by Learning Style");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Configure X-axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getAxisRight().setEnabled(false); // Optional: disable right Y-axis
        barChart.getDescription().setEnabled(false); // Optional: remove description

        barChart.invalidate(); // refresh
    }

    private ArrayList<BarEntry> getBarEntries(Map<String, Float> averages) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Float> entry : averages.entrySet()) {
            entries.add(new BarEntry(i++, entry.getValue()));
        }
        return entries;
    }
    private ArrayList<String> getLearningStyles(Map<String, Float> averages) {
        return new ArrayList<>(averages.keySet());
    }
    private Map<String, Float> getAverageExamScoresByLearningStyle(Context context) {
        Map<String, Float> averages = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Float> totals = new HashMap<>();

        try {
            InputStream is = context.getAssets().open("students.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            reader.readNext(); // Skip header

            while ((nextLine = reader.readNext()) != null) {
                String style = nextLine[4]; // Preferred Learning Style column
                float score = Float.parseFloat(nextLine[8]); // Exam Score (%) column

                totals.put(style, totals.getOrDefault(style, 0f) + score);
                counts.put(style, counts.getOrDefault(style, 0) + 1);
            }
            reader.close();

            for (String style : totals.keySet()) {
                averages.put(style, totals.get(style) / counts.get(style));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return averages;
    }
}