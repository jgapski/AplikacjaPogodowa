package com.example.jg.aplikacjapogodowa.Layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jg.aplikacjapogodowa.R;
import java.util.ArrayList;


/**
 * Created by jg on 06.01.18.
 */

public class AdditionalBar extends ArrayAdapter {

    private Context _context;
    ArrayList<String> _title;
    ArrayList<String> _tempData;
    ArrayList <String> _iconData;

    public AdditionalBar(Context context, int resource,  ArrayList<String> title, ArrayList<String> tempData, ArrayList <String> iconData) {
        super(context, resource, title);
        this._title = title;
        this._context = context;
        this._tempData = tempData;
        this._iconData = iconData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.bar, parent, false);
        TextView tittleText = view.findViewById(R.id.text_1);
        TextView tempText = view.findViewById(R.id.tempData);
        ImageView iconImage= view.findViewById(R.id.iconWeather);

        switch (_iconData.get(position)){

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
        String tittleString = "  "+ _title.get(position);
        String tempString = "  "+ _tempData.get(position ) + "º";
        tempText.setText(tempString);
        tittleText.setText(tittleString);
        return view;


    }
}