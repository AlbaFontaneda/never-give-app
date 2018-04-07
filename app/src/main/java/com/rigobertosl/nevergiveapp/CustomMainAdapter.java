package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMainAdapter extends RecyclerView.Adapter<CustomMainAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TrainingTable> trainingTables;
    private ArrayList<FoodTable> foodTables;
    private DataBaseContract db;
    private RecyclerView recyclerView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageButton itemOptions;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            itemOptions = (ImageButton) view.findViewById(R.id.item_options);
        }
    }

    public CustomMainAdapter(Context mContext, ArrayList<TrainingTable> trainingTables, ArrayList<FoodTable> foodTables) {
        this.mContext = mContext;
        this.trainingTables = trainingTables;
        this.foodTables = foodTables;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_main, parent, false);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclcer_main);
        recyclerView.setHasFixedSize(true);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title.setText(trainingTables.get(position).getName());

    }
    @Override
    public int getItemCount() {
        return trainingTables.size();
    }
}
