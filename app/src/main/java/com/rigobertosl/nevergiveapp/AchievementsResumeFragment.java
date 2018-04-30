package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class AchievementsResumeFragment extends Fragment {

    private DataBaseContract db;
    private int weekDay;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weekDay = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());


        View rootView = inflater.inflate(R.layout.fragment_achievements_points, container, false);

        TextView achievementsPoints = (TextView)rootView.findViewById(R.id.achievements_points);
        TextView numExercises = (TextView)rootView.findViewById(R.id.num_exercises);
        TextView numKcal = (TextView)rootView.findViewById(R.id.num_kcal);
        TextView numDuration = (TextView)rootView.findViewById(R.id.duration);

        db.open();
        numExercises.setText(String.valueOf(db.getAllExercisesOfDataBase().size()));
        achievementsPoints.setText(String.valueOf(db.getTotalPoints()));
        numDuration.setText(db.getTimeOfTables());
        db.close();

        return rootView;
    }
}
