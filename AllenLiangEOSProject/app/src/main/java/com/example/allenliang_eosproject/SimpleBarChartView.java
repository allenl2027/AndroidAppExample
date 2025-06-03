package com.example.allenliang_eosproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

public class SimpleBarChartView extends View {

    private Map<String, Float> data; // Map of label -> average score (0-100)
    private Paint barPaint;
    private Paint textPaint;
    private int[] barColors = {
            Color.rgb(255, 99, 132),   // Visual - Red
            Color.rgb(54, 162, 235),   // Auditory - Blue
            Color.rgb(255, 206, 86),   // Reading/Writing - Yellow
            Color.rgb(75, 192, 192)    // Kinesthetic - Teal
    };

    public SimpleBarChartView(Context context) {
        super(context);
        init();
    }

    public SimpleBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        barPaint = new Paint();
        barPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(Map<String, Float> data) {
        this.data = data;
        invalidate(); // redraw view
    }
    //Line 49-92 was written by Perplexity AI
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || data.isEmpty()) {
            canvas.drawText("No data to display", getWidth()/2f, getHeight()/2f, textPaint);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int padding = 100;
        int barWidth = (width - 2 * padding) / data.size();
        int maxBarHeight = height - 2 * padding;

        int i = 0;
        for (Map.Entry<String, Float> entry : data.entrySet()) {
            float avgScore = entry.getValue(); // 0-100
            String label = entry.getKey();

            // Calculate bar height proportional to avgScore
            float barHeight = (avgScore / 100f) * maxBarHeight;

            // Set bar color cycling through predefined colors
            barPaint.setColor(barColors[i % barColors.length]);

            // Calculate bar position
            float left = padding + i * barWidth + 20;
            float top = height - padding - barHeight;
            float right = left + barWidth - 40;
            float bottom = height - padding;

            // Draw bar
            canvas.drawRect(left, top, right, bottom, barPaint);

            // Draw label below bar
            canvas.drawText(label, left + (barWidth - 40) / 2f, height - padding + 40, textPaint);

            // Draw value above bar
            canvas.drawText(String.format("%.1f", avgScore), left + (barWidth - 40) / 2f, top - 20, textPaint);

            i++;
        }
    }
}
