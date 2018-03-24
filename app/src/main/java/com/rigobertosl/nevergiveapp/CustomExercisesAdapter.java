package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class CustomExercisesAdapter extends ArrayAdapter<String>{
    CustomExercisesAdapter(Context context, String[] titulos) {
        super(context, R.layout.layout_training, titulos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        final View customView = buckysInflater.inflate(R.layout.layout_training, parent,false);

        return customView;
    }

}
