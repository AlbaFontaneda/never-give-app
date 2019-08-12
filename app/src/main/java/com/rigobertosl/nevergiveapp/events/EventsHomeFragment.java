package com.rigobertosl.nevergiveapp.events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.R;

import java.util.ArrayList;

public class EventsHomeFragment extends FragmentFiredatabase implements LocationListener {


    /**************************  Variables   **************************/

    private GoogleMap mMap;
    private MapView mMapView;
    private LocationManager locationManager;
    private RecyclerView recyclerView;
    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList<>();

    /************************** Listeners **************************/

    /** Listener que carga todos los eventos que existen en la base de datos **/
    ValueEventListener loadEvents = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            eventList.clear();
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                Event eventRead = eventSnapshot.getValue(Event.class);
                eventList.add(eventRead);
                markerList.add(mMap.addMarker((new MarkerOptions().position(eventRead.getLatLng()).title(eventRead.getSport()))));
            }

            // Creación del RecyclerView con todos los eventos.
            RecyclerView.Adapter adapterEvent = new EventHomeAdapter(eventList);
            recyclerView.setAdapter(adapterEvent);
            ((EventHomeAdapter) adapterEvent).setOnItemClickListener(markerClickListener);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /** Listener cuando clickeas en uno de los eventos dentro del RecyclerView **/
    private EventHomeAdapter.ClickListener markerClickListener = new EventHomeAdapter.ClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            LatLng eventLatLng = eventList.get(position).getLatLng();
            LatLng latLng = new LatLng(eventLatLng.latitude, eventLatLng.longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .tilt(60)
                    .target(latLng)
                    .zoom(19)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate);
            mMap.setInfoWindowAdapter(new CustomInfoMarkerAdapter(LayoutInflater.from(getActivity()), eventList.get(position)));
            markerList.get(position).showInfoWindow();
        }
    };

    /**************************  Métodos   **************************/

    @Override
    public void onStart() {
        super.onStart();

        mydbRef = database.getReference(eventsKey);
        mydbRef.addValueEventListener(loadEvents);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100000, 100, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View mapView = inflater.inflate(R.layout.fragment_events_home, container, false);

        mMapView = mapView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        recyclerView = (RecyclerView) mapView.findViewById(R.id.eventsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // ToDo: Esto está hecho a mano. Quitarlo en el futuro.
/*
        Profile user = new Profile("Blusslightyear");
        eventList = new ArrayList<>();
        Event evento1 = new Event("Tenis", "20", "30", "04", "05", "2009", "2", null, new LatLong(40.316877, -3.706114), user );
        Event evento2 = new Event("Fútbol", "19", "00", "05", "05", "2009", "14", null, new LatLong(40.317572, -3.706876), user);
        Event evento3 = new Event("Pokemon Go", "17","15","08", "08", "2009", "5", null,  new LatLong(40.317703, -3.702198), user);
        Event evento4 = new Event("Gimnasio","13","20","20", "10", "2009", "3", null,  new LatLong(40.316819, -3.704751), user);
        Event evento5 = new Event("Tirar piedras", "13","00","08", "11", "2009", "3", null,  new LatLong(42.343995, -3.697103), user);
        eventList.add(evento1);
        eventList.add(evento2);
        eventList.add(evento3);
        eventList.add(evento4);
        eventList.add(evento5);

*/
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                markerList = new ArrayList<>();
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                mMap.setMyLocationEnabled(true);
/*
                for (Event event : eventList){
                    markerList.add(mMap.addMarker((new MarkerOptions().position(event.getLatLng()).title(event.getSport()))));
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                int padding = 60;

                for(Marker mMarker : markerList) {
                    builder.include(mMarker.getPosition());
                }
                LatLngBounds bounds = builder.build();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cameraUpdate);
                */
            }
        });

        return mapView;
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}