package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExercisesTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout pechoLinearLayout = (LinearLayout)findViewById(R.id.pecho);
        LinearLayout espaldaLinearLayout = (LinearLayout) findViewById(R.id.espalda);
        LinearLayout bicepsLinearLayout = (LinearLayout) findViewById(R.id.biceps);
        LinearLayout tricepsLinearLayout = (LinearLayout) findViewById(R.id.triceps);
        LinearLayout hombroLinearLayout = (LinearLayout) findViewById(R.id.hombro);
        LinearLayout piernaLinearLayout = (LinearLayout) findViewById(R.id.pierna);

        pechoLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, ChestActivity.class));
            }
        });
        espaldaLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, EspaldaActivity.class));
            }
        });

    }

}
