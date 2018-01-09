package com.example.jg.aplikacjapogodowa.Klient;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jg on 06.01.18.
 */

public class GetData {

    static String URL;
        private static final String ID = "a9ba67d0a9a33a56f6d485c0a03c58f1";

    public static void getWeather (String Latitude, String Longitude, RequestParams params, AsyncHttpResponseHandler handler){

        AsyncHttpClient newClient = new AsyncHttpClient();
        URL = "http://api.openweathermap.org/data/2.5/forecast/daily?"+"lat="+Latitude+"&lon="+Longitude+"&units=metric&lang=pl&appid="+ID;
        newClient.get(URL, params, handler);

    }
}
