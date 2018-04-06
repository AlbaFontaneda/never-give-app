package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class MainFragment extends Fragment{

    private DataBaseContract db;
    private int weekDay;
    private String filterDay;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weekDay = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());
        db.open();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerViewTraining = (RecyclerView) rootView.findViewById(R.id.list_training);
        //RecyclerView recyclerViewFoods = (RecyclerView) rootView.findViewById(R.id.list_food);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTraining.setLayoutManager(layoutManager);
        //RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        //recyclerViewFoods.setLayoutManager(layoutManager2);

        if(weekDay == 0) {
            filterDay = "LU";
        }else if(weekDay == 1) {
            filterDay = "M";
        }else if(weekDay == 2) {
            filterDay = "X";
        }else if(weekDay == 3) {
            filterDay = "JU";
        }else if(weekDay == 4) {
            filterDay = "VI";
        }else if(weekDay == 5) {
            filterDay = "SA";
        }else if(weekDay == 6) {
            filterDay = "DO";
        }

        ArrayList<TrainingTable> trainingTable = db.getAllTablesFilterByDay(filterDay);
        ArrayList<TrainingTable> trainingTable2 = db.getAllTablesFilterByDay("M");
        RecyclerView.Adapter adapterTraining = new CustomTrainingAdapter(getContext(), trainingTable);

        //RecyclerView.Adapter adapterFood = new CustomTrainingAdapter(getContext(), trainingTable2);
        recyclerViewTraining.setAdapter(adapterTraining);
        //recyclerViewFoods.setAdapter(adapterFood);


        return rootView;
    }
}