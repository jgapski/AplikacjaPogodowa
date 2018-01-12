package com.example.jg.aplikacjapogodowa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        /*
            intent.putExtra(PRESSURE, pressure5days);
            intent.putExtra(CLOUDS, clouds5days);
            intent.putExtra(HUMIDITY, humidity5days);
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Intent intent = getIntent();
        days = intent.getCharSequenceArrayExtra(MainActivity.DATE);

        displayTitle(intent);
        displayGraphTemp(intent);
        displayGraphPressWindHum(intent);
    }

    private void displayTitle(Intent intent) {
        String cityName = intent.getStringExtra(MainActivity.CITY);
        TextView title = findViewById(R.id.title);
        title.setText(cityName + " - prognoza godzinowa");
    }

    private void displayGraphTemp(Intent intent) {
        double[] temp = intent.getDoubleArrayExtra(MainActivity.TEMPERATURE);
        double[] tempMax = intent.getDoubleArrayExtra(MainActivity.TEMP_MAX);
        double[] tempMin = intent.getDoubleArrayExtra(MainActivity.TEMP_MIN);

        GraphView graph = findViewById(R.id.graph_temp);
        LabelFormatter formatter = new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) return "+" + super.formatLabel(value, isValueX) + "h";
                else return super.formatLabel(value, isValueX) + "ºC";
            }
        };
        configureGraph(graph, formatter, 0, days.length,
                arrayMin(tempMin)-5, arrayMax(tempMax)+5);

        LineGraphSeries<DataPoint> tempSeries = arrayToSeries(temp);
        LineGraphSeries<DataPoint> tempMaxSeries = arrayToSeries(tempMax);
        LineGraphSeries<DataPoint> tempMinSeries = arrayToSeries(tempMin);

        graph.addSeries(tempMaxSeries);
        graph.addSeries(tempMinSeries);
        graph.addSeries(tempSeries);

        tempSeries.setTitle("Temperatura średnia");
        tempMaxSeries.setTitle("Temperatura maks.");
        tempMinSeries.setTitle("Temperatura min.");

        tempSeries.setColor(Color.GREEN);
        tempMaxSeries.setColor(Color.RED);
        tempMinSeries.setColor(Color.BLUE);
    }

    private void displayGraphPressWindHum(Intent intent) {
        double[] pressure = intent.getDoubleArrayExtra(MainActivity.PRESSURE);
        int[] humidity = intent.getIntArrayExtra(MainActivity.HUMIDITY);
        int[] clouds = intent.getIntArrayExtra(MainActivity.CLOUDS);

        GraphView graph = findViewById(R.id.graph_press_wind_hum);

        LabelFormatter formatter = new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) return "+" + super.formatLabel(value, isValueX) + "h";
                else return super.formatLabel(value, isValueX) + "hPa";
            }
        };
        configureGraph(graph, formatter, 0, days.length,
                arrayMin(pressure)-5, arrayMax(pressure)+5);

        LineGraphSeries<DataPoint> pressureSeries = arrayToSeries(pressure);
        LineGraphSeries<DataPoint> humiditySeries = arrayToSeries(humidity);
        LineGraphSeries<DataPoint> cloudsSeries = arrayToSeries(clouds);

        graph.addSeries(pressureSeries);
        graph.getSecondScale().addSeries(humiditySeries);
        graph.getSecondScale().addSeries(cloudsSeries);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(100);

        pressureSeries.setTitle("Ciśnienie");
        humiditySeries.setTitle("Wilgotność");
        cloudsSeries.setTitle("Zachmurzenie");

        pressureSeries.setColor(Color.YELLOW);
        humiditySeries.setColor(Color.BLUE);
        cloudsSeries.setColor(Color.GRAY);
    }

    private LineGraphSeries<DataPoint> arrayToSeries(double[] array) {
        DataPoint[] points = new DataPoint[array.length];

        for (int i = 0; i < array.length; i++)
            points[i] = new DataPoint(i*3, array[i]);

        return new LineGraphSeries<>(points);
    }

    private LineGraphSeries<DataPoint> arrayToSeries(int[] array) {
        DataPoint[] points = new DataPoint[array.length];

        for (int i = 0; i < array.length; i++)
            points[i] = new DataPoint(i*3, array[i]);

        return new LineGraphSeries<>(points);
    }

    private double arrayMax(double[] arr) {
        double max = Double.NEGATIVE_INFINITY;

        for(double cur: arr)
            max = Math.max(max, cur);

        return max;
    }

    private double arrayMin(double[] arr) {
        double min = Double.POSITIVE_INFINITY;

        for(double cur: arr)
            min = Math.min(min, cur);

        return min;
    }

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

//        // activate horizontal and vertical zooming and scrolling
//        graph.getViewport().setScalableY(true);
//
//        // activate vertical scrolling
//        graph.getViewport().setScrollableY(true);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        //////////////

        // set date label formatter
//        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
//        graph.getGridLabelRenderer().setHumanRounding(false);

        // custom label formatter to show currency "EUR"
        graph.getGridLabelRenderer().setLabelFormatter(formatter);
    }


}
