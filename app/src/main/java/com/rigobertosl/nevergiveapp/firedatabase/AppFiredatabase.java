package com.rigobertosl.nevergiveapp.firedatabase;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class AppFiredatabase extends AppCompatActivity implements FiredatabaseInterface {

    private DatabaseReference mydbRef;

    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }
}
