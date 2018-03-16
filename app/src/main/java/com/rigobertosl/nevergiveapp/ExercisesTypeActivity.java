package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ExercisesTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView pechoLink = (TextView) findViewById(R.id.pecho_text_view);
        TextView espaldaLink = (TextView) findViewById(R.id.espalda_text_view);
        TextView tricepsLink = (TextView) findViewById(R.id.triceps_text_view);
        TextView bicepsLink = (TextView) findViewById(R.id.biceps_text_view);
        TextView piernaLink = (TextView) findViewById(R.id.pierna_text_view);
        TextView hombroLink = (TextView) findViewById(R.id.hombro_text_view);

        pechoLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ExercisesTypeActivity.this, ChestActivity.class));
            }
        });
    }

}
