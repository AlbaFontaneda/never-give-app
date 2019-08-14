package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rigobertosl.nevergiveapp.objects.TrainingTable;

import java.util.ArrayList;

public class TrainingFragmentDefault extends Fragment {

    private DataBaseContract db;

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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_item);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        db = new DataBaseContract(getActivity());
        db.open();
        ArrayList<TrainingTable> trainingTable = db.getAllDefaultTables();
        db.close();
        RecyclerView.Adapter adapter = new CustomTrainingDefaultTablesAdapter(getContext(), trainingTable);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}

