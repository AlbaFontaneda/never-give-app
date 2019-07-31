package com.rigobertosl.nevergiveapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class FireBaseController {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public FireBaseController(){
    }

    public void addData(String key, String data) {
        myRef = database.getReference(key);
        //myRef.setValue(data);

        myRef.child(UUID.randomUUID().toString()).setValue(data);
    }
}
