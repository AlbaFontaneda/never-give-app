package com.rigobertosl.nevergiveapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainFragment extends Fragment{

    private DataBaseContract db;
    public static int weekDay;
    public String filterDay;

    public static MainFragment newInstance(int sectionNumber){
        final String ARG_SECTION_NUMBER = "section_number";
        weekDay = sectionNumber;
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_item);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(weekDay == 1) {
            filterDay = "LU";
        }else if(weekDay == 2) {
            filterDay = "M";
        }else if(weekDay == 3) {
            filterDay = "X";
        }else if(weekDay == 4) {
            filterDay = "JU";
        }else if(weekDay == 5) {
            filterDay = "VI";
        }else if(weekDay == 6) {
            filterDay = "SA";
        }else if(weekDay == 7) {
            filterDay = "DO";
        }

        db = new DataBaseContract(getActivity());
        db.open();

        ArrayList<TrainingTable> trainingTable = db.getAllTablesFilterByDay(filterDay);
        RecyclerView.Adapter adapter = new CustomTrainingAdapter(getContext(), trainingTable);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
