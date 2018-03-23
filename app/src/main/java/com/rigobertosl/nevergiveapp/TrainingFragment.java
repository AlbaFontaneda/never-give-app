package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class TrainingFragment extends Fragment {

    private DataBaseContract db;

    public static TrainingFragment newInstance(int sectionNumber){
        final String ARG_SECTION_NUMBER = "section_number";
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training_custom_tab, container, false);

        db = new DataBaseContract(getActivity());
        db.open();

        String[] aux = {"NULL"};
        String[] tablas = fillDataTitle();
        if(tablas.length == 0) {
            tablas = aux;
        }
        fillDataContent();
        ListAdapter listAdapter = new CustomTrainingAdapter(getContext(), tablas);
        ListView lista = (ListView) rootView.findViewById(R.id.list_item);
        lista.setAdapter(listAdapter);

        // Para que se haga el scroll correctamente ocultando el toolbar
        ViewCompat.setNestedScrollingEnabled(lista, true);

        return rootView;
    }

    private String[] fillDataTitle() {

        ArrayList<String> names = db.fetchAllNamesNameTraining();
        String[] titles = names.toArray(new String[names.size()]);
        return titles;
    }

    private String[] fillDataContent() {
        String[] names = fillDataTitle();
        String[] content = {};
        ArrayList<String> list = new ArrayList<>();
        for(String name: names){
            List<Object> aux = db.fetchListByNameTrain(name);
            list.add(String.valueOf(aux));
        }
        content = list.toArray(new String[list.size()]);
        return content;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
