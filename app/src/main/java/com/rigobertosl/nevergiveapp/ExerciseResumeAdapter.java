package com.rigobertosl.nevergiveapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciseResumeAdapter extends RecyclerView.Adapter<ExerciseResumeAdapter.MyViewHolder> {

    private ArrayList<Exercise> exercises;
    private  ArrayList<Exercise> exercisesEdited;

    public ExerciseResumeAdapter(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        exercisesEdited = exercises;
    }

    @Override
    public ExerciseResumeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_from_table_card, parent, false);
        return new ExerciseResumeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExerciseResumeAdapter.MyViewHolder holder, final int position) {

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