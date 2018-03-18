package com.rigobertosl.nevergiveapp;

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
import android.widget.Toast;

public class ChestActivity extends TrainingActivity {
    FloatingActionButton fab;
    String numSeries;
    String numRepeticiones;
    String tiempoDescanso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        LinearLayout pressSuperiorLinear = (LinearLayout) findViewById(R.id.press_superior);

        pressSuperiorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });
    }
    public void openDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_new_exercise, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.setTitle("Introduce los datos para el ejercicio");
        dialog.show();

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

        final EditText seriesEditText = (EditText)dialogLayout.findViewById(R.id.num_series);
        final EditText repeticionesEditText = (EditText)dialogLayout.findViewById(R.id.num_repeticiones);
        final EditText descansoEditText = (EditText)dialogLayout.findViewById(R.id.tiempo_descanso);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numSeries = seriesEditText.getText().toString();
                numRepeticiones = repeticionesEditText.getText().toString();
                tiempoDescanso = descansoEditText.getText().toString();
                if (numSeries.matches("") || numRepeticiones.matches("") || tiempoDescanso.matches("")) {
                    Toast.makeText(ChestActivity.this,
                            "Necesitas rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    fab.setVisibility(View.VISIBLE);
                    dialog.cancel();
                    saveData(view);
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

    public void saveData(View view) {
        SQLiteDatabase db = tablaEjercicios.getWritableDatabase();
        String ID = String.valueOf(TrainingActivity.lastRow);
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.DataBaseEntry.COLUMN_SERIES, numSeries);
        values.put(DataBaseContract.DataBaseEntry.COLUMN_REPETICIONES, numRepeticiones);
        values.put(DataBaseContract.DataBaseEntry.COLUMN_DESCANSO, tiempoDescanso);

        String selection = DataBaseContract.DataBaseEntry._ID + " LIKE ?";
        String[] selectionArgs = { ID };

        db.update(DataBaseContract.DataBaseEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        Toast.makeText(this, "Datos de la tabla guardados", Toast.LENGTH_SHORT).show();

    }
}
