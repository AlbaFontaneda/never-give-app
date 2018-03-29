package com.rigobertosl.nevergiveapp;


import android.content.Context;
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

public class CustomTrainingAdapter extends RecyclerView.Adapter<CustomTrainingAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TrainingTable> trainingTables;
    private ArrayList<ArrayList> content = new ArrayList<>();

    private DataBaseContract db;
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public int count;
        public TextView title;
        public ImageButton itemOptions;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            itemOptions = (ImageButton) view.findViewById(R.id.item_options);
        }
    }
    
    public CustomTrainingAdapter(Context mContext, ArrayList<TrainingTable> trainingTables) {
        this.mContext = mContext;
        this.trainingTables = trainingTables;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_training, parent, false);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);

        return new MyViewHolder(itemView);
    }
    //TODO: Esto seria lo optimo, nose como llamarlo
    /*public void deleteAllData(){
        final int size = trainingTables.size();
        trainingTables.clear();
        notifyItemRangeRemoved(0, size);
    }
    */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(trainingTables.get(position).getName());
        holder.itemOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.itemOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_training_elements);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        ArrayList<TrainingTable> trainingTable = db.getAllTables();

                        switch (item.getItemId()) {
                            case R.id.menu_training_elements_edit:
                                Toast.makeText(mContext,
                                        "Edit pulsado", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_training_elements_delete:

                                db = new DataBaseContract(mContext);
                                db.open();
                                db.deleteTable(trainingTable.get(holder.getAdapterPosition()), true);
                                db.close();
                                Toast.makeText(mContext,
                                        "Delete pulsado", Toast.LENGTH_LONG).show();

                                trainingTables.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), trainingTables.size());

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.title.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        db = new DataBaseContract(holder.title.getContext());
        db.open();

        ArrayList<String> nombresTablas = db.fetchAllNamesNameTraining();
        exerciseList = db.getAllExercisesFromTable(nombresTablas.get(position));
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(exerciseAdapter);

    }

    @Override
    public int getItemCount() {
        return trainingTables.size();
    }


    public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

        List<Exercise> exercises;

        public ExerciseAdapter(List<Exercise> exercises){
            this.exercises = exercises;
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

    }
}