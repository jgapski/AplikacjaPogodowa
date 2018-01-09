package com.example.jg.aplikacjapogodowa.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jg on 06.01.18.
 */

public class JSONModel {

    public ArrayList <JSONObject> temp = new ArrayList<>();
    public ArrayList<String> clouds = new ArrayList<>();
    public ArrayList <String> humidity = new ArrayList<>();
    public ArrayList <String> pressure = new ArrayList<>();
    public ArrayList <String> speed = new ArrayList<>();
    public ArrayList <String> tempMax = new ArrayList<>();
    public ArrayList <String> tempMin = new ArrayList<>();
    public ArrayList <String> tempDay = new ArrayList<>();
    public ArrayList <String> tempMorn = new ArrayList<>();
    public ArrayList <String> tempNight = new ArrayList<>();
    public ArrayList <String> descripWeather = new ArrayList<>();
    public ArrayList <String> iconWeather = new ArrayList<>();
    public ArrayList <String> weekDay = new ArrayList<>();

    public  void  arrayList (JSONArray array) throws JSONException {

        clouds.clear();
        humidity.clear();
        pressure.clear();
        speed.clear();
        tempMax.clear();
        tempMin.clear();
        tempDay.clear();
        tempMorn.clear();
        tempNight.clear();
        descripWeather.clear();
        iconWeather.clear();
        weekDay.clear();

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            String cloudsString = object.getString("clouds");
            String humidityString = object.getString("humidity");
            String pressureString = object.getString("pressure");
            String speedString = object.getString("speed");
            JSONObject tempObject = object.getJSONObject("temp");
            String tempMaxString = tempObject.getString("max");
            String tempMinString = tempObject.getString("min");
            String tempDayString = tempObject.getString("day");
            String tempMornSring = tempObject.getString("morn");
            String tempNigthString = tempObject.getString("night");
            JSONArray weatherArray = object.getJSONArray("weather");
            JSONObject object1 = weatherArray.getJSONObject(0);
            String descriptionWeather = object1.getString("description");
            String iconString = object1.getString("icon");

            clouds.add(cloudsString );
            humidity.add(humidityString);
            pressure.add(pressureString);
            speed.add(speedString);
            temp.add(tempObject);
            tempMax.add(tempMaxString);
            tempMin.add(tempMinString);
            tempDay.add(tempDayString);
            tempMorn.add(tempMornSring);
            tempNight.add(tempNigthString);
            descripWeather.add(descriptionWeather);
            iconWeather.add(iconString);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String formattedDate = dayFormat.format(calendar.getTime());
            weekDay.add(formattedDate);
        }
    }
}
