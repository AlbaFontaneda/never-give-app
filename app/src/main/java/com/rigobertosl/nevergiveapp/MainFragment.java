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
    private boolean isType;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weekDay = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());
        db.open();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerViewTraining = (RecyclerView) rootView.findViewById(R.id.list_main_training);
        RecyclerView recyclerViewFoods = (RecyclerView) rootView.findViewById(R.id.list_main_foods);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTraining.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManagerFood = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFoods.setLayoutManager(layoutManagerFood);

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
        isType = false;
        ArrayList<TrainingTable> trainingTable = db.getAllTablesFilterByDay(filterDay);
        ArrayList<FoodTable> foodTable = db.getAllFoodsFilterByDay(filterDay);

        RecyclerView.Adapter adapterTrain = new CustomTrainingAdapter(getContext(), trainingTable, filterDay);
        RecyclerView.Adapter adapterFood = new CustomFoodAdapter(getContext(), foodTable, filterDay, isType);

        recyclerViewTraining.setAdapter(adapterTrain);
        recyclerViewFoods.setAdapter(adapterFood);


        return rootView;
    }
}
