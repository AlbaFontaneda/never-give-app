package com.rigobertosl.nevergiveapp.events.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.events.activity.EventsActivity;
import com.rigobertosl.nevergiveapp.objects.Event;

import java.util.ArrayList;

public class EventHomeAdapter extends RecyclerView.Adapter<EventHomeAdapter.MyViewHolder> {

    private Resources resources;
    private ArrayList<Event> eventList;
    private String[] sports;
    private String[] sportsImagesSources;
    private static ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, hour, people;
        private ImageView image;

        public MyViewHolder (View view){
            super(view);
            title = view.findViewById(R.id.title);
            hour = view.findViewById(R.id.hour);
            people = view.findViewById(R.id.people);
            image = view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public EventHomeAdapter(ArrayList<Event> eventList, String[] sports, String[] imagesResources) {
        this.eventList = eventList;
        this.sports = sports;
        this.sportsImagesSources = imagesResources;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);
        resources = parent.getResources();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String title = eventList.get(position).getSport();
        holder.title.setText(title);
        holder.hour.setText(eventList.get(position).getDate().getTime());
        holder.people.setText(String.valueOf(eventList.get(position).getAssistants()));
        holder.image.setImageResource(getImage(title));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        EventHomeAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }

    private int getImage(String title){
        int index = 14;
        for(int i = 0; i < sports.length; i++){
            if(title.equals(sports[i])){
                index = i;
                break;
            }
        }
        return resources.getIdentifier(sportsImagesSources[index], "drawable", EventsActivity.PACKAGE_NAME);
    }
}
