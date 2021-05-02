package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Earthquake> {

    public CustomArrayAdapter(Context context, List<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Earthquake currentEarthquake = getItem(position);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        Double magnitude =currentEarthquake.getMagnitude();
        String magnitudeToDisplay = decimalFormat.format(magnitude);

        TextView magnitudeView = (TextView) listitemView.findViewById(R.id.magnitude_text_view);
        magnitudeView.setText(magnitudeToDisplay);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = ContextCompat.getColor(getContext(),getMagnitudeColorId(magnitude));

        magnitudeCircle.setColor(magnitudeColor);

        String city = currentEarthquake.getCity();
        String locationOffset;
        String cityToDisplay;
        if(city.contains("of")){
            int index = city.indexOf("of");
            locationOffset = city.substring(0,index+2);
            cityToDisplay = city.substring(index+3);
        }
        else {
            locationOffset = "Near the";
            cityToDisplay = city;
        }
        TextView cityView = (TextView) listitemView.findViewById(R.id.city_text_view);
        cityView.setText(cityToDisplay);

        TextView locationOffsetView = (TextView) listitemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        Date date = new Date(currentEarthquake.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dateFormat.format(date);

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormat.format(date);


        TextView dateView = (TextView) listitemView.findViewById(R.id.date_text_view);
        dateView.setText(dateToDisplay);

        TextView time = (TextView) listitemView.findViewById(R.id.time_text_view);
        time.setText(timeToDisplay);
        return listitemView;
    }

    private int getMagnitudeColorId(double magnitude){
        int magnitudeColorId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorId = R.color.magnitude9;
                break;
            default:
                magnitudeColorId = R.color.magnitude10plus;
                break;
        }
        return magnitudeColorId;
    }
}
