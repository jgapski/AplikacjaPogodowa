package com.example.jg.aplikacjapogodowa.Klient;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jg on 06.01.18.
 */

public class GetData {

    static String URL;
        private static final String ID = "bd5e378503939ddaee76f12ad7a97608";

    public static void getWeather (String Latitude, String Longitude, RequestParams params, AsyncHttpResponseHandler handler){

        AsyncHttpClient newClient = new AsyncHttpClient();
        URL = "http://api.openweathermap.org/data/2.5/forecast/daily?"+"lat="+Latitude+"&lon="+Longitude+"&units=metric&lang=pl&appid="+ID;
        newClient.get(URL, params, handler);

    }
    public String getCityName(Context context, double lat, double lon) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat,lon, 1);
        return addresses.get(0).getLocality();
    }

    public static void getWeather(String city, RequestParams params, AsyncHttpResponseHandler handler){
        AsyncHttpClient newClient = new AsyncHttpClient();
        URL = "http://api.openweathermap.org/data/2.5/forecast/daily?"+"q="+city+"&units=metric&lang=pl&appid="+ID;
        newClient.get(URL, params, handler);
    }

    public static void getWeather5Days (String city, RequestParams params, AsyncHttpResponseHandler handler){
        AsyncHttpClient newClient = new AsyncHttpClient();
        URL = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&lang=pl&appid="+ID;
        newClient.get(URL, params, handler);
    }
}
