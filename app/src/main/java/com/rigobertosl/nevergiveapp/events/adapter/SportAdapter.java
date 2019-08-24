package com.rigobertosl.nevergiveapp.events.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.events.activity.EventsActivity;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.MyViewHolder> {

    private Resources resources;
    private String[] sports;
    private String[] sportsImagesSources;
    private static ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public SportAdapter(String[] sports, String[] imagesResources) {
        this.sports = sports;
        this.sportsImagesSources = imagesResources;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sport_card, parent, false);
        resources = parent.getResources();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(sports[position]);
        int imageInt  = resources.getIdentifier(sportsImagesSources[position], "drawable", EventsActivity.PACKAGE_NAME);
        holder.image.setImageResource(imageInt);
    }

    @Override
    public int getItemCount() {
        return sports.length;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SportAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }
}
