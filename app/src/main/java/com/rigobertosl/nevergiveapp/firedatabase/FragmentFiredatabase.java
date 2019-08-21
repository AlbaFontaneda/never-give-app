package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.UUID;

public class FragmentFiredatabase extends Fragment implements FiredatabaseInterface {

    protected DatabaseReference mydbRef;
    protected Profile host;
    protected ValueEventListener getCurrentHost = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            host = dataSnapshot.getValue(Profile.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    /************************************* Methods ************************************************/
    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }


}
