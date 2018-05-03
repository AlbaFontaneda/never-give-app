package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class CustomTrainingAdapter extends RecyclerView.Adapter<CustomTrainingAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TrainingTable> trainingTables;
    private String filterDay;
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
    
    public CustomTrainingAdapter(Context mContext, ArrayList<TrainingTable> trainingTables, String filterDay) {
        this.mContext = mContext;
        this.trainingTables = trainingTables;
        this.filterDay = filterDay;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_training, parent, false);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);

        return new MyViewHolder(itemView);
    }

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
                        ArrayList<TrainingTable> trainingTable;
                        if(filterDay == null) {
                            trainingTable = db.getAllTables();
                        } else {
                            trainingTable = db.getAllTablesFilterByDay(filterDay);
                        }

                        switch (item.getItemId()) {
                            case R.id.menu_training_elements_edit:

                                Intent intent = new Intent(mContext, TableResumeActivity.class);
                                if(mContext.getClass() == MainActivity.class){
                                    intent.putExtra("fromTraining", false);
                                }else if (mContext.getClass() == TrainingActivity.class){
                                    intent.putExtra("fromTraining", true);
                                }
                                intent.putExtra("tablaID", trainingTable.get(holder.getAdapterPosition()).getId());
                                intent.putExtra("isDefault", false);
                                mContext.startActivity(intent);

                                break;
                            case R.id.menu_training_elements_delete:

                                db = new DataBaseContract(mContext);
                                db.open();
                                db.deleteTable(trainingTable.get(holder.getAdapterPosition()), true);
                                db.close();
                                Toast.makeText(mContext,
                                        "Tabla eliminada", Toast.LENGTH_LONG).show();

                                trainingTables.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), trainingTables.size());

                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        db = new DataBaseContract(holder.title.getContext());
        db.open();
        ArrayList<TrainingTable> trainingTables;
        if(filterDay == null) {
            trainingTables = db.getAllTables();
        } else {
            trainingTables = db.getAllTablesFilterByDay(filterDay);
        }
        exerciseList = db.getAllExercisesFromTable(trainingTables.get(position));
        db.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.title.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
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
            byte[] b = exercises.get(position).getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            final Drawable d = new BitmapDrawable(mContext.getResources(), bmp);
            holder.imageView.setImageDrawable(d);
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