package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FragmentFiredatabase extends Fragment implements FiredatabaseInterface {

    /************************************* Variables **********************************************/
    protected DatabaseReference mydbRef;
    protected Profile currentUser;
    protected HashMap<String, Event> allEvents = new HashMap<>();

    /************************************* Listeners **********************************************/
    /**
     * Rellena la variable @currentUser con un perfil del usuario logueado.
     */
    private ValueEventListener loadCurrentUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            currentUser = dataSnapshot.getValue(Profile.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    /**
     * Lee todos los eventos y va rellenando la variable @allEvents
     */
    private ValueEventListener loadAllEvents = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                Event eventLoaded = eventSnapshot.getValue(Event.class);
                eventLoaded.setID(eventSnapshot.getKey());
                allEvents.put(eventLoaded.getID(), eventLoaded);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    /************************************* Methods ************************************************/
    /**
     * Actualiza el usuario logueado para poder trabajar con él como un objeto Profile
     */
    protected void loadCurrentUser() {
        mydbRef = database.getReference(usersKey).child(getUid());
        mydbRef.addListenerForSingleValueEvent(loadCurrentUser);
    }

    /**
     * Lee todos los eventos que existen en la base de datos y los almacena en la variable
     * @allEvents
     */
    protected void loadAllEvents() {
        mydbRef = database.getReference(eventsKey);
        mydbRef.addListenerForSingleValueEvent(loadAllEvents);
    }

    /**
     * Introduce un nuevo evento dentro de la base de datos y a su vez actualiza la lista de
     * eventos del usuario logueado, donde introduce el elemento también.
     * @param event evento a introducir en Firebase.
     */
    protected void addEventToFirebase(Event event){
        String keyEvent = database.getReference(eventsKey).push().getKey();
        if(currentUser != null){

            currentUser.addTargetedEvents(keyEvent);
            event.addMember(currentUser);
        }
        event.setID(keyEvent);
        Map<String, Object> eventValue = event.toMap();
        Map<String, Object> userValue = currentUser.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+eventsKey+"/" + keyEvent, eventValue);
        childUpdates.put("/"+usersKey+"/" + getUid(), userValue);

        database.getReference().updateChildren(childUpdates);
    }

    /**
     * Se apunta un nuevo usuario a la lista de miembros del evento.
     *  - Se introduce el ID del evento dentro de la lista de eventos del usuario logueado
     *  - Se introduce el usuario logueado dentro de la lista de miembros del evento
     * @param event evento donde se quiere apuntar el usuario
     */
    protected void addUserToEvent(Event event){
        if(currentUser != null) {
            currentUser.addTargetedEvents(event.getID());
            event.addMember(currentUser);

            Map<String, Object> eventValue = event.toMap();
            Map<String, Object> userValue = currentUser.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/"+eventsKey+"/" + event.getID(), eventValue);
            childUpdates.put("/"+usersKey+"/" + getUid(), userValue);

            database.getReference().updateChildren(childUpdates);
        }
    }

    /**
     * Se desapunta el usuario actual de un evento.
     *  - Se borra el evento del usuario dentro de Firebase
     *  - Se borra el usuario del evento dentro de Firebase
     * @param event evento que se desea borrar
     */
    protected void removeUserFromEvent(Event event){
        if(currentUser != null) {
            currentUser.getTargetedEvents().remove(event.getID());
            event.getMembers().remove(currentUser.getID());

            Map<String, Object> eventValue = event.toMap();
            Map<String, Object> userValue = currentUser.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/"+eventsKey+"/" + event.getID(), eventValue);
            childUpdates.put("/"+usersKey+"/" + getUid(), userValue);

            database.getReference().updateChildren(childUpdates);
        }
    }

    @Override
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
