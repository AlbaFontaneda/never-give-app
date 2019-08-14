package com.rigobertosl.nevergiveapp.events;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;

public class CustomInfoMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoMarkerAdapter";
    private LayoutInflater inflater;
    private Event myEvent;

    private TextView myTitle, myDate, myTime, myPeople, myHost;

    public CustomInfoMarkerAdapter(LayoutInflater inflater, Event eventFocused, ArrayList<Marker> markerList){
        this.inflater = inflater;
        this.myEvent = eventFocused;
        for (Marker marker : markerList){
            marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
    }

    @Override
    public View getInfoContents(final Marker myMarker) {
        //Carga layout personalizado.
        View view = inflater.inflate(R.layout.info_event_marker, null);

        myMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        myTitle = (TextView) view.findViewById(R.id.info_title);
        myDate = (TextView) view.findViewById(R.id.info_date);
        myTime = (TextView) view.findViewById(R.id.info_hour);
        myPeople = (TextView) view.findViewById(R.id.info_people);
        myHost = (TextView) view.findViewById(R.id.info_host);

        myTitle.setText(myEvent.getSport());
        myDate.setText(myEvent.getDate().getDayMonthYear());
        myTime.setText(myEvent.getDate().getTime());
        myPeople.setText(myEvent.getAssistants() + " personas");
        if(myEvent.getHost() != null){
            myHost.setText(myEvent.getHost().getName());
        }

        return view;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

}