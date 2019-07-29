package com.rigobertosl.nevergiveapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class EventsHomeFragment extends Fragment implements LocationListener {

    private GoogleMap mMap;
    private MapView mMapView;
    ArrayList<Event> eventList;
    GooglePlace myLocation;
    private LocationManager locationManager;

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

        RecyclerView recyclerView = (RecyclerView) mapView.findViewById(R.id.eventsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        eventList = new ArrayList<>();

        Event evento1 = new Event("Tenis", new LatLng(40.316877, -3.706114), "20", "30", "04", "05", "2009", "2");
        Event evento2 = new Event("Fútbol", new LatLng(40.317572, -3.706876), "19", "00", "05", "05", "2009", "14");
        Event evento3 = new Event("Pokemon Go", new LatLng(40.317703, -3.702198), "17","15","08", "08", "2009", "5");
        Event evento4 = new Event("Gimnasio", new LatLng(40.316819, -3.704751), "13","20","20", "10", "2009", "3");
        Event evento5 = new Event("Tirar piedras", new LatLng(42.343995, -3.697103), "13","00","08", "11", "2009", "3");
        eventList.add(evento1);
        eventList.add(evento2);
        eventList.add(evento3);
        eventList.add(evento4);
        eventList.add(evento5);

        RecyclerView.Adapter adapterEvent = new EventHomeAdapter(eventList);
        recyclerView.setAdapter(adapterEvent);

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

                List<Marker> markerList = new ArrayList<>();
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                mMap.setMyLocationEnabled(true);

                for (Event event : eventList){
                    markerList.add(mMap.addMarker((new MarkerOptions().position(event.getLocation()).title(event.getSport()))));
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                int padding = 60;

                for(Marker mMarker : markerList) {
                    builder.include(mMarker.getPosition());
                }
                LatLngBounds bounds = builder.build();

                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                //mMap.animateCamera(cameraUpdate);


            }
        });

        return mapView;
    }

    @Override
    public void onLocationChanged(Location location) {
        //myLocation = new GooglePlace("My location", location.getLatitude(), location.getLongitude());
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

    public interface OnFragmentInteractionListener {
    }
}