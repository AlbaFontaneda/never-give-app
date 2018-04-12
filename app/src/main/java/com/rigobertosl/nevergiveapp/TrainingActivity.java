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
import android.widget.EditText;
import android.widget.Toast;

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
        final EditText tableDaysEditText = (EditText)dialogLayout.findViewById(R.id.table_days);

        tableDaysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putBoolean(RecurrencePickerDialogFragment.BUNDLE_HIDE_SWITCH_BUTTON, true);
                RecurrencePickerDialogFragment rpd = new RecurrencePickerDialogFragment();
                rpd.setArguments(bundle);
                rpd.setOnRecurrenceSetListener(new RecurrencePickerDialogFragment.OnRecurrenceSetListener(){
                    @Override
                    public void onRecurrenceSet(String rrule) {
                        if (rrule != null && rrule.length() > 0) {
                            weekDays = rrule.substring(rrule.lastIndexOf("=") + 1);
                            char[] myDaysChars = weekDays.toCharArray();
                            ArrayList<String> myDays = new ArrayList<>();
                            for(int i = 0; i < myDaysChars.length; i++) {
                                if(myDaysChars[i] == 'S' && myDaysChars[i+1] == 'U'){
                                    myDays.add("DO");
                                }
                                else if(myDaysChars[i] == 'M'){
                                    myDays.add("LU");
                                }
                                else if(myDaysChars[i] == 'T' && myDaysChars[i+1] == 'U'){
                                    myDays.add("M");
                                }
                                else if(myDaysChars[i] == 'W'){
                                    myDays.add("X");
                                }
                                else if(myDaysChars[i] == 'T' && myDaysChars[i+1] == 'H'){
                                    myDays.add("JU");
                                }
                                else if(myDaysChars[i] == 'F'){
                                    myDays.add("VI");
                                }
                                else if(myDaysChars[i] == 'S' && myDaysChars[i+1] == 'A'){
                                    myDays.add("SA");
                                }
                            }
                            if(myDays.get(0).equals("DO")) {
                                myDays.remove(0);
                                myDays.add("DO");
                            }

                            weekDays = myDays.toString();
                            tableDaysEditText.setText(weekDays.substring(1, weekDays.length()-1));
                        }
                    }
                });
                rpd.show(fm, "recurrencePickerDialogFragment");
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


