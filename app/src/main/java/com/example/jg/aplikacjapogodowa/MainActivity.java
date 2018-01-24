package com.example.jg.aplikacjapogodowa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jg.aplikacjapogodowa.JSON.JSONModel;
import com.example.jg.aplikacjapogodowa.JSON.JSONModel5Days;
import com.example.jg.aplikacjapogodowa.Klient.GetData;
import com.example.jg.aplikacjapogodowa.Layout.AdditionalBar;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private boolean network_enabled = false;
    Boolean CurrenLocation;
    TextView cityTxt, cloudsTxt, humidityTxt, pressureTxt, speedTxt, tempMaxTxt, tempMinTxt, mornTxt, dayTxt, nightTxt;
    TextView weatherTxt, dateText, tempText;
    ImageView iconImage;
    TwoWayView twoWayView;
    LinearLayout mainContainer, circleContainer_2;
    RelativeLayout mainView, circleContainer, loadingContainer;
    AdditionalBar adapter;
    Button butMore;

    Boolean containerFlag;

    JSONModel Data = new JSONModel();

    public static final String TEMPERATURE = "com.example.jg.aplikacjapogodowa.TEMPERATURE";
    public static final String CITY = "com.example.jg.aplikacjapogodowa.CITY";
    public static final String DATE = "com.example.jg.aplikacjapogodowa.DATE";
    public static final String PRESSURE = "com.example.jg.aplikacjapogodowa.PRESSURE";
    public static final String TEMP_MAX = "com.example.jg.aplikacjapogodowa.TEMP_MAX";
    public static final String TEMP_MIN = "com.example.jg.aplikacjapogodowa.TEMP_MIN";
    public static final String CLOUDS = "com.example.jg.aplikacjapogodowa.CLOUDS";
    public static final String HUMIDITY = "com.example.jg.aplikacjapogodowa.HUMIDITY";
    private int jsons_read = 0;
    private long[] date5days;
    private double[] temp5days;
    private double[] tempMax5days;
    private double[] tempMin5days;
    private int[] clouds5days;
    private double[] pressure5days;
    private int[] humidity5days;
    private CharSequence[] days5;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        cityTxt = findViewById(R.id.city);
        cloudsTxt = findViewById(R.id.clouds);
        humidityTxt = findViewById(R.id.humidity);
        pressureTxt = findViewById(R.id.pressure);
        speedTxt = findViewById(R.id.speed);
        tempMaxTxt = findViewById(R.id.tempMax);
        tempMinTxt = findViewById(R.id.tempMin);
        mornTxt = findViewById(R.id.tempMorn);
        dayTxt = findViewById(R.id.tempDay);
        nightTxt = findViewById(R.id.tempNight);
        weatherTxt = findViewById(R.id.weatherDescrip);
        iconImage = findViewById(R.id.weatherIcon);
        twoWayView = findViewById(R.id.lvItems);
        dateText = findViewById(R.id.dateText);
        tempText = findViewById(R.id.temp_A);
        mainContainer = findViewById(R.id.mainContainer);
        loadingContainer = findViewById(R.id.LoadingView);
        mainView = findViewById(R.id.mainView);
        circleContainer =  findViewById(R.id.circle);
        circleContainer_2 = findViewById(R.id.cicle_2);
        butMore = findViewById(R.id.but_more);

        containerFlag = true;

        butMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlotActivity.class);
                intent.putExtra(TEMPERATURE, temp5days);
                intent.putExtra(CITY, cityName);
                intent.putExtra(DATE, days5);
                intent.putExtra(TEMP_MAX, tempMax5days);
                intent.putExtra(TEMP_MIN, tempMin5days);
                intent.putExtra(PRESSURE, pressure5days);
                intent.putExtra(CLOUDS, clouds5days);
                intent.putExtra(HUMIDITY, humidity5days);
                startActivity(intent);
            }
        });

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (network_enabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }

        CurrenLocation = true;

    }

    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            ++jsons_read;

            if (jsons_read == 2)
                loadingContainer.setVisibility(View.GONE);

            try {

//                JSONObject city = response.getJSONObject("city");
//                String name = city.getString("name");
//                cityTxt.setText(name);
                cityTxt.setText(cityName);

                JSONArray list = response.getJSONArray("list");
                Data.arrayList(list);

                setModel(0);

                adapter = new AdditionalBar(getApplicationContext(), R.layout.bar, Data.weekDay, Data.tempMax,Data.tempMin, Data.iconWeather);
                twoWayView.setAdapter(adapter);
                twoWayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        setModel(position);
                    }
                });



            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    private JsonHttpResponseHandler handler5days = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            ++jsons_read;

            if (jsons_read == 2)
                loadingContainer.setVisibility(View.GONE);

            try {
                JSONArray list = response.getJSONArray("list");
                JSONModel5Days model5days = new JSONModel5Days();
                model5days.arrayList(list);

                date5days = new long[model5days.date.size()];
                for (int i = 0; i < model5days.date.size(); i++)
                    date5days[i] = model5days.date.get(i);

                days5 = new CharSequence[model5days.dateTxt.size()];
                for (int i = 0; i < model5days.dateTxt.size(); i++)
                    days5[i] = model5days.dateTxt.get(i);

                temp5days = listToArray(model5days.temp);
                clouds5days = listToArrayInt(model5days.clouds);
                tempMax5days = listToArray(model5days.tempMax);
                tempMin5days = listToArray(model5days.tempMin);
                pressure5days = listToArray(model5days.pressure);
                humidity5days = listToArrayInt(model5days.humidity);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    private double[] listToArray(List<Double> list) {
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++)
            arr[i] = list.get(i);
        return arr;
    }

    private int[] listToArrayInt(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            arr[i] = list.get(i);
        return arr;
    }

    public void setModel(Integer position){

        DateFormat df = new SimpleDateFormat("HH");
        int time = Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        RelativeLayout ll = findViewById(R.id.mainView);
        if (time < 4 && time > 20){
            ll.setBackgroundResource(R.drawable.tlonoc);
        }
        else{
            ll.setBackgroundResource(R.drawable.tlodzien);
        }

        String cloudsTxtString = Data.clouds.get(position) + "%";
        cloudsTxt.setText(cloudsTxtString);
        String humidityTxtString = Data.humidity.get(position) + "%";
        humidityTxt.setText(humidityTxtString);
        String pressureTxtString = Data.pressure.get(position) + "hpa";
        pressureTxt.setText(pressureTxtString);
        String speedTxtString = Data.speed.get(position) + "m/s";
        speedTxt.setText(speedTxtString);
        weatherTxt.setText(Data.descripWeather.get(position));
        dateText.setText(Data.weekDay.get(position));
        String dayTmpString = Data.tempDay.get(position) + "º";
        tempText.setText(dayTmpString);

        switch (Data.iconWeather.get(position)){
            case "01d":
                iconImage.setImageResource(R.drawable.d01);
                break;
            case "01n":
                iconImage.setImageResource(R.drawable.d01);
                break;
            case "02d":
                iconImage.setImageResource(R.drawable.d02);
                break;
            case "02n":
                iconImage.setImageResource(R.drawable.d02);
                break;
            case "03d":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "03n":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "04d":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "04n":
                iconImage.setImageResource(R.drawable.d03);
                break;
            case "09d":
                iconImage.setImageResource(R.drawable.d09);
                break;
            case "09n":
                iconImage.setImageResource(R.drawable.d09);
                break;
            case "10d":
                iconImage.setImageResource(R.drawable.d10);
                break;
            case "10n":
                iconImage.setImageResource(R.drawable.d10);
                break;
            case "11d":
                iconImage.setImageResource(R.drawable.d11);
                break;
            case "11n":
                iconImage.setImageResource(R.drawable.d11);
                break;
            case "13d":
                iconImage.setImageResource(R.drawable.d13);
                break;
            case "13n":
                iconImage.setImageResource(R.drawable.d13);
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        if (CurrenLocation) {
            GetData client = new GetData();
            // client.getWeather("50.08", "19.92", null, handler);
            try {
                cityName = client.getCityName(getApplicationContext(),
                        location.getLatitude(), location.getLongitude());
                client.getWeather(cityName, null, handler);
                client.getWeather5Days(cityName, null, handler5days);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CurrenLocation = false;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}