package com.example.jg.aplikacjapogodowa.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class JSONModel5Days {

    public ArrayList <Double> temp = new ArrayList<>();
    public ArrayList<Integer> clouds = new ArrayList<>();
    public ArrayList <Integer> humidity = new ArrayList<>();
    public ArrayList <Double> pressure = new ArrayList<>();
    public ArrayList <Double> speed = new ArrayList<>();
    public ArrayList <Double> tempMax = new ArrayList<>();
    public ArrayList <Double> tempMin = new ArrayList<>();
    public ArrayList <String> weekDay = new ArrayList<>();
    public ArrayList<Long> date = new ArrayList<>();
    public ArrayList<String> dateTxt = new ArrayList<>();
    /**
     * converts JSON data from API (OpenWeatherMap) for 5 days to arrayList containing:
     * CLOUDS, HUMIDITY, PRESSURE, SPEED(wind), TEMPERATURE - maximum, minimum,
     * NAME of a WEEK DAY
     * @param array
     * @throws JSONException
     */
    public void arrayList(JSONArray array) throws JSONException {
        clouds.clear();
        humidity.clear();
        pressure.clear();
        speed.clear();
        tempMax.clear();
        tempMin.clear();
        weekDay.clear();
        date.clear();
        dateTxt.clear();

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Integer cloudsInt = object.getJSONObject("clouds").getInt("all");
            JSONObject mainObj = object.getJSONObject("main");
            Integer humidityInt = mainObj.getInt("humidity");
            Double pressureDouble = mainObj.getDouble("pressure");
            Double tempDouble = mainObj.getDouble("temp");
            Double tempMaxDouble = mainObj.getDouble("temp_max");
            Double tempMinDouble = mainObj.getDouble("temp_min");
            Double speedDouble = object.getJSONObject("wind").getDouble("speed");
            Long dateLong = object.getLong("dt");
            String dateString = object.getString("dt_txt");

            date.add(dateLong);
            clouds.add(cloudsInt);
            humidity.add(humidityInt);
            pressure.add(pressureDouble);
            speed.add(speedDouble);
            temp.add(tempDouble);
            tempMax.add(tempMaxDouble);
            tempMin.add(tempMinDouble);
            dateTxt.add(dateString);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String formattedDate = dayFormat.format(calendar.getTime());
            weekDay.add(formattedDate);
        }
    }
}
