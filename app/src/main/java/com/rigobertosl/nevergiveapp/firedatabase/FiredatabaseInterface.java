package com.rigobertosl.nevergiveapp.firedatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public interface FiredatabaseInterface {

    String usersKey = "USERS";
    String eventsKey = "EVENTS";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String getUid();
}
