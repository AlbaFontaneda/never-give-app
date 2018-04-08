package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.os.CountDownTimer;
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

    private int position;
    private int pagina;

    private DataBaseContract db;
    private ArrayList<Exercise> ejercicios;

    private static final long START_TIME = 5000;
    private long timeLeft = START_TIME;
    private CountDownTimer timer;
    private boolean timerRunning;
    private TextView countDown;
    private ProgressBar progressBar;


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
        countDown = (TextView)rootView.findViewById(R.id.temporizador);
        ImageButton play = (ImageButton)rootView.findViewById(R.id.play);
        ImageButton stop = (ImageButton)rootView.findViewById(R.id.stop);
        ImageButton pause = (ImageButton)rootView.findViewById(R.id.pause);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setProgress(100);

        updateCountDown();

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
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

    /** ESTO HACE PUTAS COSAS RARAS. NO TIENEN SENTIDO **/
    private void startTimer(){
        // Tiempo en milisegundos, pasa cada 1000ms, es decir, cada segundo
        timer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long msUntilFinished) {
                timeLeft = msUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                //progressBar.setProgress(0);
                //countDown.setText("00:00");
                timerRunning = false;
            }
        }.start();

        timerRunning = true;
    }

    private void pauseTimer(){
        timer.cancel();
        timerRunning = false;
    }

    private void resetTimer(){
        timeLeft = START_TIME;
        timer.cancel();
        updateCountDown();
        timerRunning = false;
    }

    private void updateCountDown() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;

        progressBar.setProgress(seconds*100/((int)START_TIME/1000));
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDown.setText(timeLeftFormatted);
    }
}
