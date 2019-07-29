package com.rigobertosl.nevergiveapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class EventsHomeFragment extends Fragment {

    private GoogleMap mMap;
    private MapView mMapView;
    ArrayList<Event> eventList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View mapView = inflater.inflate(R.layout.fragment_events_home, container, false);

        mMapView = mapView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        RecyclerView recyclerView = (RecyclerView) mapView.findViewById(R.id.eventsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        eventList = new ArrayList<>();

        Event evento1 = new Event("Tenis", new LatLng(40.316877, -3.706114), "20", "30", "04", "05", "2009", "2");
        Event evento2 = new Event("Fútbol", new LatLng(40.317572, -3.706876), "19", "00", "05", "05", "2009", "14");
        Event evento3 = new Event("Pokemon Go", new LatLng(40.317703, -3.702198), "17","15","08", "08", "2009", "5");
        Event evento4 = new Event("Gimnasio", new LatLng(40.316819, -3.704751), "13","20","20", "10", "2009", "3");
        eventList.add(evento1);
        eventList.add(evento2);
        eventList.add(evento3);
        eventList.add(evento4);

        RecyclerView.Adapter adapterEvent = new EventHomeAdapter(eventList);
        recyclerView.setAdapter(adapterEvent);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                List<Marker> markerList = new ArrayList<>();
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(40.3208737, -3.7130592)).title("Hello World"));
                markerList.add(marker);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));

                for (Event event : eventList){
                    markerList.add(mMap.addMarker((new MarkerOptions().position(event.getLocation()).title(event.getSport()))));
                }

            }
        });

        return mapView;
    }

    public interface OnFragmentInteractionListener {
    }
}