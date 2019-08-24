package com.rigobertosl.nevergiveapp.training.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rigobertosl.nevergiveapp.database.DataBaseContract;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.Exercise;
import com.rigobertosl.nevergiveapp.objects.TrainingTable;
import com.rigobertosl.nevergiveapp.training.adapter.ExerciseResumeAdapter;

import java.util.ArrayList;

public class EditTableActivity extends AppCompatActivity {
    private Context mContext;
    private DataBaseContract db;
    private long tableID;
    private TrainingTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        // Leemos los datos que vamos a utilizar a lo largo de la actividad
        mContext = getApplicationContext();
        tableID = (long) getIntent().getSerializableExtra("tablaID");
        db = new DataBaseContract(mContext);
        db.open();
        table = db.getTrainingTableByID(tableID);
        db.close();



        // Leemos todos los elementos de la actividad
        final EditText tableName = (EditText) findViewById(R.id.table_name);
        final EditText tableDays = (EditText) findViewById(R.id.table_days);
        tableName.setText(table.getName());
        tableDays.setText(table.getDays());
        tableDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDays(tableDays);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);
        ArrayList<Exercise> exerciseList = db.getAllExercisesFromTable(table);
        final ExerciseResumeAdapter exerciseResumeAdapter = new ExerciseResumeAdapter(exerciseList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseResumeAdapter);

        // Botón flotante para acabar la edición
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tableName.getText().toString().equals("") || !exerciseResumeAdapter.exercisesFilled()) {
                    Toast.makeText(EditTableActivity.this,
                            "Necesita rellenar todos los campos.", Toast.LENGTH_LONG).show();
                } else {
                    db.editTable(table, tableName.getText().toString(), tableDays.getText().toString(), (ArrayList<Exercise>) exerciseResumeAdapter.getExercisesEdited());
                    Intent intent = new Intent(mContext, TableResumeActivity.class);
                    intent.putExtra("tablaID", tableID);
                    intent.putExtra("fromTraining", getIntent().getSerializableExtra("fromTraining"));
                    intent.putExtra("isDefault", false);
                    mContext.startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void selectDays(final EditText tableDays) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_custom_weekpicker, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final ToggleButton lunes = (ToggleButton)dialogLayout.findViewById(R.id.button_lunes);
        final ToggleButton martes = (ToggleButton)dialogLayout.findViewById(R.id.button_martes);
        final ToggleButton miercoles = (ToggleButton)dialogLayout.findViewById(R.id.button_miercoles);
        final ToggleButton jueves = (ToggleButton)dialogLayout.findViewById(R.id.button_jueves);
        final ToggleButton viernes = (ToggleButton)dialogLayout.findViewById(R.id.button_viernes);
        final ToggleButton sabado = (ToggleButton)dialogLayout.findViewById(R.id.button_sabado);
        final ToggleButton domingo = (ToggleButton)dialogLayout.findViewById(R.id.button_domingo);

        final CheckBox checkAll = (CheckBox) dialogLayout.findViewById(R.id.check_all);

        final ArrayList<String> myDays = new ArrayList<>();

        final boolean[] lunesChecked = new boolean[1];
        final boolean[] martesChecked = new boolean[1];
        final boolean[] miercolesChecked = new boolean[1];
        final boolean[] juevesChecked = new boolean[1];
        final boolean[] viernesChecked = new boolean[1];
        final boolean[] sabadoChecked = new boolean[1];
        final boolean[] domingoChecked = new boolean[1];

        final boolean[] checks = new boolean[7];

        lunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lunesChecked[0] = true;
                } else {
                    lunesChecked[0] = false;
                }
                checks[0] = lunesChecked[0];
            }
        });

        martes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    martesChecked[0] = true;
                } else {
                    martesChecked[0] = false;
                }
                checks[1] = martesChecked[0];
            }
        });

        miercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    miercolesChecked[0] = true;
                } else {
                    miercolesChecked[0] = false;
                }
                checks[2] = miercolesChecked[0];
            }
        });

        jueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    juevesChecked[0] = true;
                } else {
                    juevesChecked[0] = false;
                }
                checks[3] = juevesChecked[0];
            }
        });

        viernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viernesChecked[0] = true;
                } else {
                    viernesChecked[0] = false;
                }
                checks[4] = viernesChecked[0];
            }
        });

        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sabadoChecked[0] = true;
                } else {
                    sabadoChecked[0] = false;
                }
                checks[5] = sabadoChecked[0];
            }
        });

        domingo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    domingoChecked[0] = true;
                } else {
                    domingoChecked[0] = false;
                }
                checks[6] = domingoChecked[0];
            }
        });

        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lunes.setChecked(true);
                    martes.setChecked(true);
                    miercoles.setChecked(true);
                    jueves.setChecked(true);
                    viernes.setChecked(true);
                    sabado.setChecked(true);
                    domingo.setChecked(true);
                } else {
                    lunes.setChecked(false);
                    martes.setChecked(false);
                    miercoles.setChecked(false);
                    jueves.setChecked(false);
                    viernes.setChecked(false);
                    sabado.setChecked(false);
                    domingo.setChecked(false);
                }
            }
        });

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checks[0] == true){
                    myDays.add("LU");
                }
                if (checks[1] == true) {
                    myDays.add("M");
                }
                if (checks[2] == true) {
                    myDays.add("X");
                }
                if (checks[3] == true) {
                    myDays.add("JU");
                }
                if (checks[4] == true) {
                    myDays.add("VI");
                }
                if (checks[5] == true) {
                    myDays.add("SA");
                }
                if (checks[6] == true) {
                    myDays.add("DO");
                }

                String weekDays = myDays.toString();
                if(!weekDays.equals("[]")) {
                    tableDays.setText(weekDays.substring(1, weekDays.length() - 1));
                }
                dialog.cancel();

            }
        });

        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditTableActivity.this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();
        TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
        textoAviso.setText(R.string.avisoVolver);
        final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
        final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TableResumeActivity.class);
                intent.putExtra("tablaID", tableID);
                intent.putExtra("fromTraining", getIntent().getSerializableExtra("fromTraining"));
                intent.putExtra("isDefault", false);
                mContext.startActivity(intent);
                dialog.cancel();
                finish();
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
