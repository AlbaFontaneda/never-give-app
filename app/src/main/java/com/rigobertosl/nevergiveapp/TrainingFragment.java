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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_item);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        db = new DataBaseContract(getActivity());
        db.open();
        ArrayList<TrainingTable> trainingTable = db.getAllTables();
        db.close();
        if(trainingTable.size() == 0) {
            View itemView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.layout_no_content, container, false);
            TextView mensaje = itemView.findViewById(R.id.text_view);
            mensaje.setText("Parece que no tienes ninguna tabla de entrenamiento creada, ¿por qué no pruebas a crear una?");
            return itemView;
        } else {
            RecyclerView.Adapter adapter = new CustomTrainingAdapter(getContext(), trainingTable, null);
            recyclerView.setAdapter(adapter);
        }
        return rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
