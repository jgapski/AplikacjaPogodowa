package com.example.jg.aplikacjapogodowa.Listeners;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.jg.aplikacjapogodowa.Widget.WeatherWidget;

/**
 * Created by jg on 06.01.18.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent _intent = new Intent(context, WeatherWidget.class);
        _intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids [] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,  WeatherWidget.class));
        _intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(_intent);

    }
}