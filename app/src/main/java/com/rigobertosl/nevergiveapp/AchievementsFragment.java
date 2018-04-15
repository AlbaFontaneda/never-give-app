package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class AchievementsFragment extends Fragment {

    private DataBaseContract db;
    private int page;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        page = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_achivements);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        String achievementType = "";
        if (page == 1){
            achievementType = "foods";
        }else if (page == 2){
            achievementType = "training";
        }else if (page == 3){
            achievementType = "events";
        }

        // SUSTITUIR POR LA BASE DE DATOS Y FILTRAR DENTRO DEL CustomAchievementAdapter
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();
        Achievement newAchievement = new Achievement("Crea tu primera tabla", "Ve a las tablas de entrenamiento y crea tu primera tabla", "entrenamiento", 1);
        achievements.add(newAchievement);

        RecyclerView.Adapter adapterTrain = new CustomAchievementAdapter(getContext(), achievements, achievementType);
        recyclerView.setAdapter(adapterTrain);

        return rootView;
    }
}
