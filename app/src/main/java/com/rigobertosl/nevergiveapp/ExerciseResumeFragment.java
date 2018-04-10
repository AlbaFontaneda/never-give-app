package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Integer.valueOf;

public class ExerciseResumeFragment extends Fragment {

    private long tableID;
    private int pagina;

    private DataBaseContract db;
    private ArrayList<Exercise> ejercicios;

    private static final long START_TIME = 10000;
    private long timeLeft = START_TIME;
    private TextView countDown;
    private ProgressBar progressBar;

    private MyCountDownTimer mycounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DataBaseContract(getActivity());
        db.open();
        tableID = (long) getActivity().getIntent().getSerializableExtra("tablaID");
        pagina = valueOf(getArguments().getInt("page_position"));
        TrainingTable trainingTable = db.getTrainingTableByID(tableID);

        ejercicios = db.getAllExercisesFromTable(trainingTable);
        Exercise ejercicio = ejercicios.get(pagina);

        View rootView = inflater.inflate(R.layout.fragment_table_resume, container, false);

        TextView exerciseTitle = (TextView)rootView.findViewById(R.id.titleExercise);
        TextView exerciseSeries = (TextView)rootView.findViewById(R.id.series);
        TextView exerciseRep = (TextView)rootView.findViewById(R.id.repeticiones);
        countDown = (TextView)rootView.findViewById(R.id.temporizador);
        ImageButton play = (ImageButton)rootView.findViewById(R.id.play);
        ImageButton stop = (ImageButton)rootView.findViewById(R.id.stop);
        ImageButton pause = (ImageButton)rootView.findViewById(R.id.pause);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        //updateCountDown();
        progressBar.setProgress(100);
        mycounter = new MyCountDownTimer(START_TIME, 1000);
        RefreshTimer();

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycounter.reset();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycounter.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycounter.pause();
            }
        });

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

    public void RefreshTimer()
    {
        final Handler handler = new Handler();
        final Runnable counter = new Runnable(){

            public void run(){
                updateCountDown(mycounter.getCurrentTime());
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(counter, 100);
    }


    private void updateCountDown(long timeLeft) {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;

        progressBar.setProgress(seconds*100/((int)START_TIME/1000));
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDown.setText(timeLeftFormatted);
    }

}
