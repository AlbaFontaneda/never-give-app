package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TrainingFragmentDefault extends Fragment {

    public static TrainingFragmentDefault newInstance(int sectionNumber){
        final String ARG_SECTION_NUMBER = "section_number";
        TrainingFragmentDefault fragment = new TrainingFragmentDefault();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training_default_tab, container, false);

        String[] tablas = {"Cardio", "Musculación", "Principiante", "Intermedio", "Avanzado", "Estiramientos"};
        ListAdapter listAdapter = new CustomTrainingAdapter(getContext(), tablas);
        ListView lista = (ListView) rootView.findViewById(R.id.list_item);
        lista.setAdapter(listAdapter);

        // Para que se haga el scroll correctamente ocultando el toolbar
        ViewCompat.setNestedScrollingEnabled(lista, true);

        return rootView;
    }
}
