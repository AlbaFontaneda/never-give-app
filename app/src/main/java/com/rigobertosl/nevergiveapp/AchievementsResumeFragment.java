package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementsResumeFragment extends Fragment {

    private DataBaseContract db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DataBaseContract(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_achievements_points, container, false);
        TextView achievementsPoints = (TextView)rootView.findViewById(R.id.achievements_points);
        TextView numExercises = (TextView)rootView.findViewById(R.id.num_exercises);

        db.open();
        numExercises.setText(String.valueOf(db.getAllExercisesOfDataBase()));
        achievementsPoints.setText(String.valueOf(db.getTotalPoints()));
        db.close();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_achivements);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        db.open();
        ArrayList<Achievement> achievements = db.getAllAchievementsCompleted();
        db.close();
        RecyclerView.Adapter adapterAchievements = new CustomAchievementAdapter(achievements);
        recyclerView.setAdapter(adapterAchievements);

        return rootView;
    }
}
