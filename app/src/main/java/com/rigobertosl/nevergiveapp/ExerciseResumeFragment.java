package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class ExerciseResumeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int position;
    private DataBaseContract db;
    private ArrayList<Exercise> ejercicios;
    private int pagina;
    private int timer = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        position = (int) getActivity().getIntent().getSerializableExtra("position");
        pagina = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());
        db.open();

        ArrayList<TrainingTable> trainingTable = db.getAllTables();
        ejercicios = db.getAllExercisesFromTable(trainingTable.get(position));
        Exercise ejercicio = ejercicios.get(pagina);

        View rootView = inflater.inflate(R.layout.fragment_table_resume, container, false);

        TextView exerciseTitle = (TextView)rootView.findViewById(R.id.titleExercise);
        TextView exerciseSeries = (TextView)rootView.findViewById(R.id.series);
        TextView exerciseRep = (TextView)rootView.findViewById(R.id.repeticiones);


        final ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setProgress(timer);
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer++;
                progressBar.setProgress((int)timer*100/(5000/1000));
            }

            @Override
            public void onFinish() {
                //Do what you want
                timer++;
                progressBar.setProgress(100);
            }
        };
        countDownTimer.start();

        //TextView exerciseDescanso = (TextView)rootView.findViewById(R.id.descanso);

        exerciseTitle.setText((String) ejercicio.getNombre());
        exerciseSeries.setText((String) ejercicio.getSeries());
        exerciseRep.setText((String) ejercicio.getRepeticiones());
        //exerciseDescanso.setText((String) ejercicio.getDescanso());

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerCheckBoxView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new TableResumeActivity.CheckboxAdapter(ejercicio);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
