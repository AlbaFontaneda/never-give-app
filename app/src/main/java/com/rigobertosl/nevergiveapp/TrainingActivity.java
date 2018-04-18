package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;

import java.sql.Time;
import java.util.ArrayList;

public class TrainingActivity extends MainActivity {
    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;
    public FloatingActionButton fab;
    private DataBaseContract db;
    public static TrainingTable trainingTable;
    public static long lastRowId;
    public String weekDays = "";
    EditText tableDaysEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        db = new DataBaseContract(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Función para volver a la pantalla anterior
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager());
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        TabLayout trainTabs = (TabLayout) findViewById(R.id.tabs);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(trainTabs){
            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }
        });
        trainTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));

        db.open();
        if(db.checkifTableisEmpty()) {
            String[] tableNames = getResources().getStringArray(R.array.defaultTables);
            String[] pechoEjercicios = getResources().getStringArray(R.array.ejerciciosPecho);
            String[] espaldaEjercicios = getResources().getStringArray(R.array.ejerciciosEspalda);
            String[] ejerciciosValues = getResources().getStringArray(R.array.ejerciciosValues);
            for(String name: tableNames) {
                TrainingTable defaultTable = db.createDefaultTable(name);
                if(defaultTable.getId() == 1) {
                    for(String ejercicio: pechoEjercicios) {
                        long ejerciciosPechoId = db.createTableListDefaultTraining(ejercicio, ejerciciosValues[0], ejerciciosValues[1], null);
                        db.createDefaultLinkTraining(defaultTable.getId(), ejerciciosPechoId);
                    }
                } else if(defaultTable.getId() == 2) {
                    for (String ejercicio : espaldaEjercicios) {
                        long ejerciciosEspaldaId = db.createTableListDefaultTraining(ejercicio, ejerciciosValues[0], ejerciciosValues[1], null);
                        db.createDefaultLinkTraining(defaultTable.getId(), ejerciciosEspaldaId);
                    }
                }
            }
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(TrainingActivity.this,
                    "Settings pulsado", Toast.LENGTH_LONG).show();
            db.resetTrainingTables();
            finish();
            startActivity(getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_new_table, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        //dialog.setTitle("Nueva Tabla");
        dialog.show();

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

        final EditText tableNameEditText = (EditText)dialogLayout.findViewById(R.id.table_name);
        tableDaysEditText = (EditText)dialogLayout.findViewById(R.id.table_days);

        tableDaysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDays();
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tableName = tableNameEditText.getText().toString();
                String tableDays = "";
                if(!weekDays.equals("")){
                    tableDays = weekDays.substring(1, weekDays.length()-1);
                }
                if (tableName.matches("") || tableDays.matches("")) {
                    Toast.makeText(TrainingActivity.this,
                            "Necesitas rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(TrainingActivity.this, ExercisesTypeActivity.class));
                    db.open();
                    trainingTable = db.createTableNameTraining(tableName, tableDays);
                    long id = trainingTable.getId();
                    lastRowId = id;
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
    public void selectDays() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
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

                weekDays = myDays.toString();
                tableDaysEditText.setText(weekDays.substring(1, weekDays.length()-1));
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

    /** Transiciones entre tabs y fragmentos **/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return TrainingFragment.newInstance(position);
                }
                case 1: {
                    return TrainingFragmentDefault.newInstance(position);
                }
                default:
                    return new Fragment();
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     * Enlace: https://stackoverflow.com/questions/3141996/android-how-to-override-the-back-button-so-it-doesnt-finish-my-activity?rq=1
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
        Intent setIntent = new Intent(TrainingActivity.this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}


