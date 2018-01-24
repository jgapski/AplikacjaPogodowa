package com.example.jg.aplikacjapogodowa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PlotActivity extends AppCompatActivity {

    private CharSequence[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Intent intent = getIntent();
        days = intent.getCharSequenceArrayExtra(MainActivity.DATE);

        displayTitle(intent);
        displayGraphTempHum(intent);
        displayGraphPressCloud(intent);
        displayGraphWind(intent);
    }

    /**
     * Prints the title containing the name of the city at the top of the screen.
     */
    private void displayTitle(Intent intent) {
        String cityName = intent.getStringExtra(MainActivity.CITY);
        TextView title = findViewById(R.id.title);
        title.setText(cityName + " - prognoza godzinowa");
    }

    /**
     * Displays two plots on the single graph using the data from two arrays.
     * @param titleL Title of the first plot.
     * @param titleR Title of the second plot.
     * @param fL Label formatter for the axis of the first plot.
     * @param fR Label formatter for the axis of the second plot.
     * @param dataL Data defining the first plot.
     * @param dataR Data defining the second plot.
     * @param colorL Color of the first plot.
     * @param colorR Color of the second plot.
     * @param minL Smallest possible value of the first plot.
     * @param maxL Greatest possible value of the first plot.
     * @param minR Smallest possible value of the second plot.
     * @param maxR Greatest possible value of the second plot.
     * @param graph Reference to the view component.
     */
    private void displayGraph(String titleL, String titleR, LabelFormatter fL, LabelFormatter fR,
                              double[] dataL, double[] dataR, int colorL, int colorR,
                              double minL, double maxL, double minR, double maxR, GraphView graph) {
        configureGraph(graph, fL, 0, days.length, getMin(dataL, minL), getMax(dataL, maxL));

        graph.getSecondScale().setLabelFormatter(fR);
        graph.getSecondScale().setMinY(getMin(dataR, minR));
        graph.getSecondScale().setMaxY(getMax(dataR, maxR));

        LineGraphSeries<DataPoint> seriesL = arrayToSeries(dataL);
        LineGraphSeries<DataPoint> seriesR = arrayToSeries(dataR);

        graph.addSeries(seriesL);
        graph.getSecondScale().addSeries(seriesR);

        seriesL.setTitle(titleL);
        seriesR.setTitle(titleR);

        seriesL.setColor(colorL);
        seriesR.setColor(colorR);
    }

    /**
     * Returns the smallest value from the array minus some constant value.
     */
    private int getMin(double[] data, double min) {
        return (int) Math.max(Math.round(arrayMin(data)) - 2, min);
    }

    /**
     * Returns the greatest value from the array plus some constant value.
     */
    private int getMax(double[] data, double max) {
        return (int) Math.min(Math.round(arrayMax(data)) + 2, max);
    }

    /**
     * Displays the graph containing temperature and air humidity plots.
     */
    private void displayGraphTempHum(Intent intent) {
        double[] temp = intent.getDoubleArrayExtra(MainActivity.TEMPERATURE);
        double[] humidity = intent.getDoubleArrayExtra(MainActivity.HUMIDITY);
        GraphView graph = findViewById(R.id.graph_temp_hum);
        LabelFormatter leftLF = createFormatter("ºC", "+", "h");
        LabelFormatter rightLF = createFormatter("%");

        displayGraph("Temperatura [ºC]", "Wilgotność [%]", leftLF, rightLF, temp, humidity,
                Color.GREEN, Color.BLUE, -100, 100, 0, 100, graph);
    }

    /**
     * Displays the graph containing air pressure and cloudiness plots.
     */
    private void displayGraphPressCloud(Intent intent) {
        double[] pressure = intent.getDoubleArrayExtra(MainActivity.PRESSURE);
        double[] clouds = intent.getDoubleArrayExtra(MainActivity.CLOUDS);
        GraphView graph = findViewById(R.id.graph_press_cloud);
        LabelFormatter leftLF = createFormatter("hPa", "+", "h");
        LabelFormatter rightLF = createFormatter("%");

        displayGraph("Ciśnienie [hPa]", "Zachmurzenie [%]", leftLF, rightLF, pressure, clouds,
                Color.YELLOW, Color.GRAY, 0, 1500, 0, 100, graph);
    }

    /**
     * Displays the graph containing wind data plots.
     */
    private void displayGraphWind(Intent intent) {
        double[] wind_speed = intent.getDoubleArrayExtra(MainActivity.WIND_SPEED);
        double[] wind_direction = intent.getDoubleArrayExtra(MainActivity.WIND_DIRECTION);
        GraphView graph = findViewById(R.id.graph_wind);
        LabelFormatter leftLF = createFormatter("m/s", "+", "h");
        LabelFormatter rightLF = createFormatter("º");

        displayGraph("Szybkość [m/s]", "Kierunek [º]", leftLF, rightLF, wind_speed, wind_direction,
                Color.RED, Color.MAGENTA, 0, 1000, 0, 359, graph);
    }

    private LineGraphSeries<DataPoint> arrayToSeries(double[] array) {
        DataPoint[] points = new DataPoint[array.length];

        for (int i = 0; i < array.length; i++)
            points[i] = new DataPoint(i*3, array[i]);

        return new LineGraphSeries<>(points);
    }

    /**
     * Finds the minimum value of the array.
     */
    private double arrayMax(double[] arr) {
        double max = Double.NEGATIVE_INFINITY;

        for(double cur: arr)
            max = Math.max(max, cur);

        return max;
    }

    /**
     * Finds the maximum value of the array.
     */
    private double arrayMin(double[] arr) {
        double min = Double.POSITIVE_INFINITY;

        for(double cur: arr)
            min = Math.min(min, cur);

        return min;
    }

    /**
     * Applies some configuration parameters to the graph.
     */
    private void configureGraph(GraphView graph, LabelFormatter formatter,
                                double minX, double maxX, double minY, double maxY) {
        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(minY);
        graph.getViewport().setMaxY(maxY);

        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);

        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);

        // display legend at the top-right corner
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setLabelFormatter(formatter);
    }

    private LabelFormatter createFormatter(final String vertical) {
        return createFormatter(vertical, "", "");
    }

    /**
     * Creates formatter changing values displayed at the axis.
     */
    private LabelFormatter createFormatter(final String vertical, final String horizontalBefore,
                                           final String horizontalAfter) {
        return new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX)
                    return horizontalBefore + super.formatLabel(value, isValueX) + horizontalAfter;
                else
                    return super.formatLabel(value, isValueX) + vertical;
            }
        };
    }

}
