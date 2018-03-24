package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomTrainingAdapter extends ArrayAdapter<String>{

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exercisesList;

    CustomTrainingAdapter(Context context, String[] titulos) {
        super(context, R.layout.layout_training, titulos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        final View customView = buckysInflater.inflate(R.layout.layout_training, parent,false);

        String singleTitleItem = getItem(position);
        final TextView title = (TextView) customView.findViewById(R.id.item_title);
        final ImageButton options = (ImageButton) customView.findViewById(R.id.item_options);

        title.setText(singleTitleItem);

        recyclerView = (RecyclerView)customView.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true); // Para que no cambie el tamaño del Recycler
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        exercisesList = new ArrayList<>();
        exercisesList.add(new Exercise("Press de banca", "12"));
        exercisesList.add(new Exercise("Press superior", "20"));
        adapter = new ExerciseAdapter(exercisesList);
        recyclerView.setAdapter(adapter);



        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(customView);
            }
        });
        return customView;
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(view.findViewById(R.id.item_options).getContext(), view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });// to implement on click event on items of menu
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_training_elements, popup.getMenu());
        popup.show();
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
            holder.title.setText(exercises.get(position).nombre);
            holder.repeticiones.setText(exercises.get(position).repeticiones);
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
            TextView title, repeticiones;
            ImageView imageView;

            ExerciseViewHolder(View view) {
                super(view);
                cardView = (CardView)view.findViewById(R.id.card_view);
                imageView = (ImageView)view.findViewById(R.id.image);
                //imageView.setImageResource(R.drawable.achievements_logo);
                title = (TextView)view.findViewById(R.id.title);
                repeticiones = (TextView)view.findViewById(R.id.repeticiones);
            }
        }

    }
}
