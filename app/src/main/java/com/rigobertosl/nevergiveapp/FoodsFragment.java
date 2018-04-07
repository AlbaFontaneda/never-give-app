package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class FoodsFragment extends Fragment{

    private DataBaseContract db;
    private int weekDay;
    private String filterDay;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weekDay = valueOf(getArguments().getInt("page_position"));
        db = new DataBaseContract(getActivity());
        db.open();

        View rootView = inflater.inflate(R.layout.fragment_foods, container, false);
        RecyclerView recyclerViewTraining = (RecyclerView) rootView.findViewById(R.id.list_food);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTraining.setLayoutManager(layoutManager);

        if(weekDay == 0) {
            filterDay = "Desayuno";
        }else if(weekDay == 1) {
            filterDay = "Almuerzo";
        }else if(weekDay == 2) {
            filterDay = "Comida";
        }else if(weekDay == 3) {
            filterDay = "Merienda";
        }else if(weekDay == 4) {
            filterDay = "Cena";
        }

        ArrayList<FoodTable> foodTables = db.getAllFoodsFilterByDay(filterDay);
        db.close();
        RecyclerView.Adapter adapterFoods = new CustomFoodAdapter(getContext(), foodTables, filterDay);

        recyclerViewTraining.setAdapter(adapterFoods);

        return rootView;
    }
}
