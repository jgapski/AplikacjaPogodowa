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

    /**
     * sends a request to specified web API, data received is in JSON
     * @param Latitude
     * @param Longitude
     * @param params
     * @param handler
     */
    public static void getWeather (String Latitude, String Longitude, RequestParams params, AsyncHttpResponseHandler handler){

        AsyncHttpClient newClient = new AsyncHttpClient();
        URL = "http://api.openweathermap.org/data/2.5/forecast/daily?"+"lat="+Latitude+"&lon="+Longitude+"&units=metric&lang=pl&appid="+ID;
        newClient.get(URL, params, handler);

    }

    /**
     * specifiest the city name based on coordinates
     * @param context
     * @param lat
     * @param lon
     * @return
     * @throws IOException
     */
    public String getCityName(Context context, double lat, double lon) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat,lon, 1);
        return addresses.get(0).getLocality();
    }

    /**
     * sends a request to specified web API, data received is in JSON
     * @param city
     * @param params
     * @param handler
     */
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
