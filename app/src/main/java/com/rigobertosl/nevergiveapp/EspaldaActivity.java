package com.rigobertosl.nevergiveapp;

import android.content.Intent;
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
import android.widget.Toast;

public class EspaldaActivity extends TrainingActivity {
    FloatingActionButton fab;
    private DataBaseContract db;
    public long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espalda);

        db = new DataBaseContract(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EspaldaActivity.this, TrainingActivity.class));
            }
        });

        LinearLayout dominadasLinear = (LinearLayout) findViewById(R.id.dominadas);
        LinearLayout dominadasCerradoLinear = (LinearLayout) findViewById(R.id.dominadas_cerrado);
        LinearLayout remoBarraLinear = (LinearLayout) findViewById(R.id.remo_barra);
        LinearLayout remoPoleaLinear = (LinearLayout) findViewById(R.id.remo_polea);
        LinearLayout pesoMuertoLinear = (LinearLayout) findViewById(R.id.peso_muerto);
        LinearLayout pulloverBancoLinear = (LinearLayout) findViewById(R.id.pullover_banco);

        dominadasLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Dominadas");
            }
        });
        dominadasCerradoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Dominadas con agarre cerrado");
            }
        });
        remoBarraLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Remo con barra");
            }
        });
        remoPoleaLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Remo en polea sentado");
            }
        });
        pesoMuertoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Peso muerto");
            }
        });
        pulloverBancoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, "Pullover banco declinado");
            }
        });
    }
    public void openDialog(View view, final String name) {
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
                String numSeries = seriesEditText.getText().toString();
                String numRepeticiones = repeticionesEditText.getText().toString();
                String tiempoDescanso = descansoEditText.getText().toString();
                if (numSeries.matches("") || numRepeticiones.matches("") || tiempoDescanso.matches("")) {
                    Toast.makeText(EspaldaActivity.this,
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
