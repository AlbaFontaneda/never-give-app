package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;

public class EspaldaActivity extends AppCompatActivity {
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
        db.open();
        if(!db.controlExerciseInput(TrainingActivity.lastRowId)) {
            fab.setVisibility(View.VISIBLE);
        }
        db.close();

        String[] espaldaExercises = getResources().getStringArray(R.array.all_exercises_titles);

        final TextView textUno = findViewById(R.id.ejercicioUnoText);
        textUno.setText(espaldaExercises[0]);

        final TextView textDos = findViewById(R.id.ejercicioDosText);
        textDos.setText(espaldaExercises[1]);

        final TextView textTres = findViewById(R.id.ejercicioTresText);
        textTres.setText(espaldaExercises[2]);

        final TextView textCuatro = findViewById(R.id.ejercicioCuatroText);
        textCuatro.setText(espaldaExercises[3]);


        final ImageView imageUno = findViewById(R.id.ejercicioUnoImage);
        imageUno.setImageBitmap(setImage(textUno.getText().toString()));

        final ImageView imageDos = findViewById(R.id.ejercicioDosImage);
        imageDos.setImageBitmap(setImage(textDos.getText().toString()));

        final ImageView imageTres = findViewById(R.id.ejercicioTresImage);
        imageTres.setImageBitmap(setImage(textTres.getText().toString()));

        final ImageView imageCuatro = findViewById(R.id.ejercicioCuatroImage);
        imageCuatro.setImageBitmap(setImage(textCuatro.getText().toString()));


        LinearLayout unoLinear = (LinearLayout) findViewById(R.id.ejercicioUno);
        LinearLayout dosCerradoLinear = (LinearLayout) findViewById(R.id.ejercicioDos);
        LinearLayout tresLinear = (LinearLayout) findViewById(R.id.ejercicioTres);
        LinearLayout cuatroLinear = (LinearLayout) findViewById(R.id.ejercicioCuatro);

        unoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, textUno.getText().toString());
            }
        });
        dosCerradoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, textDos.getText().toString());
            }
        });
        tresLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, textTres.getText().toString());
            }
        });
        cuatroLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, textCuatro.getText().toString());
            }
        });

    }

    public Bitmap setImage(String exerciseName) {
        db.open();
        byte[] b = db.getExerciseImage(exerciseName);
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        db.close();
        return bmp;
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

        db.open();
        final byte[] image = db.getExerciseImage(name);
        final String description = db.getExerciseDescription(name);
        db.close();

        descansoEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDatePicker(v, descansoEditText);
            }
        });

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
                    long id = db.createTableListTraining(name, numSeries, numRepeticiones, tiempoDescanso, "espalda", image, description);
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

    public void openDatePicker(View view, final EditText descansoEditText) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_custom_timepicker, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final int[] selectedMinute = {0};
        final int[] selectedSeconds = {0};

        NumberPicker minutosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.minutos_picker);
        NumberPicker segundosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.segundos_picker);

        final Button continuar = (Button) dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button) dialogLayout.findViewById(R.id.button_cancel);

        minutosPikcer.setValue(0);
        minutosPikcer.setMinValue(0);
        minutosPikcer.setMaxValue(5);
        minutosPikcer.setWrapSelectorWheel(true);

        minutosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                selectedMinute[0] = newVal;
            }
        });

        segundosPikcer.setValue(00);
        segundosPikcer.setMinValue(0);
        segundosPikcer.setMaxValue(59);
        segundosPikcer.setWrapSelectorWheel(true);

        segundosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                selectedSeconds[0] = newVal;
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedSeconds[0] == 0 && selectedMinute[0] == 0){
                    Toast.makeText(getApplicationContext(), "Debe tomarse un descanso entre serie y serie.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", selectedMinute[0], selectedSeconds[0]);
                    descansoEditText.setText(timeLeftFormatted);
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

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(EspaldaActivity.this, ExercisesTypeActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}
