package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FoodsFragment extends Fragment{
    private DataBaseContract db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new DataBaseContract(getActivity());
        db.open();

        View rootView = inflater.inflate(R.layout.fragment_foods, container, false);
        RecyclerView recyclerViewTraining = (RecyclerView) rootView.findViewById(R.id.list_food);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTraining.setLayoutManager(layoutManager);

        ArrayList<TrainingTable> trainingTable = db.getAllTablesFilterByDay("M");
        RecyclerView.Adapter adapterTraining = new CustomTrainingAdapter(getContext(), trainingTable);

        recyclerViewTraining.setAdapter(adapterTraining);

        return rootView;
    }
}
