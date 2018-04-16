package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomTrainingDefaultTablesAdapter extends RecyclerView.Adapter<CustomTrainingDefaultTablesAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TrainingTable> trainingTables;
    private DataBaseContract db;
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageButton itemOptions;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            itemOptions = (ImageButton) view.findViewById(R.id.item_options);
        }
    }

    public CustomTrainingDefaultTablesAdapter(Context mContext, ArrayList<TrainingTable> trainingTables) {
        this.mContext = mContext;
        this.trainingTables = trainingTables;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_training_default, parent, false);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title.setText(trainingTables.get(position).getName());
        holder.title.setSingleLine(false);
        holder.itemOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.itemOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_default_training);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        ArrayList<TrainingTable> trainingTable = db.getAllDefaultTables();

                        switch (menuItem.getItemId()) {
                            case R.id.menu_default_table_show:
                                Intent intent = new Intent(mContext, DefaultResume.class);
                                intent.putExtra("tablaID", trainingTable.get(holder.getAdapterPosition()).getId());
                                intent.putExtra("isDefault", true);
                                mContext.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.title.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        db = new DataBaseContract(holder.title.getContext());
        db.open();


        ArrayList<TrainingTable> trainingTables;

        trainingTables = db.getAllDefaultTables();

        exerciseList = db.getAllDefaultExercisesFromTable(trainingTables.get(position));
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(exerciseAdapter);
    }

    public int getItemCount() {
        return trainingTables.size();
    }

    public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

        List<Exercise> exercises;

        public ExerciseAdapter(List<Exercise> exercises){
            this.exercises = exercises;
        }

        public class ExerciseViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView title, series, repeticiones;
            ImageView imageView;

            ExerciseViewHolder(View view) {
                super(view);
                cardView = (CardView)view.findViewById(R.id.card_view);
                imageView = (ImageView)view.findViewById(R.id.image);
                //imageView.setImageResource(R.drawable.achievements_logo);
                title = (TextView)view.findViewById(R.id.title);
                series = (TextView)view.findViewById(R.id.num_series);
                repeticiones = (TextView)view.findViewById(R.id.num_repeticiones);
            }
        }

        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_card, parent, false);
            ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(view);
            return exerciseViewHolder;
        }

        @Override
        public void onBindViewHolder(ExerciseViewHolder holder, int position) {
            holder.title.setText(exercises.get(position).getNombre());
            holder.series.setText(exercises.get(position).getSeries()+" series");
            holder.repeticiones.setText(exercises.get(position).getRepeticiones()+" repeticiones");
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

}
