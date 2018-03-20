package com.rigobertosl.nevergiveapp;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        db = new DataBaseContract(getActivity());
        db.open();

        String[] aux = {"NULL"};
        String[] tablas = fillData();
        if(tablas.length == 0) {
            tablas = aux;
        }
        fillData();
        ListAdapter listAdapter = new CustomTrainingAdapter(getContext(), tablas);
        ListView lista = (ListView) rootView.findViewById(R.id.list_item);
        lista.setAdapter(listAdapter);

        // Para que se haga el scroll correctamente ocultando el toolbar
        ViewCompat.setNestedScrollingEnabled(lista, true);

        return rootView;
    }

    private String[] fillData() {

        Cursor cursor = db.fetchAllRowsTraining();
        ArrayList<String> aux = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do {
                String varaible1 = cursor.getString(cursor.getColumnIndex(DataBaseContract.DataBaseEntryTrain.COLUMN_NAME));
                aux.add(varaible1);
            }while (cursor.moveToNext());
        }
        String[] titles = aux.toArray(new String[aux.size()]);
        return titles;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
