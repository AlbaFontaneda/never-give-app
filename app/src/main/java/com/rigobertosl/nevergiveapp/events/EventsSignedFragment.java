package com.rigobertosl.nevergiveapp.events;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;


public class EventsSignedFragment extends FragmentFiredatabase {

    /************************************* Variables **********************************************/
    private ArrayList<Event> eventSignedList = new ArrayList<>();
    private RecyclerView recyclerView;

    /************************************* Listeners **********************************************/
    private ValueEventListener loadEventsIDsFromUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            eventSignedList.clear();
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                Event eventRead = eventSnapshot.getValue(Event.class);
                eventRead.setID(eventSnapshot.getKey());
                eventSignedList.add(eventRead);
            }

            recyclerView.setAdapter(new EventListAdapter(recyclerView, eventSignedList));
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    /************************************* Methods ************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCurrentUser();
        loadAllEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_events_signed, container, false);

        recyclerView = (RecyclerView)fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getEventsFromUser();

        //recyclerView.setAdapter(new EventListAdapter(recyclerView, eventSignedList));

        return fragmentView;
    }

    private void getEventsFromUser(){
        //if(currentUser != null && allEvents != null){}
        mydbRef = database.getReference(usersKey).child(mAuth.getCurrentUser().getUid()).child("targetedEvents");
        mydbRef.addValueEventListener(loadEventsIDsFromUser);
    }

}
