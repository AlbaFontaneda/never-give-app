package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class TableResumeActivity extends AppCompatActivity {

    private Context mContext;
    private DataBaseContract db;
    private long tableID;
    private RecyclerView recyclerView;
    private ExerciseResumeAdapter exerciseResumeAdapter;
    private ExercisePageAdapter mExercisePageAdapter;
    private ViewPager mViewPager;
    private int numPaginas;
    private TrainingTable trainingTable;
    public String weekDays = "";
    EditText tableDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_resume);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        tableID = (long) getIntent().getSerializableExtra("tablaID");
        trainingTable = db.getTrainingTableByID(tableID);
        db.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numPaginas = (int)db.getAllExercisesFromTable(trainingTable).size();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<numPaginas; i++){
            Fragment f = new ExerciseResumeFragment();
            fragments.add(f);
        }

        mExercisePageAdapter = new ExercisePageAdapter(getSupportFragmentManager(), fragments);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mExercisePageAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, trainingTable);
            }
        });
    }

    public void openDialog(View view, TrainingTable table) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_edit_table, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final EditText tableName = (EditText) dialogLayout.findViewById(R.id.table_name);
        tableDays = (EditText) dialogLayout.findViewById(R.id.table_days);
        final Button editar = (Button)dialogLayout.findViewById(R.id.button_edit);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);
        tableName.setText(table.getName());
        tableDays.setText(table.getDays());
        tableDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDays();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView)dialogLayout.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);
        ArrayList<Exercise> exerciseList = db.getAllExercisesFromTable(table);
        exerciseResumeAdapter = new ExerciseResumeAdapter(exerciseList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseResumeAdapter);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                db.editTable(trainingTable, tableName.getText().toString(), tableDays.getText().toString(), (ArrayList<Exercise>) exerciseResumeAdapter.getExercisesEdited());
                dialog.cancel();
                finish();
                startActivity(getIntent());
            }
        });
    }

    public void selectDays() {
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

                weekDays = myDays.toString();
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

    /** ADAPTADOR DEL VIEWPAGER **/
    public class ExercisePageAdapter extends FragmentPagerAdapter {

        private  ArrayList<Fragment> fragments;

        public ExercisePageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            ExerciseResumeFragment fragment = (ExerciseResumeFragment) fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return numPaginas;
        }
    }


    /** ADAPTADOR DEL RECYCLERVIEW **/
    public static class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.MyViewHolder> {

        private Exercise exercise;

        public CheckboxAdapter(Exercise exercise){
            this.exercise = exercise;
        }

        @Override
        public CheckboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox, parent, false);
            return new CheckboxAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CheckboxAdapter.MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return valueOf(exercise.getSeries());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
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
        Intent setIntent;
        if((boolean) getIntent().getSerializableExtra("fromTraining")){
            setIntent = new Intent(TableResumeActivity.this, TrainingActivity.class);
        }else{
            setIntent = new Intent(TableResumeActivity.this, MainActivity.class);

        }
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}
