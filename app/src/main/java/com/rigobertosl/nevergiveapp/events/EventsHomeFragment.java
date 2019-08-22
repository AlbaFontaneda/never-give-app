package com.rigobertosl.nevergiveapp.events;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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
import com.rigobertosl.nevergiveapp.LocationPermissions;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Map;

import static com.rigobertosl.nevergiveapp.events.EventsMain.DEFAULT_ZOOM;

public class EventsHomeFragment extends FragmentFiredatabase implements LocationListener {

    private static final String TAG = "EventsHomeFragment";

    /**************************  Variables   **************************/
    private ExpandableLayout expandablelayoutRecyclerView, expandableLayoutSwitch;
    private RecyclerView recyclerView;
    private ImageView arrowBack;
    private TextView textLayoutSwitch;
    private Switch switchButton;

    private GoogleMap mMap;
    private MapView mMapView;
    private LatLng myLocation = null;
    private FusedLocationProviderClient mFusedLocationClient;

    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList<>();

    private String[] sports;
    private String[] sportsImages;

    /************************** Listeners **************************/
    /** Listener que carga todos los eventos que existen en la base de datos **/
    ValueEventListener loadEvents = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            eventList.clear();
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                Event eventRead = eventSnapshot.getValue(Event.class);
                eventRead.setID(eventSnapshot.getKey());
                eventList.add(eventRead);
                MarkerOptions newMarkerOptions = new MarkerOptions().position(eventRead.getPlace().getLatLng()); //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker newMarker = mMap.addMarker(newMarkerOptions);
                markerList.add(newMarker);
            }

            // Creación del RecyclerView con todos los eventos.
            RecyclerView.Adapter adapterEvent = new EventHomeAdapter(eventList, sports, sportsImages);
            recyclerView.setAdapter(adapterEvent);
            ((EventHomeAdapter) adapterEvent).setOnItemClickListener(markerClickListener);
            if(!expandablelayoutRecyclerView.isExpanded()){
                expandablelayoutRecyclerView.expand();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /** Listener cuando clickeas en uno de los eventos dentro del RecyclerView **/
    private EventHomeAdapter.ClickListener markerClickListener = new EventHomeAdapter.ClickListener() {
        @Override
        public void onItemClick(final int position, View view) {
            final Event eventClicked = eventList.get(position);

            final LatLng eventLatLng = eventClicked.getPlace().getLatLng();
            LatLng latLng = new LatLng(eventLatLng.latitude, eventLatLng.longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .tilt(60)
                    .target(latLng)
                    .zoom(19)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate);
            mMap.setInfoWindowAdapter(new CustomInfoMarkerAdapter(LayoutInflater.from(getActivity()),
                    eventClicked, markerList, sports, sportsImages));
            markerList.get(position).showInfoWindow();

            if(checkCurrentUserInEvent(eventClicked)){
                switchButton.setChecked(true);
                textLayoutSwitch.setText("¡Ya formas parte de este evento!");
            }else{
                switchButton.setChecked(false);
                textLayoutSwitch.setText("¿Te gustaría formar parte de este evento?");
            }

            expandableLayoutSwitch.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
                @Override
                public void onExpansionUpdate(float expansionFraction, int state) {

                    switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if(isChecked){
                                addCurrentUserToEvent(eventClicked);
                                textLayoutSwitch.setText("¡Ya formas parte de este evento!");
                            }else{
                                removeCurrentUserFromEvent(eventClicked);
                                textLayoutSwitch.setText("¿Te gustaría formar parte de este evento?");
                            }
                        }
                    });
                }
            });
            expandableLayoutSwitch.expand();
        }
    };

    /**************************  Métodos   **************************/
    @Override
    public void onStart() {
        super.onStart();

        loadCurrentUser();
        mydbRef = database.getReference(eventsKey);
        mydbRef.addValueEventListener(loadEvents);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sports = getActivity().getResources().getStringArray(R.array.sportsNames);
        sportsImages = getActivity().getResources().getStringArray(R.array.sportsImagesNames);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_events_home, container, false);

        mMapView = fragmentView.findViewById(R.id.map);
        expandablelayoutRecyclerView = fragmentView.findViewById(R.id.expandablelayout_recyclerview);
        expandableLayoutSwitch = fragmentView.findViewById(R.id.expandable_layout_switch);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.eventsRecyclerView);
        arrowBack = (ImageView)fragmentView.findViewById(R.id.arrow_back);
        textLayoutSwitch = (TextView) fragmentView.findViewById(R.id.text_layout_switch);
        switchButton = (Switch) fragmentView.findViewById(R.id.switch_button);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        expandablelayoutRecyclerView.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {

            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayoutSwitch.collapse();
            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                getDeviceLocation();
            }
        });
        return fragmentView;
    }

    /************************************** Custom methods ****************************************/
    public void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            LocationPermissions.requestPermission((AppFiredatabase) getActivity(), EventsMain.LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            final Task location = mFusedLocationClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        moveCamera(myLocation, DEFAULT_ZOOM);
                    }
                }
            });
            mMap.setMyLocationEnabled(true);
        }
    }

    public void moveCamera(LatLng latLng, int zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public boolean checkCurrentUserInEvent(Event event){
        boolean result = false;
        for(Map.Entry<String, Profile> currentProfile : event.getMembers().entrySet()){
            if(currentProfile.getValue().getID().equals(mAuth.getCurrentUser().getUid())){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void onLocationChanged(Location location) {}
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}
    @Override
    public void onProviderEnabled(String s) {}
    @Override
    public void onProviderDisabled(String s) {}
}