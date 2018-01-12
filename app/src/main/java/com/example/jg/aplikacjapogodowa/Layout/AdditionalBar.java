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
    ArrayList<String> _tempMin;
    ArrayList<String> _tempMax;
    ArrayList <String> _iconData;

    public AdditionalBar(Context context, int resource,  ArrayList<String> title, ArrayList<String> tempMax,ArrayList<String> tempMin, ArrayList <String> iconData) {
        super(context, resource, title);
        this._title = title;
        this._context = context;
        this._tempMax = tempMax;
        this._tempMin = tempMin;
        this._iconData = iconData;
    }

    /**
     * Allows user to get more specific information about each day of next week
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.bar, parent, false);
        TextView tittleText = view.findViewById(R.id.text_1);
        TextView tempText1 = view.findViewById(R.id.tempMin);
        TextView tempText2 = view.findViewById(R.id.tempMax);
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
        String tempString = "  "+ _tempMin.get(position ) + "º";
        tempText1.setText(tempString);
        tittleText.setText(tittleString);
        String tempString2 = "  "+ _tempMax.get(position ) + "º";
        tempText2.setText(tempString2);
        tittleText.setText(tittleString);
        return view;


    }
}