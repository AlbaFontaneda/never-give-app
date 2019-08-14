package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.v4.app.Fragment;

import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class FragmentFiredatabase extends Fragment implements FiredatabaseInterface {

    protected DatabaseReference mydbRef;

    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }

}
