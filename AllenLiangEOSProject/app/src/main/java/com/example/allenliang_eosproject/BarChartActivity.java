package com.example.allenliang_eosproject;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private HashMap<Student, Integer> studentRankMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.barChart);
        studentRankMap = CsvParser.parseStudents(this);

        setupBarChart();
    }

    private void setupBarChart() {
        // Calculate average exam score by learning style
        Map<String, List<Integer>> scoresByStyle = new HashMap<>();
        String[] learningStyles = {"Visual", "Auditory", "Reading/Writing", "Kinesthetic"};

        for (String style : learningStyles) {
            scoresByStyle.put(style, new ArrayList<>());
        }

        for (Student s : studentRankMap.keySet()) {
            String style = s.getPreferredLearningStyle();
            if (scoresByStyle.containsKey(style)) {
                scoresByStyle.get(style).add(s.getExamScore());
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;

        for (String style : learningStyles) {
            List<Integer> scores = scoresByStyle.get(style);
            double avg = 0;
            if (scores != null && !scores.isEmpty()) {
                int sum = 0;
                for (int score : scores) sum += score;
                avg = (double) sum / scores.size();
            }
            entries.add(new BarEntry(index, (float) avg));
            labels.add(style);
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Average Exam Score by Learning Style");
        dataSet.setColors(new int[] {
                Color.rgb(255, 99, 132),  // Visual - Red
                Color.rgb(54, 162, 235),  // Auditory - Blue
                Color.rgb(255, 206, 86),  // Reading/Writing - Yellow
                Color.rgb(75, 192, 192)   // Kinesthetic - Teal
        });
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        // Customize chart
        Description desc = new Description();
        desc.setText("Average Exam Scores by Learning Style");
        barChart.setDescription(desc);

        // X-axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter((value, axis) -> {
            if (value >= 0 && value < labels.size()) {
                return labels.get((int) value);
            } else {
                return "";
            }
        });

        // Y-axis
        YAxis left = barChart.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setAxisMaximum(100f);
        barChart.getAxisRight().setEnabled(false);

        // Legend
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

        barChart.animateY(1500);
        barChart.invalidate();
    }
}

