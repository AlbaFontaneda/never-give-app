package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;
import java.util.UUID;

public class FragmentFiredatabase extends Fragment implements FiredatabaseInterface {

    /************************************* Variables **********************************************/
    protected DatabaseReference mydbRef;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected Profile currentUser;
    protected ArrayList<Event> allEvents = new ArrayList<>();

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
     * Borra dentro de un evento, el usuario logueado.
     */
    private ValueEventListener removeCurrentUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                if(memberSnapshot.getValue(Profile.class).getID().equals(mAuth.getCurrentUser().getUid())){
                    memberSnapshot.getRef().removeValue();
                }
            }
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
                allEvents.add(eventLoaded);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    /************************************* Methods ************************************************/
    /**
     * Actualiza el usuario logueado para poder trabajar con él como un objeto Profile
     */
    protected void loadCurrentUser() {
        mydbRef = database.getReference(usersKey).child(mAuth.getCurrentUser().getUid());
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
     * Introduce un nuevo dato u objeto (conjunto de datos) a la base de datos de Firebase con un
     * identificador único.
     * @param key dónde se quiere meter el nuevo dato (Perfil o Evento)
     * @param data objeto que se quiere introducir a la base de datos
     */
    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }

    /**
     * Se añade un nuevo usuario a la lista de miembros del evento.
     *  - Se introduce el usuario logueado dentro de la lista de miembros del evento
     *  - Se introduce el evento dentro de la lista de eventos del usuario logueado
     * @param event
     */
    protected void addCurrentUserToEvent(Event event) {
        // Se introduce un nuevo usuario (el usuario logueado) dentro de la lista de Perfiles
        // @members, dentro del evento @event
        database.getReference(eventsKey).child(event.getID()).child("members")
                .push().setValue(currentUser);

        // Se introduce un nuevo evento (@event) dentro de la lista @targetedEvents,
        // dentro del usuario logueado
        database.getReference(usersKey).child(mAuth.getCurrentUser().getUid()).child("targetedEvents")
                .push().setValue(event);
    }

    /**
     * Se desapunta el usuario actual de un evento.
     *  - Se borra el usuario del evento dentro de Firebase
     *  - Se borra el evento del usuario dentro de Firebase
     * @param event evento que se desea borrar
     */
    protected void removeCurrentUserFromEvent(final Event event) {
        // Query que devuelve todos los usuarios dentro de un evento
        mydbRef = database.getReference(eventsKey).child(event.getID()).child("members");
        mydbRef.addListenerForSingleValueEvent(removeCurrentUser);

        // Query que devuelve todos los eventos dentro del usuario logueado
        mydbRef = database.getReference(usersKey).child(mAuth.getCurrentUser().getUid()).child("targetedEvents");
        /** Borra dentro del usuario logueado, un evento. **/
        mydbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    if(eventSnapshot.getValue(Event.class).getID().equals(event.getID())) {
                        eventSnapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
