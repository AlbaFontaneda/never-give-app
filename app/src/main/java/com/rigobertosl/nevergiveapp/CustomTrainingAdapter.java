package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTrainingAdapter extends RecyclerView.Adapter<CustomTrainingAdapter.MyViewHolder> {
    private Context mContext;
    private String[] titles = {};
    private ArrayList<ArrayList> content = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public int count;
        public TextView title;
        public TextView ejercicio;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            ejercicio = (TextView) view.findViewById(R.id.ejercicio);
        }
    }
    
    public CustomTrainingAdapter(Context mContext, String[] titles, ArrayList<ArrayList> content) {
        this.mContext = mContext;
        this.titles = titles;
        this.content = content;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_training, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(this.titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}