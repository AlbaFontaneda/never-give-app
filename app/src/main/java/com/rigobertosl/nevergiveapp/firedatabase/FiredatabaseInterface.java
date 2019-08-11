package com.rigobertosl.nevergiveapp.firedatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.rigobertosl.nevergiveapp.DataBaseContract;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;
import java.util.HashMap;

public interface FiredatabaseInterface {

    String usersKey = "USERS";
    String eventsKey = "EVENTS";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    void addDataToFirebase(String key, Object data);
}
