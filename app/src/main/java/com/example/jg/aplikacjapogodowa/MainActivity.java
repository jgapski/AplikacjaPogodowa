package com.example.jg.aplikacjapogodowa;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jg.aplikacjapogodowa.JSON.JSONModel;
import com.example.jg.aplikacjapogodowa.Klient.GetData;
import com.example.jg.aplikacjapogodowa.Layout.AdditionalBar;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.io.IOException;


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

    Boolean containerFlag;

    JSONModel Data = new JSONModel();


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

        containerFlag = true;

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


            loadingContainer.setVisibility(View.GONE);

            try {

                JSONObject city = response.getJSONObject("city");
                String name = city.getString("name");
                cityTxt.setText(name);

                JSONArray list = response.getJSONArray("list");
                Data.arrayList(list);

                setModel(0);

                adapter = new AdditionalBar(getApplicationContext(), R.layout.bar, Data.weekDay, Data.tempDay, Data.iconWeather);
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


    public void setModel(Integer position){

        cloudsTxt.setText(Data.clouds.get(position));
        humidityTxt.setText(Data.humidity.get(position));
        pressureTxt.setText(Data.pressure.get(position));
        speedTxt.setText(Data.speed.get(position));
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

        if (CurrenLocation ){
            GetData client = new GetData();
            // client.getWeather("50.08", "19.92", null, handler);
            try {
                client.getWeather(client.getCityName(getApplicationContext(),location.getLatitude(),location.getLongitude()), null, handler);
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