package com.rigobertosl.nevergiveapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAchievementAdapter extends RecyclerView.Adapter<CustomAchievementAdapter.MyViewHolder> {

    private ArrayList<Achievement> achievements;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description, points;
        public ImageButton itemType;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            description = (TextView) view.findViewById(R.id.item_content);
            points = (TextView) view.findViewById(R.id.item_points);
            itemType = (ImageButton) view.findViewById(R.id.item_type);
        }
    }

    public CustomAchievementAdapter(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_achievement, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(achievements.get(position).getTitle());
        holder.description.setText(achievements.get(position).getDescription());
        holder.points.setText(achievements.get(position).getPoints());
        holder.itemType.setImageResource(R.drawable.ic_logro_no_completado);
        if (achievements.get(position).isCompleted()){
            holder.itemType.setImageResource(R.drawable.ic_logro_completado);
        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
