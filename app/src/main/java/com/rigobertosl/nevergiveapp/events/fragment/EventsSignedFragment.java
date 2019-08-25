package com.rigobertosl.nevergiveapp.events.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.events.adapter.EventListAdapter;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;


public class EventsSignedFragment extends FragmentFiredatabase {

    /************************************* Variables **********************************************/
    private ArrayList<Event> eventSignedList = new ArrayList<>();
    private RecyclerView recyclerView;

    /************************************* Methods ************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_events_signed, container, false);

        recyclerView = (RecyclerView)fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAllEvents();

        return fragmentView;
    }

    protected void loadAllEvents() {
        mydbRef = database.getReference(eventsKey);
        mydbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event eventLoaded = eventSnapshot.getValue(Event.class);
                    eventLoaded.setID(eventSnapshot.getKey());
                    allEvents.put(eventLoaded.getID(), eventLoaded);
                }
                getEventsFromUser();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void getEventsFromUser(){
        //if(currentUser != null && allEvents != null){}
        mydbRef = database.getReference(usersKey).child(getUid()).child("targetedEvents");
        mydbRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventSignedList.clear();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                    String eventID = eventSnapshot.getValue(String.class);
                    eventSignedList.add(allEvents.get(eventID));
                }
                recyclerView.setAdapter(new EventListAdapter(recyclerView, eventSignedList));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
