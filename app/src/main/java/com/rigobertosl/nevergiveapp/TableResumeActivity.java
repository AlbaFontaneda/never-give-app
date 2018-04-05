package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPicker;

import java.util.ArrayList;
import java.util.List;

public class TableResumeActivity extends AppCompatActivity {

    private Context mContext;
    private DataBaseContract db;
    private ArrayList<TrainingTable> trainingTable;
    private int position;
    private RecyclerView recyclerView;
    private ExerciseResumeAdapter exerciseResumeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_resume);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        trainingTable = db.getAllTables();
        position = (int) getIntent().getSerializableExtra("position");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(trainingTable.get(position).getName());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, trainingTable.get(position));
            }
        });
    }

    public void openDialog(View view, TrainingTable table) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_edit_table, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.setTitle(table.getName());
        dialog.show();

        final EditText tableName = (EditText) dialogLayout.findViewById(R.id.table_name);
        EditText tableDays = (EditText) dialogLayout.findViewById(R.id.table_days);
        final Button editar = (Button)dialogLayout.findViewById(R.id.button_edit);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);
        tableName.setText(table.getName());
        tableDays.setText(table.getDays());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
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

                db.editTable(trainingTable.get(position), tableName.getText().toString(), (ArrayList<Exercise>) exerciseResumeAdapter.getExercisesEdited());

                dialog.cancel();
                finish();
                startActivity(getIntent());
            }
        });
    }

    public static class ExerciseResumeAdapter extends RecyclerView.Adapter<ExerciseResumeAdapter.MyViewHolder> {

        private ArrayList<Exercise> exercises;
        private  ArrayList<Exercise> exercisesEdited;

        public ExerciseResumeAdapter(ArrayList<Exercise> exercises) {
            this.exercises = exercises;
            exercisesEdited = exercises;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_from_table_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            //Título (no editable)
            holder.title.setText(exercises.get(position).getNombre());
            // Editables
            holder.nSeries.setText(exercises.get(position).getSeries());
            holder.nRepeticiones.setText(exercises.get(position).getRepeticiones());
            holder.nDescanso.setText(exercises.get(position).getDescanso());

        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        public  ArrayList<Exercise> getExercisesEdited(){
            return exercisesEdited;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private CardView cardView;
            private TextView title, series, repeticiones, descanso;
            private EditText nSeries, nRepeticiones, nDescanso;

            MyViewHolder(View view) {
                super(view);

                cardView = (CardView)view.findViewById(R.id.card_view);

                title = (TextView)view.findViewById(R.id.nombre_ejercicio);
                series = (TextView)view.findViewById(R.id.series);
                repeticiones = (TextView)view.findViewById(R.id.repeticiones);
                descanso = (TextView)view.findViewById(R.id.descanso);

                nSeries = (EditText)view.findViewById(R.id.num_series);
                nSeries.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        exercisesEdited.get(getAdapterPosition()).setSeries(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                nRepeticiones = (EditText)view.findViewById(R.id.num_repeticiones);
                nRepeticiones.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        exercisesEdited.get(getAdapterPosition()).setRepeticiones(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                nDescanso = (EditText)view.findViewById(R.id.num_descanso);
                nDescanso.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        exercisesEdited.get(getAdapterPosition()).setDescanso(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        }
    }
}
