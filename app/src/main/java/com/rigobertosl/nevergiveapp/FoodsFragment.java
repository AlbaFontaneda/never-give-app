package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class FoodsFragment extends Fragment{

    private DataBaseContract db;
    private int weekDay;
    private String filterType;
    private boolean isType;

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
            filterType = "Desayuno";
        }else if(weekDay == 1) {
            filterType = "Almuerzo";
        }else if(weekDay == 2) {
            filterType = "Comida";
        }else if(weekDay == 3) {
            filterType = "Merienda";
        }else if(weekDay == 4) {
            filterType = "Cena";
        }
        isType = true;
        ArrayList<FoodTable> foodTables = db.getAllFoodsFilterByType(filterType);
        db.close();

        if(foodTables.size() == 0) {
            View itemView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.layout_no_content, container, false);
            TextView mensaje = itemView.findViewById(R.id.text_view);
            mensaje.setText("Parece que no tienes ninguna comida creada, ¿por qué no pruebas a crear una?");
            return itemView;
        } else {

            RecyclerView.Adapter adapterFoods = new CustomFoodAdapter(getContext(), foodTables, filterType, isType);
            recyclerViewTraining.setAdapter(adapterFoods);
        }
        return rootView;
    }
}
