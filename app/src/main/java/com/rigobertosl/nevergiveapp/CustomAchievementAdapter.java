package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomAchievementAdapter extends RecyclerView.Adapter<CustomAchievementAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Achievement> achievements;
    private String type;
    private DataBaseContract db;

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

    public CustomAchievementAdapter(Context mContext, ArrayList<Achievement> achievements, String type) {
        this.mContext = mContext;
        this.type = type;
        this.achievements = achievements;
        db = new DataBaseContract(this.mContext);
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
        if (type == "training"){
            db.open();
            boolean[] createdTables = db.getNumOfTables();
            db.close();
            if (position < 5){
                if (createdTables[position]){
                    holder.itemType.setImageResource(R.drawable.ic_logro_completado);
                }
            }
            if (position >= 5 && position < 13){
                //String[] dia = holder.itemView.getContext().getResources().getStringArray(R.array.diasSemana);
                db.open();
                boolean[] existTableOnDay = db.existDayTable();
                db.close();

                if (position == 12){
                    if (Arrays.toString(existTableOnDay).contains("T")){
                        holder.itemType.setImageResource(R.drawable.ic_logro_completado);
                    }
                } else {
                    if (existTableOnDay[position - 5]){
                        holder.itemType.setImageResource(R.drawable.ic_logro_completado);
                    }
                }
            }
            if (position >= 13 && position < 20){
                db.open();
                boolean[] typeExercises = db.getNumTypeExercises();
                db.close();

                if (typeExercises[position - 13]){
                    holder.itemType.setImageResource(R.drawable.ic_logro_completado);
                }
            }
            if (position >= 20){
                db.open();
                boolean[] numExercises = db.getNumExercises();
                db.close();
                if (numExercises[position - 20]){
                    holder.itemType.setImageResource(R.drawable.ic_logro_completado);
                }
            }
        }else if (type == "comidas"){

        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
