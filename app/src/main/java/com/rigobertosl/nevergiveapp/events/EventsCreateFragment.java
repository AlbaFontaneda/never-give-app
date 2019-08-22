package com.rigobertosl.nevergiveapp.events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.rigobertosl.nevergiveapp.objects.Date;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.GooglePlace;
import com.rigobertosl.nevergiveapp.objects.Profile;
import com.shawnlin.numberpicker.NumberPicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.security.auth.callback.PasswordCallback;

import static com.rigobertosl.nevergiveapp.events.EventsMain.DEFAULT_ZOOM;

public class EventsCreateFragment extends FragmentFiredatabase implements LocationListener, DatePickerDialog.OnDateSetListener {

    private ExpandableLayout expandableLayoutMain, expandableLayoutSelectSport, expandableLayoutTop, expandableLayoutBottom, expandableLayoutRecyclerView;
    private LinearLayout calendarLayout, time_layout, peopleLayout, location_Layout, notes_Layout, sportTitleLayout;
    private EditText notesEditText, peopleText;
    private TextView sportText, dateText, timeText, locationText;
    private RecyclerView recyclerSports, recyclerBottom;
    private FloatingActionButton fab;

    private GoogleMap mMap;
    private MapView mMapView;
    private LatLng myLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private RecyclerView.LayoutManager layoutManagerHorizontal;
    private RecyclerView.LayoutManager layoutManagerVertical;
    private Profile host;

    private ArrayList<GooglePlace> googlePlacesList = new ArrayList<>();
    private ArrayList<MarkerOptions> markerList = new ArrayList<>();
    private ArrayList<String> distancesList = new ArrayList<>();
    private Event evento = new Event();
    private Date eventDate = new Date();

    private String[] sports;
    private String[] sportsImages;

    protected ValueEventListener getCurrentHost = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            host = dataSnapshot.getValue(Profile.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    /** Listener cuando clickeas en uno de los eventos dentro del RecyclerView **/
    private GooglePlaceAdapter.ClickListener markerClickListener = new GooglePlaceAdapter.ClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            locationText.setText(googlePlacesList.get(position).getName());
            locationText.setTextColor(Color.BLACK);

            evento.setPlace(googlePlacesList.get(position));

            LatLng myPlace = googlePlacesList.get(position).getLatLng();
            animateCamera(myPlace, 19, 60);

            layoutManagerHorizontal.scrollToPosition(position);

            //mMap.setInfoWindowAdapter(new CustomInfoMarkerAdapter(LayoutInflater.from(getActivity()), googlePlacesList.get(position), markerList));
            //markerList.get(position).showInfoWindow();
        }
    };

    private SportAdapter.ClickListener sportClickListener = new SportAdapter.ClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            sportText.setText(sports[position]);
            expandableLayoutSelectSport.collapse();
            expandableLayoutMain.expand();
            fab.show();
        }
    };


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydbRef = database.getReference(usersKey).child(mAuth.getCurrentUser().getUid());
        mydbRef.addListenerForSingleValueEvent(getCurrentHost);

        sports = getActivity().getResources().getStringArray(R.array.sportsNames);
        sportsImages = getActivity().getResources().getStringArray(R.array.sportsImagesNames);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_event, container, false);

        /************************************ Binds **********************************************/
        mMapView = view.findViewById(R.id.map);

        expandableLayoutMain = (ExpandableLayout)view.findViewById(R.id.expandablelayout_main);
        expandableLayoutSelectSport = (ExpandableLayout)view.findViewById(R.id.expandablelayout_select_sport);
        expandableLayoutTop = (ExpandableLayout)view.findViewById(R.id.expandablelayout_top);
        expandableLayoutBottom = (ExpandableLayout)view.findViewById(R.id.expandablelayout_bottom);
        expandableLayoutRecyclerView = (ExpandableLayout)view.findViewById(R.id.expandablelayout_recyclerview);

        calendarLayout = (LinearLayout)view.findViewById(R.id.calendar_layout);
        time_layout = (LinearLayout)view.findViewById(R.id.time_layout);
        peopleLayout = (LinearLayout)view.findViewById(R.id.people_layout);
        location_Layout = (LinearLayout)view.findViewById(R.id.location_layout);
        notes_Layout = (LinearLayout)view.findViewById(R.id.notes_layout);
        sportTitleLayout = (LinearLayout)view.findViewById(R.id.sports_title_layout);

        peopleText = (EditText) view.findViewById(R.id.people_text);
        notesEditText = (EditText)view.findViewById(R.id.notes_text);

        sportText = (TextView)view.findViewById(R.id.sport_text);
        dateText = (TextView)view.findViewById(R.id.date_text);
        timeText = (TextView)view.findViewById(R.id.time_text);
        locationText = (TextView)view.findViewById(R.id.location_text);

        recyclerBottom = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerSports = (RecyclerView)view.findViewById(R.id.recycler_sports);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        /************************* LayoutManager for recyclerViews ********************************/
        layoutManagerHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerVertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerBottom.setLayoutManager(layoutManagerHorizontal);
        recyclerSports.setLayoutManager(layoutManagerVertical);


        /********************************* Listeners *********************************************/
        calendarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarPicker();
            }
        });

        time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHourPicker(v, timeText);
            }
        });

        peopleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleText.requestFocus();
            }
        });

        //ToDo: cambiar esto en un futuro
        location_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayoutTop.isExpanded() && expandableLayoutBottom.isExpanded()) {
                    if(markerList.isEmpty()){
                        new FindPlaces().execute();
                    }else{
                        for(MarkerOptions marker : markerList){
                            mMap.addMarker(marker);
                        }
                        expandableLayoutRecyclerView.expand();
                    }
                    expandableLayoutTop.collapse();
                    expandableLayoutBottom.collapse();
                    fab.hide();
                } else {
                    mMap.clear();
                    if(evento.getPlace() != null){
                        MarkerOptions markerOptions = new MarkerOptions().position(evento.getPlace().getLatLng())
                                .title(evento.getPlace().getName());
                        mMap.addMarker(markerOptions);
                        animateCamera(evento.getPlace().getLatLng(), 15, 0);
                    }

                    expandableLayoutTop.expand();
                    expandableLayoutBottom.expand();
                    expandableLayoutRecyclerView.collapse();
                    fab.show();
                }
            }
        });

        notes_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesEditText.requestFocus();
            }
        });

        sportTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecyclerView.Adapter adapterSports = new SportAdapter(sports, sportsImages);
                recyclerSports.setAdapter(adapterSports);
                ((SportAdapter) adapterSports).setOnItemClickListener(sportClickListener);
                fab.hide();
                expandableLayoutMain.collapse();
                expandableLayoutSelectSport.expand();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sportText.getText().toString().equals("") && !peopleText.getText().toString().equals("")
                        && !dateText.getText().toString().equals("") && !timeText.getText().toString().equals("")){

                    evento.setSport(sportText.getText().toString());
                    evento.setAssistants(Integer.parseInt(peopleText.getText().toString()));
                    evento.setDate(eventDate);
                    evento.setHost(host);
                    addDataToFirebase(eventsKey, evento);
                    Toast.makeText(getContext(), "Evento creado", Toast.LENGTH_LONG);

                    // Cambiamos a la pantalla principal
                    ((EventsMain)getActivity()).changeFragment(1);
                }else{
                    Toast.makeText(getContext(), "Por favor, rellene todos los campos.", Toast.LENGTH_LONG).show();
                }
            }
        });

        expandableLayoutTop.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        expandableLayoutBottom.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        expandableLayoutRecyclerView.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        /****************************************** Maps ******************************************/
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
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

        return view;
    }

    /************************************** Calendar Methods **************************************/
    public void openCalendarPicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String inputDate = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year);
        dateText.setText(inputDate);
        eventDate.setDayOfMonth(day);
        eventDate.setMonth(month);
        eventDate.setYear(year);
    }

    /**************************************** Hour Methods ****************************************/
    public void openHourPicker(View view, final TextView editTextTime){
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogLayout = LayoutInflater.from(view.getContext())
                .inflate(R.layout.popup_hours_minutes, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final int[] selectedMinute = {0};
        final int[] selectedHour = {0};

        NumberPicker minutosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.minutos_picker);
        NumberPicker horasPicker = (NumberPicker) dialogLayout.findViewById(R.id.horas_picker);

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

        minutosPikcer.setValue(0);
        minutosPikcer.setMinValue(0);
        minutosPikcer.setMaxValue(59);
        minutosPikcer.setWrapSelectorWheel(true);

        minutosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                selectedMinute[0] = newVal;
            }
        });

        horasPicker.setValue(0);
        horasPicker.setMinValue(0);
        horasPicker.setMaxValue(23);
        horasPicker.setWrapSelectorWheel(true);

        horasPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                selectedHour[0] = newVal;
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", selectedHour[0], selectedMinute[0]);
                editTextTime.setText(timeLeftFormatted);
                eventDate.setHour(selectedHour[0]);
                eventDate.setMinutes(selectedMinute[0]);
                dialog.cancel();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    /************************************ Google Maps Methods *************************************/
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

    private void animateCamera(LatLng latLng, int zoom, int tilt){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .tilt(tilt)
                .target(latLng)
                .zoom(zoom)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }

    /************************************ Get gyms from .json *************************************/
    public class FindPlaces extends AsyncTask<View, Void, ArrayList<GooglePlace>> {

        private static final String GOOGLE_API = "AIzaSyDRUgS9EY-LGUknQkqjkBKl-IV1lv5b4WY";
        private static final String type = "gym";
        private static final String radius = "4000";

        @Override
        protected ArrayList<GooglePlace> doInBackground(View... views) {
            ArrayList<GooglePlace> placesList = null;
            if(myLocation != null){
                placesList = makeCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                        + myLocation.latitude + "," + myLocation.longitude + "&radius=" + radius + "&type=" + type + "&sensor=true&key=" + GOOGLE_API);
            }
            return placesList;
        }

        private ArrayList<GooglePlace> makeCall(String stringURL) {
            URL url = null;
            InputStream is = null;
            ArrayList<GooglePlace> placesList = new ArrayList<>();

            try {
                url = new URL(stringURL);
            } catch (Exception ex) {
                System.out.println("Malformed URL");
            }

            try {
                if (url != null) {
                    is = url.openStream();
                }
            } catch (IOException ioe) {
                System.out.println("IOException");
            }
            if(is != null) {
                try {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONObject jsonObj = new JSONObject(jsonText);

                    JSONArray results = jsonObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jObj = results.getJSONObject(i);

                        JSONObject place = jObj.getJSONObject("geometry");
                        JSONObject location = place.getJSONObject("location");
                        String lat = location.getString("lat");
                        String lng = location.getString("lng");

                        String name = jObj.getString("name");

                        String open_now = "";
                        if(!jObj.isNull("opening_hours")){
                            JSONObject opening_hours = jObj.getJSONObject("opening_hours");
                            open_now = opening_hours.getString("open_now");
                        }
                        String rating = "-1";
                        if(!jObj.isNull("rating")){
                            rating = jObj.getString("rating");
                        }

                        String userRatings = "-1";
                        if(!jObj.isNull("user_ratings_total")){
                            userRatings = jObj.getString("user_ratings_total");
                        }

                        String address = "";
                        if(!jObj.isNull("vicinity")){
                            address = jObj.getString("vicinity");
                        }

                        GooglePlace googlePlace = new GooglePlace(name, address,
                                Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(rating),
                                Integer.parseInt(userRatings), Boolean.parseBoolean(open_now));
                        placesList.add(googlePlace);
                    }

                } catch (Exception e) {
                    System.out.println("Exception");
                    return new ArrayList<>();
                }
            }
            googlePlacesList = placesList;
            return placesList;
        }

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        protected void onPostExecute(ArrayList<GooglePlace> result) {
            for(GooglePlace place : result){
                MarkerOptions newMarkerOptions = new MarkerOptions().position(place.getLatLng())
                        .title(place.getName());
                markerList.add(newMarkerOptions);
                mMap.addMarker(newMarkerOptions);
                /*
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatitude(), place.getLongitude()))
                        .title(place.getName()));
                */
                RecyclerView.Adapter adapterPlace = new GooglePlaceAdapter(googlePlacesList, myLocation);
                recyclerBottom.setAdapter(adapterPlace);
                ((GooglePlaceAdapter) adapterPlace).setOnItemClickListener(markerClickListener);
                expandableLayoutRecyclerView.expand();
            }
        }
    }
}