package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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
        db = new DataBaseContract(ExercisesTypeActivity.this);
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

        String[] exerciseTypes = getResources().getStringArray(R.array.exerciseTypes);

        TextView textEspalda = findViewById(R.id.exerciseEspalda);
        textEspalda.setText(exerciseTypes[0]);

        TextView textPecho = findViewById(R.id.exercisePecho);
        textPecho.setText(exerciseTypes[1]);

        TextView textBiceps = findViewById(R.id.exerciseBiceps);
        textBiceps.setText(exerciseTypes[2]);

        TextView textTriceps = findViewById(R.id.exerciseTriceps);
        textTriceps.setText(exerciseTypes[3]);

        TextView textPierna = findViewById(R.id.exercisePierna);
        textPierna.setText(exerciseTypes[4]);

        TextView textAbs = findViewById(R.id.exerciseAbdominales);
        textAbs.setText(exerciseTypes[5]);

        LinearLayout espaldaRadioButton = (LinearLayout) findViewById(R.id.radioButtonEspalda);
        LinearLayout pechoRadioButton = (LinearLayout) findViewById(R.id.radioButtonPecho);
        LinearLayout bicepsRadioButton = (LinearLayout) findViewById(R.id.radioButtonBiceps);
        LinearLayout tricepsRadioButton = (LinearLayout) findViewById(R.id.radioButtonTriceps);
        LinearLayout piernaRadioButton = (LinearLayout) findViewById(R.id.radioButtonPierna);
        LinearLayout abdominalesRadioButton = (LinearLayout) findViewById(R.id.radioButtonAbdominales);

        espaldaRadioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, EspaldaActivity.class));
            }
        });

        pechoRadioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ExercisesTypeActivity.this, ChestActivity.class));
            }
        });
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

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
}
