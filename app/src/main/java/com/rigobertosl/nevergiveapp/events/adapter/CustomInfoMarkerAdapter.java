package com.rigobertosl.nevergiveapp.events.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.events.activity.EventsActivity;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;

public class CustomInfoMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoMarkerAdapter";
    private Resources resources;

    private LayoutInflater inflater;
    private Event myEvent;
    private String[] sports;
    private String[] sportsImagesSources;

    private TextView mySport, myDate, myTime, myPeople, myLocation, myAddress;
    private ImageView image;

    public CustomInfoMarkerAdapter(LayoutInflater inflater, Event eventFocused, ArrayList<Marker> markerList, String[] sports, String[] imagesResources){
        this.inflater = inflater;
        this.myEvent = eventFocused;
        this.sports = sports;
        this.sportsImagesSources = imagesResources;
        for (Marker marker : markerList){
            marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
    }

    @Override
    public View getInfoContents(final Marker myMarker) {
        //Carga layout personalizado.
        View view = inflater.inflate(R.layout.info_event_marker, null);
        resources = view.getResources();

        myMarker.setIcon(getMarkerIcon("#196F3D"));

        mySport = (TextView) view.findViewById(R.id.info_title);
        myDate = (TextView) view.findViewById(R.id.info_date);
        myTime = (TextView) view.findViewById(R.id.info_hour);
        myPeople = (TextView) view.findViewById(R.id.info_people);
        myLocation = (TextView) view.findViewById(R.id.info_location);
        myAddress = (TextView) view.findViewById(R.id.info_address);
        image = (ImageView) view.findViewById(R.id.info_image);

        mySport.setText(myEvent.getSport());
        myDate.setText(myEvent.getDate().getDayMonthYear());
        myTime.setText(myEvent.getDate().getTime());
        myPeople.setText(myEvent.getAssistants() + " personas");
        myLocation.setText(myEvent.getPlace().getName());
        myAddress.setText(myEvent.getPlace().getaddress());
        image.setImageResource(getImage(myEvent.getSport()));

        return view;
    }

    private BitmapDescriptor getMarkerIcon (String color) { //"#239B56"
        float[] hsv = new float[3];
        int _color = Color.parseColor(color);
        Color.colorToHSV(_color, hsv);

        return BitmapDescriptorFactory.defaultMarker(getHsvFromColor(color)[0]);
    }

    private static float[] getHsvFromColor(String colorString) {
        float[] hsv = new float[3];
        int _color = Color.parseColor(colorString);
        Color.colorToHSV(_color, hsv);
        return hsv;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

    private int getImage(String title){
        int index = 14;
        for(int i = 0; i < sports.length; i++){
            if(title.equals(sports[i])){
                index = i;
                break;
            }
        }
        return resources.getIdentifier(sportsImagesSources[index], "drawable", EventsActivity.PACKAGE_NAME);
    }
}