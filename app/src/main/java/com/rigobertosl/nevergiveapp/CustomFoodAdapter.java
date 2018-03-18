package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomFoodAdapter extends ArrayAdapter<String>{
    CustomFoodAdapter(Context context, String[] titulos) {
        super(context, R.layout.layout_foods, titulos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        View customView = buckysInflater.inflate(R.layout.layout_foods, parent,false);

        String singleTitleItem = getItem(position);
        final TextView titulo = (TextView) customView.findViewById(R.id.item_title);
        final ImageButton item_options = (ImageButton) customView.findViewById(R.id.item_options);

        titulo.setText(singleTitleItem);
        return customView;
    }
}
