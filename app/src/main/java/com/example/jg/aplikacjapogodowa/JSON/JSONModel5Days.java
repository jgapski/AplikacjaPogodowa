package com.example.jg.aplikacjapogodowa.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class JSONModel5Days {

    public ArrayList<Double> temp = new ArrayList<>();
    public ArrayList<Double> clouds = new ArrayList<>();
    public ArrayList<Double> humidity = new ArrayList<>();
    public ArrayList<Double> pressure = new ArrayList<>();
    public ArrayList<String> weekDay = new ArrayList<>();
    public ArrayList<Long> date = new ArrayList<>();
    public ArrayList<String> dateTxt = new ArrayList<>();
    public ArrayList<Double> windSpeed = new ArrayList<>();
    public ArrayList<Double> windDirection = new ArrayList<>();

    public void arrayList(JSONArray array) throws JSONException {
        clouds.clear();
        humidity.clear();
        pressure.clear();
        weekDay.clear();
        date.clear();
        dateTxt.clear();
        windSpeed.clear();
        windDirection.clear();

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Double cloudsDouble = (double)object.getJSONObject("clouds").getInt("all");
            JSONObject mainObj = object.getJSONObject("main");
            Double humidityDouble = mainObj.getDouble("humidity");
            Double pressureDouble = mainObj.getDouble("pressure");
            Double tempDouble = mainObj.getDouble("temp");
            JSONObject wind = object.getJSONObject("wind");
            Double windSpeedDouble = wind.getDouble("speed");
            Double windDirectionDouble = wind.getDouble("deg");
            Long dateLong = object.getLong("dt");
            String dateString = object.getString("dt_txt");

            date.add(dateLong);
            clouds.add(cloudsDouble);
            humidity.add(humidityDouble);
            pressure.add(pressureDouble);
            windSpeed.add(windSpeedDouble);
            windDirection.add(windDirectionDouble);
            temp.add(tempDouble);
            dateTxt.add(dateString);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String formattedDate = dayFormat.format(calendar.getTime());
            weekDay.add(formattedDate);
        }
    }
}
