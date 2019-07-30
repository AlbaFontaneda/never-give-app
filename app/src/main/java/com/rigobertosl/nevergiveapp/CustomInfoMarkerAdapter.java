package com.rigobertosl.nevergiveapp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomInfoMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoMarkerAdapter";
    private LayoutInflater inflater;
    private Event myEvent;

    private TextView myTitle, myDate, myHour, myPeople;

    public CustomInfoMarkerAdapter(LayoutInflater inflater, Event eventFocused){
        this.inflater = inflater;
        this.myEvent = eventFocused;
    }

    @Override
    public View getInfoContents(final Marker myMarker) {
        //Carga layout personalizado.
        View view = inflater.inflate(R.layout.info_event_marker, null);
        String[] info = myMarker.getTitle().split("&");
        String url = myMarker.getSnippet();

        myTitle = (TextView) view.findViewById(R.id.info_title);
        myDate = (TextView) view.findViewById(R.id.info_date);
        myHour = (TextView) view.findViewById(R.id.info_hour);
        myPeople = (TextView) view.findViewById(R.id.info_people);

        myTitle.setText(myEvent.getSport());
        myDate.setText(myEvent.getDay());
        myHour.setText(myEvent.getTime());
        myPeople.setText(myEvent.getPeople());

        return view;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

}