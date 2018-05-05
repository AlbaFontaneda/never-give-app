package com.rigobertosl.nevergiveapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Locale;

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

    public boolean exercisesFilled(){
        boolean fill = true;
        for (Exercise exercise : exercisesEdited){
            if(exercise.getRepeticiones().equals("") || exercise.getSeries().equals("")){
                fill = false;
                break;
            }
        }
        return fill;
    }

    public  ArrayList<Exercise> getExercisesEdited(){
        return exercisesEdited;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private EditText nSeries, nRepeticiones, nDescanso;

        MyViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.nombre_ejercicio);

            nSeries = (EditText)view.findViewById(R.id.num_series);
            nSeries.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.equals("")) {
                        exercisesEdited.get(getAdapterPosition()).setSeries(charSequence.toString());
                    }
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
                    if(!charSequence.equals("")) {
                        exercisesEdited.get(getAdapterPosition()).setRepeticiones(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            nDescanso = (EditText)view.findViewById(R.id.num_descanso);
            nDescanso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDatePicker(view, nDescanso);
                }
            });
            nDescanso.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.equals("")){
                        exercisesEdited.get(getAdapterPosition()).setDescanso(charSequence.toString());
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        public void openDatePicker(View view, final EditText descansoEditText){
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            View dialogLayout = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.popup_custom_timepicker, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();

            final int[] selectedMinute = {0};
            final int[] selectedSeconds = {0};

            NumberPicker minutosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.minutos_picker);
            NumberPicker segundosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.segundos_picker);

            final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
            final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

            minutosPikcer.setValue(0);
            minutosPikcer.setMinValue(0);
            minutosPikcer.setMaxValue(5);
            minutosPikcer.setWrapSelectorWheel(true);

            minutosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                    //Display the newly selected number from picker
                    selectedMinute[0] = newVal;
                }
            });

            segundosPikcer.setValue(0);
            segundosPikcer.setMinValue(0);
            segundosPikcer.setMaxValue(59);
            segundosPikcer.setWrapSelectorWheel(true);

            segundosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                    //Display the newly selected number from picker
                    selectedSeconds[0] = newVal;
                }
            });

            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedSeconds[0] == 0 && selectedMinute[0] == 0){
                        Toast.makeText(view.getContext(), "Debe tomarse un descanso entre serie y serie.", Toast.LENGTH_SHORT).show();
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
    }
}