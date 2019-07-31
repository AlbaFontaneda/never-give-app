package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.objects.FoodTable;
import com.rigobertosl.nevergiveapp.objects.TrainingTable;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class MainFragment extends Fragment{

    private DataBaseContract db;
    public int weekDay;
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
        ArrayList<TrainingTable> trainingTable = new ArrayList<>();
        ArrayList<TrainingTable> auxTrain = db.getAllTablesFilterByDay(filterDay);
        if(auxTrain.size() > 1) {
            trainingTable.add(0, auxTrain.get(0));
            trainingTable.add(1, auxTrain.get(1));
        } else {
            trainingTable = auxTrain;
        }

        ArrayList<FoodTable> foodTable = new ArrayList<>();
        ArrayList<FoodTable> auxFood = db.getAllFoodsFilterByDay(filterDay);

        if(auxFood.size() > 1) {
            foodTable.add(0, auxFood.get(0));
            foodTable.add(1, auxFood.get(1));
        } else {
            foodTable = auxFood;
        }

        if(trainingTable.size() == 0 && foodTable.size() == 0) {

            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_no_content, container, false);
            TextView mensaje = itemView.findViewById(R.id.text_view);
            mensaje.setText("Parece que no tienes nada establecido para este día. ¿Por qué no pruebas a guardar algun dato?");
            return itemView;

        } else {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewTraining.setLayoutManager(layoutManager);

            RecyclerView.LayoutManager layoutManagerFood = new LinearLayoutManager(getActivity());
            recyclerViewFoods.setLayoutManager(layoutManagerFood);

            RecyclerView.Adapter adapterTrain = new CustomTrainingAdapter(getContext(), trainingTable, filterDay);
            RecyclerView.Adapter adapterFood = new CustomFoodAdapter(getContext(), foodTable, filterDay, isType);
            recyclerViewTraining.setAdapter(adapterTrain);
            recyclerViewFoods.setAdapter(adapterFood);
        }

        return rootView;
    }
}
