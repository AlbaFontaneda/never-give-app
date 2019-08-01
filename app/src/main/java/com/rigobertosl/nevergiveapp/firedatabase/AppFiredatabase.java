package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AppFiredatabase extends AppCompatActivity implements FiredatabaseInterface {

    protected DatabaseReference mydbRef;
    public final String TAG = "-------EVENTOS-------";
    protected ArrayList<Event> allEvents = new ArrayList<>();
    protected FiredatabaseInterface firedatabaseInterface = new FiredatabaseInterface() {
        @Override
        public void addDataToFirebase(String key, Object data) {

        }

        @Override
        public void onCallback(ArrayList<Event> eventList) {
            allEvents = eventList;
        }
    };

    /***************  Métodos de la interfaz  ***************/
    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }

    @Override
    public void onCallback(ArrayList<Event> eventList) {

    }

    /**********************  Eventos  **********************/

    public void loadEvents(final FiredatabaseInterface firedatabaseInterface){
        mydbRef = database.getReference(eventsKey);
        //Log.e(TAG, "ANTES DEL LISTENER.");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.e(TAG, "DENTRO DEL MÉTODO onDataChange().");
                for (DataSnapshot eventoFirebase : dataSnapshot.getChildren()) {
                    Event event = eventoFirebase.getValue(Event.class);
                    //TODO: no sé porqué aquí devuelve el objeto sin ID
                    event.setID(eventoFirebase.getKey());
                    allEvents.add(event);
                    //eventList.put(eventID, event);
                    //Log.e("GET DATA", event.creacionDeEvento());
                }

                firedatabaseInterface.onCallback(allEvents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR DE LECTURA", databaseError.getMessage());
            }
        };
        mydbRef.addValueEventListener(valueEventListener);
        //Log.e(TAG, "DESPUÉS DEL LISTENER.");
    }

}