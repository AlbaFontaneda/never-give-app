package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ExercisesTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RadioButton pechoRadioButton = (RadioButton) findViewById(R.id.radioButtonPecho);
        RadioButton espaldaRadioButton = (RadioButton) findViewById(R.id.radioButtonEspalda);
        RadioButton tricepsRadioButton = (RadioButton) findViewById(R.id.radioButtonTriceps);
        RadioButton bicepsRadioButton = (RadioButton) findViewById(R.id.radioButtonBiceps);
        RadioButton piernaRadioButton = (RadioButton) findViewById(R.id.radioButtonPierna);

        pechoRadioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, ChestActivity.class));
            }
        });
        espaldaRadioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, EspaldaActivity.class));
            }
        });
    }

}
