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
        }

        String[] achievementTitles,  achievementDescription, achievementPoints;

        // SUSTITUIR POR LA BASE DE DATOS Y FILTRAR DENTRO DEL CustomAchievementAdapter
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();

        if (page == 1){
            achievementTitles = getResources().getStringArray(R.array.achievementsFoodsTitles);
            achievementDescription = getResources().getStringArray(R.array.achievementsFoodsDescriptions);
            achievementPoints = getResources().getStringArray(R.array.achievementsFoodsPoints);

            for (int i = 0; i < achievementTitles.length; i++){
                Achievement newAchievement = new Achievement(achievementTitles[i], achievementDescription[i], achievementType, achievementPoints[i]);
                achievements.add(newAchievement);
            }
            RecyclerView.Adapter adapterTrain = new CustomAchievementAdapter(getContext(), achievements, achievementType);
            recyclerView.setAdapter(adapterTrain);

        } else if (page == 2){
            achievementTitles = getResources().getStringArray(R.array.achievementsTrainingTitles);
            achievementDescription = getResources().getStringArray(R.array.achievementsTrainingDescriptions);
            achievementPoints = getResources().getStringArray(R.array.achievementsTrainingPoints);
            for (int i = 0; i < achievementTitles.length ; i++){
                Achievement newAchievement = new Achievement(achievementTitles[i], achievementDescription[i], achievementType, achievementPoints[i]);
                achievements.add(newAchievement);
            }
            RecyclerView.Adapter adapterTrain = new CustomAchievementAdapter(getContext(), achievements, achievementType);
            recyclerView.setAdapter(adapterTrain);
        }

        return rootView;
    }
}
