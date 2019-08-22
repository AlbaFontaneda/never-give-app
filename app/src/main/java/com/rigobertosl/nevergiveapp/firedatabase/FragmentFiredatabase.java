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

import java.util.UUID;

public class FragmentFiredatabase extends Fragment implements FiredatabaseInterface {

    /************************************* Variables **********************************************/
    protected DatabaseReference mydbRef;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected Profile currentUser;

    /************************************* Listeners **********************************************/
    private ValueEventListener loadCurrentUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            currentUser = dataSnapshot.getValue(Profile.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    private ValueEventListener removeCurrentUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                if(memberSnapshot.getValue(Profile.class).getID().equals(currentUser.getID())){
                    memberSnapshot.getRef().removeValue();
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    /************************************* Methods ************************************************/
    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }

    protected void loadCurrentUser() {
        mydbRef = database.getReference(usersKey).child(mAuth.getCurrentUser().getUid());
        mydbRef.addListenerForSingleValueEvent(loadCurrentUser);
    }

    public void removeCurrentUserFromEvent(Event event) {
        mydbRef = database.getReference(eventsKey).child(event.getID()).child("members");
        mydbRef.addListenerForSingleValueEvent(removeCurrentUser);
    }

    public void addCurrentUserToEvent(Event event) {
        database.getReference(eventsKey).child(event.getID()).child("members")
                .push().setValue(currentUser);
    }

}
