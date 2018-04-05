package com.rigobertosl.nevergiveapp;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ChestActivity extends TrainingActivity {
    FloatingActionButton fab;
    private DataBaseContract db;
    public long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);

        db = new DataBaseContract(this);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChestActivity.this, TrainingActivity.class));
            }
        });

        LinearLayout pressSuperiorLinear = (LinearLayout) findViewById(R.id.press_superior);
        LinearLayout pressBancaLinear = (LinearLayout) findViewById(R.id.press_banca);
        LinearLayout poleaAltaLinear = (LinearLayout) findViewById(R.id.polea_alta);
        LinearLayout pullOverLinear = (LinearLayout) findViewById(R.id.pullover);
        LinearLayout fondosLinear = (LinearLayout) findViewById(R.id.fondos);
        LinearLayout pressBarraLinear = (LinearLayout) findViewById(R.id.press_barra);

        pressSuperiorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Aperturas con mancuernas");
            }
        });
        pressBancaLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Press de banca al cuello");
            }
        });
        poleaAltaLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Cruces en polea alta");
            }
        });
        pullOverLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Pullover");
            }
        });
        fondosLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Fondos entre bancos");
            }
        });
        pressBarraLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Press con barra");
            }
        });
    }
    public void openDialog(View view, final String name) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_new_exercise, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

        final EditText seriesEditText = (EditText)dialogLayout.findViewById(R.id.num_series);
        final EditText repeticionesEditText = (EditText)dialogLayout.findViewById(R.id.num_repeticiones);
        final EditText descansoEditText = (EditText)dialogLayout.findViewById(R.id.tiempo_descanso);

        descansoEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = 00;
                int minute = 00;
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ChestActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        descansoEditText.setText( selectedHour + "' " + selectedMinute +"''");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numSeries = seriesEditText.getText().toString();
                String numRepeticiones = repeticionesEditText.getText().toString();
                String tiempoDescanso = descansoEditText.getText().toString();
                if (numSeries.matches("") || numRepeticiones.matches("") || tiempoDescanso.matches("")) {
                    Toast.makeText(ChestActivity.this,
                            "Necesitas rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    fab.setVisibility(View.VISIBLE);
                    db.open();
                    long id = db.createTableListTraining(name, numSeries, numRepeticiones, tiempoDescanso);
                    rowId = id;
                    db.createTableTraining(TrainingActivity.lastRowId, rowId);
                    db.close();
                    dialog.cancel();

                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
