package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ExercisesTypeActivity extends AppCompatActivity {
    private DataBaseContract db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final AlertDialog.Builder builder = new AlertDialog.Builder(ExercisesTypeActivity.this);
                final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
                final AlertDialog dialog = builder.create();
                dialog.setView(dialogLayout);
                dialog.show();

                final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
                final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

                volver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db = new DataBaseContract(ExercisesTypeActivity.this);
                        db.open();
                        db.deleteTable(TrainingActivity.trainingTable, true);
                        db.close();
                        Intent intent = new Intent(ExercisesTypeActivity.this, TrainingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                quedarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        RadioButton pechoRadioButton = (RadioButton) findViewById(R.id.radioButtonPecho);
        RadioButton espaldaRadioButton = (RadioButton) findViewById(R.id.radioButtonEspalda);
        RadioButton tricepsRadioButton = (RadioButton) findViewById(R.id.radioButtonTriceps);
        RadioButton bicepsRadioButton = (RadioButton) findViewById(R.id.radioButtonBiceps);
        RadioButton abdominalesRadioButton = (RadioButton) findViewById(R.id.radioButtonAbdominales);
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
