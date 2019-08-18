package com.rigobertosl.nevergiveapp.events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;

import static com.rigobertosl.nevergiveapp.events.EventsMain.DEFAULT_ZOOM;

public class EventsHomeFragment extends FragmentFiredatabase implements LocationListener {

    private static final String TAG = "EventsHomeFragment";

    /**************************  Variables   **************************/

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private MapView mMapView;
    private LocationManager locationManager;
    private RecyclerView recyclerView;
    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList<>();
    private LatLng myLocation = null;

    /************************** Listeners **************************/

    /** Listener que carga todos los eventos que existen en la base de datos **/
    ValueEventListener loadEvents = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            eventList.clear();
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                Event eventRead = eventSnapshot.getValue(Event.class);
                eventList.add(eventRead);
                MarkerOptions newMarkerOptions = new MarkerOptions().position(eventRead.getPlace().getLatLng()); //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker newMarker = mMap.addMarker(newMarkerOptions);
                markerList.add(newMarker);
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
            LatLng eventLatLng = eventList.get(position).getPlace().getLatLng();
            LatLng latLng = new LatLng(eventLatLng.latitude, eventLatLng.longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .tilt(60)
                    .target(latLng)
                    .zoom(19)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate);
            mMap.setInfoWindowAdapter(new CustomInfoMarkerAdapter(LayoutInflater.from(getActivity()), eventList.get(position), markerList));
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

        final View fragmentView = inflater.inflate(R.layout.fragment_events_home, container, false);

        mMapView = fragmentView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.eventsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

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
                getDeviceLocation();
                mMap.setMyLocationEnabled(true);
            }
        });

        return fragmentView;
    }

    /************************************** Custom methods ****************************************/
    private void getDeviceLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        @SuppressLint("MissingPermission") final Task location = mFusedLocationClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();
                    myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    moveCamera(myLocation, DEFAULT_ZOOM);
                }else{

                }
            }
        });
    }

    private void moveCamera(LatLng latLng, int zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onLocationChanged(Location location) {
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